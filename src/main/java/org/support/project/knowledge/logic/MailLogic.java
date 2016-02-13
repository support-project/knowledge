package org.support.project.knowledge.logic;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.UUID;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.support.project.common.config.ConfigLoader;
import org.support.project.common.config.LocaleConfigLoader;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.DateUtils;
import org.support.project.common.util.PasswordUtil;
import org.support.project.common.util.StringUtils;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.bat.MailSendBat;
import org.support.project.knowledge.config.AppConfig;
import org.support.project.knowledge.config.MailConfig;
import org.support.project.knowledge.config.SystemConfig;
import org.support.project.web.bean.LoginedUser;
import org.support.project.web.config.WebConfig;
import org.support.project.web.dao.MailConfigsDao;
import org.support.project.web.dao.MailsDao;
import org.support.project.web.dao.SystemConfigsDao;
import org.support.project.web.dao.UsersDao;
import org.support.project.web.entity.ConfirmMailChangesEntity;
import org.support.project.web.entity.MailConfigsEntity;
import org.support.project.web.entity.MailsEntity;
import org.support.project.web.entity.PasswordResetsEntity;
import org.support.project.web.entity.ProvisionalRegistrationsEntity;
import org.support.project.web.entity.SystemConfigsEntity;
import org.support.project.web.entity.UsersEntity;

@DI(instance=Instance.Singleton)
public class MailLogic {
	//private static final String MAIL_ENCODE = "ISO-2022-JP";
	private static final String MAIL_ENCODE = "UTF-8";
	
	/** ログ */
	private static Log LOG = LogFactory.getLog(MailLogic.class);
	
	//private static final DateFormat DAY_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss");
	private static final String MAIL_CONFIG_DIR = "/org/support/project/knowledge/mail/";

	
	public static MailLogic get() {
		return Container.getComp(MailLogic.class);
	}
	
	/**
	 * メールを送信
	 * 
	 * @param config
	 * @param entity
	 * @throws MessagingException
	 * @throws UnsupportedEncodingException
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws NoSuchPaddingException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 */
	public void mailSend(MailConfigsEntity config, MailsEntity entity) throws InvalidKeyException, NoSuchAlgorithmException, 
		NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException, MessagingException  {
		String host = config.getHost();
		String port = String.valueOf(config.getPort());

		String to = entity.getToAddress();
		String toName = entity.getToName();

		String from = entity.getFromAddress();
		String fromName = entity.getFromName();
		if (StringUtils.isEmpty(from)) {
			from = config.getFromAddress();
		}
		if (StringUtils.isEmpty(fromName)) {
			fromName = config.getFromName();
		}

		String title = entity.getTitle();
		String message = entity.getContent();

		Properties property = new Properties();
		property.put("mail.smtp.host", host);
		property.put("mail.smtp.port", port);
		property.put("mail.smtp.socketFactory.port", port);
		property.put("mail.smtp.debug", "true");
		property.put("mail.debug", "true");

		Session session;
		if (1 == config.getAuthType()) {
			// 認証あり
			final String smtpid = config.getSmtpId();
			final String smtppass = PasswordUtil.decrypt(config.getSmtpPassword(), config.getSalt());

			property.put("mail.smtp.auth", "true");
			property.put("mail.smtp.starttls.enable", "true");
			property.put("mail.smtp.ssl.trust", host);

			session = Session.getInstance(property, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(smtpid, smtppass);
				}
			});
		} else {
			// 認証無し
			session = Session.getDefaultInstance(property);
		}

		MimeMessage mimeMessage = new MimeMessage(session);
		InternetAddress toAddress = new InternetAddress(to, toName, MAIL_ENCODE);
		mimeMessage.setRecipient(Message.RecipientType.TO, toAddress);
		InternetAddress fromAddress = new InternetAddress(from, fromName, MAIL_ENCODE);
		mimeMessage.setFrom(fromAddress);
		mimeMessage.setSubject(title, MAIL_ENCODE);
		mimeMessage.setText(message, MAIL_ENCODE);

		// メールの形式を指定
		// mimeMessage.setHeader( "Content-Type", "text/html" );
		// 送信日付を指定
		mimeMessage.setSentDate(new Date());

		Transport.send(mimeMessage);
		LOG.debug("Mail sended.");
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
		builder.append(DateUtils.getSimpleFormat().format(new Date()));
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
		MailConfig mailConfig = LocaleConfigLoader.load(MAIL_CONFIG_DIR, configName, locale, MailConfig.class);
		return mailConfig;
	}
	
	
	/**
	 * URLを生成
	 * @param id
	 * @return
	 */
	private CharSequence makeURL(String servletPath, String id) {
		SystemConfigsDao dao = SystemConfigsDao.get();
		SystemConfigsEntity config = dao.selectOnKey(SystemConfig.SYSTEM_URL, AppConfig.get().getSystemName());
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
		MailConfigsDao mailConfigsDao = MailConfigsDao.get();
		MailConfigsEntity mailConfigsEntity = mailConfigsDao.selectOnKey(org.support.project.knowledge.config.AppConfig.get().getSystemName());
		if (mailConfigsEntity == null) {
			// メールの設定が登録されていなければ、送信処理は終了
			return;
		}
		
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
		MailConfigsDao mailConfigsDao = MailConfigsDao.get();
		MailConfigsEntity mailConfigsEntity = mailConfigsDao.selectOnKey(org.support.project.knowledge.config.AppConfig.get().getSystemName());
		if (mailConfigsEntity == null) {
			// メールの設定が登録されていなければ、送信処理は終了
			return;
		}
		
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
		MailConfigsDao mailConfigsDao = MailConfigsDao.get();
		MailConfigsEntity mailConfigsEntity = mailConfigsDao.selectOnKey(org.support.project.knowledge.config.AppConfig.get().getSystemName());
		if (mailConfigsEntity == null) {
			// メールの設定が登録されていなければ、送信処理は終了
			return;
		}
		
		LOG.trace("sendNotifyAddUser");
		SystemConfigsDao configsDao = SystemConfigsDao.get();
		SystemConfigsEntity configsEntity = configsDao.selectOnKey(SystemConfig.USER_ADD_NOTIFY, AppConfig.get().getSystemName());
		if (configsEntity != null && SystemConfig.USER_ADD_NOTIFY_ON.equals(configsEntity.getConfigValue())) {
			// 管理者へのメール通知がONなので、メールを送信する
			UsersDao usersDao = UsersDao.get();
			List<UsersEntity> users = usersDao.selectOnRoleKey(WebConfig.ROLE_ADMIN);
			for (UsersEntity entity : users) {
				if (!StringUtils.isEmailAddress(entity.getMailAddress())) {
					// 送信先のメールアドレスが不正なので、送信処理は終了
					LOG.warn("mail targget [" + entity.getMailAddress() + "] is wrong.");
					continue;
				}
				Locale locale = entity.getLocale();
				MailConfig mailConfig = load("notify_add_user", locale);
				
				String contents = mailConfig.getContents();
				contents = contents.replace("{UserId}", String.valueOf(user.getUserId()));
				contents = contents.replace("{UserName}", user.getUserName());
				contents = contents.replace("{UserMail}", user.getMailAddress());
				String title = mailConfig.getTitle();
				
				MailsDao mailsDao = MailsDao.get();
				MailsEntity mailsEntity = new MailsEntity();
				String mailId = idGen("Notify");
				mailsEntity.setMailId(mailId);
				mailsEntity.setStatus(MailSendBat.MAIL_STATUS_UNSENT);
				mailsEntity.setToAddress(entity.getMailAddress());
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
		MailConfigsDao mailConfigsDao = MailConfigsDao.get();
		MailConfigsEntity mailConfigsEntity = mailConfigsDao.selectOnKey(org.support.project.knowledge.config.AppConfig.get().getSystemName());
		if (mailConfigsEntity == null) {
			// メールの設定が登録されていなければ、送信処理は終了
			return;
		}
		
		LOG.trace("sendNotifyAcceptUser");
		SystemConfigsDao configsDao = SystemConfigsDao.get();
		SystemConfigsEntity configsEntity = configsDao.selectOnKey(SystemConfig.USER_ADD_NOTIFY, AppConfig.get().getSystemName());
		if (configsEntity != null && SystemConfig.USER_ADD_NOTIFY_ON.equals(configsEntity.getConfigValue())) {
			// 管理者へのメール通知がONなので、メールを送信する
			UsersDao usersDao = UsersDao.get();
			List<UsersEntity> users = usersDao.selectOnRoleKey(WebConfig.ROLE_ADMIN);
			for (UsersEntity entity : users) {
				if (!StringUtils.isEmailAddress(entity.getMailAddress())) {
					// 送信先のメールアドレスが不正なので、送信処理は終了
					LOG.warn("mail targget [" + entity.getMailAddress() + "] is wrong.");
					continue;
				}
				Locale locale = entity.getLocale();
				MailConfig mailConfig = load("notify_accept_user", locale);
				
				String contents = mailConfig.getContents();
				String title = mailConfig.getTitle();
				
				MailsDao mailsDao = MailsDao.get();
				MailsEntity mailsEntity = new MailsEntity();
				String mailId = idGen("Notify");
				mailsEntity.setMailId(mailId);
				mailsEntity.setStatus(MailSendBat.MAIL_STATUS_UNSENT);
				mailsEntity.setToAddress(entity.getMailAddress());
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
		MailConfigsDao mailConfigsDao = MailConfigsDao.get();
		MailConfigsEntity mailConfigsEntity = mailConfigsDao.selectOnKey(org.support.project.knowledge.config.AppConfig.get().getSystemName());
		if (mailConfigsEntity == null) {
			// メールの設定が登録されていなければ、送信処理は終了
			return;
		}
		
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
		MailConfigsDao mailConfigsDao = MailConfigsDao.get();
		MailConfigsEntity mailConfigsEntity = mailConfigsDao.selectOnKey(org.support.project.knowledge.config.AppConfig.get().getSystemName());
		if (mailConfigsEntity == null) {
			// メールの設定が登録されていなければ、送信処理は終了
			return;
		}
		
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
