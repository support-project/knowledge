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
 * LDAP認証設定
 */
@DI(instance = Instance.Prototype)
public class GenLdapConfigsEntity implements Serializable {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * Get instance from DI container.
     * @return instance
     */
    public static GenLdapConfigsEntity get() {
        return Container.getComp(GenLdapConfigsEntity.class);
    }

    /**
     * Constructor.
     */
    public GenLdapConfigsEntity() {
        super();
    }

    /**
     * Constructor
     * @param systemName 設定名
     */

    public GenLdapConfigsEntity(String systemName) {
        super();
        this.systemName = systemName;
    }
    /** 設定名 */
    private String systemName;
    /** DESCRIPTION */
    private String description;
    /** HOST */
    private String host;
    /** PORT */
    private Integer port;
    /** USE_SSL */
    private Integer useSsl;
    /** USE_TLS */
    private Integer useTls;
    /** BIND_DN */
    private String bindDn;
    /** BIND_PASSWORD */
    private String bindPassword;
    /** SALT */
    private String salt;
    /** BASE_DN */
    private String baseDn;
    /** FILTER */
    private String filter;
    /** ID_ATTR */
    private String idAttr;
    /** NAME_ATTR */
    private String nameAttr;
    /** MAIL_ATTR */
    private String mailAttr;
    /** ADMIN_CHECK_FILTER */
    private String adminCheckFilter;
    /** AUTH_TYPE	 0:DB認証,1:LDAP認証,2:DB認証+LDAP認証(LDAP優先) */
    private Integer authType;
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
     * Get 設定名.
     * @return 設定名
     */
    public String getSystemName() {
        return this.systemName;
    }
    /**
     * Set 設定名.
     * @param systemName 設定名
     * @return this object     */
    public GenLdapConfigsEntity setSystemName(String systemName) {
        this.systemName = systemName;
        return this;
    }

    /**
     * Get DESCRIPTION.
     * @return DESCRIPTION
     */
    public String getDescription() {
        return this.description;
    }
    /**
     * Set DESCRIPTION.
     * @param description DESCRIPTION
     * @return this object     */
    public GenLdapConfigsEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * Get HOST.
     * @return HOST
     */
    public String getHost() {
        return this.host;
    }
    /**
     * Set HOST.
     * @param host HOST
     * @return this object     */
    public GenLdapConfigsEntity setHost(String host) {
        this.host = host;
        return this;
    }

    /**
     * Get PORT.
     * @return PORT
     */
    public Integer getPort() {
        return this.port;
    }
    /**
     * Set PORT.
     * @param port PORT
     * @return this object     */
    public GenLdapConfigsEntity setPort(Integer port) {
        this.port = port;
        return this;
    }

    /**
     * Get USE_SSL.
     * @return USE_SSL
     */
    public Integer getUseSsl() {
        return this.useSsl;
    }
    /**
     * Set USE_SSL.
     * @param useSsl USE_SSL
     * @return this object     */
    public GenLdapConfigsEntity setUseSsl(Integer useSsl) {
        this.useSsl = useSsl;
        return this;
    }

    /**
     * Get USE_TLS.
     * @return USE_TLS
     */
    public Integer getUseTls() {
        return this.useTls;
    }
    /**
     * Set USE_TLS.
     * @param useTls USE_TLS
     * @return this object     */
    public GenLdapConfigsEntity setUseTls(Integer useTls) {
        this.useTls = useTls;
        return this;
    }

    /**
     * Get BIND_DN.
     * @return BIND_DN
     */
    public String getBindDn() {
        return this.bindDn;
    }
    /**
     * Set BIND_DN.
     * @param bindDn BIND_DN
     * @return this object     */
    public GenLdapConfigsEntity setBindDn(String bindDn) {
        this.bindDn = bindDn;
        return this;
    }

    /**
     * Get BIND_PASSWORD.
     * @return BIND_PASSWORD
     */
    public String getBindPassword() {
        return this.bindPassword;
    }
    /**
     * Set BIND_PASSWORD.
     * @param bindPassword BIND_PASSWORD
     * @return this object     */
    public GenLdapConfigsEntity setBindPassword(String bindPassword) {
        this.bindPassword = bindPassword;
        return this;
    }

    /**
     * Get SALT.
     * @return SALT
     */
    public String getSalt() {
        return this.salt;
    }
    /**
     * Set SALT.
     * @param salt SALT
     * @return this object     */
    public GenLdapConfigsEntity setSalt(String salt) {
        this.salt = salt;
        return this;
    }

    /**
     * Get BASE_DN.
     * @return BASE_DN
     */
    public String getBaseDn() {
        return this.baseDn;
    }
    /**
     * Set BASE_DN.
     * @param baseDn BASE_DN
     * @return this object     */
    public GenLdapConfigsEntity setBaseDn(String baseDn) {
        this.baseDn = baseDn;
        return this;
    }

    /**
     * Get FILTER.
     * @return FILTER
     */
    public String getFilter() {
        return this.filter;
    }
    /**
     * Set FILTER.
     * @param filter FILTER
     * @return this object     */
    public GenLdapConfigsEntity setFilter(String filter) {
        this.filter = filter;
        return this;
    }

    /**
     * Get ID_ATTR.
     * @return ID_ATTR
     */
    public String getIdAttr() {
        return this.idAttr;
    }
    /**
     * Set ID_ATTR.
     * @param idAttr ID_ATTR
     * @return this object     */
    public GenLdapConfigsEntity setIdAttr(String idAttr) {
        this.idAttr = idAttr;
        return this;
    }

    /**
     * Get NAME_ATTR.
     * @return NAME_ATTR
     */
    public String getNameAttr() {
        return this.nameAttr;
    }
    /**
     * Set NAME_ATTR.
     * @param nameAttr NAME_ATTR
     * @return this object     */
    public GenLdapConfigsEntity setNameAttr(String nameAttr) {
        this.nameAttr = nameAttr;
        return this;
    }

    /**
     * Get MAIL_ATTR.
     * @return MAIL_ATTR
     */
    public String getMailAttr() {
        return this.mailAttr;
    }
    /**
     * Set MAIL_ATTR.
     * @param mailAttr MAIL_ATTR
     * @return this object     */
    public GenLdapConfigsEntity setMailAttr(String mailAttr) {
        this.mailAttr = mailAttr;
        return this;
    }

    /**
     * Get ADMIN_CHECK_FILTER.
     * @return ADMIN_CHECK_FILTER
     */
    public String getAdminCheckFilter() {
        return this.adminCheckFilter;
    }
    /**
     * Set ADMIN_CHECK_FILTER.
     * @param adminCheckFilter ADMIN_CHECK_FILTER
     * @return this object     */
    public GenLdapConfigsEntity setAdminCheckFilter(String adminCheckFilter) {
        this.adminCheckFilter = adminCheckFilter;
        return this;
    }

    /**
     * Get AUTH_TYPE	 0:DB認証,1:LDAP認証,2:DB認証+LDAP認証(LDAP優先).
     * @return AUTH_TYPE	 0:DB認証,1:LDAP認証,2:DB認証+LDAP認証(LDAP優先)
     */
    public Integer getAuthType() {
        return this.authType;
    }
    /**
     * Set AUTH_TYPE	 0:DB認証,1:LDAP認証,2:DB認証+LDAP認証(LDAP優先).
     * @param authType AUTH_TYPE	 0:DB認証,1:LDAP認証,2:DB認証+LDAP認証(LDAP優先)
     * @return this object     */
    public GenLdapConfigsEntity setAuthType(Integer authType) {
        this.authType = authType;
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
    public GenLdapConfigsEntity setRowId(String rowId) {
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
    public GenLdapConfigsEntity setInsertUser(Integer insertUser) {
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
    public GenLdapConfigsEntity setInsertDatetime(Timestamp insertDatetime) {
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
    public GenLdapConfigsEntity setUpdateUser(Integer updateUser) {
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
    public GenLdapConfigsEntity setUpdateDatetime(Timestamp updateDatetime) {
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
    public GenLdapConfigsEntity setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
        return this;
    }

    /**
     * Get key values 
     * @return values 
     */
    public Object[] getKeyValues() {
        Object[] keyValues = new Object[1];
        keyValues[0] = this.systemName;
        return keyValues;
    }
    /**
     * Set key values 
     * @param systemName 設定名
     */
    public void setKeyValues(String systemName) {
        this.systemName = systemName;
    }
    /**
     * compare on key 
     * @param entity entity 
     * @return result 
     */
    public boolean equalsOnKey(GenLdapConfigsEntity entity) {
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
        builder.append("systemName = ").append(systemName).append("\n");
        builder.append("description = ").append(description).append("\n");
        builder.append("host = ").append(host).append("\n");
        builder.append("port = ").append(port).append("\n");
        builder.append("useSsl = ").append(useSsl).append("\n");
        builder.append("useTls = ").append(useTls).append("\n");
        builder.append("bindDn = ").append(bindDn).append("\n");
        builder.append("bindPassword = ").append(bindPassword).append("\n");
        builder.append("salt = ").append(salt).append("\n");
        builder.append("baseDn = ").append(baseDn).append("\n");
        builder.append("filter = ").append(filter).append("\n");
        builder.append("idAttr = ").append(idAttr).append("\n");
        builder.append("nameAttr = ").append(nameAttr).append("\n");
        builder.append("mailAttr = ").append(mailAttr).append("\n");
        builder.append("adminCheckFilter = ").append(adminCheckFilter).append("\n");
        builder.append("authType = ").append(authType).append("\n");
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
        error = validator.validate(this.systemName, convLabelName("System Name"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(this.systemName, convLabelName("System Name"), 64);
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(this.description, convLabelName("Description"), 64);
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.REQUIRED);
        error = validator.validate(this.host, convLabelName("Host"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(this.host, convLabelName("Host"), 256);
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.REQUIRED);
        error = validator.validate(this.port, convLabelName("Port"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.INTEGER);
        error = validator.validate(this.port, convLabelName("Port"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.INTEGER);
        error = validator.validate(this.useSsl, convLabelName("Use Ssl"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.INTEGER);
        error = validator.validate(this.useTls, convLabelName("Use Tls"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(this.bindDn, convLabelName("Bind Dn"), 256);
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(this.bindPassword, convLabelName("Bind Password"), 1024);
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(this.salt, convLabelName("Salt"), 1024);
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.REQUIRED);
        error = validator.validate(this.baseDn, convLabelName("Base Dn"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(this.baseDn, convLabelName("Base Dn"), 256);
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(this.filter, convLabelName("Filter"), 256);
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.REQUIRED);
        error = validator.validate(this.idAttr, convLabelName("Id Attr"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(this.idAttr, convLabelName("Id Attr"), 256);
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(this.nameAttr, convLabelName("Name Attr"), 256);
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(this.mailAttr, convLabelName("Mail Attr"), 256);
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(this.adminCheckFilter, convLabelName("Admin Check Filter"), 256);
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.REQUIRED);
        error = validator.validate(this.authType, convLabelName("Auth Type"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.INTEGER);
        error = validator.validate(this.authType, convLabelName("Auth Type"));
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
        error = validator.validate(values.get("systemName"), convLabelName("System Name"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(values.get("systemName"), convLabelName("System Name"), 64);
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(values.get("description"), convLabelName("Description"), 64);
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.REQUIRED);
        error = validator.validate(values.get("host"), convLabelName("Host"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(values.get("host"), convLabelName("Host"), 256);
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.REQUIRED);
        error = validator.validate(values.get("port"), convLabelName("Port"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.INTEGER);
        error = validator.validate(values.get("port"), convLabelName("Port"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.INTEGER);
        error = validator.validate(values.get("useSsl"), convLabelName("Use Ssl"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.INTEGER);
        error = validator.validate(values.get("useTls"), convLabelName("Use Tls"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(values.get("bindDn"), convLabelName("Bind Dn"), 256);
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(values.get("bindPassword"), convLabelName("Bind Password"), 1024);
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(values.get("salt"), convLabelName("Salt"), 1024);
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.REQUIRED);
        error = validator.validate(values.get("baseDn"), convLabelName("Base Dn"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(values.get("baseDn"), convLabelName("Base Dn"), 256);
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(values.get("filter"), convLabelName("Filter"), 256);
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.REQUIRED);
        error = validator.validate(values.get("idAttr"), convLabelName("Id Attr"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(values.get("idAttr"), convLabelName("Id Attr"), 256);
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(values.get("nameAttr"), convLabelName("Name Attr"), 256);
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(values.get("mailAttr"), convLabelName("Mail Attr"), 256);
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(values.get("adminCheckFilter"), convLabelName("Admin Check Filter"), 256);
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
