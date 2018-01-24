package org.support.project.web.filter;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.net.SocketException;

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
import org.support.project.di.Container;
import org.support.project.web.boundary.Boundary;
import org.support.project.web.common.HttpStatus;
import org.support.project.web.common.InvokeTarget;
import org.support.project.web.exception.SendErrorException;
import org.support.project.web.logic.CallControlLogic;

/**
 * リクエストのパス
 * 
 * @author Koda
 *
 */
public class ControlManagerFilter implements Filter {
    /** ログ */
    private static Log log = LogFactory.getLog(MethodHandles.lookup());
    
    private CallControlLogic callControlLogic;
    
    @Override
    public void destroy() {
        callControlLogic = null;
    }

    @Override
    public void init(FilterConfig filterconfig) throws ServletException {
        String controlPackage = filterconfig.getInitParameter("controlPackage");
        if (controlPackage == null) {
            throw new ServletException("control package name is required.");
        }
        String classSuffix = filterconfig.getInitParameter("classSuffix");
        if (classSuffix == null) {
            classSuffix = "Control";
        }
        String sub = filterconfig.getInitParameter("subpackages");
        boolean searchSubpackages = true;
        if (sub != null && sub.toLowerCase().equals("false")) {
            searchSubpackages = false;
        }
        String ignoreRegularExpression = filterconfig.getInitParameter("ignore-regular-expression");
        
        String callControlLogicClass = "org.support.project.web.logic.impl.CallControlLogicImpl";
        if (filterconfig.getInitParameter("callControlLogicClass") != null) {
            callControlLogicClass = filterconfig.getInitParameter("callControlLogicClass");
        }
        try {
            Class<?> clazz = Class.forName(callControlLogicClass);
            callControlLogic = (CallControlLogic) Container.getComp(clazz);
            callControlLogic.init(controlPackage, classSuffix, searchSubpackages, ignoreRegularExpression);
        } catch (ClassNotFoundException e) {
            throw new ServletException(e);
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        try {
            InvokeTarget invokeTarget = callControlLogic.searchInvokeTarget(request, response);
            if (invokeTarget == null) {
                filterChain.doFilter(request, response);
                return;
            }
            invoke(invokeTarget, request, response);
        } catch (SendErrorException e) {
            response.sendError(e.getHttpStatus());
        } catch (Exception e) {
            log.trace("any exception is thrown. [" + e.getClass().getName() + "]", e);
            if (e.getCause() != null) {
                log.trace("[Cause]" + e.getCause().getMessage(), e.getCause());
            }
            if (e instanceof SocketException) {
                log.debug("SocketException. " + e.getMessage());
                // ソケットがおかしくなっているので何も出来ない
            } else {
                // HttpUtil.forward(response, request, "/open/errors/error.jsp");
                if (!response.isCommitted()) {
                    log.error("ERROR SEND");
                    response.sendError(HttpStatus.SC_500_INTERNAL_SERVER_ERROR);
                }
            }
            // LoggingFilterへ
            if (e instanceof ServletException) {
                throw (ServletException) e;
            } else if (e instanceof IOException) {
                throw (IOException) e;
            } else {
                throw new ServletException(e);
            }
        }
    }

    protected void invoke(InvokeTarget invokeTarget, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Object result = invokeTarget.invoke();
        if (result instanceof Boundary) {
            Boundary boundary = (Boundary) result;
            boundary.navigate();
        }
    }




}
