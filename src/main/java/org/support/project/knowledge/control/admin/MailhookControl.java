package org.support.project.knowledge.control.admin;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.support.project.common.bean.ValidateError;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.PasswordUtil;
import org.support.project.common.util.StringUtils;
import org.support.project.common.validate.Validator;
import org.support.project.common.validate.ValidatorFactory;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.control.Control;
import org.support.project.knowledge.dao.MailHookConditionsDao;
import org.support.project.knowledge.dao.MailHooksDao;
import org.support.project.knowledge.dao.MailPropertiesDao;
import org.support.project.knowledge.entity.MailHookConditionsEntity;
import org.support.project.knowledge.entity.MailHooksEntity;
import org.support.project.knowledge.entity.MailPropertiesEntity;
import org.support.project.knowledge.logic.MailhookLogic;
import org.support.project.knowledge.logic.TargetLogic;
import org.support.project.web.annotation.Auth;
import org.support.project.web.bean.LabelValue;
import org.support.project.web.boundary.Boundary;
import org.support.project.web.common.HttpStatus;
import org.support.project.web.config.MessageStatus;
import org.support.project.web.control.service.Get;
import org.support.project.web.control.service.Post;
import org.support.project.web.exception.InvalidParamException;

import net.arnx.jsonic.JSONException;

@DI(instance = Instance.Prototype)
public class MailhookControl extends Control {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(MailhookControl.class);
    
    private static final String NO_CHANGE_PASSWORD = "NO_CHANGE_PASSWORD-GUHO-UIG-ZJ2E-HishaihTHY-YIOHA"; // パスワードを更新しなかったことを表すパスワード
    
    /**
     * MailHookの設定画面を表示
     * 
     * @return
     */
    @Get(publishToken = "admin")
    @Auth(roles = "admin")
    public Boundary index() {
        MailHooksEntity entity = MailHooksDao.get().selectOnKey(MailhookLogic.MAIL_HOOK_ID);
        if (entity == null) {
            entity = new MailHooksEntity();
            
            List<MailPropertiesEntity> properties = new ArrayList<>();
            MailPropertiesEntity property = new MailPropertiesEntity(MailhookLogic.MAIL_HOOK_ID, "mail.store.protocol");
            property.setPropertyValue("imaps");
            properties.add(property);
            property = new MailPropertiesEntity(MailhookLogic.MAIL_HOOK_ID, "mail.imaps.ssl.trust");
            property.setPropertyValue("*");
            properties.add(property);
            setAttribute("properties", properties);
        } else {
            if (StringUtils.isNotEmpty(entity.getMailPass())) {
                entity.setMailPass(NO_CHANGE_PASSWORD);
            }
            List<MailHookConditionsEntity> mailHooks = MailHookConditionsDao.get().selectOnHookId(MailhookLogic.MAIL_HOOK_ID);
            setAttribute("mailHooks", mailHooks);
            
            List<MailPropertiesEntity> properties = MailPropertiesDao.get().selectOnHookId(MailhookLogic.MAIL_HOOK_ID);
            setAttribute("properties", properties);
        }
        setAttributeOnProperty(entity);
        return forward("config.jsp");
    }

    /**
     * MailHook設定を保存
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
    public Boundary save() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
        IllegalBlockSizeException, BadPaddingException {
        // プロパティ取得
        String[] keys = getParam("propertyKey", String[].class);
        String[] vals = getParam("propertyValue", String[].class);
        List<MailPropertiesEntity> properties = new ArrayList<>();
        int count = 0;
        if (keys != null) {
            for (String key : keys) {
                MailPropertiesEntity property = new MailPropertiesEntity(MailhookLogic.MAIL_HOOK_ID, key);
                if (vals.length > count) {
                    property.setPropertyValue(vals[count]);
                } else {
                    property.setPropertyValue("");
                }
                properties.add(property);
                count++;
            }
        }
        setAttribute("properties", properties);
        
        List<ValidateError> errors = new ArrayList<>();
        errors.addAll(MailHooksEntity.get().validate(getParams()));
        if (!errors.isEmpty()) {
            setResult(null, errors);
            return forward("config.jsp");
        }
        MailHooksEntity entity = super.getParams(MailHooksEntity.class);
        entity.setHookId(MailhookLogic.MAIL_HOOK_ID);
        if (NO_CHANGE_PASSWORD.equals(entity.getMailPass())) {
            MailHooksEntity db = MailHooksDao.get().selectOnKey(MailhookLogic.MAIL_HOOK_ID);
            if (db != null && StringUtils.isNotEmpty(db.getMailPass())) {
                entity.setMailPass(db.getMailPass());
                entity.setMailPassSalt(db.getMailPassSalt());
            }
        } else {
            // パスワードは暗号化する
            String salt = PasswordUtil.getSalt();
            entity.setMailPass(PasswordUtil.encrypt(entity.getMailPass(), salt));
            entity.setMailPassSalt(salt);
        }
        entity.setHookId(MailhookLogic.MAIL_HOOK_ID);
        
        MailhookLogic.get().saveMailConfig(entity, properties);
        setAttributeOnProperty(entity);
        
        String successMsg = "message.success.save";
        setResult(successMsg, errors);
        
        return index();
    }

    /**
     * MailHookの設定でメールサーバーにアクセスできるかチェック
     * 
     * @return
     */
    @Get(subscribeToken = "admin")
    @Auth(roles = "admin")
    public Boundary check() {
        MailHooksEntity entity = MailHooksDao.get().selectOnKey(MailhookLogic.MAIL_HOOK_ID);
        if (entity == null) {
            addMsgWarn("errors.notfound", getResource("knowledge.admin.post.from.mail"));
            return index();
        }
        if (MailhookLogic.get().connect(entity)) {
            addMsgSuccess("message.success.process");
        } else {
            addMsgWarn("message.fail.process");
        }
        return index();
    }
    
    /**
     * 設定削除
     * @return
     */
    @Post(subscribeToken = "admin")
    @Auth(roles = "admin")
    public Boundary delete() {
        MailhookLogic.get().removeHook(MailhookLogic.MAIL_HOOK_ID);
        addMsgSuccess("message.success.delete");
        return index();
    }
    
    
    

    /**
     * MailHookの追加
     * 
     * @return
     * @throws InvalidParamException 
     */
    @Get(publishToken = "admin")
    @Auth(roles = "admin")
    public Boundary hook() throws InvalidParamException {
        int conditionNo = getPathInteger(-1);
        MailHookConditionsEntity hooksEntity = null;
        if (conditionNo != -1) {
            hooksEntity = MailHookConditionsDao.get().selectOnKey(conditionNo, MailhookLogic.MAIL_HOOK_ID);
        }
        if (hooksEntity == null) {
            hooksEntity = new MailHookConditionsEntity();
            hooksEntity.setConditionNo(-1);
        }
        setAttributeOnProperty(hooksEntity);
        
        if (StringUtils.isNotEmpty(hooksEntity.getViewers())) {
            String[] targets = hooksEntity.getViewers().split(",");
            List<LabelValue> viewers = TargetLogic.get().selectTargets(targets);
            setAttribute("viewers", viewers);
        }
        if (StringUtils.isNotEmpty(hooksEntity.getEditors())) {
            String[] targets = hooksEntity.getEditors().split(",");
            List<LabelValue> editors = TargetLogic.get().selectTargets(targets);
            setAttribute("editors", editors);
        }
        
        return forward("hook.jsp");
    }
    
    /**
     * MailHookの追加
     * 
     * @return
     * @throws InvalidParamException 
     * @throws IOException 
     * @throws JSONException 
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     */
    @Post(path = "admin.mailhook/hook", subscribeToken = "admin")
    @Auth(roles = "admin")
    public Boundary saveHook() throws InvalidParamException, InstantiationException, IllegalAccessException, JSONException, IOException {
        LOG.trace("saveHook");
        MailHookConditionsEntity hooksEntity = super.getParamOnProperty(MailHookConditionsEntity.class);
        if (hooksEntity.getConditionNo() == null) {
            hooksEntity.setConditionNo(-1);
        }
        List<ValidateError> errors = hooksEntity.validate();
        if (!errors.isEmpty()) {
            return sendValidateError(errors);
        }
        Validator validator = ValidatorFactory.getInstance(Validator.REQUIRED);
        ValidateError error = validator.validate(hooksEntity.getCondition(), getResource("knowledge.admin.mailhook.condition.character"));
        if (error != null) {
            errors.add(error);
        }
        error = validator.validate(hooksEntity.getProcessUser(), getResource("knowledge.admin.mailhook.condition.process.user"));
        if (error != null) {
            errors.add(error);
        }
        if (!errors.isEmpty()) {
            return sendValidateError(errors);
        }
        
        hooksEntity = MailhookLogic.get().saveCondition(hooksEntity);
        setAttributeOnProperty(hooksEntity);
        
        return sendMsg(MessageStatus.Success, HttpStatus.SC_200_OK, String.valueOf(hooksEntity.getConditionNo()), "message.success.save");
    }
    
    /**
     * メール投稿条件の削除
     * @return
     * @throws InvalidParamException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws JSONException
     * @throws IOException
     */
    @Post(path = "admin.mailhook/deleteHook", subscribeToken = "admin")
    @Auth(roles = "admin")
    public Boundary deleteHook() throws InvalidParamException, InstantiationException, IllegalAccessException, JSONException, IOException {
        Integer conditionNo = super.getParam("conditionNo", Integer.class);
        if (conditionNo == null || conditionNo.intValue() == -1) {
            return index();
        }
        MailhookLogic.get().deleteHookCondition(conditionNo);
        
        return sendMsg(MessageStatus.Success, HttpStatus.SC_200_OK, "deleted", "message.success.delete");
    }
    
    
    
}
