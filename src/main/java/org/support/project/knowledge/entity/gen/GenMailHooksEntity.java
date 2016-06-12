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
 * 受信したメールからの処理
 */
@DI(instance = Instance.Prototype)
public class GenMailHooksEntity implements Serializable {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * Get instance from DI container.
     * @return instance
     */
    public static GenMailHooksEntity get() {
        return Container.getComp(GenMailHooksEntity.class);
    }

    /**
     * Constructor.
     */
    public GenMailHooksEntity() {
        super();
    }

    /**
     * Constructor
     * @param hookId HOOK_ID
     */

    public GenMailHooksEntity(Integer hookId) {
        super();
        this.hookId = hookId;
    }
    /** HOOK_ID */
    private Integer hookId;
    /** MAIL_PROTOCOL */
    private String mailProtocol;
    /** MAIL_HOST */
    private String mailHost;
    /** MAIL_PORT */
    private Integer mailPort;
    /** MAIL_USER */
    private String mailUser;
    /** MAIL_PASS */
    private String mailPass;
    /** MAIL_PASS_SALT */
    private String mailPassSalt;
    /** MAIL_FOLDER */
    private String mailFolder;
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
     * Get HOOK_ID.
     * @return HOOK_ID
     */
    public Integer getHookId() {
        return this.hookId;
    }
    /**
     * Set HOOK_ID.
     * @param hookId HOOK_ID
     * @return this object     */
    public GenMailHooksEntity setHookId(Integer hookId) {
        this.hookId = hookId;
        return this;
    }

    /**
     * Get MAIL_PROTOCOL.
     * @return MAIL_PROTOCOL
     */
    public String getMailProtocol() {
        return this.mailProtocol;
    }
    /**
     * Set MAIL_PROTOCOL.
     * @param mailProtocol MAIL_PROTOCOL
     * @return this object     */
    public GenMailHooksEntity setMailProtocol(String mailProtocol) {
        this.mailProtocol = mailProtocol;
        return this;
    }

    /**
     * Get MAIL_HOST.
     * @return MAIL_HOST
     */
    public String getMailHost() {
        return this.mailHost;
    }
    /**
     * Set MAIL_HOST.
     * @param mailHost MAIL_HOST
     * @return this object     */
    public GenMailHooksEntity setMailHost(String mailHost) {
        this.mailHost = mailHost;
        return this;
    }

    /**
     * Get MAIL_PORT.
     * @return MAIL_PORT
     */
    public Integer getMailPort() {
        return this.mailPort;
    }
    /**
     * Set MAIL_PORT.
     * @param mailPort MAIL_PORT
     * @return this object     */
    public GenMailHooksEntity setMailPort(Integer mailPort) {
        this.mailPort = mailPort;
        return this;
    }

    /**
     * Get MAIL_USER.
     * @return MAIL_USER
     */
    public String getMailUser() {
        return this.mailUser;
    }
    /**
     * Set MAIL_USER.
     * @param mailUser MAIL_USER
     * @return this object     */
    public GenMailHooksEntity setMailUser(String mailUser) {
        this.mailUser = mailUser;
        return this;
    }

    /**
     * Get MAIL_PASS.
     * @return MAIL_PASS
     */
    public String getMailPass() {
        return this.mailPass;
    }
    /**
     * Set MAIL_PASS.
     * @param mailPass MAIL_PASS
     * @return this object     */
    public GenMailHooksEntity setMailPass(String mailPass) {
        this.mailPass = mailPass;
        return this;
    }

    /**
     * Get MAIL_PASS_SALT.
     * @return MAIL_PASS_SALT
     */
    public String getMailPassSalt() {
        return this.mailPassSalt;
    }
    /**
     * Set MAIL_PASS_SALT.
     * @param mailPassSalt MAIL_PASS_SALT
     * @return this object     */
    public GenMailHooksEntity setMailPassSalt(String mailPassSalt) {
        this.mailPassSalt = mailPassSalt;
        return this;
    }

    /**
     * Get MAIL_FOLDER.
     * @return MAIL_FOLDER
     */
    public String getMailFolder() {
        return this.mailFolder;
    }
    /**
     * Set MAIL_FOLDER.
     * @param mailFolder MAIL_FOLDER
     * @return this object     */
    public GenMailHooksEntity setMailFolder(String mailFolder) {
        this.mailFolder = mailFolder;
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
    public GenMailHooksEntity setInsertUser(Integer insertUser) {
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
    public GenMailHooksEntity setInsertDatetime(Timestamp insertDatetime) {
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
    public GenMailHooksEntity setUpdateUser(Integer updateUser) {
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
    public GenMailHooksEntity setUpdateDatetime(Timestamp updateDatetime) {
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
    public GenMailHooksEntity setDeleteFlag(Integer deleteFlag) {
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
     * @param hookId HOOK_ID
     */
    public void setKeyValues(Integer hookId) {
        this.hookId = hookId;
    }
    /**
     * compare on key 
     * @param entity entity 
     * @return result 
     */
    public boolean equalsOnKey(GenMailHooksEntity entity) {
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
        builder.append("mailProtocol = ").append(mailProtocol).append("\n");
        builder.append("mailHost = ").append(mailHost).append("\n");
        builder.append("mailPort = ").append(mailPort).append("\n");
        builder.append("mailUser = ").append(mailUser).append("\n");
        builder.append("mailPass = ").append(mailPass).append("\n");
        builder.append("mailPassSalt = ").append(mailPassSalt).append("\n");
        builder.append("mailFolder = ").append(mailFolder).append("\n");
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
        error = validator.validate(this.mailProtocol, convLabelName("Mail Protocol"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(this.mailProtocol, convLabelName("Mail Protocol"), 10);
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.REQUIRED);
        error = validator.validate(this.mailHost, convLabelName("Mail Host"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(this.mailHost, convLabelName("Mail Host"), 256);
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.REQUIRED);
        error = validator.validate(this.mailPort, convLabelName("Mail Port"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.INTEGER);
        error = validator.validate(this.mailPort, convLabelName("Mail Port"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(this.mailUser, convLabelName("Mail User"), 256);
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(this.mailPass, convLabelName("Mail Pass"), 1024);
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(this.mailPassSalt, convLabelName("Mail Pass Salt"), 1024);
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(this.mailFolder, convLabelName("Mail Folder"), 64);
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
        error = validator.validate(values.get("mailProtocol"), convLabelName("Mail Protocol"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(values.get("mailProtocol"), convLabelName("Mail Protocol"), 10);
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.REQUIRED);
        error = validator.validate(values.get("mailHost"), convLabelName("Mail Host"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(values.get("mailHost"), convLabelName("Mail Host"), 256);
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.REQUIRED);
        error = validator.validate(values.get("mailPort"), convLabelName("Mail Port"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.INTEGER);
        error = validator.validate(values.get("mailPort"), convLabelName("Mail Port"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(values.get("mailUser"), convLabelName("Mail User"), 256);
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(values.get("mailPass"), convLabelName("Mail Pass"), 1024);
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(values.get("mailPassSalt"), convLabelName("Mail Pass Salt"), 1024);
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(values.get("mailFolder"), convLabelName("Mail Folder"), 64);
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
