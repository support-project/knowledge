package org.support.project.knowledge.entity;

import org.support.project.knowledge.entity.gen.GenPointUserHistoriesEntity;

import java.util.List;
import java.util.Map;

import org.support.project.common.bean.ValidateError;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;

import java.sql.Timestamp;


/**
 * ユーザのポイント獲得履歴
 */
@DI(instance = Instance.Prototype)
public class PointUserHistoriesEntity extends GenPointUserHistoriesEntity {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * Get instance from DI container.
     * @return instance
     */
    public static PointUserHistoriesEntity get() {
        return Container.getComp(PointUserHistoriesEntity.class);
    }

    /**
     * Constructor.
     */
    public PointUserHistoriesEntity() {
        super();
    }

    /**
     * Constructor
     * @param historyNo 履歴番号
     * @param userId ユーザID
     */

    public PointUserHistoriesEntity(Long historyNo, Integer userId) {
        super( historyNo,  userId);
    }

}
