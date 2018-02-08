package org.support.project.web.dao;

import java.lang.invoke.MethodHandles;

import org.support.project.aop.Aspect;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.web.dao.gen.GenUserConfigsDao;

/**
 * ユーザ設定
 */
@DI(instance = Instance.Singleton)
public class UserConfigsDao extends GenUserConfigsDao {
    /** LOG */
    private static final Log LOG = LogFactory.getLog(MethodHandles.lookup());

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * インスタンス取得 AOPに対応
     * 
     * @return インスタンス
     */
    public static UserConfigsDao get() {
        return Container.getComp(UserConfigsDao.class);
    }

    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void removeAllUserConfig(String systemName, String configName) {
        LOG.warn("Remove all user config on " + configName);
        String sql = "DELETE FROM USER_CONFIGS WHERE SYSTEM_NAME = ? AND CONFIG_NAME = ?";
        executeUpdate(sql, systemName, configName);
    }

}
