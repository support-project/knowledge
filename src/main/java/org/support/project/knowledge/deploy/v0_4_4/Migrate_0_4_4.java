package org.support.project.knowledge.deploy.v0_4_4;

import java.util.List;

import org.support.project.knowledge.dao.KnowledgesDao;
import org.support.project.knowledge.deploy.Migrate;
import org.support.project.knowledge.entity.KnowledgesEntity;
import org.support.project.knowledge.logic.KnowledgeLogic;
import org.support.project.ormapping.tool.dao.InitializeDao;

public class Migrate_0_4_4 implements Migrate {

    public static Migrate_0_4_4 get() {
        return org.support.project.di.Container.getComp(Migrate_0_4_4.class);
    }

    @Override
    public boolean doMigrate() throws Exception {
        InitializeDao initializeDao = InitializeDao.get();
        String[] sqlpaths = { "/org/support/project/knowledge/deploy/v0_4_4/migrate.sql" };
        initializeDao.initializeDatabase(sqlpaths);

        KnowledgesDao knowledgesDao = KnowledgesDao.get();
        List<KnowledgesEntity> knowledges = knowledgesDao.selectAll();

        KnowledgeLogic knowledgeLogic = KnowledgeLogic.get();

        for (KnowledgesEntity entity : knowledges) {
            knowledgeLogic.updateKnowledgeExInfo(entity);
        }

        return true;
    }

}
