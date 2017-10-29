package org.support.project.knowledge.deploy;

import java.io.File;
import java.util.TimeZone;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.support.project.common.exception.SystemException;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.knowledge.config.AnalyticsConfig;
import org.support.project.knowledge.config.AppConfig;
import org.support.project.knowledge.config.SystemConfig;
import org.support.project.knowledge.logic.ServiceConfigLogic;
import org.support.project.ormapping.connection.ConnectionManager;
import org.support.project.web.dao.SystemAttributesDao;
import org.support.project.web.entity.SystemAttributesEntity;

public class InitializationListener implements ServletContextListener {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(InitializationListener.class);

    @Override
    public void contextDestroyed(ServletContextEvent contextEvent) {
        LOG.debug("contextDestroyed");
        ConnectionManager connectionManager = ConnectionManager.getInstance();
        connectionManager.release();
        connectionManager.destroy();
    }

    @Override
    public void contextInitialized(ServletContextEvent contextEvent) {
        LOG.debug("contextInitialized");
        // DBの初期化処理
        TimeZone zone = TimeZone.getTimeZone("GMT");
        TimeZone.setDefault(zone);
        InitDB initDB = new InitDB();
        String version;
        try {
            version = initDB.init();
        } catch (Exception e) {
            throw new SystemException(e);
        }

        // 添付ファイル格納ディレクトリ（テンポラリディレクトリ）が存在しなければ生成
        AppConfig appConfig = AppConfig.get();
        String tmpDir = appConfig.getTmpPath();
        File tmp = new File(tmpDir);
        if (!tmp.exists()) {
            tmp.mkdirs();
            LOG.info("tmp directory created." + tmpDir);
        }
        String idxDir = appConfig.getIndexPath();
        File idx = new File(idxDir);
        if (!idx.exists()) {
            idx.mkdirs();
            LOG.info("idx directory created." + idxDir);
        }
        String path = contextEvent.getServletContext().getRealPath("/");
        LOG.info("Knowledge start");
        LOG.info("knowledge install path: '" + path + "'");
        LOG.info("knowledge home path: '" + appConfig.getBasePath() + "'");
        AppConfig.setWebRealPath(path);

        SystemAttributesDao dao = SystemAttributesDao.get();
        SystemAttributesEntity config = dao.selectOnKey(SystemConfig.ANALYTICS, AppConfig.get().getSystemName());
        if (config != null) {
            // 設定を毎回DBから取得するのはパフォーマンス面で良くないので、メモリに保持する
            AnalyticsConfig.get().setAnalyticsScript(config.getConfigValue());
        }
        
        // トップページ情報を取得
        ServiceConfigLogic.get().load();
        
        if (!InitDB.CURRENT.equals(version)) {
            // Databaseのマイグレーションが必要
            appConfig.setMaintenanceMode(true);
            LOG.warn("The database version of this service is " + version + ". but lastest version is " + InitDB.CURRENT + ". Then this service start MaintenanceMode");
        }
    }

}
