package org.support.project.knowledge.dao;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;

import org.support.project.knowledge.dao.gen.GenServiceConfigsDao;

/**
 * サービスの設定
 */
@DI(instance = Instance.Singleton)
public class ServiceConfigsDao extends GenServiceConfigsDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;
    /**
     * Get instance from DI container.
     * @return instance
     */
    public static ServiceConfigsDao get() {
        return Container.getComp(ServiceConfigsDao.class);
    }



}
