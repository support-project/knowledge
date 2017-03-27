package org.support.project.knowledge.deploy.v0_0_1;

import org.support.project.common.util.FileUtil;
import org.support.project.common.util.StringUtils;
import org.support.project.common.util.SystemUtils;
import org.support.project.knowledge.config.AppConfig;
import org.support.project.knowledge.dao.ServiceConfigsDao;
import org.support.project.knowledge.dao.ServiceLocaleConfigsDao;
import org.support.project.knowledge.dao.gen.DatabaseControlDao;
import org.support.project.knowledge.deploy.Migrate;
import org.support.project.knowledge.entity.ServiceConfigsEntity;
import org.support.project.knowledge.entity.ServiceLocaleConfigsEntity;
import org.support.project.knowledge.logic.MailLogic;
import org.support.project.ormapping.tool.dao.InitializeDao;
import org.support.project.web.config.WebConfig;
import org.support.project.web.dao.RolesDao;
import org.support.project.web.dao.UserRolesDao;
import org.support.project.web.dao.UsersDao;
import org.support.project.web.entity.RolesEntity;
import org.support.project.web.entity.UserRolesEntity;
import org.support.project.web.entity.UsersEntity;

public class InitializeSystem implements Migrate {
    private String adminKey = "admin";
    
    public static InitializeSystem get() {
        return org.support.project.di.Container.getComp(InitializeSystem.class);
    }

    @Override
    public boolean doMigrate() throws Exception {
        createTables();
        addInitDatas();
        return true;
    }

    private void addInitDatas() throws Exception {
        // 権限の追加
        RolesEntity adminRole = RolesEntity.get();
        adminRole.setRoleId(1);
        adminRole.setRoleKey(WebConfig.ROLE_ADMIN);
        adminRole.setRoleName("Administrator");
        RolesDao.get().insert(adminRole);

        RolesEntity userRole = RolesEntity.get();
        userRole.setRoleId(2);
        userRole.setRoleKey(WebConfig.ROLE_USER);
        userRole.setRoleName("User");
        RolesDao.get().insert(userRole);

        // 管理者ユーザ追加
        UsersDao usersDao = UsersDao.get();
        UsersEntity usersEntity = UsersEntity.get();
        usersEntity.setUserId(1);
        String admin = SystemUtils.getenv("KNOWLEDGE_ADMIN_KEY");
        if (!StringUtils.isEmpty(admin)) {
            adminKey = admin;
        }
        usersEntity.setUserKey(adminKey);
        String password = SystemUtils.getenv("KNOWLEDGE_ADMIN_PASSWORD");
        if (StringUtils.isEmpty(password)) {
            password = "admin123";
        }
        usersEntity.setPassword(password);
        usersEntity.setUserName("Administrator");
        usersDao.save(usersEntity);

        // 管理者ユーザと管理者権を紐付け
        UserRolesDao userRolesDao = UserRolesDao.get();
        UserRolesEntity userRolesEntity = UserRolesEntity.get();
        userRolesEntity.setUserId(1);
        userRolesEntity.setRoleId(1);
        userRolesDao.save(userRolesEntity);
        
        ServiceConfigsEntity serviceConfigsEntity = new ServiceConfigsEntity(AppConfig.get().getSystemName());
        serviceConfigsEntity.setServiceLabel(AppConfig.get().getSystemName());
        serviceConfigsEntity.setServiceIcon("fa-book");
        ServiceConfigsDao.get().insert(serviceConfigsEntity);
        
        ServiceLocaleConfigsEntity en = new ServiceLocaleConfigsEntity("en", AppConfig.get().getSystemName());
        en.setPageHtml(FileUtil.read(getClass().getResourceAsStream("/org/support/project/knowledge/deploy/v1_8_0/top_info.html")));
        ServiceLocaleConfigsDao.get().insert(en);
        
        ServiceLocaleConfigsEntity ja = new ServiceLocaleConfigsEntity("ja", AppConfig.get().getSystemName());
        ja.setPageHtml(FileUtil.read(getClass().getResourceAsStream("/org/support/project/knowledge/deploy/v1_8_0/top_info_ja.html")));
        ServiceLocaleConfigsDao.get().insert(ja);
        
        // メールのテンプレートを初期化
        MailLogic.get().initMailTemplate();
    }


    private void createTables() {
        DatabaseControlDao dao1 = new DatabaseControlDao();
        dao1.dropAllTable();
        org.support.project.web.dao.gen.DatabaseControlDao dao2 = new org.support.project.web.dao.gen.DatabaseControlDao();
        dao2.dropAllTable();

        // 存在するテーブルを全て削除
        InitializeDao initializeDao = InitializeDao.get();

        String[] sqlpaths = {
            "/org/support/project/web/database/ddl.sql",
            "/org/support/project/knowledge/database/ddl.sql",
            "/org/support/project/knowledge/database/init_datas.sql"
        };
        initializeDao.initializeDatabase(sqlpaths);
    }
    
    
}
