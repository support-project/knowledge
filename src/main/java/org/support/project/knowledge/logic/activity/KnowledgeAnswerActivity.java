package org.support.project.knowledge.logic.activity;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.di.Container;

/**
 * 
 * 5    | 51          | 参照者       | 3        | アンケート回答者にポイント付与
 * 5    | 52          | 記事登録者    | 3       | 記事の登録者にポイント追加（一つの記事に対し、参照者毎に1回のみ）
 * 5    | 53          | 記事         | 3       | 記事のポイントが追加（一つの記事に対し、参照者毎に1回のみ）
 * 
 * @author koda
 */
public class KnowledgeAnswerActivity extends AbstractAddPointForKnowledgeProcessor {
    private static final Log LOG = LogFactory.getLog(KnowledgeAnswerActivity.class);
    public static KnowledgeAnswerActivity get() {
        return Container.getComp(KnowledgeAnswerActivity.class);
    }
    
    @Override
    protected Activity getActivity() {
        LOG.debug("Start add point process on answer knowledge.");
        return Activity.KNOWLEDGE_ANSWER;
    }
    @Override
    protected TypeAndPoint getTypeAndPointForActivityExecuter() {
        return new TypeAndPoint(TYPE_KNOWLEDGE_DO_ANSWER, 3);
    }
    @Override
    protected TypeAndPoint getTypeAndPointForKnowledgeOwner() {
        return new TypeAndPoint(TYPE_KNOWLEDGE_ANSWERD_BY_OHER, 3);
    }
    @Override
    protected TypeAndPoint getTypeAndPointForKnowledge() {
        return new TypeAndPoint(TYPE_KNOWLEDGE_ANSWERD, 3);
    }

}
