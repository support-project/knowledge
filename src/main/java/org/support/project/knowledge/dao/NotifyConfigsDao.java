package org.support.project.knowledge.dao;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;

import org.support.project.knowledge.dao.gen.GenNotifyConfigsDao;

/**
 * 通知設定
 */
@DI(instance=Instance.Singleton)
public class NotifyConfigsDao extends GenNotifyConfigsDao {

	/** SerialVersion */
	private static final long serialVersionUID = 1L;
	/**
	 * インスタンス取得
	 * AOPに対応
	 * @return インスタンス
	 */
	public static NotifyConfigsDao get() {
		return Container.getComp(NotifyConfigsDao.class);
	}


	/**
	 * ID 
	 */
	private int currentId = 0;

	/**
	 * IDを採番 
	 * ※コミットしなくても次のIDを採番する為、保存しなければ欠番になる 
	 */
	public Integer getNextId() {
		String sql = "SELECT MAX(USER_ID) FROM NOTIFY_CONFIGS;";
		Integer integer = executeQuerySingle(sql, Integer.class);
		if (integer != null && currentId < integer) {
			currentId = integer;
		}
		currentId++;
		return currentId;
	}


}
