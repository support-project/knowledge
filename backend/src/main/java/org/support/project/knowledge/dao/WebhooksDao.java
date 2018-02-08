package org.support.project.knowledge.dao;

import java.util.List;

import org.support.project.aop.Aspect;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.dao.gen.GenWebhooksDao;
import org.support.project.knowledge.entity.WebhooksEntity;

/**
 * Webhooks
 */
@DI(instance = Instance.Singleton)
public class WebhooksDao extends GenWebhooksDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * インスタンス取得 AOPに対応
     * 
     * @return インスタンス
     */
    public static WebhooksDao get() {
        return Container.getComp(WebhooksDao.class);
    }

    /**
     * ステータスで取得
     * 
     * @param status
     * @return
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<WebhooksEntity> selectOnStatus(int status) {
        String sql = "SELECT * FROM WEBHOOKS WHERE STATUS = ? AND DELETE_FLAG = 0";
        return executeQueryList(sql, WebhooksEntity.class, status);
    }
}