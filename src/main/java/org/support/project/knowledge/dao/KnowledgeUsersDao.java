package org.support.project.knowledge.dao;

import org.support.project.aop.Aspect;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.dao.gen.GenKnowledgeUsersDao;

/**
 * アクセス可能なユーザ
 */
@DI(instance = Instance.Singleton)
public class KnowledgeUsersDao extends GenKnowledgeUsersDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * インスタンス取得 AOPに対応
     * 
     * @return インスタンス
     */
    public static KnowledgeUsersDao get() {
        return Container.getComp(KnowledgeUsersDao.class);
    }

    /**
     * ナレッジIDを指定し、アクセス可能ユーザ情報をクリアする
     * 
     * @param knowledgeId
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void deleteOnKnowledgeId(Long knowledgeId) {
        String sql = "DELETE FROM KNOWLEDGE_USERS WHERE KNOWLEDGE_ID = ?";
        super.executeUpdate(sql, knowledgeId);
    }

}
