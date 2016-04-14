package org.support.project.knowledge.entity;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.entity.gen.GenViewHistoriesEntity;

/**
 * ナレッジの参照履歴
 */
@DI(instance = Instance.Prototype)
public class ViewHistoriesEntity extends GenViewHistoriesEntity {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * インスタンス取得 AOPに対応
     * 
     * @return インスタンス
     */
    public static ViewHistoriesEntity get() {
        return Container.getComp(ViewHistoriesEntity.class);
    }

    /**
     * コンストラクタ
     */
    public ViewHistoriesEntity() {
        super();
    }

    /**
     * コンストラクタ
     * 
     * @param historyNo HISTORY_NO
     */

    public ViewHistoriesEntity(Long historyNo) {
        super(historyNo);
    }

}
