package org.support.project.knowledge.logic.activity;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.di.Container;

/**
 * 
 * 6    | 61          | 参照者       | 5        | イベント参加者にポイント付与
 * 6    | 62          | 記事登録者    | 5       | 記事の登録者にポイント追加（一つの記事に対し、参照者毎に1回のみ）
 * 6    | 63          | 記事         | 5       | 記事のポイントが追加（一つの記事に対し、参照者毎に1回のみ）
 * 
 * @author koda
 */
public class KnowledgeEventActivity extends AbstractAddPointForKnowledgeProcessor {
    private static final Log LOG = LogFactory.getLog(KnowledgeEventActivity.class);
    public static KnowledgeEventActivity get() {
        return Container.getComp(KnowledgeEventActivity.class);
    }
    
    @Override
    protected Activity getActivity() {
        LOG.debug("Start add point process on answer knowledge.");
        return Activity.KNOWLEDGE_ANSWER;
    }
    @Override
    protected TypeAndPoint getTypeAndPointForActivityExecuter() {
        return new TypeAndPoint(TYPE_KNOWLEDGE_DO_JOIN_EVENT, 5);
    }
    @Override
    protected TypeAndPoint getTypeAndPointForKnowledgeOwner() {
        return new TypeAndPoint(TYPE_KNOWLEDGE_JOINED_BY_OHER, 5);
    }
    @Override
    protected TypeAndPoint getTypeAndPointForKnowledge() {
        return new TypeAndPoint(TYPE_KNOWLEDGE_JOINED, 5);
    }

}
