package org.support.project.web.entity;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.web.entity.gen.GenFunctionsEntity;

/**
 * 機能
 */
@DI(instance = Instance.Prototype)
public class FunctionsEntity extends GenFunctionsEntity {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * インスタンス取得 AOPに対応
     * 
     * @return インスタンス
     */
    public static FunctionsEntity get() {
        return Container.getComp(FunctionsEntity.class);
    }

    /**
     * コンストラクタ
     */
    public FunctionsEntity() {
        super();
    }

    /**
     * コンストラクタ
     * 
     * @param functionKey 機能
     */

    public FunctionsEntity(String functionKey) {
        super(functionKey);
    }

}
