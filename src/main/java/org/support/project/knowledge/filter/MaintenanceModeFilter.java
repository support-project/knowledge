package org.support.project.knowledge.filter;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.support.project.knowledge.config.AppConfig;
import org.support.project.web.bean.LoginedUser;
import org.support.project.web.common.HttpUtil;
import org.support.project.web.config.CommonWebParameter;
import org.support.project.web.dao.SystemsDao;
import org.support.project.web.entity.SystemsEntity;

public class MaintenanceModeFilter implements Filter {
    
    private Pattern pattern = Pattern.compile("^/bower|^/images|^/css|^/js|^/favicon.ico|^/lang|css$|js$|jpg$|jpeg$|gif$|png$|ico$|html$");
    
    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig config) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletrequest, ServletResponse servletresponse, FilterChain filterchain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletrequest;
        HttpServletResponse res = (HttpServletResponse) servletresponse;
        
        StringBuilder pathBuilder = new StringBuilder();
        pathBuilder.append(req.getServletPath());
        if (req.getPathInfo() != null && req.getPathInfo().length() > 0) {
            pathBuilder.append(req.getPathInfo());
        }
        String path = pathBuilder.toString();
        
        if (AppConfig.get().isMaintenanceMode()) {
            // アクセス遮断の対象外のものは、普通にアクセス可能
            Matcher matcher = pattern.matcher(path);
            if (matcher.find()) {
                // 対象外なのでスルー
                filterchain.doFilter(servletrequest, servletresponse);
                return;
            }
            
            // メンテナンスモード中にして、一般のユーザはアクセスできないようにする
            LoginedUser user = (LoginedUser) req.getSession().getAttribute(CommonWebParameter.LOGIN_USER_INFO_SESSION_KEY);
            if (user != null && user.isAdmin()) {
                // 管理者
                if (path.equals("/protect.migrate")) {
                    SystemsEntity entity = SystemsDao.get().selectOnKey(AppConfig.get().getSystemName());
                    if (entity != null) {
                        req.setAttribute("db_version", entity.getVersion());
                    }
                    HttpUtil.forward(res, req, "/WEB-INF/views/commons/migrate.jsp");
                    return;
                } else if (path.equals("/migrate")) {
                    // マイグレーションのWebSocketパス
                    filterchain.doFilter(servletrequest, servletresponse);
                    return;
                }
            }
            HttpUtil.forward(res, req, "/WEB-INF/views/commons/maintenance.jsp");
            return;
        } else {
            if (path.equals("/protect.migrate")) {
                SystemsEntity entity = SystemsDao.get().selectOnKey(AppConfig.get().getSystemName());
                if (entity != null) {
                    req.setAttribute("db_version", entity.getVersion());
                }
                HttpUtil.forward(res, req, "/WEB-INF/views/commons/migrate.jsp");
                return;
            }
            filterchain.doFilter(servletrequest, servletresponse);
        }
    }

}
