package org.support.project.knowledge.entity;

import org.support.project.knowledge.entity.gen.GenBadgesEntity;

import java.util.List;
import java.util.Map;

import org.support.project.common.bean.ValidateError;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;

import java.sql.Timestamp;


/**
 * 称号
 */
@DI(instance = Instance.Prototype)
public class BadgesEntity extends GenBadgesEntity {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * Get instance from DI container.
     * @return instance
     */
    public static BadgesEntity get() {
        return Container.getComp(BadgesEntity.class);
    }

    /**
     * Constructor.
     */
    public BadgesEntity() {
        super();
    }

    /**
     * Constructor
     * @param no 番号
     */

    public BadgesEntity(Integer no) {
        super( no);
    }

}
