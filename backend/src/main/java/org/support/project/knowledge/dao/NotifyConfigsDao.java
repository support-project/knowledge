package org.support.project.knowledge.dao;

import java.util.List;

import org.support.project.aop.Aspect;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.dao.gen.GenNotifyConfigsDao;
import org.support.project.ormapping.common.SQLManager;
import org.support.project.web.entity.UsersEntity;

/**
 * 通知設定
 */
@DI(instance = Instance.Singleton)
public class NotifyConfigsDao extends GenNotifyConfigsDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private int currentId = 0;

    /**
     * インスタンス取得 AOPに対応
     * 
     * @return インスタンス
     */
    public static NotifyConfigsDao get() {
        return Container.getComp(NotifyConfigsDao.class);
    }

    /**
     * IDを採番 ※コミットしなくても次のIDを採番する為、保存しなければ欠番になる
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public Integer getNextId() {
        String sql = "SELECT MAX(USER_ID) FROM NOTIFY_CONFIGS;";
        Integer integer = executeQuerySingle(sql, Integer.class);
        if (integer != null && currentId < integer) {
            currentId = integer;
        }
        currentId++;
        return currentId;
    }

    /**
     * 「公開」のナレッジにコメントが追加された際に、「デスクトップ通知」が欲しいユーザの一覧を取得
     * 
     * @param limit
     * @param offset
     * @return
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<UsersEntity> getNotifyDesktopUsersOnPublicComment(int limit, int offset) {
        String sql = SQLManager.getInstance()
                .getSql("/org/support/project/knowledge/dao/sql/NotifyConfigsDao/GetNotifyDesktopUsersOnPublicComment.sql");
        return executeQueryList(sql, UsersEntity.class, limit, offset);
    }

    /**
     * 「公開」のナレッジにコメントが追加された際に、「メール通知」が欲しいユーザの一覧を取得
     * 
     * @param limit
     * @param offset
     * @return
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<UsersEntity> getNotifyMailUsersOnPublicComment(int limit, int offset) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/NotifyConfigsDao/GetNotifyMailUsersOnPublicComment.sql");
        return executeQueryList(sql, UsersEntity.class, limit, offset);
    }

}
