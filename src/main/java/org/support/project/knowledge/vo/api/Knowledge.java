package org.support.project.knowledge.vo.api;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Knowledge {
    /** ナレッジID */
    private Long knowledgeId;
    /** タイトル */
    private String title;
    /** 内容 */
    private String content;
    /** 公開区分 */
    private Integer publicFlag;
    /** いいね件数 */
    private Long likeCount;
    /** コメント件数 */
    private Integer commentCount;
    /** 登録ユーザ */
    private Integer insertUser;
    /** 登録日時 */
    private Timestamp insertDatetime;
    /** 更新ユーザ */
    private Integer updateUser;
    /** 更新日時 */
    private Timestamp updateDatetime;
    
    /** タグ */
    private List<String> tags = new ArrayList<>();
    
    /** テンプレート名 */
    private String template;
    
    /** 閲覧可能な対象（publicflag=2(保護)の場合に指定） */
    private Target viewers;
    
    
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
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return the publicFlag
     */
    public Integer getPublicFlag() {
        return publicFlag;
    }

    /**
     * @param publicFlag the publicFlag to set
     */
    public void setPublicFlag(Integer publicFlag) {
        this.publicFlag = publicFlag;
    }

    /**
     * @return the likeCount
     */
    public Long getLikeCount() {
        return likeCount;
    }

    /**
     * @param likeCount the likeCount to set
     */
    public void setLikeCount(Long likeCount) {
        this.likeCount = likeCount;
    }

    /**
     * @return the commentCount
     */
    public Integer getCommentCount() {
        return commentCount;
    }

    /**
     * @param commentCount the commentCount to set
     */
    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
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

    /**
     * @return the tags
     */
    public List<String> getTags() {
        return tags;
    }

    /**
     * @param tags the tags to set
     */
    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    /**
     * @return the template
     */
    public String getTemplate() {
        return template;
    }

    /**
     * @param template the template to set
     */
    public void setTemplate(String template) {
        this.template = template;
    }

    /**
     * @return the viewers
     */
    public Target getViewers() {
        return viewers;
    }

    /**
     * @param viewers the viewers to set
     */
    public void setViewers(Target viewers) {
        this.viewers = viewers;
    }

    
    
    
    
    
    
    
}
