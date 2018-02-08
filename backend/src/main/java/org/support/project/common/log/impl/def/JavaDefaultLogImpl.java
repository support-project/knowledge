package org.support.project.common.log.impl.def;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.support.project.common.log.LogLevel;
import org.support.project.common.log.impl.AbstractLog;
import org.support.project.common.util.PropertyUtil;

class JavaDefaultLogImpl extends AbstractLog {
	
	private Logger logger;
	
	public JavaDefaultLogImpl(Logger logger) {
		this.logger = logger;
	}

	@Override
	public void print(LogLevel level, Object msg, Throwable t) {
		String str = null;
		if (msg != null) {
			if (msg instanceof String) {
				str = (String) msg;
			} else {
				str = PropertyUtil.reflectionToString(msg);
			}
		}
		
		if (t == null) {
			logger.log(getLevel(level), str);
		} else {
			logger.log(getLevel(level), str, t);
		}
	}

	@Override
	public boolean isEnabled(LogLevel level) {
		return logger.isLoggable(getLevel(level));
	}

	
	private Level getLevel(LogLevel level) {
		Level jlevel = Level.INFO;
		if (level == LogLevel.TRACE) {
			jlevel = Level.FINE;
		} else if (level == LogLevel.DEBUG) {
			jlevel = Level.CONFIG;
		} else if (level == LogLevel.INFO) {
			jlevel = Level.INFO;
		} else if (level == LogLevel.WARN) {
			jlevel = Level.WARNING;
		} else if (level == LogLevel.ERROR) {
			jlevel = Level.SEVERE;
		} else if (level == LogLevel.FATAL) {
			jlevel = Level.SEVERE;
		}
		return jlevel;
	}
	



}
