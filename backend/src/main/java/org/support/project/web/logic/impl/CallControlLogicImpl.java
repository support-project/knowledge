package org.support.project.web.logic.impl;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.security.NoSuchAlgorithmException;
import java.util.Enumeration;
import java.util.List;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.support.project.common.classanalysis.ClassAnalysis;
import org.support.project.common.classanalysis.ClassAnalysisFactory;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.PropertyUtil;
import org.support.project.common.util.StringUtils;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.web.bean.AccessUser;
import org.support.project.web.common.HttpStatus;
import org.support.project.web.common.HttpUtil;
import org.support.project.web.common.InvokeSearch;
import org.support.project.web.common.InvokeTarget;
import org.support.project.web.config.HttpMethod;
import org.support.project.web.control.Control;
import org.support.project.web.exception.CallControlException;
import org.support.project.web.exception.InvalidParamException;
import org.support.project.web.exception.SendErrorException;
import org.support.project.web.logic.CallControlLogic;
import org.support.project.web.logic.HttpRequestCheckLogic;

import net.arnx.jsonic.JSONException;


@DI(instance = Instance.Singleton)
public class CallControlLogicImpl implements CallControlLogic {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(MethodHandles.lookup());
    
    /** 対象外のパスの正規表現のデフォルト */
    protected static final String IGNORE_REGULAR_EXPRESSION = "^open|css$|js$|jpg$|jpeg$|gif$|png$|init$";
    /** 対象外のパスの正規表現のルール */
    protected Pattern ignoreRegularExpressionPattern = null;
    /** クラス検索 */
    protected static InvokeSearch _invokeSearch = null;
    
    protected InvokeSearch getInvokeSearch() {
        if (_invokeSearch == null) {
            _invokeSearch = Container.getComp(InvokeSearch.class);
        }
        return _invokeSearch;
    }
    
    /* (non-Javadoc)
     * @see org.support.project.web.logic.impl.CallControlLogic#init(java.lang.String, java.lang.String, boolean, java.lang.String)
     */
    @Override
    public void init(String controlPackage, String classSuffix, boolean searchSubpackages, String ignoreRegularExpression) {
        String[] controlPackages; // 
        if (controlPackage.indexOf(",") != -1) {
            controlPackages = controlPackage.split(",");
        } else {
            controlPackages = new String[1];
            controlPackages[0] = controlPackage;
        }
        if (StringUtils.isNotEmpty(ignoreRegularExpression)) {
            this.ignoreRegularExpressionPattern = Pattern.compile(ignoreRegularExpression);
        } else {
            this.ignoreRegularExpressionPattern = Pattern.compile(IGNORE_REGULAR_EXPRESSION);
        }
        // 対象のクラスを登録
        InvokeSearch invokeSearch = getInvokeSearch();
        for (String target : controlPackages) {
            invokeSearch.addTarget(target, classSuffix, searchSubpackages);
        }
    }
    /* (non-Javadoc)
     * @see org.support.project.web.logic.impl.CallControlLogic#searchInvokeTarget(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    public InvokeTarget searchInvokeTarget(HttpServletRequest request, HttpServletResponse response)
            throws SendErrorException, InstantiationException, IllegalAccessException, JSONException,
            IOException, InvalidParamException, NoSuchAlgorithmException {
        StringBuilder pathBuilder = new StringBuilder();
        pathBuilder.append(request.getServletPath());
        if (request.getPathInfo() != null && request.getPathInfo().length() > 0) {
            pathBuilder.append(request.getPathInfo());
        }
        if (ignoreRegularExpressionPattern != null) {
            Matcher matcher = ignoreRegularExpressionPattern.matcher(pathBuilder.toString());
            if (matcher.find()) {
                return null;
            }
        }
        String path = request.getServletPath();
        LOG.trace("real path : " + path);
        String pathInfo = null;
        if (path.startsWith("/")) {
            path = path.substring(1);
        }
        if (StringUtils.countOccurrencesOf(path, "/") > 1) {
            pathInfo = path.substring(path.indexOf("/", path.indexOf("/") + 1));
            path = path.substring(0, path.indexOf("/", path.indexOf("/") + 1));
        }
        if (path.length() == 0) {
            path = "index";
        }
        if (path.indexOf("/") == -1) {
            // メソッド指定無し
            path = path + "/index";
        }
        if (path.endsWith("/")) {
            if (path.indexOf("/") != path.lastIndexOf("/")) {
                // "/"が複数あり、最後が"/"になっている
                path = path.substring(0, path.length() - 1);
            } else {
                // メソッド指定無し
                path = path + "index";
            }
        }
        if (LOG.isTraceEnabled()) {
            LOG.trace("path : " + path);
            LOG.trace("pathinfo : " + pathInfo);
        }

        String method = request.getMethod().toLowerCase();
        HttpMethod m = HttpMethod.get;
        if (method.equals("post")) {
            m = HttpMethod.post;
        } else if (method.equalsIgnoreCase("put")) {
            m = HttpMethod.put;
        } else if (method.equalsIgnoreCase("delete")) {
            m = HttpMethod.delete;
        }
        InvokeSearch invokeSearch = getInvokeSearch();
        InvokeTarget invokeTarget = invokeSearch.getController(m, path, pathInfo);
        if (invokeTarget != null) {
            AccessUser loginedUser = HttpUtil.getLoginedUser(request);
            Integer loginId = Integer.MIN_VALUE;
            if (loginedUser != null) {
                loginId = loginedUser.getUserId();
            }
            
            HttpRequestCheckLogic check = HttpRequestCheckLogic.get();
            if (!check.checkCSRF(invokeTarget, request, loginId)) {
                // CSRFチェック対象であればチェック実施
                throw new CallControlException(HttpStatus.SC_403_FORBIDDEN, method, path, "CSRF check error.");
            }
            // CSRF用のリクエストキーなど発行
            check.setCSRFTocken(invokeTarget, request, response, loginId);
            
            // コントローラーで処理を呼び出す場合、パラメータは全てリクエストのアトリビュートにコピーする
            this.copyAttribute(request);
            // パスで取得できる値があればセット
            for (Entry<String, String> entry : invokeTarget.getPathValue().entrySet()) {
                request.setAttribute(entry.getKey(), entry.getValue());
            }

            if (auth(invokeTarget, request, response, path, pathInfo)) {
                return this.setInvokeParams(invokeTarget, request, response, path, pathInfo);
            } else {
                // 認可エラー
                throw new CallControlException(HttpStatus.SC_403_FORBIDDEN, method, path, "Authentication error.");
            }
        }
        return null;
    }
    
    /**
     * リクエストのパラメータを、全て、アトリビュートでもアクセス可能にする為、コピーする
     * 
     * @param request request
     */
    private void copyAttribute(HttpServletRequest request) {
        Enumeration<String> enumeration = request.getParameterNames();
        while (enumeration.hasMoreElements()) {
            String name = (String) enumeration.nextElement();
            if (request.getParameterValues(name) != null) {
                if (request.getParameterValues(name).length > 1) {
                    request.setAttribute(name, request.getParameterValues(name));
                } else {
                    request.setAttribute(name, request.getParameter(name));
                }
            } else {
                request.setAttribute(name, request.getParameter(name));
            }
        }
    }
    
    /**
     * 指定の機能(メソッド)にアクセス可能かチェックする
     * 
     * @param invokeTarget invokeTarget
     * @param request request
     * @param response response
     * @param pathInfo pathInfo
     * @param path path
     * @return result
     */
    protected boolean auth(InvokeTarget invokeTarget, HttpServletRequest request, HttpServletResponse response, String path, String pathInfo) {
        List<String> roles = invokeTarget.getRoles();
        if (roles != null && !roles.isEmpty()) {
            AccessUser loginedUser = HttpUtil.getLoginedUser(request);
            if (loginedUser == null) {
                return false;
            }
            for (String role : roles) {
                if (request.isUserInRole(role)) {
                    return true;
                }
            }
            return false;
        }
        return true;
    }
    
    /**
     * コントローラの処理を呼び出し
     * 
     * @param invokeTarget invokeTarget
     * @param request request
     * @param response response
     * @param pathInfo pathInfo
     * @param path path
     * @return result
     * @throws InvalidParamException 
     * @throws IOException 
     * @throws JSONException 
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     */
    private InvokeTarget setInvokeParams(InvokeTarget invokeTarget, HttpServletRequest request, HttpServletResponse response,
            String path, String pathInfo) throws InstantiationException, IllegalAccessException, JSONException, IOException, InvalidParamException {
        // サービスクラス内に、HttpServletRequest HttpServletResponseのフィールドが存在すればセットする
        ClassAnalysis classAnalysis = ClassAnalysisFactory.getClassAnalysis(invokeTarget.getTargetClass());
        Object invoke = invokeTarget.getTarget();

        List<String> props = classAnalysis.getPropertyNames();
        for (String prop : props) {
            if (HttpServletRequest.class.isAssignableFrom(classAnalysis.getPropertyType(prop))) {
                PropertyUtil.setPropertyValue(invoke, prop, request);
            } else if (HttpServletResponse.class.isAssignableFrom(classAnalysis.getPropertyType(prop))) {
                PropertyUtil.setPropertyValue(invoke, prop, response);
            } else if (InvokeTarget.class.isAssignableFrom(classAnalysis.getPropertyType(prop))) {
                PropertyUtil.setPropertyValue(invoke, prop, invokeTarget);
            }
        }
        
        // 実行のパラメータを準備
        Class<?>[] parameterClass = invokeTarget.getTargetMethod().getParameterTypes();
        Object[] params = null;
        if (parameterClass == null || parameterClass.length == 0) {
            // invokeTarget.invoke(null);
            params = null;
        } else {
            params = new Object[parameterClass.length];
            int cnt = 0;
            for (Class<?> class1 : parameterClass) {
                if (HttpServletRequest.class.isAssignableFrom(class1)) {
                    params[cnt] = request;
                } else if (HttpServletResponse.class.isAssignableFrom(class1)) {
                    params[cnt] = response;
                } else {
                    Object param = HttpUtil.parseRequest(request, class1);
                    params[cnt] = param;
                }
                cnt++;
            }
        }

        if (Control.class.isAssignableFrom(invokeTarget.getTargetClass())) {
            Control control = (Control) invoke;
            control.setPath(path);
            control.setPathInfo(pathInfo);
        }
        invokeTarget.setParams(params);
        return invokeTarget;
    }
    
    
}
