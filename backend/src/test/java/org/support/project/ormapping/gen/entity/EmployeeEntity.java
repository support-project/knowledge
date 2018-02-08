package org.support.project.ormapping.gen.entity;

import java.sql.Timestamp;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.ormapping.gen.entity.gen.GenEmployeeEntity;

/**
 * 従業員
 */
@DI(instance = Instance.Prototype)
public class EmployeeEntity extends GenEmployeeEntity {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * インスタンス取得 AOPに対応
     * 
     * @return インスタンス
     */
    public static EmployeeEntity get() {
        return Container.getComp(EmployeeEntity.class);
    }

    /**
     * コンストラクタ
     */
    public EmployeeEntity() {
        super();
    }

    /**
     * コンストラクタ
     * 
     * @param employeeId
     *            従業員ID
     */

    public EmployeeEntity(String employeeId) {
        super(employeeId);
    }

}
