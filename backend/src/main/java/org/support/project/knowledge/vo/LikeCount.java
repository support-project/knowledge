package org.support.project.knowledge.vo;

public class LikeCount {
    private Long knowledgeId;
    private Long count;

    /**
     * @return the knowledgeId
     */
    public Long getKnowledgeId() {
        return knowledgeId;
    }

    /**
     * @param knowledgeId the knowledgeId to set
     */
    public void setKnowledgeId(Long knowledgeId) {
        this.knowledgeId = knowledgeId;
    }

    /**
     * @return the count
     */
    public Long getCount() {
        return count;
    }

    /**
     * @param count the count to set
     */
    public void setCount(Long count) {
        this.count = count;
    }
}
