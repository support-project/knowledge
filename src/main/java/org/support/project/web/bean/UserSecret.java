package org.support.project.web.bean;

import java.io.Serializable;

/**
 * Cookieに格納するユーザの指紋
 * 本オブジェクトを暗号化しCookieに入れておく。
 * 暗号化をとき、ID／名称／Emailが一致した場合に、そのCookieでログイン可能とする。
 * @author Koda
 */
public class UserSecret implements Serializable {
    /** シリアルバージョン */
    private static final long serialVersionUID = 1L;
    
    /** ユーザID  */
    private String userKey;
    /** ユーザ名  */
    private String userName;
    /** Email */
    private String email;
    /**
     * Get userName
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }
    /**
     * Set userName
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }
    /**
     * Get email
     * @return the email
     */
    public String getEmail() {
        return email;
    }
    /**
     * Set email
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }
    /**
     * Get userKey
     * @return the userKey
     */
    public String getUserKey() {
        return userKey;
    }
    /**
     * Set userKey
     * @param userKey the userKey to set
     */
    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }
    
}
