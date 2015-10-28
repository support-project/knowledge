package org.support.project.knowledge.control.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.arnx.jsonic.JSONException;

import org.apache.http.HttpStatus;
import org.support.project.common.bean.ValidateError;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.StringUtils;
import org.support.project.knowledge.control.Control;
import org.support.project.knowledge.dao.TemplateMastersDao;
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

public class TemplateControl extends Control {
	/** ログ */
	private static Log LOG = LogFactory.getLog(TemplateControl.class);
	
	/**
	 * テンプレートの一覧を表示
	 * @return
	 */
	@Get
	@Auth(roles="admin")
	public Boundary list() {
		// テンプレートの個数はあまり多く出来ないようにする（でないと登録の画面が微妙）
		TemplateMastersDao mastersDao = TemplateMastersDao.get();
		List<TemplateMastersEntity> templates = mastersDao.selectAll();
		setAttribute("templates", templates);
		return forward("list.jsp");
	}
	
	
	/**
	 * 登録画面を表示する
	 * @return
	 */
	@Get
	@Auth(roles="admin")
	public Boundary view_add() {
		return forward("view_add.jsp");
	}
	
	/**
	 * 更新画面を表示する
	 * @return
	 * @throws InvalidParamException 
	 */
	@Get
	@Auth(roles="admin")
	public Boundary view_edit() throws InvalidParamException {
		Integer id = super.getPathInteger();
		TemplateMastersDao mastersDao = TemplateMastersDao.get();
		TemplateMastersEntity entity = mastersDao.selectOnKey(id);
		if (entity == null) {
			sendError(404, null);
		}
		setAttributeOnProperty(entity);
		
		boolean editable = true;
		if (KnowledgeLogic.TEMPLATE_TYPE_KNOWLEDGE == id || KnowledgeLogic.TEMPLATE_TYPE_BOOKMARK == id) {
			editable = false;
		}
		setAttribute("editable", editable);
		return forward("view_edit.jsp");
	}
	
	
	/**
	 * リクエスト情報にあるテンプレートの情報を取得する
	 * 同時にバリデーションエラーもチェックし、もしバリデーションエラーが発生する場合、
	 * 引数のerrorsのリストに追加していく
	 * 
	 * @param errors
	 * @return
	 * @throws InvalidParamException 
	 * @throws IOException 
	 * @throws JSONException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public TemplateMastersEntity loadParams(List<ValidateError> errors) throws InstantiationException, IllegalAccessException, JSONException, IOException, InvalidParamException {
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
			}
		}
		return template;
	}
	
	
	/**
	 * 登録
	 * 画面遷移すると再度画面を作るのが面倒なので、Ajaxアクセスとする
	 * @return
	 * @throws InvalidParamException 
	 * @throws IOException 
	 * @throws JSONException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	@Post
	@Auth(roles="admin")
	public Boundary create() throws InstantiationException, IllegalAccessException, JSONException, IOException, InvalidParamException {
		List<ValidateError> errors = new ArrayList<ValidateError>();
		TemplateMastersEntity template = loadParams(errors);
		if (!errors.isEmpty()) {
			return sendValidateError(errors);
		}
		// 保存
		template = TemplateLogic.get().addTemplate(template, getLoginedUser());
		
		// メッセージ送信
		return sendMsg(MessageStatus.Success, HttpStatus.SC_OK, String.valueOf(template.getTypeId()), "message.success.insert");
	}
	


	/**
	 * 更新
	 * @return
	 * @throws InvalidParamException 
	 * @throws IOException 
	 * @throws JSONException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	@Post
	@Auth(roles="admin")
	public Boundary update() throws InstantiationException, IllegalAccessException, JSONException, IOException, InvalidParamException {
		List<ValidateError> errors = new ArrayList<ValidateError>();
		TemplateMastersEntity template = loadParams(errors);
		if (!errors.isEmpty()) {
			setResult(null, errors);
			return forward("view_edit.jsp");
		}
		
		Integer typeId = getParam("typeId", Integer.class);
		if (KnowledgeLogic.TEMPLATE_TYPE_KNOWLEDGE == typeId) {
			// TODO 項目の増減はできない
		} else if(KnowledgeLogic.TEMPLATE_TYPE_BOOKMARK == typeId) {
			// TODO 項目の増減はできない
		}
		
		template = getParamOnProperty(TemplateMastersEntity.class);
		
		TemplateMastersDao templateDao = TemplateMastersDao.get();
		template = templateDao.save(template);
		
		super.setPathInfo(String.valueOf(template.getTypeId()));
		
		String successMsg = "message.success.update";
		setResult(successMsg, errors);
		
		return view_edit();
	}
	
	
	/**
	 * 削除
	 * @return
	 * @throws Exception 
	 */
	@Post
	@Auth(roles="admin")
	public Boundary delete() throws Exception {
		TemplateMastersDao dao = TemplateMastersDao.get();
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
