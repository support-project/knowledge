package org.support.project.knowledge.control.protect;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.support.project.common.bean.ValidateError;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.DateUtils;
import org.support.project.common.util.PropertyUtil;
import org.support.project.common.util.StringUtils;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.control.admin.TemplateControl;
import org.support.project.knowledge.dao.KnowledgesDao;
import org.support.project.knowledge.entity.ItemChoicesEntity;
import org.support.project.knowledge.entity.KnowledgesEntity;
import org.support.project.knowledge.entity.SurveyAnswersEntity;
import org.support.project.knowledge.entity.SurveyChoicesEntity;
import org.support.project.knowledge.entity.SurveyItemAnswersEntity;
import org.support.project.knowledge.entity.SurveyItemsEntity;
import org.support.project.knowledge.entity.SurveysEntity;
import org.support.project.knowledge.entity.TemplateItemsEntity;
import org.support.project.knowledge.entity.TemplateMastersEntity;
import org.support.project.knowledge.logic.KnowledgeLogic;
import org.support.project.knowledge.logic.SurveyLogic;
import org.support.project.knowledge.logic.TargetLogic;
import org.support.project.knowledge.logic.activity.Activity;
import org.support.project.knowledge.logic.activity.ActivityLogic;
import org.support.project.knowledge.vo.SurveyReport;
import org.support.project.web.bean.LabelValue;
import org.support.project.web.boundary.Boundary;
import org.support.project.web.common.HttpStatus;
import org.support.project.web.config.MessageStatus;
import org.support.project.web.control.service.Delete;
import org.support.project.web.control.service.Get;
import org.support.project.web.control.service.Post;
import org.support.project.web.exception.InvalidParamException;
import org.support.project.web.logic.DateConvertLogic;

import net.arnx.jsonic.JSONException;

@DI(instance = Instance.Prototype)
public class SurveyControl extends TemplateControl {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(SurveyControl.class);

    
    /**
     * 登録
     * 画面遷移すると再度画面を作るのが面倒なので、Ajaxアクセスとする
     * 
     * @return
     * @throws InvalidParamException
     * @throws IOException
     * @throws JSONException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    @Post(subscribeToken = "survey", checkReqToken = true)
    public Boundary save() throws InstantiationException, IllegalAccessException, JSONException, IOException, InvalidParamException {
        List<ValidateError> errors = new ArrayList<ValidateError>();
        TemplateMastersEntity template = loadParams(errors);
        if (!errors.isEmpty()) {
            return sendValidateError(errors);
        }
        String id = getParam("knowledgeId");
        Long knowledgeId = null;
        if (StringUtils.isLong(id)) {
            knowledgeId = Long.parseLong(id);
        }
        if (knowledgeId == null) {
            return sendError(HttpStatus.SC_400_BAD_REQUEST, "BAD_REQUEST");
        }
        KnowledgesEntity knowledge = KnowledgeLogic.get().select(knowledgeId, getLoginedUser());
        List<LabelValue> editors = TargetLogic.get().selectEditorsOnKnowledgeId(knowledgeId);
        if (knowledge == null || !KnowledgeLogic.get().isEditor(super.getLoginedUser(), knowledge, editors)) {
            return sendError(HttpStatus.SC_403_FORBIDDEN, "FORBIDDEN");
        }
        // テンプレートと同じ構造にしているが、アンケートに変換して保存する
        SurveysEntity survey = convSurvey(template);
        survey.setKnowledgeId(knowledgeId);
        
        // 保存
        survey = SurveyLogic.get().saveSurvey(survey, getLoginedUser());

        // メッセージ送信
        return sendMsg(MessageStatus.Success, HttpStatus.SC_200_OK, "saved", "message.success.save");
    }

    private SurveysEntity convSurvey(TemplateMastersEntity template) {
        SurveysEntity survey = new SurveysEntity();
        survey.setTitle(template.getTypeName());
        survey.setDescription(template.getDescription());
        
        List<TemplateItemsEntity> items = template.getItems();
        for (TemplateItemsEntity item : items) {
            SurveyItemsEntity sitem = new SurveyItemsEntity();
            sitem.setItemName(item.getItemName());
            sitem.setItemNo(item.getItemNo());
            sitem.setItemType(item.getItemType());
            sitem.setDescription(item.getDescription());
            survey.getItems().add(sitem);
            
            List<ItemChoicesEntity> choices = item.getChoices();
            for (ItemChoicesEntity choice : choices) {
                SurveyChoicesEntity schoice = new SurveyChoicesEntity();
                schoice.setChoiceNo(choice.getChoiceNo());
                schoice.setChoiceLabel(choice.getChoiceLabel());
                schoice.setChoiceValue(choice.getChoiceValue());
                sitem.getChoices().add(schoice);
            }
        }
        
        return survey;
    }
    
    
    /**
     * 一覧取得
     * @return
     * @throws InvalidParamException
     */
    @Get
    public Boundary list() throws InvalidParamException {
        Integer page = super.getPathInteger(0);
        String plefix = getParam("q");
        if (plefix == null) {
            plefix = "";
        }
        List<SurveysEntity> list = SurveyLogic.get().listSurveys(getLoginedUser(), plefix, page);
        return send(list);
    }
    
    
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
        return send(entity);
    }
    
    /**
     * アンケート削除
     * @return
     * @throws InvalidParamException
     */
    @Delete(subscribeToken = "survey")
    public Boundary delete() throws InvalidParamException {
        Long id = super.getPathLong(new Long(-1));
        SurveyLogic.get().deleteSurvey(id);
        return sendMsg(MessageStatus.Success, HttpStatus.SC_200_OK, String.valueOf(id), "message.success.delete");
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
    @Post(subscribeToken = "survey", checkReqToken = true)
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
        SurveyAnswersEntity answer = new SurveyAnswersEntity();
        answer.setAnswerId(getLoginUserId()); // 回答のIDは、ユーザとする（ユーザ毎に1件）
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
        return sendMsg(MessageStatus.Success, HttpStatus.SC_200_OK, "saved", "message.success.save");
    }
    
    
    /**
     * 回答のレポート表示
     * @return
     * @throws InvalidParamException
     */
    @Get
    public Boundary report() throws InvalidParamException {
        Long knowledgeId = super.getPathLong(new Long(-1));
        if (!KnowledgeLogic.get().isEditor(super.getLoginedUser(), knowledgeId)) {
            return sendError(HttpStatus.SC_403_FORBIDDEN, "FORBIDDEN");
        }
        SurveyReport report = SurveyLogic.get().loadAnswers(knowledgeId, getLoginUserId());
        List<SurveyAnswersEntity> answers = report.getAnswers();
        for (SurveyAnswersEntity ans : answers) {
            ans.setLocalDatetime(DateConvertLogic.get().convertDate(ans.getInsertDatetime(), getRequest()));
        }
        return send(report);
    }
    
    
    /**
     * アンケート編集画面を表示
     * @return
     * @throws InvalidParamException
     */
    @Get(publishToken = "survey")
    public Boundary edit() throws InvalidParamException {
        Long knowledgeId = super.getPathLong(new Long(-1));
        if (!KnowledgeLogic.get().isEditor(super.getLoginedUser(), knowledgeId)) {
            return sendError(HttpStatus.SC_403_FORBIDDEN, "FORBIDDEN");
        }
        setAttribute("knowledgeId", knowledgeId);
        return forward("edit.jsp");
    }
    /**
     * アンケート結果レポート
     * @return
     * @throws InvalidParamException
     */
    @Get
    public Boundary answers() throws InvalidParamException {
        Long knowledgeId = super.getPathLong(new Long(-1));
        if (!KnowledgeLogic.get().isEditor(super.getLoginedUser(), knowledgeId)) {
            return sendError(HttpStatus.SC_403_FORBIDDEN, "FORBIDDEN");
        }
        setAttribute("knowledgeId", knowledgeId);
        return forward("answers.jsp");
    }
    
    
    
    
    
    
}
