package org.support.project.knowledge.entity;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.entity.gen.GenKnowledgeEditGroupsEntity;

/**
 * 編集可能なグループ
 */
@DI(instance = Instance.Prototype)
public class KnowledgeEditGroupsEntity extends GenKnowledgeEditGroupsEntity {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * インスタンス取得 AOPに対応
     * 
     * @return インスタンス
     */
    public static KnowledgeEditGroupsEntity get() {
        return Container.getComp(KnowledgeEditGroupsEntity.class);
    }

    /**
     * コンストラクタ
     */
    public KnowledgeEditGroupsEntity() {
        super();
    }

    /**
     * コンストラクタ
     * 
     * @param groupId GROUP_ID
     * @param knowledgeId ナレッジID
     */

    public KnowledgeEditGroupsEntity(Integer groupId, Long knowledgeId) {
        super(groupId, knowledgeId);
    }

}
