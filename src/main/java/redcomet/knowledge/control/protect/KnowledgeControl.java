package redcomet.knowledge.control.protect;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import redcomet.common.bean.ValidateError;
import redcomet.common.log.Log;
import redcomet.common.log.LogFactory;
import redcomet.common.util.StringUtils;
import redcomet.di.Container;
import redcomet.di.DI;
import redcomet.di.Instance;
import redcomet.knowledge.control.Control;
import redcomet.knowledge.dao.CommentsDao;
import redcomet.knowledge.dao.KnowledgesDao;
import redcomet.knowledge.entity.CommentsEntity;
import redcomet.knowledge.entity.KnowledgesEntity;
import redcomet.knowledge.entity.TagsEntity;
import redcomet.knowledge.logic.KnowledgeLogic;
import redcomet.knowledge.logic.UploadedFileLogic;
import redcomet.knowledge.vo.UploadFile;
import redcomet.web.boundary.Boundary;
import redcomet.web.common.HttpStatus;
import redcomet.web.exception.InvalidParamException;

@DI(instance=Instance.Prototype)
public class KnowledgeControl extends Control {
	/** ログ */
	private static Log LOG = LogFactory.getLog(KnowledgeControl.class);
	
	private KnowledgeLogic knowledgeLogic = KnowledgeLogic.get();
	private UploadedFileLogic fileLogic = UploadedFileLogic.get();
	
	/**
	 * 登録画面を表示する
	 * @return
	 */
	public Boundary view_add() {
		String offset = super.getParam("offset", String.class);
		if (StringUtils.isEmpty(offset)) {
			offset = "0";
		}
		setAttribute("offset", offset);

		return forward("view_add.jsp");
	}
	/**
	 * 登録画面を表示する
	 * @return
	 * @throws InvalidParamException 
	 */
	public Boundary view_edit() throws InvalidParamException {
		String offset = super.getParam("offset", String.class);
		if (StringUtils.isEmpty(offset)) {
			offset = "0";
		}
		setAttribute("offset", offset);
		
		Long knowledgeId = super.getPathLong();
		KnowledgesEntity entity = knowledgeLogic.select(knowledgeId, getLoginedUser());
		if (entity == null) {
			return sendError(HttpStatus.SC_404_NOT_FOUND, "NOT_FOUND");
		}
		setAttributeOnProperty(entity);
		
		// ナレッジに紐づく添付ファイルを取得
		List<UploadFile> files = fileLogic.selectOnKnowledgeId(knowledgeId, getRequest().getContextPath());
		setAttribute("files", files);
		
		if (!super.getLoginedUser().isAdmin() && entity.getInsertUser().intValue() != super.getLoginUserId().intValue()) {
			addMsgWarn("自分で登録したもの以外は編集出来ません");
			return forward("/open/knowledge/view.jsp");
		}
		return forward("view_edit.jsp");
	}
	
	/**
	 * 登録する
	 * @return
	 * @throws Exception 
	 */
	public Boundary add(KnowledgesEntity entity) throws Exception {
		LOG.trace("validate");
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
		String tags = super.getParam("tags");
		List<TagsEntity> tagList = knowledgeLogic.manegeTags(tags);
		entity = knowledgeLogic.insert(entity, tagList, fileNos, super.getLoginedUser());
		setAttributeOnProperty(entity);
		
		List<UploadFile> files = fileLogic.selectOnKnowledgeId(entity.getKnowledgeId(), getRequest().getContextPath());
		setAttribute("files", files);
		
		addMsgSuccess("登録しました");
		return forward("view_edit.jsp");
	}
	
	/**
	 * 更新する
	 * @return
	 * @throws Exception 
	 */
	public Boundary update(KnowledgesEntity entity) throws Exception {
		LOG.trace("validate");
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
		if (!super.getLoginedUser().isAdmin() && check.getInsertUser().intValue() != super.getLoginUserId().intValue()) {
			addMsgWarn("自分で登録したもの以外は編集出来ません");
			return forward("/open/knowledge/view.jsp");
		}
		
		LOG.trace("save");
		String tags = super.getParam("tags");
		List<TagsEntity> tagList = knowledgeLogic.manegeTags(tags);
		entity = knowledgeLogic.update(entity, tagList, fileNos, super.getLoginedUser());
		setAttributeOnProperty(entity);
		addMsgSuccess("更新しました");
		
		List<UploadFile> files = fileLogic.selectOnKnowledgeId(entity.getKnowledgeId(), getRequest().getContextPath());
		setAttribute("files", files);
		
		return forward("view_edit.jsp");
	}
	
	/**
	 * ナレッジを削除
	 * @return
	 * @throws Exception
	 */
	public Boundary delete() throws Exception {
		LOG.trace("validate");
		KnowledgesDao dao = Container.getComp(KnowledgesDao.class);
		String id = getParam("knowledgeId");
		if (!StringUtils.isInteger(id)) {
			// 削除するIDが指定されていない
			//return sendError(HttpStatus.SC_400_BAD_REQUEST, null);
			addMsgError("削除するナレッジが指定されていません");
			//return super.devolution("open.knowledge/list");
			return forward("/commons/errors/server_error.jsp");
		}
		Long knowledgeId = new Long(id);
		KnowledgesEntity check = dao.selectOnKey(knowledgeId);
		if (check == null) {
			return sendError(HttpStatus.SC_404_NOT_FOUND, "NOT_FOUND");
		}
		if (!super.getLoginedUser().isAdmin() && check.getInsertUser().intValue() != super.getLoginUserId().intValue()) {
			addMsgWarn("自分で登録したもの以外は削除出来ません");
			return forward("/open/knowledge/view.jsp");
		}
		LOG.trace("save");
		knowledgeLogic.delete(knowledgeId, getLoginedUser());
		
		addMsgSuccess("削除しました");
		return super.devolution("open.knowledge/list");
	}
	
	/**
	 * ログイン後、表示しなおし
	 * @return
	 * @throws InvalidParamException
	 */
	public Boundary view() throws InvalidParamException {
		Long knowledgeId = super.getPathLong(Long.valueOf(-1));
		return super.redirect(getRequest().getContextPath() + "/open.knowledge/view/" + knowledgeId);
	}
	
	
	public Boundary comment() throws InvalidParamException {
		Long knowledgeId = super.getPathLong(Long.valueOf(-1));
		
		String comment = getParam("comment");
		CommentsDao commentsDao = CommentsDao.get();
		CommentsEntity commentsEntity = new CommentsEntity();
		commentsEntity.setKnowledgeId(knowledgeId);
		commentsEntity.setComment(comment);
		commentsDao.insert(commentsEntity);
		
		return super.redirect(getRequest().getContextPath() + "/open.knowledge/view/" + knowledgeId);
	}
	
}




