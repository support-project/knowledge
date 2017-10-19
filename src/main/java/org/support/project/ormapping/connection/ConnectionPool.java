package org.support.project.ormapping.connection;

import java.sql.Connection;

import org.support.project.di.DI;
import org.support.project.di.Instance;

/**
 * コネクションプールのインタフェース
 * 
 * @author koda
 *
 */
@DI(instance = Instance.Singleton)
public interface ConnectionPool {

    Connection getConnection();

    void freeConnection(Connection con);

    void release();
}
