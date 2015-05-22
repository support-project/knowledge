package org.support.project.knowledge.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.support.project.knowledge.config.AppConfig;

public class GlobalInitializationListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent config) {
		AppConfig.initEnvKey("KNOWLEDGE_HOME");
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent config) {
		
	}


}
