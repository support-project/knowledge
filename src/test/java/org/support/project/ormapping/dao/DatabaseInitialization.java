package org.support.project.ormapping.dao;

import java.io.File;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.support.project.common.config.AppConfig;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.logic.H2DBServerLogic;
import org.support.project.ormapping.tool.dao.InitializeDao;

/**
 * Database initialization for test
 * @author Koda
 */
public class DatabaseInitialization {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(DatabaseInitialization.class);
    
    /**
     * set up
     * @throws Exception
     */
    public static void setUp() throws Exception {
        String rootPath = AppConfig.get().getBasePath();
        System.setProperty("user.dir", rootPath);
        
        // ログディレクトリ生成
        String logsPath = AppConfig.get().getLogsPath();
        File logDir = new File(logsPath);
        if (!logDir.exists()) {
            logDir.mkdirs();
        }
        Logger log = Logger.getRootLogger();
        FileAppender appendar = (FileAppender) log.getAppender("APP_FILEOUT");
        if (appendar != null) {
            File logfile = new File(logDir, "app.log");
            appendar.setFile(logfile.getAbsolutePath());
            appendar.activateOptions(); //変更の反映
        }
        
        LOG.info("App start");
        LOG.info("App home path: '" + AppConfig.get().getBasePath() + "'");
        LOG.info("[APP LOG] " + logDir.getAbsolutePath() + "/app.log");
        
        if (!H2DBServerLogic.get().isActive()) {
            H2DBServerLogic.get().start();
        }
        InitializeDao dao = InitializeDao.get();
        //全テーブル削除
        dao.dropAllTable();
        // Webのデータベース登録
        dao.initializeDatabase("/ddl.sql");
    }

}
