package org.support.project.knowledge.control.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.support.project.common.bean.ValidateError;
import org.support.project.common.util.StringUtils;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.control.Control;
import org.support.project.knowledge.dao.TemplateMastersDao;
import org.support.project.knowledge.entity.ItemChoicesEntity;
import org.support.project.knowledge.entity.TemplateItemsEntity;
import org.support.project.knowledge.entity.TemplateMastersEntity;
import org.support.project.knowledge.logic.KnowledgeLogic;
import org.support.project.knowledge.logic.TemplateLogic;
import org.support.project.web.annotation.Auth;
import org.support.project.web.boundary.Boundary;
import org.support.project.web.config.MessageStatus;
import org.support.project.web.control.service.Get;
import org.support.project.web.control.service.Post;
import org.support.project.web.exception.InvalidParamException;

import net.arnx.jsonic.JSONException;

@DI(instance = Instance.Prototype)
public class TemplateControl extends Control {
    /**
     * テンプレートの一覧を表示
     * 
     * @return
     */
    @Get
    @Auth(roles = "admin")
    public Boundary list() {
        // テンプレートの個数はあまり多く出来ないようにする（でないと登録の画面が微妙）
        TemplateMastersDao mastersDao = TemplateMastersDao.get();
        List<TemplateMastersEntity> templates = mastersDao.selectAll();
        Collections.sort(templates, new Comparator<TemplateMastersEntity>() {
            @Override
            public int compare(TemplateMastersEntity o1, TemplateMastersEntity o2) {
                if (!o1.getTypeId().equals(o2.getTypeId())) {
                    return o1.getTypeId().compareTo(o2.getTypeId());
                }
                return 0;
            }
        });
        setAttribute("templates", templates);
        return forward("list.jsp");
    }

    /**
     * 登録画面を表示する
     * 
     * @return
     */
    @Get
    @Auth(roles = "admin")
    public Boundary view_add() {
        return forward("view_add.jsp");
    }

    /**
     * 更新画面を表示する
     * 
     * @return
     * @throws InvalidParamException
     */
    @Get
    @Auth(roles = "admin")
    public Boundary view_edit() throws InvalidParamException {
        Integer id = super.getPathInteger();
        TemplateMastersEntity entity = TemplateLogic.get().loadTemplate(id);
        if (entity == null) {
            sendError(404, null);
        }
        setAttributeOnProperty(entity);
        setAttribute("items", entity.getItems());

        boolean editable = true;
        if (KnowledgeLogic.TEMPLATE_TYPE_KNOWLEDGE == id || KnowledgeLogic.TEMPLATE_TYPE_BOOKMARK == id) {
            editable = false;
        }
        setAttribute("editable", editable);
        return forward("view_edit.jsp");
    }

    /**
     * リクエスト情報にあるテンプレートの情報を取得する 同時にバリデーションエラーもチェックし、もしバリデーションエラーが発生する場合、 引数のerrorsのリストに追加していく
     * 
     * @param errors
     * @return
     * @throws InvalidParamException
     * @throws IOException
     * @throws JSONException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    private TemplateMastersEntity loadParams(List<ValidateError> errors)
            throws InstantiationException, IllegalAccessException, JSONException, IOException, InvalidParamException {
        TemplateMastersEntity template = new TemplateMastersEntity();
        Map<String, String> values = getParams();
        errors.addAll(template.validate(values));
        if (!errors.isEmpty()) {
            return null;
        }
        template = getParamOnProperty(TemplateMastersEntity.class);

        String[] itemTypes = getParam("itemType", String[].class);
        if (itemTypes != null) {
            for (int i = 0; i < itemTypes.length; i++) {
                String itemType = itemTypes[i]; // text_1 や radio_3 といった形式
                if (itemType.indexOf("_") == -1) {
                    errors.add(new ValidateError("errors.invalid", "Request Data"));
                    return null;
                }
                String type = itemType.split("_")[0];
                String itemId = itemType.split("_")[1];
                if (!itemId.startsWith("item")) {
                    errors.add(new ValidateError("errors.invalid", "Request Data"));
                    return null;
                }
                String idx = itemId.substring(4);

                if (!StringUtils.isInteger(idx)) {
                    errors.add(new ValidateError("errors.invalid", "Request Data"));
                    return null;
                }
                int typeNum = TemplateLogic.get().convType(type);
                if (typeNum == -1) {
                    errors.add(new ValidateError("errors.invalid", "Request Data"));
                    return null;
                }

                String itemTitle = getParam("title_" + itemId);
                String itemDescription = getParam("description_" + itemId);

                TemplateItemsEntity itemsEntity = new TemplateItemsEntity();
                itemsEntity.setTypeId(-1); // バリデーションエラー対策
                itemsEntity.setItemNo(Integer.parseInt(idx));
                itemsEntity.setItemType(typeNum);
                itemsEntity.setItemName(itemTitle);
                itemsEntity.setDescription(itemDescription);

                errors.addAll(itemsEntity.validate());
                if (!errors.isEmpty()) {
                    // エラーが発生した時点で抜ける
                    return null;
                }
                template.getItems().add(itemsEntity);

                // 選択肢
                if (typeNum == TemplateLogic.ITEM_TYPE_RADIO || typeNum == TemplateLogic.ITEM_TYPE_CHECKBOX) {
                    String[] choiceLabels = getParam("label_item" + idx, String[].class);
                    String[] choiceValues = getParam("value_item" + idx, String[].class);
                    if (choiceLabels.length != choiceValues.length) {
                        errors.add(new ValidateError("errors.invalid", "Request Data"));
                        return null;
                    }
                    for (int j = 0; j < choiceLabels.length; j++) {
                        String label = choiceLabels[j];
                        String value = choiceValues[j];
                        ItemChoicesEntity choicesEntity = new ItemChoicesEntity();
                        choicesEntity.setTypeId(-1);
                        choicesEntity.setItemNo(-1);
                        choicesEntity.setChoiceLabel(label);
                        choicesEntity.setChoiceValue(value);
                        errors.addAll(itemsEntity.validate());
                        if (!errors.isEmpty()) {
                            // エラーが発生した時点で抜ける
                            return null;
                        }
                        itemsEntity.getChoices().add(choicesEntity);
                    }
                }
            }
        }
        return template;
    }

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
    @Post
    @Auth(roles = "admin")
    public Boundary create() throws InstantiationException, IllegalAccessException, JSONException, IOException, InvalidParamException {
        List<ValidateError> errors = new ArrayList<ValidateError>();
        TemplateMastersEntity template = loadParams(errors);
        if (!errors.isEmpty()) {
            return sendValidateError(errors);
        }
        template.setTypeId(null); // 自動採番する
        // 保存
        template = TemplateLogic.get().addTemplate(template, getLoginedUser());

        // メッセージ送信
        return sendMsg(MessageStatus.Success, HttpStatus.SC_OK, String.valueOf(template.getTypeId()), "message.success.insert");
    }

    /**
     * 更新
     * 
     * @return
     * @throws InvalidParamException
     * @throws IOException
     * @throws JSONException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    @Post
    @Auth(roles = "admin")
    public Boundary update() throws InstantiationException, IllegalAccessException, JSONException, IOException, InvalidParamException {
        List<ValidateError> errors = new ArrayList<ValidateError>();
        TemplateMastersEntity template = loadParams(errors);
        if (!errors.isEmpty()) {
            return sendValidateError(errors);
        }

        // 保存
        try {
            template = TemplateLogic.get().updateTemplate(template, getLoginedUser());
        } catch (InvalidParamException e) {
            // エラーメッセージ送信
            return send(e.getMessageResult());
        }
        // メッセージ送信
        return sendMsg(MessageStatus.Success, HttpStatus.SC_OK, String.valueOf(template.getTypeId()), "message.success.update");
    }

    /**
     * 削除
     * 
     * @return
     * @throws Exception
     */
    @Post
    @Auth(roles = "admin")
    public Boundary delete() throws Exception {
        Integer typeId = getParam("typeId", Integer.class);
        if (KnowledgeLogic.TEMPLATE_TYPE_KNOWLEDGE == typeId || KnowledgeLogic.TEMPLATE_TYPE_BOOKMARK == typeId) {
            addMsgWarn("knowledge.template.msg.not.delete");
            super.setPathInfo(String.valueOf(typeId));
            return view_edit();
        }

        TemplateLogic.get().deleteTemplate(typeId, getLoginedUser());
        String successMsg = "message.success.delete";
        setResult(successMsg, null);
        return list();
    }

}
