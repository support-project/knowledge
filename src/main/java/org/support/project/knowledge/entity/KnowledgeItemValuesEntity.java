package org.support.project.knowledge.entity;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.entity.gen.GenKnowledgeItemValuesEntity;

/**
 * ナレッジの項目値
 */
@DI(instance = Instance.Prototype)
public class KnowledgeItemValuesEntity extends GenKnowledgeItemValuesEntity {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;
    /** ステータス: 保存済み */
    public static final Integer STATUS_SAVED = 0;
    /** ステータス: 処理中 */
    public static final Integer STATUS_DO_PROCESS = 1;
    /** ステータス: Webの値取得済み */
    public static final Integer STATUS_WEBACCESSED = 10;
    /** ステータス: エラー */
    public static final Integer STATUS_WEBACCESS_ERROR = -1;

    /**
     * インスタンス取得 AOPに対応
     * 
     * @return インスタンス
     */
    public static KnowledgeItemValuesEntity get() {
        return Container.getComp(KnowledgeItemValuesEntity.class);
    }

    /**
     * コンストラクタ
     */
    public KnowledgeItemValuesEntity() {
        super();
    }

    /**
     * コンストラクタ
     * 
     * @param itemNo 項目NO
     * @param knowledgeId ナレッジID
     * @param typeId テンプレートの種類ID
     */

    public KnowledgeItemValuesEntity(Integer itemNo, Long knowledgeId, Integer typeId) {
        super(itemNo, knowledgeId, typeId);
    }

}
