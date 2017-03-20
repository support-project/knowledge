package org.support.project.knowledge.logic.hook;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.dao.EventsDao;
import org.support.project.knowledge.entity.EventsEntity;
import org.support.project.knowledge.logic.TemplateLogic;
import org.support.project.knowledge.vo.KnowledgeData;
import org.support.project.web.bean.LoginedUser;

@DI(instance = Instance.Singleton)
public class AfterSaveEventHook implements AfterSaveHook {

    @Override
    public void afterSave(KnowledgeData knowledgeData, LoginedUser loginedUser) throws Exception {
        if (TemplateLogic.TYPE_ID_EVENT == knowledgeData.getKnowledge().getTypeId()) {
            BeforeSaveEventHook beforehook = Container.getComp(BeforeSaveEventHook.class);
            EventsEntity event = beforehook.parseDate(knowledgeData, loginedUser);
            EventsDao.get().save(event);
        }
    }


}
