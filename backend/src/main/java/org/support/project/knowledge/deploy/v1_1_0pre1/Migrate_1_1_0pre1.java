package org.support.project.knowledge.deploy.v1_1_0pre1;

import org.support.project.knowledge.deploy.Migrate;
import org.support.project.ormapping.tool.dao.InitializeDao;

public class Migrate_1_1_0pre1 implements Migrate {

    public static Migrate_1_1_0pre1 get() {
        return org.support.project.di.Container.getComp(Migrate_1_1_0pre1.class);
    }

    @Override
    public boolean doMigrate() throws Exception {
        InitializeDao initializeDao = InitializeDao.get();
        String[] sqlpaths = {
            "/org/support/project/knowledge/deploy/v1_1_0pre1/migrate.sql",
            "/org/support/project/knowledge/deploy/v1_1_0pre1/migrate2.sql"
        };
        initializeDao.initializeDatabase(sqlpaths);
        return true;
    }
}