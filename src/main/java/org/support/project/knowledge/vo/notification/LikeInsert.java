package org.support.project.knowledge.vo.notification;

public class LikeInsert extends KnowledgeUpdate {
    private String likeInsertUser;

    /**
     * @return the likeInsertUser
     */
    public String getLikeInsertUser() {
        if (likeInsertUser == null) return "";
        return likeInsertUser;
    }

    /**
     * @param likeInsertUser the likeInsertUser to set
     */
    public void setLikeInsertUser(String likeInsertUser) {
        this.likeInsertUser = likeInsertUser;
    }

}
