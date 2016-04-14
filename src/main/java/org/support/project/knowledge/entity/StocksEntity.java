package org.support.project.knowledge.entity;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.entity.gen.GenStocksEntity;

/**
 * ストックしたナレッジ
 */
@DI(instance = Instance.Prototype)
public class StocksEntity extends GenStocksEntity {

    public static final int STOCKTYPE_PRIVATE = 0;
    public static final int STOCKTYPE_PUBLIC = 1;

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * ストックに登録されたナレッジの件数
     */
    private Integer knowledgeCount;

    /**
     * インスタンス取得 AOPに対応
     * 
     * @return インスタンス
     */
    public static StocksEntity get() {
        return Container.getComp(StocksEntity.class);
    }

    /**
     * コンストラクタ
     */
    public StocksEntity() {
        super();
    }

    /**
     * コンストラクタ
     * 
     * @param stockId stockId
     */

    public StocksEntity(Long stockId) {
        super(stockId);
    }

    /**
     * @return the knowledgeCount
     */
    public Integer getKnowledgeCount() {
        return knowledgeCount;
    }

    /**
     * @param knowledgeCount the knowledgeCount to set
     */
    public void setKnowledgeCount(Integer knowledgeCount) {
        this.knowledgeCount = knowledgeCount;
    }

}
