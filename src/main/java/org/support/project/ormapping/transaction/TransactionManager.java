package org.support.project.ormapping.transaction;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.ormapping.connection.ConnectionManager;

/**
 * TransactionManager
 * @author Koda
 */
@DI(instance = Instance.Singleton)
public class TransactionManager {
    /** LOG */
    private static final Log LOG = LogFactory.getLog(TransactionManager.class);
    /**
     * Thread単位にコネクションを保持するマップ 一スレッドは一つのトランザクションを管理できる (一つのスレッドで複数のトランザクションを同時に行うことは、たぶんありえない)
     * 
     * TODO 複数コネクションのトランザクション管理には対応していない
     */
    private Map<String, Map<Thread, Connection>> map;
    // /**
    // * コネクション設定
    // */
    // private ConnectionConfig config = null;

    /**
     * コンストラクタ
     */
    public TransactionManager() {
        map = new HashMap<>();
    }
    
    /**
     * is started of Transaction
     * @param name connection name
     * @return check result
     */
    public boolean isStarted(String name) {
        if (map.containsKey(name)) {
            return map.get(name).containsKey(Thread.currentThread());
        }
        return false;
    }

    /**
     * トランザクションスタート
     * @param name connection name
     */
    public void start(String name) {
        if (!map.containsKey(name)) {
            map.put(name, new HashMap<>());
        }
        Map<Thread, Connection> conmap = map.get(name);
        if (!isStarted(name)) {
            ConnectionManager connectionManager = ConnectionManager.getInstance();
            Connection con = connectionManager.getConnection(name); // コネクションプールからコネクションを取得
            if (LOG.isTraceEnabled()) {
                LOG.trace("Get new connection : " + con.toString());
                LOG.info("Transaction start : " + name);
            }
            conmap.put(Thread.currentThread(), con);
        }
    }

    /**
     * 開始したトランザクションで利用しているトランザクションを取得
     * 
     * @param name connection name
     * @return Connection
     */
    public Connection getConnection(String name) {
        if (!map.containsKey(name)) {
            map.put(name, new HashMap<>());
        }
        Map<Thread, Connection> conmap = map.get(name);
        return conmap.get(Thread.currentThread());
    }

    /**
     * トランザクションをコミット
     * 
     * @param name connection name
     * @throws SQLException SQLException
     */
    public void commit(String name) throws SQLException {
        if (LOG.isTraceEnabled()) {
            LOG.info("Transaction commit : " + name);
        }
        Connection pool = getConnection(name);
        pool.commit();
    }

    /**
     * トランザクションをロールバック
     * 
     * @param name connection name
     * @throws SQLException SQLException
     */
    public void rollback(String name) throws SQLException {
        if (LOG.isTraceEnabled()) {
            LOG.info("Transaction rollback : " + name);
        }
        Connection pool = getConnection(name);
        pool.rollback();
    }

    /**
     * トランザクションを終了
     * 
     * @param name connection name
     * @throws SQLException SQLException
     */
    public void end(String name) throws SQLException {
        if (LOG.isTraceEnabled()) {
            LOG.info("Transaction end : " + name);
        }
        Connection connection = getConnection(name);
        if (connection != null) {
            connection.close(); // コネクションプールへ返却
            Map<Thread, Connection> conmap = map.get(name);
            conmap.remove(Thread.currentThread());
        }
    }

    /**
     * 現在開始している全てのトランザクションを終了
     * 
     * @param name connection name
     * @throws SQLException SQLException
     */
    public void release(String name) throws SQLException {
        if (LOG.isTraceEnabled()) {
            LOG.info("Transaction release : " + name);
        }
        if (map.containsKey(name)) {
            Map<Thread, Connection> conmap = map.get(name);
            Iterator<Thread> iterator = conmap.keySet().iterator();
            while (iterator.hasNext()) {
                Thread thread = (Thread) iterator.next();
                Connection connection = conmap.get(thread);
                connection.close();
                conmap.remove(thread);
            }
        }
    }

}
