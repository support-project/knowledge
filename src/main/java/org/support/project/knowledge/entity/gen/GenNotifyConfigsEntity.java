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
 * 通知設定
 */
@DI(instance=Instance.Prototype)
public class GenNotifyConfigsEntity implements Serializable {

	/** SerialVersion */
	private static final long serialVersionUID = 1L;

	/**
	 * インスタンス取得
	 * AOPに対応
	 * @return インスタンス
	 */
	public static GenNotifyConfigsEntity get() {
		return Container.getComp(GenNotifyConfigsEntity.class);
	}

	/**
	 * コンストラクタ
	 */
	public GenNotifyConfigsEntity() {
		super();
	}

	/**
	 * コンストラクタ
	 * @param userId ユーザID
	 */

	public GenNotifyConfigsEntity(Integer userId) {
		super();
		this.userId = userId;
	}
	/** ユーザID */
	private Integer userId;
	/** メール通知する */
	private Integer notifyMail;
	/** デスクトップ通知する */
	private Integer notifyDesktop;
	/** 自分が登録した投稿にコメントが登録されたら通知 */
	private Integer myItemComment;
	/** 自分が登録した投稿にいいねが追加されたら通知 */
	private Integer myItemLike;
	/** 自分が登録した投稿がストックされたら通知 */
	private Integer myItemStock;
	/** 自分宛の投稿が更新されたら通知 */
	private Integer toItemSave;
	/** 自分宛の投稿にコメントが登録されたら通知 */
	private Integer toItemComment;
	/** 自分宛の投稿で「公開」は除外 */
	private Integer toItemIgnorePublic;
	/** ストックしたナレッジが更新されたら通知 */
	private Integer stockItemSave;
	/** ストックしたナレッジにコメントが登録されたら通知 */
	private Integer stokeItemComment;
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
	 * ユーザID を取得する
	 */
	public Integer getUserId() {
		return this.userId;
	}
	/**
	 * ユーザID を設定する
	 * @param userId ユーザID
	 */
	public GenNotifyConfigsEntity setUserId(Integer userId) {
		this.userId = userId;
		return this;
	}

	/**
	 * メール通知する を取得する
	 */
	public Integer getNotifyMail() {
		return this.notifyMail;
	}
	/**
	 * メール通知する を設定する
	 * @param notifyMail メール通知する
	 */
	public GenNotifyConfigsEntity setNotifyMail(Integer notifyMail) {
		this.notifyMail = notifyMail;
		return this;
	}

	/**
	 * デスクトップ通知する を取得する
	 */
	public Integer getNotifyDesktop() {
		return this.notifyDesktop;
	}
	/**
	 * デスクトップ通知する を設定する
	 * @param notifyDesktop デスクトップ通知する
	 */
	public GenNotifyConfigsEntity setNotifyDesktop(Integer notifyDesktop) {
		this.notifyDesktop = notifyDesktop;
		return this;
	}

	/**
	 * 自分が登録した投稿にコメントが登録されたら通知 を取得する
	 */
	public Integer getMyItemComment() {
		return this.myItemComment;
	}
	/**
	 * 自分が登録した投稿にコメントが登録されたら通知 を設定する
	 * @param myItemComment 自分が登録した投稿にコメントが登録されたら通知
	 */
	public GenNotifyConfigsEntity setMyItemComment(Integer myItemComment) {
		this.myItemComment = myItemComment;
		return this;
	}

	/**
	 * 自分が登録した投稿にいいねが追加されたら通知 を取得する
	 */
	public Integer getMyItemLike() {
		return this.myItemLike;
	}
	/**
	 * 自分が登録した投稿にいいねが追加されたら通知 を設定する
	 * @param myItemLike 自分が登録した投稿にいいねが追加されたら通知
	 */
	public GenNotifyConfigsEntity setMyItemLike(Integer myItemLike) {
		this.myItemLike = myItemLike;
		return this;
	}

	/**
	 * 自分が登録した投稿がストックされたら通知 を取得する
	 */
	public Integer getMyItemStock() {
		return this.myItemStock;
	}
	/**
	 * 自分が登録した投稿がストックされたら通知 を設定する
	 * @param myItemStock 自分が登録した投稿がストックされたら通知
	 */
	public GenNotifyConfigsEntity setMyItemStock(Integer myItemStock) {
		this.myItemStock = myItemStock;
		return this;
	}

	/**
	 * 自分宛の投稿が更新されたら通知 を取得する
	 */
	public Integer getToItemSave() {
		return this.toItemSave;
	}
	/**
	 * 自分宛の投稿が更新されたら通知 を設定する
	 * @param toItemSave 自分宛の投稿が更新されたら通知
	 */
	public GenNotifyConfigsEntity setToItemSave(Integer toItemSave) {
		this.toItemSave = toItemSave;
		return this;
	}

	/**
	 * 自分宛の投稿にコメントが登録されたら通知 を取得する
	 */
	public Integer getToItemComment() {
		return this.toItemComment;
	}
	/**
	 * 自分宛の投稿にコメントが登録されたら通知 を設定する
	 * @param toItemComment 自分宛の投稿にコメントが登録されたら通知
	 */
	public GenNotifyConfigsEntity setToItemComment(Integer toItemComment) {
		this.toItemComment = toItemComment;
		return this;
	}

	/**
	 * 自分宛の投稿で「公開」は除外 を取得する
	 */
	public Integer getToItemIgnorePublic() {
		return this.toItemIgnorePublic;
	}
	/**
	 * 自分宛の投稿で「公開」は除外 を設定する
	 * @param toItemIgnorePublic 自分宛の投稿で「公開」は除外
	 */
	public GenNotifyConfigsEntity setToItemIgnorePublic(Integer toItemIgnorePublic) {
		this.toItemIgnorePublic = toItemIgnorePublic;
		return this;
	}

	/**
	 * ストックしたナレッジが更新されたら通知 を取得する
	 */
	public Integer getStockItemSave() {
		return this.stockItemSave;
	}
	/**
	 * ストックしたナレッジが更新されたら通知 を設定する
	 * @param stockItemSave ストックしたナレッジが更新されたら通知
	 */
	public GenNotifyConfigsEntity setStockItemSave(Integer stockItemSave) {
		this.stockItemSave = stockItemSave;
		return this;
	}

	/**
	 * ストックしたナレッジにコメントが登録されたら通知 を取得する
	 */
	public Integer getStokeItemComment() {
		return this.stokeItemComment;
	}
	/**
	 * ストックしたナレッジにコメントが登録されたら通知 を設定する
	 * @param stokeItemComment ストックしたナレッジにコメントが登録されたら通知
	 */
	public GenNotifyConfigsEntity setStokeItemComment(Integer stokeItemComment) {
		this.stokeItemComment = stokeItemComment;
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
	public GenNotifyConfigsEntity setInsertUser(Integer insertUser) {
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
	public GenNotifyConfigsEntity setInsertDatetime(Timestamp insertDatetime) {
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
	public GenNotifyConfigsEntity setUpdateUser(Integer updateUser) {
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
	public GenNotifyConfigsEntity setUpdateDatetime(Timestamp updateDatetime) {
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
	public GenNotifyConfigsEntity setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
		return this;
	}

	/**
	 * キーの値を取得 
	 */
	public Object[] getKeyValues() {
		Object[] keyValues = new Object[1];
		keyValues[0] = this.userId;
		return keyValues;
	}
	/**
	 * キーの値を設定 
	 * @param userId ユーザID
	 */
	public void setKeyValues(Integer userId) {
		this.userId = userId;
	}
	/**
	 * キーで比較 
	 */
	public boolean equalsOnKey(GenNotifyConfigsEntity entity) {
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
		builder.append("userId = ").append(userId).append("\n");
		builder.append("notifyMail = ").append(notifyMail).append("\n");
		builder.append("notifyDesktop = ").append(notifyDesktop).append("\n");
		builder.append("myItemComment = ").append(myItemComment).append("\n");
		builder.append("myItemLike = ").append(myItemLike).append("\n");
		builder.append("myItemStock = ").append(myItemStock).append("\n");
		builder.append("toItemSave = ").append(toItemSave).append("\n");
		builder.append("toItemComment = ").append(toItemComment).append("\n");
		builder.append("toItemIgnorePublic = ").append(toItemIgnorePublic).append("\n");
		builder.append("stockItemSave = ").append(stockItemSave).append("\n");
		builder.append("stokeItemComment = ").append(stokeItemComment).append("\n");
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
		error = validator.validate(this.notifyMail, convLabelName("Notify Mail"));
		if (error != null) {
			errors.add(error);
		}
		validator = ValidatorFactory.getInstance(Validator.INTEGER);
		error = validator.validate(this.notifyDesktop, convLabelName("Notify Desktop"));
		if (error != null) {
			errors.add(error);
		}
		validator = ValidatorFactory.getInstance(Validator.INTEGER);
		error = validator.validate(this.myItemComment, convLabelName("My Item Comment"));
		if (error != null) {
			errors.add(error);
		}
		validator = ValidatorFactory.getInstance(Validator.INTEGER);
		error = validator.validate(this.myItemLike, convLabelName("My Item Like"));
		if (error != null) {
			errors.add(error);
		}
		validator = ValidatorFactory.getInstance(Validator.INTEGER);
		error = validator.validate(this.myItemStock, convLabelName("My Item Stock"));
		if (error != null) {
			errors.add(error);
		}
		validator = ValidatorFactory.getInstance(Validator.INTEGER);
		error = validator.validate(this.toItemSave, convLabelName("To Item Save"));
		if (error != null) {
			errors.add(error);
		}
		validator = ValidatorFactory.getInstance(Validator.INTEGER);
		error = validator.validate(this.toItemComment, convLabelName("To Item Comment"));
		if (error != null) {
			errors.add(error);
		}
		validator = ValidatorFactory.getInstance(Validator.INTEGER);
		error = validator.validate(this.toItemIgnorePublic, convLabelName("To Item Ignore Public"));
		if (error != null) {
			errors.add(error);
		}
		validator = ValidatorFactory.getInstance(Validator.INTEGER);
		error = validator.validate(this.stockItemSave, convLabelName("Stock Item Save"));
		if (error != null) {
			errors.add(error);
		}
		validator = ValidatorFactory.getInstance(Validator.INTEGER);
		error = validator.validate(this.stokeItemComment, convLabelName("Stoke Item Comment"));
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
		error = validator.validate(values.get("notifyMail"), convLabelName("Notify Mail"));
		if (error != null) {
			errors.add(error);
		}
		validator = ValidatorFactory.getInstance(Validator.INTEGER);
		error = validator.validate(values.get("notifyDesktop"), convLabelName("Notify Desktop"));
		if (error != null) {
			errors.add(error);
		}
		validator = ValidatorFactory.getInstance(Validator.INTEGER);
		error = validator.validate(values.get("myItemComment"), convLabelName("My Item Comment"));
		if (error != null) {
			errors.add(error);
		}
		validator = ValidatorFactory.getInstance(Validator.INTEGER);
		error = validator.validate(values.get("myItemLike"), convLabelName("My Item Like"));
		if (error != null) {
			errors.add(error);
		}
		validator = ValidatorFactory.getInstance(Validator.INTEGER);
		error = validator.validate(values.get("myItemStock"), convLabelName("My Item Stock"));
		if (error != null) {
			errors.add(error);
		}
		validator = ValidatorFactory.getInstance(Validator.INTEGER);
		error = validator.validate(values.get("toItemSave"), convLabelName("To Item Save"));
		if (error != null) {
			errors.add(error);
		}
		validator = ValidatorFactory.getInstance(Validator.INTEGER);
		error = validator.validate(values.get("toItemComment"), convLabelName("To Item Comment"));
		if (error != null) {
			errors.add(error);
		}
		validator = ValidatorFactory.getInstance(Validator.INTEGER);
		error = validator.validate(values.get("toItemIgnorePublic"), convLabelName("To Item Ignore Public"));
		if (error != null) {
			errors.add(error);
		}
		validator = ValidatorFactory.getInstance(Validator.INTEGER);
		error = validator.validate(values.get("stockItemSave"), convLabelName("Stock Item Save"));
		if (error != null) {
			errors.add(error);
		}
		validator = ValidatorFactory.getInstance(Validator.INTEGER);
		error = validator.validate(values.get("stokeItemComment"), convLabelName("Stoke Item Comment"));
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
