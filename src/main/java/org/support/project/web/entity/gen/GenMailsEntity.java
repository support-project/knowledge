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
 * メール
 */
@DI(instance = Instance.Prototype)
public class GenMailsEntity implements Serializable {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * Get instance from DI container.
     * @return instance
     */
    public static GenMailsEntity get() {
        return Container.getComp(GenMailsEntity.class);
    }

    /**
     * Constructor.
     */
    public GenMailsEntity() {
        super();
    }

    /**
     * Constructor
     * @param mailId MAIL_ID
     */

    public GenMailsEntity(String mailId) {
        super();
        this.mailId = mailId;
    }
    /** MAIL_ID */
    private String mailId;
    /** ステータス */
    private Integer status;
    /** 送信先 */
    private String toAddress;
    /** 送信先名 */
    private String toName;
    /** 送信元 */
    private String fromAddress;
    /** 送信元名 */
    private String fromName;
    /** タイトル */
    private String title;
    /** メッセージ */
    private String content;
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
     * Get MAIL_ID.
     * @return MAIL_ID
     */
    public String getMailId() {
        return this.mailId;
    }
    /**
     * Set MAIL_ID.
     * @param mailId MAIL_ID
     * @return this object     */
    public GenMailsEntity setMailId(String mailId) {
        this.mailId = mailId;
        return this;
    }

    /**
     * Get ステータス.
     * @return ステータス
     */
    public Integer getStatus() {
        return this.status;
    }
    /**
     * Set ステータス.
     * @param status ステータス
     * @return this object     */
    public GenMailsEntity setStatus(Integer status) {
        this.status = status;
        return this;
    }

    /**
     * Get 送信先.
     * @return 送信先
     */
    public String getToAddress() {
        return this.toAddress;
    }
    /**
     * Set 送信先.
     * @param toAddress 送信先
     * @return this object     */
    public GenMailsEntity setToAddress(String toAddress) {
        this.toAddress = toAddress;
        return this;
    }

    /**
     * Get 送信先名.
     * @return 送信先名
     */
    public String getToName() {
        return this.toName;
    }
    /**
     * Set 送信先名.
     * @param toName 送信先名
     * @return this object     */
    public GenMailsEntity setToName(String toName) {
        this.toName = toName;
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
    public GenMailsEntity setFromAddress(String fromAddress) {
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
    public GenMailsEntity setFromName(String fromName) {
        this.fromName = fromName;
        return this;
    }

    /**
     * Get タイトル.
     * @return タイトル
     */
    public String getTitle() {
        return this.title;
    }
    /**
     * Set タイトル.
     * @param title タイトル
     * @return this object     */
    public GenMailsEntity setTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     * Get メッセージ.
     * @return メッセージ
     */
    public String getContent() {
        return this.content;
    }
    /**
     * Set メッセージ.
     * @param content メッセージ
     * @return this object     */
    public GenMailsEntity setContent(String content) {
        this.content = content;
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
    public GenMailsEntity setRowId(String rowId) {
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
    public GenMailsEntity setInsertUser(Integer insertUser) {
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
    public GenMailsEntity setInsertDatetime(Timestamp insertDatetime) {
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
    public GenMailsEntity setUpdateUser(Integer updateUser) {
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
    public GenMailsEntity setUpdateDatetime(Timestamp updateDatetime) {
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
    public GenMailsEntity setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
        return this;
    }

    /**
     * Get key values 
     * @return values 
     */
    public Object[] getKeyValues() {
        Object[] keyValues = new Object[1];
        keyValues[0] = this.mailId;
        return keyValues;
    }
    /**
     * Set key values 
     * @param mailId MAIL_ID
     */
    public void setKeyValues(String mailId) {
        this.mailId = mailId;
    }
    /**
     * compare on key 
     * @param entity entity 
     * @return result 
     */
    public boolean equalsOnKey(GenMailsEntity entity) {
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
        builder.append("mailId = ").append(mailId).append("\n");
        builder.append("status = ").append(status).append("\n");
        builder.append("toAddress = ").append(toAddress).append("\n");
        builder.append("toName = ").append(toName).append("\n");
        builder.append("fromAddress = ").append(fromAddress).append("\n");
        builder.append("fromName = ").append(fromName).append("\n");
        builder.append("title = ").append(title).append("\n");
        builder.append("content = ").append(content).append("\n");
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
        error = validator.validate(this.mailId, convLabelName("Mail Id"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(this.mailId, convLabelName("Mail Id"), 64);
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.REQUIRED);
        error = validator.validate(this.status, convLabelName("Status"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.INTEGER);
        error = validator.validate(this.status, convLabelName("Status"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.REQUIRED);
        error = validator.validate(this.toAddress, convLabelName("To Address"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(this.toAddress, convLabelName("To Address"), 256);
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(this.toName, convLabelName("To Name"), 256);
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
        validator = ValidatorFactory.getInstance(Validator.REQUIRED);
        error = validator.validate(this.title, convLabelName("Title"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(this.title, convLabelName("Title"), 256);
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
        error = validator.validate(values.get("mailId"), convLabelName("Mail Id"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(values.get("mailId"), convLabelName("Mail Id"), 64);
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.REQUIRED);
        error = validator.validate(values.get("status"), convLabelName("Status"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.INTEGER);
        error = validator.validate(values.get("status"), convLabelName("Status"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.REQUIRED);
        error = validator.validate(values.get("toAddress"), convLabelName("To Address"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(values.get("toAddress"), convLabelName("To Address"), 256);
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(values.get("toName"), convLabelName("To Name"), 256);
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
        validator = ValidatorFactory.getInstance(Validator.REQUIRED);
        error = validator.validate(values.get("title"), convLabelName("Title"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(values.get("title"), convLabelName("Title"), 256);
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
