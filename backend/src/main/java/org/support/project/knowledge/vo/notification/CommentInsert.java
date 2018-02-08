package org.support.project.knowledge.vo.notification;

public class CommentInsert extends KnowledgeUpdate {
    
    private String commentInsertUser;
    private String commentContents;
    
    /**
     * @return the commentInsertUser
     */
    public String getCommentInsertUser() {
        if (commentInsertUser == null) return "";
        return commentInsertUser;
    }
    /**
     * @param commentInsertUser the commentInsertUser to set
     */
    public void setCommentInsertUser(String commentInsertUser) {
        this.commentInsertUser = commentInsertUser;
    }
    /**
     * @return the commentContents
     */
    public String getCommentContents() {
        if (commentContents == null) return "";
        return commentContents;
    }
    /**
     * @param commentContents the commentContents to set
     */
    public void setCommentContents(String commentContents) {
        this.commentContents = commentContents;
    }

}
