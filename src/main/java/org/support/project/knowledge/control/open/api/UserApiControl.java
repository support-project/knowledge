package org.support.project.knowledge.control.open.api;

import java.util.List;

import org.support.project.web.boundary.JsonBoundary;
import org.support.project.web.control.ApiControl;
import org.support.project.web.control.service.Get;
import org.support.project.web.entity.UsersEntity;
import org.support.project.web.logic.UserLogic;

public class UserApiControl extends ApiControl {
    private static final int MAX_LIMIT = 50;
    private static final int DEFAULT_LIMIT = 20;
    
    @Get(path="open.api/users", publishToken="")
    public JsonBoundary index() {
        String keyword = getParam("keyword");
        int offset = getParamInt("offset", 0, Integer.MAX_VALUE);
        int limit = getParamInt("limit", DEFAULT_LIMIT, MAX_LIMIT);
        List<UsersEntity> list = UserLogic.get().getUser(keyword, offset, limit);
        return send(list);
        
    }

}
