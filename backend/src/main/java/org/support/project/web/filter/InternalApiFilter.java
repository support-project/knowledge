package org.support.project.web.filter;

import java.io.IOException;
import java.lang.invoke.MethodHandles;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.di.Container;
import org.support.project.web.common.HttpStatus;
import org.support.project.web.common.InvokeTarget;
import org.support.project.web.config.AppConfig;
import org.support.project.web.exception.SendErrorException;
import org.support.project.web.logic.CallControlLogic;
import org.support.project.web.logic.invoke.CallControlExLogicImpl;

public class InternalApiFilter extends PublicApiFilter {
    /** ログ */
    private static Log LOG = LogFactory.getLog(MethodHandles.lookup());

    @Override
    public void doFilter(ServletRequest servletrequest, ServletResponse servletresponse, FilterChain filterchain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletrequest;
        HttpServletResponse res = (HttpServletResponse) servletresponse;
        
        // 処理対象外であれば、filterchainを呼び出して終了
        if (!isTarget(req, res)) {
            filterchain.doFilter(req, res);
            return;
        }
        // メンテナンス中であれば、APIのパスは 503 を返す
        if (AppConfig.get().isMaintenanceMode()) {
            sendError(req, res, HttpStatus.SC_503_SERVICE_UNAVAILABLE);
            return;
        }
        
        boolean setSession = false;
        // 既にログインしたセッションを持っている場合、それを使うが、無い場合は認証Tokenのチェックに入る
        if (!getAuthenticationLogic().isLogined(req)) {
            setSession = setSession(req, res);
        }
        // APIの実際の処理を呼び出し
        try {
            CallControlLogic callControlLogic = Container.getComp(CallControlExLogicImpl.class);
            InvokeTarget invokeTarget = callControlLogic.searchInvokeTarget(req, res);
            if (invokeTarget == null) {
                sendError(req, res, HttpStatus.SC_404_NOT_FOUND);
                return;
            }
            invoke(invokeTarget, req, res);
            // もし新たにセッションをAPIで生成した場合は、リクエスト毎にクリア（基本的には、内部APIの場合はクリアする必要は無いはず）
            if (setSession) {
                LOG.info("clear session. sessionid:" + req.getSession().getId());
                getAuthenticationLogic().clearSession(req);
            }
        } catch (SendErrorException e) {
            LOG.warn(e.getMessage());
            sendError(req, res, e.getHttpStatus());
        } catch (Exception e) {
            LOG.error("error", e);
            sendError(req, res, HttpStatus.SC_500_INTERNAL_SERVER_ERROR);
        }
    }
    


}
