package org.support.project.web.entity;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.web.entity.gen.GenUserConfigsEntity;

/**
 * ユーザ設定
 */
@DI(instance = Instance.Prototype)
public class UserConfigsEntity extends GenUserConfigsEntity {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * インスタンス取得 AOPに対応
     * 
     * @return インスタンス
     */
    public static UserConfigsEntity get() {
        return Container.getComp(UserConfigsEntity.class);
    }

    /**
     * コンストラクタ
     */
    public UserConfigsEntity() {
        super();
    }

    /**
     * コンストラクタ
     * 
     * @param configName コンフィグ名
     * @param systemName システム名
     * @param userId ユーザID
     */

    public UserConfigsEntity(String configName, String systemName, Integer userId) {
        super(configName, systemName, userId);
    }

}
