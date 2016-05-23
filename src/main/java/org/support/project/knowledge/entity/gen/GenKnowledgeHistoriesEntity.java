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
 * ナレッジ更新履歴
 */
@DI(instance = Instance.Prototype)
public class GenKnowledgeHistoriesEntity implements Serializable {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * Get instance from DI container.
     * @return instance
     */
    public static GenKnowledgeHistoriesEntity get() {
        return Container.getComp(GenKnowledgeHistoriesEntity.class);
    }

    /**
     * Constructor.
     */
    public GenKnowledgeHistoriesEntity() {
        super();
    }

    /**
     * Constructor
     * @param historyNo 履歴番号
     * @param knowledgeId ナレッジID
     */

    public GenKnowledgeHistoriesEntity(Integer historyNo, Long knowledgeId) {
        super();
        this.historyNo = historyNo;
        this.knowledgeId = knowledgeId;
    }
    /** ナレッジID */
    private Long knowledgeId;
    /** 履歴番号 */
    private Integer historyNo;
    /** タイトル */
    private String title;
    /** 内容 */
    private String content;
    /** 公開区分 */
    private Integer publicFlag;
    /** タグID一覧 */
    private String tagIds;
    /** タグ名称一覧 */
    private String tagNames;
    /** いいね件数 */
    private Long likeCount;
    /** コメント件数 */
    private Integer commentCount;
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
    public GenKnowledgeHistoriesEntity setKnowledgeId(Long knowledgeId) {
        this.knowledgeId = knowledgeId;
        return this;
    }

    /**
     * Get 履歴番号.
     * @return 履歴番号
     */
    public Integer getHistoryNo() {
        return this.historyNo;
    }
    /**
     * Set 履歴番号.
     * @param historyNo 履歴番号
     * @return this object     */
    public GenKnowledgeHistoriesEntity setHistoryNo(Integer historyNo) {
        this.historyNo = historyNo;
        return this;
    }

    /**
     * Get タイトル.
     * @return タイトル
     */
    public String getTitle() {
        return this.title;
    }
    /**
     * Set タイトル.
     * @param title タイトル
     * @return this object     */
    public GenKnowledgeHistoriesEntity setTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     * Get 内容.
     * @return 内容
     */
    public String getContent() {
        return this.content;
    }
    /**
     * Set 内容.
     * @param content 内容
     * @return this object     */
    public GenKnowledgeHistoriesEntity setContent(String content) {
        this.content = content;
        return this;
    }

    /**
     * Get 公開区分.
     * @return 公開区分
     */
    public Integer getPublicFlag() {
        return this.publicFlag;
    }
    /**
     * Set 公開区分.
     * @param publicFlag 公開区分
     * @return this object     */
    public GenKnowledgeHistoriesEntity setPublicFlag(Integer publicFlag) {
        this.publicFlag = publicFlag;
        return this;
    }

    /**
     * Get タグID一覧.
     * @return タグID一覧
     */
    public String getTagIds() {
        return this.tagIds;
    }
    /**
     * Set タグID一覧.
     * @param tagIds タグID一覧
     * @return this object     */
    public GenKnowledgeHistoriesEntity setTagIds(String tagIds) {
        this.tagIds = tagIds;
        return this;
    }

    /**
     * Get タグ名称一覧.
     * @return タグ名称一覧
     */
    public String getTagNames() {
        return this.tagNames;
    }
    /**
     * Set タグ名称一覧.
     * @param tagNames タグ名称一覧
     * @return this object     */
    public GenKnowledgeHistoriesEntity setTagNames(String tagNames) {
        this.tagNames = tagNames;
        return this;
    }

    /**
     * Get いいね件数.
     * @return いいね件数
     */
    public Long getLikeCount() {
        return this.likeCount;
    }
    /**
     * Set いいね件数.
     * @param likeCount いいね件数
     * @return this object     */
    public GenKnowledgeHistoriesEntity setLikeCount(Long likeCount) {
        this.likeCount = likeCount;
        return this;
    }

    /**
     * Get コメント件数.
     * @return コメント件数
     */
    public Integer getCommentCount() {
        return this.commentCount;
    }
    /**
     * Set コメント件数.
     * @param commentCount コメント件数
     * @return this object     */
    public GenKnowledgeHistoriesEntity setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
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
    public GenKnowledgeHistoriesEntity setInsertUser(Integer insertUser) {
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
    public GenKnowledgeHistoriesEntity setInsertDatetime(Timestamp insertDatetime) {
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
    public GenKnowledgeHistoriesEntity setUpdateUser(Integer updateUser) {
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
    public GenKnowledgeHistoriesEntity setUpdateDatetime(Timestamp updateDatetime) {
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
    public GenKnowledgeHistoriesEntity setDeleteFlag(Integer deleteFlag) {
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
    public void setKeyValues(Integer historyNo, Long knowledgeId) {
        this.historyNo = historyNo;
        this.knowledgeId = knowledgeId;
    }
    /**
     * compare on key 
     * @param entity entity 
     * @return result 
     */
    public boolean equalsOnKey(GenKnowledgeHistoriesEntity entity) {
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
        builder.append("title = ").append(title).append("\n");
        builder.append("content = ").append(content).append("\n");
        builder.append("publicFlag = ").append(publicFlag).append("\n");
        builder.append("tagIds = ").append(tagIds).append("\n");
        builder.append("tagNames = ").append(tagNames).append("\n");
        builder.append("likeCount = ").append(likeCount).append("\n");
        builder.append("commentCount = ").append(commentCount).append("\n");
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
        validator = ValidatorFactory.getInstance(Validator.INTEGER);
        error = validator.validate(this.historyNo, convLabelName("History No"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.REQUIRED);
        error = validator.validate(this.title, convLabelName("Title"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(this.title, convLabelName("Title"), 1024);
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.INTEGER);
        error = validator.validate(this.publicFlag, convLabelName("Public Flag"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(this.tagIds, convLabelName("Tag Ids"), 1024);
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.INTEGER);
        error = validator.validate(this.commentCount, convLabelName("Comment Count"));
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
        validator = ValidatorFactory.getInstance(Validator.INTEGER);
        error = validator.validate(values.get("historyNo"), convLabelName("History No"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.REQUIRED);
        error = validator.validate(values.get("title"), convLabelName("Title"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(values.get("title"), convLabelName("Title"), 1024);
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.INTEGER);
        error = validator.validate(values.get("publicFlag"), convLabelName("Public Flag"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
        error = validator.validate(values.get("tagIds"), convLabelName("Tag Ids"), 1024);
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.INTEGER);
        error = validator.validate(values.get("commentCount"), convLabelName("Comment Count"));
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
