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
 * ログイン履歴
 */
@DI(instance = Instance.Prototype)
public class GenLoginHistoriesEntity implements Serializable {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * Get instance from DI container.
     * @return instance
     */
    public static GenLoginHistoriesEntity get() {
        return Container.getComp(GenLoginHistoriesEntity.class);
    }

    /**
     * Constructor.
     */
    public GenLoginHistoriesEntity() {
        super();
    }

    /**
     * Constructor
     * @param loginCount ログイン番号
     * @param userId ユーザID
     */

    public GenLoginHistoriesEntity(Double loginCount, Integer userId) {
        super();
        this.loginCount = loginCount;
        this.userId = userId;
    }
    /** ユーザID */
    private Integer userId;
    /** ログイン番号 */
    private Double loginCount;
    /** ログイン日時 */
    private Timestamp lodinDateTime;
    /** IPアドレス */
    private String ipAddress;
    /** エージェント */
    private String userAgent;
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
     * Get ユーザID.
     * @return ユーザID
     */
    public Integer getUserId() {
        return this.userId;
    }
    /**
     * Set ユーザID.
     * @param userId ユーザID
     * @return this object     */
    public GenLoginHistoriesEntity setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    /**
     * Get ログイン番号.
     * @return ログイン番号
     */
    public Double getLoginCount() {
        return this.loginCount;
    }
    /**
     * Set ログイン番号.
     * @param loginCount ログイン番号
     * @return this object     */
    public GenLoginHistoriesEntity setLoginCount(Double loginCount) {
        this.loginCount = loginCount;
        return this;
    }

    /**
     * Get ログイン日時.
     * @return ログイン日時
     */
    public Timestamp getLodinDateTime() {
        return this.lodinDateTime;
    }
    /**
     * Set ログイン日時.
     * @param lodinDateTime ログイン日時
     * @return this object     */
    public GenLoginHistoriesEntity setLodinDateTime(Timestamp lodinDateTime) {
        this.lodinDateTime = lodinDateTime;
        return this;
    }

    /**
     * Get IPアドレス.
     * @return IPアドレス
     */
    public String getIpAddress() {
        return this.ipAddress;
    }
    /**
     * Set IPアドレス.
     * @param ipAddress IPアドレス
     * @return this object     */
    public GenLoginHistoriesEntity setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
        return this;
    }

    /**
     * Get エージェント.
     * @return エージェント
     */
    public String getUserAgent() {
        return this.userAgent;
    }
    /**
     * Set エージェント.
     * @param userAgent エージェント
     * @return this object     */
    public GenLoginHistoriesEntity setUserAgent(String userAgent) {
        this.userAgent = userAgent;
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
    public GenLoginHistoriesEntity setRowId(String rowId) {
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
    public GenLoginHistoriesEntity setInsertUser(Integer insertUser) {
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
    public GenLoginHistoriesEntity setInsertDatetime(Timestamp insertDatetime) {
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
    public GenLoginHistoriesEntity setUpdateUser(Integer updateUser) {
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
    public GenLoginHistoriesEntity setUpdateDatetime(Timestamp updateDatetime) {
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
    public GenLoginHistoriesEntity setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
        return this;
    }

    /**
     * Get key values 
     * @return values 
     */
    public Object[] getKeyValues() {
        Object[] keyValues = new Object[2];
        keyValues[0] = this.loginCount;
        keyValues[1] = this.userId;
        return keyValues;
    }
    /**
     * Set key values 
     * @param loginCount ログイン番号
     * @param userId ユーザID
     */
    public void setKeyValues(Double loginCount, Integer userId) {
        this.loginCount = loginCount;
        this.userId = userId;
    }
    /**
     * compare on key 
     * @param entity entity 
     * @return result 
     */
    public boolean equalsOnKey(GenLoginHistoriesEntity entity) {
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
        builder.append("loginCount = ").append(loginCount).append("\n");
        builder.append("userId = ").append(userId).append("\n");
        builder.append("lodinDateTime = ").append(lodinDateTime).append("\n");
        builder.append("ipAddress = ").append(ipAddress).append("\n");
        builder.append("userAgent = ").append(userAgent).append("\n");
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
        error = validator.validate(this.userId, convLabelName("User Id"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.INTEGER);
        error = validator.validate(this.userId, convLabelName("User Id"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.REQUIRED);
        error = validator.validate(this.loginCount, convLabelName("Login Count"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.REQUIRED);
        error = validator.validate(this.lodinDateTime, convLabelName("Lodin Date Time"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(this.ipAddress, convLabelName("Ip Address"), 15);
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(this.userAgent, convLabelName("User Agent"), 256);
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
        error = validator.validate(values.get("userId"), convLabelName("User Id"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.INTEGER);
        error = validator.validate(values.get("userId"), convLabelName("User Id"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.REQUIRED);
        error = validator.validate(values.get("loginCount"), convLabelName("Login Count"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.REQUIRED);
        error = validator.validate(values.get("lodinDateTime"), convLabelName("Lodin Date Time"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(values.get("ipAddress"), convLabelName("Ip Address"), 15);
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(values.get("userAgent"), convLabelName("User Agent"), 256);
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
