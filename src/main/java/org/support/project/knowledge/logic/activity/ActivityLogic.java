package org.support.project.knowledge.logic.activity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.support.project.aop.Aspect;
import org.support.project.common.config.Resources;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.StringUtils;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.dao.ActivitiesDao;
import org.support.project.knowledge.dao.PointUserHistoriesDao;
import org.support.project.knowledge.entity.ActivitiesEntity;
import org.support.project.knowledge.entity.CommentsEntity;
import org.support.project.knowledge.entity.KnowledgesEntity;
import org.support.project.knowledge.entity.PointUserHistoriesEntity;
import org.support.project.knowledge.logic.KnowledgeLogic;
import org.support.project.knowledge.vo.ActivityHistory;
import org.support.project.knowledge.vo.ContributionPointHistory;
import org.support.project.knowledge.vo.UserConfigs;
import org.support.project.ormapping.config.Order;
import org.support.project.web.bean.LoginedUser;
import org.support.project.web.logic.DateConvertLogic;

@DI(instance = Instance.Singleton)
public class ActivityLogic {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(ActivityLogic.class);

    public static ActivityLogic get() {
        return Container.getComp(ActivityLogic.class);
    }
    
    private List<ActivityProcessor> getActivityProcessors(Activity activity) {
        List<ActivityProcessor> array = new ArrayList<>();
        if (activity == Activity.KNOWLEDGE_POST_PUBLIC
                || activity == Activity.KNOWLEDGE_POST_PROTECTED
                || activity == Activity.KNOWLEDGE_POST_PRIVATE) {
            array.add(KnowledgesaveActivity.get());
        } else if (activity == Activity.KNOWLEDGE_SHOW) {
            array.add(KnowledgeShowActivity.get());
        } else if (activity == Activity.KNOWLEDGE_LIKE) {
            array.add(KnowledgeLikeActivity.get());
        } else if (activity == Activity.KNOWLEDGE_STOCK) {
            array.add(KnowledgeStockActivity.get());
        } else if (activity == Activity.KNOWLEDGE_ANSWER) {
            array.add(KnowledgeAnswerActivity.get());
        } else if (activity == Activity.KNOWLEDGE_EVENT_ADD) {
            array.add(KnowledgeEventActivity.get());
        } else if (activity == Activity.COMMENT_INSERT) {
            array.add(CommentInsertActivity.get());
        } else if (activity == Activity.COMMENT_LIKE) {
            array.add(CommentLikeActivity.get());
        }
        return array;
    }
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    private void execute(Activity activity, LoginedUser eventUser, Date eventDateTime, KnowledgesEntity knowledge, CommentsEntity comment) {
        List<ActivityProcessor> processors = this.getActivityProcessors(activity);
        for (ActivityProcessor activityProcessor : processors) {
            if (activityProcessor instanceof AbstractActivityProcessor) {
                AbstractActivityProcessor processor = (AbstractActivityProcessor) activityProcessor;
                processor.setEventUser(eventUser);
                processor.setEventDateTime(eventDateTime);
            }
            if (activityProcessor instanceof AbstractAddPointForKnowledgeProcessor) {
                if (knowledge == null) {
                    LOG.warn("bad parameter [knowledge], because it is null.");
                    continue;
                }
                AbstractAddPointForKnowledgeProcessor processor = (AbstractAddPointForKnowledgeProcessor) activityProcessor;
                processor.setKnowledge(knowledge);
            } else if (activityProcessor instanceof AbstractAddPointForCommentProcessor) {
                if (comment == null) {
                    LOG.warn("bad parameter [comment], because it is null.");
                    continue;
                }
                AbstractAddPointForCommentProcessor processor = (AbstractAddPointForCommentProcessor) activityProcessor;
                processor.setComment(comment);
            }
            if (activityProcessor instanceof AbstractActivityProcessor) {
                AbstractActivityProcessor processor = (AbstractActivityProcessor) activityProcessor;
                processor.setEventUser(eventUser);
                processor.setEventDateTime(eventDateTime);
            }
            if (activityProcessor instanceof MultiActivityProcessor) {
                MultiActivityProcessor processor = (MultiActivityProcessor) activityProcessor;
                processor.setActivity(activity);
            }
            try {
                activityProcessor.execute();
            } catch (Exception e) {
                // Activity処理は失敗しても、いったん無視する
                LOG.error("error", e);
            }
        }
    }
    
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void processActivity(Activity activity, LoginedUser eventUser, Date eventDateTime) {
        execute(activity, eventUser, eventDateTime, null, null);
    }
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void processActivity(Activity activity, LoginedUser eventUser, Date eventDateTime, KnowledgesEntity knowledge) {
        execute(activity, eventUser, eventDateTime, knowledge, null);
    }
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void processActivity(Activity activity, LoginedUser eventUser, Date eventDateTime, CommentsEntity comment) {
        execute(activity, eventUser, eventDateTime, null, comment);
    }
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void processKnowledgeSaveActivity(LoginedUser eventUser, Date eventDateTime, KnowledgesEntity knowledge) {
        Activity activity = null;
        if (knowledge.getPublicFlag().intValue() == KnowledgeLogic.PUBLIC_FLAG_PUBLIC) {
            activity = Activity.KNOWLEDGE_POST_PUBLIC;
        } else if (knowledge.getPublicFlag().intValue() == KnowledgeLogic.PUBLIC_FLAG_PROTECT) {
            activity = Activity.KNOWLEDGE_POST_PROTECTED;
        } else if (knowledge.getPublicFlag().intValue() == KnowledgeLogic.PUBLIC_FLAG_PRIVATE) {
            activity = Activity.KNOWLEDGE_POST_PRIVATE;
        }
        if (activity == null) {
            LOG.warn("invalid public flag. knowledge[]" + knowledge.getKnowledgeId());
            return;
        }
        execute(activity, eventUser, eventDateTime, knowledge, null);
    }

    public List<ContributionPointHistory> getUserPointHistoriesByDate(Integer userId, UserConfigs userConfigs) {
        return PointUserHistoriesDao.get().selectPointHistoriesByDate(userId, userConfigs);
    }

    private String getDisplayDate(Date date, UserConfigs userConfigs) {
        return DateConvertLogic.get().convertDate(date, userConfigs.getLocale(), String.valueOf(userConfigs.getTimezoneOffset()));
    }
    
    private String getActivityMsg(PointUserHistoriesEntity history, Map<Long, ActivitiesEntity> activities, UserConfigs userConfigs) {
        Resources resources = Resources.getInstance(userConfigs.getLocale());
        ActivitiesEntity activity = activities.get(history.getActivityNo());
        if (activity == null) {
            return "";
        }
        if (history.getType() == ActivityProcessor.TYPE_KNOWLEDGE_DO_INSERT) {
            return resources.getResource("knowledge.activity.type.11.do.insert", activity.getTarget());
        } else if (history.getType() == ActivityProcessor.TYPE_KNOWLEDGE_DO_SHOW) {
            return resources.getResource("knowledge.activity.type.21.do.show", activity.getTarget());
        } else if (history.getType() == ActivityProcessor.TYPE_KNOWLEDGE_SHOWN_BY_OHER) {
            return resources.getResource("knowledge.activity.type.22.shown", activity.getTarget(), activity.getUserName());
        
        } else if (history.getType() == ActivityProcessor.TYPE_KNOWLEDGE_DO_LIKE) {
            return resources.getResource("knowledge.activity.type.31.do.like", activity.getTarget(), activity.getUserName());
        } else if (history.getType() == ActivityProcessor.TYPE_KNOWLEDGE_LIKED_BY_OHER) {
            return resources.getResource("knowledge.activity.type.32.liked", activity.getTarget(), activity.getUserName());
        } else if (history.getType() == ActivityProcessor.TYPE_KNOWLEDGE_DO_STOCK) {
            return resources.getResource("knowledge.activity.type.41.do.stock", activity.getTarget(), activity.getUserName());
        } else if (history.getType() == ActivityProcessor.TYPE_KNOWLEDGE_STOCKED_BY_OHER) {
            return resources.getResource("knowledge.activity.type.42.stocked", activity.getTarget(), activity.getUserName());
        }
        return "";
    }
    public List<ActivityHistory> getUserPointHistoriese(Integer userId, int limit, int offset, UserConfigs userConfigs) {
        List<PointUserHistoriesEntity> histories = PointUserHistoriesDao.get().selectOnUser(userId, limit, offset, Order.DESC);
        List<Long> activityNos = new ArrayList<>();
        for (PointUserHistoriesEntity history : histories) {
            activityNos.add(history.getActivityNo());
        }
        List<ActivitiesEntity> activityList = ActivitiesDao.get().selectOnNos(activityNos);
        Map<Long, ActivitiesEntity> activities = new HashMap<>();
        for (ActivitiesEntity activitiesEntity : activityList) {
            activities.put(activitiesEntity.getActivityNo(), activitiesEntity);
        }
        List<ActivityHistory> list = new ArrayList<>();
        for (PointUserHistoriesEntity history : histories) {
            String msg = getActivityMsg(history, activities, userConfigs);
            if (StringUtils.isNotEmpty(msg)) {
                ActivityHistory activity = new ActivityHistory();
                activity.setDate(history.getInsertDatetime());
                activity.setDispDate(getDisplayDate(history.getInsertDatetime(), userConfigs));
                activity.setMsg(msg);
                list.add(activity);
            }
        }
        return list;
    }

}
