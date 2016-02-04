package org.support.project.knowledge;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.support.project.common.config.ConfigLoader;
import org.support.project.common.exception.SerializeException;
import org.support.project.common.serialize.SerializeUtils;
import org.support.project.common.util.FileUtil;
import org.support.project.common.util.RandomUtil;
import org.support.project.knowledge.config.AppConfig;
import org.support.project.knowledge.deploy.InitDB;
import org.support.project.ormapping.connection.ConnectionManager;
import org.support.project.ormapping.tool.config.ORmappingToolConfig;
import org.support.project.ormapping.tool.dao.InitializeDao;
import org.support.project.web.bean.LoginedUser;
import org.support.project.web.dao.RolesDao;
import org.support.project.web.dao.UsersDao;
import org.support.project.web.entity.RolesEntity;
import org.support.project.web.entity.UsersEntity;

public class TestCommon {
	public static LoginedUser loginedUser = null;
	public static LoginedUser loginedUser2 = null;
	
	public static void testConnection() throws SerializeException, IOException {
		// コネクション設定
		String configFileName = "/ormappingtool.xml";
		ORmappingToolConfig config = SerializeUtils.bytesToObject(
				TestCommon.class.getResourceAsStream(configFileName), 
				ORmappingToolConfig.class);
		config.getConnectionConfig().convURL();
		ConnectionManager.getInstance().addConnectionConfig(config.getConnectionConfig());
	}
	
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
