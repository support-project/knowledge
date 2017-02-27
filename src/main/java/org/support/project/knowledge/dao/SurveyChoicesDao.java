package org.support.project.knowledge.dao;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;

import org.support.project.knowledge.dao.gen.GenSurveyChoicesDao;

/**
 * アンケートの選択肢の値
 */
@DI(instance = Instance.Singleton)
public class SurveyChoicesDao extends GenSurveyChoicesDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;
    /**
     * Get instance from DI container.
     * @return instance
     */
    public static SurveyChoicesDao get() {
        return Container.getComp(SurveyChoicesDao.class);
    }



}
