package org.support.project.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.logic.H2DBServerLogic;
import org.support.project.web.logic.DBConnenctionLogic;

/**
 * 組み込みDBをサーバーモードで起動するリスナ
 * 
 * @author Koda
 */
public class H2DBManagerListener implements ServletContextListener {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(H2DBManagerListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        LOG.trace("H2DBManagerListener#contextInitialized");
        if (DBConnenctionLogic.get().connectCustomConnection()) {
            // カスタムコネクションに接続したので、組み込みDBは起動しない
            return;
        }
        // カスタマイズコネクション設定が存在しないので、デフォルトの組み込みDBをサーバーモードで起動
        H2DBServerLogic logic = H2DBServerLogic.get();
        logic.start();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        H2DBServerLogic logic = H2DBServerLogic.get();
        if (logic.isActive()) {
            logic.stop();
        }
    }
}
