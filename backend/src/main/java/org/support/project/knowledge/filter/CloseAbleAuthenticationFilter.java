package org.support.project.knowledge.filter;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.support.project.common.util.StringUtils;
import org.support.project.di.Container;
import org.support.project.knowledge.config.AppConfig;
import org.support.project.knowledge.config.SystemConfig;
import org.support.project.knowledge.config.UserConfig;
import org.support.project.knowledge.logic.AccountLogic;
import org.support.project.knowledge.logic.KnowledgeAuthenticationLogic;
import org.support.project.knowledge.logic.SystemConfigLogic;
import org.support.project.knowledge.vo.UserConfigs;
import org.support.project.web.common.HttpStatus;
import org.support.project.web.common.HttpUtil;
import org.support.project.web.dao.SystemConfigsDao;
import org.support.project.web.entity.SystemConfigsEntity;
import org.support.project.web.filter.AuthenticationFilter;
import org.support.project.web.logic.AuthenticationLogic;

public class CloseAbleAuthenticationFilter extends AuthenticationFilter {

    private Pattern pattern = null;

    /*
     * (non-Javadoc)
     * 
     * @see org.support.project.web.filter.AuthenticationFilter#init(javax.servlet.FilterConfig)
     */
    @Override
    public void init(FilterConfig filterconfig) throws ServletException {
        SystemConfigsDao dao = SystemConfigsDao.get();
        SystemConfigsEntity config = dao.selectOnKey(SystemConfig.SYSTEM_EXPOSE_TYPE, AppConfig.get().getSystemName());
        if (config != null) {
            if (SystemConfig.SYSTEM_EXPOSE_TYPE_CLOSE.equals(config.getConfigValue())) {
                SystemConfigLogic.get().setClose(true);
            }
        }
        String ignoreRegularExpression = filterconfig.getInitParameter("close-ignore-regular-expression");
        if (StringUtils.isNotEmpty(ignoreRegularExpression)) {
            this.pattern = Pattern.compile(ignoreRegularExpression);
        }
        super.init(filterconfig);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.support.project.web.filter.AuthenticationFilter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse,
     * javax.servlet.FilterChain)
     */
    @Override
    public void doFilter(ServletRequest servletrequest, ServletResponse servletresponse, FilterChain filterchain)
            throws IOException, ServletException {
        if (SystemConfigLogic.get().isClose()) {
            HttpServletRequest req = (HttpServletRequest) servletrequest;
            HttpServletResponse res = (HttpServletResponse) servletresponse;
            
            // ブラウザから必ず送られてくるCookie値でユーザ設定情報を作成
            UserConfigs userConfig = new UserConfigs();
            String timezone = HttpUtil.getCookie(req, AccountLogic.get().getCookieKeyTimezone());
            if (StringUtils.isNotEmpty(timezone)) {
                userConfig.setTimezone(timezone);
            }
            String offset = HttpUtil.getCookie(req, AccountLogic.get().getCookieKeyTimezoneOffset());
            if (StringUtils.isInteger(offset)) {
                userConfig.setTimezoneOffset(Integer.parseInt(offset));
            }
            req.setAttribute(UserConfig.REQUEST_USER_CONFIG_KEY, userConfig);
            
            try {
                if (!isLogin(req)) {
                    AuthenticationLogic logic = Container.getComp(KnowledgeAuthenticationLogic.class);
                    logic.cookieLogin(req, res);
                }

                if (!isLogin(req)) {
                    // ログインしていない
                    if (pattern != null) {
                        StringBuilder pathBuilder = new StringBuilder();
                        pathBuilder.append(req.getServletPath());
                        if (req.getPathInfo() != null && req.getPathInfo().length() > 0) {
                            pathBuilder.append(req.getPathInfo());
                        }
                        String path = pathBuilder.toString();
                        Matcher matcher = pattern.matcher(path);
                        if (!matcher.find() && !path.equals(getLoginProcess())) {
                            // 対象外でないし、ログインページへの遷移でない
                            String page = req.getParameter("page");
                            req.setAttribute("page", page);

                            res.setStatus(HttpStatus.SC_401_UNAUTHORIZED);
                            StringBuilder builder = new StringBuilder();
                            builder.append(getLoginPage());
                            HttpUtil.forward(res, req, builder.toString());
                            return;
                        }
                    }
                }
            } catch (Exception e) {
                throw new ServletException(e);
            }
        }
        super.doFilter(servletrequest, servletresponse, filterchain);
    }

}
