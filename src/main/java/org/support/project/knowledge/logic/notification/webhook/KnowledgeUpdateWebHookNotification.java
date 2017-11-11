package org.support.project.knowledge.logic.notification.webhook;

import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.FileUtil;
import org.support.project.common.util.PropertyUtil;
import org.support.project.common.util.StringUtils;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.dao.KnowledgesDao;
import org.support.project.knowledge.dao.TagsDao;
import org.support.project.knowledge.dao.WebhookConfigsDao;
import org.support.project.knowledge.dao.WebhooksDao;
import org.support.project.knowledge.entity.KnowledgesEntity;
import org.support.project.knowledge.entity.TagsEntity;
import org.support.project.knowledge.entity.WebhookConfigsEntity;
import org.support.project.knowledge.entity.WebhooksEntity;
import org.support.project.knowledge.logic.KnowledgeLogic;
import org.support.project.knowledge.logic.MailLogic;
import org.support.project.knowledge.logic.NotifyLogic;
import org.support.project.knowledge.logic.TargetLogic;
import org.support.project.knowledge.logic.WebhookLogic;
import org.support.project.knowledge.logic.notification.QueueNotification;
import org.support.project.knowledge.vo.notification.webhook.WebhookKnowledgeJson;
import org.support.project.web.bean.LabelValue;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

import net.arnx.jsonic.JSON;

/**
 * 記事が投稿された際のWebHook通知
 * 
 * @author koda
 */
@DI(instance = Instance.Prototype)
public class KnowledgeUpdateWebHookNotification {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(KnowledgeUpdateWebHookNotification.class);

    /** インスタンス取得 */
    public static KnowledgeUpdateWebHookNotification get() {
        return Container.getComp(KnowledgeUpdateWebHookNotification.class);
    }

    /**
     * 記事の追加・更新のWebhookの登録を行う
     * 
     * @param comment
     * @param knowledge
     */
    public void sendKnowledgeWebhook(KnowledgesEntity knowledge, int type) {
        WebhookConfigsDao webhookConfigsDao = WebhookConfigsDao.get();
        List<WebhookConfigsEntity> webhookConfigsEntities = webhookConfigsDao.selectOnHook(WebhookConfigsEntity.HOOK_KNOWLEDGES);
        if (0 == webhookConfigsEntities.size()) {
            return;
        }
        String json = createWebhookJson(knowledge, type);
        WebhooksEntity webhooksEntity = new WebhooksEntity();
        String webhookId = MailLogic.get().idGen("Notify");
        webhooksEntity.setWebhookId(webhookId);
        webhooksEntity.setStatus(WebhookLogic.WEBHOOK_STATUS_UNSENT);
        webhooksEntity.setHook(WebhookConfigsEntity.HOOK_KNOWLEDGES);
        webhooksEntity.setContent(json);

        if (LOG.isTraceEnabled()) {
            LOG.trace(PropertyUtil.reflectionToString(webhooksEntity));
        }
        WebhooksDao.get().insert(webhooksEntity);
    }

    /**
     * Webhook通知情報に格納するJSONを生成する 送信先に合わせて送るJSONの型を変更するため、初めはキーの値のみをJSONとして保存している
     * 
     * @param knowledge
     * @param type
     * @return
     */
    private String createWebhookJson(KnowledgesEntity knowledge, int type) {
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

    /**
     * Webhookで送信するJSONを生成する 送信先毎に送る型を変化させることが可能
     * 
     * @param entity
     * @param configEntity
     * @throws IOException
     * @throws UnsupportedEncodingException
     */
    public String createSendJson(WebhooksEntity entity, WebhookConfigsEntity configEntity) throws UnsupportedEncodingException, IOException {
        String template = configEntity.getTemplate();
        if (StringUtils.isEmpty(template)) {
            template = FileUtil.read(getClass().getResourceAsStream("knowledge_template.json"), "UTF-8");
        }
        WebhookKnowledgeJson json = JSON.decode(entity.getContent(), WebhookKnowledgeJson.class);
        KnowledgesEntity knowledge = KnowledgesDao.get().selectOnKeyWithUserName(json.knowledgeId);
        JsonElement send = new JsonParser().parse(new StringReader(template));
        buildJson(send.getAsJsonObject(), knowledge, json.type, json.became_public);
        return send.toString();
    }
    private void buildJson(JsonObject obj, KnowledgesEntity knowledge, int type, boolean became_public) {
        LOG.info(obj.toString());
        Iterator<String> props = obj.keySet().iterator();
        while (props.hasNext()) {
            String prop = (String) props.next();
            JsonElement e = obj.get(prop);
            LOG.info(prop + ":" + e.toString());
            if (e.isJsonPrimitive()) {
                JsonElement conv = convValue(e.getAsJsonPrimitive(), knowledge, type, became_public);
                if (conv != null) {
                    obj.add(prop, conv);
                }
            } else if (e.isJsonObject()) {
                LOG.info("property:" + prop + " is object.");
                JsonObject child = e.getAsJsonObject();
                buildJson(child, knowledge, type, became_public);
            } else if (e.isJsonArray()) {
                JsonArray array = e.getAsJsonArray();
                for (int i = 0; i < array.size(); i++) {
                    JsonElement item = array.get(i);
                    if (item.isJsonObject()) {
                        JsonObject child = item.getAsJsonObject();
                        buildJson(child, knowledge, type, became_public);
                    }
                }
            }
        }
    }
    public JsonElement convValue(JsonPrimitive primitive, KnowledgesEntity knowledge, int type, boolean became_public) {
        if (!primitive.isString()) {
            return null;
        }
        String val = primitive.getAsString();
        if (!val.startsWith("{") || !val.endsWith("}")) {
            return null;
        }
        String item = val.substring(1, val.length() - 1);
        String option = "";
        if (item.indexOf(",") != -1) {
            option = item.substring(item.indexOf(",") + 1);
            item = item.substring(0, item.indexOf(","));
        }
        LOG.debug(item + " : " + option);
        String[] sp = item.split("\\.");
        LOG.debug(sp);
        if (sp.length == 2) {
            String name = sp[0];
            String prop = sp[1];
            if (name.equals("knowledge")) {
                if (prop.equals("link")) {
                    return new JsonPrimitive(NotifyLogic.get().makeURL(knowledge.getKnowledgeId()));
                } else if (prop.equals("status")) {
                    String status = "updated";
                    if (QueueNotification.TYPE_KNOWLEDGE_INSERT == type) {
                        status = "created";
                    }
                    return new JsonPrimitive(status);
                } else if (prop.equals("became_public")) {
                    return new JsonPrimitive(became_public);
                } else if (prop.equals("text")) {
                    /** This code make JSON to send Slack */
                    String linktop = "<";
                    String linkpipe = "|";
                    String linkend = ">";

                    /** This code make JSON to send Slack */
                    StringBuffer SendBuff = new StringBuffer();
                    SendBuff.append(linktop);
                    SendBuff.append(NotifyLogic.get().makeURL(knowledge.getKnowledgeId()));
                    SendBuff.append(linkpipe);
                    SendBuff.append(knowledge.getTitle());
                    SendBuff.append(linkend);
                    String SendString = SendBuff.toString();
                    return new JsonPrimitive(SendString);
                } else if (prop.equals("groups")) {
                    List<LabelValue> labelGroups = TargetLogic.get().selectTargetsOnKnowledgeId(knowledge.getKnowledgeId());
                    JsonArray groups = new JsonArray();
                    for (LabelValue label : labelGroups) {
                        if (label.getValue().startsWith("G")) {
                            groups.add(label.getLabel());
                        }
                    }
                    return groups;
                } else if (prop.equals("tags")) {
                    List<TagsEntity> tagsEntities = TagsDao.get().selectOnKnowledgeId(knowledge.getKnowledgeId());
                    JsonArray tags = new JsonArray();
                    for (TagsEntity tag : tagsEntities) {
                        tags.add(tag.getTagName());
                    }
                    return tags;
                } else {
                    return convValue(knowledge, prop, option);
                }
            }
        }
        return null;
    }

    public JsonElement convValue(Object obj, String prop, String option) {
        if (prop.equals("insertUserName") || prop.equals("updateUserName")) {
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
