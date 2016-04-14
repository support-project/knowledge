package org.support.project.knowledge.entity;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.entity.gen.GenKnowledgeHistoriesEntity;

/**
 * ナレッジ更新履歴
 */
@DI(instance = Instance.Prototype)
public class KnowledgeHistoriesEntity extends GenKnowledgeHistoriesEntity {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    private String userName;

    /**
     * インスタンス取得 AOPに対応
     * 
     * @return インスタンス
     */
    public static KnowledgeHistoriesEntity get() {
        return Container.getComp(KnowledgeHistoriesEntity.class);
    }

    /**
     * コンストラクタ
     */
    public KnowledgeHistoriesEntity() {
        super();
    }

    /**
     * コンストラクタ
     * 
     * @param historyNo 履歴番号
     * @param knowledgeId ナレッジID
     */

    public KnowledgeHistoriesEntity(Integer historyNo, Long knowledgeId) {
        super(historyNo, knowledgeId);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
