package org.support.project.knowledge.deploy.v0_6_0pre2;

import org.support.project.knowledge.deploy.Migrate;
import org.support.project.ormapping.tool.dao.InitializeDao;

public class Migrate_0_6_0pre2 implements Migrate {

    public static Migrate_0_6_0pre2 get() {
        return org.support.project.di.Container.getComp(Migrate_0_6_0pre2.class);
    }

    @Override
    public boolean doMigrate() throws Exception {
        InitializeDao initializeDao = InitializeDao.get();
        String[] sqlpaths = { "/org/support/project/knowledge/deploy/v0_6_0pre2/migrate.sql" };
        initializeDao.initializeDatabase(sqlpaths);
        return true;

    }

}
