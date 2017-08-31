package org.support.project.knowledge.deploy.v1_11_0;

import java.util.List;

import org.support.project.aop.Aspect;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.knowledge.dao.KnowledgesDao;
import org.support.project.knowledge.deploy.Migrate;
import org.support.project.knowledge.entity.KnowledgesEntity;
import org.support.project.knowledge.logic.activity.Activity;
import org.support.project.knowledge.logic.activity.ActivityLogic;
import org.support.project.ormapping.tool.dao.InitializeDao;
import org.support.project.web.bean.LoginedUser;
import org.support.project.web.dao.UsersDao;
import org.support.project.web.entity.UsersEntity;

public class Migrate_1_11_2 implements Migrate {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(Migrate_1_11_2.class);

    public static Migrate_1_11_2 get() {
        return org.support.project.di.Container.getComp(Migrate_1_11_2.class);
    }

    @Override
    public boolean doMigrate() throws Exception {
        InitializeDao initializeDao = InitializeDao.get();
        String[] sqlpaths = {
            "/org/support/project/knowledge/deploy/v1_11_0/migrate_v1_11_2.sql",
        };
        initializeDao.initializeDatabase(sqlpaths);
        
        // Knowledgeを登録したイベントによりポイントを集計
        doAddPointByKnowledge();
        
        
        // 参照回数を集計
        
        
        return true;
    }
    
    
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void doAddPointByKnowledge() {
        // ポイント修正
        LOG.info("Aggregate point by knowledge insert");
        List<KnowledgesEntity> knowledges;
        int offset = 0;
        int limit = 50;
        do {
            knowledges = KnowledgesDao.get().selectAllWidthPager(limit, offset);
            for (KnowledgesEntity knowledge : knowledges) {
                LoginedUser user = new LoginedUser();
                UsersEntity account = UsersDao.get().selectOnKey(knowledge.getInsertUser());
                if (account == null) {
                    LOG.info("insert user [" + knowledge.getInsertUser() + "] is not found. so skip add point by knowledge insert.");
                    continue;
                }
                user.setLoginUser(account);
                LOG.info("    knowledge [" + knowledge.getKnowledgeId() + "] ");
                ActivityLogic.get().processActivity(Activity.KNOWLEDGE_INSERT, user, knowledge);
            }
            offset = offset + limit;
        } while (knowledges.size() > 0);
    }
    
}