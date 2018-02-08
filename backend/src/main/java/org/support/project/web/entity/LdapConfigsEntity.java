package org.support.project.web.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.support.project.common.bean.ValidateError;
import org.support.project.common.validate.Validator;
import org.support.project.common.validate.ValidatorFactory;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.web.entity.gen.GenLdapConfigsEntity;

/**
 * LDAP認証設定
 */
@DI(instance = Instance.Prototype)
public class LdapConfigsEntity extends GenLdapConfigsEntity {
    public static final int AUTH_TYPE_DB = 0; // DB 認証のみ
    public static final int AUTH_TYPE_LDAP = 1; // Ldap認証のみ
    public static final int AUTH_TYPE_BOTH = 2; // LdapとDB認証両方が有効
    public static final int AUTH_TYPE_LDAP_2 = 11; // Ldap認証のみ(Ldap認証の認証方式を検索＆認証に変更）
    public static final int AUTH_TYPE_BOTH_2 = 12; // LdapとDB認証両方が有効(Ldap認証の認証方式を検索＆認証に変更）

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    public boolean isLdapLoginAble() {
        if (getAuthType().intValue() == LdapConfigsEntity.AUTH_TYPE_LDAP || getAuthType().intValue() == LdapConfigsEntity.AUTH_TYPE_BOTH
                || getAuthType().intValue() == LdapConfigsEntity.AUTH_TYPE_LDAP_2 || getAuthType().intValue() == LdapConfigsEntity.AUTH_TYPE_BOTH_2) {
            return true;
        }
        return false;
    }

    public boolean isLdapLoginOnly() {
        if (getAuthType().intValue() == LdapConfigsEntity.AUTH_TYPE_LDAP || getAuthType().intValue() == LdapConfigsEntity.AUTH_TYPE_LDAP_2) {
            return true;
        }
        return false;
    }

    /**
     * インスタンス取得 AOPに対応
     * 
     * @return インスタンス
     */
    public static LdapConfigsEntity get() {
        return Container.getComp(LdapConfigsEntity.class);
    }

    /**
     * コンストラクタ
     */
    public LdapConfigsEntity() {
        super();
    }

    /**
     * コンストラクタ
     * 
     * @param systemName システム名
     */

    public LdapConfigsEntity(String systemName) {
        super(systemName);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.support.project.web.entity.gen.GenLdapConfigsEntity#validate(java.util.Map)
     */
    @Override
    public List<ValidateError> validate(Map<String, String> values) {
        String configType = values.get("configType");
        List<ValidateError> errors;
        if ("config2".equals(configType)) {
            errors = new ArrayList<ValidateError>();
            Validator validator = ValidatorFactory.getInstance(Validator.REQUIRED);
            ValidateError error = validator.validate(values.get("host"), convLabelName("Host"));
            if (error != null) {
                errors.add(error);
            }
            validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
            error = validator.validate(values.get("host2"), convLabelName("Host"), 256);
            if (error != null) {
                errors.add(error);
            }
            validator = ValidatorFactory.getInstance(Validator.REQUIRED);
            error = validator.validate(values.get("port2"), convLabelName("Port"));
            if (error != null) {
                errors.add(error);
            }
            validator = ValidatorFactory.getInstance(Validator.INTEGER);
            error = validator.validate(values.get("port2"), convLabelName("Port"));
            if (error != null) {
                errors.add(error);
            }
            validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
            error = validator.validate(values.get("bindDn2"), convLabelName("Bind Dn"), 256);
            if (error != null) {
                errors.add(error);
            }
            validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
            error = validator.validate(values.get("bindPassword2"), convLabelName("Bind Password"), 1048);
            if (error != null) {
                errors.add(error);
            }
            validator = ValidatorFactory.getInstance(Validator.REQUIRED);
            error = validator.validate(values.get("baseDn2"), convLabelName("Base Dn"));
            if (error != null) {
                errors.add(error);
            }
            validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
            error = validator.validate(values.get("baseDn2"), convLabelName("Base Dn"), 256);
            if (error != null) {
                errors.add(error);
            }
            validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
            error = validator.validate(values.get("filter2"), convLabelName("Filter"), 256);
            if (error != null) {
                errors.add(error);
            }
            validator = ValidatorFactory.getInstance(Validator.REQUIRED);
            error = validator.validate(values.get("idAttr2"), convLabelName("Id Attr"));
            if (error != null) {
                errors.add(error);
            }
            validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
            error = validator.validate(values.get("idAttr2"), convLabelName("Id Attr"), 256);
            if (error != null) {
                errors.add(error);
            }
            validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
            error = validator.validate(values.get("nameAttr2"), convLabelName("Name Attr"), 256);
            if (error != null) {
                errors.add(error);
            }
            validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
            error = validator.validate(values.get("mailAttr2"), convLabelName("Mail Attr"), 256);
            if (error != null) {
                errors.add(error);
            }
            validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
            error = validator.validate(values.get("adminCheckFilter2"), convLabelName("Admin Check Filter"), 256);
            if (error != null) {
                errors.add(error);
            }
            validator = ValidatorFactory.getInstance(Validator.REQUIRED);
            error = validator.validate(values.get("authType"), convLabelName("Auth Type"));
            if (error != null) {
                errors.add(error);
            }
            validator = ValidatorFactory.getInstance(Validator.INTEGER);
            error = validator.validate(values.get("authType"), convLabelName("Auth Type"));
            if (error != null) {
                errors.add(error);
            }
        } else {
            errors = super.validate(values);
        }
        return errors;
    }

}
