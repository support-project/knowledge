package org.support.project.web.logic;

import java.lang.annotation.Annotation;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.support.project.common.exception.SerializeException;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.serialize.SerializeUtils;
import org.support.project.common.util.StringUtils;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.web.bean.CSRFTokens;
import org.support.project.web.common.HttpUtil;
import org.support.project.web.common.InvokeTarget;
import org.support.project.web.config.AppConfig;
import org.support.project.web.config.WebConfig;
import org.support.project.web.control.service.Delete;
import org.support.project.web.control.service.Get;
import org.support.project.web.control.service.Post;
import org.support.project.web.control.service.Put;
import org.support.project.web.dao.CsrfTokensDao;
import org.support.project.web.dao.SystemConfigsDao;
import org.support.project.web.entity.CsrfTokensEntity;
import org.support.project.web.entity.SystemConfigsEntity;

@DI(instance = Instance.Singleton)
public class HttpRequestCheckLogic {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(MethodHandles.lookup());
    /** セッション／Cookieに格納するキー */
    public static final String CSRF_TOKENS = "CSRFTokens";
    /** セッション／リクエストに格納するキー */
    public static final String CSRF_REQIDS = "CSRFReqIds";
    /** リクエストIDのキー（リクエストスコープにセット） */
    public static final String REQ_ID_KEY = "__REQ_ID_KEY";
    
    /** Httpヘッダーにセットするリクエストトークンのキー */
    private static final String REQUEST_TOKEN = "request-token";
    
    /**
     * Get instance
     * @return instance
     */
    public static HttpRequestCheckLogic get() {
        return Container.getComp(HttpRequestCheckLogic.class);
    }
    
    /**
     * CSRFの簡易対策で、Referrerをチェックする
     * @param request
     * @return
     */
    public boolean checkReferrer(HttpServletRequest request) {
        SystemConfigsDao dao = SystemConfigsDao.get();
        SystemConfigsEntity config = dao.selectOnKey(WebConfig.KEY_SYSTEM_URL, AppConfig.get().getSystemName());
        if (config == null) {
            // システムのURLがわからないので、チェックは成功とする
            return true;
        }
        String url = config.getConfigValue();
        String referrer = request.getHeader("REFERER");
        if (StringUtils.isNotEmpty(referrer) && referrer.startsWith(url)) {
            return true;
        }
        LOG.warn("It is a request from outside the system.");
        LOG.warn("Request: " + HttpUtil.getRequestInfo(request));
        LOG.warn("Referer: " + referrer);
        return false;
    }
    
    
    private String getSubscribeToken(InvokeTarget invokeTarget) {
        Method method = invokeTarget.getTargetMethod();
        Annotation[] annotations = method.getAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation instanceof Get) {
                Get config = (Get) annotation;
                return config.subscribeToken();
            } else if (annotation instanceof Put) {
                Put config = (Put) annotation;
                return config.subscribeToken();
            } else if (annotation instanceof Post) {
                Post config = (Post) annotation;
                return config.subscribeToken();
            } else if (annotation instanceof Delete) {
                Delete config = (Delete) annotation;
                return config.subscribeToken();
            }
        }
        return "";
    }
    
    private String getPublishToken(InvokeTarget invokeTarget) {
        Method method = invokeTarget.getTargetMethod();
        Annotation[] annotations = method.getAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation instanceof Get) {
                Get config = (Get) annotation;
                return config.publishToken();
            } else if (annotation instanceof Put) {
                Put config = (Put) annotation;
                return config.publishToken();
            } else if (annotation instanceof Post) {
                Post config = (Post) annotation;
                return config.publishToken();
            } else if (annotation instanceof Delete) {
                Delete config = (Delete) annotation;
                return config.publishToken();
            }
        }
        return "";
    }
    
    private boolean isCheckReferer(InvokeTarget invokeTarget) {
        Method method = invokeTarget.getTargetMethod();
        Annotation[] annotations = method.getAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation instanceof Get) {
                Get config = (Get) annotation;
                return config.checkReferer();
            } else if (annotation instanceof Put) {
                Put config = (Put) annotation;
                return config.checkReferer();
            } else if (annotation instanceof Post) {
                Post config = (Post) annotation;
                return config.checkReferer();
            } else if (annotation instanceof Delete) {
                Delete config = (Delete) annotation;
                return config.checkReferer();
            }
        }
        return false;
    }
    
    private boolean isCheckReqToken(InvokeTarget invokeTarget) {
        Method method = invokeTarget.getTargetMethod();
        Annotation[] annotations = method.getAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation instanceof Get) {
                Get config = (Get) annotation;
                return config.checkReqToken();
            } else if (annotation instanceof Put) {
                Put config = (Put) annotation;
                return config.checkReqToken();
            } else if (annotation instanceof Post) {
                Post config = (Post) annotation;
                return config.checkReqToken();
            } else if (annotation instanceof Delete) {
                Delete config = (Delete) annotation;
                return config.checkReqToken();
            }
        }
        return false;
    }
    private boolean isCheckCookieToken(InvokeTarget invokeTarget) {
        Method method = invokeTarget.getTargetMethod();
        Annotation[] annotations = method.getAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation instanceof Get) {
                Get config = (Get) annotation;
                return config.checkCookieToken();
            } else if (annotation instanceof Put) {
                Put config = (Put) annotation;
                return config.checkCookieToken();
            } else if (annotation instanceof Post) {
                Post config = (Post) annotation;
                return config.checkCookieToken();
            } else if (annotation instanceof Delete) {
                Delete config = (Delete) annotation;
                return config.checkCookieToken();
            }
        }
        return false;
    }
    private boolean isCheckHeaderToken(InvokeTarget invokeTarget) {
        Method method = invokeTarget.getTargetMethod();
        Annotation[] annotations = method.getAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation instanceof Get) {
                Get config = (Get) annotation;
                return config.checkHeaderToken();
            } else if (annotation instanceof Put) {
                Put config = (Put) annotation;
                return config.checkHeaderToken();
            } else if (annotation instanceof Post) {
                Post config = (Post) annotation;
                return config.checkHeaderToken();
            } else if (annotation instanceof Delete) {
                Delete config = (Delete) annotation;
                return config.checkHeaderToken();
            }
        }
        return false;
    }
    
    /**
     * CSRF用のリクエストキーなど発行
     * @param invokeTarget
     * @param request
     * @param response
     * @throws NoSuchAlgorithmException
     */
    public void setCSRFTocken(InvokeTarget invokeTarget, HttpServletRequest request, HttpServletResponse response, Integer userId) throws NoSuchAlgorithmException {
        String tokenkey = getPublishToken(invokeTarget);
        if (!StringUtils.isEmpty(tokenkey)) {
            HttpSession session = request.getSession();
            // Cookie token
            CSRFTokens tokens = (CSRFTokens) session.getAttribute(CSRF_TOKENS);
            if (tokens == null) {
                LOG.debug("Cookie tokens pool on session is null");
                tokens = new CSRFTokens();
                session.setAttribute(CSRF_TOKENS, tokens);
            }
            String result = tokens.addToken(tokenkey);
            if (LOG.isDebugEnabled()) {
                LOG.debug("Add token to CSRF_TOKENS. key:" + tokenkey + "  token:" + result);
            }
            try {
                HttpUtil.setCookie(request, response, CSRF_TOKENS, SerializeUtils.objectToBase64(tokens));
            } catch (SerializeException e) {
                LOG.debug("Error on set CSRF token to request. " + e.getClass().getSimpleName());
            }
            
            // Reauest token
            if (userId != Integer.MIN_VALUE) { // ログインしていない場合、Integer.MIN_VALUEが入ってくる
                String reqid = CsrfTokensDao.get().addToken(tokenkey, userId);
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Add token to CSRF_REQIDS. key:" + tokenkey + "  token:" + reqid);
                }
                request.setAttribute(REQ_ID_KEY, reqid);
                response.setHeader(REQUEST_TOKEN, reqid);
            }
        }
    }
    
    /**
     * CSRFチェック対象であればチェック実施
     * @param invokeTarget
     * @param request
     * @return
     */
    public boolean checkCSRF(InvokeTarget invokeTarget, HttpServletRequest request, Integer userId) {
        if (isCheckReferer(invokeTarget)) {
            // CSRFの簡易対策で、Referrerをチェックする
            HttpRequestCheckLogic check = HttpRequestCheckLogic.get();
            if (!check.checkReferrer(request)) {
                LOG.warn("Invalid referrer header.");
                return false;
            }
        }

        String tokenkey = getSubscribeToken(invokeTarget);
        if (StringUtils.isEmpty(tokenkey)) {
            // token チェックの対象になっていない
            return true;
        }
        HttpSession session = request.getSession();
        
        // Cookieを使った簡易チェック
        if (isCheckCookieToken(invokeTarget)) {
            CSRFTokens tokens = (CSRFTokens) session.getAttribute(CSRF_TOKENS);
            if (tokens == null) {
                LOG.warn("request token is invalid. session token is null");
                return false;
            }
            String base64 = HttpUtil.getCookie(request, CSRF_TOKENS);
            if (StringUtils.isEmpty(base64)) {
                LOG.warn("request token is invalid. coockie token is null");
                return false;
            }
            try {
                CSRFTokens reqTokens = SerializeUtils.Base64ToObject(base64, CSRFTokens.class);
                if (!tokens.checkToken(tokenkey, reqTokens)) {
                    LOG.warn("Token NG : " + tokenkey);
                    return false;
                }
            } catch (SerializeException e) {
                LOG.warn("Failed to restore Token", e);
                return false;
            }
        }
        
        // HiddenパラメータにRequestTokenがセットされているかチェックする場合
        if (isCheckReqToken(invokeTarget)) {
            String reqId = request.getParameter(REQ_ID_KEY);
            if (!checkReqId(reqId, tokenkey, userId)) {
                LOG.warn("Request parameter's token is NG : " + tokenkey);
                return false;
            }
        }
        // リクエストヘッダにRequestTokenがセットされているかチェックする場合
        if (isCheckHeaderToken(invokeTarget)) {
            String reqId = request.getHeader(REQUEST_TOKEN);
            if (!checkReqId(reqId, tokenkey, userId)) {
                LOG.warn("Request header's token is NG : " + tokenkey);
                return false;
            }
        }
        LOG.debug("Request check : success ");
        return true;
    }
    
    private boolean checkReqId(String reqId, String tokenkey, Integer userId) {
        CsrfTokensEntity csrf = CsrfTokensDao.get().selectOnKey(tokenkey, userId);
        if (csrf == null) {
            return false;
        }
        if (!csrf.getToken().equals(reqId)) {
            LOG.info("Req Token NG : " + reqId);
            return false;
        }
        // 一度使った Request tokenは無効にする？
        // 並列にPostやPutリクエストを送る場合は削除はしない方が良いので、いったんは、有効期限が切れるまで、何回でも使えるようにする
        return true;
    }
    
    
}
