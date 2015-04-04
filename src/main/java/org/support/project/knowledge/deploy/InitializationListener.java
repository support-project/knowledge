package org.support.project.knowledge.deploy;

import java.io.File;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.support.project.common.config.ConfigLoader;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.knowledge.config.AppConfig;
import org.support.project.ormapping.connection.ConnectionManager;

public class InitializationListener implements ServletContextListener {
	/** ログ */
	private static Log LOG = LogFactory.getLog(InitializationListener.class);

	@Override
	public void contextDestroyed(ServletContextEvent contextEvent) {
		LOG.debug("contextDestroyed");
		ConnectionManager connectionManager = ConnectionManager.getInstance();
		connectionManager.release();
		connectionManager.destroy();
	}

	@Override
	public void contextInitialized(ServletContextEvent contextEvent) {
		LOG.debug("contextInitialized");
		InitializationLogic.get().init();
		
		// 添付ファイル格納ディレクトリ（テンポラリディレクトリ）が存在しなければ生成
		AppConfig appConfig = ConfigLoader.load(AppConfig.APP_CONFIG, AppConfig.class);
		String tmpDir = appConfig.getTmpPath();
		File tmp = new File(tmpDir);
		if (!tmp.exists()) {
			tmp.mkdirs();
			LOG.info("tmp directory created." + tmpDir);
		}
		String idxDir = appConfig.getIndexPath();
		File idx = new File(idxDir);
		if (!idx.exists()) {
			idx.mkdirs();
			LOG.info("idx directory created." + idxDir);
		}
		LOG.info("Knowledge start on '" + contextEvent.getServletContext().getRealPath("/") + "'");
	}

}
