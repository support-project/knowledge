package org.support.project.web.entity;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.web.entity.gen.GenConfirmMailChangesEntity;

/**
 * メールアドレス変更確認
 */
@DI(instance = Instance.Prototype)
public class ConfirmMailChangesEntity extends GenConfirmMailChangesEntity {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * インスタンス取得 AOPに対応
     * 
     * @return インスタンス
     */
    public static ConfirmMailChangesEntity get() {
        return Container.getComp(ConfirmMailChangesEntity.class);
    }

    /**
     * コンストラクタ
     */
    public ConfirmMailChangesEntity() {
        super();
    }

    /**
     * コンストラクタ
     * 
     * @param id リセット用ID
     */

    public ConfirmMailChangesEntity(String id) {
        super(id);
    }

}
