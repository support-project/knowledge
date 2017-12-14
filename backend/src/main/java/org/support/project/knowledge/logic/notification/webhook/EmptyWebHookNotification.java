package org.support.project.knowledge.logic.notification.webhook;

import org.support.project.knowledge.entity.WebhookConfigsEntity;
import org.support.project.knowledge.entity.WebhooksEntity;

public class EmptyWebHookNotification extends AbstractWebHookNotification {

    @Override
    protected String getHook() {
        return "";
    }

    @Override
    protected String createWebhookJson() {
        return null;
    }

    @Override
    public String loadTemplate(WebhookConfigsEntity configEntity) throws Exception {
        return "";
    }

    @Override
    public String createSendJson(WebhooksEntity entity, WebhookConfigsEntity configEntity) throws Exception {
        return entity.getContent();
    }

}
