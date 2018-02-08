package org.support.project.web.entity;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.web.entity.gen.GenProxyConfigsEntity;

/**
 * プロキシ設定
 */
@DI(instance = Instance.Prototype)
public class ProxyConfigsEntity extends GenProxyConfigsEntity {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * インスタンス取得 AOPに対応
     * 
     * @return インスタンス
     */
    public static ProxyConfigsEntity get() {
        return Container.getComp(ProxyConfigsEntity.class);
    }

    /**
     * コンストラクタ
     */
    public ProxyConfigsEntity() {
        super();
    }

    /**
     * コンストラクタ
     * 
     * @param systemName システム名
     */

    public ProxyConfigsEntity(String systemName) {
        super(systemName);
    }

}
