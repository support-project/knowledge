package org.support.project.knowledge.control.admin;

import java.io.UnsupportedEncodingException;
import java.lang.invoke.MethodHandles;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.mail.MessagingException;

import org.support.project.common.bean.ValidateError;
import org.support.project.common.config.INT_FLAG;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.PasswordUtil;
import org.support.project.common.util.StringUtils;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.config.AppConfig;
import org.support.project.knowledge.control.Control;
import org.support.project.knowledge.entity.MailLocaleTemplatesEntity;
import org.support.project.knowledge.logic.MailLogic;
import org.support.project.web.annotation.Auth;
import org.support.project.web.boundary.Boundary;
import org.support.project.web.control.service.Get;
import org.support.project.web.control.service.Post;
import org.support.project.web.dao.MailConfigsDao;
import org.support.project.web.entity.MailConfigsEntity;
import org.support.project.web.entity.MailsEntity;

@DI(instance = Instance.Prototype)
public class MailControl extends Control {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(MethodHandles.lookup());

    private static final String NO_CHANGE_PASSWORD = "NO_CHANGE_PASSWORD-fXLSJ_V-ZJ2E-GBAghu_usb-gtaG"; // パスワードを更新しなかったことを表すパスワード

    /**
     * メールの設定画面を表示
     * 
     * @return
     */
    @Get(publishToken = "admin")
    @Auth(roles = "admin")
    public Boundary config() {
        MailConfigsDao dao = MailConfigsDao.get();
        MailConfigsEntity entity = dao.selectOnKey(AppConfig.get().getSystemName());
        if (entity == null) {
            entity = new MailConfigsEntity();
        } else {
            entity.setSmtpPassword(NO_CHANGE_PASSWORD); // パスワードは送らない
        }
        entity.setSystemName(AppConfig.get().getSystemName());
        setAttributeOnProperty(entity);

        return forward("config.jsp");
    }

    /**
     * メールの設定を保存
     * 
     * @return
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     */
    @Post(subscribeToken = "admin")
    @Auth(roles = "admin")
    public Boundary save()
            throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        List<ValidateError> errors = new ArrayList<>();
        errors.addAll(MailConfigsEntity.get().validate(getParams()));

        String type = getParam("authType");
        // 認証がONの場合のチェック
        if (type.equals(String.valueOf(INT_FLAG.ON.getValue()))) {
            if (StringUtils.isEmpty(getParam("smtpId"))) {
                ValidateError error = new ValidateError("knowledge.config.mail.smtpid.require");
                errors.add(error);
            }
            if (StringUtils.isEmpty(getParam("smtpPassword"))) {
                ValidateError error = new ValidateError("knowledge.config.mail.smtppass.require");
                errors.add(error);
            }
        }
        if (!errors.isEmpty()) {
            setResult(null, errors);
            return forward("config.jsp");
        }

        MailConfigsEntity entity = super.getParams(MailConfigsEntity.class);
        MailConfigsDao dao = MailConfigsDao.get();

        if (entity.getSmtpPassword().equals(NO_CHANGE_PASSWORD)) {
            MailConfigsEntity db = dao.selectOnKey(AppConfig.get().getSystemName());
            entity.setSmtpPassword(db.getSmtpPassword());
            entity.setSalt(db.getSalt());
        } else {
            // パスワードは暗号化する
            String salt = PasswordUtil.getSalt();
            entity.setSmtpPassword(PasswordUtil.encrypt(entity.getSmtpPassword(), salt));
            entity.setSalt(salt);
        }

        entity = dao.save(entity);
        setAttributeOnProperty(entity);

        // TODO テストでメール送信（更新したユーザのメールアドレス宛に、メールを送る）

        String successMsg = "message.success.save";
        setResult(successMsg, errors);

        return forward("config.jsp");
    }

    /**
     * メールの設定を削除
     * 
     * @return
     */
    @Post(subscribeToken = "admin")
    @Auth(roles = "admin")
    public Boundary delete() {
        MailConfigsDao dao = MailConfigsDao.get();
        dao.physicalDelete(AppConfig.get().getSystemName()); // 物理削除で消してしまう

        MailConfigsEntity entity = new MailConfigsEntity();
        entity.setSystemName(AppConfig.get().getSystemName());
        setAttributeOnProperty(entity);

        addMsgInfo("message.success.delete.target", getResource("knowledge.config.mail"));

        return forward("config.jsp");
    }

    /**
     * テストメール送信
     * 
     * @return
     */
    @Post(subscribeToken = "admin")
    @Auth(roles = "admin")
    public Boundary test_send() {
        MailConfigsDao mailConfigsDao = MailConfigsDao.get();
        MailConfigsEntity mailConfigsEntity = mailConfigsDao.selectOnKey(AppConfig.get().getSystemName());
        if (mailConfigsEntity == null) {
            // メールの設定が登録されていなければ、送信処理は終了
            addMsgInfo(getResource("knowledge.mail.config.empty"));
            return config();
        }

        String toAddress = getParam("to_address");
        if (!StringUtils.isEmailAddress(toAddress)) {
            addMsgWarn(getResource("knowledge.mail.error.to.address"));
            return config();
        }
        MailLocaleTemplatesEntity template = MailLogic.get().load(getLoginedUser().getLocale(), MailLogic.TEST_MAIL);
        MailsEntity entity = new MailsEntity();
        entity.setFromAddress(mailConfigsEntity.getFromAddress());
        entity.setFromName(mailConfigsEntity.getFromName());
        entity.setTitle(getResource(template.getTitle()));
        entity.setToAddress(toAddress);
        entity.setToName(toAddress);
        entity.setContent(template.getContent());

        try {
            MailLogic.get().mailSend(mailConfigsEntity, entity);
            addMsgInfo(getResource("knowledge.mail.test.success"));
        } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException
                | UnsupportedEncodingException | MessagingException e) {
            LOG.error("mail send error", e);
            addMsgWarn(getResource("knowledge.mail.test.fail"));
            setAttribute("mail_send_error_class", e.getClass());
            setAttribute("mail_send_error", e.getMessage());
        }
        return config();
    }

}
