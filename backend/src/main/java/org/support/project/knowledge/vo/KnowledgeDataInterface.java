package org.support.project.knowledge.vo;

public interface KnowledgeDataInterface extends KnowledgeKeyInterface {
    /** テンプレートの種類ID */
    public Integer getTypeId();
    /** タグ名称一覧 */
    public String getTagNames();
    /** 公開区分 */
    public Integer getPublicFlag();
    /** 登録ユーザ */
    public Integer getInsertUser();
    
}
