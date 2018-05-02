package org.support.project.web.entity;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.web.entity.gen.GenProvisionalRegistrationsEntity;

/**
 * 仮登録ユーザ
 */
@DI(instance = Instance.Prototype)
public class ProvisionalRegistrationsEntity extends GenProvisionalRegistrationsEntity {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /** 既に暗号化済かどうか */
    private Boolean encrypted = Boolean.FALSE;

    /**
     * インスタンス取得 AOPに対応
     * 
     * @return インスタンス
     */
    public static ProvisionalRegistrationsEntity get() {
        return Container.getComp(ProvisionalRegistrationsEntity.class);
    }

    /**
     * コンストラクタ
     */
    public ProvisionalRegistrationsEntity() {
        super();
    }

    /**
     * コンストラクタ
     * 
     * @param id 仮発行ID
     */

    public ProvisionalRegistrationsEntity(String id) {
        super(id);
    }

    public Boolean getEncrypted() {
        return encrypted;
    }

    public void setEncrypted(Boolean encrypted) {
        this.encrypted = encrypted;
    }

}
