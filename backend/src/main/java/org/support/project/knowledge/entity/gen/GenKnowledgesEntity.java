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
 * ナレッジ
 */
@DI(instance = Instance.Prototype)
public class GenKnowledgesEntity implements Serializable {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * Get instance from DI container.
     * @return instance
     */
    public static GenKnowledgesEntity get() {
        return Container.getComp(GenKnowledgesEntity.class);
    }

    /**
     * Constructor.
     */
    public GenKnowledgesEntity() {
        super();
    }

    /**
     * Constructor
     * @param knowledgeId ナレッジID
     */

    public GenKnowledgesEntity(Long knowledgeId) {
        super();
        this.knowledgeId = knowledgeId;
    }
    /** ナレッジID */
    private Long knowledgeId;
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
    /** 参照件数 */
    private Long viewCount;
    /** テンプレートの種類ID */
    private Integer typeId;
    /** 通知ステータス */
    private Integer notifyStatus;
    /** ポイント */
    private Integer point;
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
    public GenKnowledgesEntity setKnowledgeId(Long knowledgeId) {
        this.knowledgeId = knowledgeId;
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
    public GenKnowledgesEntity setTitle(String title) {
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
    public GenKnowledgesEntity setContent(String content) {
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
    public GenKnowledgesEntity setPublicFlag(Integer publicFlag) {
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
    public GenKnowledgesEntity setTagIds(String tagIds) {
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
    public GenKnowledgesEntity setTagNames(String tagNames) {
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
    public GenKnowledgesEntity setLikeCount(Long likeCount) {
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
    public GenKnowledgesEntity setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
        return this;
    }

    /**
     * Get 参照件数.
     * @return 参照件数
     */
    public Long getViewCount() {
        return this.viewCount;
    }
    /**
     * Set 参照件数.
     * @param viewCount 参照件数
     * @return this object     */
    public GenKnowledgesEntity setViewCount(Long viewCount) {
        this.viewCount = viewCount;
        return this;
    }

    /**
     * Get テンプレートの種類ID.
     * @return テンプレートの種類ID
     */
    public Integer getTypeId() {
        return this.typeId;
    }
    /**
     * Set テンプレートの種類ID.
     * @param typeId テンプレートの種類ID
     * @return this object     */
    public GenKnowledgesEntity setTypeId(Integer typeId) {
        this.typeId = typeId;
        return this;
    }

    /**
     * Get 通知ステータス.
     * @return 通知ステータス
     */
    public Integer getNotifyStatus() {
        return this.notifyStatus;
    }
    /**
     * Set 通知ステータス.
     * @param notifyStatus 通知ステータス
     * @return this object     */
    public GenKnowledgesEntity setNotifyStatus(Integer notifyStatus) {
        this.notifyStatus = notifyStatus;
        return this;
    }

    /**
     * Get ポイント.
     * @return ポイント
     */
    public Integer getPoint() {
        return this.point;
    }
    /**
     * Set ポイント.
     * @param point ポイント
     * @return this object     */
    public GenKnowledgesEntity setPoint(Integer point) {
        this.point = point;
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
    public GenKnowledgesEntity setInsertUser(Integer insertUser) {
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
    public GenKnowledgesEntity setInsertDatetime(Timestamp insertDatetime) {
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
    public GenKnowledgesEntity setUpdateUser(Integer updateUser) {
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
    public GenKnowledgesEntity setUpdateDatetime(Timestamp updateDatetime) {
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
    public GenKnowledgesEntity setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
        return this;
    }

    /**
     * Get key values 
     * @return values 
     */
    public Object[] getKeyValues() {
        Object[] keyValues = new Object[1];
        keyValues[0] = this.knowledgeId;
        return keyValues;
    }
    /**
     * Set key values 
     * @param knowledgeId ナレッジID
     */
    public void setKeyValues(Long knowledgeId) {
        this.knowledgeId = knowledgeId;
    }
    /**
     * compare on key 
     * @param entity entity 
     * @return result 
     */
    public boolean equalsOnKey(GenKnowledgesEntity entity) {
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
        builder.append("knowledgeId = ").append(knowledgeId).append("\n");
        builder.append("title = ").append(title).append("\n");
        builder.append("content = ").append(content).append("\n");
        builder.append("publicFlag = ").append(publicFlag).append("\n");
        builder.append("tagIds = ").append(tagIds).append("\n");
        builder.append("tagNames = ").append(tagNames).append("\n");
        builder.append("likeCount = ").append(likeCount).append("\n");
        builder.append("commentCount = ").append(commentCount).append("\n");
        builder.append("viewCount = ").append(viewCount).append("\n");
        builder.append("typeId = ").append(typeId).append("\n");
        builder.append("notifyStatus = ").append(notifyStatus).append("\n");
        builder.append("point = ").append(point).append("\n");
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
        error = validator.validate(this.typeId, convLabelName("Type Id"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.INTEGER);
        error = validator.validate(this.notifyStatus, convLabelName("Notify Status"));
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
        error = validator.validate(values.get("typeId"), convLabelName("Type Id"));
        if (error != null) {
            errors.add(error);
        }
        validator = ValidatorFactory.getInstance(Validator.INTEGER);
        error = validator.validate(values.get("notifyStatus"), convLabelName("Notify Status"));
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
