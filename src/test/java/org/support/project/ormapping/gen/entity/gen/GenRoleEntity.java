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
 * 役割
 */
@DI(instance = Instance.Prototype)
public class GenRoleEntity implements Serializable {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * インスタンス取得 AOPに対応
     * 
     * @return インスタンス
     */
    public static GenRoleEntity get() {
        return Container.getComp(GenRoleEntity.class);
    }

    /**
     * コンストラクタ
     */
    public GenRoleEntity() {
        super();
    }

    /**
     * コンストラクタ
     * 
     * @param roleId
     *            役割ID
     */

    public GenRoleEntity(String roleId) {
        super();
        this.roleId = roleId;
    }

    /** 役割ID */
    private String roleId;
    /** 役割名 */
    private String roleName;
    /** 登録ユーザ */
    private String insertUser;
    /** 登録日時 */
    private Timestamp insertDatetime;
    /** 更新ユーザ */
    private String updateUser;
    /** 更新日時 */
    private Timestamp updateDatetime;

    /**
     * 役割ID を取得する
     */
    public String getRoleId() {
        return this.roleId;
    }

    /**
     * 役割ID を設定する
     * 
     * @param roleId
     *            役割ID
     */
    public GenRoleEntity setRoleId(String roleId) {
        this.roleId = roleId;
        return this;
    }

    /**
     * 役割名 を取得する
     */
    public String getRoleName() {
        return this.roleName;
    }

    /**
     * 役割名 を設定する
     * 
     * @param roleName
     *            役割名
     */
    public GenRoleEntity setRoleName(String roleName) {
        this.roleName = roleName;
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
    public GenRoleEntity setInsertUser(String insertUser) {
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
    public GenRoleEntity setInsertDatetime(Timestamp insertDatetime) {
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
    public GenRoleEntity setUpdateUser(String updateUser) {
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
    public GenRoleEntity setUpdateDatetime(Timestamp updateDatetime) {
        this.updateDatetime = updateDatetime;
        return this;
    }

    /**
     * キーの値を取得
     */
    public Object[] getKeyValues() {
        Object[] keyValues = new Object[1];
        keyValues[0] = this.roleId;
        return keyValues;
    }

    /**
     * キーの値を設定
     * 
     * @param roleId
     *            役割ID
     */
    public void setKeyValues(String roleId) {
        this.roleId = roleId;
    }

    /**
     * キーで比較
     */
    public boolean equalsOnKey(GenRoleEntity entity) {
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
        builder.append("roleId = ").append(roleId).append("\n");
        builder.append("roleName = ").append(roleName).append("\n");
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
        error = validator.validate(this.roleId, convLabelName("Role Id"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(this.roleId, convLabelName("Role Id"), 20);
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.REQUIRED);
        error = validator.validate(this.roleName, convLabelName("Role Name"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(this.roleName, convLabelName("Role Name"), 50);
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
        error = validator.validate(values.get("roleId"), convLabelName("Role Id"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(values.get("roleId"), convLabelName("Role Id"), 20);
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.REQUIRED);
        error = validator.validate(values.get("roleName"), convLabelName("Role Name"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(values.get("roleName"), convLabelName("Role Name"), 50);
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
