package org.support.project.web.logic;

import java.io.File;
import java.lang.invoke.MethodHandles;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.web.config.AppConfig;

/**
 * バッチプログラムを定期的に呼び出すテスト実行クラス
 * @author Koda
 */
public class ScheduledBatchLogicCall {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(MethodHandles.lookup());
    /**
     * テスト開始
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        String logsPath = AppConfig.get().getLogsPath();
        File logDir = new File(logsPath);
        if (!logDir.exists()) {
            logDir.mkdirs();
        }
        Logger log = Logger.getRootLogger();
        FileAppender appendar = (FileAppender) log.getAppender("APP_FILEOUT");
        File logfile = new File(logDir, "/batch.log");
        appendar.setFile(logfile.getAbsolutePath());
        appendar.activateOptions(); //変更の反映
        
        ScheduledBatchLogic logic = ScheduledBatchLogic.get();
        logic.scheduleInitialize();
        
        int count = 0;
        while (count < 10) {
            LOG.info("バッチが起動されるのを待ってます");
            Thread.sleep(1000 * 60);
            count++;
        }
        
        logic.scheduleDestroy();
    }

}
