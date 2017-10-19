package org.support.project.web.entity;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.web.entity.gen.GenPasswordResetsEntity;

/**
 * パスワードリセット
 */
@DI(instance = Instance.Prototype)
public class PasswordResetsEntity extends GenPasswordResetsEntity {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * インスタンス取得 AOPに対応
     * 
     * @return インスタンス
     */
    public static PasswordResetsEntity get() {
        return Container.getComp(PasswordResetsEntity.class);
    }

    /**
     * コンストラクタ
     */
    public PasswordResetsEntity() {
        super();
    }

    /**
     * コンストラクタ
     * 
     * @param id パスワードリセットID
     */

    public PasswordResetsEntity(String id) {
        super(id);
    }

}
