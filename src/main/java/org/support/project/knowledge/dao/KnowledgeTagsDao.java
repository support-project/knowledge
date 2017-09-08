package org.support.project.knowledge.dao;

import org.support.project.aop.Aspect;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.dao.gen.GenKnowledgeTagsDao;

/**
 * ナレッジが持つタグ
 */
@DI(instance = Instance.Singleton)
public class KnowledgeTagsDao extends GenKnowledgeTagsDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * インスタンス取得 AOPに対応
     * 
     * @return インスタンス
     */
    public static KnowledgeTagsDao get() {
        return Container.getComp(KnowledgeTagsDao.class);
    }

    /**
     * ナレッジが持つタグを削除
     * 
     * @param knowledgeId
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void deleteOnKnowledgeId(Long knowledgeId) {
        String sql = "DELETE FROM KNOWLEDGE_TAGS WHERE KNOWLEDGE_ID = ?";
        super.executeUpdate(sql, knowledgeId);
    }

}
