package org.support.project.knowledge.logic.activity;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.dao.ActivitiesDao;

/**
 * 
 * 種類  | 獲得のタイプ  | ポイント付与先 | ポイント    | 獲得タイプの意味
 * 2    | 21          | 参照者       | 1        | 記事を参照するアクションを行うと、参照者にポイント追加（一つの記事に付き1回のみ）
 * 2    | 22          | 記事登録者    | 1        | 自分が登録された記事が参照されたら、登録者にポイント追加（一つの記事に対し、参照者毎に1回のみ）
 * 2    | 23          | 記事         | 1        | 記事が参照されると、その記事のポイントが追加（一つの記事に対し、参照者毎に1回のみ）
 * 
 * @author koda
 */
@DI(instance = Instance.Prototype)
public class KnowledgeShowActivity extends AbstractAddPointForKnowledgeProcessor {
    private static final Log LOG = LogFactory.getLog(KnowledgeShowActivity.class);
    public static KnowledgeShowActivity get() {
        return Container.getComp(KnowledgeShowActivity.class);
    }
    
    @Override
    protected Activity getActivity() {
        LOG.debug("Start add point process on show knowledge.");
        return Activity.KNOWLEDGE_SHOW;
    }
    private int getPoint() {
        // ユニークな参照者が多くなると、ポイントが増えるように調整(投稿数が少なくても、良い記事を書けばポイントが高くなる）
        int point = 1;
        long count = ActivitiesDao.get().selectCountByTarget(
                getActivity().getValue(), getKnowledge().getKnowledgeId());
        int add = 0;
        if (count > 1000) {
            add = 100; // 1000 人以上だと100固定
        } else if (count > 100){
            add = 10 + ((int) count - 100) / 15; // 100人を超えると、20人毎に1ポイント増えるようになる
        } else if (count > 10){
            add = ((int) count) / 10; // 10人を超えると、ポイントが増える(10人毎に１ポイント）
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("[Bonus point]: " + add + " [COUNT]:" + count);
        }
        point += add;
        return point;
    }
    @Override
    protected TypeAndPoint getTypeAndPointForActivityExecuter() {
        if (eventUser.getUserId().intValue() == getKnowledge().getInsertUser().intValue()) {
            return null;
        }
        return new TypeAndPoint(TYPE_KNOWLEDGE_DO_SHOW, 1);
    }
    @Override
    protected TypeAndPoint getTypeAndPointForKnowledgeOwner() {
        if (eventUser.getUserId().intValue() == getKnowledge().getInsertUser().intValue()) {
            return null;
        }
        return new TypeAndPoint(TYPE_KNOWLEDGE_SHOWN_BY_OHER, getPoint());
    }
    @Override
    protected TypeAndPoint getTypeAndPointForKnowledge() {
        if (eventUser.getUserId().intValue() == getKnowledge().getInsertUser().intValue()) {
            return null;
        }
        return new TypeAndPoint(TYPE_KNOWLEDGE_SHOWN, getPoint());
    }

}
