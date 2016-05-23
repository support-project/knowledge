package org.support.project.knowledge.dao;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.dao.gen.GenViewHistoriesDao;

/**
 * ナレッジの参照履歴
 */
@DI(instance = Instance.Singleton)
public class ViewHistoriesDao extends GenViewHistoriesDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * インスタンス取得 AOPに対応
     * 
     * @return インスタンス
     */
    public static ViewHistoriesDao get() {
        return Container.getComp(ViewHistoriesDao.class);
    }

}
