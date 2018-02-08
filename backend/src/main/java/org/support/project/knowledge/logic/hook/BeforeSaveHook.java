package org.support.project.knowledge.logic.hook;

import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.entity.KnowledgesEntity;
import org.support.project.knowledge.vo.KnowledgeData;
import org.support.project.web.bean.AccessUser;

@DI(instance=Instance.Singleton)
public interface BeforeSaveHook {
    /**
     * Knowledgeデータを保存する前に何か処理を実施する場合のHook
     * @param knowledgeData
     * @param loginedUser
     * @return
     * @throws Exception
     */
    void beforeSave(KnowledgeData knowledgeData, KnowledgesEntity db, AccessUser loginedUser) throws Exception;
}
