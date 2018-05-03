package org.support.project.web.entity;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.web.entity.gen.GenHashConfigsEntity;

/**
 * ハッシュ生成の設定
 */
@DI(instance = Instance.Prototype)
public class HashConfigsEntity extends GenHashConfigsEntity {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * インスタンス取得 AOPに対応
     * 
     * @return インスタンス
     */
    public static HashConfigsEntity get() {
        return Container.getComp(HashConfigsEntity.class);
    }

    /**
     * コンストラクタ
     */
    public HashConfigsEntity() {
        super();
    }

    /**
     * コンストラクタ
     * 
     * @param systemName システム名
     */

    public HashConfigsEntity(String systemName) {
        super(systemName);
    }

}
