package org.support.project.knowledge.logic.activity;

import org.bouncycastle.jcajce.provider.asymmetric.dsa.DSASigner.noneDSA;
import org.support.project.aop.Aspect;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.entity.ActivitiesEntity;
import org.support.project.knowledge.entity.KnowledgesEntity;

@DI(instance = Instance.Prototype)
public abstract class AbstractAddPointForKnowledgeProcessor extends AbstractActivityProcessor {
    private static final Log LOG = LogFactory.getLog(AbstractAddPointForKnowledgeProcessor.class);
    private KnowledgesEntity knowledge;
    /**
     * @return the knowledge
     */
    public KnowledgesEntity getKnowledge() {
        return knowledge;
    }
    /**
     * @param knowledge the knowledge to set
     */
    public void setKnowledge(KnowledgesEntity knowledge) {
        this.knowledge = knowledge;
    }
    
    protected abstract Activity getActivity();
    protected abstract TypeAndPoint getTypeAndPointForActivityExecuter();
    protected abstract TypeAndPoint getTypeAndPointForKnowledgeOwner();
    protected abstract TypeAndPoint getTypeAndPointForKnowledge();
    
    @Override
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void execute() throws Exception {
        if (getKnowledge() == null || eventUser == null) {
            // ありえないけど念のため確認
            return;
        }
        if (isExistsActivity(eventUser.getUserId(), getActivity(), String.valueOf(getKnowledge().getKnowledgeId()))) {
            LOG.info("This activity is already exists. [Activity]" + getActivity().toString() + " [user]" + eventUser.getUserId()
                    + " [knowledge]" + getKnowledge().getKnowledgeId());
            // 既に指定のKnowledge登録済
            return;
        }
        TypeAndPoint exec = getTypeAndPointForActivityExecuter();
        TypeAndPoint owner = getTypeAndPointForKnowledgeOwner();
        TypeAndPoint knowledge = getTypeAndPointForKnowledge();
        if (exec == null && owner == null && knowledge == null) {
            // ポイントをつける対象が無いので、処理終了
            LOG.info("This activity is not add point. [Activity]" + getActivity().toString() + " [user]" + eventUser.getUserId()
            + " [knowledge]" + getKnowledge().getKnowledgeId());
            return;
        }
        LOG.info("activity process started. [Activity]" + getActivity().toString() + " [user]" + eventUser.getUserId()
        + " [knowledge]" + getKnowledge().getKnowledgeId());
        
        StringBuilder logmsg = new StringBuilder();
        logmsg.append("Activity : " + getActivity().toString());
         // ポイント発行アクティビティを生成
        ActivitiesEntity activity = addActivity(
                getActivity(),
                String.valueOf(getKnowledge().getKnowledgeId()));
        
        // 実行したユーザのポイントアップ
        if (exec != null) {
            int point = addPointForUser(
                    eventUser.getUserId(), // ターゲットは、実行したユーザ
                    activity.getActivityNo(),
                    exec.type,
                    exec.point);
            logmsg.append("\n\tAdd event user: [id]" + eventUser.getUserId() + " [type]" + exec.type + " [add]" + exec.point + " [result]" + point);
        }
         // 記事の登録者のポイントをアップ
        if (owner != null) {
            int point = addPointForUser(
                    getKnowledge().getInsertUser(), // ターゲットは登録者
                    activity.getActivityNo(),
                    owner.type,
                    owner.point);
            logmsg.append("\n\tAdd owner user: [id]" + getKnowledge().getInsertUser() + " [type]" + owner.type + " [add]" + owner.point + " [result]" + point);
        }
         // 記事のポイントアップ
        if (knowledge != null) {
            int point = addPointForKnowledge(
                    getKnowledge().getKnowledgeId(),
                    activity.getActivityNo(),
                    knowledge.type,
                    knowledge.point);
            logmsg.append("\n\tAdd knowledge: [id]" + getKnowledge().getKnowledgeId() + " [type]" + knowledge.type + " [add]" + knowledge.point + " [result]" + point);
        }
        LOG.info(logmsg.toString());
    }


}
