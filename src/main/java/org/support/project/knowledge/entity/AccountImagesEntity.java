package org.support.project.knowledge.entity;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.entity.gen.GenAccountImagesEntity;

/**
 * アカウントの画像
 */
@DI(instance = Instance.Prototype)
public class AccountImagesEntity extends GenAccountImagesEntity {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * インスタンス取得 AOPに対応
     * 
     * @return インスタンス
     */
    public static AccountImagesEntity get() {
        return Container.getComp(AccountImagesEntity.class);
    }

    /**
     * コンストラクタ
     */
    public AccountImagesEntity() {
        super();
    }

    /**
     * コンストラクタ
     * 
     * @param imageId IMAGE_ID
     */

    public AccountImagesEntity(Long imageId) {
        super(imageId);
    }

}
