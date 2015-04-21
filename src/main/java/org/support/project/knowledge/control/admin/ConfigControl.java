package org.support.project.knowledge.control.admin;

import java.util.ArrayList;
import java.util.List;

import org.support.project.common.bean.ValidateError;
import org.support.project.common.util.StringUtils;
import org.support.project.knowledge.config.AppConfig;
import org.support.project.knowledge.config.SystemConfig;
import org.support.project.knowledge.control.Control;
import org.support.project.web.annotation.Auth;
import org.support.project.web.boundary.Boundary;
import org.support.project.web.common.HttpUtil;
import org.support.project.web.dao.MailConfigsDao;
import org.support.project.web.dao.SystemConfigsDao;
import org.support.project.web.entity.MailConfigsEntity;
import org.support.project.web.entity.SystemConfigsEntity;

public class ConfigControl extends Control {

	/**
	 * ユーザ登録方法設定画面を表示
	 * 
	 * @return
	 */
	@Auth(roles = "admin")
	public Boundary config() {
		SystemConfigsDao dao = SystemConfigsDao.get();

		SystemConfigsEntity userAddType = dao.selectOnKey(SystemConfig.USER_ADD_TYPE, AppConfig.SYSTEM_NAME);
		if (userAddType == null) {
			userAddType = new SystemConfigsEntity(SystemConfig.USER_ADD_TYPE, AppConfig.SYSTEM_NAME);
			userAddType.setConfigValue(SystemConfig.USER_ADD_TYPE_VALUE_ADMIN);
		}
		setAttribute("userAddType", userAddType.getConfigValue());

		SystemConfigsEntity userAddNotify = dao.selectOnKey(SystemConfig.USER_ADD_NOTIFY, AppConfig.SYSTEM_NAME);
		if (userAddNotify == null) {
			userAddNotify = new SystemConfigsEntity(SystemConfig.USER_ADD_NOTIFY, AppConfig.SYSTEM_NAME);
			userAddNotify.setConfigValue(SystemConfig.USER_ADD_NOTIFY_OFF);
		}
		setAttribute("userAddNotify", userAddNotify.getConfigValue());

		return forward("config.jsp");
	}

	/**
	 * ユーザ登録方法設定を保存
	 * 
	 * @return
	 */
	@Auth(roles = "admin")
	public Boundary save() {
		List<ValidateError> errors = new ArrayList<>();
		String type = getParam("userAddType");
		String notify = getParam("userAddNotify");
		// メール送信の場合、メールの設定が完了しているかチェック
		if ((type != null && type.equals(SystemConfig.USER_ADD_TYPE_VALUE_MAIL))
				|| (notify != null && notify.equals(SystemConfig.USER_ADD_NOTIFY_ON))) {
			MailConfigsDao mailConfigsDao = MailConfigsDao.get();
			MailConfigsEntity mailConfigsEntity = mailConfigsDao.selectOnKey(AppConfig.SYSTEM_NAME);
			if (mailConfigsEntity == null) {
				ValidateError error = new ValidateError("knowledge.config.mail.require");
				errors.add(error);
			}
		}
		if (!errors.isEmpty()) {
			setResult(null, errors);
			return forward("config.jsp");
		}

		SystemConfigsDao dao = SystemConfigsDao.get();
		SystemConfigsEntity userAddType = new SystemConfigsEntity(SystemConfig.USER_ADD_TYPE, AppConfig.SYSTEM_NAME);
		userAddType.setConfigValue(type);
		dao.save(userAddType);

		SystemConfigsEntity userAddNotify = new SystemConfigsEntity(SystemConfig.USER_ADD_NOTIFY, AppConfig.SYSTEM_NAME);
		userAddNotify.setConfigValue(notify);
		dao.save(userAddNotify);

		String successMsg = "message.success.save";
		setResult(successMsg, errors);

		return forward("config.jsp");
	}

	/**
	 * システム設定画面を表示
	 * 
	 * @return
	 */
	@Auth(roles = "admin")
	public Boundary system() {
		SystemConfigsDao dao = SystemConfigsDao.get();
		SystemConfigsEntity config = dao.selectOnKey(SystemConfig.SYSTEM_URL, AppConfig.SYSTEM_NAME);
		if (config == null) {
			String url = HttpUtil.getContextUrl(getRequest());
			config = new SystemConfigsEntity(SystemConfig.SYSTEM_URL, AppConfig.SYSTEM_NAME);
			config.setConfigValue(url);
			dao.save(config);
		}
		setAttribute("systemurl", config.getConfigValue());
		return forward("system.jsp");
	}

	/**
	 * ユーザ登録方法設定を保存
	 * 
	 * @return
	 */
	@Auth(roles = "admin")
	public Boundary save_params() {
		List<ValidateError> errors = new ArrayList<>();
		String systemurl = getParam("systemurl");
		// メール送信の場合、メールの設定が完了しているかチェック
		if (StringUtils.isEmpty(systemurl)) {
			ValidateError error = new ValidateError("errors.required", getResource("knowledge.config.system.label.url"));
			errors.add(error);
		}
		if (!errors.isEmpty()) {
			setResult(null, errors);
			return forward("system.jsp");
		}

		SystemConfigsDao dao = SystemConfigsDao.get();
		SystemConfigsEntity config = new SystemConfigsEntity(SystemConfig.SYSTEM_URL, AppConfig.SYSTEM_NAME);
		config.setConfigValue(systemurl);
		dao.save(config);
		
		String successMsg = "message.success.save";
		setResult(successMsg, errors);

		return forward("system.jsp");
	}	
	
	
}
