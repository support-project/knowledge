package org.support.project.knowledge.logic.hook;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

import org.support.project.common.config.Resources;
import org.support.project.common.util.StringUtils;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.entity.EventsEntity;
import org.support.project.knowledge.entity.KnowledgesEntity;
import org.support.project.knowledge.entity.TemplateItemsEntity;
import org.support.project.knowledge.logic.EventsLogic;
import org.support.project.knowledge.logic.MailEventLogic;
import org.support.project.knowledge.logic.TemplateLogic;
import org.support.project.knowledge.logic.TimeZoneLogic;
import org.support.project.knowledge.vo.KnowledgeData;
import org.support.project.web.bean.AccessUser;
import org.support.project.web.bean.MessageResult;
import org.support.project.web.common.HttpStatus;
import org.support.project.web.config.MessageStatus;
import org.support.project.web.exception.InvalidParamException;

@DI(instance = Instance.Singleton)
public class BeforeSaveEventHook implements BeforeSaveHook {
    public static final int ITEM_NO_DATE = EventsLogic.ITEM_NO_DATE;
    public static final int ITEM_NO_START = EventsLogic.ITEM_NO_START;
    public static final int ITEM_NO_END = EventsLogic.ITEM_NO_END;
    public static final int ITEM_NO_TIMEZONE = EventsLogic.ITEM_NO_TIMEZONE;
    public static final int ITEM_NO_THE_NUMBER_TO_BE_ACCEPTED = EventsLogic.ITEM_NO_THE_NUMBER_TO_BE_ACCEPTED;
    
    public EventsEntity parseDate(KnowledgeData knowledgeData, AccessUser loginedUser) throws InvalidParamException {
        EventsEntity event = new EventsEntity();
        event.setKnowledgeId(knowledgeData.getKnowledge().getKnowledgeId());
        List<TemplateItemsEntity> items = knowledgeData.getTemplate().getItems();
        if (items == null) {
            Resources resources = Resources.getInstance(loginedUser.getLocale());
            throw new InvalidParamException(
                    new MessageResult(MessageStatus.Warning, HttpStatus.SC_400_BAD_REQUEST,
                            resources.getResource("errors.invalid", "Request data"), ""));
        }
        String date = null;
        String start = null;
        String end = null;
        String timezone = null;
        String limit = null;
        String dateLabel = null;
        String startLabel = null;
        String endLabel = null;
        String timezoneLabel = null;
        String limitLabel = null;
        
        for (TemplateItemsEntity item : items) {
            if (item.getItemNo() == ITEM_NO_DATE) {
                date = item.getItemValue();
                dateLabel = item.getItemName();
            } else if (item.getItemNo() == ITEM_NO_START) {
                start = item.getItemValue();
                startLabel = item.getItemName();
            } else if (item.getItemNo() == ITEM_NO_END) {
                end = item.getItemValue();
                endLabel = item.getItemName();
            } else if (item.getItemNo() == ITEM_NO_TIMEZONE) {
                timezone = item.getItemValue();
                timezoneLabel = item.getItemName();
            } else if (item.getItemNo() == ITEM_NO_THE_NUMBER_TO_BE_ACCEPTED) {
                limit = item.getItemValue();
                limitLabel = item.getItemName();
            }
        }
        Resources resources = Resources.getInstance(loginedUser.getLocale());
        if (StringUtils.isEmpty(date) || StringUtils.isEmpty(start) || StringUtils.isEmpty(end) 
                || StringUtils.isEmpty(timezone) || StringUtils.isEmpty(limit) ) {
            StringBuilder label = new StringBuilder();
            label.append(dateLabel).append(",").append(startLabel).append(",").append(endLabel).append(",");
            label.append(timezoneLabel).append(",").append(limitLabel);
            
            throw new InvalidParamException(
                    new MessageResult(MessageStatus.Warning, HttpStatus.SC_400_BAD_REQUEST,
                            resources.getResource("errors.required", label.toString()), ""));
        }
        boolean exist = TimeZoneLogic.get().exist(timezone);
        if (!exist) {
            throw new InvalidParamException(
                    new MessageResult(MessageStatus.Warning, HttpStatus.SC_400_BAD_REQUEST,
                            resources.getResource("errors.invalid", timezoneLabel), ""));
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        try {
            sdf.setTimeZone(TimeZone.getTimeZone(timezone));
            
            event.setStartDateTime(new Timestamp(sdf.parse(date + " " + start).getTime()));
            event.setTimeZone(timezone);
            event.setNotifyStatus(MailEventLogic.NOTIFY_STATUS_NOT_SEND);

            return event;
        } catch (ParseException e) {
            throw new InvalidParamException(
                    new MessageResult(MessageStatus.Warning, HttpStatus.SC_400_BAD_REQUEST,
                            resources.getResource("errors.invalid", dateLabel + "," + startLabel), ""));
        }
    }
    
    
    @Override
    public void beforeSave(KnowledgeData knowledgeData, KnowledgesEntity db, AccessUser loginedUser) throws Exception {
        // イベントの場合、拡張項目は必須とする
        if (TemplateLogic.TYPE_ID_EVENT == knowledgeData.getKnowledge().getTypeId()) {
            parseDate(knowledgeData, loginedUser);
        }
    }


}
