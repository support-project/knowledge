package org.support.project.knowledge.logic.activity;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.StringUtils;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;

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
    protected Activity getActivity() {
        LOG.debug("Start add point process on insert knowledge.");
        return Activity.KNOWLEDGE_INSERT;
    }
    
    private int getPoint() {
        // 文章が多い力先はポイントが高いように調整
        int point = 50;
        if (StringUtils.isNotEmpty(getKnowledge().getContent()) && getKnowledge().getContent().length() > 700) {
            int add = (getKnowledge().getContent().length() - 700) / 50;
            point += add;
        }
        return point;
    }
    
    @Override
    protected TypeAndPoint getTypeAndPointForActivityExecuter() {
        return new TypeAndPoint(TYPE_KNOWLEDGE_DO_INSERT, getPoint());
    }
    @Override
    protected TypeAndPoint getTypeAndPointForKnowledgeOwner() {
        return null;
    }
    @Override
    protected TypeAndPoint getTypeAndPointForKnowledge() {
        return new TypeAndPoint(TYPE_KNOWLEDGE_INSERTED, getPoint());
    }
}
