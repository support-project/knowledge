package org.support.project.knowledge.deploy;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TimeZone;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.PropertyUtil;
import org.support.project.knowledge.config.AppConfig;
import org.support.project.knowledge.deploy.v0_0_1.InitializeSystem;
import org.support.project.knowledge.deploy.v0_4_4.Migrate_0_4_4;
import org.support.project.knowledge.deploy.v0_5_0.Migrate_0_5_0;
import org.support.project.knowledge.deploy.v0_5_1.Migrate_0_5_1;
import org.support.project.knowledge.deploy.v0_5_2pre2.Migrate_0_5_2pre2;
import org.support.project.knowledge.deploy.v0_5_3pre2.Migrate_0_5_3pre2;
import org.support.project.knowledge.deploy.v0_5_3pre3.Migrate_0_5_3pre3;
import org.support.project.knowledge.deploy.v0_6_0pre2.Migrate_0_6_0pre2;
import org.support.project.knowledge.deploy.v0_6_0pre4.Migrate_0_6_0pre4;
import org.support.project.knowledge.deploy.v0_8_0pre1.Migrate_0_8_0pre1;
import org.support.project.knowledge.deploy.v1_10_0.Migrate_1_10_0;
import org.support.project.knowledge.deploy.v1_10_0.Migrate_1_10_1;
import org.support.project.knowledge.deploy.v1_10_0.Migrate_1_10_2;
import org.support.project.knowledge.deploy.v1_11_0.Migrate_1_11_0;
import org.support.project.knowledge.deploy.v1_11_0.Migrate_1_11_1;
import org.support.project.knowledge.deploy.v1_11_0.Migrate_1_11_2;
import org.support.project.knowledge.deploy.v1_11_0.Migrate_1_11_3;
import org.support.project.knowledge.deploy.v1_11_0.Migrate_1_11_4;
import org.support.project.knowledge.deploy.v1_12.Migrate_1_12_0;
import org.support.project.knowledge.deploy.v1_12.Migrate_1_12_1;
import org.support.project.knowledge.deploy.v1_1_0pre1.Migrate_1_1_0pre1;
import org.support.project.knowledge.deploy.v1_4_0.Migrate_1_4_0;
import org.support.project.knowledge.deploy.v1_5_0.Migrate_1_5_0;
import org.support.project.knowledge.deploy.v1_5_0_2.Migrate_1_5_0_2;
import org.support.project.knowledge.deploy.v1_6_0.Migrate_1_6_0;
import org.support.project.knowledge.deploy.v1_7_0.Migrate_1_7_0;
import org.support.project.knowledge.deploy.v1_7_0.Migrate_1_7_0_1;
import org.support.project.knowledge.deploy.v1_8_0.Migrate_1_8_0;
import org.support.project.knowledge.deploy.v1_8_0.Migrate_1_8_1;
import org.support.project.knowledge.deploy.v1_8_0.Migrate_1_8_2;
import org.support.project.knowledge.deploy.v1_8_0.Migrate_1_8_3;
import org.support.project.knowledge.deploy.v1_8_0.Migrate_1_8_4;
import org.support.project.knowledge.deploy.v1_8_0.Migrate_1_8_5;
import org.support.project.web.dao.SystemsDao;
import org.support.project.web.entity.SystemsEntity;
import org.support.project.web.logic.DBConnenctionLogic;

public class InitDB {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(InitDB.class);

    private static final String SYSTEM_NAME = org.support.project.knowledge.config.AppConfig.get().getSystemName();
    private static final Map<String, Migrate> MAP = new LinkedHashMap<>();

    private static final Migrate INIT = InitializeSystem.get();
    public static final String CURRENT = "1.12.1";

    public InitDB() {
        super();
        MAP.put("0.3.1", INIT); // 初期公開バージョン
        MAP.put("0.4.4", Migrate_0_4_4.get()); // ナレッジ一覧の付加情報をナレッジテーブルに持つ
        MAP.put("0.5.0", Migrate_0_5_0.get()); // 通知設定
        MAP.put("0.5.1", Migrate_0_5_1.get()); // ナレッジの更新履歴
        MAP.put("0.5.2.pre2", Migrate_0_5_2pre2.get()); // 共同編集
        MAP.put("0.5.3.pre2", Migrate_0_5_3pre2.get()); // ALLグループ
        MAP.put("0.5.3.pre3", Migrate_0_5_3pre3.get()); // メールアドレス
        MAP.put("0.6.0.pre2", Migrate_0_6_0pre2.get());
        MAP.put("0.6.0.pre4", Migrate_0_6_0pre4.get());
        MAP.put("0.8.0.pre1", Migrate_0_8_0pre1.get());
        MAP.put("1.1.0.pre1", Migrate_1_1_0pre1.get());
        MAP.put("1.4.0", Migrate_1_4_0.get());
        MAP.put("1.5.0", Migrate_1_5_0.get());
        MAP.put("1.5.0_2", Migrate_1_5_0_2.get());
        MAP.put("1.6.0", Migrate_1_6_0.get());
        MAP.put("1.7.0", Migrate_1_7_0.get());
        MAP.put("1.7.0.1", Migrate_1_7_0_1.get());
        MAP.put("1.8.0", Migrate_1_8_0.get());
        MAP.put("1.8.1", Migrate_1_8_1.get());
        MAP.put("1.8.2", Migrate_1_8_2.get());
        MAP.put("1.8.3", Migrate_1_8_3.get());
        MAP.put("1.8.4", Migrate_1_8_4.get());
        MAP.put("1.8.5", Migrate_1_8_5.get());
        MAP.put("1.10.0", Migrate_1_10_0.get());
        MAP.put("1.10.1", Migrate_1_10_1.get());
        MAP.put("1.10.2", Migrate_1_10_2.get());
        MAP.put("1.11.0", Migrate_1_11_0.get());
        MAP.put("1.11.1", Migrate_1_11_1.get());
        MAP.put("1.11.2", Migrate_1_11_2.get());
        MAP.put("1.11.3", Migrate_1_11_3.get());
        MAP.put("1.11.4", Migrate_1_11_4.get());
        MAP.put("1.12.0", Migrate_1_12_0.get());
        MAP.put("1.12.1", Migrate_1_12_1.get());
    }

    public static void main(String[] args) throws Exception {
        // 内部的には、日付はGMTとして扱う
        TimeZone zone = TimeZone.getTimeZone("GMT");
        TimeZone.setDefault(zone);

        AppConfig.get();
        String envValue = System.getenv(AppConfig.getEnvKey());
        LOG.info("migrate is start.");
        if (LOG.isDebugEnabled()) {
            LOG.debug("Env [" + AppConfig.getEnvKey() + "] is [" + envValue + "].");
            LOG.debug("Config :" + PropertyUtil.reflectionToString(AppConfig.get()));
        }
        DBConnenctionLogic.get().connectCustomConnection();
        
        InitDB init = new InitDB();
        init.start();
    }
    
    private String doInit() throws Exception {
        String version = CURRENT;
        Migrate migrate = INIT;
        doMigrate(migrate, version);
        return CURRENT;
    }
    public String init() throws Exception {
        SystemsDao dao = SystemsDao.get();
        try {
            SystemsEntity entity = dao.selectOnKey(SYSTEM_NAME);
            if (entity != null) {
                LOG.info("The database of this service is arleady inited. database version is " + entity.getVersion() + ".");
                return entity.getVersion();
            } else {
                return doInit();
            }
        } catch (Exception e) {
            // テーブルが存在しない（初めての起動）
            return doInit();
        }
    }

    public void start() throws Exception {
        LOG.info("Lastest database version is " + CURRENT + " now");

        String version = "";

        Migrate migrate;
        SystemsDao dao = SystemsDao.get();
        SystemsEntity entity;
        do {
            boolean verup = false;
            migrate = null;
            try {
                entity = dao.selectOnKey(SYSTEM_NAME);
                if (entity != null) {
                    version = entity.getVersion();
                    verup = true;
                }
            } catch (Exception e) {
                // テーブルが存在しない
            }

            if (!verup) {
                // テーブルが存在しない（初めての起動）
                version = CURRENT;
                migrate = INIT;
                doMigrate(migrate, version);
                return;
            }
            LOG.info("Current database version is " + version + " now");

            // バージョンアップ
            Iterator<String> versions = MAP.keySet().iterator();
            boolean finded = false;
            while (versions.hasNext()) {
                String v = (String) versions.next();
                if (finded) {
                    // 一致したものの次がバージョンアップするもの
                    version = v;
                    migrate = MAP.get(v);
                    break;
                }
                if (version.equals(v)) {
                    finded = true;
                    // System テーブルに書かれているバージョンをバージョンアップの一覧から一致するものを発見
                }
            }

            if (migrate == null) {
                // バージョンアップするものが見つからない
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
