package org.support.project.knowledge.deploy.v1_8_0;

import org.support.project.common.util.FileUtil;
import org.support.project.knowledge.config.AppConfig;
import org.support.project.knowledge.dao.ServiceConfigsDao;
import org.support.project.knowledge.dao.ServiceLocaleConfigsDao;
import org.support.project.knowledge.deploy.Migrate;
import org.support.project.knowledge.entity.ServiceConfigsEntity;
import org.support.project.knowledge.entity.ServiceLocaleConfigsEntity;
import org.support.project.ormapping.tool.dao.InitializeDao;

public class Migrate_1_8_1 implements Migrate {

    public static Migrate_1_8_1 get() {
        return org.support.project.di.Container.getComp(Migrate_1_8_1.class);
    }

    @Override
    public boolean doMigrate() throws Exception {
        InitializeDao initializeDao = InitializeDao.get();
        String[] sqlpaths = {
            "/org/support/project/knowledge/deploy/v1_8_0/migrate2.sql",
        };
        initializeDao.initializeDatabase(sqlpaths);
        
        ServiceConfigsEntity serviceConfigsEntity = new ServiceConfigsEntity(AppConfig.get().getSystemName());
        serviceConfigsEntity.setServiceLabel(AppConfig.get().getSystemName());
        serviceConfigsEntity.setServiceIcon("fa-book");
        ServiceConfigsDao.get().insert(serviceConfigsEntity);
        
        ServiceLocaleConfigsEntity en = new ServiceLocaleConfigsEntity("en", AppConfig.get().getSystemName());
        en.setPageHtml(FileUtil.read(getClass().getResourceAsStream("/org/support/project/knowledge/deploy/v1_8_0/top_info.html")));
        ServiceLocaleConfigsDao.get().insert(en);
        
        ServiceLocaleConfigsEntity ja = new ServiceLocaleConfigsEntity("ja", AppConfig.get().getSystemName());
        ja.setPageHtml(FileUtil.read(getClass().getResourceAsStream("/org/support/project/knowledge/deploy/v1_8_0/top_info_ja.html")));
        ServiceLocaleConfigsDao.get().insert(ja);
        return true;
    }
}