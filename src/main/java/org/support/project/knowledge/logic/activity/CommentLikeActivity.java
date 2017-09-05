package org.support.project.knowledge.logic.activity;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.RandomUtil;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.dao.LikeCommentsDao;

/**
 * 
 * 103  | 1031        | 参照者       | 2       | イイネを押すと、押した人にポイント追加
 * 103  | 1032        | 登録者       | 10       | コメントにイイネが付くと、そのコメントを登録したユーザにポイントが付く
 * 103  | 1033        | 記事         | 10       | コメントにイイネがつくと、そのコメントの記事に対しポイント追加
 * 
 * @author koda
 */
@DI(instance = Instance.Prototype)
public class CommentLikeActivity extends AbstractAddPointForCommentProcessor {
    private static final Log LOG = LogFactory.getLog(CommentLikeActivity.class);
    public static CommentLikeActivity get() {
        return Container.getComp(CommentLikeActivity.class);
    }
    
    private int point = 0;
    
    private int getPoint() {
        if (point != 0) {
            return point;
        }
        // 指定のコメントについたイイネの件数（ユニーク件数）でポイントを増やす
        int point = 10;
        long count = LikeCommentsDao.get().selectUniqueUserCountOnCommentNo(getComment().getCommentNo());
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
        return Activity.COMMENT_LIKE;
    }
    @Override
    protected TypeAndPoint getTypeAndPointForActivityExecuter() {
        return new TypeAndPoint(TYPE_COMMENT_DO_LIKE, 2);
    }
    @Override
    protected TypeAndPoint getTypeAndPointForCommentOwner() {
        return new TypeAndPoint(TYPE_COMMENT_LIKED_BY_OHER, getPoint());
    }
    @Override
    protected TypeAndPoint getTypeAndPointForKnowledge() {
        return new TypeAndPoint(TYPE_COMMENT_LIKED, getPoint());
    }

}
