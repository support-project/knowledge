package org.support.project.knowledge.deploy.v0_0_1;

import org.support.project.knowledge.config.SystemConfig;
import org.support.project.knowledge.deploy.Migrate;
import org.support.project.ormapping.tool.dao.InitializeDao;
import org.support.project.web.dao.RolesDao;
import org.support.project.web.dao.UserRolesDao;
import org.support.project.web.dao.UsersDao;
import org.support.project.web.entity.RolesEntity;
import org.support.project.web.entity.UserRolesEntity;
import org.support.project.web.entity.UsersEntity;

public class InitializeSystem implements Migrate {
	
	public static InitializeSystem get() {
		return org.support.project.di.Container.getComp(InitializeSystem.class);
	}
	
	@Override
	public boolean doMigrate() throws Exception {
		createTables();
		addInitDatas();
		return true;
	}
	
	private void addInitDatas() {
		//権限の追加
		RolesEntity adminRole = RolesEntity.get();
		adminRole.setRoleId(1);
		adminRole.setRoleKey(SystemConfig.ROLE_ADMIN);
		adminRole.setRoleName("管理者権限");
		RolesDao.get().insert(adminRole);
		
		RolesEntity userRole = RolesEntity.get();
		userRole.setRoleId(2);
		userRole.setRoleKey(SystemConfig.ROLE_USER);
		userRole.setRoleName("一般ユーザ権限");
		RolesDao.get().insert(userRole);
		
		//管理者ユーザ追加
		UsersDao usersDao = UsersDao.get();
		UsersEntity usersEntity = UsersEntity.get();
		usersEntity.setUserId(1);
		usersEntity.setUserKey("admin");
		usersEntity.setPassword("admin123");
		usersEntity.setUserName("管理者ユーザ");
		usersDao.save(usersEntity);
		
		//管理者ユーザと管理者権を紐付け
		UserRolesDao userRolesDao = UserRolesDao.get();
		UserRolesEntity userRolesEntity = UserRolesEntity.get();
		userRolesEntity.setUserId(1);
		userRolesEntity.setRoleId(1);
		userRolesDao.save(userRolesEntity);
	}

	private void createTables() {
		//存在するテーブルを全て削除
		InitializeDao initializeDao = InitializeDao.get();
		initializeDao.dropAllTable();
		
		String[] sqlpaths = {
				"/org/support/project/web/database/ddl.sql",
				"/org/support/project/knowledge/database/ddl.sql"
		};
		initializeDao.initializeDatabase(sqlpaths);
	}


}
