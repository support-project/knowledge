package org.support.project.web.batch;

import java.io.File;
import java.lang.invoke.MethodHandles;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.PropertyUtil;
import org.support.project.web.config.AppConfig;
import org.support.project.web.logic.DBConnenctionLogic;

/**
 * 定期的に呼び出されるJavaのバッチプログラムの基盤クラス
 * 
 * @author Koda
 */
public abstract class AbstractBat {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(MethodHandles.lookup());

    /**
     * バッチが出力するログの名称をセットする
     * 
     * @param logname log
     * 
     */
    public static void initLogName(String logname) {
        String logsPath = AppConfig.get().getLogsPath();
        File logDir = new File(logsPath);
        if (!logDir.exists()) {
            logDir.mkdirs();
        }
        Logger log = Logger.getRootLogger();
        FileAppender appendar = (FileAppender) log.getAppender("APP_FILEOUT");
        if (appendar != null) {
            File logfile = new File(logDir, logname);
            appendar.setFile(logfile.getAbsolutePath());
            appendar.activateOptions(); // 変更の反映
        }
    }

    /**
     * 設定の読み込み（環境変数の反映)
     * 
     * @param batName batName
     */
    protected static void configInit(String batName) {
        AppConfig.get();
        LOG.info(batName + " is start.");
        if (LOG.isDebugEnabled()) {
            String envValue = System.getenv(AppConfig.getEnvKey());
            LOG.debug("Env [" + AppConfig.getEnvKey() + "] is [" + envValue + "].");
            LOG.debug("Config :" + PropertyUtil.reflectionToString(AppConfig.get()));
        }
    }

    /**
     * バッチ終了時に情報を出力（デバッグ用）
     */
    protected static void finishInfo() {
        if (LOG.isDebugEnabled()) {
            String sysinfo = org.support.project.common.util.SystemUtils.systemInfo();
            LOG.debug(sysinfo);
        }
        LOG.info("Finished");
    }

    /**
     * コネクションの接続先がカスタマイズされていたら、バッチでもカスタマイズ先を参照する
     */
    public void dbInit() {
        DBConnenctionLogic.get().connectCustomConnection();
    }

}
