package org.support.project.knowledge.entity.gen;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import java.io.InputStream;
import java.sql.Timestamp;



import org.support.project.common.bean.ValidateError;
import org.support.project.common.validate.Validator;
import org.support.project.common.validate.ValidatorFactory;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;

/**
 * アカウントの画像
 */
@DI(instance = Instance.Prototype)
public class GenAccountImagesEntity implements Serializable {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * Get instance from DI container.
     * @return instance
     */
    public static GenAccountImagesEntity get() {
        return Container.getComp(GenAccountImagesEntity.class);
    }

    /**
     * Constructor.
     */
    public GenAccountImagesEntity() {
        super();
    }

    /**
     * Constructor
     * @param imageId IMAGE_ID
     */

    public GenAccountImagesEntity(Long imageId) {
        super();
        this.imageId = imageId;
    }
    /** IMAGE_ID */
    private Long imageId;
    /** ユーザID */
    private Integer userId;
    /** ファイル名 */
    private String fileName;
    /** ファイルサイズ */
    private Double fileSize;
    /** バイナリ */
    private InputStream fileBinary;
    /** 拡張子 */
    private String extension;
    /** CONTENT_TYPE */
    private String contentType;
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
     * Get IMAGE_ID.
     * @return IMAGE_ID
     */
    public Long getImageId() {
        return this.imageId;
    }
    /**
     * Set IMAGE_ID.
     * @param imageId IMAGE_ID
     * @return this object     */
    public GenAccountImagesEntity setImageId(Long imageId) {
        this.imageId = imageId;
        return this;
    }

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
    public GenAccountImagesEntity setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    /**
     * Get ファイル名.
     * @return ファイル名
     */
    public String getFileName() {
        return this.fileName;
    }
    /**
     * Set ファイル名.
     * @param fileName ファイル名
     * @return this object     */
    public GenAccountImagesEntity setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    /**
     * Get ファイルサイズ.
     * @return ファイルサイズ
     */
    public Double getFileSize() {
        return this.fileSize;
    }
    /**
     * Set ファイルサイズ.
     * @param fileSize ファイルサイズ
     * @return this object     */
    public GenAccountImagesEntity setFileSize(Double fileSize) {
        this.fileSize = fileSize;
        return this;
    }

    /**
     * Get バイナリ.
     * @return バイナリ
     */
    public InputStream getFileBinary() {
        return this.fileBinary;
    }
    /**
     * Set バイナリ.
     * @param fileBinary バイナリ
     * @return this object     */
    public GenAccountImagesEntity setFileBinary(InputStream fileBinary) {
        this.fileBinary = fileBinary;
        return this;
    }

    /**
     * Get 拡張子.
     * @return 拡張子
     */
    public String getExtension() {
        return this.extension;
    }
    /**
     * Set 拡張子.
     * @param extension 拡張子
     * @return this object     */
    public GenAccountImagesEntity setExtension(String extension) {
        this.extension = extension;
        return this;
    }

    /**
     * Get CONTENT_TYPE.
     * @return CONTENT_TYPE
     */
    public String getContentType() {
        return this.contentType;
    }
    /**
     * Set CONTENT_TYPE.
     * @param contentType CONTENT_TYPE
     * @return this object     */
    public GenAccountImagesEntity setContentType(String contentType) {
        this.contentType = contentType;
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
    public GenAccountImagesEntity setInsertUser(Integer insertUser) {
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
    public GenAccountImagesEntity setInsertDatetime(Timestamp insertDatetime) {
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
    public GenAccountImagesEntity setUpdateUser(Integer updateUser) {
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
    public GenAccountImagesEntity setUpdateDatetime(Timestamp updateDatetime) {
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
    public GenAccountImagesEntity setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
        return this;
    }

    /**
     * Get key values 
     * @return values 
     */
    public Object[] getKeyValues() {
        Object[] keyValues = new Object[1];
        keyValues[0] = this.imageId;
        return keyValues;
    }
    /**
     * Set key values 
     * @param imageId IMAGE_ID
     */
    public void setKeyValues(Long imageId) {
        this.imageId = imageId;
    }
    /**
     * compare on key 
     * @param entity entity 
     * @return result 
     */
    public boolean equalsOnKey(GenAccountImagesEntity entity) {
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
        builder.append("imageId = ").append(imageId).append("\n");
        builder.append("userId = ").append(userId).append("\n");
        builder.append("fileName = ").append(fileName).append("\n");
        builder.append("fileSize = ").append(fileSize).append("\n");
        builder.append("fileBinary = ").append(fileBinary).append("\n");
        builder.append("extension = ").append(extension).append("\n");
        builder.append("contentType = ").append(contentType).append("\n");
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
        validator = ValidatorFactory.getInstance(Validator.INTEGER);
        error = validator.validate(this.userId, convLabelName("User Id"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(this.fileName, convLabelName("File Name"), 256);
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(this.extension, convLabelName("Extension"), 256);
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(this.contentType, convLabelName("Content Type"), 256);
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
        validator = ValidatorFactory.getInstance(Validator.INTEGER);
        error = validator.validate(values.get("userId"), convLabelName("User Id"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(values.get("fileName"), convLabelName("File Name"), 256);
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(values.get("extension"), convLabelName("Extension"), 256);
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(values.get("contentType"), convLabelName("Content Type"), 256);
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
