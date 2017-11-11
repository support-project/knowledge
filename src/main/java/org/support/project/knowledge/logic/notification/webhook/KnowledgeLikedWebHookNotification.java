package org.support.project.knowledge.logic.notification.webhook;

import java.io.StringReader;
import java.util.Iterator;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.FileUtil;
import org.support.project.common.util.StringUtils;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.dao.KnowledgesDao;
import org.support.project.knowledge.dao.LikesDao;
import org.support.project.knowledge.entity.KnowledgesEntity;
import org.support.project.knowledge.entity.LikesEntity;
import org.support.project.knowledge.entity.WebhookConfigsEntity;
import org.support.project.knowledge.entity.WebhooksEntity;
import org.support.project.knowledge.logic.NotifyLogic;
import org.support.project.knowledge.logic.notification.QueueNotification;
import org.support.project.knowledge.vo.notification.webhook.WebhookLongIdJson;
import org.support.project.web.dao.UsersDao;
import org.support.project.web.entity.UsersEntity;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

import net.arnx.jsonic.JSON;

/**
 * イイネが押されたときのWebHook
 * @author koda
 */
@DI(instance = Instance.Prototype)
public class KnowledgeLikedWebHookNotification extends AbstractWebHookNotification {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(KnowledgeLikedWebHookNotification.class);
    /** インスタンス取得 */
    public static KnowledgeLikedWebHookNotification get() {
        return Container.getComp(KnowledgeLikedWebHookNotification.class);
    }
    
    private KnowledgeUpdateWebHookNotification knowledgeUpdateWebHookNotification = KnowledgeUpdateWebHookNotification.get();
    
    private LikesEntity like;
    public void init(LikesEntity like) {
        this.like = like;
        super.inited = true;
    }
    @Override
    protected String getHook() {
        return WebhookConfigsEntity.HOOK_LIKED_KNOWLEDGE;
    }
    @Override
    protected String createWebhookJson() {
        WebhookLongIdJson json = new WebhookLongIdJson();
        json.id = like.getNo();
        return JSON.encode(json);
    }
    
    @Override
    public String createSendJson(WebhooksEntity entity, WebhookConfigsEntity configEntity) throws Exception {
        String template = configEntity.getTemplate();
        if (StringUtils.isEmpty(template)) {
            template = FileUtil.read(getClass().getResourceAsStream("liked_knowledge_template.json"), "UTF-8");
        }
        WebhookLongIdJson json = JSON.decode(entity.getContent(), WebhookLongIdJson.class);
        like = LikesDao.get().selectOnKey(json.id);
        if (like == null) {
            return ""; // 生成エラー
        }
        UsersEntity insertUser = UsersDao.get().selectOnKey(like.getInsertUser());
        if (insertUser != null) {
            like.setUserName(insertUser.getUserName());
        } else {
            like.setUserName("Anonymous");
        }
        KnowledgesEntity knowledge = KnowledgesDao.get().selectOnKeyWithUserName(like.getKnowledgeId());
        if (knowledge == null) {
            return ""; // 生成エラー
        }
        JsonElement send = new JsonParser().parse(new StringReader(template));
        buildJson(send.getAsJsonObject(), like, knowledge);
        return send.toString();
    }
    private void buildJson(JsonObject obj, LikesEntity like, KnowledgesEntity knowledge) {
        Iterator<String> props = obj.keySet().iterator();
        while (props.hasNext()) {
            String prop = (String) props.next();
            JsonElement e = obj.get(prop);
            if (e.isJsonPrimitive()) {
                JsonElement conv = convValue(e.getAsJsonPrimitive(), like, knowledge);
                if (conv != null) {
                    obj.add(prop, conv);
                }
            } else if (e.isJsonObject()) {
                LOG.info("property:" + prop + " is object.");
                JsonObject child = e.getAsJsonObject();
                buildJson(child, like, knowledge);
            } else if (e.isJsonArray()) {
                JsonArray array = e.getAsJsonArray();
                for (int i = 0; i < array.size(); i++) {
                    JsonElement item = array.get(i);
                    if (item.isJsonObject()) {
                        JsonObject child = item.getAsJsonObject();
                        buildJson(child, like, knowledge);
                    }
                }
            }
        }
    }
    private JsonElement convValue(JsonPrimitive primitive, LikesEntity like, KnowledgesEntity knowledge) {
        JsonElement conv = convValueOnLike(primitive, like, knowledge);
        if (conv != null) {
            return conv;
        }
        return knowledgeUpdateWebHookNotification.convValue(primitive, knowledge, QueueNotification.TYPE_KNOWLEDGE_UPDATE, false);
    }
    private JsonElement convValueOnLike(JsonPrimitive primitive, LikesEntity like, KnowledgesEntity knowledge) {
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
            if (name.equals("like")) {
                if (prop.equals("text")) {
                    /**  This code make JSON to send Slack */
                    String linktop = "<";
                    String linkpipe = "|";
                    String linkend = ">";
                    
                    /**  This code make JSON to send Slack */
                    StringBuffer SendBuff = new StringBuffer();
                    SendBuff.append(linktop);
                    SendBuff.append(NotifyLogic.get().makeURL(knowledge.getKnowledgeId()));
                    SendBuff.append(linkpipe);
                    SendBuff.append(knowledge.getTitle());
                    SendBuff.append(linkend);
                    String SendString = SendBuff.toString();
                    return new JsonPrimitive(SendString);
                }
                return super.convValue(like, prop, option);
            }
        }
        return null;
    }

}
