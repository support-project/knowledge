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
 * 通知設定
 */
@DI(instance = Instance.Prototype)
public class GenNotifyConfigsEntity implements Serializable {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * Get instance from DI container.
     * @return instance
     */
    public static GenNotifyConfigsEntity get() {
        return Container.getComp(GenNotifyConfigsEntity.class);
    }

    /**
     * Constructor.
     */
    public GenNotifyConfigsEntity() {
        super();
    }

    /**
     * Constructor
     * @param userId ユーザID
     */

    public GenNotifyConfigsEntity(Integer userId) {
        super();
        this.userId = userId;
    }
    /** ユーザID */
    private Integer userId;
    /** メール通知する */
    private Integer notifyMail;
    /** デスクトップ通知する */
    private Integer notifyDesktop;
    /** 自分が登録した投稿にコメントが登録されたら通知 */
    private Integer myItemComment;
    /** 自分が登録した投稿にいいねが追加されたら通知 */
    private Integer myItemLike;
    /** 自分が登録した投稿がストックされたら通知 */
    private Integer myItemStock;
    /** 自分宛の投稿が更新されたら通知 */
    private Integer toItemSave;
    /** 自分宛の投稿にコメントが登録されたら通知 */
    private Integer toItemComment;
    /** 自分宛の投稿で「公開」は除外 */
    private Integer toItemIgnorePublic;
    /** ストックしたナレッジが更新されたら通知 */
    private Integer stockItemSave;
    /** ストックしたナレッジにコメントが登録されたら通知 */
    private Integer stokeItemComment;
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
    public GenNotifyConfigsEntity setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    /**
     * Get メール通知する.
     * @return メール通知する
     */
    public Integer getNotifyMail() {
        return this.notifyMail;
    }
    /**
     * Set メール通知する.
     * @param notifyMail メール通知する
     * @return this object     */
    public GenNotifyConfigsEntity setNotifyMail(Integer notifyMail) {
        this.notifyMail = notifyMail;
        return this;
    }

    /**
     * Get デスクトップ通知する.
     * @return デスクトップ通知する
     */
    public Integer getNotifyDesktop() {
        return this.notifyDesktop;
    }
    /**
     * Set デスクトップ通知する.
     * @param notifyDesktop デスクトップ通知する
     * @return this object     */
    public GenNotifyConfigsEntity setNotifyDesktop(Integer notifyDesktop) {
        this.notifyDesktop = notifyDesktop;
        return this;
    }

    /**
     * Get 自分が登録した投稿にコメントが登録されたら通知.
     * @return 自分が登録した投稿にコメントが登録されたら通知
     */
    public Integer getMyItemComment() {
        return this.myItemComment;
    }
    /**
     * Set 自分が登録した投稿にコメントが登録されたら通知.
     * @param myItemComment 自分が登録した投稿にコメントが登録されたら通知
     * @return this object     */
    public GenNotifyConfigsEntity setMyItemComment(Integer myItemComment) {
        this.myItemComment = myItemComment;
        return this;
    }

    /**
     * Get 自分が登録した投稿にいいねが追加されたら通知.
     * @return 自分が登録した投稿にいいねが追加されたら通知
     */
    public Integer getMyItemLike() {
        return this.myItemLike;
    }
    /**
     * Set 自分が登録した投稿にいいねが追加されたら通知.
     * @param myItemLike 自分が登録した投稿にいいねが追加されたら通知
     * @return this object     */
    public GenNotifyConfigsEntity setMyItemLike(Integer myItemLike) {
        this.myItemLike = myItemLike;
        return this;
    }

    /**
     * Get 自分が登録した投稿がストックされたら通知.
     * @return 自分が登録した投稿がストックされたら通知
     */
    public Integer getMyItemStock() {
        return this.myItemStock;
    }
    /**
     * Set 自分が登録した投稿がストックされたら通知.
     * @param myItemStock 自分が登録した投稿がストックされたら通知
     * @return this object     */
    public GenNotifyConfigsEntity setMyItemStock(Integer myItemStock) {
        this.myItemStock = myItemStock;
        return this;
    }

    /**
     * Get 自分宛の投稿が更新されたら通知.
     * @return 自分宛の投稿が更新されたら通知
     */
    public Integer getToItemSave() {
        return this.toItemSave;
    }
    /**
     * Set 自分宛の投稿が更新されたら通知.
     * @param toItemSave 自分宛の投稿が更新されたら通知
     * @return this object     */
    public GenNotifyConfigsEntity setToItemSave(Integer toItemSave) {
        this.toItemSave = toItemSave;
        return this;
    }

    /**
     * Get 自分宛の投稿にコメントが登録されたら通知.
     * @return 自分宛の投稿にコメントが登録されたら通知
     */
    public Integer getToItemComment() {
        return this.toItemComment;
    }
    /**
     * Set 自分宛の投稿にコメントが登録されたら通知.
     * @param toItemComment 自分宛の投稿にコメントが登録されたら通知
     * @return this object     */
    public GenNotifyConfigsEntity setToItemComment(Integer toItemComment) {
        this.toItemComment = toItemComment;
        return this;
    }

    /**
     * Get 自分宛の投稿で「公開」は除外.
     * @return 自分宛の投稿で「公開」は除外
     */
    public Integer getToItemIgnorePublic() {
        return this.toItemIgnorePublic;
    }
    /**
     * Set 自分宛の投稿で「公開」は除外.
     * @param toItemIgnorePublic 自分宛の投稿で「公開」は除外
     * @return this object     */
    public GenNotifyConfigsEntity setToItemIgnorePublic(Integer toItemIgnorePublic) {
        this.toItemIgnorePublic = toItemIgnorePublic;
        return this;
    }

    /**
     * Get ストックしたナレッジが更新されたら通知.
     * @return ストックしたナレッジが更新されたら通知
     */
    public Integer getStockItemSave() {
        return this.stockItemSave;
    }
    /**
     * Set ストックしたナレッジが更新されたら通知.
     * @param stockItemSave ストックしたナレッジが更新されたら通知
     * @return this object     */
    public GenNotifyConfigsEntity setStockItemSave(Integer stockItemSave) {
        this.stockItemSave = stockItemSave;
        return this;
    }

    /**
     * Get ストックしたナレッジにコメントが登録されたら通知.
     * @return ストックしたナレッジにコメントが登録されたら通知
     */
    public Integer getStokeItemComment() {
        return this.stokeItemComment;
    }
    /**
     * Set ストックしたナレッジにコメントが登録されたら通知.
     * @param stokeItemComment ストックしたナレッジにコメントが登録されたら通知
     * @return this object     */
    public GenNotifyConfigsEntity setStokeItemComment(Integer stokeItemComment) {
        this.stokeItemComment = stokeItemComment;
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
    public GenNotifyConfigsEntity setInsertUser(Integer insertUser) {
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
    public GenNotifyConfigsEntity setInsertDatetime(Timestamp insertDatetime) {
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
    public GenNotifyConfigsEntity setUpdateUser(Integer updateUser) {
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
    public GenNotifyConfigsEntity setUpdateDatetime(Timestamp updateDatetime) {
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
    public GenNotifyConfigsEntity setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
        return this;
    }

    /**
     * Get key values 
     * @return values 
     */
    public Object[] getKeyValues() {
        Object[] keyValues = new Object[1];
        keyValues[0] = this.userId;
        return keyValues;
    }
    /**
     * Set key values 
     * @param userId ユーザID
     */
    public void setKeyValues(Integer userId) {
        this.userId = userId;
    }
    /**
     * compare on key 
     * @param entity entity 
     * @return result 
     */
    public boolean equalsOnKey(GenNotifyConfigsEntity entity) {
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
        builder.append("userId = ").append(userId).append("\n");
        builder.append("notifyMail = ").append(notifyMail).append("\n");
        builder.append("notifyDesktop = ").append(notifyDesktop).append("\n");
        builder.append("myItemComment = ").append(myItemComment).append("\n");
        builder.append("myItemLike = ").append(myItemLike).append("\n");
        builder.append("myItemStock = ").append(myItemStock).append("\n");
        builder.append("toItemSave = ").append(toItemSave).append("\n");
        builder.append("toItemComment = ").append(toItemComment).append("\n");
        builder.append("toItemIgnorePublic = ").append(toItemIgnorePublic).append("\n");
        builder.append("stockItemSave = ").append(stockItemSave).append("\n");
        builder.append("stokeItemComment = ").append(stokeItemComment).append("\n");
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
        validator = ValidatorFactory.getInstance(Validator.INTEGER);
        error = validator.validate(this.notifyMail, convLabelName("Notify Mail"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.INTEGER);
        error = validator.validate(this.notifyDesktop, convLabelName("Notify Desktop"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.INTEGER);
        error = validator.validate(this.myItemComment, convLabelName("My Item Comment"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.INTEGER);
        error = validator.validate(this.myItemLike, convLabelName("My Item Like"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.INTEGER);
        error = validator.validate(this.myItemStock, convLabelName("My Item Stock"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.INTEGER);
        error = validator.validate(this.toItemSave, convLabelName("To Item Save"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.INTEGER);
        error = validator.validate(this.toItemComment, convLabelName("To Item Comment"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.INTEGER);
        error = validator.validate(this.toItemIgnorePublic, convLabelName("To Item Ignore Public"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.INTEGER);
        error = validator.validate(this.stockItemSave, convLabelName("Stock Item Save"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.INTEGER);
        error = validator.validate(this.stokeItemComment, convLabelName("Stoke Item Comment"));
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
        validator = ValidatorFactory.getInstance(Validator.INTEGER);
        error = validator.validate(values.get("notifyMail"), convLabelName("Notify Mail"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.INTEGER);
        error = validator.validate(values.get("notifyDesktop"), convLabelName("Notify Desktop"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.INTEGER);
        error = validator.validate(values.get("myItemComment"), convLabelName("My Item Comment"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.INTEGER);
        error = validator.validate(values.get("myItemLike"), convLabelName("My Item Like"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.INTEGER);
        error = validator.validate(values.get("myItemStock"), convLabelName("My Item Stock"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.INTEGER);
        error = validator.validate(values.get("toItemSave"), convLabelName("To Item Save"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.INTEGER);
        error = validator.validate(values.get("toItemComment"), convLabelName("To Item Comment"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.INTEGER);
        error = validator.validate(values.get("toItemIgnorePublic"), convLabelName("To Item Ignore Public"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.INTEGER);
        error = validator.validate(values.get("stockItemSave"), convLabelName("Stock Item Save"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.INTEGER);
        error = validator.validate(values.get("stokeItemComment"), convLabelName("Stoke Item Comment"));
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
