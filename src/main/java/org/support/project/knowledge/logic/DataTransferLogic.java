package org.support.project.knowledge.logic;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.support.project.common.classanalysis.ClassSearch;
import org.support.project.common.config.ConfigLoader;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.DateUtils;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.config.AppConfig;
import org.support.project.knowledge.dao.TemplateItemsDao;
import org.support.project.knowledge.dao.TemplateMastersDao;
import org.support.project.knowledge.deploy.InitDB;
import org.support.project.ormapping.config.ConnectionConfig;
import org.support.project.ormapping.connection.ConnectionManager;
import org.support.project.ormapping.dao.AbstractDao;
import org.support.project.ormapping.tool.dao.InitializeDao;
import org.support.project.ormapping.transaction.TransactionManager;
import org.support.project.web.dao.GroupsDao;
import org.support.project.web.dao.RolesDao;
import org.support.project.web.dao.SystemsDao;
import org.support.project.web.dao.UserRolesDao;
import org.support.project.web.dao.UsersDao;
import org.support.project.web.entity.SystemsEntity;
import org.support.project.web.entity.UsersEntity;

@DI(instance=Instance.Singleton)
public class DataTransferLogic {
	private static final String TRANSFER_REQEST = "TRANSFER_REQEST";
	private static final String TRANSFER_REQEST_BACK = "TRANSFER_REQEST_BACK";
	private static final String TRANSFER_STARTED = "TRANSFER_STARTED";
	
	/** ログ */
	private static Log LOG = LogFactory.getLog(DataTransferLogic.class);
	
	public static DataTransferLogic get() {
		return Container.getComp(DataTransferLogic.class);
	}
	
	public void transferData(ConnectionConfig from, ConnectionConfig to) throws Exception {
		// FromのDBのDBのバージョンチェック
		ConnectionManager.getInstance().addConnectionConfig(from);
		SystemsDao systemsDao = SystemsDao.get();
		SystemsEntity systemEntity = systemsDao.selectOnKey(AppConfig.get().getSystemName());
		if (systemEntity == null) {
			// Systemバージョン情報が無い未初期化のDBからコピーしてもしょうがない
			System.out.println("Data transfer is failed.");
			return;
		}
		String version = systemEntity.getVersion();
		if (!InitDB.CURRENT.equals(version)) {
			// コピー元のDBのバージョンが古い（なんかおかしい）
			System.out.println("Data transfer is failed.");
			return;
		}
		
		// コピー先のDBの初期化
		InitDB initDB = new InitDB();
		ConnectionManager.getInstance().addConnectionConfig(to);
		InitializeDao initializeDao = InitializeDao.get();
		initializeDao.setConnectionName(to.getName());
		initializeDao.dropAllTable();
		initDB.start();
		
		ConnectionManager.getInstance().addConnectionConfig(from);
		
		List<Class> targets = new ArrayList<Class>();
		ClassSearch classSearch = Container.getComp(ClassSearch.class);
		Class[] classes = classSearch.classSearch("org.support.project.knowledge.dao.gen", false);
		targets.addAll(Arrays.asList(classes));
		classes = classSearch.classSearch("org.support.project.web.dao.gen", false);
		targets.addAll(Arrays.asList(classes));


		// 組み込みDBからカスタムDBへの移行の時のみ初期データを削除
		List<AbstractDao> truncateTargets = new ArrayList<AbstractDao>();
		truncateTargets.add(GroupsDao.get());
		truncateTargets.add(RolesDao.get());
		truncateTargets.add(UserRolesDao.get());
		truncateTargets.add(UsersDao.get());
		truncateTargets.add(SystemsDao.get());
		truncateTargets.add(TemplateItemsDao.get());
		truncateTargets.add(TemplateMastersDao.get());

		for (AbstractDao targetDao : truncateTargets) {
			targetDao.setConnectionName(to.getName());
			Method truncateMethods = targetDao.getClass().getMethod("truncate");
			truncateMethods.invoke(targetDao);

			if (isTransferRequested()) {
				try {
					Method resetSequenceMethods = targetDao.getClass().getMethod("resetSequence");
					resetSequenceMethods.invoke(targetDao);
				} catch (Exception e) { }
			}
		}


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
					insertMethods.invoke(dao, entity);
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
	 * データコピーのリクエストを登録
	 * (カスタムDBから組み込みDBへ)
	 * @throws IOException
	 */
	public void requestTransferBack() throws IOException {
		if (!isTransferRequested()) {
			AppConfig appConfig = ConfigLoader.load(AppConfig.APP_CONFIG, AppConfig.class);
			File base = new File(appConfig.getBasePath());
			File config = new File(base, TRANSFER_REQEST_BACK);
			OutputStream out = null;
			try {
				out = new FileOutputStream(config);
				out.write(new String("TRANSFER_REQEST_BACK").getBytes(Charset.forName("UTF-8")));
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
	 * コピーのリクエストがあるかチェック
	 * (カスタムDBから組み込みDBへ)
	 * @return
	 */
	public boolean isTransferBackRequested() {
		AppConfig appConfig = ConfigLoader.load(AppConfig.APP_CONFIG, AppConfig.class);
		File base = new File(appConfig.getBasePath());
		File config = new File(base, TRANSFER_REQEST_BACK);
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
		config = new File(base, TRANSFER_REQEST_BACK);
		if (config.exists()) {
			config.delete();
		}
	}
	
	/**
	 * サーバー起動時に起動しているH2Databaseをバックアップする（リネーム)
	 * @throws IOException 
	 */
	public void backupAndInitH2() throws IOException {
		AppConfig appConfig = AppConfig.get();
		Path source = Paths.get(appConfig.getDatabasePath());
		if (Files.exists(source)) {
			Path target = Paths.get(appConfig.getBasePath() + "/db_" + DateUtils.getTransferDateFormat().format(new Date()));
			Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);
		}
	}

}
