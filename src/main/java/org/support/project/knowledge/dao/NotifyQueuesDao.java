package org.support.project.knowledge.dao;

import java.util.List;

import org.support.project.aop.Aspect;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.dao.gen.GenNotifyQueuesDao;
import org.support.project.knowledge.entity.NotifyQueuesEntity;

/**
 * 通知待ちキュー
 */
@DI(instance = Instance.Singleton)
public class NotifyQueuesDao extends GenNotifyQueuesDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * インスタンス取得 AOPに対応
     * 
     * @return インスタンス
     */
    public static NotifyQueuesDao get() {
        return Container.getComp(NotifyQueuesDao.class);
    }

    /**
     * Select all data that not deleted.
     * @return all data
     */
    @Override
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<NotifyQueuesEntity> selectAll() { 
        String sql = "SELECT * FROM NOTIFY_QUEUES WHERE DELETE_FLAG = 0 ORDER BY INSERT_DATETIME ASC;";
        return executeQueryList(sql, NotifyQueuesEntity.class);
    }
    
    /**
     * 通知キューに、同一の通知種類／IDの情報が無いか検索する
     * 
     * @param type
     * @param id
     * @return
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public NotifyQueuesEntity selectOnTypeAndId(Integer type, Long id) {
        String sql = "SELECT * FROM NOTIFY_QUEUES WHERE TYPE = ? AND ID = ? AND DELETE_FLAG = 0";
        return super.executeQuerySingle(sql, NotifyQueuesEntity.class, type, id);
    }

}
