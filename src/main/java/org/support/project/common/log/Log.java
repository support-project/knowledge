package org.support.project.common.log;

public interface Log {
	
	void trace(Object msg);
	void trace(Object msg, Throwable t);
	
	void debug(Object msg);
	void debug(Object msg, Throwable t);
	
	void info(Object msg);
	void info(Object msg, Throwable t);
	
	void warn(Object msg);
	void warn(Object msg, Throwable t);
	
	void error(Object msg);
	void error(Object msg, Throwable t);
	
	void fatal(Object msg);
	void fatal(Object msg, Throwable t);
	
	boolean isTraceEnabled();
	boolean isDebugEnabled();
	boolean isInfoEnabled();
	boolean isWarnEnabled();
	boolean isErrorEnabled();
	boolean isFatalEnabled();
	
	void print(LogLevel level, Object msg);
	void print(LogLevel level, Object msg, Throwable t);
	
	boolean isEnabled(LogLevel level);
	
}
