package org.support.project.knowledge.logic.activity;

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
}
