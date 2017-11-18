package org.support.project.knowledge.entity.gen;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import java.sql.Timestamp;



import org.support.project.common.bean.ValidateError;
import org.support.project.common.validate.Validator;
import org.support.project.common.validate.ValidatorFactory;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;

/**
 * Webhook 設定
 */
@DI(instance = Instance.Prototype)
public class GenWebhookConfigsEntity implements Serializable {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * Get instance from DI container.
     * @return instance
     */
    public static GenWebhookConfigsEntity get() {
        return Container.getComp(GenWebhookConfigsEntity.class);
    }

    /**
     * Constructor.
     */
    public GenWebhookConfigsEntity() {
        super();
    }

    /**
     * Constructor
     * @param hookId HOOK ID
     */

    public GenWebhookConfigsEntity(Integer hookId) {
        super();
        this.hookId = hookId;
    }
    /** HOOK ID */
    private Integer hookId;
    /** HOOK */
    private String hook;
    /** URL */
    private String url;
    /** IGNORE_PROXY */
    private Integer ignoreProxy;
    /** TEMPLATE */
    private String template;
    /** 登録ユーザ */
    private Integer insertUser;
    /** 登録日時 */
    private Timestamp insertDatetime;
    /** 更新ユーザ */
    private Integer updateUser;
    /** 更新日時 */
    private Timestamp updateDatetime;
    /** 削除フラグ */
    private Integer deleteFlag;

    /**
     * Get HOOK ID.
     * @return HOOK ID
     */
    public Integer getHookId() {
        return this.hookId;
    }
    /**
     * Set HOOK ID.
     * @param hookId HOOK ID
     * @return this object     */
    public GenWebhookConfigsEntity setHookId(Integer hookId) {
        this.hookId = hookId;
        return this;
    }

    /**
     * Get HOOK.
     * @return HOOK
     */
    public String getHook() {
        return this.hook;
    }
    /**
     * Set HOOK.
     * @param hook HOOK
     * @return this object     */
    public GenWebhookConfigsEntity setHook(String hook) {
        this.hook = hook;
        return this;
    }

    /**
     * Get URL.
     * @return URL
     */
    public String getUrl() {
        return this.url;
    }
    /**
     * Set URL.
     * @param url URL
     * @return this object     */
    public GenWebhookConfigsEntity setUrl(String url) {
        this.url = url;
        return this;
    }

    /**
     * Get IGNORE_PROXY.
     * @return IGNORE_PROXY
     */
    public Integer getIgnoreProxy() {
        return this.ignoreProxy;
    }
    /**
     * Set IGNORE_PROXY.
     * @param ignoreProxy IGNORE_PROXY
     * @return this object     */
    public GenWebhookConfigsEntity setIgnoreProxy(Integer ignoreProxy) {
        this.ignoreProxy = ignoreProxy;
        return this;
    }

    /**
     * Get TEMPLATE.
     * @return TEMPLATE
     */
    public String getTemplate() {
        return this.template;
    }
    /**
     * Set TEMPLATE.
     * @param template TEMPLATE
     * @return this object     */
    public GenWebhookConfigsEntity setTemplate(String template) {
        this.template = template;
        return this;
    }

    /**
     * Get 登録ユーザ.
     * @return 登録ユーザ
     */
    public Integer getInsertUser() {
        return this.insertUser;
    }
    /**
     * Set 登録ユーザ.
     * @param insertUser 登録ユーザ
     * @return this object     */
    public GenWebhookConfigsEntity setInsertUser(Integer insertUser) {
        this.insertUser = insertUser;
        return this;
    }

    /**
     * Get 登録日時.
     * @return 登録日時
     */
    public Timestamp getInsertDatetime() {
        return this.insertDatetime;
    }
    /**
     * Set 登録日時.
     * @param insertDatetime 登録日時
     * @return this object     */
    public GenWebhookConfigsEntity setInsertDatetime(Timestamp insertDatetime) {
        this.insertDatetime = insertDatetime;
        return this;
    }

    /**
     * Get 更新ユーザ.
     * @return 更新ユーザ
     */
    public Integer getUpdateUser() {
        return this.updateUser;
    }
    /**
     * Set 更新ユーザ.
     * @param updateUser 更新ユーザ
     * @return this object     */
    public GenWebhookConfigsEntity setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
        return this;
    }

    /**
     * Get 更新日時.
     * @return 更新日時
     */
    public Timestamp getUpdateDatetime() {
        return this.updateDatetime;
    }
    /**
     * Set 更新日時.
     * @param updateDatetime 更新日時
     * @return this object     */
    public GenWebhookConfigsEntity setUpdateDatetime(Timestamp updateDatetime) {
        this.updateDatetime = updateDatetime;
        return this;
    }

    /**
     * Get 削除フラグ.
     * @return 削除フラグ
     */
    public Integer getDeleteFlag() {
        return this.deleteFlag;
    }
    /**
     * Set 削除フラグ.
     * @param deleteFlag 削除フラグ
     * @return this object     */
    public GenWebhookConfigsEntity setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
        return this;
    }

    /**
     * Get key values 
     * @return values 
     */
    public Object[] getKeyValues() {
        Object[] keyValues = new Object[1];
        keyValues[0] = this.hookId;
        return keyValues;
    }
    /**
     * Set key values 
     * @param hookId HOOK ID
     */
    public void setKeyValues(Integer hookId) {
        this.hookId = hookId;
    }
    /**
     * compare on key 
     * @param entity entity 
     * @return result 
     */
    public boolean equalsOnKey(GenWebhookConfigsEntity entity) {
        Object[] keyValues1 = getKeyValues();
        Object[] keyValues2 = entity.getKeyValues();
        for (int i = 0; i < keyValues1.length; i++) {
            Object val1 = keyValues1[i];
            Object val2 = keyValues2[i];
            if (val1 == null && val2 != null) {
                return false;
            }
            if (val1 != null && val2 == null) {
                return false;
            }
            if (val1 != null && val2 != null) {
                if (!val1.equals(val2)) {
                    return false;
                }
            }
            
        }
        return true;
    }
    /**
     * ToString 
     * @return string 
     */
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("hookId = ").append(hookId).append("\n");
        builder.append("hook = ").append(hook).append("\n");
        builder.append("url = ").append(url).append("\n");
        builder.append("ignoreProxy = ").append(ignoreProxy).append("\n");
        builder.append("template = ").append(template).append("\n");
        builder.append("insertUser = ").append(insertUser).append("\n");
        builder.append("insertDatetime = ").append(insertDatetime).append("\n");
        builder.append("updateUser = ").append(updateUser).append("\n");
        builder.append("updateDatetime = ").append(updateDatetime).append("\n");
        builder.append("deleteFlag = ").append(deleteFlag).append("\n");
        return builder.toString();
    }
    /**
     * Convert label to display 
     * @param label label
     * @return convert label 
     */
    protected String convLabelName(String label) {
        return label;
    }
    /**
     * validate 
     * @return validate error list 
     */
    public List<ValidateError> validate() {
        List<ValidateError> errors = new ArrayList<>();
        Validator validator;
        ValidateError error;
        validator = ValidatorFactory.getInstance(Validator.INTEGER);
        error = validator.validate(this.hookId, convLabelName("Hook Id"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.REQUIRED);
        error = validator.validate(this.hook, convLabelName("Hook"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(this.hook, convLabelName("Hook"), 20);
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.REQUIRED);
        error = validator.validate(this.url, convLabelName("Url"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(this.url, convLabelName("Url"), 256);
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.INTEGER);
        error = validator.validate(this.ignoreProxy, convLabelName("Ignore Proxy"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.INTEGER);
        error = validator.validate(this.insertUser, convLabelName("Insert User"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.INTEGER);
        error = validator.validate(this.updateUser, convLabelName("Update User"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.INTEGER);
        error = validator.validate(this.deleteFlag, convLabelName("Delete Flag"));
        if (error != null) {
            errors.add(error);
        }
        return errors;
    }
    /**
     * validate 
     * @param values value map 
     * @return validate error list 
     */
    public List<ValidateError> validate(Map<String, String> values) {
        List<ValidateError> errors = new ArrayList<>();
        Validator validator;
        ValidateError error;
        validator = ValidatorFactory.getInstance(Validator.INTEGER);
        error = validator.validate(values.get("hookId"), convLabelName("Hook Id"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.REQUIRED);
        error = validator.validate(values.get("hook"), convLabelName("Hook"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(values.get("hook"), convLabelName("Hook"), 20);
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.REQUIRED);
        error = validator.validate(values.get("url"), convLabelName("Url"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(values.get("url"), convLabelName("Url"), 256);
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.INTEGER);
        error = validator.validate(values.get("ignoreProxy"), convLabelName("Ignore Proxy"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.INTEGER);
        error = validator.validate(values.get("insertUser"), convLabelName("Insert User"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.INTEGER);
        error = validator.validate(values.get("updateUser"), convLabelName("Update User"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.INTEGER);
        error = validator.validate(values.get("deleteFlag"), convLabelName("Delete Flag"));
        if (error != null) {
            errors.add(error);
        }
        return errors;
    }

}
