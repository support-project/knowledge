package org.support.project.web.listener;

import java.io.File;
import java.lang.invoke.MethodHandles;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.web.config.AppConfig;

/**
 * WebAppの起動時の初期化処理のリスナークラス
 * @author Koda
 */
public class WebAppInitializationListener implements ServletContextListener {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(MethodHandles.lookup());

    @Override
    public void contextInitialized(ServletContextEvent sce) {
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
        
        // 添付ファイル格納ディレクトリ（テンポラリディレクトリ）が存在しなければ生成
        AppConfig appConfig = AppConfig.get();
        String tmpDir = appConfig.getTmpPath();
        File tmp = new File(tmpDir);
        if (!tmp.exists()) {
            tmp.mkdirs();
            LOG.info("tmp directory created." + tmpDir);
        }
        
        String path = sce.getServletContext().getRealPath("/");
        LOG.info("WebApp start");
        LOG.info("WebApp install path: '" + path + "'");
        LOG.info("WebApp home path: '" + appConfig.getBasePath() + "'");
        LOG.info("[APP LOG] " + logDir.getAbsolutePath() + "/app.log");
        AppConfig.setWebRealPath(path);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }

}
