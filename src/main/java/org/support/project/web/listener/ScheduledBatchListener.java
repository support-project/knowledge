package org.support.project.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.support.project.web.logic.ScheduledBatchLogic;

/**
 * 定期的にバッチ処理を呼び出すタイマーをセットするリスナークラス
 * @author Koda
 */
public class ScheduledBatchListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ScheduledBatchLogic logic = ScheduledBatchLogic.get();
        logic.scheduleInitialize();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ScheduledBatchLogic logic = ScheduledBatchLogic.get();
        logic.scheduleDestroy();
    }

}
