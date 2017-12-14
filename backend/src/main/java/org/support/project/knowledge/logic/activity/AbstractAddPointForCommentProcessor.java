package org.support.project.knowledge.logic.activity;

import org.support.project.aop.Aspect;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.dao.KnowledgesDao;
import org.support.project.knowledge.entity.ActivitiesEntity;
import org.support.project.knowledge.entity.CommentsEntity;
import org.support.project.knowledge.entity.KnowledgesEntity;

@DI(instance = Instance.Prototype)
public abstract class AbstractAddPointForCommentProcessor extends AbstractActivityProcessor {
    private static final Log LOG = LogFactory.getLog(AbstractAddPointForCommentProcessor.class);
    private CommentsEntity comment;
    private KnowledgesEntity parentKnowledge;
    /**
     * @return the comment
     */
    public CommentsEntity getComment() {
        return comment;
    }
    /**
     * @param comment the comment to set
     */
    public void setComment(CommentsEntity comment) {
        this.comment = comment;
        this.parentKnowledge = KnowledgesDao.get().selectOnKey(getComment().getKnowledgeId());
    }
    public KnowledgesEntity getParentKnowledge() {
        return parentKnowledge;
    }

    protected abstract Activity getActivity();
    protected abstract TypeAndPoint getTypeAndPointForActivityExecuter();
    protected abstract TypeAndPoint getTypeAndPointForCommentOwner();
    protected abstract TypeAndPoint getTypeAndPointForKnowledge();
    
    @Override
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void execute() throws Exception {
        if (getComment() == null || eventUser == null) {
            // ありえないけど念のため確認
            return;
        }
        if (isExistsActivity(eventUser.getUserId(), getActivity(), String.valueOf(getComment().getCommentNo()))) {
            LOG.debug("This activity is already exists. [Activity]" + getActivity().toString() + " [user]" + eventUser.getUserId()
                    + " [comment]" + getComment().getCommentNo());
            return;
        }
        if (parentKnowledge == null) {
            LOG.debug("Knowledge is not found. [comment] " + getComment().getCommentNo() + " [knowledge]" + getComment().getKnowledgeId());
            return;
        }
        
        TypeAndPoint exec = getTypeAndPointForActivityExecuter();
        TypeAndPoint owner = getTypeAndPointForCommentOwner();
        TypeAndPoint knowledge = getTypeAndPointForKnowledge();
        if (exec == null && owner == null && knowledge == null) {
            // ポイントをつける対象が無いので、処理終了
            LOG.debug("This activity is not add point. [Activity]" + getActivity().toString() + " [user]" + eventUser.getUserId()
            + " [comment]" + getComment().getCommentNo());
            return;
        }

        
        LOG.debug("activity process started. [Activity]" + getActivity().toString() + " [user]" + eventUser.getUserId()
        + " [comment]" + getComment().getCommentNo());
        
        StringBuilder logmsg = new StringBuilder();
        logmsg.append("Activity : " + getActivity().toString());
        
         // ポイント発行アクティビティを生成
        ActivitiesEntity activity = addActivity(
                getActivity(),
                String.valueOf(getComment().getCommentNo()));
        
        // 実行したユーザのポイントアップ
        if (exec != null) {
            int point = addPointForUser(
                    eventUser.getUserId(), // ターゲットは、実行したユーザ
                    activity.getActivityNo(),
                    exec.type,
                    exec.point);
            logmsg.append("\n\tAdd event user: [id]" + eventUser.getUserId() + " [type]" + exec.type + " [add]" + exec.point + " [result]" + point);
        }
         // コメントの登録者のポイントをアップ
        if (owner != null) {
            int point = addPointForUser(
                    getComment().getInsertUser(), // ターゲットは登録者
                    activity.getActivityNo(),
                    owner.type,
                    owner.point);
            logmsg.append("\n\tAdd owner user: [id]" + getComment().getInsertUser() + " [type]" + owner.type + " [add]" + owner.point + " [result]" + point);
        }
         // 記事のポイントアップ（コメントにはポイントをもっていないので、親のナレッジのポイントをアップ）
        if (knowledge != null) {
            int point = addPointForKnowledge(
                    parentKnowledge.getKnowledgeId(),
                    activity.getActivityNo(),
                    knowledge.type,
                    knowledge.point);
            logmsg.append("\n\tAdd knowledge: [id]" + parentKnowledge.getKnowledgeId() + " [type]" + knowledge.type + " [add]" + knowledge.point + " [result]" + point);
        }
        LOG.debug(logmsg.toString());
    }
    
    
}
