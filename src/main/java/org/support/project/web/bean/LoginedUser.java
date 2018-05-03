package org.support.project.web.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

import org.support.project.web.config.CommonWebParameter;
import org.support.project.web.entity.GroupsEntity;
import org.support.project.web.entity.RolesEntity;
import org.support.project.web.entity.UsersEntity;

/**
 * ログインしたユーザのセッションに保持する情報
 * 
 * @author koda
 *
 */
public class LoginedUser implements Serializable {
    /** シリアルバージョン */
    private static final long serialVersionUID = 1L;

    /** ログインしたユーザの情報 */
    private UsersEntity loginUser;

    /** ログインしたユーザが持つ権限 */
    private List<RolesEntity> roles;

    /** ログインしたユーザが所属するグループ */
    private List<GroupsEntity> groups;

    /** ログインしたユーザが利用しているロケール */
    private Locale locale;

    /**
     * ユーザIDを取得
     * 
     * @return user id
     */
    public Integer getUserId() {
        if (loginUser != null) {
            return loginUser.getUserId();
        }
        return Integer.MIN_VALUE;
    }

    /**
     * 管理者かどうか
     * 
     * @return isadmin
     */
    public boolean isAdmin() {
        if (roles != null) {
            for (RolesEntity roleId : roles) {
                if (roleId.getRoleKey().equals(CommonWebParameter.ROLE_ADMIN)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 指定のロールを持っているかチェック
     * 
     * @param roleArray roleArray
     * @return result
     */
    public boolean haveRole(String... roleArray) {
        if (roles != null) {
            for (RolesEntity roleId : roles) {
                for (String role : roleArray) {
                    if (roleId.getRoleKey().equals(role)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Get login user info
     * 
     * @return loginUser loginUser
     */
    public UsersEntity getLoginUser() {
        if (loginUser != null) {
            loginUser.setPassword(""); // セッションに持つ場合、パスワードはクリアすること
        }
        return loginUser;
    }

    /**
     * Set login user info
     * 
     * @param loginUser セットする loginUser
     */
    public void setLoginUser(UsersEntity loginUser) {
        if (loginUser != null) {
            loginUser.setPassword(""); // セッションに持つ場合、パスワードはクリアすること
        }
        this.loginUser = loginUser;
    }

    /**
     * Get roles
     * 
     * @return roles roles
     */
    public List<RolesEntity> getRoles() {
        return roles;
    }

    /**
     * Set roles
     * 
     * @param roles セットする roles
     */
    public void setRoles(List<RolesEntity> roles) {
        this.roles = roles;
    }

    /**
     * Get groups
     * 
     * @return groups
     */
    public List<GroupsEntity> getGroups() {
        return groups;
    }

    /**
     * Set Groups
     * 
     * @param groups セットする groups
     */
    public void setGroups(List<GroupsEntity> groups) {
        this.groups = groups;
    }

    /**
     * Get Locale
     * 
     * @return Locale
     */
    public Locale getLocale() {
        return locale;
    }

    /**
     * Set Locale
     * 
     * @param locale Locale
     */
    public void setLocale(Locale locale) {
        this.locale = locale;
    }

}
