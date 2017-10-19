package org.support.project.ormapping.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.StringUtils;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.ormapping.config.ConnectionConfig;
import org.support.project.ormapping.config.ORMappingParameter;
import org.support.project.ormapping.exception.ORMappingException;

@DI(instance = Instance.Singleton)
public class ConnectionPoolImpl implements ConnectionPool {
    /** ログ */
    private static Log logger = LogFactory.getLog(ConnectionPoolImpl.class);

    /** コネクションの設定 */
    private ConnectionConfig config;

    /** フリーになっているコネクション */
    private List<PoolableConnectionWrapper> freeConnections;
    private int checkedOut;
    private int retry = 100;
    private int waitamoment = 10;

    /** 新規に作成したコネクション数 */
    private int count = 0;

    public ConnectionPoolImpl(ConnectionConfig config) {
        super();
        this.config = config;

        // Nameが指定されていなければ、デフォルト値を設定する
        if (StringUtils.isEmpty(config.getName())) {
            config.setName(ORMappingParameter.DEFAULT_CONNECTION_NAME);
        }

        freeConnections = Collections.synchronizedList(new ArrayList<PoolableConnectionWrapper>());
        checkedOut = 0;
        try {
            Class.forName(config.getDriverClass());
        } catch (ClassNotFoundException e) {
            throw new ORMappingException("errors.or.instantiation", e);
        }

    }

    public synchronized Connection getConnection() {
        if (logger.isTraceEnabled()) {
            logger.trace(config);
        }
        return getConnection(waitamoment, retry);
    }

    private synchronized Connection getConnection(int waitamoment, int retry) {
        Connection con;
        int count = 0;
        while ((con = getCon()) == null) {
            try {
                wait(waitamoment);
            } catch (InterruptedException e) {
                throw new ORMappingException("", e);
            }
            count++;
            if (count > retry) {
                // return null;
                throw new ORMappingException("errors.or.get.connection");
            }
        }
        return con;
    }

    private synchronized Connection getCon() {
        logger.trace("Get Connection start.");
        Connection con = null;
        if (freeConnections.size() > 0) {
            // Pick the first Connection in the Vector
            // to get round-robin usage
            // VectorはレガシーなのでListに変更
            con = freeConnections.get(0);
            freeConnections.remove(0);
            logger.trace("Free Connection is Geted.");
            try {
                if (con.isClosed()) {
                    logger.trace("Removed bad connection from " + this.config.getName());
                    // Try again recursively
                    con = getCon();
                }
            } catch (SQLException e) {
                logger.error("Removed bad connection from " + this.config.getName());
                // Try again recursively
                // con = getCon();
                throw new ORMappingException(e);
            }
        } else if (this.config.getMaxConn() == 0 || checkedOut < this.config.getMaxConn()) {
            logger.trace("Free Connection is not exists. Create new Connection.");
            con = newConnection();
        }
        if (con != null) {
            checkedOut++;
        }

        if (con != null) {
            logger.trace("Connection is Geted." + con.toString());
        }
        return con;
    }

    private PoolableConnectionWrapper newConnection() {
        Connection con = null;
        try {
            if (StringUtils.isEmpty(this.config.getUser())) {
                con = DriverManager.getConnection(this.config.getURL());
            } else {
                con = DriverManager.getConnection(this.config.getURL(), this.config.getUser(), this.config.getPassword());
            }
            logger.trace("Created a new connection in pool " + this.config.getName());
        } catch (SQLException e) {
            logger.error("Can't create a new connection for " + this.config.getURL(), e);
            throw new ORMappingException("errors.or.create.connection", e);
        }

        if (con != null) {
            try {
                con.setAutoCommit(this.config.isAutocommit());
            } catch (SQLException e) {
                throw new ORMappingException("errors.or.set.auto.commit");
            }
        }
        logger.trace("Create connection : " + count + " : " + con.toString());
        PoolableConnectionWrapper wrapper = new PoolableConnectionWrapper(this, con, count++);
        return wrapper;
    }

    public synchronized void freeConnection(Connection con) {
        // Put the connection at the end of the Vector
        freeConnections.add((PoolableConnectionWrapper) con);
        checkedOut--;
        notifyAll();
        logger.trace("Connection is Free." + con.toString());
    }

    public synchronized void release() {
        Iterator<PoolableConnectionWrapper> allConnections = freeConnections.iterator();
        while (allConnections.hasNext()) {
            Connection con = allConnections.next();
            try {
                if (con instanceof PoolableConnectionWrapper) {
                    ((PoolableConnectionWrapper) con).closeConnection();
                } else {
                    con.close();
                }
                logger.trace("Closed connection for pool " + this.config.getName());
            } catch (SQLException e) {
                logger.error("Can't close connection for pool " + this.config.getName());
                throw new ORMappingException(e);
            }
        }
        freeConnections.clear();
    }

    /**
     * コネクションの設定を取得します。
     * 
     * @return コネクションの設定
     */
    public ConnectionConfig getConfig() {
        return config;
    }

}
