package org.support.project.knowledge.entity;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.entity.gen.GenStockKnowledgesEntity;

/**
 * ストックしたナレッジ
 */
@DI(instance = Instance.Prototype)
public class StockKnowledgesEntity extends GenStockKnowledgesEntity {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * ナレッジのタイトル
     */
    private String title;

    /**
     * インスタンス取得 AOPに対応
     * 
     * @return インスタンス
     */
    public static StockKnowledgesEntity get() {
        return Container.getComp(StockKnowledgesEntity.class);
    }

    /**
     * コンストラクタ
     */
    public StockKnowledgesEntity() {
        super();
    }

    /**
     * コンストラクタ
     * 
     * @param knowledgeId ナレッジID
     * @param stockId STOCK ID
     */

    public StockKnowledgesEntity(Long knowledgeId, Long stockId) {
        super(knowledgeId, stockId);
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

}
