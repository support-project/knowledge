package org.support.project.ormapping.connection;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.support.project.common.exception.SystemException;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.logic.H2DBServerLogic;
import org.support.project.common.util.StringUtils;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.ormapping.config.Connection.ConfigType;
import org.support.project.ormapping.config.ConnectionConfig;
import org.support.project.ormapping.config.ConnectionConfigLoader;
import org.support.project.ormapping.config.ORMappingParameter;
import org.support.project.ormapping.exception.ORMappingException;
import org.support.project.ormapping.transaction.TransactionManager;

/**
 * コネクションを管理するクラス
 */
@DI(instance = Instance.Singleton)
public final class ConnectionManager {
    /** ログ */
    private static Log logger = LogFactory.getLog(MethodHandles.lookup());

    /** シングルトンで管理されたconnectionManagerのインスタンス */
    private static ConnectionManager connectionManager = null;

    /** コネクションプールを設定名毎に保持するマップ */
    private Map<String, ConnectionPoolImpl> connectionPools = null;

    /** コネクション取得の際に、名称を指定しなかった場合のデフォルトの名称 */
    private String name = ORMappingParameter.DEFAULT_CONNECTION_NAME;

    /** 組み込みDBを使っているか */
    private boolean useEmbdb = false;

    /**
     * コンストラクタ
     * 
     * @throws ORMappingException
     *             ORMappingException
     */
    private ConnectionManager() throws ORMappingException {
        connectionPools = new HashMap<>();
        H2DBServerLogic h2dbServerLogic = H2DBServerLogic.get();
        if (h2dbServerLogic.isActive()) {
            useEmbdb = true;
        }
    }

    /**
     * インスタンスの取得
     * 
     * @return ConnectionManagerのインスタンス
     * @throws ORMappingException
     *             ORMappingException
     */
    public static synchronized ConnectionManager getInstance() throws ORMappingException {
        if (connectionManager == null) {
            connectionManager = new ConnectionManager();
        }
        return connectionManager;
    }

    /**
     * コネクションプールを生成する
     * 
     * @param name
     *            name
     * @return ConnectionPool
     */
    public ConnectionPoolImpl getConnectionPool(String name) {
        if (!connectionPools.containsKey(name)) {
            throw new ORMappingException("errors.or.not.exist", name);
        }
        ConnectionPoolImpl connectionPool = connectionPools.get(name);
        return connectionPool;
    }

    /**
     * コネクションの設定が有効かどうかチェックする
     * 
     * @param config
     *            ConnectionConfig
     * @return check result
     */
    public boolean checkConnectionConfig(ConnectionConfig config) {
        try {
            ConnectionPoolImpl connectionPool = new ConnectionPoolImpl(config);
            Connection connection = connectionPool.getConnection();
            connection.close();
            connectionPool.release();
            connectionPool = null;
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * コネクションの設定を読み込み、コネクションを設定する
     * 
     * @param name
     *            config name
     * @param configType
     *            ConfigType
     * @param configFileName
     *            configFileName
     */
    public void addConnectionConfig(String name, ConfigType configType, String configFileName) {
        ConnectionConfigLoader configLoader = Container.getComp(configType.name(), ConnectionConfigLoader.class);
        ConnectionConfig config = configLoader.load(configFileName);
        if (StringUtils.isEmpty(name)) {
            name = config.getName();
        }
        if (StringUtils.isEmpty(name)) {
            name = ORMappingParameter.DEFAULT_CONNECTION_NAME;
        }
        this.name = name; // 追加したコネクションの設定をデフォルトのコネクションとする(1つしか使わないことがほとんどであるため)

        if (!connectionPools.containsKey(name)) {
            ConnectionPoolImpl connectionPool = new ConnectionPoolImpl(config);
            connectionPools.put(name, connectionPool);
            logger.debug("Connection config added. [name]=" + name);
        }
    }

    /**
     * コネクションを設定する
     * 
     * @param config ConnectionConfig
     */
    public void addConnectionConfig(ConnectionConfig config) {
        String name = config.getName();
        if (StringUtils.isEmpty(name)) {
            name = ORMappingParameter.DEFAULT_CONNECTION_NAME;
        }
        this.name = name; // 追加したコネクションの設定をデフォルトのコネクションとする(1つしか使わないことがほとんどであるため)

        if (!connectionPools.containsKey(name)) {
            ConnectionPoolImpl connectionPool = new ConnectionPoolImpl(config);
            connectionPools.put(name, connectionPool);
            logger.debug("Connection config added. [name]=" + name);
        }
    }
    
    /**
     * 設定していたコネクションの設定を削除
     */
    public void removeDefaultConnectionConfig() {
        removeConnectionConfig(this.name);
    }

    /**
     * 設定していたコネクションの設定を削除
     * 
     * @param config ConnectionConfig
     */
    public void removeConnectionConfig(ConnectionConfig config) {
        String name = config.getName();
        removeConnectionConfig(name);
    }

    /**
     * コネクションの設定を削除
     * 
     * @param name config name
     */
    public void removeConnectionConfig(String name) {
        if (StringUtils.isEmpty(name)) {
            name = ORMappingParameter.DEFAULT_CONNECTION_NAME;
        }
        ConnectionPoolImpl pool = connectionPools.get(name);
        if (pool != null) {
            pool.release();
        }
        connectionPools.remove(name);

        TransactionManager transactionManager = Container.getComp(TransactionManager.class);
        try {
            transactionManager.release(name);
        } catch (SQLException e) {
            throw new SystemException(e);
        }

        logger.debug("Connection config removed. [name]=" + name);
        if (this.name.equals(name)) {
            this.name = "";
        }
    }

    /**
     * コネクションの設定が複数あるとき、デフォルトのコネクションを指定する
     * 
     * @param name name
     */
    public void setDefaultConnectionName(String name) {
        if (name.equals(this.name)) {
            // 既にデフォルトは同じ
            return;
        }
        logger.info("change default connection. [form] " + this.name + "  [to] " + name);
        if (!connectionPools.containsKey(name)) {
            throw new ORMappingException("conaction config is not exists. [name] = " + name);
        }
        this.name = name;
    }

    /**
     * コネクションの設定が複数あるとき、デフォルトのコネクションを取得する
     * 
     * @return name
     */
    public String getDefaultConnectionName() {
        return this.name;
    }

    /**
     * 指定の名称のコネクションの設定が存在するか
     * 
     * @param name name
     * @return check result
     */
    public boolean isExist(String name) {
        return connectionPools.containsKey(name);
    }

    /**
     * コネクションを取得
     * 
     * @return Connection
     */
    public synchronized Connection getConnection() {
        return getConnection(name);
    }

    /**
     * コネクションを取得
     * 
     * @param name name
     * @return Connection
     */
    public synchronized Connection getConnection(String name) {
        if (useEmbdb) {
            H2DBServerLogic h2dbServerLogic = H2DBServerLogic.get();
            if (!h2dbServerLogic.isActive()) {
                // サーバーが停止しているが、とりあえず操作出来るようにした
                logger.warn("Database is not active");
                return new StopConnection();
            }
        }
        if (StringUtils.isEmpty(name)) {
            // 有効なコネクション設定が存在しないが、とりあえず操作が出来るようにラッパーを返す
            logger.warn("Database config is not loaded.");
            return new StopConnection();
        }

        if (connectionPools.isEmpty()) {
            logger.debug("connection is empty. set default connection.");
            ConnectionConfigLoader loader = Container.getComp("XML", ConnectionConfigLoader.class);
            ConnectionConfig config = loader.load(ORMappingParameter.CONNECTION_SETTING);
            addConnectionConfig(config);
        }
        if (logger.isTraceEnabled()) {
            logger.trace("get connection " + name);
        }
        TransactionManager transactionManager = Container.getComp(TransactionManager.class);
        if (!transactionManager.isStarted(name)) {
            // トランザクション管理の外で、コネクション取得が呼ばれたため、
            // コネクションプールから直接コネクションを渡す
            return getConnectionPool(name).getConnection();
        } else {
            return transactionManager.getConnection(name);
        }
    }

    /**
     * スキーマを取得
     * 
     * @return schema
     */
    public String getSchema() {
        return getSchema(name);
    }

    /**
     * スキーマを取得
     * 
     * @param name name
     * @return schema
     */
    public String getSchema(String name) {
        String schema = getConnectionPool(name).getConfig().getSchema();
        if (StringUtils.isEmpty(schema)) {
            return "%";
        }
        return schema;
    }

    /**
     * JDBCドライバークラスを取得
     * 
     * @return jdbc name
     */
    public String getDriverClass() {
        return getDriverClass(name);
    }

    /**
     * JDBCドライバークラスを取得
     * 
     * @param name config name
     * @return jdbc name
     */
    public String getDriverClass(String name) {
        String driverclass = getConnectionPool(name).getConfig().getDriverClass();
        return driverclass;
    }

    /**
     * 管理しているコネクションを全て解放する
     */
    public void release() {
        Iterator<String> keys = connectionPools.keySet().iterator();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            ConnectionPoolImpl connectionPool = connectionPools.get(key);
            connectionPool.release();
            logger.trace("Connection : " + key + "was released.");
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#finalize()
     */
    @Override
    protected void finalize() throws Throwable {
        logger.trace("finalize");
        release();
        destroy();
        super.finalize();
    }

    /**
     * destroy
     */
    public void destroy() {
        // JDBC ドライバの参照を解除
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {
                logger.error("DeregisterDriver ERROR ", e);
            }
        }
    }

}
