package org.support.project.knowledge.entity;

import org.support.project.knowledge.entity.gen.GenSurveyItemAnswersEntity;

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
public class SurveyItemAnswersEntity extends GenSurveyItemAnswersEntity {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * Get instance from DI container.
     * @return instance
     */
    public static SurveyItemAnswersEntity get() {
        return Container.getComp(SurveyItemAnswersEntity.class);
    }

    /**
     * Constructor.
     */
    public SurveyItemAnswersEntity() {
        super();
    }

    /**
     * Constructor
     * @param answerId 回答ID
     * @param itemNo 項目NO
     * @param knowledgeId ナレッジID
     */

    public SurveyItemAnswersEntity(Integer answerId, Integer itemNo, Long knowledgeId) {
        super( answerId,  itemNo,  knowledgeId);
    }

}
