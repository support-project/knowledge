package org.support.project.web.test.stub;

import java.util.List;

import javax.servlet.http.Cookie;

import org.support.project.common.util.StringUtils;
import org.support.project.di.Container;
import org.support.project.di.exception.DIException;
import org.support.project.web.bean.AccessUser;
import org.support.project.web.config.CommonWebParameter;
import org.support.project.web.control.Control;

public class ControlContainer {

    private AccessUser loginedUser = null;
    private String contextPath = null;
    private List<Cookie> cookies = null;

    /**
     * コンポーネントを取得する
     * 
     * @param type type 
     * @param <T> type
     * @return component
     * @throws DIException DIException
     */
    public <T> T getComp(final Class<? extends Control> type) throws DIException {
        Control control = Container.getComp(type);

        StubHttpServletRequest request = new StubHttpServletRequest();
        StubHttpServletResponse response = new StubHttpServletResponse(request);
        control.setRequest(request);
        control.setResponse(response);

        if (loginedUser != null) {
            request.getSession().setAttribute(CommonWebParameter.LOGIN_USER_INFO_SESSION_KEY, loginedUser);
        }

        if (StringUtils.isNotEmpty(contextPath)) {
            request.setContextPath(contextPath);
        }
        if (cookies != null) {
            request.setCookies(cookies);
        }

        return (T) control;
    }

    /**
     * @param loginedUser
     *            the loginedUser to set
     */
    public void setLoginedUser(AccessUser loginedUser) {
        this.loginedUser = loginedUser;
    }

    /**
     * @param contextPath
     *            the contextPath to set
     */
    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    /**
     * @param cookies
     *            the cookies to set
     */
    public void setCookies(List<Cookie> cookies) {
        this.cookies = cookies;
    }

}
