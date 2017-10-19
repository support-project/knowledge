package org.support.project.common.log;


public enum LogLevel {
	TRACE, DEBUG, INFO, WARN, ERROR, FATAL;
	
	public int getValue() {
		return ordinal();
	}
	
	public LogLevel getType(int type) {
		LogLevel[] values = values();
		return values[type];
	}
	
}
