package org.support.project.knowledge.dao;

import java.util.List;

import org.support.project.aop.Aspect;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.dao.gen.GenItemChoicesDao;
import org.support.project.knowledge.entity.ItemChoicesEntity;

/**
 * 選択肢の値
 */
@DI(instance = Instance.Singleton)
public class ItemChoicesDao extends GenItemChoicesDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * インスタンス取得 AOPに対応
     * 
     * @return インスタンス
     */
    public static ItemChoicesDao get() {
        return Container.getComp(ItemChoicesDao.class);
    }

    /**
     * 指定の項目の、選択肢情報を取得
     * 
     * @param typeId
     * @param itemNo
     * @return
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<ItemChoicesEntity> selectOnItem(Integer typeId, Integer itemNo) {
        String sql = "SELECT * FROM ITEM_CHOICES WHERE TYPE_ID = ? AND ITEM_NO = ? AND DELETE_FLAG = 0";
        return super.executeQueryList(sql, ItemChoicesEntity.class, typeId, itemNo);
    }

}
