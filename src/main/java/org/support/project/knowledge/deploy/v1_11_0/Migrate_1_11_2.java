package org.support.project.knowledge.deploy.v1_11_0;

import java.util.List;

import org.support.project.aop.Aspect;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.knowledge.dao.KnowledgesDao;
import org.support.project.knowledge.dao.ViewHistoriesDao;
import org.support.project.knowledge.deploy.Migrate;
import org.support.project.knowledge.entity.KnowledgesEntity;
import org.support.project.knowledge.entity.ViewHistoriesEntity;
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
        
        // 参照回数を集計
        doSetViewCountToKnowledge();
        
        // Knowledgeを登録したイベントによりポイントを集計
        doAddPointByKnowledge();
        
        // Knowledgeを参照したイベントによりポイントを集計
        doAddPointByKnowledgeShow();
        
        
        
        return true;
    }
    
    

    private void doAddPointByKnowledgeShow() throws InterruptedException {
        List<ViewHistoriesEntity> list;
        int offset = 0;
        int limit = 50;
        do {
            list = doAddPointByKnowledgeShow(offset, limit);
            offset = offset + limit;
            synchronized (this) {
                wait(200);
            }
        } while (list.size() > 0);
    }

    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    private List<ViewHistoriesEntity> doAddPointByKnowledgeShow(int offset, int limit) {
        LOG.info("Aggregate point by knowledge show");
        List<ViewHistoriesEntity> list;
        list = ViewHistoriesDao.get().selectAllWidthPager(limit, offset);
        for (ViewHistoriesEntity viewHistoriesEntity : list) {
            KnowledgesEntity knowledge = KnowledgesDao.get().selectOnKey(viewHistoriesEntity.getKnowledgeId());
            if (knowledge == null) {
                LOG.info("    knowledge [" + viewHistoriesEntity.getKnowledgeId() + "] is not found. so skip add point by knowledge show.");
                continue;
            }
            LoginedUser user = new LoginedUser();
            UsersEntity account = UsersDao.get().selectOnKey(viewHistoriesEntity.getInsertUser());
            if (account == null) {
                LOG.info("    event user [" + knowledge.getInsertUser() + "] is not found. so skip add point by knowledge show.");
                continue;
            }
            user.setLoginUser(account);
            LOG.info("    knowledge [" + knowledge.getKnowledgeId() + "] ");
            ActivityLogic.get().processActivity(Activity.KNOWLEDGE_SHOW, user, knowledge);
        }
        return list;
    }

    public void doAddPointByKnowledge() throws InterruptedException {
        List<KnowledgesEntity> knowledges;
        int offset = 0;
        int limit = 50;
        do {
            knowledges = doAddPointByKnowledge(offset, limit);
            offset = offset + limit;
            synchronized (this) {
                wait(200);
            }
        } while (knowledges.size() > 0);
    }
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    private List<KnowledgesEntity> doAddPointByKnowledge(int offset, int limit) {
        LOG.info("Aggregate point by knowledge insert");
        List<KnowledgesEntity> knowledges;
        knowledges = KnowledgesDao.get().selectAllWidthPager(limit, offset);
        for (KnowledgesEntity knowledge : knowledges) {
            LoginedUser user = new LoginedUser();
            UsersEntity account = UsersDao.get().selectOnKey(knowledge.getInsertUser());
            if (account == null) {
                LOG.info("    insert user [" + knowledge.getInsertUser() + "] is not found. so skip add point by knowledge insert.");
                continue;
            }
            user.setLoginUser(account);
            LOG.info("    knowledge [" + knowledge.getKnowledgeId() + "] ");
            ActivityLogic.get().processActivity(Activity.KNOWLEDGE_INSERT, user, knowledge);
        }
        return knowledges;
    }
    
    private void doSetViewCountToKnowledge() throws InterruptedException {
        List<KnowledgesEntity> knowledges;
        int offset = 0;
        int limit = 50;
        do {
            knowledges = doSetViewCountToKnowledge(offset, limit);
            offset = offset + limit;
            synchronized (this) {
                wait(200);
            }
        } while (knowledges.size() > 0);
    }
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    private List<KnowledgesEntity> doSetViewCountToKnowledge(int offset, int limit) {
        LOG.info("Set view count to Knowledge");
        List<KnowledgesEntity> knowledges;
        knowledges = KnowledgesDao.get().selectAllWidthPager(limit, offset);
        for (KnowledgesEntity knowledge : knowledges) {
            LOG.info("    knowledge [" + knowledge.getKnowledgeId() + "] ");
            long count = ViewHistoriesDao.get().selectCountOnKnowledgeId(knowledge.getKnowledgeId());
            KnowledgesDao.get().updateViewCount(count, knowledge.getKnowledgeId());
        }
        return knowledges;
    }
}