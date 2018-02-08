package org.support.project.common.util;

import java.lang.invoke.MethodHandles;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;


public class DebugUtils {
    /** ログ */
    private static Log LOG = LogFactory.getLog(MethodHandles.lookup());
    
    private static final int LEVEL = 15;
    
    public static final void debugInfoCalled() {
        if (LOG.isDebugEnabled()) {
            Throwable t = new Throwable();
            StackTraceElement[] elements = t.getStackTrace();
            int num = LEVEL;
            if (elements.length < num) {
                num = elements.length;
            }
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < num; i++) {
                StackTraceElement element = elements[i];
                String className = element.getClassName();
                builder.append(className + "/" + element.getMethodName() + "\n");
            }
            LOG.debug("called\n" + builder.toString());
        }
    }
    
    
}
