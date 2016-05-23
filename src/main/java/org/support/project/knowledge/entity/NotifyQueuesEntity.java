package org.support.project.knowledge.entity;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.entity.gen.GenNotifyQueuesEntity;

/**
 * 通知待ちキュー
 */
@DI(instance = Instance.Prototype)
public class NotifyQueuesEntity extends GenNotifyQueuesEntity {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * インスタンス取得 AOPに対応
     * 
     * @return インスタンス
     */
    public static NotifyQueuesEntity get() {
        return Container.getComp(NotifyQueuesEntity.class);
    }

    /**
     * コンストラクタ
     */
    public NotifyQueuesEntity() {
        super();
    }

    /**
     * コンストラクタ
     * 
     * @param hash HASH
     */

    public NotifyQueuesEntity(String hash) {
        super(hash);
    }

}
