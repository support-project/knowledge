package org.support.project.knowledge.entity;

import org.support.project.knowledge.entity.gen.GenSurveyAnswersEntity;

import java.util.List;
import java.util.Map;

import org.support.project.common.bean.ValidateError;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;

import java.sql.Timestamp;


/**
 * アンケートの回答
 */
@DI(instance = Instance.Prototype)
public class SurveyAnswersEntity extends GenSurveyAnswersEntity {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * Get instance from DI container.
     * @return instance
     */
    public static SurveyAnswersEntity get() {
        return Container.getComp(SurveyAnswersEntity.class);
    }

    /**
     * Constructor.
     */
    public SurveyAnswersEntity() {
        super();
    }

    /**
     * Constructor
     * @param answerId 回答ID
     * @param knowledgeId ナレッジID
     */

    public SurveyAnswersEntity(Integer answerId, Long knowledgeId) {
        super( answerId,  knowledgeId);
    }

}
