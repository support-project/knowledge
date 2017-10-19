package org.support.project.web.entity;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.web.entity.gen.GenUserGroupsEntity;

/**
 * ユーザが所属するグループ
 */
@DI(instance = Instance.Prototype)
public class UserGroupsEntity extends GenUserGroupsEntity {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * インスタンス取得 AOPに対応
     * 
     * @return インスタンス
     */
    public static UserGroupsEntity get() {
        return Container.getComp(UserGroupsEntity.class);
    }

    /**
     * コンストラクタ
     */
    public UserGroupsEntity() {
        super();
    }

    /**
     * コンストラクタ
     * 
     * @param groupId グループID CHARACTER SET latin1
     * @param userId ユーザID
     */

    public UserGroupsEntity(Integer groupId, Integer userId) {
        super(groupId, userId);
    }

}
