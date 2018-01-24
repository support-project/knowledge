package org.support.project.web.filter;

import java.io.IOException;
import java.lang.invoke.MethodHandles;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.StringUtils;
import org.support.project.web.common.HttpUtil;

public class PartialsFilter implements Filter {
    /** ログ */
    private static Log log = LogFactory.getLog(MethodHandles.lookup());

    @Override
    public void init(FilterConfig config) throws ServletException {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        StringBuilder builder = new StringBuilder();

        String path = req.getServletPath();
        if (path != null) {
            String extention = StringUtils.getExtension(path);
            if (extention == null) {
                path = path.concat(".jsp");
            } else if (extention.equals(".html")) {
                path = path.replaceAll(".html", ".jsp");
            }
            builder.append(path);
        } else {
            builder.append("/index.html");
        }

        if (log.isDebugEnabled()) {
            log.debug("path : " + builder.toString());
        }
        HttpUtil.forward(res, req, builder.toString());
    }

}
