package org.support.project.web.filter;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;

import org.support.project.common.util.StringUtils;
import org.support.project.web.bean.AccessUser;
import org.support.project.web.logic.AuthenticationLogic;

public class FilterInitUtility {
    /** 認証／認可ロジックのクラス名 */
    private static final String DEFAULT_AUTHLOGIC_CLASSNAME = "org.support.project.web.logic.impl.DefaultAuthenticationLogicImpl";

    @SuppressWarnings("unchecked")
    public static AuthenticationLogic<?> readAuthenticationLogic(final FilterConfig filterconfig) throws ServletException {
        String authLogicClassName = DEFAULT_AUTHLOGIC_CLASSNAME;
        String className = filterconfig.getServletContext().getInitParameter("auth-class-name");
        if (StringUtils.isNotEmpty(className)) {
            authLogicClassName = className;
        }
        try {
            Class<AuthenticationLogic<AccessUser>> class1;
            class1 = (Class<AuthenticationLogic<AccessUser>>) Class.forName(authLogicClassName);
            return  org.support.project.di.Container.getComp(class1);
        } catch (ClassNotFoundException e) {
            throw new ServletException(e);
        }
    }
}
