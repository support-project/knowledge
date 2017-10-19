package org.support.project.ormapping.gen.dao;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.ormapping.gen.dao.gen.GenGroupUserRelationshipDao;

/**
 * 所属
 */
@DI(instance = Instance.Singleton)
public class GroupUserRelationshipDao extends GenGroupUserRelationshipDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * インスタンス取得 AOPに対応
     * 
     * @return インスタンス
     */
    public static GroupUserRelationshipDao get() {
        return Container.getComp(GroupUserRelationshipDao.class);
    }

}
