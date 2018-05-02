package org.support.project.ormapping.gen.dao;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.ormapping.gen.dao.gen.GenBlobTableDao;

/**
 * BLOBのテストテーブル
 */
@DI(instance = Instance.Singleton)
public class BlobTableDao extends GenBlobTableDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * インスタンス取得 AOPに対応
     * 
     * @return インスタンス
     */
    public static BlobTableDao get() {
        return Container.getComp(BlobTableDao.class);
    }

}
