package org.support.project.knowledge.entity;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.entity.gen.GenNotifyConfigsEntity;

/**
 * 通知設定
 */
@DI(instance = Instance.Prototype)
public class NotifyConfigsEntity extends GenNotifyConfigsEntity {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * インスタンス取得 AOPに対応
     * 
     * @return インスタンス
     */
    public static NotifyConfigsEntity get() {
        return Container.getComp(NotifyConfigsEntity.class);
    }

    /**
     * コンストラクタ
     */
    public NotifyConfigsEntity() {
        super();
    }

    /**
     * コンストラクタ
     * 
     * @param userId ユーザID
     */

    public NotifyConfigsEntity(Integer userId) {
        super(userId);
    }

}
