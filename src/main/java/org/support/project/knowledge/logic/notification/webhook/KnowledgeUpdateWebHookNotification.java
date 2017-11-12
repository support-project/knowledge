package org.support.project.knowledge.logic.notification.webhook;

import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.FileUtil;
import org.support.project.common.util.StringUtils;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.dao.KnowledgesDao;
import org.support.project.knowledge.entity.KnowledgesEntity;
import org.support.project.knowledge.entity.WebhookConfigsEntity;
import org.support.project.knowledge.entity.WebhooksEntity;
import org.support.project.knowledge.logic.KnowledgeLogic;
import org.support.project.knowledge.vo.notification.webhook.WebhookKnowledgeJson;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import net.arnx.jsonic.JSON;

/**
 * 記事が投稿された際のWebHook通知
 * 
 * @author koda
 */
@DI(instance = Instance.Prototype)
public class KnowledgeUpdateWebHookNotification extends AbstractWebHookNotification {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(KnowledgeUpdateWebHookNotification.class);

    /** インスタンス取得 */
    public static KnowledgeUpdateWebHookNotification get() {
        return Container.getComp(KnowledgeUpdateWebHookNotification.class);
    }
    
    private KnowledgesEntity knowledge;
    private int type;
    public void init(KnowledgesEntity knowledge, int type) {
        this.knowledge = knowledge;
        this.type = type;
        super.inited = true;
    }
    @Override
    protected String getHook() {
        return WebhookConfigsEntity.HOOK_KNOWLEDGES;
    }
    @Override
    protected String createWebhookJson() {
        LOG.trace("createWebhookJson");
        WebhookKnowledgeJson json = new WebhookKnowledgeJson();
        json.knowledgeId = knowledge.getKnowledgeId();
        json.type = type;
        boolean became_public = false;
        if (knowledge.getNotifyStatus() == null || knowledge.getNotifyStatus().intValue() == 0) {
            if (knowledge.getPublicFlag().intValue() != KnowledgeLogic.PUBLIC_FLAG_PRIVATE) {
                // 今まで非公開だったものが、初めての通知になった
                became_public = true;
            }
        }
        json.became_public = became_public;
        return JSON.encode(json);
    }
    @Override
    public String createSendJson(WebhooksEntity entity, WebhookConfigsEntity configEntity) throws UnsupportedEncodingException, IOException {
        LOG.trace("createSendJson");
        String template = loadTemplate(configEntity);
        WebhookKnowledgeJson json = JSON.decode(entity.getContent(), WebhookKnowledgeJson.class);
        KnowledgesEntity knowledge = KnowledgesDao.get().selectOnKeyWithUserName(json.knowledgeId);
        JsonElement send = new JsonParser().parse(new StringReader(template));
        
        Map<String, Object> map = new HashMap<>();
        map.put("knowledge", knowledge);
        map.put("knowledge.type", json.type);
        map.put("knowledge.became_public", json.became_public);
        buildJson(send.getAsJsonObject(), map);
        return send.toString();
    }
    
    @Override
    public String loadTemplate(WebhookConfigsEntity configEntity) throws UnsupportedEncodingException, IOException {
        String template = configEntity.getTemplate();
        if (StringUtils.isEmpty(template)) {
            template = FileUtil.read(getClass().getResourceAsStream("knowledge_template.json"), "UTF-8");
        }
        return template;
    }

}
