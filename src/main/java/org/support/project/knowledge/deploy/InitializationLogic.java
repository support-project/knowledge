package org.support.project.knowledge.deploy;

import java.util.TimeZone;

import org.support.project.common.exception.SystemException;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.di.Container;

public class InitializationLogic {
	/** ログ */
	private static Log LOG = LogFactory.getLog(InitializationLogic.class);
	
	public static InitializationLogic get() {
		return Container.getComp(InitializationLogic.class);
	}
	
	public void init() {
		LOG.debug("init database");
		// 内部的には、日付はGMTとして扱う
		TimeZone zone = TimeZone.getTimeZone("GMT");
		TimeZone.setDefault(zone);
		
		InitDB initDB = new InitDB();
		try {
			initDB.start();
		} catch (Exception e) {
			throw new SystemException(e);
		}
	}
	
	
}
