package org.support.project.knowledge.logic;

import java.lang.invoke.MethodHandles;
import java.util.List;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.dao.DraftItemValuesDao;
import org.support.project.knowledge.dao.KnowledgeItemValuesDao;
import org.support.project.knowledge.dao.TemplateMastersDao;
import org.support.project.knowledge.entity.DraftItemValuesEntity;
import org.support.project.knowledge.entity.KnowledgeItemValuesEntity;
import org.support.project.knowledge.entity.TemplateItemsEntity;
import org.support.project.knowledge.entity.TemplateMastersEntity;

@DI(instance = Instance.Singleton)
public class KnowledgeTemplateItemSelectLogic {
    /** LOG */
    private static final Log LOG = LogFactory.getLog(MethodHandles.lookup());
    /** Get instance */
    public static KnowledgeTemplateItemSelectLogic get() {
        return Container.getComp(KnowledgeTemplateItemSelectLogic.class);
    }
    
    /**
     * テンプレートの拡張項目の情報を取得
     * @param knowledgeId
     * @param typeId
     * @param includeDraft 
     * @param draftId 
     * @return
     */
    public TemplateMastersEntity getItems(long knowledgeId, int typeId, boolean includeDraft, long draftId) {
        LOG.trace("getItems");
        TemplateMastersEntity template = TemplateMastersDao.get().selectWithItems(typeId);
        if (template == null) {
            // そのテンプレートは既に削除済みの場合、通常のナレッジのテンプレートで表示する（ナレッジのテンプレートは削除できないようにする）
            typeId = TemplateLogic.TYPE_ID_KNOWLEDGE;
            template = TemplateMastersDao.get().selectWithItems(typeId);
        }
        
        // 保存している値の取得
        boolean loaded = false;
        List<TemplateItemsEntity> items = template.getItems();
        if (includeDraft) {
            List<DraftItemValuesEntity> values = DraftItemValuesDao.get().selectOnDraftId(draftId); 
            if (values != null && values.size() > 0) {
                loaded = true;
            }
            
            for (DraftItemValuesEntity val : values) {
                for (TemplateItemsEntity item : items) {
                    if (val.getItemNo().equals(item.getItemNo())) {
                        item.setItemValue(val.getItemValue());
                        break;
                    }
                }
            }
        }
        if (!loaded) {
            List<KnowledgeItemValuesEntity> values = KnowledgeItemValuesDao.get().selectOnKnowledgeId(knowledgeId);
            for (KnowledgeItemValuesEntity val : values) {
                for (TemplateItemsEntity item : items) {
                    if (val.getItemNo().equals(item.getItemNo())) {
                        item.setItemValue(val.getItemValue());
                        break;
                    }
                }
            }
        }
        return template;
    }

}
