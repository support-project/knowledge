package org.support.project.knowledge.logic;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.support.project.aop.Aspect;
import org.support.project.common.bean.ValidateError;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.DateUtils;
import org.support.project.common.util.RandomUtil;
import org.support.project.common.util.StringUtils;
import org.support.project.common.validate.Validator;
import org.support.project.common.validate.ValidatorFactory;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.config.AppConfig;
import org.support.project.knowledge.config.UserConfig;
import org.support.project.knowledge.dao.AccountImagesDao;
import org.support.project.knowledge.entity.AccountImagesEntity;
import org.support.project.knowledge.vo.UploadFile;
import org.support.project.web.bean.LoginedUser;
import org.support.project.web.dao.ConfirmMailChangesDao;
import org.support.project.web.dao.GroupsDao;
import org.support.project.web.dao.RolesDao;
import org.support.project.web.dao.UserConfigsDao;
import org.support.project.web.dao.UsersDao;
import org.support.project.web.entity.ConfirmMailChangesEntity;
import org.support.project.web.entity.GroupsEntity;
import org.support.project.web.entity.RolesEntity;
import org.support.project.web.entity.UserConfigsEntity;
import org.support.project.web.entity.UsersEntity;

@DI(instance = Instance.Singleton)
public class AccountLogic {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(AccountLogic.class);
    public static AccountLogic get() {
        return Container.getComp(AccountLogic.class);
    }
    
    private String cookieKeyTimezone = "";
    private String cookieKeyTimezoneOffset = "";
    private String cookieKeyThema = "";
    private String cookieKeyHighlight = "";
    /**
     * @return the cookie_key_timezone
     */
    public String getCookieKeyTimezone() {
        if (StringUtils.isEmpty(cookieKeyTimezone)) {
            cookieKeyTimezone = AppConfig.get().getSystemName() + "_" + UserConfig.TIMEZONE;
        }
        return cookieKeyTimezone;
    }
    /**
     * @return the cookie_key_timezone_offset
     */
    public String getCookieKeyTimezoneOffset() {
        if (StringUtils.isEmpty(cookieKeyTimezoneOffset)) {
            cookieKeyTimezoneOffset = AppConfig.get().getSystemName() + "_" + UserConfig.TIME_ZONE_OFFSET;
        }
        return cookieKeyTimezoneOffset;
    }
    /**
     * @return the cookie_key_thema
     */
    public String getCookieKeyThema() {
        if (StringUtils.isEmpty(cookieKeyThema)) {
            cookieKeyThema = AppConfig.get().getSystemName() + "_" + UserConfig.THEMA;
        }
        return cookieKeyThema;
    }
    /**
     * @return the cookie_key_highlight
     */
    public String getCookieKeyHighlight() {
        if (StringUtils.isEmpty(cookieKeyHighlight)) {
            cookieKeyHighlight = AppConfig.get().getSystemName() + "_" + UserConfig.HIGHLIGHT;
        }
        return cookieKeyHighlight;
    }

    /**
     * アイコンの保存
     * 
     * @param img
     * @param loginedUser
     * @param context
     * @return
     * @throws IOException
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public UploadFile saveIconImage(byte[] img, LoginedUser loginedUser, String context) throws IOException {
        LOG.trace("saveFile()");
        AccountImagesDao dao = AccountImagesDao.get();
        AccountImagesEntity entity = dao.selectOnUserId(loginedUser.getUserId());
        if (entity == null) {
            entity = new AccountImagesEntity();
        }

        entity.setFileName("account-" + loginedUser.getUserId() + ".png");
        entity.setFileSize(new Double(img.length));
        entity.setFileBinary(new ByteArrayInputStream(img));
        entity.setUserId(loginedUser.getUserId());

        String extension = StringUtils.getExtension(entity.getFileName());
        entity.setExtension(extension);

        String contentType = "application/octet-stream";
        if (StringUtils.isNotEmpty(extension)) {
            if (extension.toLowerCase().indexOf("png") != -1) {
                contentType = "image/png";
            } else if (extension.toLowerCase().indexOf("jpg") != -1) {
                contentType = "image/jpeg";
            } else if (extension.toLowerCase().indexOf("jpeg") != -1) {
                contentType = "image/jpeg";
            } else if (extension.toLowerCase().indexOf("gif") != -1) {
                contentType = "image/gif";
            }
        }
        entity.setContentType(contentType);
        entity = dao.save(entity);
        UploadFile file = convUploadFile(context, entity);
        return file;
    }

    /**
     * KnowledgeFilesEntity の情報から、画面に戻す UploadFile の情報を生成する
     * 
     * @param context
     * @param entity
     * @return
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    private UploadFile convUploadFile(String context, AccountImagesEntity entity) {
        UploadFile file = new UploadFile();
        file.setFileNo(new Long(entity.getUserId()));
        file.setUrl(context + "/open.account/icon/" + entity.getUserId());
        file.setThumbnailUrl(context + "/open.account/icon/" + entity.getUserId() + "?t=" + DateUtils.now().getTime());
        file.setName(entity.getFileName());
        file.setType("-");
        file.setSize(entity.getFileSize());
        file.setDeleteUrl(context + "/protect.account/delete");
        file.setDeleteType("DELETE");
        return file;
    }

    /**
     * Emailの変更のリクエストを受け付ける
     * 
     * @param changeEmail
     * @param loginedUser
     * @return
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<ValidateError> saveChangeEmailRequest(String changeEmail, LoginedUser loginedUser) {
        List<ValidateError> errors = new ArrayList<ValidateError>();
        Validator validator = ValidatorFactory.getInstance(Validator.REQUIRED);
        ValidateError error = validator.validate(changeEmail, "E-Mail");
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAIL);
        error = validator.validate(changeEmail, "E-Mail");
        if (error != null) {
            errors.add(error);
        }
        UsersDao usersDao = UsersDao.get();
        UsersEntity usersEntity = usersDao.selectOnUserKey(changeEmail);
        if (usersEntity != null) {
            error = new ValidateError("errors.exist", "E-Mail");
            errors.add(error);
        }
        if (!errors.isEmpty()) {
            return errors;
        }

        // 変更のリクエストデータを保存
        ConfirmMailChangesDao mailChangesDao = ConfirmMailChangesDao.get();
        ConfirmMailChangesEntity mailChangesEntity = new ConfirmMailChangesEntity();
        mailChangesEntity.setId(idGen(loginedUser.getUserId().toString()));
        mailChangesEntity.setMailAddress(changeEmail);
        mailChangesEntity.setUserId(loginedUser.getUserId());
        mailChangesDao.insert(mailChangesEntity);

        // メール送信
        MailLogic.get().sendChangeEmailRequest(mailChangesEntity, loginedUser);
        return errors;
    }

    /**
     * メール送信のIDを生成
     * 
     * @param string
     * @return
     */
    private String idGen(String label) {
        StringBuilder builder = new StringBuilder();
        builder.append(label);
        builder.append("-");
        builder.append(DateUtils.getTransferDateFormat().format(DateUtils.now()));
        builder.append("-");
        builder.append(UUID.randomUUID().toString());
        builder.append("-");
        builder.append(RandomUtil.randamGen(32));
        return builder.toString();
    }

    /**
     * メールアドレス変更処理を完了
     * 
     * @param id
     * @param loginedUser
     * @return
     */
    public List<ValidateError> completeChangeEmailRequest(String id, LoginedUser loginedUser) {
        List<ValidateError> errors = new ArrayList<ValidateError>();
        ConfirmMailChangesDao mailChangesDao = ConfirmMailChangesDao.get();
        ConfirmMailChangesEntity mailChangesEntity = mailChangesDao.selectOnKey(id);

        if (mailChangesEntity == null) {
            ValidateError error = new ValidateError("errors.invalid", "path");
            errors.add(error);
            return errors;
        }
        if (mailChangesEntity.getUserId().intValue() != loginedUser.getUserId().intValue()) {
            ValidateError error = new ValidateError("errors.invalid", "path");
            errors.add(error);
        }

        UsersDao usersDao = UsersDao.get();
        UsersEntity usersEntity = usersDao.selectOnUserKey(mailChangesEntity.getMailAddress());
        if (usersEntity != null) {
            ValidateError error = new ValidateError("errors.exist", "E-Mail");
            errors.add(error);
        }
        if (!errors.isEmpty()) {
            return errors;
        }

        usersEntity = usersDao.selectOnKey(loginedUser.getUserId());
        if (usersEntity == null) {
            ValidateError error = new ValidateError("errors.invalid", "path");
            errors.add(error);
        } else {
            usersEntity.setUserKey(mailChangesEntity.getMailAddress());
            usersEntity.setMailAddress(mailChangesEntity.getMailAddress());
            usersDao.update(usersEntity);
        }
        // メール変更を無効化
        mailChangesDao.delete(mailChangesEntity);
        return errors;
    }
    
    /**
     * ログインユーザのオブジェクトを生成する
     * @param userKey
     * @return
     */
    public LoginedUser createLoginUser(String userKey) {
        UsersDao usersDao = UsersDao.get();
        UsersEntity usersEntity = usersDao.selectOnUserKey(userKey);
        RolesDao rolesDao = RolesDao.get();
        List<RolesEntity> rolesEntities = rolesDao.selectOnUserKey(userKey);

        LoginedUser loginedUser = new LoginedUser();
        loginedUser.setLoginUser(usersEntity);
        loginedUser.setRoles(rolesEntities);
        loginedUser.setLocale(usersEntity.getLocale());

        // グループ
        GroupsDao groupsDao = GroupsDao.get();
        List<GroupsEntity> groups = groupsDao.selectMyGroup(loginedUser, 0, Integer.MAX_VALUE);
        loginedUser.setGroups(groups);
        
        return loginedUser;
    }
    
    /**
     * ユーザのポイント取得
     * @param user ユーザ
     * @return
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public int getPoint(int user) {
        UserConfigsEntity config = UserConfigsDao.get().selectOnKey(UserConfig.POINT, AppConfig.get().getSystemName(), user);
        if (config == null) {
            config = new UserConfigsEntity(UserConfig.POINT, AppConfig.get().getSystemName(), user);
            config.setConfigValue("0");
        }
        if (!StringUtils.isInteger(config.getConfigValue())) {
            config.setConfigValue("0");
        }
        int now = Integer.parseInt(config.getConfigValue());
        return now;
    }
    
    /**
     * ユーザの設定を取得
     * @param user ユーザ
     * @param configKey 設定キー
     * @return 設定値
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public String getConfig(int user, String configKey) {
        UserConfigsEntity config = UserConfigsDao.get().selectOnKey(configKey, AppConfig.get().getSystemName(), user);
        if (config == null) {
            return "";
        }
        return config.getConfigValue();
    }
    /**
     * ユーザの設定を取得
     * @param user ユーザ
     * @param configKey 設定キー
     * @return 設定値
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void setConfig(int user, String configKey, String configValue) {
        UserConfigsEntity config = UserConfigsDao.get().selectOnKey(configKey, AppConfig.get().getSystemName(), user);
        if (config == null) {
            config = new UserConfigsEntity(configKey, AppConfig.get().getSystemName(), user);
        }
        config.setConfigValue(configValue);
        UserConfigsDao.get().save(config);
    }






}
