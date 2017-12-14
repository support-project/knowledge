package org.support.project.knowledge.entity;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.entity.gen.GenKnowledgeUsersEntity;

/**
 * アクセス可能なユーザ
 */
@DI(instance = Instance.Prototype)
public class KnowledgeUsersEntity extends GenKnowledgeUsersEntity {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * インスタンス取得 AOPに対応
     * 
     * @return インスタンス
     */
    public static KnowledgeUsersEntity get() {
        return Container.getComp(KnowledgeUsersEntity.class);
    }

    /**
     * コンストラクタ
     */
    public KnowledgeUsersEntity() {
        super();
    }

    /**
     * コンストラクタ
     * 
     * @param knowledgeId ナレッジID
     * @param userId USER_ID
     */

    public KnowledgeUsersEntity(Long knowledgeId, Integer userId) {
        super(knowledgeId, userId);
    }

}
