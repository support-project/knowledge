package org.support.project.ormapping.gen.entity;

import java.sql.Timestamp;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.ormapping.gen.entity.gen.GenGroupUserRelationshipEntity;

/**
 * 所属
 */
@DI(instance = Instance.Prototype)
public class GroupUserRelationshipEntity extends GenGroupUserRelationshipEntity {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * インスタンス取得 AOPに対応
     * 
     * @return インスタンス
     */
    public static GroupUserRelationshipEntity get() {
        return Container.getComp(GroupUserRelationshipEntity.class);
    }

    /**
     * コンストラクタ
     */
    public GroupUserRelationshipEntity() {
        super();
    }

    /**
     * コンストラクタ
     * 
     * @param employeeId
     *            従業員ID
     * @param sectionCode
     *            組織コード
     */

    public GroupUserRelationshipEntity(String employeeId, String sectionCode) {
        super(employeeId, sectionCode);
    }

}
