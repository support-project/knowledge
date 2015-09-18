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
 * 選択肢の値
 */
@DI(instance=Instance.Prototype)
public class GenItemChoicesEntity implements Serializable {

	/** SerialVersion */
	private static final long serialVersionUID = 1L;

	/**
	 * インスタンス取得
	 * AOPに対応
	 * @return インスタンス
	 */
	public static GenItemChoicesEntity get() {
		return Container.getComp(GenItemChoicesEntity.class);
	}

	/**
	 * コンストラクタ
	 */
	public GenItemChoicesEntity() {
		super();
	}

	/**
	 * コンストラクタ
	 * @param choiceNo 選択肢番号
	 * @param itemNo 項目NO
	 * @param typeId テンプレートの種類ID
	 */

	public GenItemChoicesEntity(Integer choiceNo, Integer itemNo, Integer typeId) {
		super();
		this.choiceNo = choiceNo;
		this.itemNo = itemNo;
		this.typeId = typeId;
	}
	/** テンプレートの種類ID */
	private Integer typeId;
	/** 項目NO */
	private Integer itemNo;
	/** 選択肢番号 */
	private Integer choiceNo;
	/** 選択肢値 */
	private String choiceValue;
	/** 選択肢ラベル */
	private String choiceLabel;
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
	 * テンプレートの種類ID を取得する
	 */
	public Integer getTypeId() {
		return this.typeId;
	}
	/**
	 * テンプレートの種類ID を設定する
	 * @param typeId テンプレートの種類ID
	 */
	public GenItemChoicesEntity setTypeId(Integer typeId) {
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
	public GenItemChoicesEntity setItemNo(Integer itemNo) {
		this.itemNo = itemNo;
		return this;
	}

	/**
	 * 選択肢番号 を取得する
	 */
	public Integer getChoiceNo() {
		return this.choiceNo;
	}
	/**
	 * 選択肢番号 を設定する
	 * @param choiceNo 選択肢番号
	 */
	public GenItemChoicesEntity setChoiceNo(Integer choiceNo) {
		this.choiceNo = choiceNo;
		return this;
	}

	/**
	 * 選択肢値 を取得する
	 */
	public String getChoiceValue() {
		return this.choiceValue;
	}
	/**
	 * 選択肢値 を設定する
	 * @param choiceValue 選択肢値
	 */
	public GenItemChoicesEntity setChoiceValue(String choiceValue) {
		this.choiceValue = choiceValue;
		return this;
	}

	/**
	 * 選択肢ラベル を取得する
	 */
	public String getChoiceLabel() {
		return this.choiceLabel;
	}
	/**
	 * 選択肢ラベル を設定する
	 * @param choiceLabel 選択肢ラベル
	 */
	public GenItemChoicesEntity setChoiceLabel(String choiceLabel) {
		this.choiceLabel = choiceLabel;
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
	public GenItemChoicesEntity setInsertUser(Integer insertUser) {
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
	public GenItemChoicesEntity setInsertDatetime(Timestamp insertDatetime) {
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
	public GenItemChoicesEntity setUpdateUser(Integer updateUser) {
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
	public GenItemChoicesEntity setUpdateDatetime(Timestamp updateDatetime) {
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
	public GenItemChoicesEntity setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
		return this;
	}

	/**
	 * キーの値を取得 
	 */
	public Object[] getKeyValues() {
		Object[] keyValues = new Object[3];
		keyValues[0] = this.choiceNo;
		keyValues[1] = this.itemNo;
		keyValues[2] = this.typeId;
		return keyValues;
	}
	/**
	 * キーの値を設定 
	 * @param choiceNo 選択肢番号
	 * @param itemNo 項目NO
	 * @param typeId テンプレートの種類ID
	 */
	public void setKeyValues(Integer choiceNo, Integer itemNo, Integer typeId) {
		this.choiceNo = choiceNo;
		this.itemNo = itemNo;
		this.typeId = typeId;
	}
	/**
	 * キーで比較 
	 */
	public boolean equalsOnKey(GenItemChoicesEntity entity) {
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
		builder.append("choiceNo = ").append(choiceNo).append("\n");
		builder.append("itemNo = ").append(itemNo).append("\n");
		builder.append("typeId = ").append(typeId).append("\n");
		builder.append("choiceValue = ").append(choiceValue).append("\n");
		builder.append("choiceLabel = ").append(choiceLabel).append("\n");
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
		error = validator.validate(this.choiceNo, convLabelName("Choice No"));
		if (error != null) {
			errors.add(error);
		}
		validator = ValidatorFactory.getInstance(Validator.INTEGER);
		error = validator.validate(this.choiceNo, convLabelName("Choice No"));
		if (error != null) {
			errors.add(error);
		}
		validator = ValidatorFactory.getInstance(Validator.REQUIRED);
		error = validator.validate(this.choiceValue, convLabelName("Choice Value"));
		if (error != null) {
			errors.add(error);
		}
		validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
		error = validator.validate(this.choiceValue, convLabelName("Choice Value"), 256);
		if (error != null) {
			errors.add(error);
		}
		validator = ValidatorFactory.getInstance(Validator.REQUIRED);
		error = validator.validate(this.choiceLabel, convLabelName("Choice Label"));
		if (error != null) {
			errors.add(error);
		}
		validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
		error = validator.validate(this.choiceLabel, convLabelName("Choice Label"), 256);
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
		error = validator.validate(values.get("choiceNo"), convLabelName("Choice No"));
		if (error != null) {
			errors.add(error);
		}
		validator = ValidatorFactory.getInstance(Validator.INTEGER);
		error = validator.validate(values.get("choiceNo"), convLabelName("Choice No"));
		if (error != null) {
			errors.add(error);
		}
		validator = ValidatorFactory.getInstance(Validator.REQUIRED);
		error = validator.validate(values.get("choiceValue"), convLabelName("Choice Value"));
		if (error != null) {
			errors.add(error);
		}
		validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
		error = validator.validate(values.get("choiceValue"), convLabelName("Choice Value"), 256);
		if (error != null) {
			errors.add(error);
		}
		validator = ValidatorFactory.getInstance(Validator.REQUIRED);
		error = validator.validate(values.get("choiceLabel"), convLabelName("Choice Label"));
		if (error != null) {
			errors.add(error);
		}
		validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
		error = validator.validate(values.get("choiceLabel"), convLabelName("Choice Label"), 256);
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
