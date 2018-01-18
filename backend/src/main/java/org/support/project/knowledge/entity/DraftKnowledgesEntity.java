package org.support.project.knowledge.entity;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.entity.gen.GenDraftKnowledgesEntity;
import org.support.project.knowledge.vo.KnowledgeDataInterface;


/**
 * ナレッジの下書き
 */
@DI(instance = Instance.Prototype)
public class DraftKnowledgesEntity extends GenDraftKnowledgesEntity implements KnowledgeDataInterface {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * Get instance from DI container.
     * @return instance
     */
    public static DraftKnowledgesEntity get() {
        return Container.getComp(DraftKnowledgesEntity.class);
    }

    /**
     * Constructor.
     */
    public DraftKnowledgesEntity() {
        super();
    }

    /**
     * Constructor
     * @param draftId 下書きID
     */

    public DraftKnowledgesEntity(Long draftId) {
        super( draftId);
    }

}
