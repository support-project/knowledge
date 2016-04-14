package org.support.project.knowledge.entity;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.entity.gen.GenTagsEntity;

/**
 * タグ
 */
@DI(instance = Instance.Prototype)
public class TagsEntity extends GenTagsEntity {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * タグに紐づくナレッジの件数
     */
    private Integer knowledgeCount;

    /**
     * インスタンス取得 AOPに対応
     * 
     * @return インスタンス
     */
    public static TagsEntity get() {
        return Container.getComp(TagsEntity.class);
    }

    /**
     * コンストラクタ
     */
    public TagsEntity() {
        super();
    }

    /**
     * コンストラクタ
     * 
     * @param tagId タグ_ID
     */

    public TagsEntity(Integer tagId) {
        super(tagId);
    }

    /**
     * @return the knowledgeCount
     */
    public Integer getKnowledgeCount() {
        return knowledgeCount;
    }

    /**
     * @param knowledgeCount the knowledgeCount to set
     */
    public void setKnowledgeCount(Integer knowledgeCount) {
        this.knowledgeCount = knowledgeCount;
    }

}
