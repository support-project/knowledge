package org.support.project.knowledge.logic;

import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

import org.support.project.aop.Aspect;
import org.support.project.common.config.ConfigLoader;
import org.support.project.common.config.LocaleConfigLoader;
import org.support.project.common.config.Resources;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.DateUtils;
import org.support.project.common.util.FileUtil;
import org.support.project.common.util.PasswordUtil;
import org.support.project.common.util.RandomUtil;
import org.support.project.common.util.StringUtils;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.config.AppConfig;
import org.support.project.knowledge.config.MailConfig;
import org.support.project.knowledge.config.SystemConfig;
import org.support.project.knowledge.dao.KnowledgesDao;
import org.support.project.knowledge.dao.MailLocaleTemplatesDao;
import org.support.project.knowledge.dao.MailTemplatesDao;
import org.support.project.knowledge.entity.KnowledgesEntity;
import org.support.project.knowledge.entity.MailLocaleTemplatesEntity;
import org.support.project.knowledge.entity.MailTemplatesEntity;
import org.support.project.knowledge.parser.Parser;
import org.support.project.knowledge.parser.ParserFactory;
import org.support.project.knowledge.vo.ParseResult;
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

@DI(instance = Instance.Singleton)
public class MailLogic {
    public static final String TEST_MAIL = "test_mail";
    public static final String NOTIFY_ACCEPT_USER = "notify_accept_user";
    public static final String NOTIFY_ADD_USER = "notify_add_user";
    public static final String INVITATION = "invitation";
    public static final String PASSWORD_RESET = "password_reset";
    public static final String MAIL_CONFIRM = "mail_confirm";
    public static final String NOTIFY_INSERT_KNOWLEDGE = "notify_insert_knowledge";
    public static final String NOTIFY_UPDATE_KNOWLEDGE = "notify_update_knowledge";
    public static final String NOTIFY_INSERT_LIKE_MYITEM = "notify_insert_like_myitem";
    public static final String NOTIFY_INSERT_COMMENT = "notify_insert_comment";
    public static final String NOTIFY_INSERT_COMMENT_MYITEM = "notify_insert_comment_myitem";
    public static final String NOTIFY_ADD_PARTICIPATE = "notify_add_participate";
    public static final String NOTIFY_REMOVE_PARTICIPATE = "notify_remove_participate";
    public static final String NOTIFY_REGISTRATION_EVENT = "notify_registration_event";
    public static final String NOTIFY_CHANGE_EVENT_STATUS = "notify_change_event_status";
    public static final String NOTIFY_EVENT = "notify_event";
    
    
    public static String[] TEMPLATE_IDS = {
        INVITATION, MAIL_CONFIRM, NOTIFY_ACCEPT_USER, NOTIFY_ADD_USER,
        NOTIFY_INSERT_COMMENT_MYITEM, NOTIFY_INSERT_COMMENT, NOTIFY_INSERT_KNOWLEDGE,
        NOTIFY_INSERT_LIKE_MYITEM, NOTIFY_UPDATE_KNOWLEDGE, PASSWORD_RESET, TEST_MAIL,
        NOTIFY_ADD_PARTICIPATE, NOTIFY_REMOVE_PARTICIPATE,
        NOTIFY_REGISTRATION_EVENT, NOTIFY_CHANGE_EVENT_STATUS, NOTIFY_EVENT
    };
    // private static final String MAIL_ENCODE = "ISO-2022-JP";
    private static final String MAIL_ENCODE = "UTF-8";

    /** ログ */
    private static final Log LOG = LogFactory.getLog(MailLogic.class);
    
    /** メールの状態：未送信（送信待ち） */
    public static final int MAIL_STATUS_UNSENT = 0;
    /** メールの状態：送信済 */
    public static final int MAIL_STATUS_SENDED = 100;
    /** メールの状態：なんらかのエラーが発生した(カウントアップ) */
    public static final int MAIL_STATUS_ERROR_1 = 1;
    public static final int MAIL_STATUS_ERROR_2 = 2;
    public static final int MAIL_STATUS_ERROR_3 = 3;
    /** メールの状態：アドレスのフォーマットエラー */
    public static final int MAIL_STATUS_FORMAT_ERROR = 10;

    public static final String MAIL_FORMAT = "^[a-zA-Z0-9!#$%&'_`/=~\\*\\+\\-\\?\\^\\{\\|\\}]+(\\.[a-zA-Z0-9!#$%&'_`/=~\\*\\+\\-\\?\\^\\{\\|\\}]+)*"
            + "@" + "[a-zA-Z0-9][a-zA-Z0-9\\-]*(\\.[a-zA-Z0-9\\-]+)*$";
    
    // private static final DateFormat DAY_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss");
    private static final String MAIL_CONFIG_DIR = "/org/support/project/knowledge/mail/";

    
    /** date format */
    private static DateFormat getDayFormat() {
        return new SimpleDateFormat("yyyyMMddHHmmss");
    }
    
    
    public static MailLogic get() {
        return Container.getComp(MailLogic.class);
    }
    
    /**
     * メール設定の読み込み
     * 
     * @param configName
     * @param locale
     * @return
     */
    public MailLocaleTemplatesEntity load(Locale locale, String configName) {
        int status = 0; // 
        if (locale == null) {
            locale = Locale.ENGLISH;
        } else {
            if (!StringUtils.isEmpty(locale.getVariant())) {
                status = 3;
            } else if (!StringUtils.isEmpty(locale.getCountry())) {
                status = 2;
            } else {
                status = 1;
            }
        }
        MailLocaleTemplatesEntity entity = MailLocaleTemplatesDao.get().selectOnKey(locale.toString(), configName);
        while (entity == null && status >= 0) {
            if (status == 3) {
                locale = new Locale(locale.getLanguage(), locale.getCountry());
            } else if (status == 2) {
                locale = new Locale(locale.getLanguage());
            } else if (status == 1) {
                locale = Locale.ENGLISH;
            }
            status--;
            entity = MailLocaleTemplatesDao.get().selectOnKey(locale.toString(), configName);
        }
        return entity;
    }
    
    
    /**
     * メール送信の実行
     * @throws BadPaddingException 
     * @throws IllegalBlockSizeException 
     * @throws NoSuchPaddingException 
     * @throws NoSuchAlgorithmException 
     * @throws InvalidKeyException 
     */
    public void startSendMails() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        MailConfigsDao mailConfigsDao = MailConfigsDao.get();
        MailConfigsEntity mailConfigsEntity = mailConfigsDao.selectOnKey(AppConfig.get().getSystemName());
        if (mailConfigsEntity == null) {
            // メールの設定が登録されていなければ、送信処理は終了
            return;
        }
        
        MailsDao dao = MailsDao.get();
        List<MailsEntity> entities = dao.selectOnStatus(MAIL_STATUS_ERROR_3);
        Session session = getSession(mailConfigsEntity);
        int count = 0;
        for (MailsEntity mailsEntity : entities) {
            if (mailsEntity.getToAddress().matches(MAIL_FORMAT)) {
                try {
                    MailLogic.get().mailSend(session, mailConfigsEntity, mailsEntity);
                    // ステータス更新
                    // mailsEntity.setStatus(MAIL_STATUS_SENDED);
                    // MailsDao.get().save(mailsEntity);
                    // 送信処理が終われば、物理削除
                    dao.physicalDelete(mailsEntity);
                } catch (Exception e) {
                    LOG.error("mail send error", e);
                    //メール送信失敗（3回リトライする）
                    int status = MAIL_STATUS_UNSENT;
                    if (mailsEntity.getStatus() != null) {
                        status = mailsEntity.getStatus();
                    }
                    status++;
                    mailsEntity.setStatus(status);
                    MailsDao.get().save(mailsEntity);
                }
            } else {
                mailsEntity.setStatus(MAIL_STATUS_FORMAT_ERROR);
                dao.save(mailsEntity);
            }
            count++;
            
            // 一気に大量に送信しようとするとエラーになることがあるため、少し待機
            synchronized (this) {
                try {
                    wait(100);
                } catch (InterruptedException e) {
                    LOG.info(e.getMessage());
                }
            }
        }
        LOG.info("MAIL sended. count: " + count);
    }
    
    /**
     * メール送信のセッションを取得
     * @param config
     * @return
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    private Session getSession(MailConfigsEntity config) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        String host = config.getHost();
        String port = String.valueOf(config.getPort());
        
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
        return session;
    }
    
    /**
     * メールを送信
     * @param config メール送信設定
     * @param entity メール情報
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws UnsupportedEncodingException
     * @throws MessagingException
     */
    public void mailSend(MailConfigsEntity config, MailsEntity entity) throws InvalidKeyException, NoSuchAlgorithmException, 
        NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException, MessagingException {
        Session session = getSession(config);
        this.mailSend(session, config, entity);
    }
    
    
    /**
     * メールを送信
     * @param session メール送信セッション
     * @param config メール送信設定
     * @param entity メール情報
     * @throws MessagingException
     * @throws UnsupportedEncodingException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     */
    private void mailSend(Session session, MailConfigsEntity config, MailsEntity entity) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
            IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException, MessagingException {
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
     * 
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
     * URLを生成
     * 
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
     * ユーザが仮登録されたので、そのユーザに招待のメールを送信するために、 メール送信テーブルに登録する （メールの送信処理は、非同期にバッチで実行される）
     * 
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
        mailsEntity.setStatus(MailLogic.MAIL_STATUS_UNSENT);
        mailsEntity.setToAddress(entity.getUserKey());
        mailsEntity.setToName(entity.getUserName());

        MailLocaleTemplatesEntity mailConfig = load(locale, INVITATION);

        mailsEntity.setTitle(mailConfig.getTitle());
        String contents = mailConfig.getContent();
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
     * 
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
        mailsEntity.setStatus(MailLogic.MAIL_STATUS_UNSENT);
        mailsEntity.setToAddress(entity.getUserKey());
        mailsEntity.setToName(entity.getUserName());

        // TODO 仮登録時に、どのロケールでメールを出したかを保持しておかないと、言語を決められない、、、
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
     * 
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
                MailLocaleTemplatesEntity mailConfig = load(locale, NOTIFY_ADD_USER);

                String contents = mailConfig.getContent();
                contents = contents.replace("{UserId}", String.valueOf(user.getUserId()));
                contents = contents.replace("{UserName}", user.getUserName());
                contents = contents.replace("{UserMail}", user.getMailAddress());
                String title = mailConfig.getTitle();

                MailsDao mailsDao = MailsDao.get();
                MailsEntity mailsEntity = new MailsEntity();
                String mailId = idGen("Notify");
                mailsEntity.setMailId(mailId);
                mailsEntity.setStatus(MailLogic.MAIL_STATUS_UNSENT);
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
     * 
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
                MailLocaleTemplatesEntity mailConfig = load(locale, NOTIFY_ACCEPT_USER);

                String contents = mailConfig.getContent();
                String title = mailConfig.getTitle();

                MailsDao mailsDao = MailsDao.get();
                MailsEntity mailsEntity = new MailsEntity();
                String mailId = idGen("Notify");
                mailsEntity.setMailId(mailId);
                mailsEntity.setStatus(MailLogic.MAIL_STATUS_UNSENT);
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
     * 
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
        mailsEntity.setStatus(MailLogic.MAIL_STATUS_UNSENT);
        mailsEntity.setToAddress(email);
        mailsEntity.setToName(email);

        MailLocaleTemplatesEntity template = load(locale, PASSWORD_RESET);

        String title = template.getTitle();
        mailsEntity.setTitle(title);
        String contents = template.getContent();
        contents = contents.replace("{MAIL}", email);
        contents = contents.replace("{URL}", makeURL("open.PasswordInitialization/init/", resetsEntity.getId()));

        mailsEntity.setContent(contents);
        MailsDao.get().insert(mailsEntity);
    }

    /**
     * メールアドレス変更確認
     * 
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
        mailsEntity.setStatus(MailLogic.MAIL_STATUS_UNSENT);
        mailsEntity.setToAddress(mailChangesEntity.getMailAddress());
        mailsEntity.setToName(loginedUser.getLoginUser().getUserName());

        MailLocaleTemplatesEntity template = load(loginedUser.getLocale(), MAIL_CONFIRM);

        String title = template.getTitle();
        mailsEntity.setTitle(title);
        String contents = template.getContent();
        contents = contents.replace("{UserName}", loginedUser.getLoginUser().getUserName());
        contents = contents.replace("{URL}", makeURL("protect.Account/confirm_mail/", mailChangesEntity.getId()));

        mailsEntity.setContent(contents);
        MailsDao.get().insert(mailsEntity);
    }
    
    
    /**
     * メールにセットする文字列を取得する
     * Knowledgeから送るメールは、HTMLメールに対応していないので、
     * HTML形式で会った場合は、文字列を抽出する
     * 
     * @param content
     * @return
     * @throws Exception 
     */
    public String getMailContent(String content) throws Exception {
        if (StringUtils.isEmpty(content)) {
            return "";
        }
        if (content.indexOf("<html") != -1 || content.indexOf("<HTML") != -1) {
            // HTML
            if (content.indexOf("charset=iso-2022-jp") != -1) {
                content = content.replace("charset=iso-2022-jp", "UTF-8");
            }
            AppConfig appConfig = ConfigLoader.load(AppConfig.APP_CONFIG, AppConfig.class);
            File tmpDir = new File(appConfig.getTmpPath());
            if (!tmpDir.exists()) {
                tmpDir.mkdirs();
            }
            String name = RandomUtil.randamGen(16) + ".html";
            File tmp = new File(tmpDir, name);
            FileOutputStream outputStream = new FileOutputStream(tmp);
            try {
                FileUtil.write(outputStream, content);
            } finally {
                outputStream.close();
            }
            Parser parser = ParserFactory.getParser(tmp.getAbsolutePath());
            ParseResult result = parser.parse(tmp);
            LOG.debug("content text(length): " + result.getText().length());
            LOG.debug("content text        : " + StringUtils.abbreviate(result.getText(), 300));
            FileUtil.delete(tmp);
            return result.getText();
        }
        return content;
    }
    
    /**
     * メール送信のIDを生成
     * @param string
     * @return
     */
    protected String idGenu(String label) {
        StringBuilder builder = new StringBuilder();
        builder.append(label);
        builder.append("-");
        builder.append(getDayFormat().format(new Date()));
        builder.append("-");
        builder.append(UUID.randomUUID().toString());
        return builder.toString();
    }
    
    
    
    /**
     * メールのテンプレート設定を取得
     * @param locale
     * @return
     */
    public List<MailTemplatesEntity> selectAll(Locale locale) {
        List<MailTemplatesEntity> templates = MailTemplatesDao.get().selectAll();
        for (MailTemplatesEntity template : templates) {
            // いったんはDBの値を使わず、ロケールにあったリソースファイルのタイトル／説明を取得する
            MailConfig config = LocaleConfigLoader.load(MAIL_CONFIG_DIR, template.getTemplateId(), locale, MailConfig.class);
            template.setTemplateTitle(config.getTemplateTitle());
            template.setDescription(config.getDescription().trim());
            
            MailLocaleTemplatesEntity en = load(Locale.ENGLISH, template.getTemplateId());
            MailLocaleTemplatesEntity ja = load(Locale.JAPANESE, template.getTemplateId());
            template.setEn(en);
            template.setJa(ja);
        }
        return templates;
    }
    /**
     * メールのテンプレートを保存
     * @param templateId
     * @param enTitle
     * @param enContent
     * @param jaTitle
     * @param jaContent
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void save(String templateId, String enTitle, String enContent, String jaTitle, String jaContent) {
        MailLocaleTemplatesEntity en = new MailLocaleTemplatesEntity(Locale.ENGLISH.toString(), templateId);
        en.setTitle(enTitle);
        en.setContent(enContent);
        MailLocaleTemplatesDao.get().save(en);
        
        MailLocaleTemplatesEntity ja = new MailLocaleTemplatesEntity(Locale.JAPANESE.toString(), templateId);
        ja.setTitle(jaTitle);
        ja.setContent(jaContent);
        MailLocaleTemplatesDao.get().save(ja);
    }
    /**
     * メールのテンプレートをすべて初期化
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void initMailTemplate() {
        for (String templateId : TEMPLATE_IDS) {
            MailConfig enConfig = LocaleConfigLoader.load(MAIL_CONFIG_DIR, templateId, Locale.ENGLISH, MailConfig.class);
            MailConfig jaConfig = LocaleConfigLoader.load(MAIL_CONFIG_DIR, templateId, Locale.JAPANESE, MailConfig.class);
            
            MailTemplatesEntity mailTemplate = new MailTemplatesEntity(templateId);
            mailTemplate.setTemplateTitle(enConfig.getTemplateTitle());
            mailTemplate.setDescription(enConfig.getDescription());
            MailTemplatesDao.get().save(mailTemplate);
            
            MailLocaleTemplatesEntity en = new MailLocaleTemplatesEntity(Locale.ENGLISH.toString(), templateId);
            en.setTitle(enConfig.getTitle());
            en.setContent(enConfig.getContents());
            MailLocaleTemplatesDao.get().save(en);
            
            MailLocaleTemplatesEntity ja = new MailLocaleTemplatesEntity(Locale.JAPANESE.toString(), templateId);
            ja.setTitle(jaConfig.getTitle());
            ja.setContent(jaConfig.getContents());
            MailLocaleTemplatesDao.get().save(ja);
        }
    }
    /**
     * 指定のメールのテンプレートを初期化
     * @param templateId
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void initialize(String templateId) {
        MailConfig enConfig = LocaleConfigLoader.load(MAIL_CONFIG_DIR, templateId, Locale.ENGLISH, MailConfig.class);
        MailConfig jaConfig = LocaleConfigLoader.load(MAIL_CONFIG_DIR, templateId, Locale.JAPANESE, MailConfig.class);
        
        MailLocaleTemplatesEntity en = new MailLocaleTemplatesEntity(Locale.ENGLISH.toString(), templateId);
        en.setTitle(enConfig.getTitle());
        en.setContent(enConfig.getContents());
        MailLocaleTemplatesDao.get().save(en);
        
        MailLocaleTemplatesEntity ja = new MailLocaleTemplatesEntity(Locale.JAPANESE.toString(), templateId);
        ja.setTitle(jaConfig.getTitle());
        ja.setContent(jaConfig.getContents());
        MailLocaleTemplatesDao.get().save(ja);
    }
    
    /**
     * 参加登録があったことを、開催者へ通知
     * @param knowledgeId
     * @param userId
     * @param status
     */
    public void notifyAddParticipateForSponsor(Long knowledgeId, Integer userId, Integer status) {
        MailConfigsDao mailConfigsDao = MailConfigsDao.get();
        MailConfigsEntity mailConfigsEntity = mailConfigsDao.selectOnKey(AppConfig.get().getSystemName());
        if (mailConfigsEntity == null) {
            // メールの設定が登録されていなければ、送信処理は終了
            LOG.info("mail config is not exists.");
            return;
        }
        KnowledgesEntity knowledge = KnowledgesDao.get().selectOnKey(knowledgeId);
        if (knowledge == null) {
            LOG.info("knowledge [" + knowledgeId + "] is not exists.");
            return;
        }
        // 開催者
        UsersEntity sponsor = UsersDao.get().selectOnKey(knowledge.getInsertUser());
        // 参加者
        UsersEntity participant = UsersDao.get().selectOnKey(userId);
        if (sponsor == null || participant == null) {
            LOG.warn("sponsor or participant is not exist.");
            return;
        }
        if (!StringUtils.isEmailAddress(sponsor.getMailAddress())) {
            // 送信先のメールアドレスが不正なので、送信処理は終了
            LOG.warn("mail targget [" + sponsor.getMailAddress() + "] is wrong.");
            return;
        }

        MailsEntity mailsEntity = new MailsEntity();
        String mailId = idGenu("Notify");
        mailsEntity.setMailId(mailId);
        mailsEntity.setStatus(MailLogic.MAIL_STATUS_UNSENT);
        mailsEntity.setToAddress(sponsor.getMailAddress());
        mailsEntity.setToName(sponsor.getUserName());
        
        MailLocaleTemplatesEntity template = load(sponsor.getLocale(), NOTIFY_ADD_PARTICIPATE);
        String title = template.getTitle();
        title = title.replace("{KnowledgeId}", knowledge.getKnowledgeId().toString());
        title = title.replace("{KnowledgeTitle}", StringUtils.abbreviate(knowledge.getTitle(), 80));
        mailsEntity.setTitle(title);
        String contents = template.getContent();
        contents = contents.replace("{KnowledgeId}", knowledge.getKnowledgeId().toString());
        contents = contents.replace("{KnowledgeTitle}", knowledge.getTitle());
        StringBuilder builder = new StringBuilder();
        Resources resources = Resources.getInstance(sponsor.getLocale());
        builder.append(participant.getUserName());
        if (status == EventsLogic.STATUS_PARTICIPATION) {
            builder.append("[").append(resources.getResource("knowledge.view.label.status.participation")).append("]");
        } else {
            builder.append("[").append(resources.getResource("knowledge.view.label.status.wait.cansel")).append("]");
        }
        contents = contents.replace("{Participant}", builder.toString());
        contents = contents.replace("{URL}", NotifyLogic.get().makeURL(knowledge.getKnowledgeId()));

        mailsEntity.setContent(contents);
        MailsDao.get().insert(mailsEntity);
    }
    /**
     * 参加登録したことを、参加者へ通知
     * @param knowledgeId
     * @param userId
     * @param status
     */
    public void notifyAddParticipateForParticipant(Long knowledgeId, Integer userId, Integer status) {
        MailConfigsDao mailConfigsDao = MailConfigsDao.get();
        MailConfigsEntity mailConfigsEntity = mailConfigsDao.selectOnKey(AppConfig.get().getSystemName());
        if (mailConfigsEntity == null) {
            // メールの設定が登録されていなければ、送信処理は終了
            LOG.info("mail config is not exists.");
            return;
        }
        KnowledgesEntity knowledge = KnowledgesDao.get().selectOnKey(knowledgeId);
        if (knowledge == null) {
            LOG.info("knowledge [" + knowledgeId + "] is not exists.");
            return;
        }
        // 参加者
        UsersEntity participant = UsersDao.get().selectOnKey(userId);
        if (participant == null) {
            LOG.warn("sponsor or participant is not exist.");
            return;
        }
        if (!StringUtils.isEmailAddress(participant.getMailAddress())) {
            // 送信先のメールアドレスが不正なので、送信処理は終了
            LOG.warn("mail targget [" + participant.getMailAddress() + "] is wrong.");
            return;
        }

        MailsEntity mailsEntity = new MailsEntity();
        String mailId = idGenu("Notify");
        mailsEntity.setMailId(mailId);
        mailsEntity.setStatus(MailLogic.MAIL_STATUS_UNSENT);
        mailsEntity.setToAddress(participant.getMailAddress());
        mailsEntity.setToName(participant.getUserName());
        
        MailLocaleTemplatesEntity template = load(participant.getLocale(), NOTIFY_REGISTRATION_EVENT);
        String title = template.getTitle();
        title = title.replace("{KnowledgeId}", knowledge.getKnowledgeId().toString());
        title = title.replace("{KnowledgeTitle}", StringUtils.abbreviate(knowledge.getTitle(), 80));
        mailsEntity.setTitle(title);
        String contents = template.getContent();
        contents = contents.replace("{KnowledgeId}", knowledge.getKnowledgeId().toString());
        contents = contents.replace("{KnowledgeTitle}", knowledge.getTitle());
        StringBuilder builder = new StringBuilder();
        Resources resources = Resources.getInstance(participant.getLocale());
        if (status == EventsLogic.STATUS_PARTICIPATION) {
            builder.append(resources.getResource("knowledge.view.label.status.participation"));
        } else {
            builder.append(resources.getResource("knowledge.view.label.status.wait.cansel"));
        }
        contents = contents.replace("{Status}", builder.toString());
        contents = contents.replace("{URL}", NotifyLogic.get().makeURL(knowledge.getKnowledgeId()));

        mailsEntity.setContent(contents);
        MailsDao.get().insert(mailsEntity);
    }
    /**
     * 参加キャンセルがあったことを、開催者へ通知
     * @param knowledgeId
     * @param userId
     */
    public void notifyRemoveParticipateForSponsor(Long knowledgeId, Integer userId) {
        MailConfigsDao mailConfigsDao = MailConfigsDao.get();
        MailConfigsEntity mailConfigsEntity = mailConfigsDao.selectOnKey(AppConfig.get().getSystemName());
        if (mailConfigsEntity == null) {
            // メールの設定が登録されていなければ、送信処理は終了
            LOG.info("mail config is not exists.");
            return;
        }
        KnowledgesEntity knowledge = KnowledgesDao.get().selectOnKey(knowledgeId);
        if (knowledge == null) {
            LOG.info("knowledge [" + knowledgeId + "] is not exists.");
            return;
        }
        // 開催者
        UsersEntity sponsor = UsersDao.get().selectOnKey(knowledge.getInsertUser());
        // 参加者
        UsersEntity participant = UsersDao.get().selectOnKey(userId);
        if (sponsor == null || participant == null) {
            LOG.warn("sponsor or participant is not exist.");
            return;
        }
        if (!StringUtils.isEmailAddress(sponsor.getMailAddress())) {
            // 送信先のメールアドレスが不正なので、送信処理は終了
            LOG.warn("mail targget [" + sponsor.getMailAddress() + "] is wrong.");
            return;
        }

        MailsEntity mailsEntity = new MailsEntity();
        String mailId = idGenu("Notify");
        mailsEntity.setMailId(mailId);
        mailsEntity.setStatus(MailLogic.MAIL_STATUS_UNSENT);
        mailsEntity.setToAddress(sponsor.getMailAddress());
        mailsEntity.setToName(sponsor.getUserName());
        
        MailLocaleTemplatesEntity template = load(sponsor.getLocale(), NOTIFY_REMOVE_PARTICIPATE);
        String title = template.getTitle();
        title = title.replace("{KnowledgeId}", knowledge.getKnowledgeId().toString());
        title = title.replace("{KnowledgeTitle}", StringUtils.abbreviate(knowledge.getTitle(), 80));
        mailsEntity.setTitle(title);
        String contents = template.getContent();
        contents = contents.replace("{KnowledgeId}", knowledge.getKnowledgeId().toString());
        contents = contents.replace("{KnowledgeTitle}", knowledge.getTitle());
        contents = contents.replace("{Participant}", participant.getUserName());
        contents = contents.replace("{URL}", NotifyLogic.get().makeURL(knowledge.getKnowledgeId()));

        mailsEntity.setContent(contents);
        MailsDao.get().insert(mailsEntity);
    }
    /**
     * キャンセル待ちのユーザに、キャンセルがあったため参加になったことを通知
     * @param knowledgeId
     * @param userId
     */
    public void notifyChangeParticipateStatusForParticipant(Long knowledgeId, Integer userId) {
        MailConfigsDao mailConfigsDao = MailConfigsDao.get();
        MailConfigsEntity mailConfigsEntity = mailConfigsDao.selectOnKey(AppConfig.get().getSystemName());
        if (mailConfigsEntity == null) {
            // メールの設定が登録されていなければ、送信処理は終了
            LOG.info("mail config is not exists.");
            return;
        }
        KnowledgesEntity knowledge = KnowledgesDao.get().selectOnKey(knowledgeId);
        if (knowledge == null) {
            LOG.info("knowledge [" + knowledgeId + "] is not exists.");
            return;
        }
        // 参加者
        UsersEntity participant = UsersDao.get().selectOnKey(userId);
        if (participant == null) {
            LOG.warn("sponsor or participant is not exist.");
            return;
        }
        if (!StringUtils.isEmailAddress(participant.getMailAddress())) {
            // 送信先のメールアドレスが不正なので、送信処理は終了
            LOG.warn("mail targget [" + participant.getMailAddress() + "] is wrong.");
            return;
        }

        MailsEntity mailsEntity = new MailsEntity();
        String mailId = idGenu("Notify");
        mailsEntity.setMailId(mailId);
        mailsEntity.setStatus(MailLogic.MAIL_STATUS_UNSENT);
        mailsEntity.setToAddress(participant.getMailAddress());
        mailsEntity.setToName(participant.getUserName());
        
        MailLocaleTemplatesEntity template = load(participant.getLocale(), NOTIFY_CHANGE_EVENT_STATUS);
        String title = template.getTitle();
        title = title.replace("{KnowledgeId}", knowledge.getKnowledgeId().toString());
        title = title.replace("{KnowledgeTitle}", StringUtils.abbreviate(knowledge.getTitle(), 80));
        mailsEntity.setTitle(title);
        String contents = template.getContent();
        contents = contents.replace("{KnowledgeId}", knowledge.getKnowledgeId().toString());
        contents = contents.replace("{KnowledgeTitle}", knowledge.getTitle());
        StringBuilder builder = new StringBuilder();
        Resources resources = Resources.getInstance(participant.getLocale());
        builder.append(resources.getResource("knowledge.view.label.status.participation"));
        contents = contents.replace("{Status}", builder.toString());
        contents = contents.replace("{URL}", NotifyLogic.get().makeURL(knowledge.getKnowledgeId()));

        mailsEntity.setContent(contents);
        MailsDao.get().insert(mailsEntity);
    }

}
