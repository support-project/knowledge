package org.support.project.knowledge.control.open;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.support.project.common.util.StringUtils;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.control.Control;
import org.support.project.knowledge.entity.EventsEntity;
import org.support.project.knowledge.logic.EventsLogic;
import org.support.project.knowledge.logic.TimeZoneLogic;
import org.support.project.web.boundary.Boundary;
import org.support.project.web.common.HttpStatus;
import org.support.project.web.control.service.Get;
import org.support.project.web.exception.InvalidParamException;

@DI(instance = Instance.Prototype)
public class EventControl extends Control {
    @Get
    public Boundary list() throws InvalidParamException, ParseException {
        String date = getParam("date");
        String timezone = getParam("timezone");
        if (StringUtils.isEmpty(date) || StringUtils.isEmpty(timezone)) {
            return sendError(HttpStatus.SC_400_BAD_REQUEST, "BAD REQUEST");
        }
        DateFormat monthformat = new SimpleDateFormat("YYYYMM");
        try {
            monthformat.parse(date);
        } catch (ParseException e) {
            return sendError(HttpStatus.SC_400_BAD_REQUEST, "BAD REQUEST");
        }
        if (!TimeZoneLogic.get().exist(timezone)) {
            return sendError(HttpStatus.SC_400_BAD_REQUEST, "BAD REQUEST");
        }
        List<EventsEntity> events = EventsLogic.get().eventList(date, timezone, getLoginedUser());
        return send(events);
    }
    
    
}
