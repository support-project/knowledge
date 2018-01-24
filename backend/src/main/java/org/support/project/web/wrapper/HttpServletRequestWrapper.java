package org.support.project.web.wrapper;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.support.project.web.bean.AccessUser;
import org.support.project.web.entity.RolesEntity;
import org.support.project.web.logic.AuthenticationLogic;

/**
 * HttpServletRequestのラッパークラス
 * 
 * ユーザID取得・ロールチェックを変更している
 * 
 * @author Koda
 *
 */
public class HttpServletRequestWrapper extends javax.servlet.http.HttpServletRequestWrapper implements HttpServletRequest {

    /** ラップするHttpServletRequestオブジェクト */
    private HttpServletRequest request_;

    private AuthenticationLogic<AccessUser> authenticationLogic = null;

    /**
     * コンストラクタ
     * 
     * @param request
     *            HttpServletRequest
     * @param authenticationLogic
     *            AuthenticationLogic
     */
    public HttpServletRequestWrapper(final HttpServletRequest request, AuthenticationLogic authenticationLogic) {
        super(request);
        this.request_ = request;
        this.authenticationLogic = authenticationLogic;
    }

    @Override
    public Principal getUserPrincipal() {
        Principal principal = super.getUserPrincipal();
        return new PrincipalWrapper(principal, getRemoteUser());
    }

    @Override
    public String getRemoteUser() {
        // HttpSession se = request_.getSession();
        // String userId = (String) se.getAttribute(CommonWebParameter.LOGIN_USER_ID_SESSION_KEY);
        String userId = null;
        try {
            AccessUser loginedUser = authenticationLogic.getSession(request_);
            if (loginedUser != null) {
                // userId = loginedUser.getLoginUser().getUserKey();
                userId = String.valueOf(loginedUser.getUserInfomation().getUserId());
            }
        } catch (Exception e) {
        }
        return userId;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean isUserInRole(final String role) {
        // HttpSession se = request_.getSession();
        // List<String> roles = (List<String>) se.getAttribute(CommonWebParameter.LOGIN_ROLE_IDS_SESSION_KEY);
        try {
            AccessUser loginedUser = authenticationLogic.getSession(request_);
            if (loginedUser != null) {
                List<RolesEntity> roles = loginedUser.getRoles();
                if (roles == null) {
                    return false;
                }

                for (RolesEntity rolesEntity : roles) {
                    if (rolesEntity.getRoleKey().equals(role)) {
                        return true;
                    }
                }
                return false;
            }
        } catch (Exception e) {
        }
        return false;
    }
}
