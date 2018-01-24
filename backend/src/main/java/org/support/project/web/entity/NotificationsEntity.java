package org.support.project.web.entity;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.web.entity.gen.GenNotificationsEntity;


/**
 * 通知
 */
@DI(instance = Instance.Prototype)
public class NotificationsEntity extends GenNotificationsEntity {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;
    
    /** status */
    private int status;
    
    
    /**
     * Get instance from DI container.
     * @return instance
     */
    public static NotificationsEntity get() {
        return Container.getComp(NotificationsEntity.class);
    }

    /**
     * Constructor.
     */
    public NotificationsEntity() {
        super();
    }

    /**
     * Constructor
     * @param no NO
     */

    public NotificationsEntity(Long no) {
        super( no);
    }

    /**
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(int status) {
        this.status = status;
    }

}
