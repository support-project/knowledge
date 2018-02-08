package org.support.project.knowledge.websocket;

import java.util.Observable;

import org.support.project.common.exception.ArgumentException;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.logic.notification.DesktopNotification;

@DI(instance = Instance.Singleton)
public class NotifyAction extends Observable {

    /*
     * (non-Javadoc)
     * 
     * @see java.util.Observable#notifyObservers(java.lang.Object)
     */
    @Override
    public void notifyObservers(Object arg) {
        if (!(arg instanceof DesktopNotification)) {
            if (arg == null) {
                throw new ArgumentException("notify is invalid. only Notify.class. notify is null");
            } else {
                throw new ArgumentException("notify is invalid. only Notify.class. notify is " + arg.getClass().getName());
            }
        }
        setChanged();
        super.notifyObservers(arg);
    }

}
