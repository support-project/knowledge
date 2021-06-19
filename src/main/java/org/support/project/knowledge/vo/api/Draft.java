package org.support.project.knowledge.vo.api;

import java.sql.Timestamp;

public class Draft {
    /** 下書きID */
    private Long draftId;
    /** ナレッジID */
    private Long knowledgeId;
    /** タイトル */
    private String title;
    /** 内容 */
    private String content;
    /** 公開区分 */
    private Integer publicFlag;
    /** 公開対象 */
    private String accesses;
    /** 共同編集対象 */
    private String editors;
    /** タグ名称一覧 */
    private String tagNames;
    /** テンプレートの種類ID */
    private Integer typeId;
    /** 登録ユーザ */
    private Integer insertUser;
    /** 登録日時 */
    private Timestamp insertDatetime;
    /** 更新ユーザ */
    private Integer updateUser;
    /** 更新日時 */
    private Timestamp updateDatetime;
    /** 削除フラグ */
    private Integer deleteFlag;

    public Long getDraftId() {
        return draftId;
    }
    public void setDraftId(Long draftId) {
        this.draftId = draftId;
    }
    public Long getKnowledgeId() {
        return knowledgeId;
    }
    public void setKnowledgeId(Long knowledgeId) {
        this.knowledgeId = knowledgeId;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public Integer getPublicFlag() {
        return publicFlag;
    }
    public void setPublicFlag(Integer publicFlag) {
        this.publicFlag = publicFlag;
    }
    public String getAccesses() {
        return accesses;
    }
    public void setAccesses(String accesses) {
        this.accesses = accesses;
    }
    public String getEditors() {
        return editors;
    }
    public void setEditors(String editors) {
        this.editors = editors;
    }
    public String getTagNames() {
        return tagNames;
    }
    public void setTagNames(String tagNames) {
        this.tagNames = tagNames;
    }
    public Integer getTypeId() {
        return typeId;
    }
    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }
    public Integer getInsertUser() {
        return insertUser;
    }
    public void setInsertUser(Integer insertUser) {
        this.insertUser = insertUser;
    }
    public Timestamp getInsertDatetime() {
        return insertDatetime;
    }
    public void setInsertDatetime(Timestamp insertDatetime) {
        this.insertDatetime = insertDatetime;
    }
    public Integer getUpdateUser() {
        return updateUser;
    }
    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
    }
    public Timestamp getUpdateDatetime() {
        return updateDatetime;
    }
    public void setUpdateDatetime(Timestamp updateDatetime) {
        this.updateDatetime = updateDatetime;
    }
    public Integer getDeleteFlag() {
        return deleteFlag;
    }
    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }
}

