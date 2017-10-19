package org.support.project.ormapping.gen.entity.gen;

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
 * 組織
 */
@DI(instance = Instance.Prototype)
public class GenSectionEntity implements Serializable {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * インスタンス取得 AOPに対応
     * 
     * @return インスタンス
     */
    public static GenSectionEntity get() {
        return Container.getComp(GenSectionEntity.class);
    }

    /**
     * コンストラクタ
     */
    public GenSectionEntity() {
        super();
    }

    /**
     * コンストラクタ
     * 
     * @param sectionCode
     *            組織コード
     */

    public GenSectionEntity(String sectionCode) {
        super();
        this.sectionCode = sectionCode;
    }

    /** 組織コード */
    private String sectionCode;
    /** 組織名 */
    private String sectionName;
    /** 組織略称 */
    private String sectionSynonym;
    /** 会社コード */
    private String campanyCode;
    /** 登録ユーザ */
    private String insertUser;
    /** 登録日時 */
    private Timestamp insertDatetime;
    /** 更新ユーザ */
    private String updateUser;
    /** 更新日時 */
    private Timestamp updateDatetime;

    /**
     * 組織コード を取得する
     */
    public String getSectionCode() {
        return this.sectionCode;
    }

    /**
     * 組織コード を設定する
     * 
     * @param sectionCode
     *            組織コード
     */
    public GenSectionEntity setSectionCode(String sectionCode) {
        this.sectionCode = sectionCode;
        return this;
    }

    /**
     * 組織名 を取得する
     */
    public String getSectionName() {
        return this.sectionName;
    }

    /**
     * 組織名 を設定する
     * 
     * @param sectionName
     *            組織名
     */
    public GenSectionEntity setSectionName(String sectionName) {
        this.sectionName = sectionName;
        return this;
    }

    /**
     * 組織略称 を取得する
     */
    public String getSectionSynonym() {
        return this.sectionSynonym;
    }

    /**
     * 組織略称 を設定する
     * 
     * @param sectionSynonym
     *            組織略称
     */
    public GenSectionEntity setSectionSynonym(String sectionSynonym) {
        this.sectionSynonym = sectionSynonym;
        return this;
    }

    /**
     * 会社コード を取得する
     */
    public String getCampanyCode() {
        return this.campanyCode;
    }

    /**
     * 会社コード を設定する
     * 
     * @param campanyCode
     *            会社コード
     */
    public GenSectionEntity setCampanyCode(String campanyCode) {
        this.campanyCode = campanyCode;
        return this;
    }

    /**
     * 登録ユーザ を取得する
     */
    public String getInsertUser() {
        return this.insertUser;
    }

    /**
     * 登録ユーザ を設定する
     * 
     * @param insertUser
     *            登録ユーザ
     */
    public GenSectionEntity setInsertUser(String insertUser) {
        this.insertUser = insertUser;
        return this;
    }

    /**
     * 登録日時 を取得する
     */
    public Timestamp getInsertDatetime() {
        return this.insertDatetime;
    }

    /**
     * 登録日時 を設定する
     * 
     * @param insertDatetime
     *            登録日時
     */
    public GenSectionEntity setInsertDatetime(Timestamp insertDatetime) {
        this.insertDatetime = insertDatetime;
        return this;
    }

    /**
     * 更新ユーザ を取得する
     */
    public String getUpdateUser() {
        return this.updateUser;
    }

    /**
     * 更新ユーザ を設定する
     * 
     * @param updateUser
     *            更新ユーザ
     */
    public GenSectionEntity setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
        return this;
    }

    /**
     * 更新日時 を取得する
     */
    public Timestamp getUpdateDatetime() {
        return this.updateDatetime;
    }

    /**
     * 更新日時 を設定する
     * 
     * @param updateDatetime
     *            更新日時
     */
    public GenSectionEntity setUpdateDatetime(Timestamp updateDatetime) {
        this.updateDatetime = updateDatetime;
        return this;
    }

    /**
     * キーの値を取得
     */
    public Object[] getKeyValues() {
        Object[] keyValues = new Object[1];
        keyValues[0] = this.sectionCode;
        return keyValues;
    }

    /**
     * キーの値を設定
     * 
     * @param sectionCode
     *            組織コード
     */
    public void setKeyValues(String sectionCode) {
        this.sectionCode = sectionCode;
    }

    /**
     * キーで比較
     */
    public boolean equalsOnKey(GenSectionEntity entity) {
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
     */
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("sectionCode = ").append(sectionCode).append("\n");
        builder.append("sectionName = ").append(sectionName).append("\n");
        builder.append("sectionSynonym = ").append(sectionSynonym).append("\n");
        builder.append("campanyCode = ").append(campanyCode).append("\n");
        builder.append("insertUser = ").append(insertUser).append("\n");
        builder.append("insertDatetime = ").append(insertDatetime).append("\n");
        builder.append("updateUser = ").append(updateUser).append("\n");
        builder.append("updateDatetime = ").append(updateDatetime).append("\n");
        return builder.toString();
    }

    /**
     * 表示用の名称を変換
     */
    protected String convLabelName(String label) {
        return label;
    }

    /**
     * validate
     */
    public List<ValidateError> validate() {
        List<ValidateError> errors = new ArrayList<>();
        Validator validator;
        ValidateError error;
        validator = ValidatorFactory.getInstance(Validator.REQUIRED);
        error = validator.validate(this.sectionCode, convLabelName("Section Code"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(this.sectionCode, convLabelName("Section Code"), 10);
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(this.sectionName, convLabelName("Section Name"), 100);
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(this.sectionSynonym, convLabelName("Section Synonym"), 20);
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.REQUIRED);
        error = validator.validate(this.campanyCode, convLabelName("Campany Code"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(this.campanyCode, convLabelName("Campany Code"), 10);
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(this.insertUser, convLabelName("Insert User"), 15);
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(this.updateUser, convLabelName("Update User"), 15);
        if (error != null) {
            errors.add(error);
        }
        return errors;
    }

    /**
     * validate
     */
    public List<ValidateError> validate(Map<String, String> values) {
        List<ValidateError> errors = new ArrayList<>();
        Validator validator;
        ValidateError error;
        validator = ValidatorFactory.getInstance(Validator.REQUIRED);
        error = validator.validate(values.get("sectionCode"), convLabelName("Section Code"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(values.get("sectionCode"), convLabelName("Section Code"), 10);
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(values.get("sectionName"), convLabelName("Section Name"), 100);
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(values.get("sectionSynonym"), convLabelName("Section Synonym"), 20);
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.REQUIRED);
        error = validator.validate(values.get("campanyCode"), convLabelName("Campany Code"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(values.get("campanyCode"), convLabelName("Campany Code"), 10);
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(values.get("insertUser"), convLabelName("Insert User"), 15);
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(values.get("updateUser"), convLabelName("Update User"), 15);
        if (error != null) {
            errors.add(error);
        }
        return errors;
    }

}
