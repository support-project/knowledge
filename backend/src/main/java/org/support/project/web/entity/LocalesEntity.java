package org.support.project.web.entity;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.web.entity.gen.GenLocalesEntity;

/**
 * ロケール
 */
@DI(instance = Instance.Prototype)
public class LocalesEntity extends GenLocalesEntity {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * インスタンス取得 AOPに対応
     * 
     * @return インスタンス
     */
    public static LocalesEntity get() {
        return Container.getComp(LocalesEntity.class);
    }

    /**
     * コンストラクタ
     */
    public LocalesEntity() {
        super();
    }

    /**
     * コンストラクタ
     * 
     * @param key キー
     */

    public LocalesEntity(String key) {
        super(key);
    }

}
