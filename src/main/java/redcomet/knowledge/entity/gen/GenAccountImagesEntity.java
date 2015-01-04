package redcomet.knowledge.entity.gen;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import java.io.InputStream;
import java.sql.Timestamp;



import redcomet.common.bean.ValidateError;
import redcomet.common.validate.Validator;
import redcomet.common.validate.ValidatorFactory;
import redcomet.di.Container;
import redcomet.di.DI;
import redcomet.di.Instance;

/**
 * アカウントの画像
 */
@DI(instance=Instance.Prototype)
public class GenAccountImagesEntity implements Serializable {

	/** SerialVersion */
	private static final long serialVersionUID = 1L;

	/**
	 * インスタンス取得
	 * AOPに対応
	 * @return インスタンス
	 */
	public static GenAccountImagesEntity get() {
		return Container.getComp(GenAccountImagesEntity.class);
	}

	/**
	 * コンストラクタ
	 */
	public GenAccountImagesEntity() {
		super();
	}

	/**
	 * コンストラクタ
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
	 * IMAGE_ID を取得する
	 */
	public Long getImageId() {
		return this.imageId;
	}
	/**
	 * IMAGE_ID を設定する
	 * @param imageId IMAGE_ID
	 */
	public GenAccountImagesEntity setImageId(Long imageId) {
		this.imageId = imageId;
		return this;
	}

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
	public GenAccountImagesEntity setUserId(Integer userId) {
		this.userId = userId;
		return this;
	}

	/**
	 * ファイル名 を取得する
	 */
	public String getFileName() {
		return this.fileName;
	}
	/**
	 * ファイル名 を設定する
	 * @param fileName ファイル名
	 */
	public GenAccountImagesEntity setFileName(String fileName) {
		this.fileName = fileName;
		return this;
	}

	/**
	 * ファイルサイズ を取得する
	 */
	public Double getFileSize() {
		return this.fileSize;
	}
	/**
	 * ファイルサイズ を設定する
	 * @param fileSize ファイルサイズ
	 */
	public GenAccountImagesEntity setFileSize(Double fileSize) {
		this.fileSize = fileSize;
		return this;
	}

	/**
	 * バイナリ を取得する
	 */
	public InputStream getFileBinary() {
		return this.fileBinary;
	}
	/**
	 * バイナリ を設定する
	 * @param fileBinary バイナリ
	 */
	public GenAccountImagesEntity setFileBinary(InputStream fileBinary) {
		this.fileBinary = fileBinary;
		return this;
	}

	/**
	 * 拡張子 を取得する
	 */
	public String getExtension() {
		return this.extension;
	}
	/**
	 * 拡張子 を設定する
	 * @param extension 拡張子
	 */
	public GenAccountImagesEntity setExtension(String extension) {
		this.extension = extension;
		return this;
	}

	/**
	 * CONTENT_TYPE を取得する
	 */
	public String getContentType() {
		return this.contentType;
	}
	/**
	 * CONTENT_TYPE を設定する
	 * @param contentType CONTENT_TYPE
	 */
	public GenAccountImagesEntity setContentType(String contentType) {
		this.contentType = contentType;
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
	public GenAccountImagesEntity setInsertUser(Integer insertUser) {
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
	public GenAccountImagesEntity setInsertDatetime(Timestamp insertDatetime) {
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
	public GenAccountImagesEntity setUpdateUser(Integer updateUser) {
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
	public GenAccountImagesEntity setUpdateDatetime(Timestamp updateDatetime) {
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
	public GenAccountImagesEntity setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
		return this;
	}

	/**
	 * キーの値を取得 
	 */
	public Object[] getKeyValues() {
		Object[] keyValues = new Object[1];
		keyValues[0] = this.imageId;
		return keyValues;
	}
	/**
	 * キーの値を設定 
	 * @param imageId IMAGE_ID
	 */
	public void setKeyValues(Long imageId) {
		this.imageId = imageId;
	}
	/**
	 * キーで比較 
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
