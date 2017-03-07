package org.support.project.knowledge.logic;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import org.support.project.aop.Aspect;
import org.support.project.common.config.Resources;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.StringUtils;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.config.AppConfig;
import org.support.project.knowledge.dao.EventsDao;
import org.support.project.knowledge.dao.KnowledgeItemValuesDao;
import org.support.project.knowledge.dao.KnowledgesDao;
import org.support.project.knowledge.dao.ParticipantsDao;
import org.support.project.knowledge.dao.StockKnowledgesDao;
import org.support.project.knowledge.entity.EventsEntity;
import org.support.project.knowledge.entity.KnowledgeItemValuesEntity;
import org.support.project.knowledge.entity.KnowledgesEntity;
import org.support.project.knowledge.entity.MailLocaleTemplatesEntity;
import org.support.project.knowledge.entity.ParticipantsEntity;
import org.support.project.knowledge.entity.StockKnowledgesEntity;
import org.support.project.web.bean.LoginedUser;
import org.support.project.web.dao.MailConfigsDao;
import org.support.project.web.dao.MailsDao;
import org.support.project.web.dao.UsersDao;
import org.support.project.web.entity.MailConfigsEntity;
import org.support.project.web.entity.MailsEntity;
import org.support.project.web.entity.UsersEntity;

@DI(instance = Instance.Singleton)
public class MailEventLogic extends MailLogic {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(MailEventLogic.class);
    
    
    public static final int NOTIFY_STATUS_NOT_SEND = 0;
    /** 今週のイベント通知(日曜に通知) */
    public static final int NOTIFY_STATUS_BEFORE_WEEK = 10;
    /** 本日のイベント通知 */
    public static final int NOTIFY_STATUS_BEFORE_DATE = 20;
    /** 30分前のイベント通知 */
    public static final int NOTIFY_STATUS_BEFORE_MIN = 30;
    /** ステータス：ストック中 */
    public static final int STATUS_STOCKED = 100;
    
    
    public static MailEventLogic get() {
        return Container.getComp(MailEventLogic.class);
    }
    
    
    /**
     * 登録されているイベントの開催通知
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void notifyEvents() {
        MailConfigsDao mailConfigsDao = MailConfigsDao.get();
        MailConfigsEntity mailConfigsEntity = mailConfigsDao.selectOnKey(AppConfig.get().getSystemName());
        if (mailConfigsEntity == null) {
            // メールの設定が登録されていなければ、送信処理は終了
            LOG.info("mail config is not exists.");
            return;
        }
        
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
            notifyWeek(event, knowledge, values, now, mailConfigsEntity);
            notifyDate(event, knowledge, values, now, mailConfigsEntity);
            notifyMin(event, knowledge, values, now, mailConfigsEntity);
        }

    }

    private void notifyMin(EventsEntity event, KnowledgesEntity knowledge, List<KnowledgeItemValuesEntity> values, Calendar now, MailConfigsEntity mailConfigsEntity) {
        // TODO Auto-generated method stub
    }

    private void notifyDate(EventsEntity event, KnowledgesEntity knowledge, List<KnowledgeItemValuesEntity> values, Calendar now, MailConfigsEntity mailConfigsEntity) {
        // TODO Auto-generated method stub
    }

    private void notifyWeek(EventsEntity event, KnowledgesEntity knowledge, List<KnowledgeItemValuesEntity> values, Calendar now, MailConfigsEntity mailConfigsEntity) {
        if (event.getNotifyStatus() >= NOTIFY_STATUS_BEFORE_WEEK) {
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
        
        List<Integer> userIds = new ArrayList<>();
        // 参加者に通知
        List<ParticipantsEntity> participants = ParticipantsDao.get().selectOnKnowledgeId(event.getKnowledgeId());
        for (ParticipantsEntity participant : participants) {
            if (!userIds.contains(participant.getUserId())) {
                UsersEntity user = UsersDao.get().selectOnKey(participant.getUserId());
                if (user != null) {
                    this.sendNotifyEvent(event, knowledge, values, user, participant.getStatus(), NOTIFY_STATUS_BEFORE_WEEK);
                    userIds.add(participant.getUserId());
                }
            }
        }
        // ストックに入れているユーザに通知
        List<StockKnowledgesEntity> stocks = StockKnowledgesDao.get().selectOnKnowledgeId(event.getKnowledgeId());
        for (StockKnowledgesEntity stockKnowledgesEntity : stocks) {
            if (!userIds.contains(stockKnowledgesEntity.getInsertUser())) {
                UsersEntity user = UsersDao.get().selectOnKey(stockKnowledgesEntity.getInsertUser());
                if (user != null) {
                    this.sendNotifyEvent(event, knowledge, values, user, STATUS_STOCKED, NOTIFY_STATUS_BEFORE_WEEK);
                    userIds.add(stockKnowledgesEntity.getInsertUser());
                }
            }
        }
        event.setNotifyStatus(NOTIFY_STATUS_BEFORE_WEEK);
        EventsDao.get().save(event);
    }


    private void sendNotifyEvent(EventsEntity event, KnowledgesEntity knowledge, List<KnowledgeItemValuesEntity> values, 
            UsersEntity user, int status, int timing) {
        if (!StringUtils.isEmailAddress(user.getMailAddress())) {
            // 送信先のメールアドレスが不正なので、送信処理は終了
            LOG.warn("mail targget [" + user.getMailAddress() + "] is wrong.");
            return;
        }
        MailLocaleTemplatesEntity template = load(user.getLocale(), MailLogic.NOTIFY_EVENT);
        
        String date = "";
        String start = "";
        String end = "";
        String place = "";
        for (KnowledgeItemValuesEntity item : values) {
            if (item.getItemNo() == EventsLogic.ITEM_NO_DATE) {
                date = item.getItemValue();
            } else if (item.getItemNo() == EventsLogic.ITEM_NO_START) {
                start = item.getItemValue();
            } else if (item.getItemNo() == EventsLogic.ITEM_NO_END) {
                end = item.getItemValue();
            } else if (item.getItemNo() == EventsLogic.ITEM_NO_PLACE) {
                place = item.getItemValue();
            }
        }
        Resources resources = Resources.getInstance(user.getLocale());
        String statusLabel = "";
        if (status == EventsLogic.STATUS_PARTICIPATION) {
            statusLabel = resources.getResource("knowledge.event.label.status.participation");
        } else if (status == EventsLogic.STATUS_WAIT_CANSEL) {
            statusLabel = resources.getResource("knowledge.event.label.status.wait.cansel");
        } else if (status == STATUS_STOCKED) {
            statusLabel = resources.getResource("knowledge.event.label.status.stocked");
        }
        String timingLabel = "";
        if (timing ==  NOTIFY_STATUS_BEFORE_WEEK) {
            timingLabel = resources.getResource("knowledge.event.label.timing.week");
        } else if (timing == NOTIFY_STATUS_BEFORE_DATE) {
            timingLabel = resources.getResource("knowledge.event.label.timing.tody");
        } else if (timing == NOTIFY_STATUS_BEFORE_MIN) {
            timingLabel = resources.getResource("knowledge.event.label.timing.soon");
        }
        
        MailsEntity mailsEntity = new MailsEntity();
        String mailId = idGenu("Notify");
        mailsEntity.setMailId(mailId);
        mailsEntity.setStatus(MailLogic.MAIL_STATUS_UNSENT);
        mailsEntity.setToAddress(user.getMailAddress());
        mailsEntity.setToName(user.getUserName());
        
        String title = template.getTitle();
        title = title.replace("{KnowledgeId}", knowledge.getKnowledgeId().toString());
        title = title.replace("{KnowledgeTitle}", StringUtils.abbreviate(knowledge.getTitle(), 80));
        title = title.replace("{Date}", date);
        title = title.replace("{Start}", start);
        title = title.replace("{End}", end);
        title = title.replace("{Place}", place);
        mailsEntity.setTitle(title);
        
        String contents = template.getContent();
        contents = contents.replace("{KnowledgeId}", knowledge.getKnowledgeId().toString());
        contents = contents.replace("{KnowledgeTitle}", StringUtils.abbreviate(knowledge.getTitle(), 80));
        contents = contents.replace("{Date}", date);
        contents = contents.replace("{Start}", start);
        contents = contents.replace("{End}", end);
        contents = contents.replace("{Place}", place);
        contents = contents.replace("{URL}", NotifyLogic.get().makeURL(knowledge.getKnowledgeId()));
        contents = contents.replace("{Status}", statusLabel);
        contents = contents.replace("{Timing}", timingLabel);
        contents = contents.replace("{UserName}", user.getUserName());
        mailsEntity.setContent(contents);
        MailsDao.get().insert(mailsEntity);
    }
    



}
