package org.support.project.knowledge.entity;

import org.support.project.knowledge.entity.gen.GenPointKnowledgeHistoriesEntity;

import java.util.List;
import java.util.Map;

import org.support.project.common.bean.ValidateError;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;

import java.sql.Timestamp;


/**
 * ナレッジのポイント獲得履歴
 */
@DI(instance = Instance.Prototype)
public class PointKnowledgeHistoriesEntity extends GenPointKnowledgeHistoriesEntity {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * Get instance from DI container.
     * @return instance
     */
    public static PointKnowledgeHistoriesEntity get() {
        return Container.getComp(PointKnowledgeHistoriesEntity.class);
    }

    /**
     * Constructor.
     */
    public PointKnowledgeHistoriesEntity() {
        super();
    }

    /**
     * Constructor
     * @param historyNo 履歴番号
     * @param knowledgeId ナレッジID
     */

    public PointKnowledgeHistoriesEntity(Long historyNo, Long knowledgeId) {
        super( historyNo,  knowledgeId);
    }

}
