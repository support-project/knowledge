package org.support.project.web.entity;

import org.support.project.web.entity.gen.GenUserAliasEntity;

import java.util.List;
import java.util.Map;

import org.support.project.common.bean.ValidateError;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;

import java.sql.Timestamp;


/**
 * ユーザのエイリアス
 */
@DI(instance = Instance.Prototype)
public class UserAliasEntity extends GenUserAliasEntity {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * Get instance from DI container.
     * @return instance
     */
    public static UserAliasEntity get() {
        return Container.getComp(UserAliasEntity.class);
    }

    /**
     * Constructor.
     */
    public UserAliasEntity() {
        super();
    }

    /**
     * Constructor
     * @param authKey 認証設定キー
     * @param userId ユーザID
     */

    public UserAliasEntity(String authKey, Integer userId) {
        super( authKey,  userId);
    }

}
