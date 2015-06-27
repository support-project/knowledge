package org.support.project.knowledge.control.protect;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.support.project.common.bean.ValidateError;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.StringUtils;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.control.KnowledgeControlBase;
import org.support.project.knowledge.dao.KnowledgeGroupsDao;
import org.support.project.knowledge.dao.KnowledgesDao;
import org.support.project.knowledge.entity.KnowledgeGroupsEntity;
import org.support.project.knowledge.entity.KnowledgeUsersEntity;
import org.support.project.knowledge.entity.KnowledgesEntity;
import org.support.project.knowledge.entity.TagsEntity;
import org.support.project.knowledge.logic.KnowledgeLogic;
import org.support.project.knowledge.logic.TargetLogic;
import org.support.project.knowledge.logic.UploadedFileLogic;
import org.support.project.knowledge.vo.UploadFile;
import org.support.project.web.bean.LabelValue;
import org.support.project.web.bean.LoginedUser;
import org.support.project.web.boundary.Boundary;
import org.support.project.web.common.HttpStatus;
import org.support.project.web.config.HttpMethod;
import org.support.project.web.control.service.Get;
import org.support.project.web.control.service.Post;
import org.support.project.web.entity.GroupsEntity;
import org.support.project.web.exception.InvalidParamException;

@DI(instance=Instance.Prototype)
public class KnowledgeControl extends KnowledgeControlBase {
	/** ログ */
	private static Log LOG = LogFactory.getLog(KnowledgeControl.class);
	
	private KnowledgeLogic knowledgeLogic = KnowledgeLogic.get();
	private UploadedFileLogic fileLogic = UploadedFileLogic.get();
	
	/**
	 * 登録画面を表示する
	 * @return
	 */
	@Get
	public Boundary view_add() {
		// 共通処理呼の表示条件の保持の呼び出し
		setViewParam();
		
		String offset = super.getParam("offset", String.class);
		if (StringUtils.isEmpty(offset)) {
			offset = "0";
		}
		setAttribute("offset", offset);

		return forward("view_add.jsp");
	}
	/**
	 * 更新画面を表示する
	 * @return
	 * @throws InvalidParamException 
	 */
	@Get
	public Boundary view_edit() throws InvalidParamException {
		// 共通処理呼の表示条件の保持の呼び出し
		setViewParam();
		
		String offset = super.getParam("offset", String.class);
		if (StringUtils.isEmpty(offset)) {
			offset = "0";
		}
		setAttribute("offset", offset);
		
		Long knowledgeId = super.getPathLong();
		KnowledgesEntity entity = knowledgeLogic.selectWithTags(knowledgeId, getLoginedUser());
		if (entity == null) {
			return sendError(HttpStatus.SC_404_NOT_FOUND, "NOT_FOUND");
		}
		setAttributeOnProperty(entity);
		
		// ナレッジに紐づく添付ファイルを取得
		List<UploadFile> files = fileLogic.selectOnKnowledgeId(knowledgeId, getRequest().getContextPath());
		setAttribute("files", files);
		
		// 表示するグループを取得
		List<LabelValue> groups = TargetLogic.get().selectTargetsOnKnowledgeId(knowledgeId);
		setAttribute("groups", groups);
		
		// 共同編集者
		List<LabelValue> editors = TargetLogic.get().selectEditorsOnKnowledgeId(knowledgeId);
		setAttribute("editors", editors);
		// 編集権限チェック
		LoginedUser loginedUser = super.getLoginedUser();
		boolean edit = knowledgeLogic.isEditor(loginedUser, entity, editors);
		if (!edit) {
			setAttribute("edit", false);
			addMsgWarn("knowledge.edit.noaccess");
			//return forward("/open/knowledge/view.jsp");
			return devolution(HttpMethod.get, "open.Knowledge/view", String.valueOf(knowledgeId));
		}
		
		return forward("view_edit.jsp");
	}
	
	/**
	 * 登録する
	 * @return
	 * @throws Exception 
	 */
	@Post
	public Boundary add(KnowledgesEntity entity) throws Exception {
		// 共通処理呼の表示条件の保持の呼び出し
		setViewParam();
		
		String groupsstr = super.getParam("groups");
		String[] targets = groupsstr.split(",");
		//List<GroupsEntity> groups = GroupLogic.get().selectGroups(targets);
		List<LabelValue> groups = TargetLogic.get().selectTargets(targets);
		setAttribute("groups", groups);
		
		String editorsstr = super.getParam("editors");
		String[] editordids = editorsstr.split(",");
		List<LabelValue> editors = TargetLogic.get().selectTargets(editordids);
		setAttribute("editors", editors);

		List<Long> fileNos = new ArrayList<Long>();
		Object obj = getParam("files", Object.class);
		if (obj != null) {
			if (obj instanceof String) {
				String string = (String) obj;
				if (StringUtils.isLong(string)) {
					fileNos.add(new Long(string));
				}
			} else if (obj instanceof List) {
				List<String> strings = (List<String>) obj;
				for (String string : strings) {
					if (StringUtils.isLong(string)) {
						fileNos.add(new Long(string));
					}
				}
			}
		}

		entity.setTitle(super.doSamy(entity.getTitle())); //XSS対策
		entity.setContent(super.doSamy(entity.getContent())); //XSS対策
		
		List<ValidateError> errors = entity.validate();
		if (!errors.isEmpty()) {
			setResult(null, errors);
			// バリデーションエラーが発生した場合、設定されていた添付ファイルの情報は再取得
			List<UploadFile> files = fileLogic.selectOnFileNos(fileNos, getRequest().getContextPath());
			Iterator<UploadFile> iterator = files.iterator();
			while (iterator.hasNext()) {
				UploadFile uploadFile = (UploadFile) iterator.next();
				if (uploadFile.getKnowlegeId() != null) {
					// 新規登録なのに、添付ファイルが既にナレッジに紐づいている（おかしい）
					iterator.remove();
				}
			}
			setAttribute("files", files);

			return forward("view_add.jsp");
		}
		LOG.trace("save");
		String tags = super.getParam("tagNames");
		List<TagsEntity> tagList = knowledgeLogic.manegeTags(tags);
		
		entity = knowledgeLogic.insert(entity, tagList, fileNos, groups, editors, super.getLoginedUser());
		setAttributeOnProperty(entity);
		
		List<UploadFile> files = fileLogic.selectOnKnowledgeId(entity.getKnowledgeId(), getRequest().getContextPath());
		setAttribute("files", files);
		
		addMsgSuccess("message.success.insert");
		return forward("view_edit.jsp");
	}
	
	/**
	 * 更新する
	 * @return
	 * @throws Exception 
	 */
	@Post
	public Boundary update(KnowledgesEntity entity) throws Exception {
		// 共通処理呼の表示条件の保持の呼び出し
		setViewParam();
		
		String groupsstr = super.getParam("groups");
		String[] targets = groupsstr.split(",");
		List<LabelValue> groups = TargetLogic.get().selectTargets(targets);
		setAttribute("groups", groups);
		
		String editorsstr = super.getParam("editors");
		String[] editordids = editorsstr.split(",");
		List<LabelValue> editors = TargetLogic.get().selectTargets(editordids);
		setAttribute("editors", editors);
		
		List<Long> fileNos = new ArrayList<Long>();
		Object obj = getParam("files", Object.class);
		if (obj != null) {
			if (obj instanceof String) {
				String string = (String) obj;
				if (StringUtils.isLong(string)) {
					fileNos.add(new Long(string));
				}
			} else if (obj instanceof List) {
				List<String> strings = (List<String>) obj;
				for (String string : strings) {
					if (StringUtils.isLong(string)) {
						fileNos.add(new Long(string));
					}
				}
			}
		}

		entity.setTitle(super.doSamy(entity.getTitle())); //XSS対策
		entity.setContent(super.doSamy(entity.getContent())); //XSS対策
		
		KnowledgesDao dao = Container.getComp(KnowledgesDao.class);
		List<ValidateError> errors = entity.validate();
		if (!errors.isEmpty()) {
			setResult(null, errors);
			
			// バリデーションエラーが発生した場合、設定されていた添付ファイルの情報は再取得
			List<UploadFile> files = fileLogic.selectOnFileNos(fileNos, getRequest().getContextPath());
			Iterator<UploadFile> iterator = files.iterator();
			while (iterator.hasNext()) {
				UploadFile uploadFile = (UploadFile) iterator.next();
				if (uploadFile.getKnowlegeId() != null 
						&& uploadFile.getKnowlegeId().longValue() != entity.getKnowledgeId().longValue()) {
					// ナレッジIDが空でなく、かつ、更新中のナレッジ以外に紐づいている添付ファイルはおかしいので削除
					iterator.remove();
				}
			}
			setAttribute("files", files);
			
			return forward("view_edit.jsp");
		}
		
		KnowledgesEntity check = dao.selectOnKey(entity.getKnowledgeId());
		if (check == null) {
			return sendError(HttpStatus.SC_404_NOT_FOUND, "NOT_FOUND");
		}
		// 編集権限チェック
		if (!knowledgeLogic.isEditor(super.getLoginedUser(), check, editors)) {
			setAttribute("edit", false);
			addMsgWarn("knowledge.edit.noaccess");
			//return forward("/open/knowledge/view.jsp");
			return devolution(HttpMethod.get, "open.Knowledge/view", String.valueOf(entity.getKnowledgeId()));
		}
		
		LOG.trace("save");
		String tags = super.getParam("tagNames");
		List<TagsEntity> tagList = knowledgeLogic.manegeTags(tags);
		
		entity = knowledgeLogic.update(entity, tagList, fileNos, groups, editors, super.getLoginedUser());
		setAttributeOnProperty(entity);
		addMsgSuccess("message.success.update");
		
		List<UploadFile> files = fileLogic.selectOnKnowledgeId(entity.getKnowledgeId(), getRequest().getContextPath());
		setAttribute("files", files);
		
		return forward("view_edit.jsp");
	}
	
	/**
	 * ナレッジを削除
	 * @return
	 * @throws Exception
	 */
	@Post
	public Boundary delete() throws Exception {
		// 共通処理呼の表示条件の保持の呼び出し
		setViewParam();
		
		LOG.trace("validate");
		KnowledgesDao dao = Container.getComp(KnowledgesDao.class);
		String id = getParam("knowledgeId");
		if (!StringUtils.isInteger(id)) {
			// 削除するIDが指定されていない
			//return sendError(HttpStatus.SC_400_BAD_REQUEST, null);
			addMsgError("knowledge.delete.none");
			//return super.devolution("open.knowledge/list");
			return forward("/commons/errors/server_error.jsp");
		}
		Long knowledgeId = new Long(id);
		KnowledgesEntity check = dao.selectOnKey(knowledgeId);
		if (check == null) {
			return sendError(HttpStatus.SC_404_NOT_FOUND, "NOT_FOUND");
		}
		List<LabelValue> editors = TargetLogic.get().selectEditorsOnKnowledgeId(knowledgeId);
		if (!knowledgeLogic.isEditor(super.getLoginedUser(), check, editors)) {
			setAttribute("edit", false);
			addMsgWarn("knowledge.edit.noaccess");
			//return forward("/open/knowledge/view.jsp");
			return devolution(HttpMethod.get, "open.Knowledge/view", String.valueOf(knowledgeId));
		}
		LOG.trace("save");
		knowledgeLogic.delete(knowledgeId, getLoginedUser());
		
		addMsgSuccess("message.success.delete");
		return super.devolution(HttpMethod.get, "open.Knowledge/list");
	}
	
	/**
	 * ログイン後、表示しなおし
	 * @return
	 * @throws InvalidParamException
	 */
	@Get
	public Boundary view() throws InvalidParamException {
		// 共通処理呼の表示条件の保持の呼び出し
		setViewParam();
		
		Long knowledgeId = super.getPathLong(Long.valueOf(-1));
		return super.redirect(getRequest().getContextPath() + "/open.knowledge/view/" + knowledgeId);
	}
	
	/**
	 * コメント追加
	 * @return
	 * @throws Exception 
	 */
	@Post
	public Boundary comment() throws Exception {
		// 共通処理呼の表示条件の保持の呼び出し
		String params = setViewParam();
		Long knowledgeId = super.getPathLong(Long.valueOf(-1));
		String comment = super.doSamy(getParam("addcomment"));
		
		// 必須チェック
		if (StringUtils.isEmpty(comment)) {
			addMsgWarn("errors.required", "Comment");
			return super.devolution(HttpMethod.get, "open.Knowledge/view", String.valueOf(knowledgeId));
		}
		KnowledgeLogic.get().saveComment(knowledgeId, comment);
		return super.redirect(getRequest().getContextPath() + "/open.knowledge/view/" + knowledgeId + params);
	}
	
	
	/**
	 * 指定のナレッジにアクセスできる対象を取得
	 * @return
	 * @throws InvalidParamException 
	 */
	@Get
	public Boundary view_targets() throws InvalidParamException {
		Long knowledgeId = super.getPathLong(Long.valueOf(-1));
		List<LabelValue> groups = TargetLogic.get().selectTargetsOnKnowledgeId(knowledgeId);
		return super.send(groups);
	}
	
}




