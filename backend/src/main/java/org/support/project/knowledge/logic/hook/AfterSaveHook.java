package org.support.project.knowledge.logic.hook;

import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.vo.KnowledgeData;
import org.support.project.web.bean.AccessUser;

@DI(instance=Instance.Singleton)
public interface AfterSaveHook {
    /**
     * Knowledgeデータを保存した後に何か処理を実施する場合のHook
     * @param knowledgeData
     * @param loginedUser
     * @return
     * @throws Exception
     */
    void afterSave(KnowledgeData knowledgeData, AccessUser loginedUser) throws Exception;
}
