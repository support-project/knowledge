package org.support.project.knowledge.listener;

import java.io.File;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.StringUtils;
import org.support.project.knowledge.config.AppConfig;

public class GlobalInitializationListener implements ServletContextListener {
    private static final Log LOG = LogFactory.getLog(GlobalInitializationListener.class);

    @Override
    public void contextInitialized(ServletContextEvent config) {
        AppConfig.get();
        String envValue = System.getenv(AppConfig.getEnvKey());
        if (StringUtils.isNotEmpty(envValue)) {
            LOG.info("Env [" + AppConfig.getEnvKey() + "] is [" + envValue + "].");
        }
        String rootPath = AppConfig.get().getBasePath();
        System.setProperty("user.dir", rootPath);
        String logsPath = AppConfig.get().getLogsPath();
        File logDir = new File(logsPath);
        if (!logDir.exists()) {
            logDir.mkdirs();
        }
        Logger log = Logger.getRootLogger();
        FileAppender appendar = (FileAppender) log.getAppender("APP_FILEOUT");
        appendar.setFile(logDir + "/app.log");
        appendar.activateOptions(); // 変更の反映
        LOG.info("[APP LOG] " + logDir.getAbsolutePath() + "/app.log");
    }

    @Override
    public void contextDestroyed(ServletContextEvent config) {

    }

}
