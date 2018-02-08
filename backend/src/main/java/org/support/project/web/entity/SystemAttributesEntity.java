package org.support.project.web.entity;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.web.entity.gen.GenSystemAttributesEntity;

/**
 * システム付加情報
 */
@DI(instance = Instance.Prototype)
public class SystemAttributesEntity extends GenSystemAttributesEntity {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * インスタンス取得 AOPに対応
     * 
     * @return インスタンス
     */
    public static SystemAttributesEntity get() {
        return Container.getComp(SystemAttributesEntity.class);
    }

    /**
     * コンストラクタ
     */
    public SystemAttributesEntity() {
        super();
    }

    /**
     * コンストラクタ
     * 
     * @param configName コンフィグ名
     * @param systemName システム名
     */

    public SystemAttributesEntity(String configName, String systemName) {
        super(configName, systemName);
    }

}
