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
import org.support.project.knowledge.dao.CommentsDao;
import org.support.project.knowledge.dao.KnowledgesDao;
import org.support.project.knowledge.entity.CommentsEntity;
import org.support.project.knowledge.entity.KnowledgesEntity;
import org.support.project.knowledge.entity.WebhookConfigsEntity;
import org.support.project.knowledge.entity.WebhooksEntity;
import org.support.project.knowledge.logic.notification.QueueNotification;
import org.support.project.knowledge.vo.notification.webhook.WebhookLongIdJson;
import org.support.project.web.dao.UsersDao;
import org.support.project.web.entity.UsersEntity;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import net.arnx.jsonic.JSON;

/**
 * コメントが投稿された際のWebHook通知
 * @author koda
 */
@DI(instance = Instance.Prototype)
public class CommentInsertWebhookNotification extends AbstractWebHookNotification {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(CommentInsertWebhookNotification.class);
    /** インスタンス取得 */
    public static CommentInsertWebhookNotification get() {
        return Container.getComp(CommentInsertWebhookNotification.class);
    }
    private CommentsEntity comment;
//    private KnowledgesEntity knowledge;
    public void init(CommentsEntity comment, KnowledgesEntity knowledge) {
//        this.knowledge = knowledge;
        this.comment = comment;
        super.inited = true;
    }
    @Override
    protected String getHook() {
        return WebhookConfigsEntity.HOOK_COMMENTS;
    }
    @Override
    protected String createWebhookJson() {
        LOG.trace("createWebhookJson");
        WebhookLongIdJson json = new WebhookLongIdJson();
        json.id = comment.getCommentNo();
        return JSON.encode(json);
    }
    
    @Override
    public String createSendJson(WebhooksEntity entity, WebhookConfigsEntity configEntity) throws UnsupportedEncodingException, IOException {
        LOG.trace("createSendJson");
        String template = loadTemplate(configEntity);
        WebhookLongIdJson json = JSON.decode(entity.getContent(), WebhookLongIdJson.class);
        CommentsEntity comment = CommentsDao.get().selectOnKey(json.id);
        if (comment == null) {
            return ""; // 生成エラー
        }
        UsersEntity insertUser = UsersDao.get().selectOnKey(comment.getInsertUser());
        if (insertUser != null) {
            comment.setInsertUserName(insertUser.getUserName());
        }
        UsersEntity updateUser = UsersDao.get().selectOnKey(comment.getInsertUser());
        if (updateUser != null) {
            comment.setUpdateUserName(updateUser.getUserName());
        }
        KnowledgesEntity knowledge = KnowledgesDao.get().selectOnKeyWithUserName(comment.getKnowledgeId());
        if (knowledge == null) {
            return ""; // 生成エラー
        }
        JsonElement send = new JsonParser().parse(new StringReader(template));
        
        Map<String, Object> map = new HashMap<>();
        map.put("knowledge", knowledge);
        map.put("comment", comment);
        map.put("knowledge.type", QueueNotification.TYPE_KNOWLEDGE_UPDATE);
        map.put("knowledge.became_public", false);
        buildJson(send.getAsJsonObject(), map);
        return send.toString();
    }
    @Override
    public String loadTemplate(WebhookConfigsEntity configEntity) throws UnsupportedEncodingException, IOException {
        String template = configEntity.getTemplate();
        if (StringUtils.isEmpty(template)) {
            template = FileUtil.read(getClass().getResourceAsStream("comment_template.json"), "UTF-8");
        }
        return template;
    }
}
