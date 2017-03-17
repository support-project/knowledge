package org.support.project.knowledge.control.protect;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.support.project.common.bean.ValidateError;
import org.support.project.common.util.StringUtils;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.control.admin.TemplateControl;
import org.support.project.knowledge.entity.ItemChoicesEntity;
import org.support.project.knowledge.entity.KnowledgesEntity;
import org.support.project.knowledge.entity.SurveyChoicesEntity;
import org.support.project.knowledge.entity.SurveyItemsEntity;
import org.support.project.knowledge.entity.SurveysEntity;
import org.support.project.knowledge.entity.TemplateItemsEntity;
import org.support.project.knowledge.entity.TemplateMastersEntity;
import org.support.project.knowledge.logic.KnowledgeLogic;
import org.support.project.knowledge.logic.SurveyLogic;
import org.support.project.web.boundary.Boundary;
import org.support.project.web.common.HttpStatus;
import org.support.project.web.config.MessageStatus;
import org.support.project.web.control.service.Delete;
import org.support.project.web.control.service.Get;
import org.support.project.web.control.service.Post;
import org.support.project.web.exception.InvalidParamException;

import net.arnx.jsonic.JSONException;

@DI(instance = Instance.Prototype)
public class SurveyControl extends TemplateControl {

    
    /**
     * 登録 画面遷移すると再度画面を作るのが面倒なので、Ajaxアクセスとする
     * 
     * @return
     * @throws InvalidParamException
     * @throws IOException
     * @throws JSONException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    @Post(subscribeToken = "knowledge", checkReqToken = true)
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
        if (knowledge == null || !KnowledgeLogic.get().isEditor(super.getLoginedUser(), knowledge, null)) {
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
     * 保存されているデータを取得
     * 
     * @return
     * @throws InvalidParamException
     */
    @Get
    public Boundary load() throws InvalidParamException {
        Long id = super.getPathLong(new Long(-1));
        SurveysEntity entity = SurveyLogic.get().loadSurvey(id);
        if (entity == null) {
            return sendError(404, null);
        }
        return send(entity);
    }
    
    /**
     * アンケート削除
     * @return
     * @throws InvalidParamException
     */
    @Delete
    public Boundary delete() throws InvalidParamException {
        Long id = super.getPathLong(new Long(-1));
        SurveyLogic.get().deleteSurvey(id);
        return sendMsg(MessageStatus.Success, HttpStatus.SC_200_OK, String.valueOf(id), "message.success.delete");
    }
    
    
    
    
}
