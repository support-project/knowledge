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
 * ナレッジの項目値
 */
@DI(instance=Instance.Prototype)
public class GenKnowledgeItemValuesEntity implements Serializable {

	/** SerialVersion */
	private static final long serialVersionUID = 1L;

	/**
	 * インスタンス取得
	 * AOPに対応
	 * @return インスタンス
	 */
	public static GenKnowledgeItemValuesEntity get() {
		return Container.getComp(GenKnowledgeItemValuesEntity.class);
	}

	/**
	 * コンストラクタ
	 */
	public GenKnowledgeItemValuesEntity() {
		super();
	}

	/**
	 * コンストラクタ
	 * @param itemNo 項目NO
	 * @param knowledgeId ナレッジID
	 * @param typeId テンプレートの種類ID
	 */

	public GenKnowledgeItemValuesEntity(Integer itemNo, Long knowledgeId, Integer typeId) {
		super();
		this.itemNo = itemNo;
		this.knowledgeId = knowledgeId;
		this.typeId = typeId;
	}
	/** ナレッジID */
	private Long knowledgeId;
	/** テンプレートの種類ID */
	private Integer typeId;
	/** 項目NO */
	private Integer itemNo;
	/** 項目値 */
	private String itemValue;
	/** ステータス */
	private Integer itemStatus;
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
	public GenKnowledgeItemValuesEntity setKnowledgeId(Long knowledgeId) {
		this.knowledgeId = knowledgeId;
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
	public GenKnowledgeItemValuesEntity setTypeId(Integer typeId) {
		this.typeId = typeId;
		return this;
	}

	/**
	 * 項目NO を取得する
	 */
	public Integer getItemNo() {
		return this.itemNo;
	}
	/**
	 * 項目NO を設定する
	 * @param itemNo 項目NO
	 */
	public GenKnowledgeItemValuesEntity setItemNo(Integer itemNo) {
		this.itemNo = itemNo;
		return this;
	}

	/**
	 * 項目値 を取得する
	 */
	public String getItemValue() {
		return this.itemValue;
	}
	/**
	 * 項目値 を設定する
	 * @param itemValue 項目値
	 */
	public GenKnowledgeItemValuesEntity setItemValue(String itemValue) {
		this.itemValue = itemValue;
		return this;
	}

	/**
	 * ステータス を取得する
	 */
	public Integer getItemStatus() {
		return this.itemStatus;
	}
	/**
	 * ステータス を設定する
	 * @param itemStatus ステータス
	 */
	public GenKnowledgeItemValuesEntity setItemStatus(Integer itemStatus) {
		this.itemStatus = itemStatus;
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
	public GenKnowledgeItemValuesEntity setInsertUser(Integer insertUser) {
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
	public GenKnowledgeItemValuesEntity setInsertDatetime(Timestamp insertDatetime) {
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
	public GenKnowledgeItemValuesEntity setUpdateUser(Integer updateUser) {
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
	public GenKnowledgeItemValuesEntity setUpdateDatetime(Timestamp updateDatetime) {
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
	public GenKnowledgeItemValuesEntity setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
		return this;
	}

	/**
	 * キーの値を取得 
	 */
	public Object[] getKeyValues() {
		Object[] keyValues = new Object[3];
		keyValues[0] = this.itemNo;
		keyValues[1] = this.knowledgeId;
		keyValues[2] = this.typeId;
		return keyValues;
	}
	/**
	 * キーの値を設定 
	 * @param itemNo 項目NO
	 * @param knowledgeId ナレッジID
	 * @param typeId テンプレートの種類ID
	 */
	public void setKeyValues(Integer itemNo, Long knowledgeId, Integer typeId) {
		this.itemNo = itemNo;
		this.knowledgeId = knowledgeId;
		this.typeId = typeId;
	}
	/**
	 * キーで比較 
	 */
	public boolean equalsOnKey(GenKnowledgeItemValuesEntity entity) {
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
		builder.append("itemNo = ").append(itemNo).append("\n");
		builder.append("knowledgeId = ").append(knowledgeId).append("\n");
		builder.append("typeId = ").append(typeId).append("\n");
		builder.append("itemValue = ").append(itemValue).append("\n");
		builder.append("itemStatus = ").append(itemStatus).append("\n");
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
		error = validator.validate(this.typeId, convLabelName("Type Id"));
		if (error != null) {
			errors.add(error);
		}
		validator = ValidatorFactory.getInstance(Validator.INTEGER);
		error = validator.validate(this.typeId, convLabelName("Type Id"));
		if (error != null) {
			errors.add(error);
		}
		validator = ValidatorFactory.getInstance(Validator.REQUIRED);
		error = validator.validate(this.itemNo, convLabelName("Item No"));
		if (error != null) {
			errors.add(error);
		}
		validator = ValidatorFactory.getInstance(Validator.INTEGER);
		error = validator.validate(this.itemNo, convLabelName("Item No"));
		if (error != null) {
			errors.add(error);
		}
		validator = ValidatorFactory.getInstance(Validator.REQUIRED);
		error = validator.validate(this.itemStatus, convLabelName("Item Status"));
		if (error != null) {
			errors.add(error);
		}
		validator = ValidatorFactory.getInstance(Validator.INTEGER);
		error = validator.validate(this.itemStatus, convLabelName("Item Status"));
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
		error = validator.validate(values.get("typeId"), convLabelName("Type Id"));
		if (error != null) {
			errors.add(error);
		}
		validator = ValidatorFactory.getInstance(Validator.INTEGER);
		error = validator.validate(values.get("typeId"), convLabelName("Type Id"));
		if (error != null) {
			errors.add(error);
		}
		validator = ValidatorFactory.getInstance(Validator.REQUIRED);
		error = validator.validate(values.get("itemNo"), convLabelName("Item No"));
		if (error != null) {
			errors.add(error);
		}
		validator = ValidatorFactory.getInstance(Validator.INTEGER);
		error = validator.validate(values.get("itemNo"), convLabelName("Item No"));
		if (error != null) {
			errors.add(error);
		}
		validator = ValidatorFactory.getInstance(Validator.REQUIRED);
		error = validator.validate(values.get("itemStatus"), convLabelName("Item Status"));
		if (error != null) {
			errors.add(error);
		}
		validator = ValidatorFactory.getInstance(Validator.INTEGER);
		error = validator.validate(values.get("itemStatus"), convLabelName("Item Status"));
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
