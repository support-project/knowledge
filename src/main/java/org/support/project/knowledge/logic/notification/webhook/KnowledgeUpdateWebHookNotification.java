package org.support.project.knowledge.logic.notification.webhook;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.PropertyUtil;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
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
import org.support.project.web.bean.LabelValue;
import org.support.project.web.dao.UsersDao;
import org.support.project.web.entity.UsersEntity;

import net.arnx.jsonic.JSON;

/**
 * 記事が投稿された際のWebHook通知
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
     * @param comment
     * @param knowledge
     */
    public void sendKnowledgeWebhook(KnowledgesEntity knowledge, int type) {
        WebhookConfigsDao webhookConfigsDao = WebhookConfigsDao.get();
        List<WebhookConfigsEntity> webhookConfigsEntities = webhookConfigsDao.selectOnHook(WebhookConfigsEntity.HOOK_KNOWLEDGES);

        if (0 == webhookConfigsEntities.size()) {
            return;
        }

        Map<String, Object> knowledgeData = getKnowledgeData(knowledge, type);

        WebhooksEntity webhooksEntity = new WebhooksEntity();
        String webhookId = MailLogic.get().idGen("Notify");
        webhooksEntity.setWebhookId(webhookId);
        webhooksEntity.setStatus(WebhookLogic.WEBHOOK_STATUS_UNSENT);
        webhooksEntity.setHook(WebhookConfigsEntity.HOOK_KNOWLEDGES);
        webhooksEntity.setContent(JSON.encode(knowledgeData));
        
        if (LOG.isTraceEnabled()) {
            LOG.trace(PropertyUtil.reflectionToString(webhooksEntity));
        }
        WebhooksDao.get().insert(webhooksEntity);
    }
    
    /**
     * 記事のjsonデータを取得する
     *
     * @param knowledge
     * @param type
     * @return
     */
    public Map<String, Object> getKnowledgeData(KnowledgesEntity knowledge, Integer type) {
        Map<String, Object> jsonObject = new HashMap<String, Object>();

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
        jsonObject.put("text", SendString);
        
        jsonObject.put("knowledge_id", knowledge.getKnowledgeId());
        jsonObject.put("title", knowledge.getTitle());
        jsonObject.put("content", knowledge.getContent());
        jsonObject.put("public_flag", knowledge.getPublicFlag());
        jsonObject.put("like_count", knowledge.getLikeCount());
        jsonObject.put("comment_count", knowledge.getCommentCount());
        jsonObject.put("type_id", knowledge.getTypeId());
        jsonObject.put("link", NotifyLogic.get().makeURL(knowledge.getKnowledgeId()));
        
        boolean became_public = false;
        if (knowledge.getNotifyStatus() == null || knowledge.getNotifyStatus().intValue() == 0) {
            if (knowledge.getPublicFlag().intValue() != KnowledgeLogic.PUBLIC_FLAG_PRIVATE) {
                // 今まで非公開だったものが、初めての通知になった
                became_public = true;
            }
        }
        jsonObject.put("became_public", became_public);

        if (type != null) {
            if (QueueNotification.TYPE_KNOWLEDGE_INSERT == type) {
                jsonObject.put("status", "created");
            } else {
                jsonObject.put("status", "updated");
            }
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        UsersEntity insertUser = UsersDao.get().selectOnKey(knowledge.getInsertUser());
        if (insertUser != null) {
            jsonObject.put("insert_user", insertUser.getUserName());
        } else {
            jsonObject.put("insert_user", "Unknown user");
        }
        jsonObject.put("insert_date", simpleDateFormat.format(knowledge.getInsertDatetime()));

        UsersEntity updateUser = UsersDao.get().selectOnKey(knowledge.getInsertUser());
        if (updateUser != null) {
            jsonObject.put("update_user", updateUser.getUserName());
        } else {
            jsonObject.put("insert_user", "Unknown user");
        }
        jsonObject.put("update_date", simpleDateFormat.format(knowledge.getUpdateDatetime()));

        List<TagsEntity> tagsEntities = TagsDao.get().selectOnKnowledgeId(knowledge.getKnowledgeId());
        List<String> tags = new ArrayList<String>();
        for (TagsEntity tag : tagsEntities) {
            tags.add(tag.getTagName());
        }
        jsonObject.put("tags", tags);

        List<LabelValue> labelGroups = TargetLogic.get().selectTargetsOnKnowledgeId(knowledge.getKnowledgeId());
        List<String> groups = new ArrayList<String>();
        for (LabelValue label : labelGroups) {
            if (label.getValue().startsWith("G")) {
                groups.add(label.getLabel());
            }
        }
        jsonObject.put("groups", groups);

        return jsonObject;
    }
}
