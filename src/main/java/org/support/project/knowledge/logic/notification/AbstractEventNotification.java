package org.support.project.knowledge.logic.notification;

import java.util.ArrayList;
import java.util.List;

import org.support.project.common.config.Resources;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.StringUtils;
import org.support.project.knowledge.dao.EventsDao;
import org.support.project.knowledge.dao.KnowledgeItemValuesDao;
import org.support.project.knowledge.dao.ParticipantsDao;
import org.support.project.knowledge.dao.StockKnowledgesDao;
import org.support.project.knowledge.entity.EventsEntity;
import org.support.project.knowledge.entity.KnowledgeItemValuesEntity;
import org.support.project.knowledge.entity.KnowledgesEntity;
import org.support.project.knowledge.entity.MailLocaleTemplatesEntity;
import org.support.project.knowledge.entity.ParticipantsEntity;
import org.support.project.knowledge.entity.StockKnowledgesEntity;
import org.support.project.knowledge.logic.EventsLogic;
import org.support.project.knowledge.logic.MailEventLogic;
import org.support.project.knowledge.logic.MailLogic;
import org.support.project.knowledge.logic.NotificationLogic;
import org.support.project.knowledge.logic.NotifyLogic;
import org.support.project.knowledge.vo.notification.EventInformation;
import org.support.project.web.bean.LoginedUser;
import org.support.project.web.dao.MailsDao;
import org.support.project.web.dao.NotificationsDao;
import org.support.project.web.dao.UsersDao;
import org.support.project.web.entity.MailsEntity;
import org.support.project.web.entity.NotificationsEntity;
import org.support.project.web.entity.UsersEntity;

import net.arnx.jsonic.JSON;

public abstract class AbstractEventNotification extends AbstractNotification implements EventNotification {
    public static final String TEMPLATE_EVENT_SOON = "knowledge.event.label.timing.soon";
    public static final String TEMPLATE_EVENT_TODAY = "knowledge.event.label.timing.tody";
    public static final String TEMPLATE_EVENT_WEEK = "knowledge.event.label.timing.week";

    /** ログ */
    private static final Log LOG = LogFactory.getLog(AbstractEventNotification.class);
    
    protected static final int NOTIFY_STATUS_NOT_SEND = MailEventLogic.NOTIFY_STATUS_NOT_SEND;
    /** 今週のイベント通知(日曜に通知) */
    protected static final int NOTIFY_STATUS_BEFORE_WEEK = MailEventLogic.NOTIFY_STATUS_BEFORE_WEEK;
    /** 本日のイベント通知 */
    protected static final int NOTIFY_STATUS_BEFORE_DATE = MailEventLogic.NOTIFY_STATUS_BEFORE_DATE;
    /** 30分前のイベント通知 */
    protected static final int NOTIFY_STATUS_BEFORE_MIN = MailEventLogic.NOTIFY_STATUS_BEFORE_MIN;
    /** ステータス：ストック中 */
    protected static final int STATUS_STOCKED = MailEventLogic.STATUS_STOCKED;
    
    protected void sendNotify(EventsEntity event, KnowledgesEntity knowledge, List<KnowledgeItemValuesEntity> values, int timing) {
        // 画面での通知の情報を登録
        NotificationsEntity notification = insertNotification(event, knowledge, values, timing);
        
        List<Integer> userIds = new ArrayList<>();
        // 参加者に通知
        List<ParticipantsEntity> participants = ParticipantsDao.get().selectOnKnowledgeId(event.getKnowledgeId());
        for (ParticipantsEntity participant : participants) {
            if (!userIds.contains(participant.getUserId())) {
                UsersEntity user = UsersDao.get().selectOnKey(participant.getUserId());
                if (user != null) {
                    // 通知とユーザの紐付け
                    NotificationLogic.get().insertUserNotification(notification, user);

                    this.sendNotifyEvent(event, knowledge, values, user, participant.getStatus(), timing);
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
                    // 通知とユーザの紐付け
                    NotificationLogic.get().insertUserNotification(notification, user);
                    
                    this.sendNotifyEvent(event, knowledge, values, user, STATUS_STOCKED, timing);
                    userIds.add(stockKnowledgesEntity.getInsertUser());
                }
            }
        }
        event.setNotifyStatus(timing);
        EventsDao.get().save(event);
    }


    private NotificationsEntity insertNotification(EventsEntity event, KnowledgesEntity knowledge, List<KnowledgeItemValuesEntity> values,
            int timing) {
        // 通知情報を作成
        NotificationsEntity notification = new NotificationsEntity();
        // フォーマットのテンプレート
        notification.setTitle(MailLogic.NOTIFY_EVENT);
        EventInformation info = new EventInformation();
        info.setKnowledgeId(knowledge.getKnowledgeId());
        info.setKnowledgeTitle(knowledge.getTitle());
        info.setUpdateUser(knowledge.getUpdateUserName());
        info.setTiming(timing);
        notification.setContent(JSON.encode(info));
        notification = NotificationsDao.get().insert(notification);
        return notification;
    }


    private void sendNotifyEvent(EventsEntity event, KnowledgesEntity knowledge, List<KnowledgeItemValuesEntity> values, 
            UsersEntity user, int status, int timing) {
        if (!StringUtils.isEmailAddress(user.getMailAddress())) {
            // 送信先のメールアドレスが不正なので、送信処理は終了
            LOG.warn("mail targget [" + user.getMailAddress() + "] is wrong.");
            return;
        }
        MailLocaleTemplatesEntity template = MailLogic.get().load(user.getLocale(), MailLogic.NOTIFY_EVENT);
        
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
            timingLabel = resources.getResource(TEMPLATE_EVENT_WEEK);
        } else if (timing == NOTIFY_STATUS_BEFORE_DATE) {
            timingLabel = resources.getResource(TEMPLATE_EVENT_TODAY);
        } else if (timing == NOTIFY_STATUS_BEFORE_MIN) {
            timingLabel = resources.getResource(TEMPLATE_EVENT_SOON);
        }
        
        MailsEntity mailsEntity = new MailsEntity();
        String mailId = idGen("Notify");
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
    
    
    
    @Override
    public void convNotification(NotificationsEntity notificationsEntity, LoginedUser loginedUser, TARGET target) {
        String category = notificationsEntity.getTitle();
        EventInformation knowledge = JSON.decode(notificationsEntity.getContent(), EventInformation.class);
        MailLocaleTemplatesEntity template = MailLogic.get().load(loginedUser.getLocale(), category);
        
        List<KnowledgeItemValuesEntity> values = KnowledgeItemValuesDao.get().selectOnKnowledgeId(knowledge.getKnowledgeId());
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

        String title = template.getTitle();
        title = title.replace("{KnowledgeId}", String.valueOf(knowledge.getKnowledgeId()));
        title = title.replace("{KnowledgeTitle}", StringUtils.abbreviate(knowledge.getKnowledgeTitle(), 80));
        title = title.replace("{Date}", date);
        title = title.replace("{Start}", start);
        title = title.replace("{End}", end);
        title = title.replace("{Place}", place);
        notificationsEntity.setTitle(title);
        
        if (target == TARGET.detail) {
            EventsEntity event = EventsDao.get().selectOnKey(knowledge.getKnowledgeId());
            if (event == null) {
                return;
            }
            int status = -1;
            ParticipantsEntity participant = ParticipantsDao.get().selectOnKey(knowledge.getKnowledgeId(), loginedUser.getUserId());
            if (participant != null) {
                status = participant.getStatus();
            } else {
                // 予約が入っていないということは、ストックしたもの
                status = STATUS_STOCKED;
            }
            
            Resources resources = Resources.getInstance(loginedUser.getLocale());
            String statusLabel = "";
            if (status == EventsLogic.STATUS_PARTICIPATION) {
                statusLabel = resources.getResource("knowledge.event.label.status.participation");
            } else if (status == EventsLogic.STATUS_WAIT_CANSEL) {
                statusLabel = resources.getResource("knowledge.event.label.status.wait.cansel");
            } else if (status == STATUS_STOCKED) {
                statusLabel = resources.getResource("knowledge.event.label.status.stocked");
            }
            
            String timingLabel = "";
            int timing = knowledge.getTiming();
            if (timing ==  NOTIFY_STATUS_BEFORE_WEEK) {
                timingLabel = resources.getResource(TEMPLATE_EVENT_WEEK);
            } else if (timing == NOTIFY_STATUS_BEFORE_DATE) {
                timingLabel = resources.getResource(TEMPLATE_EVENT_TODAY);
            } else if (timing == NOTIFY_STATUS_BEFORE_MIN) {
                timingLabel = resources.getResource(TEMPLATE_EVENT_SOON);
            }

            
            String contents = template.getContent();
            contents = contents.replace("{KnowledgeId}", String.valueOf(knowledge.getKnowledgeId()));
            contents = contents.replace("{KnowledgeTitle}", StringUtils.abbreviate(knowledge.getKnowledgeTitle(), 80));
            contents = contents.replace("{Date}", date);
            contents = contents.replace("{Start}", start);
            contents = contents.replace("{End}", end);
            contents = contents.replace("{Place}", place);
            contents = contents.replace("{URL}", NotifyLogic.get().makeURL(knowledge.getKnowledgeId()));
            contents = contents.replace("{Status}", statusLabel);
            contents = contents.replace("{Timing}", timingLabel);
            contents = contents.replace("{UserName}", loginedUser.getLoginUser().getUserName());
            notificationsEntity.setContent(contents);
        }
    }
}
