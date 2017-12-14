package org.support.project.knowledge.entity;

import org.support.project.knowledge.entity.gen.GenPinsEntity;
import java.util.List;
import java.util.Map;
import org.support.project.common.bean.ValidateError;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import java.sql.Timestamp;

/**
 * ピン
 */
@DI(instance = Instance.Prototype)
public class PinsEntity extends GenPinsEntity {
    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * インスタンス取得
     * AOPに対応
     * @return インスタンス
     */
    public static PinsEntity get() {
        return Container.getComp(PinsEntity.class);
    }

    /**
     * コンストラクタ
     */
    public PinsEntity() {
        super();
    }

    /**
     * コンストラクタ
     * @param no NO
     */
    public PinsEntity(Integer no) {
        super( no);
    }
}