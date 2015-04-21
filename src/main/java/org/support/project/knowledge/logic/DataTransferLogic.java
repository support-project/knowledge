package org.support.project.knowledge.logic;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.support.project.common.classanalysis.ClassSearch;
import org.support.project.common.config.ConfigLoader;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.di.Container;
import org.support.project.knowledge.deploy.InitDB;
import org.support.project.ormapping.config.ConnectionConfig;
import org.support.project.ormapping.connection.ConnectionManager;
import org.support.project.ormapping.dao.AbstractDao;
import org.support.project.ormapping.tool.dao.InitializeDao;
import org.support.project.ormapping.transaction.TransactionManager;
import org.support.project.web.config.AppConfig;

public class DataTransferLogic {
	private static final String TRANSFER_REQEST = "TRANSFER_REQEST";
	private static final String TRANSFER_STARTED = "TRANSFER_STARTED";
	
	/** ログ */
	private static Log LOG = LogFactory.getLog(DataTransferLogic.class);
	
	public static DataTransferLogic get() {
		return Container.getComp(DataTransferLogic.class);
	}
	
	public void transferData(ConnectionConfig from, ConnectionConfig to) throws Exception {
		InitDB initDB = new InitDB();
		ConnectionManager.getInstance().addConnectionConfig(to);
		InitializeDao initializeDao = InitializeDao.get();
		initializeDao.setConnectionName(to.getName());
		initializeDao.dropAllTable();
		initDB.start();

		ConnectionManager.getInstance().addConnectionConfig(from);
		// コピー元も念のため、バージョンを合わせるために初期化を行う？
//		initDB.start();
		
		List<Class> targets = new ArrayList<Class>();
		ClassSearch classSearch = Container.getComp(ClassSearch.class);
		Class[] classes = classSearch.classSearch("org.support.project.knowledge.dao.gen", false);
		targets.addAll(Arrays.asList(classes));
		classes = classSearch.classSearch("org.support.project.web.dao.gen", false);
		targets.addAll(Arrays.asList(classes));
		
// 削除してから、再登録する場合、H2SQLだと同じ番号になるが、Postgresqlだと次の番号になりNG
//		//初期ロールと初期ユーザを削除
//		UserRolesDao userRolesDao = UserRolesDao.get();
//		userRolesDao.setConnectionName(to.getName());
//		List<UserRolesEntity> userRolesEntities = userRolesDao.physicalSelectAll();
//		for (UserRolesEntity userRolesEntity : userRolesEntities) {
//			userRolesDao.physicalDelete(userRolesEntity);
//		}
//		UsersDao usersDao = UsersDao.get();
//		usersDao.setConnectionName(to.getName());
//		List<UsersEntity> usersEntities = usersDao.physicalSelectAll();
//		for (UsersEntity usersEntity : usersEntities) {
//			usersDao.physicalDelete(usersEntity);
//		}
//		RolesDao rolesDao = RolesDao.get();
//		rolesDao.setConnectionName(to.getName());
//		List<RolesEntity> rolesEntities = rolesDao.physicalSelectAll();
//		for (RolesEntity rolesEntity : rolesEntities) {
//			rolesDao.physicalDelete(rolesEntity);
//		}
//		SystemsDao systemsDao = SystemsDao.get();
//		systemsDao.setConnectionName(to.getName());
//		List<SystemsEntity> systemsEntities = systemsDao.physicalSelectAll();
//		for (SystemsEntity systemsEntity : systemsEntities) {
//			systemsDao.physicalDelete(systemsEntity);
//		}
		
		// データコピー
		for (Class class1 : targets) {
			if (AbstractDao.class.isAssignableFrom(class1)) {
				System.out.println("Data transfer : " + class1.getName());
				Object dao = Container.getComp(class1);
				// From からデータ取得
				Method setConnectionNameMethods = class1.getMethod("setConnectionName", String.class);
				setConnectionNameMethods.invoke(dao, from.getName());
				
				Method selectAllMethods = class1.getMethod("physicalSelectAll");
				List<?> list = (List<?>) selectAllMethods.invoke(dao);
				
				// Toへデータ登録
				setConnectionNameMethods.invoke(dao, to.getName());
				TransactionManager transactionManager = Container.getComp(TransactionManager.class);
				transactionManager.start(to.getName());
				for (Object entity : list) {
					if (LOG.isTraceEnabled()) {
						LOG.trace(entity);
					}
					Method insertMethods = class1.getMethod("rawPhysicalInsert", entity.getClass());
					try {
						insertMethods.invoke(dao, entity);
					} catch (Exception e) {
						// 初期登録ユーザなどをコピーしようとすると、主キー制約違反になるが無視する
						LOG.warn("Data copy error");
					}
				}
				transactionManager.commit(to.getName());
			}
		}
		
	}
	
	
	
	/**
	 * データコピーのリクエストを登録
	 * @throws IOException
	 */
	public void requestTransfer() throws IOException {
		if (!isTransferRequested()) {
			AppConfig appConfig = ConfigLoader.load(AppConfig.APP_CONFIG, AppConfig.class);
			File base = new File(appConfig.getBasePath());
			File config = new File(base, TRANSFER_REQEST);
			OutputStream out = null;
			try {
				out = new FileOutputStream(config);
				out.write(new String("TRANSFER_REQEST").getBytes(Charset.forName("UTF-8")));
			} finally {
				if (out != null) {
					out.close();
				}
			}
		}
	}
	
	/**
	 * コピーのリクエストがあるかチェック
	 * @return
	 */
	public boolean isTransferRequested() {
		AppConfig appConfig = ConfigLoader.load(AppConfig.APP_CONFIG, AppConfig.class);
		File base = new File(appConfig.getBasePath());
		File config = new File(base, TRANSFER_REQEST);
		return config.exists();
	}
	
	/**
	 * 既に開始済かどうかチェックし、開始済でなければ開始済のフラグをON
	 * @return
	 * @throws IOException
	 */
	public boolean isTransferStarted() throws IOException {
		AppConfig appConfig = ConfigLoader.load(AppConfig.APP_CONFIG, AppConfig.class);
		File base = new File(appConfig.getBasePath());
		File config = new File(base, TRANSFER_STARTED);
		if (!config.exists()) {
			OutputStream out = null;
			try {
				out = new FileOutputStream(config);
				out.write(new String("TRANSFER_STARTED").getBytes(Charset.forName("UTF-8")));
			} finally {
				if (out != null) {
					out.close();
				}
			}
			return false;
		}
		return true;
	}
	/**
	 * データ移行の終了
	 */
	public void finishTransfer() {
		AppConfig appConfig = ConfigLoader.load(AppConfig.APP_CONFIG, AppConfig.class);
		File base = new File(appConfig.getBasePath());
		File config = new File(base, TRANSFER_STARTED);
		if (config.exists()) {
			config.delete();
		}
		config = new File(base, TRANSFER_REQEST);
		if (config.exists()) {
			config.delete();
		}
	}

}
