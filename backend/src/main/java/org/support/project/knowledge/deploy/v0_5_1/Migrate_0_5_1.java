package org.support.project.knowledge.deploy.v0_5_1;

import java.util.List;

import org.support.project.knowledge.dao.KnowledgesDao;
import org.support.project.knowledge.deploy.Migrate;
import org.support.project.knowledge.entity.KnowledgesEntity;
import org.support.project.knowledge.logic.KnowledgeLogic;
import org.support.project.ormapping.tool.dao.InitializeDao;

public class Migrate_0_5_1 implements Migrate {

    public static Migrate_0_5_1 get() {
        return org.support.project.di.Container.getComp(Migrate_0_5_1.class);
    }

    @Override
    public boolean doMigrate() throws Exception {
        InitializeDao initializeDao = InitializeDao.get();
        String[] sqlpaths = { "/org/support/project/knowledge/deploy/v0_5_1/migrate.sql" };
        initializeDao.initializeDatabase(sqlpaths);

        // すでに登録済のナレッジに更新履歴を登録
        List<KnowledgesEntity> entities = KnowledgesDao.get().selectAll();
        KnowledgeLogic logic = KnowledgeLogic.get();
        for (KnowledgesEntity knowledgesEntity : entities) {
            logic.insertHistory(knowledgesEntity);
        }

        return true;
    }

}
