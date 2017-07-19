package org.support.project.knowledge.control.protect;

import java.util.List;

import org.support.project.knowledge.control.Control;
import org.support.project.knowledge.logic.NotificationLogic;
import org.support.project.web.boundary.Boundary;
import org.support.project.web.control.service.Get;
import org.support.project.web.entity.NotificationsEntity;
import org.support.project.web.exception.InvalidParamException;

public class NotificationControl extends Control {

    @Get
    public Boundary list() throws InvalidParamException {
        int offset = getPathInteger(0);
        List<NotificationsEntity> notifications = NotificationLogic.get().getNotification(getLoginUserId(), offset);
        
        for (NotificationsEntity notificationsEntity : notifications) {
            NotificationLogic.get().convNotification(notificationsEntity, getLoginedUser(), NotificationLogic.TARGET.list);
        }
        
        setAttribute("notifications", notifications);
        
        int previous = offset - 1;
        if (previous < 0) {
            previous = 0;
        }
        setAttribute("offset", offset);
        setAttribute("previous", previous);
        setAttribute("next", offset + 1);
        
        return forward("list.jsp");
    }

    
    
}
