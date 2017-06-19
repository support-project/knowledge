package org.support.project.knowledge.deploy.v1_10_0;

import org.support.project.knowledge.dao.MailHooksDao;
import org.support.project.knowledge.dao.MailPropertiesDao;
import org.support.project.knowledge.deploy.Migrate;
import org.support.project.knowledge.entity.MailHooksEntity;
import org.support.project.knowledge.entity.MailPropertiesEntity;
import org.support.project.knowledge.logic.MailhookLogic;
import org.support.project.ormapping.tool.dao.InitializeDao;

public class Migrate_1_10_1 implements Migrate {

    public static Migrate_1_10_1 get() {
        return org.support.project.di.Container.getComp(Migrate_1_10_1.class);
    }

    @Override
    public boolean doMigrate() throws Exception {
        InitializeDao initializeDao = InitializeDao.get();
        String[] sqlpaths = {
            "/org/support/project/knowledge/deploy/v1_10_0/migrate2.sql",
        };
        initializeDao.initializeDatabase(sqlpaths);
        
        // メール設定が既に存在している場合は、それと同等のプロパティ情報を生成する
        MailHooksEntity mailHook = MailHooksDao.get().selectOnKey(MailhookLogic.MAIL_HOOK_ID);
        if (mailHook != null) {
            MailPropertiesEntity property = new MailPropertiesEntity(MailhookLogic.MAIL_HOOK_ID, "mail.store.protocol");
            property.setPropertyValue("imaps");
            MailPropertiesDao.get().save(property);
            
            property.setPropertyKey("mail.imaps.ssl.trust");
            property.setPropertyValue("*");
            MailPropertiesDao.get().save(property);
        }

        return true;
    }
}