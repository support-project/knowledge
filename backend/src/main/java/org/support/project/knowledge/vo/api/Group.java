package org.support.project.knowledge.vo.api;

import java.sql.Timestamp;

public class Group {
    /** グループID */
    private Integer groupId;
    /** グループ名称 */
    private String groupName;
    /** 説明 */
    private String description;
    /** グループの区分 */
    private Integer groupClass;
    /** 登録ユーザ */
    private Integer insertUser;
    /** 登録日時 */
    private Timestamp insertDatetime;
    /** 更新ユーザ */
    private Integer updateUser;
    /** 更新日時 */
    private Timestamp updateDatetime;
    
    /** グループのナレッジ数 */
    private int groupKnowledgeCount = 0;
    
    
    /**
     * @return the groupId
     */
    public Integer getGroupId() {
        return groupId;
    }
    /**
     * @param groupId the groupId to set
     */
    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }
    /**
     * @return the groupName
     */
    public String getGroupName() {
        return groupName;
    }
    /**
     * @param groupName the groupName to set
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }
    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
    /**
     * @return the groupClass
     */
    public Integer getGroupClass() {
        return groupClass;
    }
    /**
     * @param groupClass the groupClass to set
     */
    public void setGroupClass(Integer groupClass) {
        this.groupClass = groupClass;
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
     * @return the groupKnowledgeCount
     */
    public int getGroupKnowledgeCount() {
        return groupKnowledgeCount;
    }
    /**
     * @param groupKnowledgeCount the groupKnowledgeCount to set
     */
    public void setGroupKnowledgeCount(int groupKnowledgeCount) {
        this.groupKnowledgeCount = groupKnowledgeCount;
    }

}
