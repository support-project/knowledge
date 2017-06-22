package org.support.project.knowledge.control.api;

import java.util.ArrayList;
import java.util.List;

import org.support.project.common.util.PropertyUtil;
import org.support.project.common.util.StringUtils;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.control.Control;
import org.support.project.knowledge.dao.ExUsersDao;
import org.support.project.knowledge.vo.AccountInfo;
import org.support.project.knowledge.vo.api.User;
import org.support.project.web.bean.Msg;
import org.support.project.web.boundary.Boundary;
import org.support.project.web.common.HttpStatus;
import org.support.project.web.control.service.Get;
import org.support.project.web.dao.UsersDao;
import org.support.project.web.entity.UsersEntity;
import org.support.project.web.exception.InvalidParamException;

@DI(instance = Instance.Prototype)
public class UsersControl extends Control {

    
    /**
     * List users
     * GET /api.users
     * @throws InvalidParamException 
     */
    @Get(path="api/users")
    public Boundary index() {
        String userId = super.getPathString("");
        if (StringUtils.isEmpty(userId)) {
            // 一覧取得
            int limit = 10;
            int offset = 0;
            String limitStr = getParam("limit");
            if (StringUtils.isInteger(limitStr)) {
                limit = Integer.parseInt(limitStr);
            }
            String offsetStr = getParam("offset");
            if (StringUtils.isInteger(offsetStr)) {
                offset = Integer.parseInt(offsetStr);
            }
            List<User> results = new ArrayList<>();
            List<UsersEntity> users = UsersDao.get().selectAllWidthPager(limit, offset);
            for (UsersEntity usersEntity : users) {
                User user = new User();
                PropertyUtil.copyPropertyValue(usersEntity, user);
                results.add(user);
            }
            return send(HttpStatus.SC_200_OK, results);
        } else {
            // ユーザ取得
            if (!StringUtils.isInteger(userId)) {
                return send(HttpStatus.SC_400_BAD_REQUEST, new Msg("BAD_REQUEST"));
            }
            int id = Integer.parseInt(userId);
            AccountInfo entity = ExUsersDao.get().selectAccountInfoOnKey(id);
            if (entity == null) {
                return send(HttpStatus.SC_404_NOT_FOUND, new Msg("NOT_FOUND"));
            }
            User user = new User();
            PropertyUtil.copyPropertyValue(entity, user);
            return send(HttpStatus.SC_200_OK, user);
        }
    }
    
}
