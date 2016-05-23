package org.support.project.knowledge.entity;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.entity.gen.GenKnowledgeGroupsEntity;

/**
 * アクセス可能なグループ
 */
@DI(instance = Instance.Prototype)
public class KnowledgeGroupsEntity extends GenKnowledgeGroupsEntity {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * インスタンス取得 AOPに対応
     * 
     * @return インスタンス
     */
    public static KnowledgeGroupsEntity get() {
        return Container.getComp(KnowledgeGroupsEntity.class);
    }

    /**
     * コンストラクタ
     */
    public KnowledgeGroupsEntity() {
        super();
    }

    /**
     * コンストラクタ
     * 
     * @param groupId GROUP_ID
     * @param knowledgeId ナレッジID
     */

    public KnowledgeGroupsEntity(Integer groupId, Long knowledgeId) {
        super(groupId, knowledgeId);
    }

}
