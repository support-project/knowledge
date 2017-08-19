package org.support.project.knowledge.control.protect;

import java.util.List;

import org.support.project.knowledge.control.Control;
import org.support.project.knowledge.logic.NotificationLogic;
import org.support.project.web.boundary.Boundary;
import org.support.project.web.common.HttpStatus;
import org.support.project.web.control.service.Get;
import org.support.project.web.entity.NotificationsEntity;
import org.support.project.web.exception.InvalidParamException;

public class NotificationControl extends Control {

    @Get
    public Boundary list() throws InvalidParamException {
        int offset = getPathInteger(0);
        boolean all = "true".equals(getAttribute("all", "false"));
        List<NotificationsEntity> notifications = NotificationLogic.get().getNotification(getLoginedUser(), offset, all);
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

    @Get
    public Boundary view() throws InvalidParamException {
        long no = getPathLong(new Long(-1));
        NotificationsEntity notification = NotificationLogic.get().load(no, getLoginedUser());
        if (notification == null) {
            sendError(HttpStatus.SC_403_FORBIDDEN, "FORBIDDEN");
        }
        NotificationLogic.get().setStatus(getLoginUserId(), no, NotificationLogic.STATUS_READED);
        setAttributeOnProperty(notification);
        return forward("view.jsp");
    }
    
    
}
