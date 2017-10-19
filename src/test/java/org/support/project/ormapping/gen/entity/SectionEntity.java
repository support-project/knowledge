package org.support.project.ormapping.gen.entity;

import java.sql.Timestamp;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.ormapping.gen.entity.gen.GenSectionEntity;

/**
 * 組織
 */
@DI(instance = Instance.Prototype)
public class SectionEntity extends GenSectionEntity {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * インスタンス取得 AOPに対応
     * 
     * @return インスタンス
     */
    public static SectionEntity get() {
        return Container.getComp(SectionEntity.class);
    }

    /**
     * コンストラクタ
     */
    public SectionEntity() {
        super();
    }

    /**
     * コンストラクタ
     * 
     * @param sectionCode
     *            組織コード
     */

    public SectionEntity(String sectionCode) {
        super(sectionCode);
    }

}
