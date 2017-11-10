package org.support.project.knowledge.logic.notification.webhook;

import java.util.List;

import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.dao.WebhookConfigsDao;
import org.support.project.knowledge.dao.WebhooksDao;
import org.support.project.knowledge.entity.WebhookConfigsEntity;
import org.support.project.knowledge.entity.WebhooksEntity;
import org.support.project.knowledge.logic.MailLogic;
import org.support.project.knowledge.logic.WebhookLogic;

@DI(instance = Instance.Prototype)
public abstract class WebHookNotification {
    /** Hookの種類を取得 */
    protected abstract String getHook();
    /** WebHookで送信するJSONを取得 */
    protected abstract String createJson();
    
    public void addWebhook() {
        WebhookConfigsDao webhookConfigsDao = WebhookConfigsDao.get();
        List<WebhookConfigsEntity> webhookConfigsEntities = webhookConfigsDao.selectOnHook(getHook());

        if (0 == webhookConfigsEntities.size()) {
            return;
        }
        String json = createJson();
        WebhooksEntity webhooksEntity = new WebhooksEntity();
        String webhookId = MailLogic.get().idGen("Notify");
        webhooksEntity.setWebhookId(webhookId);
        webhooksEntity.setStatus(WebhookLogic.WEBHOOK_STATUS_UNSENT);
        webhooksEntity.setHook(WebhookConfigsEntity.HOOK_KNOWLEDGES);
        webhooksEntity.setContent(json);
        WebhooksDao.get().insert(webhooksEntity);
    }
    
}
