package org.support.project.knowledge.vo.api;

import java.util.Date;

public class AuthResult {
    private User user;
    private String token;
    private Date expires;

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public Date getExpires() {
        return expires;
    }
    public void setExpires(Date expires) {
        this.expires = expires;
    }
}
