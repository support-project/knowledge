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
import org.support.project.common.util.HtmlUtils;
import org.support.project.common.util.StringUtils;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.config.AppConfig;
import org.support.project.knowledge.config.SystemConfig;
import org.support.project.knowledge.dao.ActivitiesDao;
import org.support.project.knowledge.dao.CommentsDao;
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
import org.support.project.web.dao.SystemConfigsDao;
import org.support.project.web.entity.SystemConfigsEntity;
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
            array.add(KnowledgeSaveActivity.get());
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
        int publicFlag = KnowledgeLogic.PUBLIC_FLAG_PRIVATE;
        if (knowledge.getPublicFlag() != null) {
            publicFlag = knowledge.getPublicFlag();
        }
        if (publicFlag == KnowledgeLogic.PUBLIC_FLAG_PUBLIC) {
            activity = Activity.KNOWLEDGE_POST_PUBLIC;
        } else if (publicFlag == KnowledgeLogic.PUBLIC_FLAG_PROTECT) {
            activity = Activity.KNOWLEDGE_POST_PROTECTED;
        } else if (publicFlag == KnowledgeLogic.PUBLIC_FLAG_PRIVATE) {
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
        return DateConvertLogic.get().convertDate(date, userConfigs.getLocale(), userConfigs.getTimezoneOffset());
    }
    private String convKnowledgeLink(String systemUrl, String target) {
        StringBuilder builder = new StringBuilder();
        builder.append("<a href=\"");
        builder.append(systemUrl);
        builder.append("open.knowledge/view/");
        builder.append(target);
        builder.append("\">#");
        builder.append(target);
        builder.append("</a>");
        return builder.toString();
    }
    private String convUserLink(String systemUrl, Integer userID, String userName) {
        StringBuilder builder = new StringBuilder();
        builder.append("<a href=\"");
        builder.append(systemUrl);
        builder.append("open.account/info/");
        builder.append(userID);
        builder.append("\">");
        builder.append(HtmlUtils.escapeHTML(userName));
        builder.append("</a>");
        return builder.toString();
    }
    private String getActivityMsg(PointUserHistoriesEntity history, Map<Long, ActivitiesEntity> activities, UserConfigs userConfigs, String systemUrl) {
        Resources resources = Resources.getInstance(userConfigs.getLocale());
        ActivitiesEntity activity = activities.get(history.getActivityNo());
        if (activity == null) {
            return "";
        }
        if (history.getType() == ActivityProcessor.TYPE_KNOWLEDGE_DO_INSERT) {
            return resources.getResource("knowledge.activity.type.11.do.insert", convKnowledgeLink(systemUrl, activity.getTarget()));
        } else if (history.getType() == ActivityProcessor.TYPE_KNOWLEDGE_DO_SHOW) {
            return resources.getResource("knowledge.activity.type.21.do.show", convKnowledgeLink(systemUrl, activity.getTarget()));
        } else if (history.getType() == ActivityProcessor.TYPE_KNOWLEDGE_SHOWN_BY_OHER) {
            return resources.getResource("knowledge.activity.type.22.shown", convKnowledgeLink(systemUrl, activity.getTarget()),
                    convUserLink(systemUrl, activity.getInsertUser(), activity.getUserName()));
        } else if (history.getType() == ActivityProcessor.TYPE_KNOWLEDGE_DO_LIKE) {
            return resources.getResource("knowledge.activity.type.31.do.like", convKnowledgeLink(systemUrl, activity.getTarget()),
                    convUserLink(systemUrl, activity.getInsertUser(), activity.getUserName()));
        } else if (history.getType() == ActivityProcessor.TYPE_KNOWLEDGE_LIKED_BY_OHER) {
            return resources.getResource("knowledge.activity.type.32.liked", convKnowledgeLink(systemUrl, activity.getTarget()),
                    convUserLink(systemUrl, activity.getInsertUser(), activity.getUserName()));
        } else if (history.getType() == ActivityProcessor.TYPE_KNOWLEDGE_DO_STOCK) {
            return resources.getResource("knowledge.activity.type.41.do.stock", convKnowledgeLink(systemUrl, activity.getTarget()),
                    convUserLink(systemUrl, activity.getInsertUser(), activity.getUserName()));
        } else if (history.getType() == ActivityProcessor.TYPE_KNOWLEDGE_STOCKED_BY_OHER) {
            return resources.getResource("knowledge.activity.type.42.stocked", convKnowledgeLink(systemUrl, activity.getTarget()),
                    convUserLink(systemUrl, activity.getInsertUser(), activity.getUserName()));
        } else if (history.getType() == ActivityProcessor.TYPE_KNOWLEDGE_DO_ANSWER) {
            return resources.getResource("knowledge.activity.type.51.do.ansewer", convKnowledgeLink(systemUrl, activity.getTarget()),
                    convUserLink(systemUrl, activity.getInsertUser(), activity.getUserName()));
        } else if (history.getType() == ActivityProcessor.TYPE_KNOWLEDGE_ANSWERD_BY_OHER) {
            return resources.getResource("knowledge.activity.type.52.answered", convKnowledgeLink(systemUrl, activity.getTarget()),
                    convUserLink(systemUrl, activity.getInsertUser(), activity.getUserName()));
        } else if (history.getType() == ActivityProcessor.TYPE_KNOWLEDGE_DO_JOIN_EVENT) {
            return resources.getResource("knowledge.activity.type.61.do.join", convKnowledgeLink(systemUrl, activity.getTarget()),
                    convUserLink(systemUrl, activity.getInsertUser(), activity.getUserName()));
        } else if (history.getType() == ActivityProcessor.TYPE_KNOWLEDGE_JOINED_BY_OHER) {
            return resources.getResource("knowledge.activity.type.62.joined", convKnowledgeLink(systemUrl, activity.getTarget()),
                    convUserLink(systemUrl, activity.getInsertUser(), activity.getUserName()));

        } else if (history.getType() == ActivityProcessor.TYPE_KNOWLEDGE_DO_POST_PUBLIC) {
            return resources.getResource("knowledge.activity.type.101.do.post.public", convKnowledgeLink(systemUrl, activity.getTarget()),
                    convUserLink(systemUrl, activity.getInsertUser(), activity.getUserName()));
        } else if (history.getType() == ActivityProcessor.TYPE_KNOWLEDGE_DO_POST_PROTECT) {
            return resources.getResource("knowledge.activity.type.111.do.post.protect", convKnowledgeLink(systemUrl, activity.getTarget()),
                    convUserLink(systemUrl, activity.getInsertUser(), activity.getUserName()));
        } else if (history.getType() == ActivityProcessor.TYPE_KNOWLEDGE_DO_POST_PRIVATE) {
            return resources.getResource("knowledge.activity.type.121.do.post.private", convKnowledgeLink(systemUrl, activity.getTarget()),
                    convUserLink(systemUrl, activity.getInsertUser(), activity.getUserName()));

        } else if (history.getType() >= ActivityProcessor.TYPE_COMMENT_DO_INSERT) {
            CommentsEntity comment = CommentsDao.get().selectOnKey(new Long(activity.getTarget()));
            if (comment != null) {
                if (history.getType() == ActivityProcessor.TYPE_COMMENT_DO_INSERT) {
                    return resources.getResource("knowledge.activity.type.1011.do.comment.insert",
                            convKnowledgeLink(systemUrl, String.valueOf(comment.getKnowledgeId())),
                            convUserLink(systemUrl, activity.getInsertUser(), activity.getUserName()));
                } else if (history.getType() == ActivityProcessor.TYPE_COMMENT_DO_LIKE) {
                    return resources.getResource("knowledge.activity.type.1031.do.comment.like",
                            convKnowledgeLink(systemUrl, String.valueOf(comment.getKnowledgeId())),
                            convUserLink(systemUrl, activity.getInsertUser(), activity.getUserName()));
                } else if (history.getType() == ActivityProcessor.TYPE_COMMENT_LIKED_BY_OHER) {
                    return resources.getResource("knowledge.activity.type.1032.comment.liked",
                            convKnowledgeLink(systemUrl, String.valueOf(comment.getKnowledgeId())),
                            convUserLink(systemUrl, activity.getInsertUser(), activity.getUserName()));
                }
            }
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
        
        SystemConfigsDao dao = SystemConfigsDao.get();
        SystemConfigsEntity config = dao.selectOnKey(SystemConfig.SYSTEM_URL, AppConfig.get().getSystemName());
        StringBuilder builder = new StringBuilder();
        if (config != null) {
            builder.append(config.getConfigValue());
            if (!config.getConfigValue().endsWith("/")) {
                builder.append("/");
            }
        }
        List<ActivityHistory> list = new ArrayList<>();
        for (PointUserHistoriesEntity history : histories) {
            String msg = getActivityMsg(history, activities, userConfigs, builder.toString());
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
