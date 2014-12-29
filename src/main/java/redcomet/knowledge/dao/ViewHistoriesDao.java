package redcomet.knowledge.dao;

import redcomet.di.Container;
import redcomet.di.DI;
import redcomet.di.Instance;

import redcomet.knowledge.dao.gen.GenViewHistoriesDao;

/**
 * ナレッジの参照履歴
 */
@DI(instance=Instance.Singleton)
public class ViewHistoriesDao extends GenViewHistoriesDao {

	/** SerialVersion */
	private static final long serialVersionUID = 1L;
	/**
	 * インスタンス取得
	 * AOPに対応
	 * @return インスタンス
	 */
	public static ViewHistoriesDao get() {
		return Container.getComp(ViewHistoriesDao.class);
	}



}
