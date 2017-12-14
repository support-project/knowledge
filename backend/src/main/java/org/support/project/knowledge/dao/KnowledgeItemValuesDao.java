package org.support.project.knowledge.dao;

import java.util.List;

import org.support.project.aop.Aspect;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.dao.gen.GenKnowledgeItemValuesDao;
import org.support.project.knowledge.entity.KnowledgeItemValuesEntity;

/**
 * ナレッジの項目値
 */
@DI(instance = Instance.Singleton)
public class KnowledgeItemValuesDao extends GenKnowledgeItemValuesDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * インスタンス取得 AOPに対応
     * 
     * @return インスタンス
     */
    public static KnowledgeItemValuesDao get() {
        return Container.getComp(KnowledgeItemValuesDao.class);
    }

    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<KnowledgeItemValuesEntity> selectOnTypeIdAndItemNoAndStatus(int typeId, int itemNo, int status) {
        String sql = "SELECT * FROM KNOWLEDGE_ITEM_VALUES WHERE TYPE_ID = ? AND ITEM_NO = ? AND ITEM_STATUS = ? AND DELETE_FLAG = 0";
        return executeQueryList(sql, KnowledgeItemValuesEntity.class, typeId, itemNo, status);
    }

}
