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
 * プロキシ設定
 */
@DI(instance = Instance.Prototype)
public class GenProxyConfigsEntity implements Serializable {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * Get instance from DI container.
     * @return instance
     */
    public static GenProxyConfigsEntity get() {
        return Container.getComp(GenProxyConfigsEntity.class);
    }

    /**
     * Constructor.
     */
    public GenProxyConfigsEntity() {
        super();
    }

    /**
     * Constructor
     * @param systemName システム名
     */

    public GenProxyConfigsEntity(String systemName) {
        super();
        this.systemName = systemName;
    }
    /** システム名 */
    private String systemName;
    /** [Proxy]ホスト名 */
    private String proxyHostName;
    /** [Proxy]ポート番号 */
    private Integer proxyPortNo;
    /** [Proxy-Auth]認証タイプ */
    private Integer proxyAuthType;
    /** [Proxy-Auth]認証ユーザID */
    private String proxyAuthUserId;
    /** [Proxy-Auth]認証パスワード */
    private String proxyAuthPassword;
    /** [Proxy-Auth]認証SALT */
    private String proxyAuthSalt;
    /** [Proxy-Auth-NTLM]認証PC名 */
    private String proxyAuthPcName;
    /** [Auth-NTLM]認証ドメイン */
    private String proxyAuthDomain;
    /** [Web]SSL証明書チェック */
    private Integer thirdPartyCertificate;
    /** [Web]接続確認用URL */
    private String testUrl;
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
     * Get システム名.
     * @return システム名
     */
    public String getSystemName() {
        return this.systemName;
    }
    /**
     * Set システム名.
     * @param systemName システム名
     * @return this object     */
    public GenProxyConfigsEntity setSystemName(String systemName) {
        this.systemName = systemName;
        return this;
    }

    /**
     * Get [Proxy]ホスト名.
     * @return [Proxy]ホスト名
     */
    public String getProxyHostName() {
        return this.proxyHostName;
    }
    /**
     * Set [Proxy]ホスト名.
     * @param proxyHostName [Proxy]ホスト名
     * @return this object     */
    public GenProxyConfigsEntity setProxyHostName(String proxyHostName) {
        this.proxyHostName = proxyHostName;
        return this;
    }

    /**
     * Get [Proxy]ポート番号.
     * @return [Proxy]ポート番号
     */
    public Integer getProxyPortNo() {
        return this.proxyPortNo;
    }
    /**
     * Set [Proxy]ポート番号.
     * @param proxyPortNo [Proxy]ポート番号
     * @return this object     */
    public GenProxyConfigsEntity setProxyPortNo(Integer proxyPortNo) {
        this.proxyPortNo = proxyPortNo;
        return this;
    }

    /**
     * Get [Proxy-Auth]認証タイプ.
     * @return [Proxy-Auth]認証タイプ
     */
    public Integer getProxyAuthType() {
        return this.proxyAuthType;
    }
    /**
     * Set [Proxy-Auth]認証タイプ.
     * @param proxyAuthType [Proxy-Auth]認証タイプ
     * @return this object     */
    public GenProxyConfigsEntity setProxyAuthType(Integer proxyAuthType) {
        this.proxyAuthType = proxyAuthType;
        return this;
    }

    /**
     * Get [Proxy-Auth]認証ユーザID.
     * @return [Proxy-Auth]認証ユーザID
     */
    public String getProxyAuthUserId() {
        return this.proxyAuthUserId;
    }
    /**
     * Set [Proxy-Auth]認証ユーザID.
     * @param proxyAuthUserId [Proxy-Auth]認証ユーザID
     * @return this object     */
    public GenProxyConfigsEntity setProxyAuthUserId(String proxyAuthUserId) {
        this.proxyAuthUserId = proxyAuthUserId;
        return this;
    }

    /**
     * Get [Proxy-Auth]認証パスワード.
     * @return [Proxy-Auth]認証パスワード
     */
    public String getProxyAuthPassword() {
        return this.proxyAuthPassword;
    }
    /**
     * Set [Proxy-Auth]認証パスワード.
     * @param proxyAuthPassword [Proxy-Auth]認証パスワード
     * @return this object     */
    public GenProxyConfigsEntity setProxyAuthPassword(String proxyAuthPassword) {
        this.proxyAuthPassword = proxyAuthPassword;
        return this;
    }

    /**
     * Get [Proxy-Auth]認証SALT.
     * @return [Proxy-Auth]認証SALT
     */
    public String getProxyAuthSalt() {
        return this.proxyAuthSalt;
    }
    /**
     * Set [Proxy-Auth]認証SALT.
     * @param proxyAuthSalt [Proxy-Auth]認証SALT
     * @return this object     */
    public GenProxyConfigsEntity setProxyAuthSalt(String proxyAuthSalt) {
        this.proxyAuthSalt = proxyAuthSalt;
        return this;
    }

    /**
     * Get [Proxy-Auth-NTLM]認証PC名.
     * @return [Proxy-Auth-NTLM]認証PC名
     */
    public String getProxyAuthPcName() {
        return this.proxyAuthPcName;
    }
    /**
     * Set [Proxy-Auth-NTLM]認証PC名.
     * @param proxyAuthPcName [Proxy-Auth-NTLM]認証PC名
     * @return this object     */
    public GenProxyConfigsEntity setProxyAuthPcName(String proxyAuthPcName) {
        this.proxyAuthPcName = proxyAuthPcName;
        return this;
    }

    /**
     * Get [Auth-NTLM]認証ドメイン.
     * @return [Auth-NTLM]認証ドメイン
     */
    public String getProxyAuthDomain() {
        return this.proxyAuthDomain;
    }
    /**
     * Set [Auth-NTLM]認証ドメイン.
     * @param proxyAuthDomain [Auth-NTLM]認証ドメイン
     * @return this object     */
    public GenProxyConfigsEntity setProxyAuthDomain(String proxyAuthDomain) {
        this.proxyAuthDomain = proxyAuthDomain;
        return this;
    }

    /**
     * Get [Web]SSL証明書チェック.
     * @return [Web]SSL証明書チェック
     */
    public Integer getThirdPartyCertificate() {
        return this.thirdPartyCertificate;
    }
    /**
     * Set [Web]SSL証明書チェック.
     * @param thirdPartyCertificate [Web]SSL証明書チェック
     * @return this object     */
    public GenProxyConfigsEntity setThirdPartyCertificate(Integer thirdPartyCertificate) {
        this.thirdPartyCertificate = thirdPartyCertificate;
        return this;
    }

    /**
     * Get [Web]接続確認用URL.
     * @return [Web]接続確認用URL
     */
    public String getTestUrl() {
        return this.testUrl;
    }
    /**
     * Set [Web]接続確認用URL.
     * @param testUrl [Web]接続確認用URL
     * @return this object     */
    public GenProxyConfigsEntity setTestUrl(String testUrl) {
        this.testUrl = testUrl;
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
    public GenProxyConfigsEntity setRowId(String rowId) {
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
    public GenProxyConfigsEntity setInsertUser(Integer insertUser) {
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
    public GenProxyConfigsEntity setInsertDatetime(Timestamp insertDatetime) {
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
    public GenProxyConfigsEntity setUpdateUser(Integer updateUser) {
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
    public GenProxyConfigsEntity setUpdateDatetime(Timestamp updateDatetime) {
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
    public GenProxyConfigsEntity setDeleteFlag(Integer deleteFlag) {
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
     * @param systemName システム名
     */
    public void setKeyValues(String systemName) {
        this.systemName = systemName;
    }
    /**
     * compare on key 
     * @param entity entity 
     * @return result 
     */
    public boolean equalsOnKey(GenProxyConfigsEntity entity) {
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
        builder.append("proxyHostName = ").append(proxyHostName).append("\n");
        builder.append("proxyPortNo = ").append(proxyPortNo).append("\n");
        builder.append("proxyAuthType = ").append(proxyAuthType).append("\n");
        builder.append("proxyAuthUserId = ").append(proxyAuthUserId).append("\n");
        builder.append("proxyAuthPassword = ").append(proxyAuthPassword).append("\n");
        builder.append("proxyAuthSalt = ").append(proxyAuthSalt).append("\n");
        builder.append("proxyAuthPcName = ").append(proxyAuthPcName).append("\n");
        builder.append("proxyAuthDomain = ").append(proxyAuthDomain).append("\n");
        builder.append("thirdPartyCertificate = ").append(thirdPartyCertificate).append("\n");
        builder.append("testUrl = ").append(testUrl).append("\n");
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
        validator = ValidatorFactory.getInstance(Validator.REQUIRED);
        error = validator.validate(this.proxyHostName, convLabelName("Proxy Host Name"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(this.proxyHostName, convLabelName("Proxy Host Name"), 256);
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.REQUIRED);
        error = validator.validate(this.proxyPortNo, convLabelName("Proxy Port No"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.INTEGER);
        error = validator.validate(this.proxyPortNo, convLabelName("Proxy Port No"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.REQUIRED);
        error = validator.validate(this.proxyAuthType, convLabelName("Proxy Auth Type"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.INTEGER);
        error = validator.validate(this.proxyAuthType, convLabelName("Proxy Auth Type"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(this.proxyAuthUserId, convLabelName("Proxy Auth User Id"), 256);
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(this.proxyAuthPassword, convLabelName("Proxy Auth Password"), 1024);
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(this.proxyAuthSalt, convLabelName("Proxy Auth Salt"), 1024);
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(this.proxyAuthPcName, convLabelName("Proxy Auth Pc Name"), 256);
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(this.proxyAuthDomain, convLabelName("Proxy Auth Domain"), 256);
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.INTEGER);
        error = validator.validate(this.thirdPartyCertificate, convLabelName("Third Party Certificate"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(this.testUrl, convLabelName("Test Url"), 256);
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
        validator = ValidatorFactory.getInstance(Validator.REQUIRED);
        error = validator.validate(values.get("proxyHostName"), convLabelName("Proxy Host Name"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(values.get("proxyHostName"), convLabelName("Proxy Host Name"), 256);
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.REQUIRED);
        error = validator.validate(values.get("proxyPortNo"), convLabelName("Proxy Port No"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.INTEGER);
        error = validator.validate(values.get("proxyPortNo"), convLabelName("Proxy Port No"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.REQUIRED);
        error = validator.validate(values.get("proxyAuthType"), convLabelName("Proxy Auth Type"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.INTEGER);
        error = validator.validate(values.get("proxyAuthType"), convLabelName("Proxy Auth Type"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(values.get("proxyAuthUserId"), convLabelName("Proxy Auth User Id"), 256);
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(values.get("proxyAuthPassword"), convLabelName("Proxy Auth Password"), 1024);
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(values.get("proxyAuthSalt"), convLabelName("Proxy Auth Salt"), 1024);
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(values.get("proxyAuthPcName"), convLabelName("Proxy Auth Pc Name"), 256);
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(values.get("proxyAuthDomain"), convLabelName("Proxy Auth Domain"), 256);
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.INTEGER);
        error = validator.validate(values.get("thirdPartyCertificate"), convLabelName("Third Party Certificate"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(values.get("testUrl"), convLabelName("Test Url"), 256);
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
