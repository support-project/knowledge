package org.support.project.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.support.project.web.logic.AuthenticationLogic;
import org.support.project.web.wrapper.HttpServletRequestWrapper;

public class SeqFilter implements Filter {
    /** 認証／認可ロジックのインスタンス */
    private AuthenticationLogic<?> authenticationLogic = null;

    @Override
    public void init(FilterConfig filterconfig) throws ServletException {
        authenticationLogic = FilterInitUtility.readAuthenticationLogic(filterconfig);
    }

    @Override
    public void destroy() {
        this.authenticationLogic = null;
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req_origin = (HttpServletRequest) request;
        HttpServletRequestWrapper req = new HttpServletRequestWrapper((HttpServletRequest) req_origin, authenticationLogic);
        HttpServletResponse res = (HttpServletResponse) response;
        res.setHeader("X-XSS-Protection", "1; mode=block");
        res.setHeader("X-Frame-Options", "DENY");
        res.setHeader("X-Content-Type-Options", "nosniff");
        chain.doFilter(req, res);
    }


}
