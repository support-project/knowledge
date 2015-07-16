package org.support.project.knowledge.control.protect;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.fileupload.FileItem;
import org.support.project.common.bean.ValidateError;
import org.support.project.common.config.INT_FLAG;
import org.support.project.common.exception.ParseException;
import org.support.project.common.util.StringUtils;
import org.support.project.common.validate.Validator;
import org.support.project.common.validate.ValidatorFactory;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.config.AppConfig;
import org.support.project.knowledge.config.SystemConfig;
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
import org.support.project.web.config.HttpMethod;
import org.support.project.web.control.service.Get;
import org.support.project.web.control.service.Post;
import org.support.project.web.dao.SystemConfigsDao;
import org.support.project.web.dao.UsersDao;
import org.support.project.web.entity.SystemConfigsEntity;
import org.support.project.web.entity.UsersEntity;
import org.support.project.web.exception.InvalidParamException;
import org.support.project.web.logic.AuthenticationLogic;
import org.support.project.web.logic.impl.DefaultAuthenticationLogicImpl;

@DI(instance=Instance.Prototype)
public class AccountControl extends Control {
	
	/**
	 * アカウント情報表示
	 */
	@Get
	@Override
	public Boundary index() {
		SystemConfigsDao dao = SystemConfigsDao.get();
		SystemConfigsEntity userAddType = dao.selectOnKey(SystemConfig.USER_ADD_TYPE, AppConfig.get().getSystemName());
		if (userAddType == null) {
			userAddType = new SystemConfigsEntity(SystemConfig.USER_ADD_TYPE, AppConfig.get().getSystemName());
			userAddType.setConfigValue(SystemConfig.USER_ADD_TYPE_VALUE_ADMIN);
		}
		setAttribute("userAddType", userAddType.getConfigValue());

		LoginedUser loginedUser = super.getLoginedUser();
		if (loginedUser == null) {
			return sendError(HttpStatus.SC_401_UNAUTHORIZED, "");
		}
		Integer userId = loginedUser.getLoginUser().getUserId();
		UsersEntity user = UsersDao.get().selectOnKey(userId);
		if (user == null) {
			return sendError(HttpStatus.SC_404_NOT_FOUND, "NOT FOUND");
		}
		user.setPassword(null);
		
		setAttributeOnProperty(user);
		return forward("index.jsp");
	}
	
	/**
	 * ユーザの情報更新
	 * @return
	 * @throws ParseException 
	 * @throws ScanException 
	 * @throws PolicyException 
	 */
	@Post
	public Boundary update() throws ParseException {
		SystemConfigsDao systemConfigsDao = SystemConfigsDao.get();

		SystemConfigsEntity userAddType = systemConfigsDao.selectOnKey(SystemConfig.USER_ADD_TYPE, AppConfig.get().getSystemName());
		if (userAddType == null) {
			userAddType = new SystemConfigsEntity(SystemConfig.USER_ADD_TYPE, AppConfig.get().getSystemName());
			userAddType.setConfigValue(SystemConfig.USER_ADD_TYPE_VALUE_ADMIN);
		}
		setAttribute("userAddType", userAddType.getConfigValue());
		
		LoginedUser loginedUser = super.getLoginedUser();
		if (loginedUser == null) {
			return sendError(HttpStatus.SC_401_UNAUTHORIZED, "");
		}
		
		Map<String, String> values = getParams();
		values.put("userId", String.valueOf(super.getLoginUserId()));
		values.put("rowId", "-"); //row_idは必須だが、画面からはこない
		if (StringUtils.isEmpty(getParam("password"))) {
			values.put("password", "-");
		}
		values.put("userName", super.sanitize(values.get("userName"))); // ユーザ名はXSS対策する
		
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
				return sendError(HttpStatus.SC_400_BAD_REQUEST, "user is allready removed.");
			}
			if (user.getAuthLdap() != null && user.getAuthLdap().intValue() == INT_FLAG.ON.getValue()) {
				return sendError(HttpStatus.SC_400_BAD_REQUEST, "can not edit ldap user.");
			}
			if (userAddType.getConfigValue().equals(SystemConfig.USER_ADD_TYPE_VALUE_ADMIN)) {
				//ユーザ登録を管理者が行っている場合、メールアドレスは変更出来ない（変更用の画面も使えない）
			} else if (userAddType.getConfigValue().equals(SystemConfig.USER_ADD_TYPE_VALUE_APPROVE)) {
				// ユーザが自分で登録して管理者が承認の場合も変更出来ない（変更用の画面も使えない）
			} else if (userAddType.getConfigValue().equals(SystemConfig.USER_ADD_TYPE_VALUE_MAIL)) {
				// ダブルオプトインの場合も変更出来ない（変更用の画面で変更）
			} else {
				user.setUserKey(getParam("userKey"));
			}
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
	@Get
	public Boundary withdrawal() {
		return forward("withdrawal.jsp");
	}
	

	
	/**
	 * 退会を実行
	 * @return
	 * @throws Exception 
	 */
	@Post
	public Boundary delete() throws Exception {
		// アカウント削除(退会処理)
		boolean knowledgeRemove = true;
		if ("2".equals(getParam("knowledge_remove"))) {
			knowledgeRemove = false;
		}
		UserLogic.get().withdrawal(getLoginUserId(), knowledgeRemove, HttpUtil.getLocale(getRequest()));
		
		// セッションを破棄
		AuthenticationLogic<LoginedUser> authenticationLogic = Container.getComp(DefaultAuthenticationLogicImpl.class);
		authenticationLogic.clearSession(getRequest());
		
		addMsgInfo("knowledge.account.delete");
		return devolution(HttpMethod.get, "Index/index");
		//return redirect(getRequest().getContextPath());
	}
	
	/**
	 * アイコン画像をアップロード
	 * @return
	 * @throws IOException 
	 */
	@Post
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
	
	/**
	 * メールアドレスの変更リクエストを登録する画面を表示
	 * @return
	 */
	@Get
	public Boundary changekey() {
		SystemConfigsDao dao = SystemConfigsDao.get();
		SystemConfigsEntity userAddType = dao.selectOnKey(SystemConfig.USER_ADD_TYPE, AppConfig.get().getSystemName());
		if (userAddType == null) {
			userAddType = new SystemConfigsEntity(SystemConfig.USER_ADD_TYPE, AppConfig.get().getSystemName());
			userAddType.setConfigValue(SystemConfig.USER_ADD_TYPE_VALUE_ADMIN);
		}
		if (!userAddType.getConfigValue().equals(SystemConfig.USER_ADD_TYPE_VALUE_MAIL)) {
			return sendError(HttpStatus.SC_403_FORBIDDEN, "FORBIDDEN");
		}
		//ダブルオプトインでユーザ登録をしている場合のみ、メールアドレス変更通知にてアドレスを変更する
		
		return forward("changekey.jsp");
	}
	

	/**
	 * メールアドレスの変更リクエストを登録
	 * @return
	 */
	@Post
	public Boundary changerequest() {
		SystemConfigsDao dao = SystemConfigsDao.get();
		SystemConfigsEntity userAddType = dao.selectOnKey(SystemConfig.USER_ADD_TYPE, AppConfig.get().getSystemName());
		if (userAddType == null) {
			userAddType = new SystemConfigsEntity(SystemConfig.USER_ADD_TYPE, AppConfig.get().getSystemName());
			userAddType.setConfigValue(SystemConfig.USER_ADD_TYPE_VALUE_ADMIN);
		}
		if (!userAddType.getConfigValue().equals(SystemConfig.USER_ADD_TYPE_VALUE_MAIL)) {
			return sendError(HttpStatus.SC_403_FORBIDDEN, "FORBIDDEN");
		}
		
		//ダブルオプトインでユーザ登録をしている場合のみ、メールアドレス変更通知にてアドレスを変更する
		AccountLogic accountLogic = AccountLogic.get();
		List<ValidateError> results = accountLogic.saveChangeEmailRequest(getParam("userKey"), getLoginedUser());
		
		setResult("message.success.insert.target", results, getResource("knowledge.account.changekey.title"));
		
		if (results != null && !results.isEmpty()) {
			return forward("changekey.jsp");
		}
		return forward("saveresult.jsp");
	}
	
	/**
	 * メールアドレス変更通知の確認
	 * @return
	 * @throws InvalidParamException 
	 */
	@Get
	public Boundary confirm_mail() throws InvalidParamException {
		// メールアドレス変更ができるのは、ダブルオプトインでユーザ登録する設定になっている場合のみ
		SystemConfigsDao dao = SystemConfigsDao.get();
		SystemConfigsEntity userAddType = dao.selectOnKey(SystemConfig.USER_ADD_TYPE, AppConfig.get().getSystemName());
		if (userAddType == null) {
			userAddType = new SystemConfigsEntity(SystemConfig.USER_ADD_TYPE, AppConfig.get().getSystemName());
			userAddType.setConfigValue(SystemConfig.USER_ADD_TYPE_VALUE_ADMIN);
		}
		if (!userAddType.getConfigValue().equals(SystemConfig.USER_ADD_TYPE_VALUE_MAIL)) {
			return sendError(HttpStatus.SC_403_FORBIDDEN, "FORBIDDEN");
		}
		
		String id = getPathString();
		if (StringUtils.isEmpty(id)) {
			return sendError(HttpStatus.SC_404_NOT_FOUND, "NOT FOUND");
		}
		
		AccountLogic accountLogic = AccountLogic.get();
		List<ValidateError> results = accountLogic.completeChangeEmailRequest(id, getLoginedUser());
		setResult("knowledge.account.changekey.complete", results);
		if (results != null && !results.isEmpty()) {
			return index(); //何かエラーになった場合アカウントの画面へ遷移
		}
		//return forward("complete.jsp");
		return index(); //エラーの無い場合でもアカウントの画面へ遷移（新しいメールアドレスになったことを確認）
	}
	
	
}
