package org.support.project.knowledge.entity;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.entity.gen.GenItemChoicesEntity;

/**
 * 選択肢の値
 */
@DI(instance = Instance.Prototype)
public class ItemChoicesEntity extends GenItemChoicesEntity {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * インスタンス取得 AOPに対応
     * 
     * @return インスタンス
     */
    public static ItemChoicesEntity get() {
        return Container.getComp(ItemChoicesEntity.class);
    }

    /**
     * コンストラクタ
     */
    public ItemChoicesEntity() {
        super();
    }

    /**
     * コンストラクタ
     * 
     * @param choiceNo 選択肢番号
     * @param itemNo 項目NO
     * @param typeId テンプレートの種類ID
     */

    public ItemChoicesEntity(Integer choiceNo, Integer itemNo, Integer typeId) {
        super(choiceNo, itemNo, typeId);
    }

}
