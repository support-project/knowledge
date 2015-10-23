package org.support.project.knowledge.control.admin;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import net.arnx.jsonic.JSONException;

import org.support.project.common.bean.ValidateError;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.knowledge.control.Control;
import org.support.project.knowledge.dao.TemplateMastersDao;
import org.support.project.knowledge.entity.TemplateMastersEntity;
import org.support.project.knowledge.logic.KnowledgeLogic;
import org.support.project.web.annotation.Auth;
import org.support.project.web.boundary.Boundary;
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
	 * 登録
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
		TemplateMastersEntity template = new TemplateMastersEntity();
		Map<String, String> values = getParams();
		List<ValidateError> errors = template.validate(values);
		if (!errors.isEmpty()) {
			setResult(null, errors);
			return forward("view_add.jsp");
		}
		template = getParamOnProperty(TemplateMastersEntity.class);
		
		TemplateMastersDao templateDao = TemplateMastersDao.get();
		template = templateDao.insert(template);
		
		super.setPathInfo(String.valueOf(template.getTypeId()));
		
		String successMsg = "message.success.insert";
		setResult(successMsg, errors);

		return view_edit();
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
		TemplateMastersEntity template = new TemplateMastersEntity();
		Map<String, String> values = getParams();
		List<ValidateError> errors = template.validate(values);
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
		
		TemplateMastersEntity entity = dao.selectOnKey(typeId);
		if (entity != null) {
			dao.delete(entity);
		}
		
		String successMsg = "message.success.delete";
		setResult(successMsg, null);
		return list();
	}
	
	
}
