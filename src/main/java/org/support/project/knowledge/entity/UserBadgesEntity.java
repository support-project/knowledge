package org.support.project.knowledge.entity;

import org.support.project.knowledge.entity.gen.GenUserBadgesEntity;

import java.util.List;
import java.util.Map;

import org.support.project.common.bean.ValidateError;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;

import java.sql.Timestamp;


/**
 * ユーザの称号
 */
@DI(instance = Instance.Prototype)
public class UserBadgesEntity extends GenUserBadgesEntity {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * Get instance from DI container.
     * @return instance
     */
    public static UserBadgesEntity get() {
        return Container.getComp(UserBadgesEntity.class);
    }

    /**
     * Constructor.
     */
    public UserBadgesEntity() {
        super();
    }

    /**
     * Constructor
     * @param no 番号
     * @param userId ユーザID
     */

    public UserBadgesEntity(Integer no, Integer userId) {
        super( no,  userId);
    }

}
