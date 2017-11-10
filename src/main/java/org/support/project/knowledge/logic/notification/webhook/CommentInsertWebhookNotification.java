package org.support.project.knowledge.logic.notification.webhook;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.PropertyUtil;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.dao.WebhookConfigsDao;
import org.support.project.knowledge.dao.WebhooksDao;
import org.support.project.knowledge.entity.CommentsEntity;
import org.support.project.knowledge.entity.KnowledgesEntity;
import org.support.project.knowledge.entity.WebhookConfigsEntity;
import org.support.project.knowledge.entity.WebhooksEntity;
import org.support.project.knowledge.logic.MailLogic;
import org.support.project.knowledge.logic.NotifyLogic;
import org.support.project.knowledge.logic.WebhookLogic;
import org.support.project.web.dao.UsersDao;
import org.support.project.web.entity.UsersEntity;

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

        Map<String, Object> commentData = getCommentData(comment, knowledge);

        WebhooksEntity webhooksEntity = new WebhooksEntity();
        String webhookId = MailLogic.get().idGen("Notify");
        webhooksEntity.setWebhookId(webhookId);
        webhooksEntity.setStatus(WebhookLogic.WEBHOOK_STATUS_UNSENT);
        webhooksEntity.setHook(WebhookConfigsEntity.HOOK_COMMENTS);
        webhooksEntity.setContent(JSON.encode(commentData));
        
        if (LOG.isTraceEnabled()) {
            LOG.trace(PropertyUtil.reflectionToString(webhooksEntity));
        }

        WebhooksDao.get().insert(webhooksEntity);
    }
    
    
    /**
     * コメントのjsonデータを取得する
     *
     * @param comment
     * @param knowledge
     * @return
     */
    public Map<String, Object> getCommentData(CommentsEntity comment, KnowledgesEntity knowledge) {
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
        
        jsonObject.put("comment_no", comment.getCommentNo());
        jsonObject.put("comment", comment.getComment());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        UsersEntity insertUser = UsersDao.get().selectOnKey(comment.getInsertUser());
        if (insertUser != null) {
            jsonObject.put("insert_user", insertUser.getUserName());
        } else {
            jsonObject.put("insert_user", "Unknown user");
        }
        jsonObject.put("insert_date", simpleDateFormat.format(comment.getInsertDatetime()));

        UsersEntity updateUser = UsersDao.get().selectOnKey(comment.getInsertUser());
        if (updateUser != null) {
            jsonObject.put("update_user", updateUser.getUserName());
        } else {
            jsonObject.put("insert_user", "Unknown user");
        }
        jsonObject.put("update_date", simpleDateFormat.format(knowledge.getUpdateDatetime()));

        jsonObject.put("knowledge", KnowledgeUpdateWebHookNotification.get().getKnowledgeData(knowledge, null));
        return jsonObject;
    }
    

}
