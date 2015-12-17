package org.support.project.knowledge.deploy.v001;

import org.support.project.knowledge.config.AppConfig;
import org.support.project.knowledge.config.SystemConfig;
import org.support.project.knowledge.deploy.Migrate;
import org.support.project.ormapping.tool.dao.InitializeDao;
import org.support.project.web.dao.SystemConfigsDao;
import org.support.project.web.entity.SystemConfigsEntity;

public class Migrate001 implements Migrate {

	public static Migrate001 get() {
		return org.support.project.di.Container.getComp(Migrate001.class);
	}

	@Override
	public boolean doMigrate() throws Exception {
		// ユーザ個人設定
		InitializeDao initializeDao = InitializeDao.get();
		String[] sqlpaths = { "/org/support/project/knowledge/deploy/v001/migrate.sql" };
		initializeDao.initializeDatabase(sqlpaths);
		
		// システムのデフォルトのテーマをセット
		SystemConfigsEntity config = new SystemConfigsEntity(SystemConfig.CONFIG_KEY_THEMA, AppConfig.get().getSystemName());
		config.setConfigValue("flatly");
		SystemConfigsDao.get().insert(config);
		
		return true;

	}

}
