package org.support.project.knowledge.logic.activity;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;

/**
 * 
 * 4    | 41          | 参照者       | 0        | ストックした場合、ストックした人にポイントは付与しない
 * 4    | 42          | 記事登録者    | 2       | 記事の登録者にポイント追加（一つの記事に対し、参照者毎に1回のみ）
 * 4    | 43          | 記事         | 2       | 記事のポイントが追加（一つの記事に対し、参照者毎に1回のみ）
 * 
 * @author koda
 */
@DI(instance = Instance.Prototype)
public class KnowledgeStockActivity extends AbstractAddPointForKnowledgeProcessor {
    private static final Log LOG = LogFactory.getLog(KnowledgeStockActivity.class);
    public static KnowledgeStockActivity get() {
        return Container.getComp(KnowledgeStockActivity.class);
    }
    
    private int point = 0;
    
    private int getPoint() {
        if (point != 0) {
            return point;
        }
//        int[] points = {1,1,1,1,1,2,2,2,2,3};
//        this.point = points[RandomUtil.randamNum(0, 10)]; // ランダムで値を増減してみた
        this.point = 2;
        return point;
    }
    
    
    @Override
    protected Activity getActivity() {
        LOG.debug("Start add point process on stock knowledge.");
        return Activity.KNOWLEDGE_STOCK;
    }
    @Override
    protected TypeAndPoint getTypeAndPointForActivityExecuter() {
        return null;
    }
    @Override
    protected TypeAndPoint getTypeAndPointForKnowledgeOwner() {
        if (eventUser.getUserId().intValue() == getKnowledge().getInsertUser().intValue()) {
            return null;
        }
        return new TypeAndPoint(TYPE_KNOWLEDGE_STOCKED_BY_OHER, getPoint());
    }
    @Override
    protected TypeAndPoint getTypeAndPointForKnowledge() {
        if (eventUser.getUserId().intValue() == getKnowledge().getInsertUser().intValue()) {
            return null;
        }
        return new TypeAndPoint(TYPE_KNOWLEDGE_STOCKED, getPoint());
    }

}
