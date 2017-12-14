package org.support.project.knowledge.logic.notification;

import java.util.Calendar;
import java.util.List;

import org.support.project.aop.Aspect;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.entity.EventsEntity;
import org.support.project.knowledge.entity.KnowledgeItemValuesEntity;
import org.support.project.knowledge.entity.KnowledgesEntity;

@DI(instance = Instance.Singleton)
public class EventNotificationByMinute extends AbstractEventNotification {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(EventNotificationByMinute.class);
    /** インスタンス取得 */
    public static EventNotificationByMinute get() {
        return Container.getComp(EventNotificationByMinute.class);
    }
    
    @Override
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void notify(EventsEntity event, KnowledgesEntity knowledge, List<KnowledgeItemValuesEntity> values, Calendar now) {
        int timing = NOTIFY_STATUS_BEFORE_MIN;
        if (event.getNotifyStatus() >= timing) {
            // 既に通知済
            return;
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("開始までの時間: " + (event.getStartDateTime().getTime() - now.getTimeInMillis()));
        }
        // 30分を切った？
        if (event.getStartDateTime().getTime() <= now.getTimeInMillis() ||
                event.getStartDateTime().getTime() - now.getTimeInMillis() > (1000 * 60 * 30)) {
            return;
        }
        LOG.info("イベントのメール通知 [ID] " + knowledge.getKnowledgeId() + " [Timing] 30分前");
        sendNotify(event, knowledge, values, timing);
    }

}
