package org.support.project.knowledge.logic.activity;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.RandomUtil;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.dao.CommentsDao;
import org.support.project.knowledge.logic.KnowledgeLogic;

/**
 * 
 * 101  | 1011        | 登録者       | 20       | コメントを投稿すると、投稿者にポイント追加
 * 101  | 1012        | 記事         | 20       | 記事にコメントが付くと、その記事に対しポイント追加
 * 
 * @author koda
 */
@DI(instance = Instance.Prototype)
public class CommentInsertActivity extends AbstractAddPointForCommentProcessor {
    private static final Log LOG = LogFactory.getLog(CommentInsertActivity.class);
    public static CommentInsertActivity get() {
        return Container.getComp(CommentInsertActivity.class);
    }
    
    private int point = 0;
    
    private int getKnowledgePoint() {
        if (getParentKnowledge().getPublicFlag() == KnowledgeLogic.PUBLIC_FLAG_PRIVATE) {
            // 非公開の記事にポイントをつけてもポイントにはならない
            return 0;
        }
        int point = 0;
        if (getParentKnowledge().getPublicFlag() == KnowledgeLogic.PUBLIC_FLAG_PUBLIC) {
            point = 10;
        } else if (getParentKnowledge().getPublicFlag() == KnowledgeLogic.PUBLIC_FLAG_PROTECT) {
            point = 5;
        }
        return point;
    }
    
    private int getPoint() {
        if (point != 0) {
            return point;
        }
        if (getParentKnowledge().getPublicFlag() == KnowledgeLogic.PUBLIC_FLAG_PRIVATE) {
            // 非公開の記事にポイントをつけてもポイントにはならない
            return 0;
        }
        // 指定の記事に登録した、ユニークなユーザ数によりポイントを変える
        int point = getKnowledgePoint();
        long count = CommentsDao.get().selectUniqueUserCountOnKnowledgeId(getComment().getKnowledgeId());
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
        point += add;
        this.point = point;
        return point;
    }
    
    
    @Override
    protected Activity getActivity() {
        LOG.debug("Start add point process on add comment knowledge.");
        return Activity.COMMENT_INSERT;
    }
    @Override
    protected TypeAndPoint getTypeAndPointForActivityExecuter() {
        int point = getKnowledgePoint();
        if (point == 0) {
            return null;
        }
        return new TypeAndPoint(TYPE_COMMENT_DO_INSERT, point);
    }
    @Override
    protected TypeAndPoint getTypeAndPointForCommentOwner() {
        return null;
    }
    @Override
    protected TypeAndPoint getTypeAndPointForKnowledge() {
        int point = getPoint();
        if (point == 0) {
            return null;
        }
        return new TypeAndPoint(TYPE_COMMENT_INSERTED, point);
    }

}
