package org.support.project.knowledge.dao;

import org.support.project.aop.Aspect;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;

import org.support.project.knowledge.dao.gen.GenDraftItemValuesDao;

/**
 * ナレッジの項目値
 */
@DI(instance = Instance.Singleton)
public class DraftItemValuesDao extends GenDraftItemValuesDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;
    /**
     * Get instance from DI container.
     * @return instance
     */
    public static DraftItemValuesDao get() {
        return Container.getComp(DraftItemValuesDao.class);
    }
    
    /**
     * 下書きのテンプレート拡張項目値を削除
     * @param draftId
     * @return
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public int deleteOnDraftId(Long draftId) {
        String sql = "DELETE FROM DRAFT_ITEM_VALUES WHERE DRAFT_ID = ?";
        return super.executeUpdate(sql, draftId);
    }




}
