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
 * メールテンプレート
 */
@DI(instance = Instance.Prototype)
public class GenMailTemplatesEntity implements Serializable {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * Get instance from DI container.
     * @return instance
     */
    public static GenMailTemplatesEntity get() {
        return Container.getComp(GenMailTemplatesEntity.class);
    }

    /**
     * Constructor.
     */
    public GenMailTemplatesEntity() {
        super();
    }

    /**
     * Constructor
     * @param templateId テンプレートID
     */

    public GenMailTemplatesEntity(String templateId) {
        super();
        this.templateId = templateId;
    }
    /** テンプレートID */
    private String templateId;
    /** テンプレートタイトル */
    private String templateTitle;
    /** 説明文 */
    private String description;
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
     * Get テンプレートID.
     * @return テンプレートID
     */
    public String getTemplateId() {
        return this.templateId;
    }
    /**
     * Set テンプレートID.
     * @param templateId テンプレートID
     * @return this object     */
    public GenMailTemplatesEntity setTemplateId(String templateId) {
        this.templateId = templateId;
        return this;
    }

    /**
     * Get テンプレートタイトル.
     * @return テンプレートタイトル
     */
    public String getTemplateTitle() {
        return this.templateTitle;
    }
    /**
     * Set テンプレートタイトル.
     * @param templateTitle テンプレートタイトル
     * @return this object     */
    public GenMailTemplatesEntity setTemplateTitle(String templateTitle) {
        this.templateTitle = templateTitle;
        return this;
    }

    /**
     * Get 説明文.
     * @return 説明文
     */
    public String getDescription() {
        return this.description;
    }
    /**
     * Set 説明文.
     * @param description 説明文
     * @return this object     */
    public GenMailTemplatesEntity setDescription(String description) {
        this.description = description;
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
    public GenMailTemplatesEntity setInsertUser(Integer insertUser) {
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
    public GenMailTemplatesEntity setInsertDatetime(Timestamp insertDatetime) {
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
    public GenMailTemplatesEntity setUpdateUser(Integer updateUser) {
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
    public GenMailTemplatesEntity setUpdateDatetime(Timestamp updateDatetime) {
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
    public GenMailTemplatesEntity setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
        return this;
    }

    /**
     * Get key values 
     * @return values 
     */
    public Object[] getKeyValues() {
        Object[] keyValues = new Object[1];
        keyValues[0] = this.templateId;
        return keyValues;
    }
    /**
     * Set key values 
     * @param templateId テンプレートID
     */
    public void setKeyValues(String templateId) {
        this.templateId = templateId;
    }
    /**
     * compare on key 
     * @param entity entity 
     * @return result 
     */
    public boolean equalsOnKey(GenMailTemplatesEntity entity) {
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
        builder.append("templateId = ").append(templateId).append("\n");
        builder.append("templateTitle = ").append(templateTitle).append("\n");
        builder.append("description = ").append(description).append("\n");
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
        error = validator.validate(this.templateId, convLabelName("Template Id"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(this.templateId, convLabelName("Template Id"), 32);
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.REQUIRED);
        error = validator.validate(this.templateTitle, convLabelName("Template Title"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(this.templateTitle, convLabelName("Template Title"), 128);
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
        error = validator.validate(values.get("templateId"), convLabelName("Template Id"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(values.get("templateId"), convLabelName("Template Id"), 32);
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.REQUIRED);
        error = validator.validate(values.get("templateTitle"), convLabelName("Template Title"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(values.get("templateTitle"), convLabelName("Template Title"), 128);
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
