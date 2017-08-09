package org.support.project.knowledge.dao;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.dao.gen.GenLikeCommentsDao;

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
    
    public Long selectOnCommentNo(Long commentNo) {
        String sql = "SELECT COUNT(*) FROM LIKE_COMMENTS WHERE COMMENT_NO = ?";
        return super.executeQuerySingle(sql, Long.class, commentNo);
    }



}
