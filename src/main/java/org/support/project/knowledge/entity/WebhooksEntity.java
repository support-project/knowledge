package org.support.project.knowledge.entity;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.entity.gen.GenWebhooksEntity;

/**
 * Webhooks
 */
@DI(instance = Instance.Prototype)
public class WebhooksEntity extends GenWebhooksEntity {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * インスタンス取得 AOPに対応
     * 
     * @return インスタンス
     */
    public static WebhooksEntity get() {
        return Container.getComp(WebhooksEntity.class);
    }

    /**
     * コンストラクタ
     */
    public WebhooksEntity() {
        super();
    }

    /**
     * コンストラクタ
     * 
     * @param webhookId WEBHOOK ID
     */

    public WebhooksEntity(String webhookId) {
        super(webhookId);
    }

}
