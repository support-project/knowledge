package org.support.project.knowledge.control.protect;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.fileupload.FileItem;
import org.support.project.common.bean.ValidateError;
import org.support.project.common.util.StringUtils;
import org.support.project.common.validate.Validator;
import org.support.project.common.validate.ValidatorFactory;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.control.Control;
import org.support.project.knowledge.logic.AccountLogic;
import org.support.project.knowledge.logic.UserLogic;
import org.support.project.knowledge.vo.UploadFile;
import org.support.project.knowledge.vo.UploadResults;
import org.support.project.web.bean.LoginedUser;
import org.support.project.web.bean.Msg;
import org.support.project.web.boundary.Boundary;
import org.support.project.web.common.HttpStatus;
import org.support.project.web.common.HttpUtil;
import org.support.project.web.dao.UsersDao;
import org.support.project.web.entity.UsersEntity;
import org.support.project.web.logic.AuthenticationLogic;
import org.support.project.web.logic.impl.DefaultAuthenticationLogicImpl;

@DI(instance=Instance.Prototype)
public class AccountControl extends Control {

	@Override
	public Boundary index() {
		LoginedUser loginedUser = super.getLoginedUser();
		if (loginedUser == null) {
			sendError(HttpStatus.SC_401_UNAUTHORIZED, "");
		}
		Integer userId = loginedUser.getLoginUser().getUserId();
		UsersEntity user = UsersDao.get().selectOnKey(userId);
		user.setPassword(null);
		
		setAttributeOnProperty(user);
		return forward("index.jsp");
	}
	
	public Boundary update() {
		LoginedUser loginedUser = super.getLoginedUser();
		if (loginedUser == null) {
			sendError(HttpStatus.SC_401_UNAUTHORIZED, "");
		}
		
		Map<String, String> values = getParams();
		values.put("userId", String.valueOf(super.getLoginUserId()));
		values.put("rowId", "-"); //row_idは必須だが、画面からはこない
		if (StringUtils.isEmpty(getParam("password"))) {
			values.put("password", "-");
		}
		
		UsersEntity user = new UsersEntity();
		List<ValidateError> errors = user.validate(values);
		if (!StringUtils.isEmpty(getParam("password"))) {
			if (!getParam("password").equals(getParam("confirm_password", String.class))) {
				ValidateError error = new ValidateError("knowledge.user.invalid.same.password");
				errors.add(error);
			}
		}
		
		if (errors.isEmpty()) {
			// エラーが無い場合のみ更新する
			//UsersEntity user = super.getParams(UsersEntity.class);
			UsersDao dao = UsersDao.get();
			user = dao.selectOnKey(getLoginUserId());
			if (user == null) {
				sendError(HttpStatus.SC_400_BAD_REQUEST, "user is allready removed.");
			}
			user.setUserKey(getParam("userKey"));
			user.setUserName(getParam("userName"));
			if (!StringUtils.isEmpty(getParam("password"))) {
				user.setPassword(getParam("password"));
				user.setEncrypted(false);
			}
			dao.update(user);
		}
		String successMsg = "message.success.update";
		setResult(successMsg, errors);
		
		return forward("index.jsp");
	}

	/**
	 * 退会画面を表示
	 * @return
	 */
	public Boundary withdrawal() {
		return forward("withdrawal.jsp");
	}
	

	
	/**
	 * 退会を実行
	 * @return
	 * @throws Exception 
	 */
	public Boundary delete() throws Exception {
		// アカウント削除(退会処理)
		boolean knowledgeRemove = true;
		if ("2".equals(getParam("knowledge_remove"))) {
			knowledgeRemove = false;
		}
		UserLogic.get().withdrawal(getLoginUserId(), knowledgeRemove);
		
		// セッションを破棄
		AuthenticationLogic<LoginedUser> authenticationLogic = Container.getComp(DefaultAuthenticationLogicImpl.class);
		authenticationLogic.clearSession(getRequest());
		
		addMsgInfo("knowledge.account.delete");
		return devolution("index/index");
		//return redirect(getRequest().getContextPath());
	}
	
	/**
	 * アイコン画像をアップロード
	 * @return
	 * @throws IOException 
	 */
	public Boundary iconupload() throws IOException {
		AccountLogic logic = AccountLogic.get();
		UploadResults results = new UploadResults();
		List<UploadFile> files = new ArrayList<UploadFile>();
		Object obj = getParam("files[]", Object.class);
		if (obj instanceof FileItem) {
			FileItem fileItem = (FileItem) obj;
			ValidateError error = checkFile(fileItem);
			if (error != null) {
				Msg msg = new Msg(error.getMsg(HttpUtil.getLocale(getRequest())));
				return send(HttpStatus.SC_400_BAD_REQUEST, msg);
			}
			UploadFile file = logic.saveIconImage(fileItem, getLoginedUser(), getRequest().getContextPath());
			files.add(file);
		} else if (obj instanceof List) {
			List<FileItem> fileItems = (List<FileItem>) obj;
			for (FileItem fileItem : fileItems) {
				ValidateError error = checkFile(fileItem);
				if (error != null) {
					Msg msg = new Msg(error.getMsg(HttpUtil.getLocale(getRequest())));
					return send(HttpStatus.SC_400_BAD_REQUEST, msg);
				}
				UploadFile file = logic.saveIconImage(fileItem, getLoginedUser(), getRequest().getContextPath());
				files.add(file);
			}
		}
		results.setFiles(files);
		return send(HttpStatus.SC_200_OK, results);
	}
	/**
	 * アップロードされたファイルのチェック
	 * アイコン画像の拡張子チェック
	 * @param name
	 * @return
	 */
	private ValidateError checkFile(FileItem fileItem) {
		if (fileItem.getSize() > 5 * 1024 * 1024) {
			ValidateError error = new ValidateError("errors.maxfilesize", "5MB");
			return error;
		}
		
		String name = fileItem.getName();
		Validator validator = ValidatorFactory.getInstance(Validator.EXTENSION);
		return validator.validate(name, "icon", "png", "jpg", "jpeg", "gif");
	}
}
