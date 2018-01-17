package org.support.project.knowledge.deploy.v2_0;

import org.support.project.knowledge.deploy.Migrate;
import org.support.project.ormapping.tool.dao.InitializeDao;

public class Migrate_2_0_1 implements Migrate {

    public static Migrate_2_0_1 get() {
        return org.support.project.di.Container.getComp(Migrate_2_0_1.class);
    }

    @Override
    public boolean doMigrate() throws Exception {
        InitializeDao initializeDao = InitializeDao.get();
        String[] sqlpaths = {
            "/org/support/project/knowledge/deploy/v2_0/migrate_v2_0_1.sql",
        };
        initializeDao.initializeDatabase(sqlpaths);
        return true;
    }
}