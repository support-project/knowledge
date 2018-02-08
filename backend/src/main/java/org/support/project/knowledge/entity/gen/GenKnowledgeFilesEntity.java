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
 * 添付ファイル
 */
@DI(instance = Instance.Prototype)
public class GenKnowledgeFilesEntity implements Serializable {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * Get instance from DI container.
     * @return instance
     */
    public static GenKnowledgeFilesEntity get() {
        return Container.getComp(GenKnowledgeFilesEntity.class);
    }

    /**
     * Constructor.
     */
    public GenKnowledgeFilesEntity() {
        super();
    }

    /**
     * Constructor
     * @param fileNo 添付ファイル番号
     */

    public GenKnowledgeFilesEntity(Long fileNo) {
        super();
        this.fileNo = fileNo;
    }
    /** 添付ファイル番号 */
    private Long fileNo;
    /** ナレッジID */
    private Long knowledgeId;
    /** コメント番号 */
    private Long commentNo;
    /** 下書きID */
    private Long draftId;
    /** ファイル名 */
    private String fileName;
    /** ファイルサイズ */
    private Double fileSize;
    /** バイナリ */
    private InputStream fileBinary;
    /** パース結果 */
    private Integer parseStatus;
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
     * Get 添付ファイル番号.
     * @return 添付ファイル番号
     */
    public Long getFileNo() {
        return this.fileNo;
    }
    /**
     * Set 添付ファイル番号.
     * @param fileNo 添付ファイル番号
     * @return this object     */
    public GenKnowledgeFilesEntity setFileNo(Long fileNo) {
        this.fileNo = fileNo;
        return this;
    }

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
    public GenKnowledgeFilesEntity setKnowledgeId(Long knowledgeId) {
        this.knowledgeId = knowledgeId;
        return this;
    }

    /**
     * Get コメント番号.
     * @return コメント番号
     */
    public Long getCommentNo() {
        return this.commentNo;
    }
    /**
     * Set コメント番号.
     * @param commentNo コメント番号
     * @return this object     */
    public GenKnowledgeFilesEntity setCommentNo(Long commentNo) {
        this.commentNo = commentNo;
        return this;
    }

    /**
     * Get 下書きID.
     * @return 下書きID
     */
    public Long getDraftId() {
        return this.draftId;
    }
    /**
     * Set 下書きID.
     * @param draftId 下書きID
     * @return this object     */
    public GenKnowledgeFilesEntity setDraftId(Long draftId) {
        this.draftId = draftId;
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
    public GenKnowledgeFilesEntity setFileName(String fileName) {
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
    public GenKnowledgeFilesEntity setFileSize(Double fileSize) {
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
    public GenKnowledgeFilesEntity setFileBinary(InputStream fileBinary) {
        this.fileBinary = fileBinary;
        return this;
    }

    /**
     * Get パース結果.
     * @return パース結果
     */
    public Integer getParseStatus() {
        return this.parseStatus;
    }
    /**
     * Set パース結果.
     * @param parseStatus パース結果
     * @return this object     */
    public GenKnowledgeFilesEntity setParseStatus(Integer parseStatus) {
        this.parseStatus = parseStatus;
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
    public GenKnowledgeFilesEntity setInsertUser(Integer insertUser) {
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
    public GenKnowledgeFilesEntity setInsertDatetime(Timestamp insertDatetime) {
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
    public GenKnowledgeFilesEntity setUpdateUser(Integer updateUser) {
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
    public GenKnowledgeFilesEntity setUpdateDatetime(Timestamp updateDatetime) {
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
    public GenKnowledgeFilesEntity setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
        return this;
    }

    /**
     * Get key values 
     * @return values 
     */
    public Object[] getKeyValues() {
        Object[] keyValues = new Object[1];
        keyValues[0] = this.fileNo;
        return keyValues;
    }
    /**
     * Set key values 
     * @param fileNo 添付ファイル番号
     */
    public void setKeyValues(Long fileNo) {
        this.fileNo = fileNo;
    }
    /**
     * compare on key 
     * @param entity entity 
     * @return result 
     */
    public boolean equalsOnKey(GenKnowledgeFilesEntity entity) {
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
        builder.append("fileNo = ").append(fileNo).append("\n");
        builder.append("knowledgeId = ").append(knowledgeId).append("\n");
        builder.append("commentNo = ").append(commentNo).append("\n");
        builder.append("draftId = ").append(draftId).append("\n");
        builder.append("fileName = ").append(fileName).append("\n");
        builder.append("fileSize = ").append(fileSize).append("\n");
        builder.append("fileBinary = ").append(fileBinary).append("\n");
        builder.append("parseStatus = ").append(parseStatus).append("\n");
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
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(this.fileName, convLabelName("File Name"), 256);
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.REQUIRED);
        error = validator.validate(this.parseStatus, convLabelName("Parse Status"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.INTEGER);
        error = validator.validate(this.parseStatus, convLabelName("Parse Status"));
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
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(values.get("fileName"), convLabelName("File Name"), 256);
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.REQUIRED);
        error = validator.validate(values.get("parseStatus"), convLabelName("Parse Status"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.INTEGER);
        error = validator.validate(values.get("parseStatus"), convLabelName("Parse Status"));
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
