package org.support.project.ormapping.gen.entity;

import java.util.List;
import java.util.Map;
import java.sql.Timestamp;

import org.support.project.common.bean.ValidateError;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.ormapping.gen.entity.gen.GenAutoNoEntity;

/**
 * 採番のテストテーブル
 */
@DI(instance = Instance.Prototype)
public class AutoNoEntity extends GenAutoNoEntity {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * インスタンス取得 AOPに対応
     * 
     * @return インスタンス
     */
    public static AutoNoEntity get() {
        return Container.getComp(AutoNoEntity.class);
    }

    /**
     * コンストラクタ
     */
    public AutoNoEntity() {
        super();
    }

    /**
     * コンストラクタ
     * 
     * @param no
     *            番号
     */

    public AutoNoEntity(Long no) {
        super(no);
    }

}
