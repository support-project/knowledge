package org.support.project.knowledge.control.open;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.DateUtils;
import org.support.project.common.util.PropertyUtil;
import org.support.project.common.util.StringUtils;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.control.admin.TemplateControl;
import org.support.project.knowledge.dao.KnowledgesDao;
import org.support.project.knowledge.dao.SurveyAnswersDao;
import org.support.project.knowledge.entity.KnowledgesEntity;
import org.support.project.knowledge.entity.SurveyAnswersEntity;
import org.support.project.knowledge.entity.SurveyItemAnswersEntity;
import org.support.project.knowledge.entity.SurveyItemsEntity;
import org.support.project.knowledge.entity.SurveysEntity;
import org.support.project.knowledge.logic.KnowledgeLogic;
import org.support.project.knowledge.logic.SurveyLogic;
import org.support.project.knowledge.logic.TargetLogic;
import org.support.project.knowledge.logic.activity.Activity;
import org.support.project.knowledge.logic.activity.ActivityLogic;
import org.support.project.web.bean.LabelValue;
import org.support.project.web.boundary.Boundary;
import org.support.project.web.common.HttpStatus;
import org.support.project.web.config.MessageStatus;
import org.support.project.web.control.service.Get;
import org.support.project.web.control.service.Post;
import org.support.project.web.exception.InvalidParamException;

import net.arnx.jsonic.JSONException;

@DI(instance = Instance.Prototype)
public class SurveyControl extends TemplateControl {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(MethodHandles.lookup());
    
    /**
     * 保存されているデータを取得
     * 
     * @return
     * @throws InvalidParamException
     */
    @Get(publishToken = "survey")
    public Boundary load() throws InvalidParamException {
        Long id = super.getPathLong(new Long(-1));
        SurveysEntity entity = SurveyLogic.get().loadSurvey(id, getLoginUserId());
        if (entity == null) {
            entity = new SurveysEntity();
            entity.setDescription("");
            entity.setItems(new ArrayList<>());
            entity.setExist(false);
        } else {
            entity.setExist(true);
        }
        KnowledgesEntity knowledge = KnowledgeLogic.get().select(id, getLoginedUser());
        List<LabelValue> editors = TargetLogic.get().selectEditorsOnKnowledgeId(id);
        if (knowledge != null && KnowledgeLogic.get().isEditor(super.getLoginedUser(), knowledge, editors)) {
            entity.setEditable(true);
        } else {
            entity.setEditable(false);
        }
        if (entity.getLoginNecessary() != null && entity.getLoginNecessary().intValue() == 1 && getLoginedUser() == null) {
            entity.setDescription("");
            entity.setItems(new ArrayList<>());
        }
        
        return send(entity);
    }
    
    
    /**
     * 回答
     * 画面遷移すると再度画面を作るのが面倒なので、Ajaxアクセスとする
     * 
     * @return
     * @throws InvalidParamException
     * @throws IOException
     * @throws JSONException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    @Post(subscribeToken = "survey")
    public Boundary answer() throws InstantiationException, IllegalAccessException, JSONException, IOException, InvalidParamException {
        String id = getParam("knowledgeId");
        if (!StringUtils.isLong(id)) {
            return sendError(HttpStatus.SC_400_BAD_REQUEST, "BAD_REQUEST");
        }
        Long knowledgeId = new Long(id);
        
        SurveysEntity survey = SurveyLogic.get().loadSurvey(knowledgeId, getLoginUserId());
        if (survey == null) {
            return sendError(HttpStatus.SC_400_BAD_REQUEST, "BAD_REQUEST");
        }
        
        if (survey.getLoginNecessary().intValue() == 1 && getLoginedUser() == null) {
            return sendError(HttpStatus.SC_403_FORBIDDEN, "FORBIDDEN");
        }
        
        SurveyAnswersEntity answer = new SurveyAnswersEntity();
        if (getLoginedUser() == null) {
            if (StringUtils.isInteger(getParam("answerId"))) {
                Integer answerId = new Integer(getParam("answerId"));
                answer.setAnswerId(answerId);
            } else {
                // ログインしていなくても保存可能であった場合には、採番する
                Integer answerId = SurveyAnswersDao.get().selectAnonymousId(knowledgeId);
                if (answerId == 0) {
                    answerId = Integer.MIN_VALUE;
                } else {
                    answerId++;
                }
                answer.setAnswerId(answerId);
            }
        } else {
            answer.setAnswerId(getLoginUserId()); // 回答のIDは、ユーザとする（ユーザ毎に1件）
        }
        
        answer.setKnowledgeId(knowledgeId);
        
        List<SurveyItemsEntity> items = survey.getItems();
        for (SurveyItemsEntity item : items) {
            SurveyItemAnswersEntity answerItem = new SurveyItemAnswersEntity();
            answerItem.setAnswerId(getLoginUserId());
            answerItem.setKnowledgeId(knowledgeId);
            answerItem.setItemNo(item.getItemNo());
            answerItem.setItemValue("");
            answer.getItems().add(answerItem);
            
            String[] itemValues = super.getParam("item_" + item.getItemNo(), String[].class);
            if (itemValues != null && itemValues.length == 1) {
                String itemValue = itemValues[0];
                if (itemValue.startsWith("[") && itemValue.endsWith("]")) {
                    itemValue = itemValue.substring(1, itemValue.length() - 1);
                    answerItem.setItemValue(itemValue);
                } else {
                    answerItem.setItemValue(itemValue);
                }
            } else if (itemValues != null && itemValues.length > 1) {
                for (String itemValue : itemValues) {
                    StringBuilder value = new StringBuilder();
                    if (!StringUtils.isEmpty(answerItem.getItemValue())) {
                        value.append(answerItem.getItemValue()).append(",");
                    }
                    if (itemValue.startsWith("[") && itemValue.endsWith("]")) {
                        itemValue = itemValue.substring(1, itemValue.length() - 1);
                        value.append(itemValue);
                    } else {
                        value.append(itemValue);
                    }
                    answerItem.setItemValue(value.toString());
                }
            }
        }
        
        if (LOG.isDebugEnabled()) {
            LOG.debug(PropertyUtil.reflectionToString(answer));
        }
        SurveyLogic.get().saveAnswer(answer, getLoginUserId());
        ActivityLogic.get().processActivity(Activity.KNOWLEDGE_ANSWER, getLoginedUser(), DateUtils.now(),
                KnowledgesDao.get().selectOnKey(knowledgeId));
        
        // メッセージ送信
        return sendMsg(MessageStatus.Success, HttpStatus.SC_200_OK, String.valueOf(answer.getAnswerId()), "message.success.save");
    }
    
}
