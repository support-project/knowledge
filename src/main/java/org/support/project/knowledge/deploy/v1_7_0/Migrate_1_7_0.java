package org.support.project.knowledge.deploy.v1_7_0;

import org.support.project.knowledge.deploy.Migrate;
import org.support.project.ormapping.tool.dao.InitializeDao;

public class Migrate_1_7_0 implements Migrate {

    public static Migrate_1_7_0 get() {
        return org.support.project.di.Container.getComp(Migrate_1_7_0.class);
    }

    @Override
    public boolean doMigrate() throws Exception {
        InitializeDao initializeDao = InitializeDao.get();
        String[] sqlpaths = {
            "/org/support/project/knowledge/deploy/v1_7_0/migrate.sql",
        };
        initializeDao.initializeDatabase(sqlpaths);
        return true;
    }
}