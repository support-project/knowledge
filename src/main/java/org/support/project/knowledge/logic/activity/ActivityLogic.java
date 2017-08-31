package org.support.project.knowledge.logic.activity;

import java.util.ArrayList;
import java.util.List;

import org.support.project.aop.Aspect;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.entity.CommentsEntity;
import org.support.project.knowledge.entity.KnowledgesEntity;
import org.support.project.web.bean.LoginedUser;

@DI(instance = Instance.Singleton)
public class ActivityLogic {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(ActivityLogic.class);

    public static ActivityLogic get() {
        return Container.getComp(ActivityLogic.class);
    }
    
    private List<ActivityProcessor> getActivityProcessors(Activity activity) {
        List<ActivityProcessor> array = new ArrayList<>();
        if (activity == Activity.KNOWLEDGE_INSERT) {
            array.add(KnowledgeInsertActivity.get());
        }
        
        
        return array;
    }
    private void execute(Activity activity, LoginedUser user, KnowledgesEntity knowledge, CommentsEntity comment) {
        List<ActivityProcessor> processors = this.getActivityProcessors(activity);
        for (ActivityProcessor activityProcessor : processors) {
            if (activityProcessor instanceof AbstractActivityProcessor) {
                AbstractActivityProcessor processor = (AbstractActivityProcessor) activityProcessor;
                processor.setUser(user);
            }
            if (activityProcessor instanceof AbstractAddPointForKnowledgeProcessor) {
                if (knowledge == null) {
                    LOG.warn("bad parameter [knowledge]");
                    continue;
                }
                AbstractAddPointForKnowledgeProcessor processor = (AbstractAddPointForKnowledgeProcessor) activityProcessor;
                processor.setKnowledge(knowledge);
            } else if (activityProcessor instanceof AbstractAddPointForCommentProcessor) {
                if (knowledge == null) {
                    LOG.warn("bad parameter [comment]");
                    continue;
                }
                AbstractAddPointForCommentProcessor processor = (AbstractAddPointForCommentProcessor) activityProcessor;
                processor.setComment(comment);
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
    public void processActivity(Activity activity, LoginedUser user) {
        execute(activity, user, null, null);
    }
    
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void processActivity(Activity activity, LoginedUser user, KnowledgesEntity knowledge) {
        execute(activity, user, knowledge, null);
    }
    
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void processActivity(Activity activity, LoginedUser user, CommentsEntity comment) {
        execute(activity, user, null, comment);
    }
}
