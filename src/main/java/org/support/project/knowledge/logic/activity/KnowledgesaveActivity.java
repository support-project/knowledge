package org.support.project.knowledge.logic.activity;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.StringUtils;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.dao.ActivitiesDao;
import org.support.project.knowledge.entity.ActivitiesEntity;

/**
 * ナレッジ投稿時のポイント付与
 * 過去の投稿の公開区分によって、今回獲得するポイントが変化する
 * 
 * 種類  | 獲得のタイプ  | ポイント付与先 | ポイント    | 獲得タイプの意味
 * 10   | 101         | 記事登録者    | 50       | 公開になった時点でトータル50になるように
 * 10   | 103         | 記事         | 50       | 
 * 11   | 111         | 記事登録者    | 30       | 
 * 11   | 113         | 記事         | 30       | 
 * 12   | 121         | 記事登録者    | 0        | このアクティビティの前に、投稿のアクティビティがあった場合、それを打ち消す（マイナスのポイント）
 * 12   | 123         | 記事         | 0        | 
 * @author koda
 */
@DI(instance = Instance.Prototype)
public class KnowledgesaveActivity extends AbstractAddPointForKnowledgeProcessor implements MultiActivityProcessor {
    private static final Log LOG = LogFactory.getLog(KnowledgesaveActivity.class);
    public static KnowledgesaveActivity get() {
        return Container.getComp(KnowledgesaveActivity.class);
    }
    private Activity activity;
    public void setActivity(Activity activity) {
        this.activity = activity;
    }
    @Override
    protected Activity getActivity() {
        LOG.debug("Activity process start. " + activity.toString());
        return activity;
    }
    private int point = 0;
    private int getKindPoint(int kind) {
        if (kind == Activity.KNOWLEDGE_POST_PUBLIC.getValue()) {
            return 50;
        } else if (kind == Activity.KNOWLEDGE_POST_PROTECTED.getValue()) {
            return 30;
        }
        return 0;
    }
    private int getPoint() {
        if (point != 0) {
            return point;
        }
        // 過去に登録した情報があるか検索
        ActivitiesEntity before = ActivitiesDao.get().selectBefore(
                super.eventUser.getUserId(),
                String.valueOf(getKnowledge().getKnowledgeId()),
                Activity.KNOWLEDGE_POST_PUBLIC.getValue(),
                Activity.KNOWLEDGE_POST_PROTECTED.getValue(),
                Activity.KNOWLEDGE_POST_PRIVATE.getValue()
                );
        int point = getKindPoint(getActivity().getValue());
        if (before == null) {
            if (getActivity() == Activity.KNOWLEDGE_POST_PRIVATE) {
                // 過去に登録した履歴が無く、今回が非公開ならポイント付けない
                return 0;
            }
            // 文章が多い力先はポイントが高いように調整(初めの一回なので、初めの一回を超えて
            if (StringUtils.isNotEmpty(getKnowledge().getContent()) && getKnowledge().getContent().length() > 1000) {
                int add = (getKnowledge().getContent().length() - 1000) / 100;
                if (add > 100) {
                    add = 100;
                }
                point += add;
            }
        } else {
            point = point - getKindPoint(before.getKind());
        }
        this.point = point;
        return point;
    }
    private int getTypeForExecuter() {
        if (getActivity() == Activity.KNOWLEDGE_POST_PUBLIC) {
            return TYPE_KNOWLEDGE_DO_POST_PUBLIC;
        } else if (getActivity() == Activity.KNOWLEDGE_POST_PROTECTED) {
            return TYPE_KNOWLEDGE_DO_POST_PROTECT;
        } else if (getActivity() == Activity.KNOWLEDGE_POST_PRIVATE) {
            return TYPE_KNOWLEDGE_DO_POST_PRIVATE;
        }
        return -1;
    }
    private int getTypeForKnowledge() {
        if (getActivity() == Activity.KNOWLEDGE_POST_PUBLIC) {
            return TYPE_KNOWLEDGE_POSTED_PUBLIC;
        } else if (getActivity() == Activity.KNOWLEDGE_POST_PROTECTED) {
            return TYPE_KNOWLEDGE_POSTED_PROTECT;
        } else if (getActivity() == Activity.KNOWLEDGE_POST_PRIVATE) {
            return TYPE_KNOWLEDGE_POSTED_PRIVATE;
        }
        return -1;
    }
    
    @Override
    protected TypeAndPoint getTypeAndPointForActivityExecuter() {
        int point = getPoint();
        if (point == 0) {
            return null;
        }
        return new TypeAndPoint(getTypeForExecuter(), point);
    }
    @Override
    protected TypeAndPoint getTypeAndPointForKnowledgeOwner() {
        return null;
    }
    @Override
    protected TypeAndPoint getTypeAndPointForKnowledge() {
        int point = getPoint();
        if (point == 0) {
            return null;
        }
        return new TypeAndPoint(getTypeForKnowledge(), point);
    }
}
