package org.support.project.knowledge.vo.notification;

public class LikeInsert extends KnowledgeUpdate {
    private String likeInsertUser;
    
    /** コメントにイイネが投稿された場合は、コメントのNoを保持 */
    private Long commentNo;

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

    /**
     * @return the commentNo
     */
    public Long getCommentNo() {
        return commentNo;
    }

    /**
     * @param commentNo the commentNo to set
     */
    public void setCommentNo(Long commentNo) {
        this.commentNo = commentNo;
    }

}
