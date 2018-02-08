package org.support.project.knowledge.dao;

import java.util.List;

import org.support.project.aop.Aspect;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.dao.gen.GenLikeCommentsDao;
import org.support.project.knowledge.entity.LikeCommentsEntity;
import org.support.project.ormapping.common.SQLManager;

/**
 * コメントのイイネ
 */
@DI(instance = Instance.Singleton)
public class LikeCommentsDao extends GenLikeCommentsDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;
    /**
     * Get instance from DI container.
     * @return instance
     */
    public static LikeCommentsDao get() {
        return Container.getComp(LikeCommentsDao.class);
    }
    
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public Long selectOnCommentNo(Long commentNo) {
        String sql = "SELECT COUNT(*) FROM LIKE_COMMENTS WHERE COMMENT_NO = ?";
        return super.executeQuerySingle(sql, Long.class, commentNo);
    }

    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<LikeCommentsEntity> selectOnCommentNo(Long commentNo, int offset, int limit) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/LikeCommentsDao/LikeCommentsDao_selectOnCommentNo.sql");
        return executeQueryList(sql, LikeCommentsEntity.class, commentNo, limit, offset);
    }

    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public LikeCommentsEntity selectExistsOnUser(Long commentNo, Integer userId) {
        String sql = "SELECT * FROM LIKE_COMMENTS WHERE COMMENT_NO = ? AND INSERT_USER = ? LIMIT 1 OFFSET 0";
        return super.executeQuerySingle(sql, LikeCommentsEntity.class, commentNo, userId);
    }

    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public long selectUniqueUserCountOnCommentNo(Long commentNo) {
        String sql = "SELECT COUNT(*) FROM ("
                + "SELECT COMMENT_NO, INSERT_USER FROM LIKE_COMMENTS WHERE COMMENT_NO = ? GROUP BY COMMENT_NO, INSERT_USER) AS SUBQ";
        return super.executeQuerySingle(sql, Long.class, commentNo);
    }



}
