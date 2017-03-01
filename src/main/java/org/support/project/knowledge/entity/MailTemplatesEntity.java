package org.support.project.knowledge.entity;

import org.support.project.knowledge.entity.gen.GenMailTemplatesEntity;

import java.util.List;
import java.util.Map;

import org.support.project.common.bean.ValidateError;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;

import java.sql.Timestamp;


/**
 * メールテンプレート
 */
@DI(instance = Instance.Prototype)
public class MailTemplatesEntity extends GenMailTemplatesEntity {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * Get instance from DI container.
     * @return instance
     */
    public static MailTemplatesEntity get() {
        return Container.getComp(MailTemplatesEntity.class);
    }

    /**
     * Constructor.
     */
    public MailTemplatesEntity() {
        super();
    }

    /**
     * Constructor
     * @param templateId テンプレートID
     */

    public MailTemplatesEntity(String templateId) {
        super( templateId);
    }

}
