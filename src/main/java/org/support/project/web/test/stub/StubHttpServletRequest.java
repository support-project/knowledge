package org.support.project.web.test.stub;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.AsyncContext;
import javax.servlet.DispatcherType;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpUpgradeHandler;
import javax.servlet.http.Part;

import org.apache.commons.collections.iterators.IteratorEnumeration;
import org.support.project.common.exception.NotImplementedException;
import org.support.project.common.util.PropertyUtil;
import org.support.project.common.util.StringJoinBuilder;

public class StubHttpServletRequest implements HttpServletRequest {
    /** セッション */
    private HttpSession session = null;
    /** Cookie */
    private List<Cookie> cookies;

    /** パラメータのマップ */
    private Hashtable<String, String[]> parameterMap;

    /** エンコード */
    private String characterEncoding;
    /** ContextPath */
    private String contextPath;
    
    private String servletPath;
    private String pathInfo;
    private String method;
    private Locale locale = Locale.JAPAN;
    private Map<String, Object> attributes = new HashMap<>();
    private InputStream inputStream;

    /**
     * コンストラクタ
     */
    public StubHttpServletRequest() {
        parameterMap = new Hashtable<>();
        session = new StubHttpSession();
        cookies = new ArrayList<>();
    }
    /**
     * コンストラクタ
     * @param request StubHttpServletRequest
     */
    public StubHttpServletRequest(StubHttpServletRequest request) {
        this();
        this.setCookies(request.cookies);
        this.setSession(request.session);
    }
    /**
     * set attributes by property
     * @param object
     */
    public void setAttributeByProperty(Object object) {
        List<String> props = PropertyUtil.getPropertyNames(object);
        for (String key : props) {
            this.setAttribute(key, PropertyUtil.getPropertyValue(object, key));
        }
    }

    /**
     * パラメータ追加
     * 
     * @param param
     *            param
     * @param value
     *            value
     */
    public void addParameter(String param, String value) {
        List<String> values = new ArrayList<>();
        if (parameterMap.containsKey(value)) {
            String[] vs = parameterMap.get(param);
            values.addAll(Arrays.asList(vs));
        }
        values.add(value);
        String[] vs = values.toArray(new String[0]);
        parameterMap.put(param, vs);
    }

    @Override
    public String getParameter(String paramString) {
        String[] params = parameterMap.get(paramString);
        if (params == null) {
            return null;
        } else if (params.length > 0) {
            return params[0];
        }
        return null;
    }

    @Override
    public Enumeration<String> getParameterNames() {
        return parameterMap.keys();
    }

    @Override
    public String[] getParameterValues(String paramString) {
        String[] params = parameterMap.get(paramString);
        if (params == null) {
            return null;
        }
        return params;
    }
    @Override
    public Map<String, String[]> getParameterMap() {
        return parameterMap;
    }
    
    @Override
    public String getQueryString() {
        StringJoinBuilder builder = new StringJoinBuilder();
        for (Entry<String, String[]> entry : parameterMap.entrySet()) {
            StringJoinBuilder join = new StringJoinBuilder<>(entry.getValue());
            builder.append(entry.getKey()).append("=").append(join.join(","));
        }
        return builder.join("&");
    }
    
    @Override
    public String getServletPath() {
        return servletPath;
    }
    public void setServletPath(String servletPath) {
        this.servletPath = servletPath;
    }
    @Override
    public String getPathInfo() {
        return pathInfo;
    }
    public void setPathInfo(String pathInfo) {
        this.pathInfo = pathInfo;
    }
    
    @Override
    public String getMethod() {
        return method;
    }
    public void setMethod(String method) {
        this.method = method;
    }
    @Override
    public HttpSession getSession() {
        return session;
    }
    @Override
    public HttpSession getSession(boolean paramBoolean) {
        return session;
    }
    public void setSession(HttpSession session) {
        this.session = session;
    }
    
    @Override
    public Object getAttribute(String name) {
        return attributes.get(name);
    }
    @Override
    public void setAttribute(String name, Object value) {
        attributes.put(name, value);
    }
    @Override
    public void removeAttribute(String paramString) {
        attributes.remove(paramString);
    }
    @Override
    public Enumeration<String> getAttributeNames() {
        Enumeration enumeration = new IteratorEnumeration(attributes.keySet().iterator());
        return enumeration;
    }
    @Override
    public Locale getLocale() {
        return locale;
    }
    public void setLocale(Locale locale) {
        this.locale = locale;
    }
    public void addCookie(Cookie paramCookie) {
        cookies.add(paramCookie);
    }
    @Override
    public Cookie[] getCookies() {
        return (Cookie[]) cookies.toArray(new Cookie[0]);
    }
    /**
     * @param cookies
     *            the cookies to set
     */
    public void setCookies(List<Cookie> cookies) {
        this.cookies = cookies;
    }
    /**
     * @param contextPath
     *            the contextPath to set
     */
    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }
    @Override
    public String getCharacterEncoding() {
        return this.characterEncoding;
    }

    @Override
    public void setCharacterEncoding(String characterEncoding) throws UnsupportedEncodingException {
        this.characterEncoding = characterEncoding;
    }
    
    @Override
    public ServletInputStream getInputStream() throws IOException {
        return new StubServletInputStream(this.inputStream);
    }
    public void setInputstream(InputStream inputStream) {
        this.inputStream = inputStream;
    }
    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(inputStream));
    }
    

    
    
    
    @Override
    public boolean authenticate(HttpServletResponse paramHttpServletResponse) throws IOException, ServletException {
        throw new NotImplementedException("NotImplemented");
    }

    @Override
    public void login(String paramString1, String paramString2) throws ServletException {
        throw new NotImplementedException("NotImplemented");
    }

    @Override
    public void logout() throws ServletException {
        throw new NotImplementedException("NotImplemented");
    }

    @Override
    public String getContextPath() {
        return contextPath;
    }

    @Override
    public int getContentLength() {
        throw new NotImplementedException("NotImplemented");
    }

    @Override
    public String getContentType() {
        throw new NotImplementedException("NotImplemented");
    }


    @Override
    public String getProtocol() {
        throw new NotImplementedException("NotImplemented");
    }

    @Override
    public String getScheme() {
        return "http";
    }

    @Override
    public String getServerName() {
        return "localhost";
    }

    @Override
    public int getServerPort() {
        return 8080;
    }


    @Override
    public String getRemoteAddr() {
        throw new NotImplementedException("NotImplemented");
    }

    @Override
    public String getRemoteHost() {
        throw new NotImplementedException("NotImplemented");
    }


    @Override
    public Enumeration<Locale> getLocales() {
        throw new NotImplementedException("NotImplemented");
    }

    @Override
    public boolean isSecure() {
        throw new NotImplementedException("NotImplemented");
    }

    @Override
    public RequestDispatcher getRequestDispatcher(String paramString) {
        throw new NotImplementedException("NotImplemented");
    }

    @Override
    public String getRealPath(String paramString) {
        throw new NotImplementedException("NotImplemented");
    }

    @Override
    public int getRemotePort() {
        throw new NotImplementedException("NotImplemented");
    }

    @Override
    public String getLocalName() {
        throw new NotImplementedException("NotImplemented");
    }

    @Override
    public String getLocalAddr() {
        throw new NotImplementedException("NotImplemented");
    }

    @Override
    public int getLocalPort() {
        throw new NotImplementedException("NotImplemented");
    }

    @Override
    public ServletContext getServletContext() {
        throw new NotImplementedException("NotImplemented");
    }

    @Override
    public AsyncContext startAsync() {
        throw new NotImplementedException("NotImplemented");
    }

    @Override
    public AsyncContext startAsync(ServletRequest paramServletRequest, ServletResponse paramServletResponse) {
        throw new NotImplementedException("NotImplemented");
    }

    @Override
    public boolean isAsyncStarted() {
        throw new NotImplementedException("NotImplemented");
    }

    @Override
    public boolean isAsyncSupported() {
        throw new NotImplementedException("NotImplemented");
    }

    @Override
    public AsyncContext getAsyncContext() {
        throw new NotImplementedException("NotImplemented");
    }

    @Override
    public DispatcherType getDispatcherType() {
        throw new NotImplementedException("NotImplemented");
    }

    @Override
    public String getAuthType() {
        throw new NotImplementedException("NotImplemented");
    }

    @Override
    public long getDateHeader(String paramString) {
        throw new NotImplementedException("NotImplemented");
    }

    @Override
    public String getHeader(String paramString) {
        return "";
    }

    @Override
    public Enumeration<String> getHeaders(String paramString) {
        throw new NotImplementedException("NotImplemented");
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        throw new NotImplementedException("NotImplemented");
    }

    @Override
    public int getIntHeader(String paramString) {
        throw new NotImplementedException("NotImplemented");
    }


    @Override
    public String getPathTranslated() {
        throw new NotImplementedException("NotImplemented");
    }


    @Override
    public String getRemoteUser() {
        throw new NotImplementedException("NotImplemented");
    }

    @Override
    public boolean isUserInRole(String paramString) {
        throw new NotImplementedException("NotImplemented");
    }

    @Override
    public Principal getUserPrincipal() {
        throw new NotImplementedException("NotImplemented");
    }

    @Override
    public String getRequestedSessionId() {
        throw new NotImplementedException("NotImplemented");
    }

    @Override
    public String getRequestURI() {
        throw new NotImplementedException("NotImplemented");
    }

    @Override
    public StringBuffer getRequestURL() {
        throw new NotImplementedException("NotImplemented");
    }


    @Override
    public boolean isRequestedSessionIdValid() {
        throw new NotImplementedException("NotImplemented");
    }

    @Override
    public boolean isRequestedSessionIdFromCookie() {
        throw new NotImplementedException("NotImplemented");
    }

    @Override
    public boolean isRequestedSessionIdFromURL() {
        throw new NotImplementedException("NotImplemented");
    }

    @Override
    public boolean isRequestedSessionIdFromUrl() {
        throw new NotImplementedException("NotImplemented");
    }

    @Override
    public Collection<Part> getParts() throws IOException, IllegalStateException, ServletException {
        throw new NotImplementedException("NotImplemented");
    }

    @Override
    public Part getPart(String paramString) throws IOException, IllegalStateException, ServletException {
        throw new NotImplementedException("NotImplemented");
    }


    @Override
    public long getContentLengthLong() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public String changeSessionId() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <T extends HttpUpgradeHandler> T upgrade(Class<T> arg0) throws IOException, ServletException {
        // TODO Auto-generated method stub
        return null;
    }





}
