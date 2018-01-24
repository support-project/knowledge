package org.support.project.web.entity.gen;

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
 * メール設定
 */
@DI(instance = Instance.Prototype)
public class GenMailConfigsEntity implements Serializable {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * Get instance from DI container.
     * @return instance
     */
    public static GenMailConfigsEntity get() {
        return Container.getComp(GenMailConfigsEntity.class);
    }

    /**
     * Constructor.
     */
    public GenMailConfigsEntity() {
        super();
    }

    /**
     * Constructor
     * @param systemName システム名
     */

    public GenMailConfigsEntity(String systemName) {
        super();
        this.systemName = systemName;
    }
    /** システム名 */
    private String systemName;
    /** SMTP_HOST */
    private String host;
    /** SMTP_PORT */
    private Integer port;
    /** AUTH_TYPE */
    private Integer authType;
    /** SMTP_ID */
    private String smtpId;
    /** SMTP_PASSWORD	 暗号化（可逆） */
    private String smtpPassword;
    /** SALT */
    private String salt;
    /** 送信元 */
    private String fromAddress;
    /** 送信元名 */
    private String fromName;
    /** 行ID */
    private String rowId;
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
     * Get システム名.
     * @return システム名
     */
    public String getSystemName() {
        return this.systemName;
    }
    /**
     * Set システム名.
     * @param systemName システム名
     * @return this object     */
    public GenMailConfigsEntity setSystemName(String systemName) {
        this.systemName = systemName;
        return this;
    }

    /**
     * Get SMTP_HOST.
     * @return SMTP_HOST
     */
    public String getHost() {
        return this.host;
    }
    /**
     * Set SMTP_HOST.
     * @param host SMTP_HOST
     * @return this object     */
    public GenMailConfigsEntity setHost(String host) {
        this.host = host;
        return this;
    }

    /**
     * Get SMTP_PORT.
     * @return SMTP_PORT
     */
    public Integer getPort() {
        return this.port;
    }
    /**
     * Set SMTP_PORT.
     * @param port SMTP_PORT
     * @return this object     */
    public GenMailConfigsEntity setPort(Integer port) {
        this.port = port;
        return this;
    }

    /**
     * Get AUTH_TYPE.
     * @return AUTH_TYPE
     */
    public Integer getAuthType() {
        return this.authType;
    }
    /**
     * Set AUTH_TYPE.
     * @param authType AUTH_TYPE
     * @return this object     */
    public GenMailConfigsEntity setAuthType(Integer authType) {
        this.authType = authType;
        return this;
    }

    /**
     * Get SMTP_ID.
     * @return SMTP_ID
     */
    public String getSmtpId() {
        return this.smtpId;
    }
    /**
     * Set SMTP_ID.
     * @param smtpId SMTP_ID
     * @return this object     */
    public GenMailConfigsEntity setSmtpId(String smtpId) {
        this.smtpId = smtpId;
        return this;
    }

    /**
     * Get SMTP_PASSWORD	 暗号化（可逆）.
     * @return SMTP_PASSWORD	 暗号化（可逆）
     */
    public String getSmtpPassword() {
        return this.smtpPassword;
    }
    /**
     * Set SMTP_PASSWORD	 暗号化（可逆）.
     * @param smtpPassword SMTP_PASSWORD	 暗号化（可逆）
     * @return this object     */
    public GenMailConfigsEntity setSmtpPassword(String smtpPassword) {
        this.smtpPassword = smtpPassword;
        return this;
    }

    /**
     * Get SALT.
     * @return SALT
     */
    public String getSalt() {
        return this.salt;
    }
    /**
     * Set SALT.
     * @param salt SALT
     * @return this object     */
    public GenMailConfigsEntity setSalt(String salt) {
        this.salt = salt;
        return this;
    }

    /**
     * Get 送信元.
     * @return 送信元
     */
    public String getFromAddress() {
        return this.fromAddress;
    }
    /**
     * Set 送信元.
     * @param fromAddress 送信元
     * @return this object     */
    public GenMailConfigsEntity setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
        return this;
    }

    /**
     * Get 送信元名.
     * @return 送信元名
     */
    public String getFromName() {
        return this.fromName;
    }
    /**
     * Set 送信元名.
     * @param fromName 送信元名
     * @return this object     */
    public GenMailConfigsEntity setFromName(String fromName) {
        this.fromName = fromName;
        return this;
    }

    /**
     * Get 行ID.
     * @return 行ID
     */
    public String getRowId() {
        return this.rowId;
    }
    /**
     * Set 行ID.
     * @param rowId 行ID
     * @return this object     */
    public GenMailConfigsEntity setRowId(String rowId) {
        this.rowId = rowId;
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
    public GenMailConfigsEntity setInsertUser(Integer insertUser) {
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
    public GenMailConfigsEntity setInsertDatetime(Timestamp insertDatetime) {
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
    public GenMailConfigsEntity setUpdateUser(Integer updateUser) {
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
    public GenMailConfigsEntity setUpdateDatetime(Timestamp updateDatetime) {
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
    public GenMailConfigsEntity setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
        return this;
    }

    /**
     * Get key values 
     * @return values 
     */
    public Object[] getKeyValues() {
        Object[] keyValues = new Object[1];
        keyValues[0] = this.systemName;
        return keyValues;
    }
    /**
     * Set key values 
     * @param systemName システム名
     */
    public void setKeyValues(String systemName) {
        this.systemName = systemName;
    }
    /**
     * compare on key 
     * @param entity entity 
     * @return result 
     */
    public boolean equalsOnKey(GenMailConfigsEntity entity) {
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
        builder.append("systemName = ").append(systemName).append("\n");
        builder.append("host = ").append(host).append("\n");
        builder.append("port = ").append(port).append("\n");
        builder.append("authType = ").append(authType).append("\n");
        builder.append("smtpId = ").append(smtpId).append("\n");
        builder.append("smtpPassword = ").append(smtpPassword).append("\n");
        builder.append("salt = ").append(salt).append("\n");
        builder.append("fromAddress = ").append(fromAddress).append("\n");
        builder.append("fromName = ").append(fromName).append("\n");
        builder.append("rowId = ").append(rowId).append("\n");
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
        validator = ValidatorFactory.getInstance(Validator.REQUIRED);
        error = validator.validate(this.systemName, convLabelName("System Name"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(this.systemName, convLabelName("System Name"), 64);
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.REQUIRED);
        error = validator.validate(this.host, convLabelName("Host"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(this.host, convLabelName("Host"), 256);
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.REQUIRED);
        error = validator.validate(this.port, convLabelName("Port"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.INTEGER);
        error = validator.validate(this.port, convLabelName("Port"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.REQUIRED);
        error = validator.validate(this.authType, convLabelName("Auth Type"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.INTEGER);
        error = validator.validate(this.authType, convLabelName("Auth Type"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(this.smtpId, convLabelName("Smtp Id"), 256);
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(this.smtpPassword, convLabelName("Smtp Password"), 1024);
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(this.salt, convLabelName("Salt"), 1024);
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(this.fromAddress, convLabelName("From Address"), 256);
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(this.fromName, convLabelName("From Name"), 256);
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(this.rowId, convLabelName("Row Id"), 64);
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
        validator = ValidatorFactory.getInstance(Validator.REQUIRED);
        error = validator.validate(values.get("systemName"), convLabelName("System Name"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(values.get("systemName"), convLabelName("System Name"), 64);
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.REQUIRED);
        error = validator.validate(values.get("host"), convLabelName("Host"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(values.get("host"), convLabelName("Host"), 256);
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.REQUIRED);
        error = validator.validate(values.get("port"), convLabelName("Port"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.INTEGER);
        error = validator.validate(values.get("port"), convLabelName("Port"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.REQUIRED);
        error = validator.validate(values.get("authType"), convLabelName("Auth Type"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.INTEGER);
        error = validator.validate(values.get("authType"), convLabelName("Auth Type"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(values.get("smtpId"), convLabelName("Smtp Id"), 256);
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(values.get("smtpPassword"), convLabelName("Smtp Password"), 1024);
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(values.get("salt"), convLabelName("Salt"), 1024);
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(values.get("fromAddress"), convLabelName("From Address"), 256);
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(values.get("fromName"), convLabelName("From Name"), 256);
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(values.get("rowId"), convLabelName("Row Id"), 64);
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
