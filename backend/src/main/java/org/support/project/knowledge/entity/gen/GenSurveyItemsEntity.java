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
 * アンケート項目
 */
@DI(instance = Instance.Prototype)
public class GenSurveyItemsEntity implements Serializable {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * Get instance from DI container.
     * @return instance
     */
    public static GenSurveyItemsEntity get() {
        return Container.getComp(GenSurveyItemsEntity.class);
    }

    /**
     * Constructor.
     */
    public GenSurveyItemsEntity() {
        super();
    }

    /**
     * Constructor
     * @param itemNo 項目NO
     * @param knowledgeId ナレッジID
     */

    public GenSurveyItemsEntity(Integer itemNo, Long knowledgeId) {
        super();
        this.itemNo = itemNo;
        this.knowledgeId = knowledgeId;
    }
    /** ナレッジID */
    private Long knowledgeId;
    /** 項目NO */
    private Integer itemNo;
    /** 項目名 */
    private String itemName;
    /** 項目の種類 */
    private Integer itemType;
    /** 説明 */
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
     * Get ナレッジID.
     * @return ナレッジID
     */
    public Long getKnowledgeId() {
        return this.knowledgeId;
    }
    /**
     * Set ナレッジID.
     * @param knowledgeId ナレッジID
     * @return this object     */
    public GenSurveyItemsEntity setKnowledgeId(Long knowledgeId) {
        this.knowledgeId = knowledgeId;
        return this;
    }

    /**
     * Get 項目NO.
     * @return 項目NO
     */
    public Integer getItemNo() {
        return this.itemNo;
    }
    /**
     * Set 項目NO.
     * @param itemNo 項目NO
     * @return this object     */
    public GenSurveyItemsEntity setItemNo(Integer itemNo) {
        this.itemNo = itemNo;
        return this;
    }

    /**
     * Get 項目名.
     * @return 項目名
     */
    public String getItemName() {
        return this.itemName;
    }
    /**
     * Set 項目名.
     * @param itemName 項目名
     * @return this object     */
    public GenSurveyItemsEntity setItemName(String itemName) {
        this.itemName = itemName;
        return this;
    }

    /**
     * Get 項目の種類.
     * @return 項目の種類
     */
    public Integer getItemType() {
        return this.itemType;
    }
    /**
     * Set 項目の種類.
     * @param itemType 項目の種類
     * @return this object     */
    public GenSurveyItemsEntity setItemType(Integer itemType) {
        this.itemType = itemType;
        return this;
    }

    /**
     * Get 説明.
     * @return 説明
     */
    public String getDescription() {
        return this.description;
    }
    /**
     * Set 説明.
     * @param description 説明
     * @return this object     */
    public GenSurveyItemsEntity setDescription(String description) {
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
    public GenSurveyItemsEntity setInsertUser(Integer insertUser) {
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
    public GenSurveyItemsEntity setInsertDatetime(Timestamp insertDatetime) {
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
    public GenSurveyItemsEntity setUpdateUser(Integer updateUser) {
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
    public GenSurveyItemsEntity setUpdateDatetime(Timestamp updateDatetime) {
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
    public GenSurveyItemsEntity setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
        return this;
    }

    /**
     * Get key values 
     * @return values 
     */
    public Object[] getKeyValues() {
        Object[] keyValues = new Object[2];
        keyValues[0] = this.itemNo;
        keyValues[1] = this.knowledgeId;
        return keyValues;
    }
    /**
     * Set key values 
     * @param itemNo 項目NO
     * @param knowledgeId ナレッジID
     */
    public void setKeyValues(Integer itemNo, Long knowledgeId) {
        this.itemNo = itemNo;
        this.knowledgeId = knowledgeId;
    }
    /**
     * compare on key 
     * @param entity entity 
     * @return result 
     */
    public boolean equalsOnKey(GenSurveyItemsEntity entity) {
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
        builder.append("itemNo = ").append(itemNo).append("\n");
        builder.append("knowledgeId = ").append(knowledgeId).append("\n");
        builder.append("itemName = ").append(itemName).append("\n");
        builder.append("itemType = ").append(itemType).append("\n");
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
        error = validator.validate(this.knowledgeId, convLabelName("Knowledge Id"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.REQUIRED);
        error = validator.validate(this.itemNo, convLabelName("Item No"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.INTEGER);
        error = validator.validate(this.itemNo, convLabelName("Item No"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.REQUIRED);
        error = validator.validate(this.itemName, convLabelName("Item Name"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(this.itemName, convLabelName("Item Name"), 1024);
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.REQUIRED);
        error = validator.validate(this.itemType, convLabelName("Item Type"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.INTEGER);
        error = validator.validate(this.itemType, convLabelName("Item Type"));
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
        error = validator.validate(values.get("knowledgeId"), convLabelName("Knowledge Id"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.REQUIRED);
        error = validator.validate(values.get("itemNo"), convLabelName("Item No"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.INTEGER);
        error = validator.validate(values.get("itemNo"), convLabelName("Item No"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.REQUIRED);
        error = validator.validate(values.get("itemName"), convLabelName("Item Name"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(values.get("itemName"), convLabelName("Item Name"), 1024);
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.REQUIRED);
        error = validator.validate(values.get("itemType"), convLabelName("Item Type"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.INTEGER);
        error = validator.validate(values.get("itemType"), convLabelName("Item Type"));
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
