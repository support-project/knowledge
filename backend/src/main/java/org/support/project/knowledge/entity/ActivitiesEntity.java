package org.support.project.knowledge.entity;

import org.support.project.knowledge.entity.gen.GenActivitiesEntity;

import java.util.List;
import java.util.Map;

import org.support.project.common.bean.ValidateError;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;

import java.sql.Timestamp;


/**
 * アクティビティ
 */
@DI(instance = Instance.Prototype)
public class ActivitiesEntity extends GenActivitiesEntity {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;
    
    private String userName = "";

    /**
     * Get instance from DI container.
     * @return instance
     */
    public static ActivitiesEntity get() {
        return Container.getComp(ActivitiesEntity.class);
    }

    /**
     * Constructor.
     */
    public ActivitiesEntity() {
        super();
    }

    /**
     * Constructor
     * @param no 番号
     */

    public ActivitiesEntity(Long no) {
        super( no);
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

}
