package org.support.project.knowledge.deploy.v0_5_3pre2;

import java.util.List;

import org.support.project.knowledge.deploy.Migrate;
import org.support.project.ormapping.tool.dao.InitializeDao;
import org.support.project.web.config.CommonWebParameter;
import org.support.project.web.dao.UserGroupsDao;
import org.support.project.web.dao.UsersDao;
import org.support.project.web.entity.UserGroupsEntity;
import org.support.project.web.entity.UsersEntity;

public class Migrate_0_5_3pre2 implements Migrate {

    public static Migrate_0_5_3pre2 get() {
        return org.support.project.di.Container.getComp(Migrate_0_5_3pre2.class);
    }

    @Override
    public boolean doMigrate() throws Exception {
        InitializeDao initializeDao = InitializeDao.get();
        String[] sqlpaths = { "/org/support/project/knowledge/deploy/v0_5_3pre2/migrate.sql" };
        initializeDao.initializeDatabase(sqlpaths);

        List<UsersEntity> users = UsersDao.get().selectAll();
        UserGroupsDao userGroupsDao = UserGroupsDao.get();
        for (UsersEntity usersEntity : users) {
            UserGroupsEntity userGroupsEntity = new UserGroupsEntity();
            userGroupsEntity.setUserId(usersEntity.getUserId());
            userGroupsEntity.setGroupId(0); // ALL
            userGroupsEntity.setGroupRole(CommonWebParameter.GROUP_ROLE_MEMBER);
            userGroupsDao.save(userGroupsEntity);
        }
        return true;
    }

}
