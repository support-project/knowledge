package org.support.project.web.entity;

import org.support.project.web.entity.gen.GenUserNotificationsEntity;

import java.util.List;
import java.util.Map;

import org.support.project.common.bean.ValidateError;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;

import java.sql.Timestamp;


/**
 * ユーザへの通知
 */
@DI(instance = Instance.Prototype)
public class UserNotificationsEntity extends GenUserNotificationsEntity {
    
    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * Get instance from DI container.
     * @return instance
     */
    public static UserNotificationsEntity get() {
        return Container.getComp(UserNotificationsEntity.class);
    }

    /**
     * Constructor.
     */
    public UserNotificationsEntity() {
        super();
    }

    /**
     * Constructor
     * @param no NO
     * @param userId ユーザID
     */

    public UserNotificationsEntity(Long no, Integer userId) {
        super( no,  userId);
    }

}
