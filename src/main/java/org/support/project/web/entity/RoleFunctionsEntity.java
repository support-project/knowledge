package org.support.project.web.entity;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.web.entity.gen.GenRoleFunctionsEntity;

/**
 * 機能にアクセスできる権限
 */
@DI(instance = Instance.Prototype)
public class RoleFunctionsEntity extends GenRoleFunctionsEntity {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * インスタンス取得 AOPに対応
     * 
     * @return インスタンス
     */
    public static RoleFunctionsEntity get() {
        return Container.getComp(RoleFunctionsEntity.class);
    }

    /**
     * コンストラクタ
     */
    public RoleFunctionsEntity() {
        super();
    }

    /**
     * コンストラクタ
     * 
     * @param functionKey 機能
     * @param roleId 権限ID
     */

    public RoleFunctionsEntity(String functionKey, Integer roleId) {
        super(functionKey, roleId);
    }

}
