package org.support.project.knowledge.dao;

import java.util.List;

import org.support.project.aop.Aspect;
import org.support.project.di.Container;
import org.support.project.knowledge.vo.AccountInfo;
import org.support.project.knowledge.vo.GroupUser;
import org.support.project.ormapping.common.SQLManager;
import org.support.project.web.dao.UsersDao;
import org.support.project.web.entity.UsersEntity;

/**
 * ユーザ情報を取得するDaoのKnowledgeでの拡張
 * 
 * @author Koda
 */
public class ExUsersDao extends UsersDao {
    /** シリアルバージョン */
    private static final long serialVersionUID = 1L;

    /**
     * インスタンス取得 AOPに対応
     * 
     * @return インスタンス
     */
    public static ExUsersDao get() {
        return Container.getComp(ExUsersDao.class);
    }

    /**
     * グループに所属しているユーザを取得
     * 
     * @param groupId
     * @param offset
     * @param limit
     * @return
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<GroupUser> selectGroupUser(Integer groupId, int offset, int limit) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/ExUsersDao/selectGroupUser.sql");
        return executeQueryList(sql, GroupUser.class, groupId, limit, offset);
    }

    /**
     * 公開区分が「公開」のナレッジが登録された場合に、通知を希望しているユーザの一覧を取得
     * 
     * @return
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<UsersEntity> selectNotifyPublicUsers() {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/ExUsersDao/selectNotifyPublicUsers.sql");
        return executeQueryList(sql, UsersEntity.class);
    }

    /**
     * アカウント情報ページに表示するデータを取得
     * 
     * アカウント名 ナレッジ登録件数 イイネをおされた件数 ストックされた件数
     * 
     * @param userId
     * @return
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public AccountInfo selectAccountInfoOnKey(Integer userId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/ExUsersDao/selectAccountInfoOnKey.sql");
        AccountInfo info = executeQuerySingle(sql, AccountInfo.class, userId);
        if (info == null) {
            return null;
        }
        sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/ExUsersDao/selectLikeCountOnAccount.sql");
        Integer likeCount = executeQuerySingle(sql, Integer.class, userId);
        info.setLikeCount(likeCount);

        sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/ExUsersDao/selectStockCountOnAccount.sql");
        Integer stockCount = executeQuerySingle(sql, Integer.class, userId);
        info.setStockCount(stockCount);

        return info;
    }
    
    /**
     * ユーザ名でユーザの情報を取得
     * 同姓同名も存在しうるため、リストで返す
     * @param userName
     * @return
     */
    public List<UsersEntity> selectByUserName(String userName) {
        String sql = "SELECT * FROM USERS WHERE USER_NAME = ?";
        List<UsersEntity> users = executeQueryList(sql, UsersEntity.class, userName);
        for (UsersEntity usersEntity : users) {
            usersEntity.setPassword(""); // パスワードはクリア
        }
        return users;
    }

}
