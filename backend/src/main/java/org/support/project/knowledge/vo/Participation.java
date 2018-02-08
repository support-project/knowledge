package org.support.project.knowledge.vo;

import org.support.project.web.entity.UsersEntity;

public class Participation extends UsersEntity {
    /** シリアルバージョン */
    private static final long serialVersionUID = 1L;
    /** 参加するイベントのID */
    private Long knowledgeId;
    /** 参加ステータス */
    private Integer status;
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
     * @return the status
     */
    public Integer getStatus() {
        return status;
    }
    /**
     * @param status the status to set
     */
    public void setStatus(Integer status) {
        this.status = status;
    }
    
}
