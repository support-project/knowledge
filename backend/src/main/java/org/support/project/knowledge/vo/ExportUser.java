package org.support.project.knowledge.vo;

import java.sql.Timestamp;

public class ExportUser {
    /** ユーザID */
    private Integer userId;
    /** ユーザKEY ユニーク */
    private String userKey;
    /** ユーザ名 */
    private String userName;
    /** 登録ユーザ */
    private Integer insertUser;
    /** 登録日時 */
    private Timestamp insertDatetime;
    /** 更新ユーザ */
    private Integer updateUser;
    /** 更新日時 */
    private Timestamp updateDatetime;
    /** 削除フラグ */
    private Integer deleteFlag;

    /**
     * @return the userId
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * @return the userKey
     */
    public String getUserKey() {
        return userKey;
    }

    /**
     * @param userKey the userKey to set
     */
    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the insertUser
     */
    public Integer getInsertUser() {
        return insertUser;
    }

    /**
     * @param insertUser the insertUser to set
     */
    public void setInsertUser(Integer insertUser) {
        this.insertUser = insertUser;
    }

    /**
     * @return the insertDatetime
     */
    public Timestamp getInsertDatetime() {
        return insertDatetime;
    }

    /**
     * @param insertDatetime the insertDatetime to set
     */
    public void setInsertDatetime(Timestamp insertDatetime) {
        this.insertDatetime = insertDatetime;
    }

    /**
     * @return the updateUser
     */
    public Integer getUpdateUser() {
        return updateUser;
    }

    /**
     * @param updateUser the updateUser to set
     */
    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
    }

    /**
     * @return the updateDatetime
     */
    public Timestamp getUpdateDatetime() {
        return updateDatetime;
    }

    /**
     * @param updateDatetime the updateDatetime to set
     */
    public void setUpdateDatetime(Timestamp updateDatetime) {
        this.updateDatetime = updateDatetime;
    }

    /**
     * @return the deleteFlag
     */
    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    /**
     * @param deleteFlag the deleteFlag to set
     */
    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

}
