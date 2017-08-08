package org.support.project.knowledge.logic;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import org.support.project.aop.Aspect;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.StringUtils;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.dao.EventsDao;
import org.support.project.knowledge.dao.KnowledgeItemValuesDao;
import org.support.project.knowledge.dao.KnowledgesDao;
import org.support.project.knowledge.entity.EventsEntity;
import org.support.project.knowledge.entity.KnowledgeItemValuesEntity;
import org.support.project.knowledge.entity.KnowledgesEntity;
import org.support.project.knowledge.logic.notification.EventNotificationByDate;
import org.support.project.knowledge.logic.notification.EventNotificationByMinute;
import org.support.project.knowledge.logic.notification.EventNotificationByWeek;
import org.support.project.web.bean.LoginedUser;

@DI(instance = Instance.Singleton)
public class MailEventLogic extends MailLogic {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(MailEventLogic.class);
    
    public static MailEventLogic get() {
        return Container.getComp(MailEventLogic.class);
    }
    
    public static final int NOTIFY_STATUS_NOT_SEND = 0;
    /** 今週のイベント通知(日曜に通知) */
    public static final int NOTIFY_STATUS_BEFORE_WEEK = 10;
    /** 本日のイベント通知 */
    public static final int NOTIFY_STATUS_BEFORE_DATE = 20;
    /** 30分前のイベント通知 */
    public static final int NOTIFY_STATUS_BEFORE_MIN = 30;
    /** ステータス：ストック中 */
    public static final int STATUS_STOCKED = 100;
    
    /**
     * 登録されているイベントの開催通知
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void notifyEvents() {
        // 登録されているイベントを一週間分取得
        // タイムゾーンを考慮して取得するため、少し範囲広めで取得する。
        // GMT で先週の土曜〜次週の月曜
        LoginedUser admin = UserLogicEx.get().getAdminUser(); // バッチ起動であるため、管理者権限でデータを取得する
        
        TimeZone gmt = TimeZone.getTimeZone("GMT");
        Calendar now = Calendar.getInstance(gmt);
        Calendar start = Calendar.getInstance(gmt);
        Calendar end = Calendar.getInstance(gmt);
        
        start.set(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DATE), 0, 0, 0);
        start.set(Calendar.MILLISECOND, 0);
        LOG.debug("曜日: " + now.get(Calendar.DAY_OF_WEEK));
        int diff = now.get(Calendar.DAY_OF_WEEK);
        start.add(Calendar.DATE, -diff);
        end.setTime(start.getTime());
        end.add(Calendar.DATE, 9);
        
        EventsLogic.get().logging(now);
        EventsLogic.get().logging(start);
        EventsLogic.get().logging(end);
        
        List<EventsEntity> events = EventsDao.get().selectAccessAbleEvents(start, end, admin);
        
        for (EventsEntity event : events) {
            if (event.getNotifyStatus() == null) {
                event.setNotifyStatus(NOTIFY_STATUS_NOT_SEND);
            }
            if (StringUtils.isEmpty(event.getTimeZone())) {
                event.setTimeZone("GMT");
            }
            KnowledgesEntity knowledge = KnowledgesDao.get().selectOnKey(event.getKnowledgeId());
            if (knowledge == null) {
                continue;
            }
            List<KnowledgeItemValuesEntity> values = KnowledgeItemValuesDao.get().selectOnKnowledgeId(event.getKnowledgeId());
            
            EventNotificationByMinute.get().notify(event, knowledge, values, now);
            EventNotificationByDate.get().notify(event, knowledge, values, now);
            EventNotificationByWeek.get().notify(event, knowledge, values, now);
        }
    }

}
