package org.support.project.knowledge.entity;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.entity.gen.GenKnowledgeEditUsersEntity;

/**
 * 編集可能なユーザ
 */
@DI(instance = Instance.Prototype)
public class KnowledgeEditUsersEntity extends GenKnowledgeEditUsersEntity {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * インスタンス取得 AOPに対応
     * 
     * @return インスタンス
     */
    public static KnowledgeEditUsersEntity get() {
        return Container.getComp(KnowledgeEditUsersEntity.class);
    }

    /**
     * コンストラクタ
     */
    public KnowledgeEditUsersEntity() {
        super();
    }

    /**
     * コンストラクタ
     * 
     * @param knowledgeId ナレッジID
     * @param userId USER_ID
     */

    public KnowledgeEditUsersEntity(Long knowledgeId, Integer userId) {
        super(knowledgeId, userId);
    }

}
