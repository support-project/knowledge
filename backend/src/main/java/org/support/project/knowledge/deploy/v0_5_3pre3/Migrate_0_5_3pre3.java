package org.support.project.knowledge.deploy.v0_5_3pre3;

import java.util.List;

import org.support.project.common.util.StringUtils;
import org.support.project.knowledge.deploy.Migrate;
import org.support.project.web.dao.UsersDao;
import org.support.project.web.entity.UsersEntity;

public class Migrate_0_5_3pre3 implements Migrate {

    public static Migrate_0_5_3pre3 get() {
        return org.support.project.di.Container.getComp(Migrate_0_5_3pre3.class);
    }

    @Override
    public boolean doMigrate() throws Exception {
        List<UsersEntity> users = UsersDao.get().selectAll();
        for (UsersEntity usersEntity : users) {
            if (StringUtils.isEmailAddress(usersEntity.getUserKey())) {
                usersEntity.setMailAddress(usersEntity.getUserKey());
                usersEntity.setEncrypted(true);
                UsersDao.get().save(usersEntity);
            }
        }
        return true;
    }

}
