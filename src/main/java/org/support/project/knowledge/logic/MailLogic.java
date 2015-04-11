package org.support.project.knowledge.logic;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.support.project.common.config.ConfigLoader;
import org.support.project.common.config.LocaleConfigLoader;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.DateUtils;
import org.support.project.common.util.StringUtils;
import org.support.project.di.Container;
import org.support.project.knowledge.bat.MailSendBat;
import org.support.project.knowledge.config.AppConfig;
import org.support.project.knowledge.config.MailConfig;
import org.support.project.knowledge.config.SystemConfig;
import org.support.project.web.bean.LoginedUser;
import org.support.project.web.dao.MailsDao;
import org.support.project.web.dao.SystemConfigsDao;
import org.support.project.web.dao.UsersDao;
import org.support.project.web.entity.ConfirmMailChangesEntity;
import org.support.project.web.entity.MailsEntity;
import org.support.project.web.entity.PasswordResetsEntity;
import org.support.project.web.entity.ProvisionalRegistrationsEntity;
import org.support.project.web.entity.SystemConfigsEntity;
import org.support.project.web.entity.UsersEntity;

public class MailLogic {
	/** ログ */
	private static Log LOG = LogFactory.getLog(MailLogic.class);
	
	//private static final DateFormat DAY_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss");
	private static final String MAIL_CONFIG_DIR = "/org/support/project/knowledge/mail/";

	
	public static MailLogic get() {
		return Container.getComp(MailLogic.class);
	}
	
	/**
	 * メール送信のIDを生成
	 * @param label 11桁まで
	 * @return
	 */
	public String idGen(String label) {
		StringBuilder builder = new StringBuilder();
		builder.append(label);
		builder.append("-");
		builder.append(DateUtils.SECOND_FORMAT.format(new Date()));
		builder.append("-");
		builder.append(UUID.randomUUID().toString());
		return builder.toString();
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
	 * URLを生成
	 * @param id
	 * @return
	 */
	private CharSequence makeURL(String servletPath, String id) {
		SystemConfigsDao dao = SystemConfigsDao.get();
		SystemConfigsEntity config = dao.selectOnKey(SystemConfig.SYSTEM_URL, AppConfig.SYSTEM_NAME);
		if (config == null) {
			return "";
		}
		
		StringBuilder builder = new StringBuilder();
		builder.append(config.getConfigValue());
		if (!config.getConfigValue().endsWith("/")) {
			builder.append("/");
		}
		builder.append(servletPath);
		if (!StringUtils.isEmpty(id)) {
			if (!servletPath.endsWith("/")) {
				builder.append("/");
			}
			builder.append(id);
		}
		return builder.toString();
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
		String mailId = idGen("Invitation");
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
		String mailId = idGen("Accept");
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
				String mailId = idGen("Notify");
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
				String mailId = idGen("Notify");
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
	 * メール変更のリクエストを受付
	 * @param email
	 * @param locale
	 * @param resetsEntity
	 */
	public void sendPasswordReset(String email, Locale locale, PasswordResetsEntity resetsEntity) {
		MailsEntity mailsEntity = new MailsEntity();
		String mailId = idGen("MAIL-RESET");
		mailsEntity.setMailId(mailId);
		mailsEntity.setStatus(MailSendBat.MAIL_STATUS_UNSENT);
		mailsEntity.setToAddress(email);
		mailsEntity.setToName(email);
		
		MailConfig config = LocaleConfigLoader.load(MAIL_CONFIG_DIR, "password_reset", locale, MailConfig.class);;

		String title = config.getTitle();
		mailsEntity.setTitle(title);
		String contents = config.getContents();
		contents = contents.replace("{MAIL}", email);
		contents = contents.replace("{URL}", makeURL("open.PasswordInitialization/init/", resetsEntity.getId()));

		mailsEntity.setContent(contents);
		MailsDao.get().insert(mailsEntity);
	}

	
	
	/**
	 * メールアドレス変更確認
	 * @param mailChangesEntity
	 * @param loginedUser
	 */
	public void sendChangeEmailRequest(ConfirmMailChangesEntity mailChangesEntity, LoginedUser loginedUser) {
		MailsEntity mailsEntity = new MailsEntity();
		String mailId = idGen("MAIL-CHANGE");
		mailsEntity.setMailId(mailId);
		mailsEntity.setStatus(MailSendBat.MAIL_STATUS_UNSENT);
		mailsEntity.setToAddress(mailChangesEntity.getMailAddress());
		mailsEntity.setToName(loginedUser.getLoginUser().getUserName());
		
		MailConfig config = LocaleConfigLoader.load(MAIL_CONFIG_DIR, "mail_confirm", loginedUser.getLocale(), MailConfig.class);;

		String title = config.getTitle();
		mailsEntity.setTitle(title);
		String contents = config.getContents();
		contents = contents.replace("{UserName}", loginedUser.getLoginUser().getUserName());
		contents = contents.replace("{URL}", makeURL("protect.Account/confirm_mail/", mailChangesEntity.getId()));

		mailsEntity.setContent(contents);
		MailsDao.get().insert(mailsEntity);
	}



	
}
