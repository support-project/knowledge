package org.support.project.ormapping.gen.entity;

import java.sql.Timestamp;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.ormapping.gen.entity.gen.GenUserRoleRelationshipEntity;

/**
 * 組織以外の役割の紐付
 */
@DI(instance = Instance.Prototype)
public class UserRoleRelationshipEntity extends GenUserRoleRelationshipEntity {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * インスタンス取得 AOPに対応
     * 
     * @return インスタンス
     */
    public static UserRoleRelationshipEntity get() {
        return Container.getComp(UserRoleRelationshipEntity.class);
    }

    /**
     * コンストラクタ
     */
    public UserRoleRelationshipEntity() {
        super();
    }

    /**
     * コンストラクタ
     * 
     * @param employeeId
     *            従業員ID
     * @param roleId
     *            役割ID
     */

    public UserRoleRelationshipEntity(String employeeId, String roleId) {
        super(employeeId, roleId);
    }

}
