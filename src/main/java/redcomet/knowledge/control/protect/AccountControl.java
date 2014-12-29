package redcomet.knowledge.control.protect;

import java.util.List;
import java.util.Map;

import redcomet.common.bean.ValidateError;
import redcomet.common.util.StringUtils;
import redcomet.di.Container;
import redcomet.knowledge.control.Control;
import redcomet.knowledge.logic.UserLogic;
import redcomet.web.bean.LoginedUser;
import redcomet.web.boundary.Boundary;
import redcomet.web.common.HttpStatus;
import redcomet.web.dao.UsersDao;
import redcomet.web.entity.UsersEntity;
import redcomet.web.logic.AuthenticationLogic;
import redcomet.web.logic.impl.DefaultAuthenticationLogicImpl;

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
				ValidateError error = new ValidateError("PasswordとConfirm Passwordが違っています");
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
		String successMsg = "更新しました";
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
		
		addMsgInfo("ご利用ありがとうございました");
		return devolution("index/index");
		//return redirect(getRequest().getContextPath());
	}
	
	
}
