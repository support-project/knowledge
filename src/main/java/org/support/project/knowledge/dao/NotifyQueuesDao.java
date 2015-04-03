package org.support.project.knowledge.dao;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;

import org.support.project.knowledge.dao.gen.GenNotifyQueuesDao;

/**
 * 通知待ちキュー
 */
@DI(instance=Instance.Singleton)
public class NotifyQueuesDao extends GenNotifyQueuesDao {

	/** SerialVersion */
	private static final long serialVersionUID = 1L;
	/**
	 * インスタンス取得
	 * AOPに対応
	 * @return インスタンス
	 */
	public static NotifyQueuesDao get() {
		return Container.getComp(NotifyQueuesDao.class);
	}



}
