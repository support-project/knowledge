package org.support.project.web.logic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.support.project.common.config.ConfigLoader;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.ormapping.config.ConnectionConfig;
import org.support.project.ormapping.config.ConnectionConfigLoader;
import org.support.project.ormapping.config.ORMappingParameter;
import org.support.project.ormapping.connection.ConnectionManager;
import org.support.project.web.config.AppConfig;

@DI(instance = Instance.Singleton)
public class DBConnenctionLogic {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(DBConnenctionLogic.class);

    private static final String CONNECTION_CONFIG = "custom_connection.xml";

    /**
     * Get instance
     * 
     * @return instance
     */
    public static DBConnenctionLogic get() {
        return Container.getComp(DBConnenctionLogic.class);
    }

    /**
     * コネクションの接続先がカスタマイズされていたら、バッチでもカスタマイズ先を参照する
     * 
     * @return result
     */
    public boolean connectCustomConnection() {
        ConnectionConfig config = getCustomConnectionConfig();
        if (config != null) {
            // 設定が正しく読み込めれば、それを使う
            LOG.info("Custom connection setting is exists.");
            ConnectionManager.getInstance().addConnectionConfig(config);
            return true;
        }
        // カスタムコネクションには接続しなかった（デフォルトのコネクションに接続する）
        return false;
    }

    /**
     * カスタムコネクションの設定を取得 カスタムコネクションの設定が無い場合、nullになる
     * 
     * @return ConnectionConfig of custom connection
     */
    public ConnectionConfig getCustomConnectionConfig() {
        File config = getCustomConnectionConfigPath();
        if (config.exists() && config.canRead()) {
            ConnectionConfigLoader loader = Container.getComp("XML", ConnectionConfigLoader.class);
            FileInputStream in = null;
            try {
                in = new FileInputStream(config);
                ConnectionConfig connectionConfig = loader.load(in);
                return connectionConfig;
            } catch (FileNotFoundException e) {
                LOG.error(e);
            } finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        LOG.error(e);
                    }
                }
            }
        }
        return null;
    }

    /**
     * カスタムコネクション設定のファイルのパスを取得
     * 
     * @return file path of custom setting
     */
    public File getCustomConnectionConfigPath() {
        AppConfig appConfig = ConfigLoader.load(AppConfig.APP_CONFIG, AppConfig.class);
        File base = new File(appConfig.getBasePath());
        File config = new File(base, CONNECTION_CONFIG);
        return config;
    }

    /**
     * ファイルを削除
     */
    public void removeCustomConnectionConfig() {
        File config = getCustomConnectionConfigPath();
        if (config.exists() && config.canRead()) {
            config.delete();
        }
    }

    /**
     * デフォルトのコネクション設定を返す
     * 
     * @return ConnectionConfig
     */
    public ConnectionConfig getDefaultConnectionConfig() {
        ConnectionConfigLoader loader = Container.getComp("XML", ConnectionConfigLoader.class);
        ConnectionConfig connectionConfig = loader.load(ORMappingParameter.CONNECTION_SETTING);
        return connectionConfig;
    }

}
