package org.support.project.knowledge.logic.hook;

import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.dao.EventsDao;
import org.support.project.knowledge.entity.EventsEntity;
import org.support.project.knowledge.entity.KnowledgesEntity;
import org.support.project.knowledge.logic.TemplateLogic;
import org.support.project.knowledge.vo.KnowledgeData;
import org.support.project.web.bean.AccessUser;

@DI(instance = Instance.Singleton)
public class BeforeSaveOldEventRemoveHook implements BeforeSaveHook {

    @Override
    public void beforeSave(KnowledgeData data, KnowledgesEntity db, AccessUser loginedUser) throws Exception {
        // 前に保存されていたデータがイベント情報で、かつ、現在の保存する情報がそれ以外になった場合に、イベント情報を削除する
        if (db != null) {
            if (TemplateLogic.TYPE_ID_EVENT == db.getTypeId()
                    && TemplateLogic.TYPE_ID_EVENT != data.getKnowledge().getTypeId()) {
                EventsEntity event = EventsDao.get().selectOnKey(db.getKnowledgeId());
                if (event != null) {
                    EventsDao.get().physicalDelete(event);
                }
            }
        }
    }


}
