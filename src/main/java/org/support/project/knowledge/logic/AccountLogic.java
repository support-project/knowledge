package org.support.project.knowledge.logic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.fileupload.FileItem;
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
import org.support.project.knowledge.dao.AccountImagesDao;
import org.support.project.knowledge.entity.AccountImagesEntity;
import org.support.project.knowledge.vo.UploadFile;
import org.support.project.web.bean.LoginedUser;
import org.support.project.web.dao.ConfirmMailChangesDao;
import org.support.project.web.dao.UsersDao;
import org.support.project.web.entity.ConfirmMailChangesEntity;
import org.support.project.web.entity.UsersEntity;

@DI(instance = Instance.Singleton)
public class AccountLogic {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(AccountLogic.class);

    public static AccountLogic get() {
        return Container.getComp(AccountLogic.class);
    }

    /**
     * アイコンの保存
     * 
     * @param fileItem
     * @param loginedUser
     * @param context
     * @return
     * @throws IOException
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public UploadFile saveIconImage(FileItem fileItem, LoginedUser loginedUser, String context) throws IOException {
        LOG.trace("saveFile()");
        AccountImagesDao dao = AccountImagesDao.get();
        AccountImagesEntity entity = dao.selectOnUserId(loginedUser.getUserId());
        if (entity == null) {
            entity = new AccountImagesEntity();
        }

        entity.setFileName(fileItem.getName());
        entity.setFileSize(new Double(fileItem.getSize()));
        entity.setFileBinary(fileItem.getInputStream());
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
        // 処理が完了したら、テンポラリのファイルを削除
        fileItem.delete();
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
        file.setThumbnailUrl(context + "/open.account/icon/" + entity.getUserId() + "?t=" + new Date().getTime());
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
        builder.append(DateUtils.getTransferDateFormat().format(new Date()));
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

}
