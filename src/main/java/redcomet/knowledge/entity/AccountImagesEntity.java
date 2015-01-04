package redcomet.knowledge.entity;

import redcomet.knowledge.entity.gen.GenAccountImagesEntity;

import java.util.List;
import java.util.Map;

import redcomet.common.bean.ValidateError;
import redcomet.di.Container;
import redcomet.di.DI;
import redcomet.di.Instance;

import java.io.InputStream;
import java.sql.Timestamp;


/**
 * アカウントの画像
 */
@DI(instance=Instance.Prototype)
public class AccountImagesEntity extends GenAccountImagesEntity {

	/** SerialVersion */
	private static final long serialVersionUID = 1L;

	/**
	 * インスタンス取得
	 * AOPに対応
	 * @return インスタンス
	 */
	public static AccountImagesEntity get() {
		return Container.getComp(AccountImagesEntity.class);
	}

	/**
	 * コンストラクタ
	 */
	public AccountImagesEntity() {
		super();
	}

	/**
	 * コンストラクタ
	 * @param imageId IMAGE_ID
	 */

	public AccountImagesEntity(Long imageId) {
		super( imageId);
	}

}
