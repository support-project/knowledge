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
 * ナレッジのポイント獲得履歴
 */
@DI(instance = Instance.Prototype)
public class GenPointKnowledgeHistoriesEntity implements Serializable {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * Get instance from DI container.
     * @return instance
     */
    public static GenPointKnowledgeHistoriesEntity get() {
        return Container.getComp(GenPointKnowledgeHistoriesEntity.class);
    }

    /**
     * Constructor.
     */
    public GenPointKnowledgeHistoriesEntity() {
        super();
    }

    /**
     * Constructor
     * @param historyNo 履歴番号
     * @param knowledgeId ナレッジID
     */

    public GenPointKnowledgeHistoriesEntity(Long historyNo, Long knowledgeId) {
        super();
        this.historyNo = historyNo;
        this.knowledgeId = knowledgeId;
    }
    /** ナレッジID */
    private Long knowledgeId;
    /** 履歴番号 */
    private Long historyNo;
    /** アクティビティ番号 */
    private Long activityNo;
    /** 獲得のタイプ */
    private Integer type;
    /** 獲得ポイント */
    private Integer point;
    /** 獲得前ポイント */
    private Integer beforeTotal;
    /** トータルポイント */
    private Integer total;
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
    public GenPointKnowledgeHistoriesEntity setKnowledgeId(Long knowledgeId) {
        this.knowledgeId = knowledgeId;
        return this;
    }

    /**
     * Get 履歴番号.
     * @return 履歴番号
     */
    public Long getHistoryNo() {
        return this.historyNo;
    }
    /**
     * Set 履歴番号.
     * @param historyNo 履歴番号
     * @return this object     */
    public GenPointKnowledgeHistoriesEntity setHistoryNo(Long historyNo) {
        this.historyNo = historyNo;
        return this;
    }

    /**
     * Get アクティビティ番号.
     * @return アクティビティ番号
     */
    public Long getActivityNo() {
        return this.activityNo;
    }
    /**
     * Set アクティビティ番号.
     * @param activityNo アクティビティ番号
     * @return this object     */
    public GenPointKnowledgeHistoriesEntity setActivityNo(Long activityNo) {
        this.activityNo = activityNo;
        return this;
    }

    /**
     * Get 獲得のタイプ.
     * @return 獲得のタイプ
     */
    public Integer getType() {
        return this.type;
    }
    /**
     * Set 獲得のタイプ.
     * @param type 獲得のタイプ
     * @return this object     */
    public GenPointKnowledgeHistoriesEntity setType(Integer type) {
        this.type = type;
        return this;
    }

    /**
     * Get 獲得ポイント.
     * @return 獲得ポイント
     */
    public Integer getPoint() {
        return this.point;
    }
    /**
     * Set 獲得ポイント.
     * @param point 獲得ポイント
     * @return this object     */
    public GenPointKnowledgeHistoriesEntity setPoint(Integer point) {
        this.point = point;
        return this;
    }

    /**
     * Get 獲得前ポイント.
     * @return 獲得前ポイント
     */
    public Integer getBeforeTotal() {
        return this.beforeTotal;
    }
    /**
     * Set 獲得前ポイント.
     * @param beforeTotal 獲得前ポイント
     * @return this object     */
    public GenPointKnowledgeHistoriesEntity setBeforeTotal(Integer beforeTotal) {
        this.beforeTotal = beforeTotal;
        return this;
    }

    /**
     * Get トータルポイント.
     * @return トータルポイント
     */
    public Integer getTotal() {
        return this.total;
    }
    /**
     * Set トータルポイント.
     * @param total トータルポイント
     * @return this object     */
    public GenPointKnowledgeHistoriesEntity setTotal(Integer total) {
        this.total = total;
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
    public GenPointKnowledgeHistoriesEntity setInsertUser(Integer insertUser) {
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
    public GenPointKnowledgeHistoriesEntity setInsertDatetime(Timestamp insertDatetime) {
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
    public GenPointKnowledgeHistoriesEntity setUpdateUser(Integer updateUser) {
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
    public GenPointKnowledgeHistoriesEntity setUpdateDatetime(Timestamp updateDatetime) {
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
    public GenPointKnowledgeHistoriesEntity setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
        return this;
    }

    /**
     * Get key values 
     * @return values 
     */
    public Object[] getKeyValues() {
        Object[] keyValues = new Object[2];
        keyValues[0] = this.historyNo;
        keyValues[1] = this.knowledgeId;
        return keyValues;
    }
    /**
     * Set key values 
     * @param historyNo 履歴番号
     * @param knowledgeId ナレッジID
     */
    public void setKeyValues(Long historyNo, Long knowledgeId) {
        this.historyNo = historyNo;
        this.knowledgeId = knowledgeId;
    }
    /**
     * compare on key 
     * @param entity entity 
     * @return result 
     */
    public boolean equalsOnKey(GenPointKnowledgeHistoriesEntity entity) {
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
        builder.append("historyNo = ").append(historyNo).append("\n");
        builder.append("knowledgeId = ").append(knowledgeId).append("\n");
        builder.append("activityNo = ").append(activityNo).append("\n");
        builder.append("type = ").append(type).append("\n");
        builder.append("point = ").append(point).append("\n");
        builder.append("beforeTotal = ").append(beforeTotal).append("\n");
        builder.append("total = ").append(total).append("\n");
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
        error = validator.validate(this.historyNo, convLabelName("History No"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.REQUIRED);
        error = validator.validate(this.activityNo, convLabelName("Activity No"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.REQUIRED);
        error = validator.validate(this.type, convLabelName("Type"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.INTEGER);
        error = validator.validate(this.type, convLabelName("Type"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.REQUIRED);
        error = validator.validate(this.point, convLabelName("Point"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.INTEGER);
        error = validator.validate(this.point, convLabelName("Point"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.REQUIRED);
        error = validator.validate(this.beforeTotal, convLabelName("Before Total"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.INTEGER);
        error = validator.validate(this.beforeTotal, convLabelName("Before Total"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.REQUIRED);
        error = validator.validate(this.total, convLabelName("Total"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.INTEGER);
        error = validator.validate(this.total, convLabelName("Total"));
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
        error = validator.validate(values.get("historyNo"), convLabelName("History No"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.REQUIRED);
        error = validator.validate(values.get("activityNo"), convLabelName("Activity No"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.REQUIRED);
        error = validator.validate(values.get("type"), convLabelName("Type"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.INTEGER);
        error = validator.validate(values.get("type"), convLabelName("Type"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.REQUIRED);
        error = validator.validate(values.get("point"), convLabelName("Point"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.INTEGER);
        error = validator.validate(values.get("point"), convLabelName("Point"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.REQUIRED);
        error = validator.validate(values.get("beforeTotal"), convLabelName("Before Total"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.INTEGER);
        error = validator.validate(values.get("beforeTotal"), convLabelName("Before Total"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.REQUIRED);
        error = validator.validate(values.get("total"), convLabelName("Total"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.INTEGER);
        error = validator.validate(values.get("total"), convLabelName("Total"));
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
