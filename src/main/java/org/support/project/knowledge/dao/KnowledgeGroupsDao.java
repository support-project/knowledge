package org.support.project.knowledge.dao;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.dao.gen.GenKnowledgeGroupsDao;

/**
 * アクセス可能なグループ
 */
@DI(instance = Instance.Singleton)
public class KnowledgeGroupsDao extends GenKnowledgeGroupsDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * インスタンス取得 AOPに対応
     * 
     * @return インスタンス
     */
    public static KnowledgeGroupsDao get() {
        return Container.getComp(KnowledgeGroupsDao.class);
    }

}
