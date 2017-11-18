package org.support.project.knowledge.logic.notification.webhook;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.support.project.common.exception.SystemException;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.PropertyUtil;
import org.support.project.common.util.StringUtils;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.dao.TagsDao;
import org.support.project.knowledge.dao.WebhookConfigsDao;
import org.support.project.knowledge.dao.WebhooksDao;
import org.support.project.knowledge.entity.KnowledgesEntity;
import org.support.project.knowledge.entity.TagsEntity;
import org.support.project.knowledge.entity.WebhookConfigsEntity;
import org.support.project.knowledge.entity.WebhooksEntity;
import org.support.project.knowledge.logic.MailLogic;
import org.support.project.knowledge.logic.NotifyLogic;
import org.support.project.knowledge.logic.TargetLogic;
import org.support.project.knowledge.logic.WebhookLogic;
import org.support.project.knowledge.logic.notification.QueueNotification;
import org.support.project.web.bean.LabelValue;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
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
     * テンプレートを読み出す
     * @param configEntity
     * @return
     * @throws Exception
     */
    public abstract String loadTemplate(WebhookConfigsEntity configEntity) throws Exception;

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
    
    protected void buildJson(JsonObject obj, Map<String, Object> map) {
        LOG.debug(obj.toString());
        Iterator<String> props = obj.keySet().iterator();
        while (props.hasNext()) {
            String prop = (String) props.next();
            JsonElement e = obj.get(prop);
            LOG.debug(prop + ":" + e.toString());
            if (e.isJsonPrimitive()) {
                JsonElement conv = convValue(e.getAsJsonPrimitive(), prop, map);
                if (conv != null) {
                    obj.add(prop, conv);
                }
            } else if (e.isJsonObject()) {
                LOG.debug("property:" + prop + " is object.");
                JsonObject child = e.getAsJsonObject();
                buildJson(child, map);
            } else if (e.isJsonArray()) {
                JsonArray array = e.getAsJsonArray();
                for (int i = 0; i < array.size(); i++) {
                    JsonElement item = array.get(i);
                    if (item.isJsonObject()) {
                        JsonObject child = item.getAsJsonObject();
                        buildJson(child, map);
                    }
                }
            }
        }
    }
    /**
     * JSONの `"title": "{knowledge.title}",` の部分を処理する
     * 置換する必要が無いものであれば、nullを返す
     * 変換する場合、そのプロパティにセットする JsonElement を返す
     * 
     * @param primitive Jsonの値( "{knowledge.title}" にあたる部分)
     *                  文字列以外の場合は、置換する必要無し
     * @param prop プロパティ名 ( "title" にあたる部分 )
     * @param map 変換に使う値が入っているMap
     * @return 変換結果
     */
    private JsonElement convValue(JsonPrimitive primitive, String prop, Map<String, Object> map) {
        if (map == null || prop == null) {
            return null;
        }
        if (!primitive.isString()) {
            return null;
        }
        String val = primitive.getAsString();
        // まず、JSONの値のテキストの置換が必要な部分とそれ以外で分ける
        List<String> params = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < val.length(); i++) {
            char c = val.charAt(i);
            if (c == '{') {
                if (builder.length() > 0) {
                    params.add(builder.toString());
                }
                builder = new StringBuilder();
                builder.append(c);
            } else if (c == '}') {
                builder.append(c);
                params.add(builder.toString());
                builder = new StringBuilder();
            } else {
                builder.append(c);
            }
        }
        if (builder.length() > 0) {
            params.add(builder.toString());
        }
        LOG.debug(params);
        
        // 各文字列を処理し、その結果を結果のリストに格納
        List<JsonElement> results = new ArrayList<>();
        for (String string : params) {
            results.add(processParam(string, map));
        }
        // 結果のリストの状態を見て、結果のJsonElementを構築
        if (results.size() == 1) {
            return results.get(0); //返却値が一つで、かつそれがJsonの型であれば、そのまま返す
        } else {
            // 複数あるのもは文字列にする
            StringBuilder join = new StringBuilder();
            for (JsonElement jsonElement : results) {
                if (jsonElement != null) {
                    join.append(jsonElement.getAsString());
                }
            }
            return new JsonPrimitive(join.toString());
        }
    }
    /**
     * 文字列の処理
     * @param string
     * @param map
     * @return
     */
    private JsonElement processParam(String val, Map<String, Object> map) {
        if (!val.startsWith("{") || !val.endsWith("}")) {
            return new JsonPrimitive(val);  // {xxx} になっていないものは、そのままの文字を返す
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
        if (map.containsKey(item)) {
            // 直接、そのキー値が格納されているものは、それをそのまま返す (ex: {knowledge.became_public})
            Object o = map.get(item);
            if (o instanceof Number) {
                return new JsonPrimitive((Number) o);
            } else if (o instanceof Boolean) {
                return new JsonPrimitive((Boolean) o);
            } else if (o instanceof String) {
                return new JsonPrimitive((Boolean) o);
            }
            return null;
        }
        
        if (sp.length == 2) {
            String name = sp[0];
            String prop = sp[1];
            // 予約語の処理
            if (name.equals("knowledge")) {
                KnowledgesEntity knowledge = (KnowledgesEntity) map.get(name);
                if (knowledge == null) {
                    return null;
                }
                if (prop.equals("link")) {
                    return new JsonPrimitive(NotifyLogic.get().makeURL(knowledge.getKnowledgeId()));
                } else if (prop.equals("status")) {
                    Object o = map.get("knowledge.type");
                    if (o == null) {
                        return null;
                    }
                    int type = (int) map.get("knowledge.type");
                    String status = "updated";
                    if (QueueNotification.TYPE_KNOWLEDGE_INSERT == type) {
                        status = "created";
                    }
                    return new JsonPrimitive(status);
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
                }
            }
            Object obj = map.get(name);
            if (obj != null) {
                return convValue(obj, prop, option);
            }
        }
        return new JsonPrimitive(val);
    }
    /**
     * 指定のオブジェクトのプロパティ値でJsonの値を変換
     * @param obj
     * @param prop
     * @param option
     * @return
     */
    public JsonElement convValue(Object obj, String prop, String option) {
        if (obj instanceof String) {
            return new JsonPrimitive((String) obj);
        } else if (obj instanceof Number) {
            return new JsonPrimitive((Number) obj);
        } else if (obj instanceof Boolean) {
            return new JsonPrimitive((Boolean) obj);
        }
        if (prop.equals("insertUserName") || prop.equals("updateUserName") || prop.equals("userName")) {
            String v = (String) PropertyUtil.getPropertyValue(obj, prop);
            if (v == null) {
                v = "Unknown user";
            }
            return new JsonPrimitive(v);
        }
        if (!PropertyUtil.hasProperty(obj.getClass(), prop)) {
            return null;
        }
        Object v = PropertyUtil.getPropertyValue(obj, prop);
        if (v instanceof Number) {
            return new JsonPrimitive((Number) v);
        } else if (v instanceof Boolean) {
            return new JsonPrimitive((Boolean) v);
        } else if (v instanceof String) {
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
            return new JsonPrimitive(str);
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
