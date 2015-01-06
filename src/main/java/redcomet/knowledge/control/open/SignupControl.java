package redcomet.knowledge.control.open;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import redcomet.aop.Aspect;
import redcomet.common.bean.ValidateError;
import redcomet.common.util.StringUtils;
import redcomet.common.validate.Validator;
import redcomet.common.validate.ValidatorFactory;
import redcomet.di.Container;
import redcomet.knowledge.config.AppConfig;
import redcomet.knowledge.config.SystemConfig;
import redcomet.knowledge.control.Control;
import redcomet.knowledge.logic.MailLogic;
import redcomet.knowledge.logic.UserLogic;
import redcomet.web.boundary.Boundary;
import redcomet.web.common.HttpStatus;
import redcomet.web.dao.ProvisionalRegistrationsDao;
import redcomet.web.dao.SystemConfigsDao;
import redcomet.web.dao.UsersDao;
import redcomet.web.entity.ProvisionalRegistrationsEntity;
import redcomet.web.entity.SystemConfigsEntity;
import redcomet.web.entity.UsersEntity;
import redcomet.web.logic.AuthenticationLogic;
import redcomet.web.logic.impl.DefaultAuthenticationLogicImpl;

public class SignupControl extends Control {

	/**
	 * ユーザのサインアップ画面を表示
	 * @return
	 */
	public Boundary view() {
		return forward("signup.jsp");
	}
	
	/**
	 * 新規登録処理を保存
	 * @return
	 */
	public Boundary save() {
		SystemConfigsDao systemConfigsDao = SystemConfigsDao.get();
		SystemConfigsEntity userAddType = systemConfigsDao.selectOnKey(SystemConfig.USER_ADD_TYPE, AppConfig.SYSTEM_NAME);
		if (userAddType == null) {
			// ユーザによるデータの追加は認められていない
			return sendError(HttpStatus.SC_404_NOT_FOUND, "NOT FOUND");
		}
		if (userAddType.getConfigValue().equals(SystemConfig.USER_ADD_TYPE_VALUE_ADMIN)) {
			// ユーザによるデータの追加は認められていない
			return sendError(HttpStatus.SC_404_NOT_FOUND, "NOT FOUND");
		}
		
		List<ValidateError> errors = validate();
		if (!errors.isEmpty()) {
			setResult(null, errors);
			return forward("signup.jsp");
		}

		if (userAddType.getConfigValue().equals(SystemConfig.USER_ADD_TYPE_VALUE_USER)) {
			// ユーザが自分で登録
			addUser();
			addMsgInfo("ユーザ登録しました");
			return redirect(getRequest().getContextPath());
		} else if (userAddType.getConfigValue().equals(SystemConfig.USER_ADD_TYPE_VALUE_MAIL)
				|| userAddType.getConfigValue().equals(SystemConfig.USER_ADD_TYPE_VALUE_APPROVE)) {
			// ユーザは仮登録を行う
			ProvisionalRegistrationsEntity entity = addProvisionalRegistration();
			if (userAddType.getConfigValue().equals(SystemConfig.USER_ADD_TYPE_VALUE_MAIL)) {
				// 招待のメールを送信
				MailLogic mailLogic = MailLogic.get();
				mailLogic.sendInvitation(entity);
				return forward("mail_sended.jsp");
			} else {
				// 管理者へメール通知
				return forward("provisional_registration.jsp");
			}
		}
		return sendError(HttpStatus.SC_404_NOT_FOUND, "NOT FOUND");
	}

	
	
	/**
	 * 仮登録
	 * @return 
	 */
	@Aspect(advice=redcomet.ormapping.transaction.Transaction.class)
	private ProvisionalRegistrationsEntity addProvisionalRegistration() {
		ProvisionalRegistrationsEntity entity = super.getParams(ProvisionalRegistrationsEntity.class);
		String id = UUID.randomUUID().toString() + "-" + new Date().getTime() + "-" + UUID.randomUUID().toString();
		entity.setId(id);
		ProvisionalRegistrationsDao dao = ProvisionalRegistrationsDao.get();
		//既に仮登録が行われたユーザ(メールアドレス)でも、再度仮登録できる
		//ただし、以前の登録は無効にする
		dao.deleteOnUserKey(entity.getUserKey());
		//データ登録
		entity = dao.insert(entity);
		return entity;
	}
	
	/**
	 * ユーザ追加
	 */
	private void addUser() {
		// エラーが無い場合のみ登録
		UsersEntity user = super.getParams(UsersEntity.class);
		String[] roles = {SystemConfig.ROLE_USER};
		user = UserLogic.get().insert(user, roles, getLoginedUser());
		setAttributeOnProperty(user);

		// ログイン処理
		AuthenticationLogic logic = Container.getComp(DefaultAuthenticationLogicImpl.class);
		logic.setSession(user.getUserKey(), getRequest());
	}
	
	/**
	 * 入力チェック
	 * @return
	 */
	private List<ValidateError> validate() {
		List<ValidateError> errors = UsersEntity.get().validate(getParams());
		if (!StringUtils.isEmpty(getParam("password"))) {
			if (!getParam("password").equals(getParam("confirm_password", String.class))) {
				ValidateError error = new ValidateError("PasswordとConfirm Passwordが違っています");
				errors.add(error);
			}
		}
		UsersDao dao = UsersDao.get();
		UsersEntity user = dao.selectOnUserKey(getParam("userKey"));
		if (user != null) {
			ValidateError error = new ValidateError("既に登録されているEmail Addressです");
			errors.add(error);
		}
		
		Validator validator = ValidatorFactory.getInstance(Validator.MAIL);
		ValidateError error = validator.validate(getParam("userKey"), "Email Address");
		if (error != null) {
			errors.add(error);
		}
		return errors;
	}
	
	
}
