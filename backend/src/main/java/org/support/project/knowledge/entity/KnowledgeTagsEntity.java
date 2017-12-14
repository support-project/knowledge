package org.support.project.knowledge.entity;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.entity.gen.GenKnowledgeTagsEntity;

/**
 * ナレッジが持つタグ
 */
@DI(instance = Instance.Prototype)
public class KnowledgeTagsEntity extends GenKnowledgeTagsEntity {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * インスタンス取得 AOPに対応
     * 
     * @return インスタンス
     */
    public static KnowledgeTagsEntity get() {
        return Container.getComp(KnowledgeTagsEntity.class);
    }

    /**
     * コンストラクタ
     */
    public KnowledgeTagsEntity() {
        super();
    }

    /**
     * コンストラクタ
     * 
     * @param knowledgeId ナレッジID
     * @param tagId タグ_ID
     */

    public KnowledgeTagsEntity(Long knowledgeId, Integer tagId) {
        super(knowledgeId, tagId);
    }

}
