package org.support.project.ormapping.gen.entity;

import java.sql.Blob;
import java.sql.Timestamp;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.ormapping.gen.entity.gen.GenBlobTableEntity;

/**
 * BLOBのテストテーブル
 */
@DI(instance = Instance.Prototype)
public class BlobTableEntity extends GenBlobTableEntity {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * インスタンス取得 AOPに対応
     * 
     * @return インスタンス
     */
    public static BlobTableEntity get() {
        return Container.getComp(BlobTableEntity.class);
    }

    /**
     * コンストラクタ
     */
    public BlobTableEntity() {
        super();
    }

    /**
     * コンストラクタ
     * 
     * @param no
     *            番号
     */

    public BlobTableEntity(Long no) {
        super(no);
    }

}
