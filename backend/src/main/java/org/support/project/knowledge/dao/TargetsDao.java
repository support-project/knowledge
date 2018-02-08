package org.support.project.knowledge.dao;

import java.util.ArrayList;
import java.util.List;

import org.support.project.aop.Aspect;
import org.support.project.common.util.StringJoinBuilder;
import org.support.project.di.Container;
import org.support.project.ormapping.common.SQLManager;
import org.support.project.ormapping.dao.AbstractDao;
import org.support.project.web.bean.LabelValue;
import org.support.project.web.entity.GroupsEntity;
import org.support.project.web.entity.UsersEntity;

public class TargetsDao extends AbstractDao {
    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * インスタンス取得
     * 
     * @return インスタンス
     */
    public static TargetsDao get() {
        return Container.getComp(TargetsDao.class);
    }

    /**
     * キーワードで対象を検索（グループとアカウント）
     * 
     * @param keyword
     * @param offset
     * @param limit
     * @return
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<LabelValue> selectOnKeyword(String keyword, int offset, int limit) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/TargetsDao/selectOnKeyword.sql");
        return executeQueryList(sql, LabelValue.class, keyword, keyword, limit, offset);
    }

    /**
     * ナレッジに指定されているアクセス可能なグループを取得
     * 
     * @param knowledgeId
     * @return
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<GroupsEntity> selectGroupsOnKnowledgeId(Long knowledgeId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/TargetsDao/selectGroupsOnKnowledgeId.sql");
        return executeQueryList(sql, GroupsEntity.class, knowledgeId);
    }

    /**
     * ナレッジに指定されているアクセス可能なグループを取得 ナレッジ一覧に用いる
     *
     * @param knowledgeIds
     * @return
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<GroupsEntity> selectGroupsOnKnowledgeIds(List<Long> knowledgeIds) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/TargetsDao/selectGroupsOnKnowledgeIds.sql");

        StringJoinBuilder builder = new StringJoinBuilder();
        List<Long> params = new ArrayList<>();

        for (Long knowledgeId : knowledgeIds) {
            builder.append("?");
            params.add(knowledgeId);
        }

        sql = sql.replace("${knowledgeIds}", builder.join(", "));
        return executeQueryList(sql, GroupsEntity.class, params.toArray(new Long[0]));
    }

    /**
     * ナレッジに指定されているアクセス可能なユーザを取得
     * 
     * @param knowledgeId
     * @return
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<UsersEntity> selectUsersOnKnowledgeId(Long knowledgeId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/TargetsDao/selectUsersOnKnowledgeId.sql");
        return executeQueryList(sql, UsersEntity.class, knowledgeId);
    }

    /**
     * ナレッジに指定されているアクセス可能なユーザを取得 ナレッジ一覧に用いる
     *
     * @param knowledgeIds
     * @return
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<UsersEntity> selectUsersOnKnowledgeIds(List<Long> knowledgeIds) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/TargetsDao/selectUsersOnKnowledgeIds.sql");

        StringJoinBuilder builder = new StringJoinBuilder();
        List<Long> params = new ArrayList<>();

        for (Long knowledgeId : knowledgeIds) {
            builder.append("?");
            params.add(knowledgeId);
        }

        sql = sql.replace("${knowledgeIds}", builder.join(", "));
        return executeQueryList(sql, UsersEntity.class, params.toArray(new Long[0]));
    }

    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<GroupsEntity> selectEditorGroupsOnKnowledgeId(Long knowledgeId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/TargetsDao/selectEditorGroupsOnKnowledgeId.sql");
        return executeQueryList(sql, GroupsEntity.class, knowledgeId);
    }

    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<UsersEntity> selectEditorUsersOnKnowledgeId(Long knowledgeId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/TargetsDao/selectEditorUsersOnKnowledgeId.sql");
        return executeQueryList(sql, UsersEntity.class, knowledgeId);
    }
}
