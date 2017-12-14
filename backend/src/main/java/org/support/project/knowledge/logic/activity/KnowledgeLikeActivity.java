package org.support.project.knowledge.logic.activity;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.RandomUtil;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.dao.ActivitiesDao;
import org.support.project.knowledge.logic.KnowledgeLogic;

/**
 * 
 * 3    | 31          | 参照者       | 2        | 記事にイイネのアクションを行うと、参照者にポイント追加（一つの記事に付き1回のみ）
 * 3    | 32          | 記事登録者    | 10       | 自分が登録された記事にイイネがついたら、登録者にポイント追加（一つの記事に対し、参照者毎に1回のみ）
 * 3    | 33          | 記事         | 10       | 記事が参照されると、その記事のポイントが追加（一つの記事に対し、参照者毎に1回のみ）
 * 
 * @author koda
 */
@DI(instance = Instance.Prototype)
public class KnowledgeLikeActivity extends AbstractAddPointForKnowledgeProcessor {
    private static final Log LOG = LogFactory.getLog(KnowledgeLikeActivity.class);
    public static KnowledgeLikeActivity get() {
        return Container.getComp(KnowledgeLikeActivity.class);
    }
    
    private int point = 0;
    
    private int getPoint() {
        if (point != 0) {
            return point;
        }
        // ユニークユーザのイイネ件数によりポイントを増やす
        int point = 0;
        if (getKnowledge().getPublicFlag() == KnowledgeLogic.PUBLIC_FLAG_PUBLIC) {
            point = 10;
        } else if (getKnowledge().getPublicFlag() == KnowledgeLogic.PUBLIC_FLAG_PROTECT) {
            point = 5;
        }
         long count = ActivitiesDao.get().selectCountByTarget(
                getActivity().getValue(), getKnowledge().getKnowledgeId());
        int add = 0;
        if (count > 100) {
            add = 1000;
        } else if (count > 100){
            add = ((int) count - 100 ) / 2; // 100人を超えると、2人毎に1ポイント増えるようになる
        } else if (count > 10){
            add = (int) count / 5; // 5人を超えると、ポイントが増える(5人毎に１ポイント）
            int[] points = {1,1,1,1,1,2,2,2,2,3};
            add += points[RandomUtil.randamNum(0, 10)]; // ランダムで値が増減するボーナスポイント
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("[Bonus point]: " + add + " [COUNT]:" + count);
        }
        point += add;
        this.point = point;
        return point;
    }
    
    @Override
    protected Activity getActivity() {
        LOG.debug("Start add point process on like knowledge.");
        return Activity.KNOWLEDGE_LIKE;
    }
    @Override
    protected TypeAndPoint getTypeAndPointForActivityExecuter() {
        return new TypeAndPoint(TYPE_KNOWLEDGE_DO_LIKE, 2);
    }
    @Override
    protected TypeAndPoint getTypeAndPointForKnowledgeOwner() {
        if (eventUser.getUserId().intValue() == getKnowledge().getInsertUser().intValue()) {
            return null;
        }
        return new TypeAndPoint(TYPE_KNOWLEDGE_LIKED_BY_OHER, getPoint());
    }
    @Override
    protected TypeAndPoint getTypeAndPointForKnowledge() {
        if (eventUser.getUserId().intValue() == getKnowledge().getInsertUser().intValue()) {
            return null;
        }
        return new TypeAndPoint(TYPE_KNOWLEDGE_LIKED, getPoint());
    }

}
