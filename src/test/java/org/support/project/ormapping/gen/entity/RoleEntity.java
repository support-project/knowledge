package org.support.project.ormapping.gen.entity;

import java.sql.Timestamp;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.ormapping.gen.entity.gen.GenRoleEntity;

/**
 * 役割
 */
@DI(instance = Instance.Prototype)
public class RoleEntity extends GenRoleEntity {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * インスタンス取得 AOPに対応
     * 
     * @return インスタンス
     */
    public static RoleEntity get() {
        return Container.getComp(RoleEntity.class);
    }

    /**
     * コンストラクタ
     */
    public RoleEntity() {
        super();
    }

    /**
     * コンストラクタ
     * 
     * @param roleId
     *            役割ID
     */

    public RoleEntity(String roleId) {
        super(roleId);
    }

}
