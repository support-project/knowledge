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
@DI(instance=Instance.Prototype)
public class GenKnowledgesEntity implements Serializable {

	/** SerialVersion */
	private static final long serialVersionUID = 1L;

	/**
	 * インスタンス取得
	 * AOPに対応
	 * @return インスタンス
	 */
	public static GenKnowledgesEntity get() {
		return Container.getComp(GenKnowledgesEntity.class);
	}

	/**
	 * コンストラクタ
	 */
	public GenKnowledgesEntity() {
		super();
	}

	/**
	 * コンストラクタ
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
	/** テンプレートの種類ID */
	private Integer typeId;
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
	 * ナレッジID を取得する
	 */
	public Long getKnowledgeId() {
		return this.knowledgeId;
	}
	/**
	 * ナレッジID を設定する
	 * @param knowledgeId ナレッジID
	 */
	public GenKnowledgesEntity setKnowledgeId(Long knowledgeId) {
		this.knowledgeId = knowledgeId;
		return this;
	}

	/**
	 * タイトル を取得する
	 */
	public String getTitle() {
		return this.title;
	}
	/**
	 * タイトル を設定する
	 * @param title タイトル
	 */
	public GenKnowledgesEntity setTitle(String title) {
		this.title = title;
		return this;
	}

	/**
	 * 内容 を取得する
	 */
	public String getContent() {
		return this.content;
	}
	/**
	 * 内容 を設定する
	 * @param content 内容
	 */
	public GenKnowledgesEntity setContent(String content) {
		this.content = content;
		return this;
	}

	/**
	 * 公開区分 を取得する
	 */
	public Integer getPublicFlag() {
		return this.publicFlag;
	}
	/**
	 * 公開区分 を設定する
	 * @param publicFlag 公開区分
	 */
	public GenKnowledgesEntity setPublicFlag(Integer publicFlag) {
		this.publicFlag = publicFlag;
		return this;
	}

	/**
	 * タグID一覧 を取得する
	 */
	public String getTagIds() {
		return this.tagIds;
	}
	/**
	 * タグID一覧 を設定する
	 * @param tagIds タグID一覧
	 */
	public GenKnowledgesEntity setTagIds(String tagIds) {
		this.tagIds = tagIds;
		return this;
	}

	/**
	 * タグ名称一覧 を取得する
	 */
	public String getTagNames() {
		return this.tagNames;
	}
	/**
	 * タグ名称一覧 を設定する
	 * @param tagNames タグ名称一覧
	 */
	public GenKnowledgesEntity setTagNames(String tagNames) {
		this.tagNames = tagNames;
		return this;
	}

	/**
	 * いいね件数 を取得する
	 */
	public Long getLikeCount() {
		return this.likeCount;
	}
	/**
	 * いいね件数 を設定する
	 * @param likeCount いいね件数
	 */
	public GenKnowledgesEntity setLikeCount(Long likeCount) {
		this.likeCount = likeCount;
		return this;
	}

	/**
	 * コメント件数 を取得する
	 */
	public Integer getCommentCount() {
		return this.commentCount;
	}
	/**
	 * コメント件数 を設定する
	 * @param commentCount コメント件数
	 */
	public GenKnowledgesEntity setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
		return this;
	}

	/**
	 * テンプレートの種類ID を取得する
	 */
	public Integer getTypeId() {
		return this.typeId;
	}
	/**
	 * テンプレートの種類ID を設定する
	 * @param typeId テンプレートの種類ID
	 */
	public GenKnowledgesEntity setTypeId(Integer typeId) {
		this.typeId = typeId;
		return this;
	}

	/**
	 * 登録ユーザ を取得する
	 */
	public Integer getInsertUser() {
		return this.insertUser;
	}
	/**
	 * 登録ユーザ を設定する
	 * @param insertUser 登録ユーザ
	 */
	public GenKnowledgesEntity setInsertUser(Integer insertUser) {
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
	 * @param insertDatetime 登録日時
	 */
	public GenKnowledgesEntity setInsertDatetime(Timestamp insertDatetime) {
		this.insertDatetime = insertDatetime;
		return this;
	}

	/**
	 * 更新ユーザ を取得する
	 */
	public Integer getUpdateUser() {
		return this.updateUser;
	}
	/**
	 * 更新ユーザ を設定する
	 * @param updateUser 更新ユーザ
	 */
	public GenKnowledgesEntity setUpdateUser(Integer updateUser) {
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
	 * @param updateDatetime 更新日時
	 */
	public GenKnowledgesEntity setUpdateDatetime(Timestamp updateDatetime) {
		this.updateDatetime = updateDatetime;
		return this;
	}

	/**
	 * 削除フラグ を取得する
	 */
	public Integer getDeleteFlag() {
		return this.deleteFlag;
	}
	/**
	 * 削除フラグ を設定する
	 * @param deleteFlag 削除フラグ
	 */
	public GenKnowledgesEntity setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
		return this;
	}

	/**
	 * キーの値を取得 
	 */
	public Object[] getKeyValues() {
		Object[] keyValues = new Object[1];
		keyValues[0] = this.knowledgeId;
		return keyValues;
	}
	/**
	 * キーの値を設定 
	 * @param knowledgeId ナレッジID
	 */
	public void setKeyValues(Long knowledgeId) {
		this.knowledgeId = knowledgeId;
	}
	/**
	 * キーで比較 
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
		builder.append("typeId = ").append(typeId).append("\n");
		builder.append("insertUser = ").append(insertUser).append("\n");
		builder.append("insertDatetime = ").append(insertDatetime).append("\n");
		builder.append("updateUser = ").append(updateUser).append("\n");
		builder.append("updateDatetime = ").append(updateDatetime).append("\n");
		builder.append("deleteFlag = ").append(deleteFlag).append("\n");
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
