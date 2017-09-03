package org.support.project.knowledge.deploy.v1_11_0;

import java.util.List;

import org.support.project.aop.Aspect;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.knowledge.config.AppConfig;
import org.support.project.knowledge.config.UserConfig;
import org.support.project.knowledge.dao.KnowledgesDao;
import org.support.project.knowledge.dao.LikesDao;
import org.support.project.knowledge.dao.ParticipantsDao;
import org.support.project.knowledge.dao.StockKnowledgesDao;
import org.support.project.knowledge.dao.SurveyAnswersDao;
import org.support.project.knowledge.dao.ViewHistoriesDao;
import org.support.project.knowledge.deploy.Migrate;
import org.support.project.knowledge.entity.KnowledgesEntity;
import org.support.project.knowledge.entity.LikesEntity;
import org.support.project.knowledge.entity.ParticipantsEntity;
import org.support.project.knowledge.entity.StockKnowledgesEntity;
import org.support.project.knowledge.entity.SurveyAnswersEntity;
import org.support.project.knowledge.entity.ViewHistoriesEntity;
import org.support.project.knowledge.logic.activity.Activity;
import org.support.project.knowledge.logic.activity.ActivityLogic;
import org.support.project.ormapping.tool.dao.InitializeDao;
import org.support.project.web.bean.LoginedUser;
import org.support.project.web.dao.UserConfigsDao;
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
        // ユーザについているポイントをクリア
        clearUserPoint();
        
        // Knowledgeを登録したイベントによりポイントを集計
        doAddPointByKnowledge();
        // Knowledgeを参照したイベントによりポイントを集計
        doAddPointByKnowledgeShow();
        // Knowledgeにイイネを押したイベントによりポイント集計
        doAddPointByKnowledgeShow();
        // Knowledgeにイイネを押したイベントによりポイント集計
        doAddPointByKnowledgeLike();
        // Knowledgeをストックイベントによりポイント集計
        doAddPointByKnowledgeStock();
        // Knowledgeのアンケートを回答したイベントによりポイント集計
        doAddPointByKnowledgeAnswer();
        // Knowledgeイベント参加したイベントによりポイント集計
        doAddPointByKnowledgeJoinEvent();
        
        
        return true;
    }
    
    
    private void doAddPointByKnowledgeJoinEvent() throws InterruptedException {
        List<ParticipantsEntity> list;
        int offset = 0;
        int limit = 50;
        do {
            list = doAddPointByKnowledgeJoinEvent(offset, limit);
            offset = offset + limit;
            synchronized (this) {
                wait(200);
            }
        } while (list.size() > 0);
    }
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    private List<ParticipantsEntity> doAddPointByKnowledgeJoinEvent(int offset, int limit) {
        LOG.info("Aggregate point by knowledge join event");
        List<ParticipantsEntity> list;
        list = ParticipantsDao.get().selectAllWidthPager(limit, offset);
        for (ParticipantsEntity item : list) {
            KnowledgesEntity knowledge = KnowledgesDao.get().selectOnKey(item.getKnowledgeId());
            if (knowledge == null) {
                LOG.info("    knowledge [" + item.getKnowledgeId() + "] is not found. so skip add point by knowledge like.");
                continue;
            }
            LoginedUser user = new LoginedUser();
            UsersEntity account = UsersDao.get().selectOnKey(item.getInsertUser());
            if (account == null) {
                LOG.info("    event user [" + knowledge.getInsertUser() + "] is not found. so skip add point by knowledge like.");
                continue;
            }
            user.setLoginUser(account);
            LOG.info("    knowledge [" + knowledge.getKnowledgeId() + "] ");
            ActivityLogic.get().processActivity(Activity.KNOWLEDGE_EVENT_ADD, user, item.getInsertDatetime(), knowledge);
        }
        return list;
    }

    private void doAddPointByKnowledgeAnswer() throws InterruptedException {
        List<SurveyAnswersEntity> list;
        int offset = 0;
        int limit = 50;
        do {
            list = doAddPointByKnowledgeAnswer(offset, limit);
            offset = offset + limit;
            synchronized (this) {
                wait(200);
            }
        } while (list.size() > 0);
    }

    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    private List<SurveyAnswersEntity> doAddPointByKnowledgeAnswer(int offset, int limit) {
        LOG.info("Aggregate point by knowledge answer");
        List<SurveyAnswersEntity> list;
        list = SurveyAnswersDao.get().selectAllWidthPager(limit, offset);
        for (SurveyAnswersEntity answer : list) {
            KnowledgesEntity knowledge = KnowledgesDao.get().selectOnKey(answer.getKnowledgeId());
            if (knowledge == null) {
                LOG.info("    knowledge [" + answer.getKnowledgeId() + "] is not found. so skip add point by knowledge like.");
                continue;
            }
            LoginedUser user = new LoginedUser();
            UsersEntity account = UsersDao.get().selectOnKey(answer.getInsertUser());
            if (account == null) {
                LOG.info("    event user [" + knowledge.getInsertUser() + "] is not found. so skip add point by knowledge like.");
                continue;
            }
            user.setLoginUser(account);
            LOG.info("    knowledge [" + knowledge.getKnowledgeId() + "] ");
            ActivityLogic.get().processActivity(Activity.KNOWLEDGE_ANSWER, user, answer.getInsertDatetime(), knowledge);
        }
        return list;
    }

    private void doAddPointByKnowledgeStock() throws InterruptedException {
        List<StockKnowledgesEntity> list;
        int offset = 0;
        int limit = 50;
        do {
            list = doAddPointByKnowledgeStock(offset, limit);
            offset = offset + limit;
            synchronized (this) {
                wait(200);
            }
        } while (list.size() > 0);
    }

    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    private List<StockKnowledgesEntity> doAddPointByKnowledgeStock(int offset, int limit) {
        LOG.info("Aggregate point by knowledge stock");
        List<StockKnowledgesEntity> list;
        list = StockKnowledgesDao.get().selectAllWidthPager(limit, offset);
        for (StockKnowledgesEntity stock : list) {
            KnowledgesEntity knowledge = KnowledgesDao.get().selectOnKey(stock.getKnowledgeId());
            if (knowledge == null) {
                LOG.info("    knowledge [" + stock.getKnowledgeId() + "] is not found. so skip add point by knowledge like.");
                continue;
            }
            LoginedUser user = new LoginedUser();
            UsersEntity account = UsersDao.get().selectOnKey(stock.getInsertUser());
            if (account == null) {
                LOG.info("    event user [" + knowledge.getInsertUser() + "] is not found. so skip add point by knowledge like.");
                continue;
            }
            user.setLoginUser(account);
            LOG.info("    knowledge [" + knowledge.getKnowledgeId() + "] ");
            ActivityLogic.get().processActivity(Activity.KNOWLEDGE_STOCK, user, stock.getInsertDatetime(), knowledge);
        }
        return list;
    }

    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    private void doAddPointByKnowledgeLike() throws InterruptedException {
        List<LikesEntity> list;
        int offset = 0;
        int limit = 50;
        do {
            list = doAddPointByKnowledgeLike(offset, limit);
            offset = offset + limit;
            synchronized (this) {
                wait(200);
            }
        } while (list.size() > 0);
    }
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    private List<LikesEntity> doAddPointByKnowledgeLike(int offset, int limit) {
        LOG.info("Aggregate point by knowledge like");
        List<LikesEntity> list;
        list = LikesDao.get().selectAllWidthPager(limit, offset);
        for (LikesEntity like : list) {
            KnowledgesEntity knowledge = KnowledgesDao.get().selectOnKey(like.getKnowledgeId());
            if (knowledge == null) {
                LOG.info("    knowledge [" + like.getKnowledgeId() + "] is not found. so skip add point by knowledge like.");
                continue;
            }
            LoginedUser user = new LoginedUser();
            UsersEntity account = UsersDao.get().selectOnKey(like.getInsertUser());
            if (account == null) {
                LOG.info("    event user [" + knowledge.getInsertUser() + "] is not found. so skip add point by knowledge like.");
                continue;
            }
            user.setLoginUser(account);
            LOG.info("    knowledge [" + knowledge.getKnowledgeId() + "] ");
            ActivityLogic.get().processActivity(Activity.KNOWLEDGE_LIKE, user, like.getInsertDatetime(), knowledge);
        }
        return list;
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
            ActivityLogic.get().processActivity(Activity.KNOWLEDGE_SHOW, user, viewHistoriesEntity.getInsertDatetime(), knowledge);
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
            ActivityLogic.get().processActivity(Activity.KNOWLEDGE_INSERT, user, knowledge.getInsertDatetime(), knowledge);
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
            
            //ついでにポイントも初期化
            KnowledgesDao.get().updatePoint(knowledge.getKnowledgeId(), 0);
        }
        return knowledges;
    }
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    private void clearUserPoint() {
        UserConfigsDao.get().removeAllUserConfig(AppConfig.get().getSystemName(), UserConfig.POINT);
    }
}