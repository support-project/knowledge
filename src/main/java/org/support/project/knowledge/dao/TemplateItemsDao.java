package org.support.project.knowledge.dao;

import org.support.project.aop.Aspect;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.dao.gen.GenTemplateItemsDao;
import org.support.project.ormapping.common.SQLManager;

/**
 * テンプレートの項目
 */
@DI(instance = Instance.Singleton)
public class TemplateItemsDao extends GenTemplateItemsDao {

    public static final int ITEM_ID_BOOKMARK_URL = 0;

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * インスタンス取得 AOPに対応
     * 
     * @return インスタンス
     */
    public static TemplateItemsDao get() {
        return Container.getComp(TemplateItemsDao.class);
    }

    /**
     * データをtruncateする
     * 
     * @return void
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void truncate() {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/TemplateItemsDao/TemplateItemsDao_truncate.sql");
        executeUpdate(sql);
    }
}
