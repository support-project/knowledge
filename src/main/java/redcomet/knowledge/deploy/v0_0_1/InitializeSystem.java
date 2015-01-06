package redcomet.knowledge.deploy.v0_0_1;

import redcomet.knowledge.config.SystemConfig;
import redcomet.knowledge.deploy.Migrate;
import redcomet.ormapping.tool.dao.InitializeDao;
import redcomet.web.dao.RolesDao;
import redcomet.web.dao.UserRolesDao;
import redcomet.web.dao.UsersDao;
import redcomet.web.entity.RolesEntity;
import redcomet.web.entity.UserRolesEntity;
import redcomet.web.entity.UsersEntity;

public class InitializeSystem implements Migrate {
	
	public static InitializeSystem get() {
		return redcomet.di.Container.getComp(InitializeSystem.class);
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
				"/redcomet/web/database/ddl.sql",
				"/redcomet/knowledge/database/ddl.sql"
		};
		initializeDao.initializeDatabase(sqlpaths);
	}


}
