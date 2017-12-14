package org.support.project.knowledge.vo;

import java.io.Serializable;
import java.util.List;

import org.support.project.knowledge.entity.SurveyAnswersEntity;
import org.support.project.knowledge.entity.SurveysEntity;

public class SurveyReport implements Serializable {
    /** シリアルバージョン */
    private static final long serialVersionUID = 1L;
    /** アンケート情報 */
    private SurveysEntity survey;
    /** 回答 */
    private List<SurveyAnswersEntity> answers;
    /**
     * @return the survey
     */
    public SurveysEntity getSurvey() {
        return survey;
    }
    /**
     * @param survey the survey to set
     */
    public void setSurvey(SurveysEntity survey) {
        this.survey = survey;
    }
    /**
     * @return the answers
     */
    public List<SurveyAnswersEntity> getAnswers() {
        return answers;
    }
    /**
     * @param answers the answers to set
     */
    public void setAnswers(List<SurveyAnswersEntity> answers) {
        this.answers = answers;
    }
    
}
