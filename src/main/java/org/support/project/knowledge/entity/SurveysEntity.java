package org.support.project.knowledge.entity;

import org.support.project.knowledge.entity.gen.GenSurveysEntity;

import java.util.List;
import java.util.Map;

import org.support.project.common.bean.ValidateError;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;

import java.sql.Timestamp;


/**
 * アンケート
 */
@DI(instance = Instance.Prototype)
public class SurveysEntity extends GenSurveysEntity {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * Get instance from DI container.
     * @return instance
     */
    public static SurveysEntity get() {
        return Container.getComp(SurveysEntity.class);
    }

    /**
     * Constructor.
     */
    public SurveysEntity() {
        super();
    }

    /**
     * Constructor
     * @param knowledgeId ナレッジID
     */

    public SurveysEntity(Long knowledgeId) {
        super( knowledgeId);
    }

}
