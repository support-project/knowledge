package org.support.project.knowledge.logic;

import java.util.List;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.config.AppConfig;
import org.support.project.knowledge.config.SystemConfig;
import org.support.project.knowledge.dao.ServiceConfigsDao;
import org.support.project.knowledge.dao.ServiceLocaleConfigsDao;
import org.support.project.knowledge.entity.ServiceConfigsEntity;
import org.support.project.knowledge.entity.ServiceLocaleConfigsEntity;

@DI(instance = Instance.Singleton)
public class ServiceConfigLogic {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(ServiceConfigLogic.class);
    /**
     * インスタンスを取得
     * @return
     */
    public static ServiceConfigLogic get() {
        return Container.getComp(ServiceConfigLogic.class);
    }
    
    public void load() {
        ServiceConfigsEntity serviceConfigsEntity = ServiceConfigsDao.get().selectOnKey(AppConfig.get().getSystemName());
        if (serviceConfigsEntity != null) {
            SystemConfig.setServiceConfigsEntity(serviceConfigsEntity);
            List<ServiceLocaleConfigsEntity> serviceLocaleConfigsEntities = 
                    ServiceLocaleConfigsDao.get().selectOnServiceName(AppConfig.get().getSystemName());
            SystemConfig.setServiceLocaleConfigsEntities(serviceLocaleConfigsEntities);
        }
    }

}
