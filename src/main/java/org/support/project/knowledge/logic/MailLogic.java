package org.support.project.knowledge.logic;

import java.io.File;
import java.io.FileOutputStream;
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

import org.support.project.aop.Aspect;
import org.support.project.common.config.ConfigLoader;
import org.support.project.common.config.LocaleConfigLoader;
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
import org.support.project.knowledge.dao.MailLocaleTemplatesDao;
import org.support.project.knowledge.dao.MailTemplatesDao;
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

    public static MailLogic get() {
        return Container.getComp(MailLogic.class);
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
     * メール設定の読み込み
     * 
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
                MailConfig mailConfig = load("notify_accept_user", locale);

                String contents = mailConfig.getContents();
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

        MailConfig config = LocaleConfigLoader.load(MAIL_CONFIG_DIR, "password_reset", locale, MailConfig.class);

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

        MailConfig config = LocaleConfigLoader.load(MAIL_CONFIG_DIR, "mail_confirm", loginedUser.getLocale(), MailConfig.class);

        String title = config.getTitle();
        mailsEntity.setTitle(title);
        String contents = config.getContents();
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

    
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void initMailTemplate() {
        String[] templateIds = {
                "invitation", "mail_confirm", "notify_accept_user", "notify_add_user",
                "notify_insert_comment_myitem", "notify_insert_comment", "notify_insert_knowledge",
                "notify_insert_like_myitem", "notify_update_knowledge", "password_reset", "test_mail"
        };
        for (String templateId : templateIds) {
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

    public List<MailTemplatesEntity> selectAll(Locale locale) {
        List<MailTemplatesEntity> templates = MailTemplatesDao.get().selectAll();
        for (MailTemplatesEntity template : templates) {
            // いったんはDBの値を使わず、ロケールにあったリソースファイルのタイトル／説明を取得する
            MailConfig config = LocaleConfigLoader.load(MAIL_CONFIG_DIR, template.getTemplateId(), locale, MailConfig.class);
            template.setTemplateTitle(config.getTemplateTitle());
            template.setDescription(config.getDescription().trim());
            
            MailLocaleTemplatesEntity en = MailLocaleTemplatesDao.get().selectOnKey(Locale.ENGLISH.toString(), template.getTemplateId());
            MailLocaleTemplatesEntity ja = MailLocaleTemplatesDao.get().selectOnKey(Locale.JAPANESE.toString(), template.getTemplateId());
            template.setEn(en);
            template.setJa(ja);
        }
        return templates;
    }

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

}
