package org.support.project.knowledge.logic;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.support.project.common.config.ConfigLoader;
import org.support.project.common.config.LocaleConfigLoader;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.di.Container;
import org.support.project.knowledge.bat.MailSendBat;
import org.support.project.knowledge.config.AppConfig;
import org.support.project.knowledge.config.MailConfig;
import org.support.project.knowledge.config.SystemConfig;
import org.support.project.web.dao.MailsDao;
import org.support.project.web.dao.SystemConfigsDao;
import org.support.project.web.dao.UsersDao;
import org.support.project.web.entity.MailsEntity;
import org.support.project.web.entity.ProvisionalRegistrationsEntity;
import org.support.project.web.entity.SystemConfigsEntity;
import org.support.project.web.entity.UsersEntity;

public class MailLogic {
	/** ログ */
	private static Log LOG = LogFactory.getLog(MailLogic.class);
	
	private DateFormat DAY_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss");
	
	public static MailLogic get() {
		return Container.getComp(MailLogic.class);
	}
	
	/**
	 * メール設定の読み込み
	 * @param configName
	 * @param locale
	 * @return
	 */
	public MailConfig load(String configName, Locale locale) {
		String base = "/org/support/project/knowledge/mail/";
		MailConfig mailConfig = LocaleConfigLoader.load(base, configName, locale, MailConfig.class);
		return mailConfig;
	}
	
	/**
	 * ユーザが仮登録されたので、そのユーザに招待のメールを送信するために、
	 * メール送信テーブルに登録する
	 * （メールの送信処理は、非同期にバッチで実行される）
	 * @param entity
	 * @param url
	 */
	public void sendInvitation(ProvisionalRegistrationsEntity entity, String url, Locale locale) {
		LOG.trace("sendInvitation");
		MailsDao mailsDao = MailsDao.get();
		MailsEntity mailsEntity = new MailsEntity();
		String mailId = idGenu("Invitation");
		mailsEntity.setMailId(mailId);
		mailsEntity.setStatus(MailSendBat.MAIL_STATUS_UNSENT);
		mailsEntity.setToAddress(entity.getUserKey());
		mailsEntity.setToName(entity.getUserName());
		
		MailConfig mailConfig = load("invitation", locale);
		
		mailsEntity.setTitle(mailConfig.getTitle());
		
		String contents = mailConfig.getContents();
		contents = contents.replace("{UserName}", entity.getUserName());
		StringBuilder path = new StringBuilder();
		path.append(url);
		path.append("/open.signup/activate/");
		path.append(entity.getId());
		contents = contents.replace("{URL}", path.toString());
		mailsEntity.setContent(contents);
		
		mailsDao.insert(mailsEntity);
	}
	
	/**
	 * ユーザ登録が承認されたことをユーザに通知
	 * @param entity
	 * @param url
	 */
	public void sendAcceptedAddRequest(ProvisionalRegistrationsEntity entity, String url) {
		LOG.trace("sendAcceptedAddRequest");
		MailsDao mailsDao = MailsDao.get();
		MailsEntity mailsEntity = new MailsEntity();
		String mailId = idGenu("Accept");
		mailsEntity.setMailId(mailId);
		mailsEntity.setStatus(MailSendBat.MAIL_STATUS_UNSENT);
		mailsEntity.setToAddress(entity.getUserKey());
		mailsEntity.setToName(entity.getUserName());
		
		//TODO 仮登録時に、どのロケールでメールを出したかを保持しておかないと、言語を決められない、、、
		MailConfig mailConfig = ConfigLoader.load("/org/support/project/knowledge/mail/accept_user.xml", MailConfig.class);
		
		mailsEntity.setTitle(mailConfig.getTitle());
		
		String contents = mailConfig.getContents();
		contents = contents.replace("{UserName}", entity.getUserName());
		StringBuilder path = new StringBuilder();
		path.append(url);
		contents = contents.replace("{URL}", path.toString());
		mailsEntity.setContent(contents);
		
		mailsDao.insert(mailsEntity);
	}
	
	
	/**
	 * メール送信のIDを生成
	 * @param string
	 * @return
	 */
	private String idGenu(String label) {
		StringBuilder builder = new StringBuilder();
		builder.append(label);
		builder.append("-");
		builder.append(DAY_FORMAT.format(new Date()));
		builder.append("-");
		builder.append(UUID.randomUUID().toString());
		return builder.toString();
	}
	
	
	/**
	 * ユーザ登録通知
	 * @param user
	 */
	public void sendNotifyAddUser(UsersEntity user) {
		LOG.trace("sendNotifyAddUser");
		SystemConfigsDao configsDao = SystemConfigsDao.get();
		SystemConfigsEntity configsEntity = configsDao.selectOnKey(SystemConfig.USER_ADD_NOTIFY, AppConfig.SYSTEM_NAME);
		if (configsEntity != null && SystemConfig.USER_ADD_NOTIFY_ON.equals(configsEntity.getConfigValue())) {
			// 管理者へのメール通知がONなので、メールを送信する
			UsersDao usersDao = UsersDao.get();
			List<UsersEntity> users = usersDao.selectOnRoleKey(SystemConfig.ROLE_ADMIN);
			for (UsersEntity entity : users) {
				Locale locale = entity.getLocale();
				MailConfig mailConfig = load("notify_add_user", locale);
				
				String contents = mailConfig.getContents();
				contents = contents.replace("{UserId}", String.valueOf(user.getUserId()));
				String title = mailConfig.getTitle();
				
				MailsDao mailsDao = MailsDao.get();
				MailsEntity mailsEntity = new MailsEntity();
				String mailId = idGenu("Notify");
				mailsEntity.setMailId(mailId);
				mailsEntity.setStatus(MailSendBat.MAIL_STATUS_UNSENT);
				mailsEntity.setToAddress(entity.getUserKey());
				mailsEntity.setToName(entity.getUserName());
				mailsEntity.setTitle(title);
				mailsEntity.setContent(contents);
				mailsDao.insert(mailsEntity);
			}
		}
	}

	
	/**
	 * ユーザの仮登録通知
	 * @param entity
	 */
	public void sendNotifyAcceptUser(ProvisionalRegistrationsEntity registrationsEntity) {
		LOG.trace("sendNotifyAcceptUser");
		SystemConfigsDao configsDao = SystemConfigsDao.get();
		SystemConfigsEntity configsEntity = configsDao.selectOnKey(SystemConfig.USER_ADD_NOTIFY, AppConfig.SYSTEM_NAME);
		if (configsEntity != null && SystemConfig.USER_ADD_NOTIFY_ON.equals(configsEntity.getConfigValue())) {
			// 管理者へのメール通知がONなので、メールを送信する
			UsersDao usersDao = UsersDao.get();
			List<UsersEntity> users = usersDao.selectOnRoleKey(SystemConfig.ROLE_ADMIN);
			for (UsersEntity entity : users) {
				Locale locale = entity.getLocale();
				MailConfig mailConfig = load("notify_accept_user", locale);
				
				String contents = mailConfig.getContents();
				String title = mailConfig.getTitle();
				
				MailsDao mailsDao = MailsDao.get();
				MailsEntity mailsEntity = new MailsEntity();
				String mailId = idGenu("Notify");
				mailsEntity.setMailId(mailId);
				mailsEntity.setStatus(MailSendBat.MAIL_STATUS_UNSENT);
				mailsEntity.setToAddress(entity.getUserKey());
				mailsEntity.setToName(entity.getUserName());
				mailsEntity.setTitle(title);
				mailsEntity.setContent(contents);
				mailsDao.insert(mailsEntity);
			}
		}
	}
	
	
	
	
	
	
}
