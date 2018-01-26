package org.support.project.knowledge.vo.api;

import java.sql.Timestamp;

public class Config {
    /** コンフィグ名 */
    private String configName;
    /** コンフィグ値 */
    private String configValue;
    
    /** 登録ユーザ */
    private Integer insertUser;
    /** 登録日時 */
    private Timestamp insertDatetime;
    /** 更新ユーザ */
    private Integer updateUser;
    /** 更新日時 */
    private Timestamp updateDatetime;
    
    
    public String getConfigName() {
        return configName;
    }
    public void setConfigName(String configName) {
        this.configName = configName;
    }
    public String getConfigValue() {
        return configValue;
    }
    public void setConfigValue(String configValue) {
        this.configValue = configValue;
    }
    public Integer getInsertUser() {
        return insertUser;
    }
    public void setInsertUser(Integer insertUser) {
        this.insertUser = insertUser;
    }
    public Timestamp getInsertDatetime() {
        return insertDatetime;
    }
    public void setInsertDatetime(Timestamp insertDatetime) {
        this.insertDatetime = insertDatetime;
    }
    public Integer getUpdateUser() {
        return updateUser;
    }
    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
    }
    public Timestamp getUpdateDatetime() {
        return updateDatetime;
    }
    public void setUpdateDatetime(Timestamp updateDatetime) {
        this.updateDatetime = updateDatetime;
    }
}
