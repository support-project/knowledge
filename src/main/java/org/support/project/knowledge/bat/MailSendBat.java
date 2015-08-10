package org.support.project.knowledge.bat;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.Properties;

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

import org.apache.commons.lang.ClassUtils;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.PasswordUtil;
import org.support.project.common.util.StringUtils;
import org.support.project.knowledge.config.AppConfig;
import org.support.project.web.dao.MailConfigsDao;
import org.support.project.web.dao.MailsDao;
import org.support.project.web.entity.MailConfigsEntity;
import org.support.project.web.entity.MailsEntity;

/**
 * メールの送信処理は、時間がかかるため、バッチ処理の中で処理する
 * 
 * @author Koda
 *
 */
public class MailSendBat extends AbstractBat {
	//private static final String MAIL_ENCODE = "ISO-2022-JP";
	private static final String MAIL_ENCODE = "UTF-8";

	/** ログ */
	private static Log LOG = LogFactory.getLog(MailSendBat.class);

	/** メールの状態：未送信（送信待ち） */
	public static final int MAIL_STATUS_UNSENT = 0;
	/** メールの状態：送信済 */
	public static final int MAIL_STATUS_SENDED = 10;
	/** メールの状態：なんらかのエラーが発生した */
	public static final int MAIL_STATUS_ERROR = -1;
	/** メールの状態：アドレスのフォーマットエラー */
	public static final int MAIL_STATUS_FORMAT_ERROR = -2;

	public static final String MAIL_FORMAT = "^[a-zA-Z0-9!#$%&'_`/=~\\*\\+\\-\\?\\^\\{\\|\\}]+(\\.[a-zA-Z0-9!#$%&'_`/=~\\*\\+\\-\\?\\^\\{\\|\\}]+)*"
			+ "@" + "[a-zA-Z0-9][a-zA-Z0-9\\-]*(\\.[a-zA-Z0-9\\-]+)*$";

	public static void main(String[] args) throws Exception {
		initLogName("MailSendBat.log");
		configInit(ClassUtils.getShortClassName(MailSendBat.class));
		
		MailSendBat bat = new MailSendBat();
		bat.dbInit();
		bat.start();
	}

	/**
	 * メール送信処理の実行
	 * 
	 * @throws UnsupportedEncodingException
	 * @throws MessagingException
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public void start() throws UnsupportedEncodingException, MessagingException, InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		MailConfigsDao mailConfigsDao = MailConfigsDao.get();
		MailConfigsEntity mailConfigsEntity = mailConfigsDao.selectOnKey(AppConfig.get().getSystemName());
		if (mailConfigsEntity == null) {
			// メールの設定が登録されていなければ、送信処理は終了
			return;
		}

		MailsDao dao = MailsDao.get();
		List<MailsEntity> entities = dao.selectOnStatus(MAIL_STATUS_UNSENT);
		int count = 0;
		for (MailsEntity mailsEntity : entities) {
			if (mailsEntity.getToAddress().matches(MAIL_FORMAT)) {
				mailSend(mailConfigsEntity, mailsEntity);
				// 正常に送信できたら、物理削除
				dao.physicalDelete(mailsEntity);
			} else {
				mailsEntity.setStatus(MAIL_STATUS_FORMAT_ERROR);
				dao.save(mailsEntity);
			}
			count++;
		}
		LOG.info("MAIL sended. count: " + count);
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
	private void mailSend(MailConfigsEntity config, MailsEntity entity) throws MessagingException, UnsupportedEncodingException, InvalidKeyException,
			NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		try {
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

			// ステータス更新
			MailsDao dao = MailsDao.get();
			entity.setStatus(MAIL_STATUS_SENDED);
			dao.save(entity);
		} catch (Exception e) {
			//TODO メール送信失敗、二度と送らないようにする（リトライする？）
			// 未送信にしておけば、再送できるが永遠に再送してしまう
			// カウント制御するべきか？
			LOG.error("Mail send error", e);
			MailsDao dao = MailsDao.get();
			entity.setStatus(MAIL_STATUS_ERROR);
			dao.save(entity);
		}
	}
}
