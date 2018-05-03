package org.support.project.web.entity;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.web.entity.gen.GenLoginHistoriesEntity;

/**
 * ログイン履歴
 */
@DI(instance = Instance.Prototype)
public class LoginHistoriesEntity extends GenLoginHistoriesEntity {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * インスタンス取得 AOPに対応
     * 
     * @return インスタンス
     */
    public static LoginHistoriesEntity get() {
        return Container.getComp(LoginHistoriesEntity.class);
    }

    /**
     * コンストラクタ
     */
    public LoginHistoriesEntity() {
        super();
    }

    /**
     * コンストラクタ
     * 
     * @param loginCount ログイン番号
     * @param userId ユーザID
     */

    public LoginHistoriesEntity(Double loginCount, Integer userId) {
        super(loginCount, userId);
    }

}
