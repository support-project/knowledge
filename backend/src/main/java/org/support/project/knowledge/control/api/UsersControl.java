package org.support.project.knowledge.control.api;

import org.support.project.common.util.PropertyUtil;
import org.support.project.common.util.StringUtils;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.dao.ExUsersDao;
import org.support.project.knowledge.vo.AccountInfo;
import org.support.project.knowledge.vo.api.User;
import org.support.project.knowledge.vo.api.UserDetail;
import org.support.project.web.bean.ApiParams;
import org.support.project.web.boundary.Boundary;
import org.support.project.web.common.HttpStatus;
import org.support.project.web.control.GetApiControl;
import org.support.project.web.control.service.Get;
import org.support.project.web.dao.UsersDao;
import org.support.project.web.entity.UsersEntity;

import java.util.ArrayList;
import java.util.List;

@DI(instance = Instance.Prototype)
public class UsersControl extends GetApiControl {
    /**
     * List users
     */
    @Get(path="api/users", publishToken="")
    public Boundary index() {
        return get();
    }

    @Override
    public Boundary getList(ApiParams params) {
        List<User> results = new ArrayList<>();
        List<UsersEntity> users = UsersDao.get().selectAllWidthPager(params.getLimit(), params.getOffset());
        for (UsersEntity usersEntity : users) {
            User user = new User();
            PropertyUtil.copyPropertyValue(usersEntity, user);
            results.add(user);
        }
        return send(HttpStatus.SC_200_OK, results);
    }

    @Override
    public Boundary getSingle(String id) {
        if (!StringUtils.isInteger(id)) {
            return sendError(HttpStatus.SC_400_BAD_REQUEST);
        }
        int userId = Integer.parseInt(id);
        AccountInfo entity = ExUsersDao.get().selectAccountInfoOnKey(userId);
        if (entity == null) {
            return sendError(HttpStatus.SC_404_NOT_FOUND);
        }
        UserDetail user = new UserDetail();
        PropertyUtil.copyPropertyValue(entity, user);
        return send(HttpStatus.SC_200_OK, user);
    }
    
}
