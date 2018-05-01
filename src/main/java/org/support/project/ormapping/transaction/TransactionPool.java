package org.support.project.ormapping.transaction;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.ormapping.connection.ConnectionManager;
import org.support.project.ormapping.connection.ConnectionPool;
import org.support.project.ormapping.connection.ConnectionPoolImpl;
import org.support.project.ormapping.connection.PoolableConnectionWrapper;
import org.support.project.ormapping.exception.ORMappingException;

/**
 * トランザクションの中で利用するコネクションをpoolしておく
 * 
 * @author koda
 * @deprecated 使い道がよくわからない（自分で作ったのに、、、）
 */
@DI(instance = Instance.Prototype)
public class TransactionPool implements ConnectionPool {
    /** ログ */
    private static Log logger = LogFactory.getLog(PoolableConnectionWrapper.class);

    /** Threadで利用したコネクション */
    private List<PoolableConnectionWrapper> connections;
    /** フリーのコネクション */
    private List<PoolableConnectionWrapper> freeConnections;

    /** コネクション設定名 */
    private String configName = null;

    public TransactionPool() {
        super();
        connections = new ArrayList<>();
        freeConnections = new ArrayList<>();
    }

    public void setConfigName(String configName) {
        if (this.configName != null && !this.configName.equals(configName)) {
            throw new ORMappingException("トランザクション管理は、今のところ一つのコネクション設定に対して有効です。");
        }
        this.configName = configName;
    }

    @Override
    public Connection getConnection() {
        if (freeConnections.size() > 1) {
            Connection con = freeConnections.get(0);
            freeConnections.remove(0);
            return con;
        } else {
            ConnectionPoolImpl pool = ConnectionManager.getInstance().getConnectionPool(configName);
            Connection con = pool.getConnection();
            logger.debug("Get new connection : " + con.toString());
            PoolableConnectionWrapper wrapper = new PoolableConnectionWrapper(this, con, connections.size());
            connections.add(wrapper);
            return con;
        }
    }

    @Override
    public void freeConnection(Connection con) {
        freeConnections.add((PoolableConnectionWrapper) con);
    }

    @Override
    public void release() {
        Iterator<PoolableConnectionWrapper> allConnections = connections.iterator();
        while (allConnections.hasNext()) {
            Connection con = allConnections.next();
            try {
                if (con instanceof PoolableConnectionWrapper) {
                    ((PoolableConnectionWrapper) con).closeConnection();
                } else {
                    con.close();
                }
            } catch (SQLException e) {
                throw new ORMappingException(e);
            }
        }
        connections.clear();
        freeConnections.clear();

    }

    public void commit() throws SQLException {
        Iterator<PoolableConnectionWrapper> allConnections = connections.iterator();
        while (allConnections.hasNext()) {
            Connection con = allConnections.next();
            con.commit();
        }
    }

    public void rollback() throws SQLException {
        Iterator<PoolableConnectionWrapper> allConnections = connections.iterator();
        while (allConnections.hasNext()) {
            Connection con = allConnections.next();
            con.rollback();
        }
    }

    public String getConfigName() {
        return configName;
    }

}
