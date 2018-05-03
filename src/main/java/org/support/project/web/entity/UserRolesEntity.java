package org.support.project.web.entity;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.web.entity.gen.GenUserRolesEntity;

/**
 * ユーザの権限
 */
@DI(instance = Instance.Prototype)
public class UserRolesEntity extends GenUserRolesEntity {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * インスタンス取得 AOPに対応
     * 
     * @return インスタンス
     */
    public static UserRolesEntity get() {
        return Container.getComp(UserRolesEntity.class);
    }

    /**
     * コンストラクタ
     */
    public UserRolesEntity() {
        super();
    }

    /**
     * コンストラクタ
     * 
     * @param roleId 権限ID
     * @param userId ユーザID
     */

    public UserRolesEntity(Integer roleId, Integer userId) {
        super(roleId, userId);
    }

}
