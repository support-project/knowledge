package org.support.project.knowledge.control.admin;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.support.project.common.bean.ValidateError;
import org.support.project.common.config.INT_FLAG;
import org.support.project.common.util.PasswordUtil;
import org.support.project.common.util.StringUtils;
import org.support.project.knowledge.config.AppConfig;
import org.support.project.knowledge.control.Control;
import org.support.project.web.annotation.Auth;
import org.support.project.web.boundary.Boundary;
import org.support.project.web.dao.MailConfigsDao;
import org.support.project.web.entity.MailConfigsEntity;

public class MailControl extends Control {

	/**
	 * メールの設定画面を表示
	 * @return
	 */
	@Auth(roles="admin")
	public Boundary config() {
		MailConfigsDao dao = MailConfigsDao.get();
		MailConfigsEntity entity = dao.selectOnKey(AppConfig.SYSTEM_NAME);
		if (entity == null) {
			entity = new MailConfigsEntity();
		}
		entity.setSystemName(AppConfig.SYSTEM_NAME);
		entity.setSmtpPassword(""); // パスワードは送らない
		setAttributeOnProperty(entity);
		
		return forward("config.jsp");
	}
	
	
	/**
	 * メールの設定を保存
	 * @return
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 * @throws NoSuchPaddingException 
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeyException 
	 */
	@Auth(roles="admin")
	public Boundary save() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		List<ValidateError> errors = new ArrayList<>();
		errors.addAll(MailConfigsEntity.get().validate(getParams()));
		
		String type = getParam("authType");
		//認証がONの場合のチェック
		if (type.equals(String.valueOf(INT_FLAG.ON.getValue()))) {
			if (StringUtils.isEmpty(getParam("smtpId"))) {
				ValidateError error = new ValidateError("認証する場合、SMTP IDは必須です");
				errors.add(error);
			}
			if (StringUtils.isEmpty(getParam("smtpPassword"))) {
				ValidateError error = new ValidateError("認証する場合、SMTP パスワードは必須です");
				errors.add(error);
			}
		}
		if (!errors.isEmpty()) {
			setResult(null, errors);
			return forward("config.jsp");
		}
		
		MailConfigsEntity entity = super.getParams(MailConfigsEntity.class);
		
		// パスワードは暗号化する
		String salt = PasswordUtil.getSalt();
		entity.setSmtpPassword(PasswordUtil.encrypt(entity.getSmtpPassword(), salt));
		entity.setSalt(salt);
		
		MailConfigsDao dao = MailConfigsDao.get();
		entity = dao.save(entity);
		setAttributeOnProperty(entity);
		
		// TODO テストでメール送信（更新したユーザのメールアドレス宛に、メールを送る）
		
		String successMsg = "保存しました";
		setResult(successMsg, errors);
		
		return forward("config.jsp");
	}
	
	
	
	/**
	 * メールの設定を削除
	 * @return
	 */
	@Auth(roles="admin")
	public Boundary delete() {
		MailConfigsDao dao = MailConfigsDao.get();
		dao.physicalDelete(AppConfig.SYSTEM_NAME); // 物理削除で消してしまう
		
		MailConfigsEntity entity = new MailConfigsEntity();
		entity.setSystemName(AppConfig.SYSTEM_NAME);
		setAttributeOnProperty(entity);
		
		addMsgInfo("メールの設定を削除しました");
		
		return forward("config.jsp");
	}

	
}
