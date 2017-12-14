package org.support.project.knowledge.dao;

import java.util.List;

import org.support.project.aop.Aspect;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.dao.gen.GenTemplateMastersDao;
import org.support.project.knowledge.entity.ItemChoicesEntity;
import org.support.project.knowledge.entity.TemplateItemsEntity;
import org.support.project.knowledge.entity.TemplateMastersEntity;
import org.support.project.ormapping.common.SQLManager;

/**
 * テンプレートのマスタ
 */
@DI(instance = Instance.Singleton)
public class TemplateMastersDao extends GenTemplateMastersDao {
    /**
     * ID
     */
    private int currentId = 0;

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * インスタンス取得 AOPに対応
     * 
     * @return インスタンス
     */
    public static TemplateMastersDao get() {
        return Container.getComp(TemplateMastersDao.class);
    }

    /**
     * IDを採番 ※コミットしなくても次のIDを採番する為、保存しなければ欠番になる
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public Integer getNextId() {
        String sql = "SELECT MAX(TYPE_ID) FROM TEMPLATE_MASTERS;";
        Integer integer = executeQuerySingle(sql, Integer.class);
        if (integer != null && currentId < integer) {
            currentId = integer;
        }
        currentId++;
        return currentId;
    }

    /**
     * 登録されているテンプレートを全て取得
     * 
     * @return
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public TemplateMastersEntity selectWithItems(Integer typeId) {
        TemplateMastersEntity template = selectOnKey(typeId);
        List<TemplateItemsEntity> items = TemplateItemsDao.get().selectOnTypeId(typeId);
        for (TemplateItemsEntity item : items) {
            template.getItems().add(item);
            List<ItemChoicesEntity> choices = ItemChoicesDao.get().selectOnItem(typeId, item.getItemNo());
            for (ItemChoicesEntity choice : choices) {
                item.getChoices().add(choice);
            }
        }
        return template;
    }

    /**
     * データをtruncateする
     * 
     * @return void
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void truncate() {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/TemplateMastersDao/TemplateMastersDao_truncate.sql");
        executeUpdate(sql);
    }

    /**
     * sequenceをリセットする
     * 
     * @return void
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void resetSequence() {
        String sql = SQLManager.getInstance()
                .getSql("/org/support/project/knowledge/dao/sql/TemplateMastersDao/TemplateMastersDao_alter_sequence.sql");
        executeUpdate(sql);
    }
    
    /**
     * テンプレート名でテンプレートを取得
     * @param template
     * @return
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public TemplateMastersEntity selectOnName(String template) {
        String sql = "SELECT * FROM TEMPLATE_MASTERS WHERE TYPE_NAME = ? AND DELETE_FLAG = 0";
        return executeQuerySingle(sql, TemplateMastersEntity.class, template);
    }
}
