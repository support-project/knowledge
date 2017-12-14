package org.support.project.knowledge.entity;

import java.util.ArrayList;
import java.util.List;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.entity.gen.GenSurveyItemsEntity;


/**
 * アンケート項目
 */
@DI(instance = Instance.Prototype)
public class SurveyItemsEntity extends GenSurveyItemsEntity {

    private List<SurveyChoicesEntity> choices = new ArrayList<>();
    
    /** 項目値 */
    private String itemValue;
    
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

    /**
     * @return the choices
     */
    public List<SurveyChoicesEntity> getChoices() {
        return choices;
    }

    /**
     * @param choices the choices to set
     */
    public void setChoices(List<SurveyChoicesEntity> choices) {
        this.choices = choices;
    }

    /**
     * @return the itemValue
     */
    public String getItemValue() {
        return itemValue;
    }

    /**
     * @param itemValue the itemValue to set
     */
    public void setItemValue(String itemValue) {
        this.itemValue = itemValue;
    }

}
