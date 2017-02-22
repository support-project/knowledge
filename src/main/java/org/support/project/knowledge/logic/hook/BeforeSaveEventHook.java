package org.support.project.knowledge.logic.hook;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.support.project.common.config.Resources;
import org.support.project.common.util.StringUtils;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.entity.EventsEntity;
import org.support.project.knowledge.entity.KnowledgesEntity;
import org.support.project.knowledge.entity.TemplateItemsEntity;
import org.support.project.knowledge.logic.TemplateLogic;
import org.support.project.knowledge.vo.KnowledgeData;
import org.support.project.web.bean.LoginedUser;
import org.support.project.web.bean.MessageResult;
import org.support.project.web.common.HttpStatus;
import org.support.project.web.config.MessageStatus;
import org.support.project.web.exception.InvalidParamException;

@DI(instance = Instance.Singleton)
public class BeforeSaveEventHook implements BeforeSaveHook {
    public static final int ITEM_NO_DATE = 0;
    public static final int ITEM_NO_START = 1;
    public static final int ITEM_NO_END = 2;
    public static final int ITEM_NO_TIMEZONE = 3;
    
    public Date parseDate(KnowledgeData knowledgeData, LoginedUser loginedUser) throws InvalidParamException {
        EventsEntity event = new EventsEntity();
        event.setKnowledgeId(knowledgeData.getKnowledge().getKnowledgeId());
        List<TemplateItemsEntity> items = knowledgeData.getTemplate().getItems();
        if (items == null) {
            Resources resources = Resources.getInstance(loginedUser.getLocale());
            throw new InvalidParamException(
                    new MessageResult(MessageStatus.Warning, HttpStatus.SC_400_BAD_REQUEST,
                            resources.getResource("errors.required", "Date,Start,End,Timezone"), ""));
        }
        String date = null;
        String start = null;
        String end = null;
        String timezone = null;
        for (TemplateItemsEntity item : items) {
            if (item.getItemNo() == ITEM_NO_DATE) {
                date = item.getItemValue();
            } else if (item.getItemNo() == ITEM_NO_START) {
                start = item.getItemValue();
            } else if (item.getItemNo() == ITEM_NO_END) {
                end = item.getItemValue();
            } else if (item.getItemNo() == ITEM_NO_TIMEZONE) {
                timezone = item.getItemValue();
            }
        }
        Resources resources = Resources.getInstance(loginedUser.getLocale());
        if (StringUtils.isEmpty(date) || StringUtils.isEmpty(start) || StringUtils.isEmpty(end) || StringUtils.isEmpty(timezone)) {
            throw new InvalidParamException(
                    new MessageResult(MessageStatus.Warning, HttpStatus.SC_400_BAD_REQUEST,
                            resources.getResource("errors.required", "Date,Start,End,Timezone"), ""));
        }
        String[] zones = TimeZone.getAvailableIDs();
        boolean exist = false;
        for (String zone : zones) {
            if (timezone.equals(zone)) {
                exist = true;
            }
        }
        if (!exist) {
            throw new InvalidParamException(
                    new MessageResult(MessageStatus.Warning, HttpStatus.SC_400_BAD_REQUEST,
                            resources.getResource("errors.invalid", "Timezone"), ""));
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        try {
            sdf.setTimeZone(TimeZone.getTimeZone(timezone));
            return sdf.parse(date + " " + start);
        } catch (ParseException e) {
            throw new InvalidParamException(
                    new MessageResult(MessageStatus.Warning, HttpStatus.SC_400_BAD_REQUEST,
                            resources.getResource("errors.invalid", "Date,Start"), ""));
        }
    }
    
    
    @Override
    public void beforeSave(KnowledgeData knowledgeData, KnowledgesEntity db, LoginedUser loginedUser) throws Exception {
        // イベントの場合、拡張項目は必須とする
        if (TemplateLogic.TYPE_ID_EVENT == knowledgeData.getKnowledge().getTypeId()) {
            parseDate(knowledgeData, loginedUser);
        }
    }


}
