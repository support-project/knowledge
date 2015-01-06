package redcomet.knowledge.deploy;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import redcomet.common.log.Log;
import redcomet.common.log.LogFactory;
import redcomet.knowledge.deploy.v0_0_1.InitializeSystem;
import redcomet.knowledge.deploy.v0_3_0.Migrate_0_3_0;
import redcomet.knowledge.deploy.v0_3_1.Migrate_0_3_1;
import redcomet.web.dao.SystemsDao;
import redcomet.web.entity.SystemsEntity;


public class InitDB {
	/** ログ */
	private static Log LOG = LogFactory.getLog(InitDB.class);
	
	private static final String SYSTEM_NAME = redcomet.knowledge.config.AppConfig.SYSTEM_NAME;
	private static final Map<String, Migrate> MAP = new LinkedHashMap<>();
	
	private static final Migrate INIT = InitializeSystem.get();
	private static final String CURRENT = "0.3.1";
	
	public InitDB() {
		super();
		// MAP.put("0.0.1", InitializeSystem.get());
		// MAP.put("0.1.0", Migrate_0_1_0.get());
		MAP.put("0.2.0", INIT); // 初期公開バージョン
		MAP.put("0.3.0", Migrate_0_3_0.get());
		MAP.put(CURRENT, Migrate_0_3_1.get());
	}

	public static void main(String[] args) throws Exception {
		InitDB init = new InitDB();
		init.start();
	}
	
	public void start() throws Exception {
		String version = "";
		
		Migrate migrate = null;
		SystemsDao dao = SystemsDao.get();
		SystemsEntity entity = null;
		do {
			migrate = null;
			boolean finded = false;
			try {
				entity = dao.selectOnKey(SYSTEM_NAME);
				if (entity != null) {
					version = entity.getVersion();
				} else {
					finded = true;
				}
			} catch (Exception e) {
				//テーブルが存在しない
				finded = true;
			}
			
			if (finded) {
				// テーブルが存在しない（初めての起動）
				version = CURRENT;
				migrate = INIT;
				doMigrate(migrate, version);
				return;
			}
			
			// バージョンアップ
			Iterator<String> versions = MAP.keySet().iterator();
			while (versions.hasNext()) {
				String v = (String) versions.next();
				if (version.equals(v)) {
					finded = true;
				}
			}
			
			if (migrate == null) {
				break;
			}
			
			// Migrate 実行
			doMigrate(migrate, version);
		} while (migrate != null);
	}

	private void doMigrate(Migrate migrate, String version) throws Exception {
		SystemsDao dao = SystemsDao.get();
		boolean result = migrate.doMigrate();
		if (result) {
			SystemsEntity entity = new SystemsEntity(SYSTEM_NAME);
			entity.setVersion(version);
			LOG.info("Migrate to " + version);
			dao.save(entity);
		}
	}

}
