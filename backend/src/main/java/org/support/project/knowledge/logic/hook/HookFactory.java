package org.support.project.knowledge.logic.hook;

import java.util.ArrayList;
import java.util.List;

import org.support.project.di.Container;
import org.support.project.knowledge.entity.KnowledgesEntity;
import org.support.project.knowledge.logic.TemplateLogic;
import org.support.project.knowledge.vo.KnowledgeData;

public class HookFactory {
    
    public static List<BeforeSaveHook> getBeforeSaveHookInstance(KnowledgeData knowledgeData, KnowledgesEntity db) {
        List<BeforeSaveHook> hooks = new ArrayList<BeforeSaveHook>();
        if (knowledgeData.getKnowledge().getTypeId() != null
                && TemplateLogic.TYPE_ID_EVENT == knowledgeData.getKnowledge().getTypeId()) {
            hooks.add(Container.getComp(BeforeSaveEventHook.class));
        }
        if (db != null) {
            if (TemplateLogic.TYPE_ID_EVENT == db.getTypeId()
                    && TemplateLogic.TYPE_ID_EVENT != knowledgeData.getKnowledge().getTypeId()) {
                hooks.add(Container.getComp(BeforeSaveOldEventRemoveHook.class));
            }
        }
        hooks.add(Container.getComp(BeforeSavePointHook.class));
        return hooks;
    }
    
    public static List<AfterSaveHook> getAfterSaveHookInstance(KnowledgeData knowledgeData) {
        List<AfterSaveHook> hooks = new ArrayList<AfterSaveHook>();
        if (knowledgeData.getKnowledge().getTypeId() != null
                && TemplateLogic.TYPE_ID_EVENT == knowledgeData.getKnowledge().getTypeId()) {
            hooks.add(Container.getComp(AfterSaveEventHook.class));
        }
        return hooks;
    }
    
}
