package org.support.project.knowledge.vo.api;

import java.sql.Timestamp;

public class AttachedFile {

    /** 添付ファイル番号 */
    private Long fileNo;
    /** ナレッジID */
    private Long knowledgeId;
    /** コメント番号 */
    private Long commentNo;
    /** ファイル名 */
    private String fileName;
    /** ファイルサイズ */
    private Double fileSize;
    /** 登録ユーザ */
    private Integer insertUser;
    /** 登録日時 */
    private Timestamp insertDatetime;
    /** 更新ユーザ */
    private Integer updateUser;
    /** 更新日時 */
    private Timestamp updateDatetime;
    /**
     * @return the fileNo
     */
    public Long getFileNo() {
        return fileNo;
    }
    /**
     * @param fileNo the fileNo to set
     */
    public void setFileNo(Long fileNo) {
        this.fileNo = fileNo;
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
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }
    /**
     * @param fileName the fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    /**
     * @return the fileSize
     */
    public Double getFileSize() {
        return fileSize;
    }
    /**
     * @param fileSize the fileSize to set
     */
    public void setFileSize(Double fileSize) {
        this.fileSize = fileSize;
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
    
    
    
    
}
