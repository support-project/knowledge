package org.support.project.knowledge.logic.activity;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.di.Container;

/**
 * 
 * 101  | 1011        | 登録者       | 20       | コメントを投稿すると、投稿者にポイント追加
 * 101  | 1012        | 記事         | 20       | 記事にコメントが付くと、その記事に対しポイント追加
 * 
 * @author koda
 */
public class KnowledgeCommentActivity extends AbstractAddPointForCommentProcessor {
    private static final Log LOG = LogFactory.getLog(KnowledgeCommentActivity.class);
    public static KnowledgeCommentActivity get() {
        return Container.getComp(KnowledgeCommentActivity.class);
    }
    
    @Override
    protected Activity getActivity() {
        LOG.debug("Start add point process on add comment knowledge.");
        return Activity.KNOWLEDGE_COMMENT_ADD;
    }
    @Override
    protected TypeAndPoint getTypeAndPointForActivityExecuter() {
        return new TypeAndPoint(TYPE_KNOWLEDGE_DO_ADD_COMMENT, 20);
    }
    @Override
    protected TypeAndPoint getTypeAndPointForCommentOwner() {
        return null;
    }
    @Override
    protected TypeAndPoint getTypeAndPointForKnowledge() {
        return new TypeAndPoint(TYPE_KNOWLEDGE_ADDED_COMMENT, 20);
    }

}
