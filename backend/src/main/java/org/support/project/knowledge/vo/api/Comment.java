package org.support.project.knowledge.vo.api;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Comment {
    /** コメント番号 */
    private Long commentNo;
    /** ナレッジID */
    private Long knowledgeId;
    /** コメント */
    private String comment;
    /** ステータス */
    private Integer commentStatus;
    /** 登録ユーザ */
    private Integer insertUser;
    /** 登録日時 */
    private Timestamp insertDatetime;
    /** 更新ユーザ */
    private Integer updateUser;
    /** 更新日時 */
    private Timestamp updateDatetime;
    
    /** 添付ファイル */
    private List<AttachedFile> attachments = new ArrayList<>();
    
    /** いいねの件数 */
    private long likeCount = 0;
    /** 登録ユーザ名 */
    private String insertUserName;
    /** 更新ユーザ名 */
    private String updateUserName;

    
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
     * @return the comment
     */
    public String getComment() {
        return comment;
    }
    /**
     * @param comment the comment to set
     */
    public void setComment(String comment) {
        this.comment = comment;
    }
    /**
     * @return the commentStatus
     */
    public Integer getCommentStatus() {
        return commentStatus;
    }
    /**
     * @param commentStatus the commentStatus to set
     */
    public void setCommentStatus(Integer commentStatus) {
        this.commentStatus = commentStatus;
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
    public List<AttachedFile> getAttachments() {
        return attachments;
    }
    public void setAttachments(List<AttachedFile> attachments) {
        this.attachments = attachments;
    }
    public long getLikeCount() {
        return likeCount;
    }
    public void setLikeCount(long likeCount) {
        this.likeCount = likeCount;
    }
    public String getInsertUserName() {
        return insertUserName;
    }
    public void setInsertUserName(String insertUserName) {
        this.insertUserName = insertUserName;
    }
    public String getUpdateUserName() {
        return updateUserName;
    }
    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName;
    }

    
}
