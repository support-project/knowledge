package org.support.project.knowledge.logic.activity;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.entity.ActivitiesEntity;

/**
 * ナレッジ登録時のポイント付与
 * 
 * 種類  | 獲得のタイプ  | ポイント付与先 | ポイント    | 獲得タイプの意味
 * 1    | 11          | 記事登録者    | 50       | 記事を投稿したら投稿者にポイント追加
 * 1    | 12          | 記事         | 50       | 登録された記事のポイント初期値
 * @author koda
 */
@DI(instance = Instance.Prototype)
public class KnowledgeInsertActivity extends AbstractAddPointForKnowledgeProcessor {
    private static final Log LOG = LogFactory.getLog(KnowledgeInsertActivity.class);
    public static KnowledgeInsertActivity get() {
        return Container.getComp(KnowledgeInsertActivity.class);
    }
    
    @Override
    public void execute() throws Exception {
        if (getKnowledge() == null || getUser() == null) {
            // ありえないけど念のため確認
            return;
        }
        if (isExistsActivity(getUser().getUserId(), Activity.KNOWLEDGE_INSERT, String.valueOf(getKnowledge().getKnowledgeId()))) {
            // 既に指定のKnowledge登録済
            return;
        }
        //int point = RandomUtil.randamNum(30, 50);
       int point = 50;
       
       LOG.debug("Add point by knowledge insert:" + point);
       
        // ポイント発行アクティビティを生成
       ActivitiesEntity activity = addActivity(
               getUser().getUserId(),
               Activity.KNOWLEDGE_INSERT,
               String.valueOf(getKnowledge().getKnowledgeId()),
               getKnowledge().getInsertDatetime());
        // 登録者のポイントをアップ
        addPointForUser(
                getUser().getUserId(),
                getKnowledge().getInsertDatetime(),
                getUser().getUserId(),
                activity.getActivityNo(),
                TYPE_KNOWLEDGE_DO_INSERT,
                point);
        // 記事のポイントアップ
        addPointForKnowledge(
                getUser().getUserId(),
                getKnowledge().getInsertDatetime(),
                getKnowledge().getKnowledgeId(),
                activity.getActivityNo(),
                TYPE_KNOWLEDGE_INSERTED,
                point);
    }
}
