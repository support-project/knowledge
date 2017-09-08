package org.support.project.knowledge.logic.hook;

import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.entity.KnowledgesEntity;
import org.support.project.knowledge.vo.KnowledgeData;
import org.support.project.web.bean.LoginedUser;

@DI(instance = Instance.Singleton)
public class BeforeSavePointHook implements BeforeSaveHook {

    @Override
    public void beforeSave(KnowledgeData data, KnowledgesEntity db, LoginedUser loginedUser) throws Exception {
        if (data.getKnowledge().getKnowledgeId() == null) {
            // 新規登録
            data.getKnowledge().setPoint(0);
        } else {
            data.getKnowledge().setPoint(db.getPoint()); // ポイントは、現在のDBの値をコピー
        }
    }
}
