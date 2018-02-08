package org.support.project.ormapping.transaction;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;

import org.support.project.aop.Advice;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.di.Container;
import org.support.project.ormapping.connection.ConnectionManager;
import org.support.project.ormapping.dao.AbstractDao;

public class Transaction implements Advice<Object> {
    /** ログ */
    private static Log log = LogFactory.getLog(MethodHandles.lookup());

    @Override
    public Object invoke(Object object, Method method, Object[] args) throws Throwable {
        TransactionManager manager = Container.getComp(TransactionManager.class);
        boolean start = false;

        String name = ConnectionManager.getInstance().getDefaultConnectionName();
        if (AbstractDao.class.isAssignableFrom(object.getClass())) {
            if (method.getName().endsWith("setConnectionName") || method.getName().endsWith("getConnectionName")) {
                Object r = method.invoke(object, args);
                return r;
            } else {
                AbstractDao dao = (AbstractDao) object;
                name = dao.getConnectionName();
            }
        }
        if (log.isTraceEnabled()) {
            log.trace(object.getClass().getName() + "#" + method.getName() + ":::" + name);
        }

        if (!manager.isStarted(name)) {
            if (log.isDebugEnabled()) {
                log.debug("Transaction Start : " + object.getClass().getName() + "#" + method.getName());
            }
            manager.start(name);
            start = true;
        } else {
            if (log.isTraceEnabled()) {
                log.trace("トランザクションは既に開始されているため引き継ぐ : " + object.getClass().getName() + "#" + method.getName());
            }
        }
        // トランザクション開始
        try {
            Object r = method.invoke(object, args);

            // トランザクション終了(コミット)
            if (start) {
                log.debug("Commmit : " + object.getClass().getName() + "#" + method.getName());
                manager.commit(name);
            }
            return r;
        } catch (Exception e) {
            if (start) {
                log.error("Error(Rollback) : " + object.getClass().getName() + "#" + method.getName());
                try {
                    manager.rollback(name);
                } catch (Exception e2) {
                    log.error("Error", e);
                    log.error("Error", e2);
                    throw e;
                }
            }
            // ロールバック
            throw e;
        } finally {
            if (start) {
                log.debug("Transaction End : " + object.getClass().getName() + "#" + method.getName());
                manager.end(name);
            }
        }
    }
}
