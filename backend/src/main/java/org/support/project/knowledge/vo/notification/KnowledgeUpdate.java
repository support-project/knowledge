package org.support.project.knowledge.vo.notification;

public class KnowledgeUpdate {
    private long knowledgeId;
    private String knowledgeTitle;
    private String updateUser;
    /**
     * @return the knowledgeId
     */
    public long getKnowledgeId() {
        return knowledgeId;
    }
    /**
     * @param knowledgeId the knowledgeId to set
     */
    public void setKnowledgeId(long knowledgeId) {
        this.knowledgeId = knowledgeId;
    }
    /**
     * @return the knowledgeTitle
     */
    public String getKnowledgeTitle() {
        if (knowledgeTitle == null) return "";
        return knowledgeTitle;
    }
    /**
     * @param knowledgeTitle the knowledgeTitle to set
     */
    public void setKnowledgeTitle(String knowledgeTitle) {
        this.knowledgeTitle = knowledgeTitle;
    }
    /**
     * @return the updateUser
     */
    public String getUpdateUser() {
        if (updateUser == null) return "";
        return updateUser;
    }
    /**
     * @param updateUser the updateUser to set
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }
}
