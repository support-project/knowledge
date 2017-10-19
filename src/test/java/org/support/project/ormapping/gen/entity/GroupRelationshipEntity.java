package org.support.project.ormapping.gen.entity;

import java.sql.Timestamp;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.ormapping.gen.entity.gen.GenGroupRelationshipEntity;

/**
 * グループの親子関係
 */
@DI(instance = Instance.Prototype)
public class GroupRelationshipEntity extends GenGroupRelationshipEntity {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * インスタンス取得 AOPに対応
     * 
     * @return インスタンス
     */
    public static GroupRelationshipEntity get() {
        return Container.getComp(GroupRelationshipEntity.class);
    }

    /**
     * コンストラクタ
     */
    public GroupRelationshipEntity() {
        super();
    }

    /**
     * コンストラクタ
     * 
     * @param childSectionCode
     *            子組織コード
     * @param parentSectionCode
     *            親組織コード
     */

    public GroupRelationshipEntity(String childSectionCode, String parentSectionCode) {
        super(childSectionCode, parentSectionCode);
    }

}
