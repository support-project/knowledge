package org.support.project.knowledge.dao;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.dao.gen.GenKnowledgeEditGroupsDao;

/**
 * 編集可能なグループ
 */
@DI(instance = Instance.Singleton)
public class KnowledgeEditGroupsDao extends GenKnowledgeEditGroupsDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * インスタンス取得 AOPに対応
     * 
     * @return インスタンス
     */
    public static KnowledgeEditGroupsDao get() {
        return Container.getComp(KnowledgeEditGroupsDao.class);
    }

    public void deleteOnKnowledgeId(Long knowledgeId) {
        String sql = "DELETE FROM KNOWLEDGE_EDIT_GROUPS WHERE KNOWLEDGE_ID = ?";
        super.executeUpdate(sql, knowledgeId);
    }

}
