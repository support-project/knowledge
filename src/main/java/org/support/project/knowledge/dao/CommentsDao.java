package org.support.project.knowledge.dao;

import java.util.List;

import org.support.project.aop.Aspect;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.dao.gen.GenCommentsDao;
import org.support.project.knowledge.entity.CommentsEntity;

/**
 * コメント
 */
@DI(instance = Instance.Singleton)
public class CommentsDao extends GenCommentsDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * インスタンス取得 AOPに対応
     * 
     * @return インスタンス
     */
    public static CommentsDao get() {
        return Container.getComp(CommentsDao.class);
    }

    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<CommentsEntity> selectOnKnowledgeId(Long knowledgeId) {
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT COMMENTS.*, UPDATE_USER.USER_NAME AS UPDATE_USER_NAME, INSERT_USER.USER_NAME AS INSERT_USER_NAME FROM COMMENTS ");
        builder.append("LEFT OUTER JOIN USERS AS UPDATE_USER ON UPDATE_USER.USER_ID = COMMENTS.UPDATE_USER ");
        builder.append("LEFT OUTER JOIN USERS AS INSERT_USER ON INSERT_USER.USER_ID = COMMENTS.INSERT_USER ");
        builder.append("WHERE COMMENTS.KNOWLEDGE_ID = ? AND COMMENTS.DELETE_FLAG = 0 ");
        builder.append("ORDER BY COMMENTS.INSERT_DATETIME ");
        return executeQueryList(builder.toString(), CommentsEntity.class, knowledgeId);
    }

    /**
     * ナレッジのコメントの件数を取得
     * 
     * @param knowledgeId
     * @return
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public Integer countOnKnowledgeId(Long knowledgeId) {
        String sql = "SELECT COUNT(*) FROM COMMENTS WHERE KNOWLEDGE_ID = ?  AND DELETE_FLAG = 0 ";
        return super.executeQuerySingle(sql, Integer.class, knowledgeId);
    }

    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public long selectUniqueUserCountOnKnowledgeId(Long knowledgeId) {
        String sql = "SELECT COUNT(*) FROM ("
                + "SELECT KNOWLEDGE_ID, INSERT_USER FROM COMMENTS WHERE KNOWLEDGE_ID = ? GROUP BY KNOWLEDGE_ID, INSERT_USER) AS SUBQ";
        return super.executeQuerySingle(sql, Long.class, knowledgeId);
    }

}
