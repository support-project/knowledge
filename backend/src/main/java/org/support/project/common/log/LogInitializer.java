package org.support.project.common.log;

/**
 * Initializer of logging
 * @author Koda
 *
 */
public interface LogInitializer {
	
	/**
	 * ログを取得
	 * @param type type
	 * @return Log
	 */
	Log createLog(Class<?> type);
	
	
}
