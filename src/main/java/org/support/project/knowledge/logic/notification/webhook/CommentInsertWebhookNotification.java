package org.support.project.knowledge.logic.notification.webhook;

import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
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
import org.support.project.knowledge.dao.CommentsDao;
import org.support.project.knowledge.dao.KnowledgesDao;
import org.support.project.knowledge.dao.WebhookConfigsDao;
import org.support.project.knowledge.dao.WebhooksDao;
import org.support.project.knowledge.entity.CommentsEntity;
import org.support.project.knowledge.entity.KnowledgesEntity;
import org.support.project.knowledge.entity.WebhookConfigsEntity;
import org.support.project.knowledge.entity.WebhooksEntity;
import org.support.project.knowledge.logic.MailLogic;
import org.support.project.knowledge.logic.NotifyLogic;
import org.support.project.knowledge.logic.WebhookLogic;
import org.support.project.knowledge.logic.notification.QueueNotification;
import org.support.project.knowledge.vo.notification.webhook.WebhookCommentJson;
import org.support.project.web.dao.UsersDao;
import org.support.project.web.entity.UsersEntity;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

import net.arnx.jsonic.JSON;

/**
 * コメントが投稿された際のWebHook通知
 * @author koda
 */
@DI(instance = Instance.Prototype)
public class CommentInsertWebhookNotification {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(CommentInsertWebhookNotification.class);
    /** インスタンス取得 */
    public static CommentInsertWebhookNotification get() {
        return Container.getComp(CommentInsertWebhookNotification.class);
    }
    
    private KnowledgeUpdateWebHookNotification knowledgeUpdateWebHookNotification = KnowledgeUpdateWebHookNotification.get();
    
    /**
     * コメント追加のWebhookの登録を行う
     * @param comment
     * @param knowledge
     */
    public void sendCommentWebhook(CommentsEntity comment, KnowledgesEntity knowledge) {
        WebhookConfigsDao webhookConfigsDao = WebhookConfigsDao.get();
        List<WebhookConfigsEntity> webhookConfigsEntities = webhookConfigsDao.selectOnHook(WebhookConfigsEntity.HOOK_COMMENTS);
        if (0 == webhookConfigsEntities.size()) {
            return;
        }
        String json = createWebhookJson(comment, knowledge);
        WebhooksEntity webhooksEntity = new WebhooksEntity();
        String webhookId = MailLogic.get().idGen("Notify");
        webhooksEntity.setWebhookId(webhookId);
        webhooksEntity.setStatus(WebhookLogic.WEBHOOK_STATUS_UNSENT);
        webhooksEntity.setHook(WebhookConfigsEntity.HOOK_COMMENTS);
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
     * @param comment
     * @return
     */
    private String createWebhookJson(CommentsEntity comment, KnowledgesEntity knowledge) {
        WebhookCommentJson json = new WebhookCommentJson();
        json.commentId = comment.getCommentNo();
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
            template = FileUtil.read(getClass().getResourceAsStream("comment_template.json"), "UTF-8");
        }
        WebhookCommentJson json = JSON.decode(entity.getContent(), WebhookCommentJson.class);
        CommentsEntity comment = CommentsDao.get().selectOnKey(json.commentId);
        UsersEntity insertUser = UsersDao.get().selectOnKey(comment.getInsertUser());
        if (insertUser != null) {
            comment.setInsertUserName(insertUser.getUserName());
        }
        UsersEntity updateUser = UsersDao.get().selectOnKey(comment.getInsertUser());
        if (updateUser != null) {
            comment.setUpdateUserName(updateUser.getUserName());
        }
        KnowledgesEntity knowledge = KnowledgesDao.get().selectOnKeyWithUserName(comment.getKnowledgeId());
        JsonElement send = new JsonParser().parse(new StringReader(template));
        buildJson(send.getAsJsonObject(), comment, knowledge);
        return send.toString();
    }
    private void buildJson(JsonObject obj, CommentsEntity comment, KnowledgesEntity knowledge) {
        Iterator<String> props = obj.keySet().iterator();
        while (props.hasNext()) {
            String prop = (String) props.next();
            JsonElement e = obj.get(prop);
            if (e.isJsonPrimitive()) {
                JsonElement conv = convValue(e.getAsJsonPrimitive(), comment, knowledge);
                if (conv != null) {
                    obj.add(prop, conv);
                }
            } else if (e.isJsonObject()) {
                LOG.info("property:" + prop + " is object.");
                JsonObject child = e.getAsJsonObject();
                buildJson(child, comment, knowledge);
            } else if (e.isJsonArray()) {
                JsonArray array = e.getAsJsonArray();
                for (int i = 0; i < array.size(); i++) {
                    JsonElement item = array.get(i);
                    if (item.isJsonObject()) {
                        JsonObject child = item.getAsJsonObject();
                        buildJson(child, comment, knowledge);
                    }
                }
            }
        }
    }
    private JsonElement convValue(JsonPrimitive primitive, CommentsEntity comment, KnowledgesEntity knowledge) {
        JsonElement conv = convValueOnComment(primitive, comment, knowledge);
        if (conv != null) {
            return conv;
        }
        return knowledgeUpdateWebHookNotification.convValue(primitive, knowledge, QueueNotification.TYPE_KNOWLEDGE_UPDATE, false);
    }
    private JsonElement convValueOnComment(JsonPrimitive primitive, CommentsEntity comment, KnowledgesEntity knowledge) {
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
            if (name.equals("comment")) {
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
                return knowledgeUpdateWebHookNotification.convValue(comment, prop, option);
            }
        }
        return null;
    }

}
