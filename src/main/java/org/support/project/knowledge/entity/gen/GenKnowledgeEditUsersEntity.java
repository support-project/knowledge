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
 * 編集可能なユーザ
 */
@DI(instance=Instance.Prototype)
public class GenKnowledgeEditUsersEntity implements Serializable {

	/** SerialVersion */
	private static final long serialVersionUID = 1L;

	/**
	 * インスタンス取得
	 * AOPに対応
	 * @return インスタンス
	 */
	public static GenKnowledgeEditUsersEntity get() {
		return Container.getComp(GenKnowledgeEditUsersEntity.class);
	}

	/**
	 * コンストラクタ
	 */
	public GenKnowledgeEditUsersEntity() {
		super();
	}

	/**
	 * コンストラクタ
	 * @param knowledgeId ナレッジID
	 * @param userId USER_ID
	 */

	public GenKnowledgeEditUsersEntity(Long knowledgeId, Integer userId) {
		super();
		this.knowledgeId = knowledgeId;
		this.userId = userId;
	}
	/** ナレッジID */
	private Long knowledgeId;
	/** USER_ID */
	private Integer userId;
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
	public GenKnowledgeEditUsersEntity setKnowledgeId(Long knowledgeId) {
		this.knowledgeId = knowledgeId;
		return this;
	}

	/**
	 * USER_ID を取得する
	 */
	public Integer getUserId() {
		return this.userId;
	}
	/**
	 * USER_ID を設定する
	 * @param userId USER_ID
	 */
	public GenKnowledgeEditUsersEntity setUserId(Integer userId) {
		this.userId = userId;
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
	public GenKnowledgeEditUsersEntity setInsertUser(Integer insertUser) {
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
	public GenKnowledgeEditUsersEntity setInsertDatetime(Timestamp insertDatetime) {
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
	public GenKnowledgeEditUsersEntity setUpdateUser(Integer updateUser) {
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
	public GenKnowledgeEditUsersEntity setUpdateDatetime(Timestamp updateDatetime) {
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
	public GenKnowledgeEditUsersEntity setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
		return this;
	}

	/**
	 * キーの値を取得 
	 */
	public Object[] getKeyValues() {
		Object[] keyValues = new Object[2];
		keyValues[0] = this.knowledgeId;
		keyValues[1] = this.userId;
		return keyValues;
	}
	/**
	 * キーの値を設定 
	 * @param knowledgeId ナレッジID
	 * @param userId USER_ID
	 */
	public void setKeyValues(Long knowledgeId, Integer userId) {
		this.knowledgeId = knowledgeId;
		this.userId = userId;
	}
	/**
	 * キーで比較 
	 */
	public boolean equalsOnKey(GenKnowledgeEditUsersEntity entity) {
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
		builder.append("userId = ").append(userId).append("\n");
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
		error = validator.validate(this.knowledgeId, convLabelName("Knowledge Id"));
		if (error != null) {
			errors.add(error);
		}
		validator = ValidatorFactory.getInstance(Validator.REQUIRED);
		error = validator.validate(this.userId, convLabelName("User Id"));
		if (error != null) {
			errors.add(error);
		}
		validator = ValidatorFactory.getInstance(Validator.INTEGER);
		error = validator.validate(this.userId, convLabelName("User Id"));
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
		error = validator.validate(values.get("knowledgeId"), convLabelName("Knowledge Id"));
		if (error != null) {
			errors.add(error);
		}
		validator = ValidatorFactory.getInstance(Validator.REQUIRED);
		error = validator.validate(values.get("userId"), convLabelName("User Id"));
		if (error != null) {
			errors.add(error);
		}
		validator = ValidatorFactory.getInstance(Validator.INTEGER);
		error = validator.validate(values.get("userId"), convLabelName("User Id"));
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
