package org.support.project.knowledge.deploy.v1_11_0;

import java.util.List;

import org.support.project.aop.Aspect;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.knowledge.config.AppConfig;
import org.support.project.knowledge.config.UserConfig;
import org.support.project.knowledge.dao.CommentsDao;
import org.support.project.knowledge.dao.KnowledgesDao;
import org.support.project.knowledge.dao.LikeCommentsDao;
import org.support.project.knowledge.dao.LikesDao;
import org.support.project.knowledge.dao.ParticipantsDao;
import org.support.project.knowledge.dao.StockKnowledgesDao;
import org.support.project.knowledge.dao.SurveyAnswersDao;
import org.support.project.knowledge.dao.ViewHistoriesDao;
import org.support.project.knowledge.deploy.Migrate;
import org.support.project.knowledge.entity.CommentsEntity;
import org.support.project.knowledge.entity.KnowledgesEntity;
import org.support.project.knowledge.entity.LikeCommentsEntity;
import org.support.project.knowledge.entity.LikesEntity;
import org.support.project.knowledge.entity.ParticipantsEntity;
import org.support.project.knowledge.entity.StockKnowledgesEntity;
import org.support.project.knowledge.entity.SurveyAnswersEntity;
import org.support.project.knowledge.entity.ViewHistoriesEntity;
import org.support.project.knowledge.logic.activity.Activity;
import org.support.project.knowledge.logic.activity.ActivityLogic;
import org.support.project.ormapping.config.Order;
import org.support.project.ormapping.tool.dao.InitializeDao;
import org.support.project.web.bean.LoginedUser;
import org.support.project.web.dao.UserConfigsDao;
import org.support.project.web.dao.UsersDao;
import org.support.project.web.entity.UsersEntity;

public class Migrate_1_11_2 implements Migrate {
    private static final int _WAIT = 50;
    /** ログ */
    private static final Log LOG = LogFactory.getLog(Migrate_1_11_2.class);

    public static Migrate_1_11_2 get() {
        return org.support.project.di.Container.getComp(Migrate_1_11_2.class);
    }
    private int limit = 1; // 1件毎にコミットいれないと、別コネクションで取得して結果をクリアしてしまう？

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
        doAddPointByKnowledgeLike();
        // Knowledgeをストックイベントによりポイント集計
        doAddPointByKnowledgeStock();
        // Knowledgeのアンケートを回答したイベントによりポイント集計
        doAddPointByKnowledgeAnswer();
        // Knowledgeイベント参加したイベントによりポイント集計
        doAddPointByKnowledgeJoinEvent();
        // コメントを登録したイベントによりポイント集計
        doAddPointByCommentInsert();
        // コメントへのイイネイベントによりポイント集計
        doAddPointByCommentLike();
        
        return true;
    }
    
    private void doAddPointByCommentLike() throws InterruptedException {
        LOG.info("Aggregate point by comment like");
        List<LikeCommentsEntity> list;
        int offset = 0;
        do {
            list = doAddPointByCommentLike(offset, limit);
            offset = offset + limit;
            synchronized (this) {
                wait(_WAIT);
            }
        } while (list.size() > 0);
    }

    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    private List<LikeCommentsEntity> doAddPointByCommentLike(int offset, int limit2) {
        List<LikeCommentsEntity> list;
        list = LikeCommentsDao.get().selectAllWidthPager(limit, offset, Order.ASC);
        for (LikeCommentsEntity item : list) {
            CommentsEntity comment = CommentsDao.get().selectOnKey(item.getCommentNo());
            if (comment == null) {
                LOG.debug("    comment [" + item.getCommentNo() + "] is not found. so skip add point by comment like.");
                continue;
            }
            LoginedUser user = new LoginedUser();
            UsersEntity account = UsersDao.get().selectOnKey(item.getInsertUser());
            if (account == null) {
                LOG.debug("    event user [" + item.getInsertUser() + "] is not found. so skip add point by comment like.");
                continue;
            }
            user.setLoginUser(account);
            LOG.debug("    comment [" + item.getCommentNo() + "], knowledge [ " + comment.getKnowledgeId() + "]");
            ActivityLogic.get().processActivity(Activity.COMMENT_LIKE, user, item.getInsertDatetime(), comment);
        }
        return list;
    }

    private void doAddPointByCommentInsert() throws InterruptedException {
        LOG.info("Aggregate point by comment insert");
        List<CommentsEntity> list;
        int offset = 0;
        do {
            list = doAddPointByKnowledgeComment(offset, limit);
            offset = offset + limit;
            synchronized (this) {
                wait(_WAIT);
            }
        } while (list.size() > 0);
    }
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<CommentsEntity> doAddPointByKnowledgeComment(int offset, int limit) {
        List<CommentsEntity> list;
        list = CommentsDao.get().selectAllWidthPager(limit, offset, Order.ASC);
        for (CommentsEntity item : list) {
            LoginedUser user = new LoginedUser();
            UsersEntity account = UsersDao.get().selectOnKey(item.getInsertUser());
            if (account == null) {
                LOG.debug("    event user [" + item.getInsertUser() + "] is not found. so skip add point by comment insert.");
                continue;
            }
            user.setLoginUser(account);
            LOG.debug("    comment [" + item.getCommentNo() + "], knowledge [ " + item.getKnowledgeId() + "]");
            ActivityLogic.get().processActivity(Activity.COMMENT_INSERT, user, item.getInsertDatetime(), item);
        }
        return list;
    }

    private void doAddPointByKnowledgeJoinEvent() throws InterruptedException {
        LOG.info("Aggregate point by knowledge join event");
        List<ParticipantsEntity> list;
        int offset = 0;
        do {
            list = doAddPointByKnowledgeJoinEvent(offset, limit);
            offset = offset + limit;
            synchronized (this) {
                wait(_WAIT);
            }
        } while (list.size() > 0);
    }
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<ParticipantsEntity> doAddPointByKnowledgeJoinEvent(int offset, int limit) {
        List<ParticipantsEntity> list;
        list = ParticipantsDao.get().selectAllWidthPager(limit, offset, Order.ASC);
        for (ParticipantsEntity item : list) {
            KnowledgesEntity knowledge = KnowledgesDao.get().selectOnKey(item.getKnowledgeId());
            if (knowledge == null) {
                LOG.debug("    knowledge [" + item.getKnowledgeId() + "] is not found. so skip add point by knowledge event.");
                continue;
            }
            LoginedUser user = new LoginedUser();
            UsersEntity account = UsersDao.get().selectOnKey(item.getInsertUser());
            if (account == null) {
                LOG.debug("    event user [" + item.getInsertUser() + "] is not found. so skip add point by knowledge event.");
                continue;
            }
            user.setLoginUser(account);
            LOG.debug("    knowledge [" + knowledge.getKnowledgeId() + "] ");
            ActivityLogic.get().processActivity(Activity.KNOWLEDGE_EVENT_ADD, user, item.getInsertDatetime(), knowledge);
        }
        return list;
    }

    private void doAddPointByKnowledgeAnswer() throws InterruptedException {
        LOG.info("Aggregate point by knowledge answer");
        List<SurveyAnswersEntity> list;
        int offset = 0;
        do {
            list = doAddPointByKnowledgeAnswer(offset, limit);
            offset = offset + limit;
            synchronized (this) {
                wait(_WAIT);
            }
        } while (list.size() > 0);
    }

    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<SurveyAnswersEntity> doAddPointByKnowledgeAnswer(int offset, int limit) {
        List<SurveyAnswersEntity> list;
        list = SurveyAnswersDao.get().selectAllWidthPager(limit, offset, Order.ASC);
        for (SurveyAnswersEntity item : list) {
            KnowledgesEntity knowledge = KnowledgesDao.get().selectOnKey(item.getKnowledgeId());
            if (knowledge == null) {
                LOG.debug("    knowledge [" + item.getKnowledgeId() + "] is not found. so skip add point by knowledge survey answer.");
                continue;
            }
            LoginedUser user = new LoginedUser();
            UsersEntity account = UsersDao.get().selectOnKey(item.getInsertUser());
            if (account == null) {
                LOG.debug("    event user [" + item.getInsertUser() + "] is not found. so skip add point by knowledge survey answer.");
                continue;
            }
            user.setLoginUser(account);
            LOG.debug("    knowledge [" + knowledge.getKnowledgeId() + "] ");
            ActivityLogic.get().processActivity(Activity.KNOWLEDGE_ANSWER, user, item.getInsertDatetime(), knowledge);
        }
        return list;
    }

    private void doAddPointByKnowledgeStock() throws InterruptedException {
        LOG.info("Aggregate point by knowledge stock");
        List<StockKnowledgesEntity> list;
        int offset = 0;
        do {
            list = doAddPointByKnowledgeStock(offset, limit);
            offset = offset + limit;
            synchronized (this) {
                wait(_WAIT);
            }
        } while (list.size() > 0);
    }

    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<StockKnowledgesEntity> doAddPointByKnowledgeStock(int offset, int limit) {
        List<StockKnowledgesEntity> list;
        list = StockKnowledgesDao.get().selectAllWidthPager(limit, offset, Order.ASC);
        for (StockKnowledgesEntity item : list) {
            KnowledgesEntity knowledge = KnowledgesDao.get().selectOnKey(item.getKnowledgeId());
            if (knowledge == null) {
                LOG.debug("    knowledge [" + item.getKnowledgeId() + "] is not found. so skip add point by knowledge stock.");
                continue;
            }
            LoginedUser user = new LoginedUser();
            UsersEntity account = UsersDao.get().selectOnKey(item.getInsertUser());
            if (account == null) {
                LOG.debug("    event user [" + item.getInsertUser() + "] is not found. so skip add point by knowledge stock.");
                continue;
            }
            user.setLoginUser(account);
            LOG.debug("    knowledge [" + knowledge.getKnowledgeId() + "] ");
            ActivityLogic.get().processActivity(Activity.KNOWLEDGE_STOCK, user, item.getInsertDatetime(), knowledge);
        }
        return list;
    }

    private void doAddPointByKnowledgeLike() throws InterruptedException {
        LOG.info("Aggregate point by knowledge like");
        List<LikesEntity> list;
        int offset = 0;
        do {
            list = doAddPointByKnowledgeLike(offset, limit);
            offset = offset + limit;
            synchronized (this) {
                wait(_WAIT);
            }
        } while (list.size() > 0);
    }
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<LikesEntity> doAddPointByKnowledgeLike(int offset, int limit) {
        List<LikesEntity> list;
        list = LikesDao.get().selectAllWidthPager(limit, offset, Order.ASC);
        for (LikesEntity item : list) {
            KnowledgesEntity knowledge = KnowledgesDao.get().selectOnKey(item.getKnowledgeId());
            if (knowledge == null) {
                LOG.debug("    knowledge [" + item.getKnowledgeId() + "] is not found. so skip add point by knowledge like.");
                continue;
            }
            LoginedUser user = new LoginedUser();
            UsersEntity account = UsersDao.get().selectOnKey(item.getInsertUser());
            if (account == null) {
                LOG.debug("    event user [" + item.getInsertUser() + "] is not found. so skip add point by knowledge like.");
                continue;
            }
            user.setLoginUser(account);
            LOG.debug("    knowledge [" + knowledge.getKnowledgeId() + "] ");
            ActivityLogic.get().processActivity(Activity.KNOWLEDGE_LIKE, user, item.getInsertDatetime(), knowledge);
        }
        return list;
    }
    
    private void doAddPointByKnowledgeShow() throws InterruptedException {
        LOG.info("Aggregate point by knowledge show");
        List<ViewHistoriesEntity> list;
        int offset = 0;
        do {
            list = doAddPointByKnowledgeShow(offset, limit);
            offset = offset + limit;
            synchronized (this) {
                wait(_WAIT);
            }
        } while (list.size() > 0);
    }
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<ViewHistoriesEntity> doAddPointByKnowledgeShow(int offset, int limit) {
        List<ViewHistoriesEntity> list;
        list = ViewHistoriesDao.get().selectDistinctAllWidthPager(limit, offset);
        for (ViewHistoriesEntity item : list) {
            KnowledgesEntity knowledge = KnowledgesDao.get().selectOnKey(item.getKnowledgeId());
            if (knowledge == null) {
                LOG.debug("    knowledge [" + item.getKnowledgeId() + "] is not found. so skip add point by knowledge show.");
                continue;
            }
            LoginedUser user = new LoginedUser();
            UsersEntity account = UsersDao.get().selectOnKey(item.getInsertUser());
            if (account == null) {
                LOG.debug("    event user [" + item.getInsertUser() + "] is not found. so skip add point by knowledge show.");
                continue;
            }
            user.setLoginUser(account);
            LOG.debug("    knowledge [" + knowledge.getKnowledgeId() + "] ");
            ActivityLogic.get().processActivity(Activity.KNOWLEDGE_SHOW, user, item.getInsertDatetime(), knowledge);
        }
        return list;
    }

    public void doAddPointByKnowledge() throws InterruptedException {
        LOG.info("Aggregate point by knowledge insert");
        List<KnowledgesEntity> knowledges;
        int offset = 0;
        do {
            knowledges = doAddPointByKnowledge(offset, limit);
            offset = offset + limit;
            synchronized (this) {
                wait(_WAIT);
            }
        } while (knowledges.size() > 0);
    }
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<KnowledgesEntity> doAddPointByKnowledge(int offset, int limit) {
        List<KnowledgesEntity> knowledges;
        knowledges = KnowledgesDao.get().selectAllWidthPager(limit, offset, Order.ASC);
        for (KnowledgesEntity item : knowledges) {
            LoginedUser user = new LoginedUser();
            UsersEntity account = UsersDao.get().selectOnKey(item.getInsertUser());
            if (account == null) {
                LOG.debug("    insert user [" + item.getInsertUser() + "] is not found. so skip add point by knowledge insert.");
                continue;
            }
            user.setLoginUser(account);
            LOG.debug("    knowledge [" + item.getKnowledgeId() + "] ");
            ActivityLogic.get().processActivity(Activity.KNOWLEDGE_INSERT, user, item.getInsertDatetime(), item);
        }
        return knowledges;
    }
    
    private void doSetViewCountToKnowledge() throws InterruptedException {
        LOG.info("Set view count to Knowledge");
        List<KnowledgesEntity> knowledges;
        int offset = 0;
        do {
            knowledges = doSetViewCountToKnowledge(offset, limit);
            offset = offset + limit;
            synchronized (this) {
                wait(_WAIT);
            }
        } while (knowledges.size() > 0);
    }
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<KnowledgesEntity> doSetViewCountToKnowledge(int offset, int limit) {
        List<KnowledgesEntity> knowledges;
        knowledges = KnowledgesDao.get().selectAllWidthPager(limit, offset, Order.ASC);
        for (KnowledgesEntity knowledge : knowledges) {
            long count = ViewHistoriesDao.get().selectCountOnKnowledgeId(knowledge.getKnowledgeId());
            KnowledgesDao.get().updateViewCount(count, knowledge.getKnowledgeId());
            //ついでにポイントも初期化
            KnowledgesDao.get().updatePoint(knowledge.getKnowledgeId(), 0);
            LOG.info("    knowledge [" + knowledge.getKnowledgeId() + "] is updated. view count : " + count);
        }
        return knowledges;
    }
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void clearUserPoint() {
        UserConfigsDao.get().removeAllUserConfig(AppConfig.get().getSystemName(), UserConfig.POINT);
    }
}