package org.support.project.web.dao;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.web.dao.gen.GenSystemConfigsDao;

/**
 * コンフィグ
 */
@DI(instance = Instance.Singleton)
public class SystemConfigsDao extends GenSystemConfigsDao {

	/** SerialVersion */
	private static final long serialVersionUID = 1L;
	/**
	 * インスタンス取得
	 * AOPに対応
	 * @return インスタンス
	 */
	public static SystemConfigsDao get() {
		return Container.getComp(SystemConfigsDao.class);
	}



}
