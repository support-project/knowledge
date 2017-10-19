package org.support.project.common.log.impl.log4j;

import org.apache.log4j.Logger;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogInitializer;

public class Log4jLogInitializerImpl implements LogInitializer {

	@Override
	public Log createLog(Class<?> type) {
		Logger logger = Logger.getLogger(type.getCanonicalName());
		Log log = new Log4jLogImpl(logger);
		return log;
	}


}
