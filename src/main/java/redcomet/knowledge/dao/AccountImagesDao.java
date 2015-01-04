package redcomet.knowledge.dao;

import redcomet.di.Container;
import redcomet.di.DI;
import redcomet.di.Instance;
import redcomet.knowledge.dao.gen.GenAccountImagesDao;
import redcomet.knowledge.entity.AccountImagesEntity;

/**
 * アカウントの画像
 */
@DI(instance=Instance.Singleton)
public class AccountImagesDao extends GenAccountImagesDao {

	/** SerialVersion */
	private static final long serialVersionUID = 1L;
	/**
	 * インスタンス取得
	 * AOPに対応
	 * @return インスタンス
	 */
	public static AccountImagesDao get() {
		return Container.getComp(AccountImagesDao.class);
	}
	public AccountImagesEntity selectOnUserId(Integer userId) {
		String sql = "SELECT * FROM ACCOUNT_IMAGES WHERE USER_ID = ?";
		return executeQuerySingle(sql, AccountImagesEntity.class, userId);
	}



}
