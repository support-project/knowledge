package org.support.project.knowledge.logic;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
import org.support.project.knowledge.dao.ParticipantsDao;
import org.support.project.knowledge.entity.EventsEntity;
import org.support.project.knowledge.entity.KnowledgeItemValuesEntity;
import org.support.project.knowledge.entity.KnowledgesEntity;
import org.support.project.knowledge.entity.ParticipantsEntity;
import org.support.project.knowledge.logic.notification.ParticipateChangeStatusForParticipantNotification;
import org.support.project.knowledge.logic.notification.ParticipateForParticipantNotification;
import org.support.project.knowledge.logic.notification.ParticipateForSponsorNotification;
import org.support.project.knowledge.logic.notification.ParticipateRemoveForSponsorNotification;
import org.support.project.knowledge.vo.Participation;
import org.support.project.knowledge.vo.Participations;
import org.support.project.web.bean.LoginedUser;

@DI(instance = Instance.Singleton)
public class EventsLogic {
    private static final int PAGE_LIMIT = 20;

    /** ログ */
    private static final Log LOG = LogFactory.getLog(EventsLogic.class);
    
    public static final int ITEM_NO_DATE = 0;
    public static final int ITEM_NO_START = 1;
    public static final int ITEM_NO_END = 2;
    public static final int ITEM_NO_TIMEZONE = 3;
    public static final int ITEM_NO_THE_NUMBER_TO_BE_ACCEPTED = 4;
    public static final int ITEM_NO_PLACE = 5;
    
    public static final int STATUS_PARTICIPATION = 2;
    public static final int STATUS_WAIT_CANSEL = 1;
    
    
    public static EventsLogic get() {
        return Container.getComp(EventsLogic.class);
    }

    public void logging(Calendar calendar) {
        if (LOG.isDebugEnabled()) {
            StringBuilder builder = new  StringBuilder();
            builder.append(calendar.getTimeInMillis()).append(" ");
            builder.append(calendar.get(Calendar.YEAR)).append("/")
                .append(calendar.get(Calendar.MONTH) + 1).append("/")
                .append(calendar.get(Calendar.DATE)).append(" ");
            builder.append(calendar.get(Calendar.HOUR_OF_DAY)).append(":")
                .append(calendar.get(Calendar.MINUTE)).append(":")
                .append(calendar.get(Calendar.SECOND)).append(".")
                .append(calendar.get(Calendar.MILLISECOND)).append(" ");
            builder.append(calendar.getTimeZone().getDisplayName());
            builder.append(" ").append("[DAY_OF_WEEK]").append(calendar.get(Calendar.DAY_OF_WEEK));
            LOG.debug(builder.toString());
        }
    }
    
    public List<EventsEntity> eventList(String date, String timezone, LoginedUser loginedUser) throws ParseException {
        DateFormat monthformat = new SimpleDateFormat("yyyyMM");
        Date d = monthformat.parse(date);
        TimeZone tz = TimeZone.getTimeZone(timezone);
        Calendar start = Calendar.getInstance(tz);
        Calendar end = Calendar.getInstance(tz);
        
        TimeZone gmt = TimeZone.getTimeZone("GMT");
        Calendar in = Calendar.getInstance(gmt);
        in.setTime(d);
        
        start.set(in.get(Calendar.YEAR), in.get(Calendar.MONTH), 1, 0, 0, 0);
        start.set(Calendar.MILLISECOND, 0);
        end.set(in.get(Calendar.YEAR), in.get(Calendar.MONTH), in.getActualMaximum(Calendar.DATE), 23, 59, 59);
        end.set(Calendar.MILLISECOND, 999);
        
        logging(in);
        logging(start);
        logging(end);
        
        return EventsDao.get().selectAccessAbleEvents(start, end, loginedUser);
    }

    public List<KnowledgesEntity> eventKnowledgeList(String date, String timezone, LoginedUser loginedUser) throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyyMMdd");
        Date d = format.parse(date);
        TimeZone tz = TimeZone.getTimeZone(timezone);
        Calendar start = Calendar.getInstance(tz);
        start.setTime(d);
        start.set(Calendar.HOUR, 0);
        start.set(Calendar.MINUTE, 0);
        start.set(Calendar.SECOND, 0);
        start.set(Calendar.MILLISECOND, 0);
        return EventsDao.get().selectAccessAbleEvents(start, loginedUser, PAGE_LIMIT, 0);
    }

    public Participations isParticipation(Long knowledgeId, Integer userId) {
        Participations participations = new Participations();
        ParticipantsEntity participant = ParticipantsDao.get().selectOnKey(knowledgeId, userId);
        if (participant != null) {
            participations.setStatus(participant.getStatus());
        }
        KnowledgeItemValuesEntity limit = KnowledgeItemValuesDao.get().selectOnKey(
                ITEM_NO_THE_NUMBER_TO_BE_ACCEPTED, knowledgeId, TemplateLogic.TYPE_ID_EVENT);
        if (StringUtils.isInteger(limit.getItemValue())) {
            participations.setLimit(new Integer(limit.getItemValue()));
        }
        List<Participation> participants = ParticipantsDao.get().selectParticipations(knowledgeId);
        for (Participation participation : participants) {
            participation.setPassword("-");
        }
        participations.setCount(participants.size());
        participations.setParticipations(participants);
        return participations;
    }
    
    /**
     * 参加登録
     * @param knowledgeId
     * @param loginUserId
     * @return
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public Boolean participation(Long knowledgeId, Integer userId) {
        ParticipantsEntity participant = ParticipantsDao.get().selectOnKey(knowledgeId, userId);
        if (participant != null) {
            // 既に登録済
            if (participant.getStatus().intValue() == STATUS_PARTICIPATION) {
                return true;
            } else {
                return false;
            }
        }
        KnowledgeItemValuesEntity limit = KnowledgeItemValuesDao.get().selectOnKey(
                ITEM_NO_THE_NUMBER_TO_BE_ACCEPTED, knowledgeId, TemplateLogic.TYPE_ID_EVENT);
        int limitCnt = 0;
        if (StringUtils.isInteger(limit.getItemValue())) {
            limitCnt = new Integer(limit.getItemValue());
        }
        List<Participation> participants = ParticipantsDao.get().selectParticipations(knowledgeId);
        participant = new ParticipantsEntity(knowledgeId, userId);
        if (participants.size() < limitCnt) {
            participant.setStatus(STATUS_PARTICIPATION);
        } else {
            participant.setStatus(STATUS_WAIT_CANSEL);
        }
        ParticipantsDao.get().insert(participant);
        
        // 開催者（＝登録者）へメール通知
        ParticipateForSponsorNotification.get().notify(knowledgeId, userId, participant.getStatus());
        // 参加者へメール通知
        ParticipateForParticipantNotification.get().notify(knowledgeId, userId, participant.getStatus());
        
        return participant.getStatus().intValue() == STATUS_PARTICIPATION;
    }
    
    /**
     * 参加キャンセル
     * @param knowledgeId
     * @param userId
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void removeParticipation(Long knowledgeId, Integer userId) {
        ParticipantsEntity participant = ParticipantsDao.get().selectOnKey(knowledgeId, userId);
        if (participant == null) {
            // 既にキャンセル済
            return;
        }
        ParticipantsDao.get().physicalDelete(participant); // 物理的に削除
        
        // 開催者（＝登録者）へメール通知
        ParticipateRemoveForSponsorNotification.get().notify(knowledgeId, userId, -1);
        
        if (participant.getStatus().intValue() == STATUS_PARTICIPATION) {
            List<Participation> participants = ParticipantsDao.get().selectParticipations(knowledgeId);
            for (int i = 0; i < participants.size(); i++) {
                Participation p = participants.get(i);
                if (p.getStatus().intValue() == STATUS_WAIT_CANSEL) {
                    ParticipantsEntity entity = new ParticipantsEntity(knowledgeId, p.getUserId());
                    entity.setStatus(STATUS_PARTICIPATION);
                    ParticipantsDao.get().update(entity);
                    
                    // キャンセル待ちが参加登録済になった参加者へメール通知
                    ParticipateChangeStatusForParticipantNotification.get().notify(knowledgeId, p.getUserId(), STATUS_PARTICIPATION);
                    
                    break; // キャンセル待ちの１件を本登録に変更
                }
            }
        }
    }

}
