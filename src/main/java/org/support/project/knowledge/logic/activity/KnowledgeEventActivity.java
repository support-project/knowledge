package org.support.project.knowledge.logic.activity;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.RandomUtil;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.dao.ActivitiesDao;

/**
 * 
 * 6    | 61          | 参照者       | 5        | イベント参加者にポイント付与
 * 6    | 62          | 記事登録者    | 5       | 記事の登録者にポイント追加（一つの記事に対し、参照者毎に1回のみ）
 * 6    | 63          | 記事         | 5       | 記事のポイントが追加（一つの記事に対し、参照者毎に1回のみ）
 * 
 * @author koda
 */
@DI(instance = Instance.Prototype)
public class KnowledgeEventActivity extends AbstractAddPointForKnowledgeProcessor {
    private static final Log LOG = LogFactory.getLog(KnowledgeEventActivity.class);
    public static KnowledgeEventActivity get() {
        return Container.getComp(KnowledgeEventActivity.class);
    }
    
    private int point = 0;
    
    private int getPoint() {
        if (point != 0) {
            return point;
        }
        // 参加者人数により増減
        int point = 5;
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
        LOG.debug("Start add point process on event knowledge.");
        return Activity.KNOWLEDGE_EVENT_ADD;
    }
    @Override
    protected TypeAndPoint getTypeAndPointForActivityExecuter() {
        return new TypeAndPoint(TYPE_KNOWLEDGE_DO_JOIN_EVENT, 5); // 参加者には固定のポイント
    }
    @Override
    protected TypeAndPoint getTypeAndPointForKnowledgeOwner() {
        if (eventUser.getUserId().intValue() == getKnowledge().getInsertUser().intValue()) {
            return null;
        }
        return new TypeAndPoint(TYPE_KNOWLEDGE_JOINED_BY_OHER, getPoint());
    }
    @Override
    protected TypeAndPoint getTypeAndPointForKnowledge() {
        return new TypeAndPoint(TYPE_KNOWLEDGE_JOINED, getPoint());
    }

}
