package org.support.project.knowledge.deploy.v1_10_0;

import java.util.List;

import org.support.project.common.config.INT_FLAG;
import org.support.project.common.util.Compare;
import org.support.project.knowledge.config.AppConfig;
import org.support.project.knowledge.deploy.Migrate;
import org.support.project.ormapping.tool.dao.InitializeDao;
import org.support.project.web.dao.LdapConfigsDao;
import org.support.project.web.dao.UserAliasDao;
import org.support.project.web.dao.UsersDao;
import org.support.project.web.entity.LdapConfigsEntity;
import org.support.project.web.entity.UserAliasEntity;
import org.support.project.web.entity.UsersEntity;

public class Migrate_1_10_0 implements Migrate {

    public static Migrate_1_10_0 get() {
        return org.support.project.di.Container.getComp(Migrate_1_10_0.class);
    }

    @Override
    public boolean doMigrate() throws Exception {
        InitializeDao initializeDao = InitializeDao.get();
        String[] sqlpaths = {
            "/org/support/project/knowledge/deploy/v1_10_0/migrate.sql",
        };
        initializeDao.initializeDatabase(sqlpaths);
        
        // 既にLdapを使っている人の情報をAliasに入れる
        LdapConfigsEntity entity = LdapConfigsDao.get().selectOnKey(AppConfig.get().getSystemName());
        if (entity != null) {
            // Descriptionに初期値をセット
            entity.setDescription("LDAP");
            LdapConfigsDao.get().save(entity);
            // あまりに多くのユーザだと問題だけど、ユーザなのでたぶん1000件以下なので一気にロード
            List<UsersEntity> users = UsersDao.get().selectAll();
            for (UsersEntity user : users) {
                if (Compare.equal(user.getAuthLdap(), INT_FLAG.ON.getValue())) {
                    UserAliasEntity alias = new UserAliasEntity(AppConfig.get().getSystemName(), user.getUserId());
                    alias.setAliasKey(user.getUserKey());
                    alias.setAliasMail(user.getMailAddress());
                    alias.setAliasName(user.getUserName());
                    alias.setUserInfoUpdate(INT_FLAG.ON.getValue());
                    UserAliasDao.get().save(alias);
                }
            }
        }
        return true;
    }
}