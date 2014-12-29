package redcomet.knowledge.deploy;

import java.util.TimeZone;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import redcomet.common.exception.SystemException;
import redcomet.common.log.Log;
import redcomet.common.log.LogFactory;
import redcomet.ormapping.connection.ConnectionManager;

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
		InitDB initDB = new InitDB();
		try {
			initDB.start();
		} catch (Exception e) {
			throw new SystemException(e);
		}
		// 内部的には、日付はGMTとして扱う
		TimeZone zone = TimeZone.getTimeZone("GMT");
		TimeZone.setDefault(zone);
		
		
	}

}
