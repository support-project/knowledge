package org.support.project.common.log;

import java.util.HashMap;
import java.util.Map;

import org.support.project.common.log.impl.def.JavaDefaultLogInitializerImpl;
import org.support.project.common.log.impl.log4j.Log4jLogInitializerImpl;

/**
 * ログを取得するクラス
 * JavaAPIの標準のログを使いやすくするために作成したもの
 * 設定ファイルを読み込みLoggerのインスタンスを生成する
 */
public class LogFactory {
    private static boolean init = false;

    private static Map<Class, Log> logMap;

    private static final int LOG_MODE_DEFAULT = 0;

    private static final int LOG_MODE_LOG4J = 1;

    private static int logmode = LOG_MODE_DEFAULT;

    private static LogInitializer logInitializer = null;

    public static final Log getLog(Class<?> clazz) {
        if (!init) {
            logMap = new HashMap<Class, Log>();
            try {
                Class.forName("org.apache.log4j.Logger");
                // Log4Jが存在する為、Log4Jを利用する
                logmode = LOG_MODE_LOG4J;
                logInitializer = new Log4jLogInitializerImpl();
            } catch (ClassNotFoundException e) {
                // Log4Jが存在しないため、Javaの標準のログを利用する
                logmode = LOG_MODE_DEFAULT;
                logInitializer = new JavaDefaultLogInitializerImpl();
            }
            init = true;
        }

        if (!logMap.containsKey(clazz)) {
            Log log = logInitializer.createLog(clazz);
            logMap.put(clazz, log);
        }
        return logMap.get(clazz);
    }

}
