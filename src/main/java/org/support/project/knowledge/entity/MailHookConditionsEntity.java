package org.support.project.knowledge.entity;

import org.support.project.knowledge.entity.gen.GenMailHookConditionsEntity;

import java.util.List;
import java.util.Map;

import org.support.project.common.bean.ValidateError;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;

import java.sql.Timestamp;


/**
 * メールから投稿する条件
 */
@DI(instance = Instance.Prototype)
public class MailHookConditionsEntity extends GenMailHookConditionsEntity {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * Get instance from DI container.
     * @return instance
     */
    public static MailHookConditionsEntity get() {
        return Container.getComp(MailHookConditionsEntity.class);
    }

    /**
     * Constructor.
     */
    public MailHookConditionsEntity() {
        super();
    }

    /**
     * Constructor
     * @param conditionNo CONDITION_NO
     * @param hookId HOOK_ID
     */

    public MailHookConditionsEntity(Integer conditionNo, Integer hookId) {
        super( conditionNo,  hookId);
    }

}
