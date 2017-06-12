package org.support.project.knowledge.bat;

import java.util.TimeZone;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.PropertyUtil;
import org.support.project.knowledge.config.AppConfig;
import org.support.project.web.logic.DBConnenctionLogic;

public abstract class AbstractBat {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(AbstractBat.class);

    public static void initLogName(String logname) {
        Logger log = Logger.getRootLogger();
        FileAppender appendar = (FileAppender) log.getAppender("APP_FILEOUT");
        appendar.setFile(logname);
        appendar.activateOptions(); // 変更の反映
    }

    protected static void configInit(String batName) {
        // 内部的には、日付はGMTとして扱う
        TimeZone zone = TimeZone.getTimeZone("GMT");
        TimeZone.setDefault(zone);
        
        AppConfig.get();
        String envValue = System.getenv(AppConfig.getEnvKey());
        LOG.info(batName + " is start.");
        if (LOG.isDebugEnabled()) {
            LOG.debug("Env [" + AppConfig.getEnvKey() + "] is [" + envValue + "].");
            LOG.debug("Config :" + PropertyUtil.reflectionToString(AppConfig.get()));
        }
    }

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

    protected void send(String msg) {
        LOG.info("[SEND]" + msg);
    }

}
