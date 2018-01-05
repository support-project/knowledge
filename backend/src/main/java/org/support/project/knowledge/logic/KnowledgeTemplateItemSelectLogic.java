package org.support.project.knowledge.logic;

import java.util.List;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.dao.KnowledgeItemValuesDao;
import org.support.project.knowledge.dao.TemplateMastersDao;
import org.support.project.knowledge.entity.KnowledgeItemValuesEntity;
import org.support.project.knowledge.entity.KnowledgesEntity;
import org.support.project.knowledge.entity.TemplateItemsEntity;
import org.support.project.knowledge.entity.TemplateMastersEntity;

@DI(instance = Instance.Singleton)
public class KnowledgeTemplateItemSelectLogic {
    /** LOG */
    private static final Log LOG = LogFactory.getLog(KnowledgeTemplateItemSelectLogic.class);
    /** Get instance */
    public static KnowledgeTemplateItemSelectLogic get() {
        return Container.getComp(KnowledgeTemplateItemSelectLogic.class);
    }
    
    /**
     * テンプレートの拡張項目の情報を取得
     * @param knowledge
     * @return
     */
    public TemplateMastersEntity getItems(KnowledgesEntity knowledge) {
        LOG.trace("getItems");
        Integer typeId = knowledge.getTypeId();
        TemplateMastersEntity template = TemplateMastersDao.get().selectWithItems(typeId);
        if (template == null) {
            // そのテンプレートは既に削除済みの場合、通常のナレッジのテンプレートで表示する（ナレッジのテンプレートは削除できないようにする）
            typeId = TemplateLogic.TYPE_ID_KNOWLEDGE;
            template = TemplateMastersDao.get().selectWithItems(typeId);
        }
        
        // 保存している値の取得
        Long knowledgeId = knowledge.getKnowledgeId();
        List<KnowledgeItemValuesEntity> values = KnowledgeItemValuesDao.get().selectOnKnowledgeId(knowledgeId);
        List<TemplateItemsEntity> items = template.getItems();
        for (KnowledgeItemValuesEntity val : values) {
            for (TemplateItemsEntity item : items) {
                if (val.getItemNo().equals(item.getItemNo())) {
                    item.setItemValue(val.getItemValue());
                    break;
                }
            }
        }
        return template;
    }

}
