package org.support.project.knowledge.entity;

import org.support.project.knowledge.entity.gen.GenNotificationStatusEntity;

import java.util.List;
import java.util.Map;

import org.support.project.common.bean.ValidateError;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;

import java.sql.Timestamp;


/**
 * いいねの通知状態
 */
@DI(instance = Instance.Prototype)
public class NotificationStatusEntity extends GenNotificationStatusEntity {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * Get instance from DI container.
     * @return instance
     */
    public static NotificationStatusEntity get() {
        return Container.getComp(NotificationStatusEntity.class);
    }

    /**
     * Constructor.
     */
    public NotificationStatusEntity() {
        super();
    }

    /**
     * Constructor
     * @param id ターゲットのID
     * @param type 種類
     * @param user 登録者
     */

    public NotificationStatusEntity(Long id, Integer type, Integer user) {
        super( id,  type,  user);
    }

}
