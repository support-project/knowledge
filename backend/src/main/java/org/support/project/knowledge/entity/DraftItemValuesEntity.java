package org.support.project.knowledge.entity;

import org.support.project.knowledge.entity.gen.GenDraftItemValuesEntity;

import java.util.List;
import java.util.Map;

import org.support.project.common.bean.ValidateError;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;

import java.sql.Timestamp;


/**
 * ナレッジの項目値
 */
@DI(instance = Instance.Prototype)
public class DraftItemValuesEntity extends GenDraftItemValuesEntity {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * Get instance from DI container.
     * @return instance
     */
    public static DraftItemValuesEntity get() {
        return Container.getComp(DraftItemValuesEntity.class);
    }

    /**
     * Constructor.
     */
    public DraftItemValuesEntity() {
        super();
    }

    /**
     * Constructor
     * @param draftId 下書きID
     * @param itemNo 項目NO
     * @param typeId テンプレートの種類ID
     */

    public DraftItemValuesEntity(Long draftId, Integer itemNo, Integer typeId) {
        super( draftId,  itemNo,  typeId);
    }

}
