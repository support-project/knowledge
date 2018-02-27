package org.support.project.knowledge.entity;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.entity.gen.GenDraftKnowledgesEntity;
import org.support.project.knowledge.vo.api.ArticleDataInterface;


/**
 * ナレッジの下書き
 */
@DI(instance = Instance.Prototype)
public class DraftKnowledgesEntity extends GenDraftKnowledgesEntity implements ArticleDataInterface {

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
    
    @Override
    public String getEditors() {
        if (super.getEditors() == null) {
            return "";
        }
        return super.getEditors();
    }
    @Override
    public String getAccesses() {
        if (super.getAccesses() == null) {
            return "";
        }
        return super.getAccesses();
    }
}
