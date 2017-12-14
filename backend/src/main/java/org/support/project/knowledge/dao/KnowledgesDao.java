package org.support.project.knowledge.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.support.project.aop.Aspect;
import org.support.project.common.util.DateUtils;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.dao.gen.GenKnowledgesDao;
import org.support.project.knowledge.entity.KnowledgesEntity;
import org.support.project.ormapping.common.SQLManager;
import org.support.project.web.bean.LoginedUser;
import org.support.project.web.entity.GroupsEntity;

/**
 * ナレッジ
 */
@DI(instance = Instance.Singleton)
public class KnowledgesDao extends GenKnowledgesDao {

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
    public static KnowledgesDao get() {
        return Container.getComp(KnowledgesDao.class);
    }

    /**
     * IDを採番 ※コミットしなくても次のIDを採番する為、保存しなければ欠番になる
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public Integer getNextId() {
        String sql = "SELECT MAX(KNOWLEDGE_ID) FROM KNOWLEDGES;";
        Integer integer = executeQuerySingle(sql, Integer.class);
        if (integer != null && currentId < integer) {
            currentId = integer;
        }
        currentId++;
        return currentId;
    }

    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<KnowledgesEntity> selectKnowledge(int offset, int limit, Integer userId) {
        // String sql = "SELECT * FROM KNOWLEDGES WHERE DELETE_FLAG = 0 ORDER BY UPDATE_DATETIME DESC Limit ? offset ?;";
        String sql = SQLManager.getInstance()
                .getSql("/org/support/project/knowledge/dao/sql/KnowledgesDao/KnowledgesDao_selectKnowledgeWithUserName.sql");
        return executeQueryList(sql, KnowledgesEntity.class, limit, offset);
    }

    /**
     * ナレッジIDを複数渡して、その情報を取得
     * 
     * @param knowledgeIds
     * @return
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<KnowledgesEntity> selectKnowledges(List<Long> knowledgeIds) {
        if (knowledgeIds == null || knowledgeIds.isEmpty()) {
            return new ArrayList<KnowledgesEntity>();
        }
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append(" KNOWLEDGES.*");
        sql.append(" ,USERS.USER_NAME AS INSERT_USER_NAME");
        sql.append(" ,UP_USER.USER_NAME AS UPDATE_USER_NAME");
        sql.append(" FROM");
        sql.append(" KNOWLEDGES");
        sql.append(" LEFT OUTER JOIN USERS");
        sql.append(" ON USERS.USER_ID = KNOWLEDGES.INSERT_USER");
        sql.append(" LEFT OUTER JOIN USERS AS UP_USER");
        sql.append(" ON UP_USER.USER_ID = KNOWLEDGES.UPDATE_USER");
        sql.append(" WHERE KNOWLEDGE_ID IN (");
        int count = 0;
        for (Long integer : knowledgeIds) {
            if (count > 0) {
                sql.append(", ");
            }
            sql.append("?");
            count++;
        }
        sql.append(")");
        return executeQueryList(sql.toString(), KnowledgesEntity.class, knowledgeIds.toArray(new Long[0]));
    }

    /**
     * ナレッジを取得 取得する際に、登録者名も取得
     * 
     * @param knowledgeId
     * @return
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public KnowledgesEntity selectOnKeyWithUserName(Long knowledgeId) {
        String sql = SQLManager.getInstance()
                .getSql("/org/support/project/knowledge/dao/sql/KnowledgesDao/KnowledgesDao_selectOnKeyWithUserName.sql");
        return executeQuerySingle(sql, KnowledgesEntity.class, knowledgeId);
    }

    /**
     * ユーザが登録したナレッジのIDを全て取得
     * 
     * @param userId
     * @return
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<Long> selectOnUser(Integer userId) {
        String sql = "SELECT KNOWLEDGE_ID FROM KNOWLEDGES WHERE INSERT_USER = ? ORDER BY KNOWLEDGE_ID DESC";
        return executeQueryList(sql, Long.class, userId);
    }

    /**
     * ユーザが登録したナレッジを全て削除 （論理削除）
     * 
     * @param loginUserId
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void deleteOnUser(Integer loginUserId) {
        String sql = "UPDATE KNOWLEDGES SET DELETE_FLAG = 1 , UPDATE_USER = ? , UPDATE_DATETIME = ? WHERE INSERT_USER = ?";
        super.executeUpdate(sql, loginUserId, new Timestamp(DateUtils.now().getTime()), loginUserId);
    }

    /**
     * 登録されているナレッジを、番号指定で取得
     * 
     * @param start
     * @param end
     * @return
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<KnowledgesEntity> selectBetween(Long start, Long end) {
        String sql = "SELECT * FROM knowledges WHERE knowledge_id BETWEEN ? AND ? AND DELETE_FLAG = 0 ORDER BY knowledge_id";
        return executeQueryList(sql, KnowledgesEntity.class, start, end);
    }

    /**
     * 指定期間でイイネの登録件数が大きいもから先頭に取得 アクセス権限を考慮しないので、管理者用
     * 
     * @param start
     * @param end
     * @param offset
     * @param limit
     * @return
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<KnowledgesEntity> selectPopularity(Timestamp start, Timestamp end, int offset, int limit) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgesDao/KnowledgesDao_selectPopularity.sql");
        return executeQueryList(sql, KnowledgesEntity.class, start, end, limit, offset);
    }

    /**
     * 指定期間でイイネの登録件数が大きいものから取得 アクセス権限を考慮する
     * 
     * @param loginedUser
     * @param start
     * @param end
     * @param offset
     * @param limit
     * @return
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<KnowledgesEntity> selectPopularityWithAccessControl(LoginedUser loginedUser, Timestamp start, Timestamp end, int offset, int limit) {
        String sql = SQLManager.getInstance()
                .getSql("/org/support/project/knowledge/dao/sql/KnowledgesDao/KnowledgesDao_selectPopularityWithAccessControl.sql");
        List<Object> params = new ArrayList<>();
        params.add(start);
        params.add(end);
        Integer loginuserId = Integer.MIN_VALUE;
        if (loginedUser != null) {
            loginuserId = loginedUser.getUserId();
        }
        params.add(loginuserId);
        params.add(loginuserId);

        List<Integer> groups = new ArrayList<>();
        groups.add(0); // ALL Groups
        if (loginedUser != null && loginedUser.getGroups() != null) {
            List<GroupsEntity> userGroups = loginedUser.getGroups();
            for (GroupsEntity groupsEntity : userGroups) {
                groups.add(groupsEntity.getGroupId());
            }
        }
        StringBuilder groupParams = new StringBuilder();
        int cnt = 0;
        for (Integer integer : groups) {
            if (cnt > 0) {
                groupParams.append(", ");
            }
            cnt++;
            params.add(integer);
            groupParams.append("?");
        }
        sql = sql.replace("%GROUPS%", groupParams.toString());
        params.add(limit);
        params.add(offset);

        return executeQueryList(sql, KnowledgesEntity.class, params.toArray(new Object[0]));
    }

    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<KnowledgesEntity> selectStocks(LoginedUser loginedUser, int offset, int limit, Long stockid) {
        if (loginedUser == null) {
            // ログインしていないのであれば、ストックは無し
            return new ArrayList<>();
        }
        if (loginedUser != null && loginedUser.isAdmin()) {
            if (stockid != null) {
                String sql = SQLManager.getInstance()
                        .getSql("/org/support/project/knowledge/dao/sql/KnowledgesDao/KnowledgesDao_selectStocksFilterOnStockID.sql");
                return executeQueryList(sql, KnowledgesEntity.class, loginedUser.getUserId(), stockid, limit, offset);
            } else {
                String sql = SQLManager.getInstance()
                        .getSql("/org/support/project/knowledge/dao/sql/KnowledgesDao/KnowledgesDao_selectStocks.sql");
                return executeQueryList(sql, KnowledgesEntity.class, loginedUser.getUserId(), limit, offset);
            }
        } else {
            String sql;
            List<Object> params = new ArrayList<>();
            Integer loginuserId = Integer.MIN_VALUE;
            if (loginedUser != null) {
                loginuserId = loginedUser.getUserId();
            }
            params.add(loginuserId); // Stock検索値
            if (stockid != null) {
                sql = SQLManager.getInstance()
                        .getSql("/org/support/project/knowledge/dao/sql/KnowledgesDao/KnowledgesDao_selectStocksWithAccessControlFilterOnStockID.sql");
                params.add(stockid);
            } else {
                sql = SQLManager.getInstance()
                        .getSql("/org/support/project/knowledge/dao/sql/KnowledgesDao/KnowledgesDao_selectStocksWithAccessControl.sql");
                
            }
            params.add(loginuserId); // 権限制御用
            params.add(loginuserId); // 権限制御用

            List<Integer> groups = new ArrayList<>();
            groups.add(0); // ALL Groups
            if (loginedUser != null && loginedUser.getGroups() != null) {
                List<GroupsEntity> userGroups = loginedUser.getGroups();
                for (GroupsEntity groupsEntity : userGroups) {
                    groups.add(groupsEntity.getGroupId());
                }
            }
            StringBuilder groupParams = new StringBuilder();
            int cnt = 0;
            for (Integer integer : groups) {
                if (cnt > 0) {
                    groupParams.append(", ");
                }
                cnt++;
                params.add(integer);
                groupParams.append("?");
            }
            sql = sql.replace("%GROUPS%", groupParams.toString());
            params.add(limit);
            params.add(offset);
            return executeQueryList(sql, KnowledgesEntity.class, params.toArray(new Object[0]));
        }
    }
    
    /**
     * ナレッジIDを前方一致の文字列検索
     * @param q
     * @return
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<KnowledgesEntity> selectKnowledgeOnIdPrefix(String q, int limit, int offset) {
        String sql = "SELECT KNOWLEDGE_ID, TITLE FROM KNOWLEDGES "
                + "WHERE CAST(KNOWLEDGE_ID AS VARCHAR(20)) LIKE ? || '%' ORDER BY KNOWLEDGES.KNOWLEDGE_ID DESC LIMIT ? OFFSET ?;";
        return executeQueryList(sql, KnowledgesEntity.class, q, 5, 0);
    }
    /**
     * ナレッジIDを前方一致の文字列検索
     * アクセス権のある記事のみを取得
     * @param q
     * @return
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<KnowledgesEntity> selectAccessAbleKnowledgeOnIdPrefix(String q, LoginedUser loginedUser, int limit, int offset) {
        String sql = SQLManager.getInstance()
                .getSql("/org/support/project/knowledge/dao/sql/KnowledgesDao/KnowledgesDao_selectAccessAbleKnowledgeOnIdPrefix.sql");
        List<Object> params = new ArrayList<>();
        params.add(q);
        Integer loginuserId = Integer.MIN_VALUE;
        if (loginedUser != null) {
            loginuserId = loginedUser.getUserId();
        }
        params.add(loginuserId);
        params.add(loginuserId);

        List<Integer> groups = new ArrayList<>();
        groups.add(0); // ALL Groups
        if (loginedUser != null && loginedUser.getGroups() != null) {
            List<GroupsEntity> userGroups = loginedUser.getGroups();
            for (GroupsEntity groupsEntity : userGroups) {
                groups.add(groupsEntity.getGroupId());
            }
        }
        StringBuilder groupParams = new StringBuilder();
        int cnt = 0;
        for (Integer integer : groups) {
            if (cnt > 0) {
                groupParams.append(", ");
            }
            cnt++;
            params.add(integer);
            groupParams.append("?");
        }
        sql = sql.replace("%GROUPS%", groupParams.toString());
        params.add(limit);
        params.add(offset);
        
        return executeQueryList(sql, KnowledgesEntity.class, params.toArray(new Object[0]));
    }

    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void updateViewCount(Long count, Long knowledgeId) {
        String sql = "UPDATE KNOWLEDGES SET VIEW_COUNT = ? WHERE KNOWLEDGE_ID = ?";
        executeUpdate(sql, count, knowledgeId);
    }
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public int selectPoint(long knowledgeId) {
        String sql = "SELECT POINT FROM KNOWLEDGES WHERE KNOWLEDGE_ID = ?";
        return executeQuerySingle(sql, Integer.class, knowledgeId);
    }
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void updatePoint(long knowledgeId, int point) {
        String sql = "UPDATE KNOWLEDGES SET POINT = ? WHERE KNOWLEDGE_ID = ?";
        executeUpdate(sql, point, knowledgeId);
    }

}
