package redcomet.knowledge.logic;

import redcomet.common.log.Log;
import redcomet.common.log.LogFactory;
import redcomet.di.Container;
import redcomet.knowledge.config.AppConfig;
import redcomet.knowledge.config.SystemConfig;
import redcomet.web.dao.SystemConfigsDao;
import redcomet.web.entity.SystemConfigsEntity;

public class SystemConfigLogic {
	/** ログ */
	private static Log LOG = LogFactory.getLog(SystemConfigLogic.class);

	public static SystemConfigLogic get() {
		return Container.getComp(SystemConfigLogic.class);
	}
	
	/**
	 * ユーザ自身の手でユーザ追加のリクエストを出せるかチェック
	 * @return
	 */
	public boolean isUserAddAble() {
		SystemConfigsDao systemConfigsDao = SystemConfigsDao.get();
		SystemConfigsEntity userAddType = systemConfigsDao.selectOnKey(SystemConfig.USER_ADD_TYPE, AppConfig.SYSTEM_NAME);
		if (userAddType == null) {
			return false;
		}
		if (userAddType.getConfigValue().equals(SystemConfig.USER_ADD_TYPE_VALUE_ADMIN)) {
			return false;
		}
		return true;
	}
	
	
	
}
