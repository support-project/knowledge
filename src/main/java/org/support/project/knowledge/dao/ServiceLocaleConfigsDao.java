package org.support.project.knowledge.dao;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;

import org.support.project.knowledge.dao.gen.GenServiceLocaleConfigsDao;

/**
 * サービスの表示言語毎の設定
 */
@DI(instance = Instance.Singleton)
public class ServiceLocaleConfigsDao extends GenServiceLocaleConfigsDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;
    /**
     * Get instance from DI container.
     * @return instance
     */
    public static ServiceLocaleConfigsDao get() {
        return Container.getComp(ServiceLocaleConfigsDao.class);
    }



}
