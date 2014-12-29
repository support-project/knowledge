package redcomet.knowledge;

import java.io.File;
import java.util.List;

import redcomet.common.config.ConfigLoader;
import redcomet.common.util.FileUtil;
import redcomet.common.util.RandomUtil;
import redcomet.knowledge.config.AppConfig;
import redcomet.knowledge.deploy.InitDB;
import redcomet.ormapping.tool.dao.InitializeDao;
import redcomet.web.bean.LoginedUser;
import redcomet.web.dao.RolesDao;
import redcomet.web.dao.UsersDao;
import redcomet.web.entity.RolesEntity;
import redcomet.web.entity.UsersEntity;

public class TestCommon {
	public static LoginedUser loginedUser = null;
	public static LoginedUser loginedUser2 = null;
	
	public static void initData() throws Exception {
		loginedUser = new LoginedUser();
		loginedUser2 = new LoginedUser();
		
		//DBを完全初期化
		InitializeDao initializeDao = InitializeDao.get();
		initializeDao.dropAllTable();
		InitDB.main(null);
		// 全文検索エンジンのインデックスの消去
		AppConfig appConfig = ConfigLoader.load(AppConfig.APP_CONFIG, AppConfig.class);
		File indexDir = new File(appConfig.getIndexPath());
		FileUtil.delete(indexDir);
		
		// テスト用のユーザを登録
		UsersEntity entity = new UsersEntity();
		entity.setUserKey(RandomUtil.randamGen(64));
		entity.setUserName("テストユーザ");
		entity.setPassword(RandomUtil.randamGen(64));
		entity = UsersDao.get().insert(entity);
		loginedUser.setLoginUser(entity);
		
		RolesDao rolesDao = RolesDao.get();
		List<RolesEntity> rolesEntities = rolesDao.selectOnUserKey(entity.getUserKey());
		loginedUser.setRoles(rolesEntities);
		
		UsersEntity entity2 = new UsersEntity();
		entity2.setUserKey(RandomUtil.randamGen(64));
		entity2.setUserName("テストユーザ2");
		entity2.setPassword(RandomUtil.randamGen(64));
		entity2 = UsersDao.get().insert(entity2);
		loginedUser2.setLoginUser(entity2);
		loginedUser.setRoles(rolesEntities);
	}
	
	
}
