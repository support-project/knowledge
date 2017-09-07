package org.support.project.knowledge.deploy.v1_11_0;

import java.util.List;

import org.support.project.aop.Aspect;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.DateUtils;
import org.support.project.knowledge.config.AppConfig;
import org.support.project.knowledge.config.UserConfig;
import org.support.project.knowledge.dao.CommentsDao;
import org.support.project.knowledge.dao.KnowledgesDao;
import org.support.project.knowledge.dao.LikeCommentsDao;
import org.support.project.knowledge.dao.LikesDao;
import org.support.project.knowledge.dao.ParticipantsDao;
import org.support.project.knowledge.dao.PointUserHistoriesDao;
import org.support.project.knowledge.dao.StockKnowledgesDao;
import org.support.project.knowledge.dao.SurveyAnswersDao;
import org.support.project.knowledge.dao.ViewHistoriesDao;
import org.support.project.knowledge.deploy.Migrate;
import org.support.project.knowledge.entity.CommentsEntity;
import org.support.project.knowledge.entity.KnowledgesEntity;
import org.support.project.knowledge.entity.LikeCommentsEntity;
import org.support.project.knowledge.entity.LikesEntity;
import org.support.project.knowledge.entity.ParticipantsEntity;
import org.support.project.knowledge.entity.PointUserHistoriesEntity;
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
    
    private int calcTotal = 0; // トータルポイントの再計算に使う内部保持変数（シーケンシャルに1件づつ処理をすること）
    
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
        
        // ポイント集計は、今後は時系列になり問題は無いが、このマイグレーションの中では、時系列にはならんでいないので、履歴のトータルを再構築
        reCalcPointUserHistory();
        
        
        return true;
    }
    
    private void reCalcPointUserHistory() throws InterruptedException {
        List<UsersEntity> list;
        int offset = 0;
        do {
            list = reCalcPointUserHistory(offset);
            offset = offset + limit;
            synchronized (this) {
                wait(_WAIT);
            }
        } while (list.size() > 0);
    }
    private List<UsersEntity> reCalcPointUserHistory(int offset) throws InterruptedException {
        List<UsersEntity> list;
        list = UsersDao.get().selectAllWidthPager(limit, offset, Order.ASC);
        for (UsersEntity item : list) {
            LOG.info("Recalculation total point. [user]" + item.getUserId());
            int offset2 = 0;
            calcTotal = 0; // ユーザ毎にトータルポイントを初期化
            List<PointUserHistoriesEntity> histories;
            do {
                histories = reCalcPointUserHistoryOnUser(item.getUserId(), offset2);
                offset2 = offset2 + limit;
                synchronized (this) {
                    wait(_WAIT);
                }
            } while (histories.size() > 0);
        }
        return list;
    }
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    private List<PointUserHistoriesEntity> reCalcPointUserHistoryOnUser(Integer userId, int offset2) {
        List<PointUserHistoriesEntity> list;
        list = PointUserHistoriesDao.get().selectOnUser(userId, limit, offset2, Order.ASC);
        for (PointUserHistoriesEntity item : list) {
            item.setBeforeTotal(calcTotal);
            calcTotal += item.getPoint();
            item.setTotal(calcTotal); // 日付毎に処理してトータルを再計算
            LOG.info("\t" + DateUtils.getSimpleFormat().format(item.getInsertDatetime()) + " [total]" + calcTotal);
            PointUserHistoriesDao.get().physicalUpdate(item);
        }
        return list;
    }

    private void doAddPointByCommentLike() throws InterruptedException {
        LOG.info("Aggregate point by comment like");
        List<LikeCommentsEntity> list;
        int offset = 0;
        do {
            list = doAddPointByCommentLike(offset);
            offset = offset + limit;
            synchronized (this) {
                wait(_WAIT);
            }
        } while (list.size() > 0);
    }

    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    private List<LikeCommentsEntity> doAddPointByCommentLike(int offset) {
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
            list = doAddPointByKnowledgeComment(offset);
            offset = offset + limit;
            synchronized (this) {
                wait(_WAIT);
            }
        } while (list.size() > 0);
    }
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<CommentsEntity> doAddPointByKnowledgeComment(int offset) {
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
            list = doAddPointByKnowledgeJoinEvent(offset);
            offset = offset + limit;
            synchronized (this) {
                wait(_WAIT);
            }
        } while (list.size() > 0);
    }
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<ParticipantsEntity> doAddPointByKnowledgeJoinEvent(int offset) {
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
            list = doAddPointByKnowledgeAnswer(offset);
            offset = offset + limit;
            synchronized (this) {
                wait(_WAIT);
            }
        } while (list.size() > 0);
    }

    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<SurveyAnswersEntity> doAddPointByKnowledgeAnswer(int offset) {
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
            list = doAddPointByKnowledgeStock(offset);
            offset = offset + limit;
            synchronized (this) {
                wait(_WAIT);
            }
        } while (list.size() > 0);
    }

    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<StockKnowledgesEntity> doAddPointByKnowledgeStock(int offset) {
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
            list = doAddPointByKnowledgeLike(offset);
            offset = offset + limit;
            synchronized (this) {
                wait(_WAIT);
            }
        } while (list.size() > 0);
    }
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<LikesEntity> doAddPointByKnowledgeLike(int offset) {
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
            list = doAddPointByKnowledgeShow(offset);
            offset = offset + limit;
            synchronized (this) {
                wait(_WAIT);
            }
        } while (list.size() > 0);
    }
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<ViewHistoriesEntity> doAddPointByKnowledgeShow(int offset) {
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
            knowledges = doAddPointByKnowledge(offset);
            offset = offset + limit;
            synchronized (this) {
                wait(_WAIT);
            }
        } while (knowledges.size() > 0);
    }
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<KnowledgesEntity> doAddPointByKnowledge(int offset) {
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
            knowledges = doSetViewCountToKnowledge(offset);
            offset = offset + limit;
            synchronized (this) {
                wait(_WAIT);
            }
        } while (knowledges.size() > 0);
    }
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<KnowledgesEntity> doSetViewCountToKnowledge(int offset) {
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