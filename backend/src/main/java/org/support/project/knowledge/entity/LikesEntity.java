package org.support.project.knowledge.entity;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.entity.gen.GenLikesEntity;

/**
 * いいね
 */
@DI(instance = Instance.Prototype)
public class LikesEntity extends GenLikesEntity {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    private String userName;

    /**
     * インスタンス取得 AOPに対応
     * 
     * @return インスタンス
     */
    public static LikesEntity get() {
        return Container.getComp(LikesEntity.class);
    }

    /**
     * コンストラクタ
     */
    public LikesEntity() {
        super();
    }

    /**
     * コンストラクタ
     * 
     * @param no NO
     */

    public LikesEntity(Long no) {
        super(no);
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
