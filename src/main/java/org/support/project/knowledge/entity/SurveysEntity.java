package org.support.project.knowledge.entity;

import java.util.ArrayList;
import java.util.List;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.entity.gen.GenSurveysEntity;


/**
 * アンケート
 */
@DI(instance = Instance.Prototype)
public class SurveysEntity extends GenSurveysEntity {

    private List<SurveyItemsEntity> items = new ArrayList<SurveyItemsEntity>();
    
    private boolean editable;
    
    private String knowledgeTitle;
    
    private boolean exist = false;
    
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

    /**
     * @return the items
     */
    public List<SurveyItemsEntity> getItems() {
        return items;
    }

    /**
     * @param items the items to set
     */
    public void setItems(List<SurveyItemsEntity> items) {
        this.items = items;
    }

    /**
     * @return the editable
     */
    public boolean isEditable() {
        return editable;
    }

    /**
     * @param editable the editable to set
     */
    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    /**
     * @return the knowledgeTitle
     */
    public String getKnowledgeTitle() {
        return knowledgeTitle;
    }

    /**
     * @param knowledgeTitle the knowledgeTitle to set
     */
    public void setKnowledgeTitle(String knowledgeTitle) {
        this.knowledgeTitle = knowledgeTitle;
    }

    /**
     * @return the exist
     */
    public boolean isExist() {
        return exist;
    }

    /**
     * @param exist the exist to set
     */
    public void setExist(boolean exist) {
        this.exist = exist;
    }

}
