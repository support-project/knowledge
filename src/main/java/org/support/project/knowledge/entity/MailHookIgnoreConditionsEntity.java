package org.support.project.knowledge.entity;

import org.support.project.knowledge.entity.gen.GenMailHookIgnoreConditionsEntity;

import java.util.List;
import java.util.Map;

import org.support.project.common.bean.ValidateError;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;

import java.sql.Timestamp;


/**
 * メールから投稿の際の除外条件
 */
@DI(instance = Instance.Prototype)
public class MailHookIgnoreConditionsEntity extends GenMailHookIgnoreConditionsEntity {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * Get instance from DI container.
     * @return instance
     */
    public static MailHookIgnoreConditionsEntity get() {
        return Container.getComp(MailHookIgnoreConditionsEntity.class);
    }

    /**
     * Constructor.
     */
    public MailHookIgnoreConditionsEntity() {
        super();
    }

    /**
     * Constructor
     * @param conditionNo CONDITION_NO
     * @param hookId HOOK_ID
     * @param ignoreConditionNo IGNORE_CONDITION_NO
     */

    public MailHookIgnoreConditionsEntity(Integer conditionNo, Integer hookId, Integer ignoreConditionNo) {
        super( conditionNo,  hookId,  ignoreConditionNo);
    }

}
