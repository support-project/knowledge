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
 * メールから投稿の際の除外条件
 */
@DI(instance = Instance.Prototype)
public class GenMailHookIgnoreConditionsEntity implements Serializable {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * Get instance from DI container.
     * @return instance
     */
    public static GenMailHookIgnoreConditionsEntity get() {
        return Container.getComp(GenMailHookIgnoreConditionsEntity.class);
    }

    /**
     * Constructor.
     */
    public GenMailHookIgnoreConditionsEntity() {
        super();
    }

    /**
     * Constructor
     * @param conditionNo CONDITION_NO
     * @param hookId HOOK_ID
     * @param ignoreConditionNo IGNORE_CONDITION_NO
     */

    public GenMailHookIgnoreConditionsEntity(Integer conditionNo, Integer hookId, Integer ignoreConditionNo) {
        super();
        this.conditionNo = conditionNo;
        this.hookId = hookId;
        this.ignoreConditionNo = ignoreConditionNo;
    }
    /** HOOK_ID */
    private Integer hookId;
    /** CONDITION_NO */
    private Integer conditionNo;
    /** IGNORE_CONDITION_NO */
    private Integer ignoreConditionNo;
    /** 条件の種類	 1:宛先が「条件文字」であった場合 */
    private Integer conditionKind;
    /** 条件の文字 */
    private String condition;
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
    public GenMailHookIgnoreConditionsEntity setHookId(Integer hookId) {
        this.hookId = hookId;
        return this;
    }

    /**
     * Get CONDITION_NO.
     * @return CONDITION_NO
     */
    public Integer getConditionNo() {
        return this.conditionNo;
    }
    /**
     * Set CONDITION_NO.
     * @param conditionNo CONDITION_NO
     * @return this object     */
    public GenMailHookIgnoreConditionsEntity setConditionNo(Integer conditionNo) {
        this.conditionNo = conditionNo;
        return this;
    }

    /**
     * Get IGNORE_CONDITION_NO.
     * @return IGNORE_CONDITION_NO
     */
    public Integer getIgnoreConditionNo() {
        return this.ignoreConditionNo;
    }
    /**
     * Set IGNORE_CONDITION_NO.
     * @param ignoreConditionNo IGNORE_CONDITION_NO
     * @return this object     */
    public GenMailHookIgnoreConditionsEntity setIgnoreConditionNo(Integer ignoreConditionNo) {
        this.ignoreConditionNo = ignoreConditionNo;
        return this;
    }

    /**
     * Get 条件の種類	 1:宛先が「条件文字」であった場合.
     * @return 条件の種類	 1:宛先が「条件文字」であった場合
     */
    public Integer getConditionKind() {
        return this.conditionKind;
    }
    /**
     * Set 条件の種類	 1:宛先が「条件文字」であった場合.
     * @param conditionKind 条件の種類	 1:宛先が「条件文字」であった場合
     * @return this object     */
    public GenMailHookIgnoreConditionsEntity setConditionKind(Integer conditionKind) {
        this.conditionKind = conditionKind;
        return this;
    }

    /**
     * Get 条件の文字.
     * @return 条件の文字
     */
    public String getCondition() {
        return this.condition;
    }
    /**
     * Set 条件の文字.
     * @param condition 条件の文字
     * @return this object     */
    public GenMailHookIgnoreConditionsEntity setCondition(String condition) {
        this.condition = condition;
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
    public GenMailHookIgnoreConditionsEntity setInsertUser(Integer insertUser) {
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
    public GenMailHookIgnoreConditionsEntity setInsertDatetime(Timestamp insertDatetime) {
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
    public GenMailHookIgnoreConditionsEntity setUpdateUser(Integer updateUser) {
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
    public GenMailHookIgnoreConditionsEntity setUpdateDatetime(Timestamp updateDatetime) {
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
    public GenMailHookIgnoreConditionsEntity setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
        return this;
    }

    /**
     * Get key values 
     * @return values 
     */
    public Object[] getKeyValues() {
        Object[] keyValues = new Object[3];
        keyValues[0] = this.conditionNo;
        keyValues[1] = this.hookId;
        keyValues[2] = this.ignoreConditionNo;
        return keyValues;
    }
    /**
     * Set key values 
     * @param conditionNo CONDITION_NO
     * @param hookId HOOK_ID
     * @param ignoreConditionNo IGNORE_CONDITION_NO
     */
    public void setKeyValues(Integer conditionNo, Integer hookId, Integer ignoreConditionNo) {
        this.conditionNo = conditionNo;
        this.hookId = hookId;
        this.ignoreConditionNo = ignoreConditionNo;
    }
    /**
     * compare on key 
     * @param entity entity 
     * @return result 
     */
    public boolean equalsOnKey(GenMailHookIgnoreConditionsEntity entity) {
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
        builder.append("conditionNo = ").append(conditionNo).append("\n");
        builder.append("hookId = ").append(hookId).append("\n");
        builder.append("ignoreConditionNo = ").append(ignoreConditionNo).append("\n");
        builder.append("conditionKind = ").append(conditionKind).append("\n");
        builder.append("condition = ").append(condition).append("\n");
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
        error = validator.validate(this.hookId, convLabelName("Hook Id"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.INTEGER);
        error = validator.validate(this.hookId, convLabelName("Hook Id"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.REQUIRED);
        error = validator.validate(this.conditionNo, convLabelName("Condition No"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.INTEGER);
        error = validator.validate(this.conditionNo, convLabelName("Condition No"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.REQUIRED);
        error = validator.validate(this.ignoreConditionNo, convLabelName("Ignore Condition No"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.INTEGER);
        error = validator.validate(this.ignoreConditionNo, convLabelName("Ignore Condition No"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.REQUIRED);
        error = validator.validate(this.conditionKind, convLabelName("Condition Kind"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.INTEGER);
        error = validator.validate(this.conditionKind, convLabelName("Condition Kind"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(this.condition, convLabelName("Condition"), 256);
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
        error = validator.validate(values.get("hookId"), convLabelName("Hook Id"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.INTEGER);
        error = validator.validate(values.get("hookId"), convLabelName("Hook Id"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.REQUIRED);
        error = validator.validate(values.get("conditionNo"), convLabelName("Condition No"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.INTEGER);
        error = validator.validate(values.get("conditionNo"), convLabelName("Condition No"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.REQUIRED);
        error = validator.validate(values.get("ignoreConditionNo"), convLabelName("Ignore Condition No"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.INTEGER);
        error = validator.validate(values.get("ignoreConditionNo"), convLabelName("Ignore Condition No"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.REQUIRED);
        error = validator.validate(values.get("conditionKind"), convLabelName("Condition Kind"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.INTEGER);
        error = validator.validate(values.get("conditionKind"), convLabelName("Condition Kind"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(values.get("condition"), convLabelName("Condition"), 256);
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
