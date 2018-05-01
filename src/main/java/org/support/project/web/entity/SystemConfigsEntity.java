package org.support.project.web.entity;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.web.entity.gen.GenSystemConfigsEntity;

/**
 * コンフィグ
 */
@DI(instance = Instance.Prototype)
public class SystemConfigsEntity extends GenSystemConfigsEntity {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * インスタンス取得 AOPに対応
     * 
     * @return インスタンス
     */
    public static SystemConfigsEntity get() {
        return Container.getComp(SystemConfigsEntity.class);
    }

    /**
     * コンストラクタ
     */
    public SystemConfigsEntity() {
        super();
    }

    /**
     * コンストラクタ
     * 
     * @param configName コンフィグ名
     * @param systemName システム名
     */

    public SystemConfigsEntity(String configName, String systemName) {
        super(configName, systemName);
    }

}
