package org.support.project.knowledge.logic.notification;

public abstract class AbstractQueueNotification extends AbstractNotification implements QueueNotification {
    /** notify type */
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

}
