package org.support.project.web.filter;

import java.io.IOException;
import java.lang.invoke.MethodHandles;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;

public class EncodingFilter implements Filter {
    /** ログ */
    private static Log log = LogFactory.getLog(MethodHandles.lookup());

    /** エンコード */
    private String encode = "UTF-8";
    
    @Override
    public void init(FilterConfig config) throws ServletException {
        this.encode = config.getInitParameter("encoding");
    }
    @Override
    public void destroy() {
        this.encode = null;
    }
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        req.setCharacterEncoding(encode);
        if (log.isTraceEnabled()) {
            log.trace("Request setCharacterEncoding : " + encode);
        }
        chain.doFilter(req, res);
    }
}
