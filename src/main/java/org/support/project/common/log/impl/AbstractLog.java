package org.support.project.common.log.impl;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogLevel;

public abstract class AbstractLog implements Log {

	@Override
	public void trace(Object msg) {
		this.print(LogLevel.TRACE, msg);
	}

	@Override
	public void trace(Object msg, Throwable t) {
		this.print(LogLevel.TRACE, msg, t);
	}

	@Override
	public void debug(Object msg) {
		this.print(LogLevel.DEBUG, msg);
	}

	@Override
	public void debug(Object msg, Throwable t) {
		this.print(LogLevel.DEBUG, msg, t);
	}

	@Override
	public void info(Object msg) {
		this.print(LogLevel.INFO, msg);
	}

	@Override
	public void info(Object msg, Throwable t) {
		this.print(LogLevel.INFO, msg, t);
	}

	@Override
	public void warn(Object msg) {
		this.print(LogLevel.WARN, msg);
	}

	@Override
	public void warn(Object msg, Throwable t) {
		this.print(LogLevel.WARN, msg, t);
	}

	@Override
	public void error(Object msg) {
		this.print(LogLevel.ERROR, msg);
	}

	@Override
	public void error(Object msg, Throwable t) {
		this.print(LogLevel.ERROR, msg, t);
	}

	@Override
	public void fatal(Object msg) {
		this.print(LogLevel.FATAL, msg);
	}

	@Override
	public void fatal(Object msg, Throwable t) {
		this.print(LogLevel.FATAL, msg, t);
	}

	@Override
	public boolean isTraceEnabled() {
		return isEnabled(LogLevel.TRACE);
	}
	
	@Override
	public boolean isDebugEnabled() {
		return isEnabled(LogLevel.DEBUG);
	}

	@Override
	public boolean isInfoEnabled() {
		return isEnabled(LogLevel.INFO);
	}

	@Override
	public boolean isWarnEnabled() {
		return isEnabled(LogLevel.WARN);
	}

	@Override
	public boolean isErrorEnabled() {
		return isEnabled(LogLevel.ERROR);
	}

	@Override
	public boolean isFatalEnabled() {
		return isEnabled(LogLevel.FATAL);
	}
	
	@Override
	public void print(LogLevel level, Object msg) {
		print(level, msg, null);
	}
	
	


}
