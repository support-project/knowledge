package org.support.project.web.listener;

import java.util.TimeZone;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.support.project.common.exception.SystemException;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.StringUtils;
import org.support.project.ormapping.connection.ConnectionManager;
import org.support.project.web.config.AnalyticsConfig;
import org.support.project.web.config.AppConfig;
import org.support.project.web.config.WebConfig;
import org.support.project.web.dao.SystemAttributesDao;
import org.support.project.web.deploy.InitDB;
import org.support.project.web.entity.SystemAttributesEntity;

/**
 * 起動時にデータベースのマイグレーションを行うリスナクラス
 * @author Koda
 */
public class DBMigrateListener implements ServletContextListener {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(DBMigrateListener.class);

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
        InitDB initDB = new InitDB();
        String migratePackage = AppConfig.get().getMigratePackage();
        if (StringUtils.isEmpty(migratePackage)) {
            migratePackage = initDB.getClass().getPackage().getName() + ".migrate";
        }
        initDB.setMigratePackage(migratePackage);
        try {
            initDB.start();
        } catch (Exception e) {
            throw new SystemException(e);
        }
        // 内部的には、日付はGMTとして扱う
        TimeZone zone = TimeZone.getTimeZone("GMT");
        TimeZone.setDefault(zone);
        
        // Analytics用の設定をロード
        SystemAttributesDao dao = SystemAttributesDao.get();
        SystemAttributesEntity config = dao.selectOnKey(WebConfig.KEY_ANALYTICS_SCRIPT, AppConfig.get().getSystemName());
        if (config != null) {
            // 設定を毎回DBから取得するのはパフォーマンス面で良くないので、メモリに保持する
            AnalyticsConfig.get().setAnalyticsScript(config.getConfigValue());
        }
    }

}
