package org.support.project.knowledge.entity;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.entity.gen.GenMailTemplatesEntity;


/**
 * メールテンプレート
 */
@DI(instance = Instance.Prototype)
public class MailTemplatesEntity extends GenMailTemplatesEntity {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;
    
    private MailLocaleTemplatesEntity en;
    private MailLocaleTemplatesEntity ja;
    
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

    /**
     * @return the en
     */
    public MailLocaleTemplatesEntity getEn() {
        return en;
    }

    /**
     * @param en the en to set
     */
    public void setEn(MailLocaleTemplatesEntity en) {
        this.en = en;
    }

    /**
     * @return the ja
     */
    public MailLocaleTemplatesEntity getJa() {
        return ja;
    }

    /**
     * @param ja the ja to set
     */
    public void setJa(MailLocaleTemplatesEntity ja) {
        this.ja = ja;
    }

}
