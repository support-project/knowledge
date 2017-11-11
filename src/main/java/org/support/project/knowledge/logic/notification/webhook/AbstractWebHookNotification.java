package org.support.project.knowledge.logic.notification.webhook;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.support.project.common.exception.SystemException;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.PropertyUtil;
import org.support.project.common.util.StringUtils;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.dao.WebhookConfigsDao;
import org.support.project.knowledge.dao.WebhooksDao;
import org.support.project.knowledge.entity.WebhookConfigsEntity;
import org.support.project.knowledge.entity.WebhooksEntity;
import org.support.project.knowledge.logic.MailLogic;
import org.support.project.knowledge.logic.WebhookLogic;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

@DI(instance = Instance.Prototype)
public abstract class AbstractWebHookNotification {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(AbstractWebHookNotification.class);
    
    protected boolean inited = false;
    
    /**
     * Hookの種類を取得
     * @return
     */
    protected abstract String getHook();
    /**
     * Webhook通知情報に格納するJSONを生成する
     * 送信先に合わせて送るJSONの型を変更するため、初めはキーの値のみをJSONを保持する
     * @return
     */
    protected abstract String createWebhookJson();

    /**
     * Webhookで送信するJSONを生成する
     * 送信先毎に送る型を変化させる
     * @param entity
     * @param configEntity
     * @throws Exception
     */
    public abstract String createSendJson(WebhooksEntity entity, WebhookConfigsEntity configEntity) throws Exception;
    
    /**
     * Webhookの登録を行う
     * @param comment
     * @param knowledge
     */
    public void saveWebhookData() {
        if (!inited) {
            throw new SystemException("send method must finished init method.");
        }
        
        WebhookConfigsDao webhookConfigsDao = WebhookConfigsDao.get();
        List<WebhookConfigsEntity> webhookConfigsEntities = webhookConfigsDao.selectOnHook(WebhookConfigsEntity.HOOK_KNOWLEDGES);
        if (0 == webhookConfigsEntities.size()) {
            return;
        }
        String json = createWebhookJson();
        WebhooksEntity webhooksEntity = new WebhooksEntity();
        String webhookId = MailLogic.get().idGen("Notify");
        webhooksEntity.setWebhookId(webhookId);
        webhooksEntity.setStatus(WebhookLogic.WEBHOOK_STATUS_UNSENT);
        webhooksEntity.setHook(getHook());
        webhooksEntity.setContent(json);

        if (LOG.isTraceEnabled()) {
            LOG.trace(PropertyUtil.reflectionToString(webhooksEntity));
        }
        WebhooksDao.get().insert(webhooksEntity);
    }
    
    
    

    /**
     * 指定のオブジェクトのプロパティ値でJsonの値を変換
     * @param obj
     * @param prop
     * @param option
     * @return
     */
    public JsonElement convValue(Object obj, String prop, String option) {
        if (prop.equals("insertUserName") || prop.equals("updateUserName") || prop.equals("userName")) {
            String v = (String) PropertyUtil.getPropertyValue(obj, prop);
            if (v == null) {
                v = "Unknown user";
            }
            return new JsonPrimitive(v);
        }
        Object v = PropertyUtil.getPropertyValue(obj, prop);
        if (v instanceof String) {
            int maxlength = -1;
            if (option.startsWith("maxlength=")) {
                String optionValue = option.substring("maxlength=".length());
                if (StringUtils.isInteger(optionValue)) {
                    maxlength = Integer.parseInt(optionValue);
                }
            }
            String str = (String) v;
            if (maxlength > 0) {
                str = StringUtils.abbreviate(str, maxlength);
            }
            return new JsonPrimitive((String) str);
        } else if (v instanceof Number) {
            return new JsonPrimitive((Number) v);
        } else if (v instanceof Boolean) {
            return new JsonPrimitive((Boolean) v);
        } else if (v instanceof Date) {
            String format = "yyyy-MM-dd HH:mm:ss.SSSZ";
            if (option.startsWith("format=")) {
                format = option.substring("format=".length());
                LOG.debug(format);
            }
            String str = new SimpleDateFormat(format).format(v);
            return new JsonPrimitive(str);
        }
        return null;
    }
}
