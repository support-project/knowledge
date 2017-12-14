package org.support.project.knowledge.logic.notification;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.entity.EventsEntity;
import org.support.project.knowledge.entity.KnowledgeItemValuesEntity;
import org.support.project.knowledge.entity.KnowledgesEntity;

@DI(instance = Instance.Singleton)
public class EventNotificationByDate extends AbstractEventNotification {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(EventNotificationByDate.class);
    /** インスタンス取得 */
    public static EventNotificationByDate get() {
        return Container.getComp(EventNotificationByDate.class);
    }

    @Override
    public void notify(EventsEntity event, KnowledgesEntity knowledge, List<KnowledgeItemValuesEntity> values, Calendar now) {
        int timing = NOTIFY_STATUS_BEFORE_DATE;
        if (event.getNotifyStatus() >= timing) {
            // 既に通知済
            return;
        }
        // 今日？
        TimeZone timezone = TimeZone.getTimeZone(event.getTimeZone());
        Calendar today = Calendar.getInstance(timezone);
        today.setTimeInMillis(now.getTimeInMillis());
        
        Calendar eventDay = Calendar.getInstance(timezone);
        eventDay.setTimeInMillis(event.getStartDateTime().getTime());
        if (today.get(Calendar.YEAR) != eventDay.get(Calendar.YEAR) ||
                today.get(Calendar.MONTH) != eventDay.get(Calendar.MONTH) ||
                today.get(Calendar.DATE) != eventDay.get(Calendar.DATE)) {
            // イベント当日以外は通知しない
            return;
        }
        LOG.info("イベントのメール通知 [ID] " + knowledge.getKnowledgeId() + " [Timing] 本日開催");
        sendNotify(event, knowledge, values, timing);
    }


}
