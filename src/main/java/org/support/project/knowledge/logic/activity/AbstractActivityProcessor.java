package org.support.project.knowledge.logic.activity;


import java.sql.Timestamp;
import java.util.Date;

import org.support.project.aop.Aspect;
import org.support.project.common.config.INT_FLAG;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.StringUtils;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.config.AppConfig;
import org.support.project.knowledge.config.UserConfig;
import org.support.project.knowledge.dao.ActivitiesDao;
import org.support.project.knowledge.dao.KnowledgesDao;
import org.support.project.knowledge.dao.PointKnowledgeHistoriesDao;
import org.support.project.knowledge.dao.PointUserHistoriesDao;
import org.support.project.knowledge.entity.ActivitiesEntity;
import org.support.project.knowledge.entity.PointKnowledgeHistoriesEntity;
import org.support.project.knowledge.entity.PointUserHistoriesEntity;
import org.support.project.web.bean.LoginedUser;
import org.support.project.web.dao.UserConfigsDao;
import org.support.project.web.entity.UserConfigsEntity;

/**
 * Activityに対応して、ポイントを付与する
 * 
 * @author koda
 */
@DI(instance = Instance.Prototype)
public abstract class AbstractActivityProcessor implements ActivityProcessor {
    private static final Log LOG = LogFactory.getLog(AbstractActivityProcessor.class);
    private static Object lockUser = new Object();
    private static Object lockKnowledge = new Object();
    
    protected LoginedUser eventUser;
    protected Date eventDateTime;
    /**
     * @param eventUser the eventUser to set
     */
    public void setEventUser(LoginedUser eventUser) {
        this.eventUser = eventUser;
    }
    /**
     * @param eventDateTime the eventDateTime to set
     */
    public void setEventDateTime(Date eventDateTime) {
        this.eventDateTime = eventDateTime;
    }
    
    /**
     * ポイントと種類を保持する内部クラス
     * @author koda
     */
    protected class TypeAndPoint {
        int type;
        int point;
        TypeAndPoint(int type, int point) {
            this.type = type;
            this.point = point;
        }
    }
    
    /**
     * 対象のactivityが既に登録されているかチェック
     * @param userId アクティビティを実行したユーザ
     * @param kind アクティビティの種類
     * @param target そのアクティビティでポイントを付与する対象
     * @return
     */
    protected boolean isExistsActivity(int userId, Activity kind, String target) {
        ActivitiesEntity entity = ActivitiesDao.get().select(userId, kind.getValue(), target);
        if (entity == null) {
            return false;
        }
        return true;
    }
    
    /**
     * activity登録
     * @param kind アクティビティの種類
     * @param target そのアクティビティでポイントを付与する対象
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    protected ActivitiesEntity addActivity(Activity kind, String target) {
        ActivitiesEntity entity = new ActivitiesEntity();
        entity.setUserId(eventUser.getUserId());
        entity.setKind(kind.getValue());
        entity.setTarget(target);
        entity.setInsertUser(eventUser.getUserId());
        entity.setInsertDatetime(new Timestamp(eventDateTime.getTime()));
        entity.setDeleteFlag(INT_FLAG.OFF.getValue());
        entity = ActivitiesDao.get().physicalInsert(entity);
        return entity;
    }
    
    /**
     * ポイントをユーザに加算
     * @param eventUser
     * @param eventTime
     * @param targetUser
     * @param activityNo
     * @param type
     * @param point
     * @return
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    protected int addPointForUser(int targetUser, long activityNo, int type, int point) {
        synchronized(lockUser) {
            UserConfigsEntity config = UserConfigsDao.get().selectOnKey(UserConfig.POINT, AppConfig.get().getSystemName(), targetUser);
            if (config == null) {
                config = new UserConfigsEntity(UserConfig.POINT, AppConfig.get().getSystemName(), targetUser);
                config.setConfigValue("0");
            }
            if (!StringUtils.isInteger(config.getConfigValue())) {
                config.setConfigValue("0");
            }
            
            int before = Integer.parseInt(config.getConfigValue());
            int now = before + point;
            if (LOG.isDebugEnabled()) {
                LOG.debug("Add point [user]" + targetUser + " [point]" + point + " [before]" + before + " [now]" + now);
            }
            config.setConfigValue(String.valueOf(now));
            UserConfigsDao.get().save(config);
            
            long num = PointUserHistoriesDao.get().selectNumOnUser(targetUser);
            num++;
            PointUserHistoriesEntity history = new PointUserHistoriesEntity();
            history.setUserId(targetUser);
            history.setHistoryNo(num);
            history.setActivityNo(activityNo);
            history.setType(type);
            history.setPoint(point);
            history.setBeforeTotal(before);
            history.setTotal(now);
            history.setInsertUser(eventUser.getUserId());
            history.setInsertDatetime(new Timestamp(eventDateTime.getTime()));
            history.setDeleteFlag(INT_FLAG.OFF.getValue());
            PointUserHistoriesDao.get().physicalInsert(history);
            
            return now;
        }
    }
    /**
     * 記事にポイントを加算
     * @param eventUser
     * @param eventTime
     * @param knowledgeId
     * @param activityNo
     * @param type
     * @param point
     * @return
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    protected int addPointForKnowledge(long knowledgeId, long activityNo, int type, int point) {
        synchronized (lockKnowledge) {
            // Daoのupdateメソッドなどを使うと、「更新者」が更新されるので、ポイントのみを更新するメソッドを呼ぶ
            // なお、記事の存在チェックは行わない
            int before = KnowledgesDao.get().selectPoint(knowledgeId);
            int now = before + point;
            if (LOG.isDebugEnabled()) {
                LOG.debug("Add point [knowledge]" + knowledgeId + " [point]" + point + " [before]" + before + " [now]" + now);
            }
            KnowledgesDao.get().updatePoint(knowledgeId, now);
            
            long num = PointKnowledgeHistoriesDao.get().selectNumOnKnowledge(knowledgeId);
            num++;
            PointKnowledgeHistoriesEntity history = new PointKnowledgeHistoriesEntity();
            history.setKnowledgeId(knowledgeId);
            history.setHistoryNo(num);
            history.setActivityNo(activityNo);
            history.setType(type);
            history.setPoint(point);
            history.setBeforeTotal(before);
            history.setTotal(now);
            history.setInsertUser(eventUser.getUserId());
            history.setInsertDatetime(new Timestamp(eventDateTime.getTime()));
            history.setDeleteFlag(INT_FLAG.OFF.getValue());
            PointKnowledgeHistoriesDao.get().physicalInsert(history);
            
            return now;
        }
    }

}
