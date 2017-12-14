package org.support.project.knowledge.deploy.v0_1_0;

import org.support.project.knowledge.deploy.Migrate;

public class Migrate_0_1_0 implements Migrate {

    public static Migrate_0_1_0 get() {
        return org.support.project.di.Container.getComp(Migrate_0_1_0.class);
    }

    @Override
    public boolean doMigrate() throws Exception {
        return true;
    }

}
