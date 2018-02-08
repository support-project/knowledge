package org.support.project.knowledge.deploy.v1_8_0;

import org.support.project.knowledge.deploy.Migrate;
import org.support.project.ormapping.tool.dao.InitializeDao;

public class Migrate_1_8_3 implements Migrate {

    public static Migrate_1_8_3 get() {
        return org.support.project.di.Container.getComp(Migrate_1_8_3.class);
    }

    @Override
    public boolean doMigrate() throws Exception {
        InitializeDao initializeDao = InitializeDao.get();
        String[] sqlpaths = {
            "/org/support/project/knowledge/deploy/v1_8_0/migrate4.sql",
        };
        initializeDao.initializeDatabase(sqlpaths);
        return true;
    }
}