package org.support.project.ormapping.gen.dao;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.ormapping.gen.dao.gen.GenSectionDao;

/**
 * 組織
 */
@DI(instance = Instance.Singleton)
public class SectionDao extends GenSectionDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * インスタンス取得 AOPに対応
     * 
     * @return インスタンス
     */
    public static SectionDao get() {
        return Container.getComp(SectionDao.class);
    }

}
