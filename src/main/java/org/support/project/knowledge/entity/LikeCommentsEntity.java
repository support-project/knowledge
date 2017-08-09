package org.support.project.knowledge.entity;

import org.support.project.knowledge.entity.gen.GenLikeCommentsEntity;

import java.util.List;
import java.util.Map;

import org.support.project.common.bean.ValidateError;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;

import java.sql.Timestamp;


/**
 * コメントのイイネ
 */
@DI(instance = Instance.Prototype)
public class LikeCommentsEntity extends GenLikeCommentsEntity {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * Get instance from DI container.
     * @return instance
     */
    public static LikeCommentsEntity get() {
        return Container.getComp(LikeCommentsEntity.class);
    }

    /**
     * Constructor.
     */
    public LikeCommentsEntity() {
        super();
    }

    /**
     * Constructor
     * @param no NO
     */

    public LikeCommentsEntity(Long no) {
        super( no);
    }

}
