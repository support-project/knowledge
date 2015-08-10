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
import org.support.project.knowledge.config.SystemConfig;

public class GlobalInitializationListener implements ServletContextListener {
	private static Log LOG = LogFactory.getLog(GlobalInitializationListener.class);

	@Override
	public void contextInitialized(ServletContextEvent config) {
		AppConfig.initEnvKey(SystemConfig.KNOWLEDGE_ENV_KEY);
		String envValue = System.getenv(SystemConfig.KNOWLEDGE_ENV_KEY);
		if (StringUtils.isNotEmpty(envValue)) {
			LOG.info("Env [" + SystemConfig.KNOWLEDGE_ENV_KEY + "] is [" + envValue + "].");
		}
		
		String rootPath = AppConfig.get().getBasePath();
		System.setProperty("user.dir", rootPath);
		File logDir = new File(rootPath + "/logs");
		if (!logDir.exists()) {
			logDir.mkdirs();
		}
		Logger log = Logger.getRootLogger();
		FileAppender appendar= (FileAppender) log.getAppender("APP_FILEOUT");
		appendar.setFile(logDir + "/app.log");
		appendar.activateOptions();//変更の反映
		System.out.println("[APP LOG] " + logDir.getAbsolutePath() + "/app.log");
		LOG.info(logDir.getAbsolutePath());
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent config) {
		
	}


}
