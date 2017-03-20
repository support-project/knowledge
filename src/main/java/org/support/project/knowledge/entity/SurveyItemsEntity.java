package org.support.project.knowledge.entity;

import org.support.project.knowledge.entity.gen.GenSurveyItemsEntity;

import java.util.List;
import java.util.Map;

import org.support.project.common.bean.ValidateError;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;

import java.sql.Timestamp;


/**
 * アンケート項目
 */
@DI(instance = Instance.Prototype)
public class SurveyItemsEntity extends GenSurveyItemsEntity {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * Get instance from DI container.
     * @return instance
     */
    public static SurveyItemsEntity get() {
        return Container.getComp(SurveyItemsEntity.class);
    }

    /**
     * Constructor.
     */
    public SurveyItemsEntity() {
        super();
    }

    /**
     * Constructor
     * @param itemNo 項目NO
     * @param knowledgeId ナレッジID
     */

    public SurveyItemsEntity(Integer itemNo, Long knowledgeId) {
        super( itemNo,  knowledgeId);
    }

}
