package org.support.project.web.entity;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.web.entity.gen.GenSystemsEntity;

/**
 * システムの設定
 */
@DI(instance = Instance.Prototype)
public class SystemsEntity extends GenSystemsEntity {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * インスタンス取得 AOPに対応
     * 
     * @return インスタンス
     */
    public static SystemsEntity get() {
        return Container.getComp(SystemsEntity.class);
    }

    /**
     * コンストラクタ
     */
    public SystemsEntity() {
        super();
    }

    /**
     * コンストラクタ
     * 
     * @param systemName システム名
     */

    public SystemsEntity(String systemName) {
        super(systemName);
    }

}
