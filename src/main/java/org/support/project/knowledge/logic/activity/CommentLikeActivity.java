package org.support.project.knowledge.logic.activity;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.di.Container;

/**
 * 
 * 103  | 1031        | 参照者       | 2       | イイネを押すと、押した人にポイント追加
 * 103  | 1032        | 登録者       | 10       | コメントにイイネが付くと、そのコメントを登録したユーザにポイントが付く
 * 103  | 1033        | 記事         | 10       | コメントにイイネがつくと、そのコメントの記事に対しポイント追加
 * 
 * @author koda
 */
public class CommentLikeActivity extends AbstractAddPointForCommentProcessor {
    private static final Log LOG = LogFactory.getLog(CommentLikeActivity.class);
    public static CommentLikeActivity get() {
        return Container.getComp(CommentLikeActivity.class);
    }
    
    @Override
    protected Activity getActivity() {
        LOG.debug("Start add point process on add comment knowledge.");
        return Activity.COMMENT_LIKE;
    }
    @Override
    protected TypeAndPoint getTypeAndPointForActivityExecuter() {
        return new TypeAndPoint(TYPE_COMMENT_DO_LIKE, 2);
    }
    @Override
    protected TypeAndPoint getTypeAndPointForCommentOwner() {
        return new TypeAndPoint(TYPE_COMMENT_LIKED_BY_OHER, 10);
    }
    @Override
    protected TypeAndPoint getTypeAndPointForKnowledge() {
        return new TypeAndPoint(TYPE_COMMENT_LIKED, 10);
    }

}
