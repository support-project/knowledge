package org.support.project.knowledge.dao;

import org.support.project.aop.Aspect;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.dao.gen.GenKnowledgeEditUsersDao;

/**
 * 編集可能なユーザ
 */
@DI(instance = Instance.Singleton)
public class KnowledgeEditUsersDao extends GenKnowledgeEditUsersDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * インスタンス取得 AOPに対応
     * 
     * @return インスタンス
     */
    public static KnowledgeEditUsersDao get() {
        return Container.getComp(KnowledgeEditUsersDao.class);
    }

    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void deleteOnKnowledgeId(Long knowledgeId) {
        String sql = "DELETE FROM KNOWLEDGE_EDIT_USERS WHERE KNOWLEDGE_ID = ?";
        super.executeUpdate(sql, knowledgeId);
    }

}
