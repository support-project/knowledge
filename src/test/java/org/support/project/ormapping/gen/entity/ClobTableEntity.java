package org.support.project.ormapping.gen.entity;

import java.sql.Clob;
import java.sql.Timestamp;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.ormapping.gen.entity.gen.GenClobTableEntity;

/**
 * CLOBのテストテーブル
 */
@DI(instance = Instance.Prototype)
public class ClobTableEntity extends GenClobTableEntity {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * インスタンス取得 AOPに対応
     * 
     * @return インスタンス
     */
    public static ClobTableEntity get() {
        return Container.getComp(ClobTableEntity.class);
    }

    /**
     * コンストラクタ
     */
    public ClobTableEntity() {
        super();
    }

    /**
     * コンストラクタ
     * 
     * @param no
     *            番号
     */

    public ClobTableEntity(Integer no) {
        super(no);
    }

}
