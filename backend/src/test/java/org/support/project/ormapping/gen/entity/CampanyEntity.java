package org.support.project.ormapping.gen.entity;

import java.sql.Timestamp;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.ormapping.gen.entity.gen.GenCampanyEntity;

/**
 * 会社
 */
@DI(instance = Instance.Prototype)
public class CampanyEntity extends GenCampanyEntity {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * インスタンス取得 AOPに対応
     * 
     * @return インスタンス
     */
    public static CampanyEntity get() {
        return Container.getComp(CampanyEntity.class);
    }

    /**
     * コンストラクタ
     */
    public CampanyEntity() {
        super();
    }

    /**
     * コンストラクタ
     * 
     * @param campanyCode
     *            会社コード
     */

    public CampanyEntity(String campanyCode) {
        super(campanyCode);
    }

}
