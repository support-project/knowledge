package org.support.project.knowledge.bat;

import java.lang.invoke.MethodHandles;

import org.apache.commons.lang.ClassUtils;
import org.h2.tools.Server;
import org.support.project.common.config.ConfigLoader;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.knowledge.logic.DataTransferLogic;
import org.support.project.ormapping.config.ConnectionConfig;
import org.support.project.web.config.AppConfig;
import org.support.project.web.logic.DBConnenctionLogic;

public class DataTransferBat extends AbstractBat implements Runnable {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(MethodHandles.lookup());

    private boolean runing = false;
    private boolean serverStarted = false;

    public static void main(String[] args) throws Exception {
        try {
            initLogName("DataTransferBat.log");
            configInit(ClassUtils.getShortClassName(DataTransferBat.class));
            DataTransferLogic.get().requestTransfer();
            DataTransferBat bat = new DataTransferBat();
            bat.dbInit();
            bat.start();
            
        } catch (Exception e) {
            LOG.error("any error", e);
            throw e;
        }
    }

    @Override
    public void run() {
        // データ取得元の組み込みDBを起動（既に起動している場合起動しない）
        runing = true;
        try {
            AppConfig appConfig = ConfigLoader.load(AppConfig.APP_CONFIG, AppConfig.class);
            String[] parms = { "-tcp", "-baseDir", appConfig.getDatabasePath() };
            
            LOG.info("start h2 database");
            Server server = Server.createTcpServer(parms);
            server.start();
            // System.out.println("Database start...");
            serverStarted = true;
            while (runing) {
                Thread.sleep(1000);
            }
            server.stop();
        } catch (Exception e) {
            LOG.error(e);
        }
        LOG.info("Database stop.");
    }

    /**
     * 
     * @throws Exception
     */
    private void start() throws Exception {
        if (DataTransferLogic.get().isTransferRequested() || DataTransferLogic.get().isTransferBackRequested()) {
            // 多重起動チェック
            if (DataTransferLogic.get().isTransferStarted()) {
                LOG.info("ALL Ready started.");
                return;
            }

            // DBを起動
            Thread thread = new Thread(this);
            thread.start();
            try {
                // サーバーが起動するまで待機
                while (!serverStarted) {
                    Thread.sleep(1000);
                }
                // コネクションの設定を読み込み
                ConnectionConfig defaultConnection = DBConnenctionLogic.get().getDefaultConnectionConfig();
                ConnectionConfig customConnection = DBConnenctionLogic.get().getCustomConnectionConfig();
                // データ移行を実行（すごく時間がかかる可能性あり）
                if (DataTransferLogic.get().isTransferBackRequested()) {
                    DataTransferLogic.get().transferData(customConnection, defaultConnection);
                } else {
                    DataTransferLogic.get().transferData(defaultConnection, customConnection);
                }
            } catch (Exception e) {
                LOG.error("ERROR", e);
            } finally {
                // データ移行終了
                DataTransferLogic.get().finishTransfer();
                // DBを停止
                runing = false;
                thread.join();
            }
        }
    }

}
