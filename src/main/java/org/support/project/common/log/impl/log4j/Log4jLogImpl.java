package org.support.project.common.log.impl.log4j;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogLevel;
import org.support.project.common.util.PropertyUtil;

/**
 * Logging on log4j
 * 
 * @author Koda
 *
 */
class Log4jLogImpl implements Log {
    /** The fully qualified name of the Log4JLogger class. */
    private static final String FQCN = Log4jLogImpl.class.getName();

    /** Log to this logger */
    private transient volatile Logger logger = null;

    /**
     * constractor
     * 
     * @param logger
     *            logger
     */
    Log4jLogImpl(Logger logger) {
        this.logger = logger;
    }

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
        if (LogLevel.TRACE == level) {
            if (t == null) {
                logger.trace(str);
            } else {
                logger.trace(str, t);
            }
        } else {
            if (t == null) {
                logger.log(FQCN, getLevel(level), str, null);
            } else {
                removeDisuseStackTraceInfo(t);
                logger.log(FQCN, getLevel(level), str, t);
            }
        }

    }
    /**
     * 不要なログを削除
     * @param t Throwable
     */
    private void removeDisuseStackTraceInfo(Throwable t) {
        // DIを使っていると、不要なinvoke系のメソッドがログに出すぎて邪魔なので消す
        List<StackTraceElement> list = new ArrayList<>();
        StackTraceElement[] stackTraceElements = t.getStackTrace();
        for (StackTraceElement stackTraceElement : stackTraceElements) {
            // String str = stackTraceElement.toString();
            if (!stackTraceElement.toString().startsWith("sun.reflect.")
                    && !stackTraceElement.toString().startsWith("java.lang.reflect.Method.invoke")
                    && !stackTraceElement.toString().startsWith("org.support.project.aop.Intercepter.invoke")
                    && stackTraceElement.toString().indexOf("_$$_") == -1) {
                list.add(stackTraceElement);
            }
        }
        StackTraceElement[] elements = list.toArray(new StackTraceElement[0]);
        t.setStackTrace(elements);
        if (t.getCause() != null) {
            removeDisuseStackTraceInfo(t.getCause());
        }
    }

    @Override
    public boolean isEnabled(LogLevel level) {
        if (LogLevel.TRACE == level) {
            return logger.isTraceEnabled();
        } else {
            return logger.isEnabledFor(getLevel(level));
        }
    }
    /**
     * get priority
     * @param level loglevel
     * @return priority
     */
    private Priority getLevel(LogLevel level) {
        Priority priority = Priority.INFO;
        if (level == LogLevel.TRACE) {
            priority = Priority.DEBUG;
        } else if (level == LogLevel.DEBUG) {
            priority = Priority.DEBUG;
        } else if (level == LogLevel.INFO) {
            priority = Priority.INFO;
        } else if (level == LogLevel.WARN) {
            priority = Priority.WARN;
        } else if (level == LogLevel.ERROR) {
            priority = Priority.ERROR;
        } else if (level == LogLevel.FATAL) {
            priority = Priority.FATAL;
        }
        return priority;
    }

}
