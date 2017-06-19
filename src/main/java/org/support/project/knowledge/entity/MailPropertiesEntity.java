package org.support.project.knowledge.entity;

import org.support.project.knowledge.entity.gen.GenMailPropertiesEntity;

import java.util.List;
import java.util.Map;

import org.support.project.common.bean.ValidateError;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;

import java.sql.Timestamp;


/**
 * メール受信設定
 */
@DI(instance = Instance.Prototype)
public class MailPropertiesEntity extends GenMailPropertiesEntity {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * Get instance from DI container.
     * @return instance
     */
    public static MailPropertiesEntity get() {
        return Container.getComp(MailPropertiesEntity.class);
    }

    /**
     * Constructor.
     */
    public MailPropertiesEntity() {
        super();
    }

    /**
     * Constructor
     * @param hookId HOOK_ID
     * @param propertyKey PROPERTY_KEY
     */

    public MailPropertiesEntity(Integer hookId, String propertyKey) {
        super( hookId,  propertyKey);
    }

}
