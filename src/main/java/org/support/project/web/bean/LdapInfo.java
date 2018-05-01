package org.support.project.web.bean;

public class LdapInfo {
    /** User Id */
    private String id = "";
    /** User Name */
    private String name = "";
    /** Mail Address */
    private String mail = "";
    /** 管理者かどうか */
    private boolean admin = false;
    
    /**
     * @return the id
     */
    public String getId() {
        return id;
    }
    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }
    /**
     * @return the name
     */
    public String getName() {
        if (name == null) {
            name = "";
        }
        return name;
    }
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * @return the mail
     */
    public String getMail() {
        return mail;
    }
    /**
     * @param mail the mail to set
     */
    public void setMail(String mail) {
        this.mail = mail;
    }
    /**
     * @return the admin
     */
    public boolean isAdmin() {
        return admin;
    }
    /**
     * @param admin the admin to set
     */
    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
    
    
}
