package org.support.project.knowledge.logic.activity;

import org.support.project.knowledge.entity.ActivitiesEntity;
import org.support.project.knowledge.entity.KnowledgesEntity;

public abstract class AbstractAddPointForKnowledgeProcessor extends AbstractActivityProcessor {
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
    public void execute() throws Exception {
        if (getKnowledge() == null || getUser() == null) {
            // ありえないけど念のため確認
            return;
        }
        if (isExistsActivity(getUser().getUserId(), getActivity(), String.valueOf(getKnowledge().getKnowledgeId()))) {
            // 既に指定のKnowledge登録済
            return;
        }
         // ポイント発行アクティビティを生成
        ActivitiesEntity activity = addActivity(
                getUser().getUserId(),
                getActivity(),
                String.valueOf(getKnowledge().getKnowledgeId()),
                getKnowledge().getInsertDatetime());
        
        
        
        // 実行したユーザのポイントアップ
        TypeAndPoint exec = getTypeAndPointForActivityExecuter();
        if (exec != null) {
            addPointForUser(
                    getUser().getUserId(),
                    getKnowledge().getInsertDatetime(),
                    getUser().getUserId(), // ターゲットは、実行したユーザ
                    activity.getActivityNo(),
                    exec.type,
                    exec.point);
        }
         // 記事の登録者のポイントをアップ
        TypeAndPoint owner = getTypeAndPointForKnowledgeOwner();
        if (owner != null) {
            addPointForUser(
                    getUser().getUserId(),
                    getKnowledge().getInsertDatetime(),
                    getKnowledge().getInsertUser(), // ターゲットは登録者
                    activity.getActivityNo(),
                    exec.type,
                    exec.point);
        }
         // 記事のポイントアップ
        TypeAndPoint knowledge = getTypeAndPointForKnowledge();
        if (knowledge != null) {
            addPointForKnowledge(
                    getUser().getUserId(),
                    getKnowledge().getInsertDatetime(),
                    getKnowledge().getKnowledgeId(),
                    activity.getActivityNo(),
                    exec.type,
                    exec.point);
        }
    }


}
