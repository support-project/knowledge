package org.support.project.knowledge.vo;

public class UploadFile {

    private Long fileNo;
    private String url;
    private String thumbnailUrl;
    private String name;
    private String type;
    private Double size;
    private String deleteUrl;
    private String deleteType;
    private Long knowlegeId;
    private Long commentNo;

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the thumbnailUrl
     */
    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    /**
     * @param thumbnailUrl the thumbnailUrl to set
     */
    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the size
     */
    public Double getSize() {
        return size;
    }

    /**
     * @param size the size to set
     */
    public void setSize(Double size) {
        this.size = size;
    }

    /**
     * @return the deleteUrl
     */
    public String getDeleteUrl() {
        return deleteUrl;
    }

    /**
     * @param deleteUrl the deleteUrl to set
     */
    public void setDeleteUrl(String deleteUrl) {
        this.deleteUrl = deleteUrl;
    }

    /**
     * @return the deleteType
     */
    public String getDeleteType() {
        return deleteType;
    }

    /**
     * @param deleteType the deleteType to set
     */
    public void setDeleteType(String deleteType) {
        this.deleteType = deleteType;
    }

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
     * @return the knowlegeId
     */
    public Long getKnowlegeId() {
        return knowlegeId;
    }

    /**
     * @param knowlegeId the knowlegeId to set
     */
    public void setKnowlegeId(Long knowlegeId) {
        this.knowlegeId = knowlegeId;
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
