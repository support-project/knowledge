package org.support.project.knowledge.deploy.v1_8_0;

import org.support.project.knowledge.deploy.Migrate;
import org.support.project.knowledge.logic.MailLogic;
import org.support.project.ormapping.tool.dao.InitializeDao;

public class Migrate_1_8_4 implements Migrate {

    public static Migrate_1_8_4 get() {
        return org.support.project.di.Container.getComp(Migrate_1_8_4.class);
    }

    @Override
    public boolean doMigrate() throws Exception {
        InitializeDao initializeDao = InitializeDao.get();
        String[] sqlpaths = {
            "/org/support/project/knowledge/deploy/v1_8_0/migrate5.sql",
        };
        initializeDao.initializeDatabase(sqlpaths);
        
        // メールのテンプレートを初期化
        MailLogic.get().initMailTemplate();
        return true;
    }
}