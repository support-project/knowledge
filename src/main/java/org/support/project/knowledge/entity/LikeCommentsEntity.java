package org.support.project.knowledge.entity;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.entity.gen.GenLikeCommentsEntity;


/**
 * コメントのイイネ
 */
@DI(instance = Instance.Prototype)
public class LikeCommentsEntity extends GenLikeCommentsEntity {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    private String userName;

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
