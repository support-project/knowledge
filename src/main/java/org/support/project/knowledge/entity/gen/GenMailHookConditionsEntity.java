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
 * メールから投稿する条件
 */
@DI(instance = Instance.Prototype)
public class GenMailHookConditionsEntity implements Serializable {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * Get instance from DI container.
     * @return instance
     */
    public static GenMailHookConditionsEntity get() {
        return Container.getComp(GenMailHookConditionsEntity.class);
    }

    /**
     * Constructor.
     */
    public GenMailHookConditionsEntity() {
        super();
    }

    /**
     * Constructor
     * @param conditionNo CONDITION_NO
     * @param hookId HOOK_ID
     */

    public GenMailHookConditionsEntity(Integer conditionNo, Integer hookId) {
        super();
        this.conditionNo = conditionNo;
        this.hookId = hookId;
    }
    /** HOOK_ID */
    private Integer hookId;
    /** CONDITION_NO */
    private Integer conditionNo;
    /** 条件の種類	 1:宛先が「条件文字」であった場合 */
    private Integer conditionKind;
    /** 条件の文字 */
    private String condition;
    /** 投稿者 */
    private Integer processUser;
    /** 投稿者の指定	 1:送信者のメールアドレスから、2:常に固定 */
    private Integer processUserKind;
    /** 公開区分 */
    private Integer publicFlag;
    /** タグ */
    private String tags;
    /** 公開先 */
    private String viewers;
    /** 共同編集者 */
    private String editors;
    /** 投稿者の制限 */
    private Integer postLimit;
    /** 制限のパラメータ */
    private String limitParam;
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
    public GenMailHookConditionsEntity setHookId(Integer hookId) {
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
    public GenMailHookConditionsEntity setConditionNo(Integer conditionNo) {
        this.conditionNo = conditionNo;
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
    public GenMailHookConditionsEntity setConditionKind(Integer conditionKind) {
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
    public GenMailHookConditionsEntity setCondition(String condition) {
        this.condition = condition;
        return this;
    }

    /**
     * Get 投稿者.
     * @return 投稿者
     */
    public Integer getProcessUser() {
        return this.processUser;
    }
    /**
     * Set 投稿者.
     * @param processUser 投稿者
     * @return this object     */
    public GenMailHookConditionsEntity setProcessUser(Integer processUser) {
        this.processUser = processUser;
        return this;
    }

    /**
     * Get 投稿者の指定	 1:送信者のメールアドレスから、2:常に固定.
     * @return 投稿者の指定	 1:送信者のメールアドレスから、2:常に固定
     */
    public Integer getProcessUserKind() {
        return this.processUserKind;
    }
    /**
     * Set 投稿者の指定	 1:送信者のメールアドレスから、2:常に固定.
     * @param processUserKind 投稿者の指定	 1:送信者のメールアドレスから、2:常に固定
     * @return this object     */
    public GenMailHookConditionsEntity setProcessUserKind(Integer processUserKind) {
        this.processUserKind = processUserKind;
        return this;
    }

    /**
     * Get 公開区分.
     * @return 公開区分
     */
    public Integer getPublicFlag() {
        return this.publicFlag;
    }
    /**
     * Set 公開区分.
     * @param publicFlag 公開区分
     * @return this object     */
    public GenMailHookConditionsEntity setPublicFlag(Integer publicFlag) {
        this.publicFlag = publicFlag;
        return this;
    }

    /**
     * Get タグ.
     * @return タグ
     */
    public String getTags() {
        return this.tags;
    }
    /**
     * Set タグ.
     * @param tags タグ
     * @return this object     */
    public GenMailHookConditionsEntity setTags(String tags) {
        this.tags = tags;
        return this;
    }

    /**
     * Get 公開先.
     * @return 公開先
     */
    public String getViewers() {
        return this.viewers;
    }
    /**
     * Set 公開先.
     * @param viewers 公開先
     * @return this object     */
    public GenMailHookConditionsEntity setViewers(String viewers) {
        this.viewers = viewers;
        return this;
    }

    /**
     * Get 共同編集者.
     * @return 共同編集者
     */
    public String getEditors() {
        return this.editors;
    }
    /**
     * Set 共同編集者.
     * @param editors 共同編集者
     * @return this object     */
    public GenMailHookConditionsEntity setEditors(String editors) {
        this.editors = editors;
        return this;
    }

    /**
     * Get 投稿者の制限.
     * @return 投稿者の制限
     */
    public Integer getPostLimit() {
        return this.postLimit;
    }
    /**
     * Set 投稿者の制限.
     * @param postLimit 投稿者の制限
     * @return this object     */
    public GenMailHookConditionsEntity setPostLimit(Integer postLimit) {
        this.postLimit = postLimit;
        return this;
    }

    /**
     * Get 制限のパラメータ.
     * @return 制限のパラメータ
     */
    public String getLimitParam() {
        return this.limitParam;
    }
    /**
     * Set 制限のパラメータ.
     * @param limitParam 制限のパラメータ
     * @return this object     */
    public GenMailHookConditionsEntity setLimitParam(String limitParam) {
        this.limitParam = limitParam;
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
    public GenMailHookConditionsEntity setInsertUser(Integer insertUser) {
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
    public GenMailHookConditionsEntity setInsertDatetime(Timestamp insertDatetime) {
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
    public GenMailHookConditionsEntity setUpdateUser(Integer updateUser) {
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
    public GenMailHookConditionsEntity setUpdateDatetime(Timestamp updateDatetime) {
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
    public GenMailHookConditionsEntity setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
        return this;
    }

    /**
     * Get key values 
     * @return values 
     */
    public Object[] getKeyValues() {
        Object[] keyValues = new Object[2];
        keyValues[0] = this.conditionNo;
        keyValues[1] = this.hookId;
        return keyValues;
    }
    /**
     * Set key values 
     * @param conditionNo CONDITION_NO
     * @param hookId HOOK_ID
     */
    public void setKeyValues(Integer conditionNo, Integer hookId) {
        this.conditionNo = conditionNo;
        this.hookId = hookId;
    }
    /**
     * compare on key 
     * @param entity entity 
     * @return result 
     */
    public boolean equalsOnKey(GenMailHookConditionsEntity entity) {
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
        builder.append("conditionKind = ").append(conditionKind).append("\n");
        builder.append("condition = ").append(condition).append("\n");
        builder.append("processUser = ").append(processUser).append("\n");
        builder.append("processUserKind = ").append(processUserKind).append("\n");
        builder.append("publicFlag = ").append(publicFlag).append("\n");
        builder.append("tags = ").append(tags).append("\n");
        builder.append("viewers = ").append(viewers).append("\n");
        builder.append("editors = ").append(editors).append("\n");
        builder.append("postLimit = ").append(postLimit).append("\n");
        builder.append("limitParam = ").append(limitParam).append("\n");
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
        validator = ValidatorFactory.getInstance(Validator.REQUIRED);
        error = validator.validate(this.processUser, convLabelName("Process User"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.INTEGER);
        error = validator.validate(this.processUser, convLabelName("Process User"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.REQUIRED);
        error = validator.validate(this.processUserKind, convLabelName("Process User Kind"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.INTEGER);
        error = validator.validate(this.processUserKind, convLabelName("Process User Kind"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.REQUIRED);
        error = validator.validate(this.publicFlag, convLabelName("Public Flag"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.INTEGER);
        error = validator.validate(this.publicFlag, convLabelName("Public Flag"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.INTEGER);
        error = validator.validate(this.postLimit, convLabelName("Post Limit"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(this.limitParam, convLabelName("Limit Param"), 256);
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
        validator = ValidatorFactory.getInstance(Validator.REQUIRED);
        error = validator.validate(values.get("processUser"), convLabelName("Process User"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.INTEGER);
        error = validator.validate(values.get("processUser"), convLabelName("Process User"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.REQUIRED);
        error = validator.validate(values.get("processUserKind"), convLabelName("Process User Kind"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.INTEGER);
        error = validator.validate(values.get("processUserKind"), convLabelName("Process User Kind"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.REQUIRED);
        error = validator.validate(values.get("publicFlag"), convLabelName("Public Flag"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.INTEGER);
        error = validator.validate(values.get("publicFlag"), convLabelName("Public Flag"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.INTEGER);
        error = validator.validate(values.get("postLimit"), convLabelName("Post Limit"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(values.get("limitParam"), convLabelName("Limit Param"), 256);
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
