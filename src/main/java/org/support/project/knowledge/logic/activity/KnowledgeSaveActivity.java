package org.support.project.knowledge.logic.activity;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.dao.PointKnowledgeHistoriesDao;

/**
 * ナレッジ投稿時のポイント付与
 * 過去の投稿の公開区分によって、今回獲得するポイントが変化する
 * 
 * 種類  | 獲得のタイプ  | ポイント付与先 | ポイント    | 獲得タイプの意味
 * 10   | 101         | 記事登録者    | 20       | 公開になった時点でトータル50になるように
 * 10   | 103         | 記事         | 20       | 
 * 11   | 111         | 記事登録者    | 10       | 
 * 11   | 113         | 記事         | 10       | 
 * 12   | 121         | 記事登録者    | 0        | このアクティビティの前に、投稿のアクティビティがあった場合、それを打ち消す（マイナスのポイント）
 * 12   | 123         | 記事         | 0        | 
 * @author koda
 */
@DI(instance = Instance.Prototype)
public class KnowledgeSaveActivity extends AbstractAddPointForKnowledgeProcessor implements MultiActivityProcessor {
    private static final Log LOG = LogFactory.getLog(KnowledgeSaveActivity.class);
    public static KnowledgeSaveActivity get() {
        return Container.getComp(KnowledgeSaveActivity.class);
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
            return 20;
        } else if (kind == Activity.KNOWLEDGE_POST_PROTECTED.getValue()) {
            return 10;
        }
        return 0;
    }
    private int getPoint() {
        if (point != 0) {
            return point;
        }
        int beforePoint = PointKnowledgeHistoriesDao.get().selectBeforePoint(
                getKnowledge().getKnowledgeId(), TYPE_KNOWLEDGE_POSTED_PUBLIC, TYPE_KNOWLEDGE_POSTED_PROTECT, TYPE_KNOWLEDGE_POSTED_PRIVATE);
        int nowPoint = getKindPoint(getActivity().getValue());
        point = nowPoint - beforePoint;
        if (LOG.isDebugEnabled()) {
            LOG.debug("[ID]" + getKnowledge().getKnowledgeId() + "[POINT NOW]" + nowPoint + "  [POINT BEFORE]" + beforePoint + " [ADD RESULT]" + point);
        }
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
