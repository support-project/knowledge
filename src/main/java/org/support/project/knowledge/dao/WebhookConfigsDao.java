package org.support.project.knowledge.dao;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;

import org.support.project.knowledge.dao.gen.GenWebhookConfigsDao;

/**
 * Webhooks 設定
 */
@DI(instance=Instance.Singleton)
public class WebhookConfigsDao extends GenWebhookConfigsDao {

	/** SerialVersion */
	private static final long serialVersionUID = 1L;
	/**
	 * インスタンス取得
	 * AOPに対応
	 * @return インスタンス
	 */
	public static WebhookConfigsDao get() {
		return Container.getComp(WebhookConfigsDao.class);
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
		String sql = "SELECT MAX(HOOK_ID) FROM WEBHOOK_CONFIGS;";
		Integer integer = executeQuerySingle(sql, Integer.class);
		if (integer != null) {
			if (currentId < integer) {
				currentId = integer;
			}
		}
		currentId++;
		return currentId;
	}


}
