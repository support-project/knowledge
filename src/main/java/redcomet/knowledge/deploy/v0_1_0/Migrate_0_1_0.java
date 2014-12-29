package redcomet.knowledge.deploy.v0_1_0;

import redcomet.knowledge.deploy.Migrate;

public class Migrate_0_1_0 implements Migrate {
	
	public static Migrate_0_1_0 get() {
		return redcomet.di.Container.getComp(Migrate_0_1_0.class);
	}

	@Override
	public boolean doMigrate() throws Exception {
		return true;
	}

}
