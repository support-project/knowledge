package org.support.project.knowledge.entity;

import org.support.project.knowledge.entity.gen.GenSurveyChoicesEntity;

import java.util.List;
import java.util.Map;

import org.support.project.common.bean.ValidateError;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;

import java.sql.Timestamp;


/**
 * アンケートの選択肢の値
 */
@DI(instance = Instance.Prototype)
public class SurveyChoicesEntity extends GenSurveyChoicesEntity {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * Get instance from DI container.
     * @return instance
     */
    public static SurveyChoicesEntity get() {
        return Container.getComp(SurveyChoicesEntity.class);
    }

    /**
     * Constructor.
     */
    public SurveyChoicesEntity() {
        super();
    }

    /**
     * Constructor
     * @param choiceNo 選択肢番号
     * @param itemNo 項目NO
     * @param knowledgeId ナレッジID
     */

    public SurveyChoicesEntity(Integer choiceNo, Integer itemNo, Long knowledgeId) {
        super( choiceNo,  itemNo,  knowledgeId);
    }

}
