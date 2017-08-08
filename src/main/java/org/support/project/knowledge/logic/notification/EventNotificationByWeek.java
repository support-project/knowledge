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
public class EventNotificationByWeek extends AbstractEventNotification {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(EventNotificationByWeek.class);
    /** インスタンス取得 */
    public static EventNotificationByWeek get() {
        return Container.getComp(EventNotificationByWeek.class);
    }
    @Override
    public void notify(EventsEntity event, KnowledgesEntity knowledge, List<KnowledgeItemValuesEntity> values, Calendar now) {
        int timing = NOTIFY_STATUS_BEFORE_WEEK;
        if (event.getNotifyStatus() >= timing) {
            // 既に通知済
            return;
        }
        //日曜日？（イベントのタイムゾーンで）
        TimeZone timezone = TimeZone.getTimeZone(event.getTimeZone());
        Calendar today = Calendar.getInstance(timezone);
        today.setTimeInMillis(now.getTimeInMillis());
        if (today.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
            // 日曜日でない場合は通知しない
            return;
        }
        LOG.info("イベントのメール通知 [ID] " + knowledge.getKnowledgeId() + " [Timing] 今週開催");
        sendNotify(event, knowledge, values, timing);
    }

}
