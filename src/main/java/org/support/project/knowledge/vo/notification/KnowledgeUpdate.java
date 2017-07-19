package org.support.project.knowledge.vo.notification;

public class KnowledgeUpdate {
    private long KnowledgeId;
    private String KnowledgeTitle;
    private String updateUser;
    /**
     * @return the knowledgeId
     */
    public long getKnowledgeId() {
        return KnowledgeId;
    }
    /**
     * @param knowledgeId the knowledgeId to set
     */
    public void setKnowledgeId(long knowledgeId) {
        KnowledgeId = knowledgeId;
    }
    /**
     * @return the knowledgeTitle
     */
    public String getKnowledgeTitle() {
        return KnowledgeTitle;
    }
    /**
     * @param knowledgeTitle the knowledgeTitle to set
     */
    public void setKnowledgeTitle(String knowledgeTitle) {
        KnowledgeTitle = knowledgeTitle;
    }
    /**
     * @return the updateUser
     */
    public String getUpdateUser() {
        return updateUser;
    }
    /**
     * @param updateUser the updateUser to set
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }
}
