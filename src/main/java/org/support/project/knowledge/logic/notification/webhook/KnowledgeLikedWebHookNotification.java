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
import org.support.project.knowledge.dao.LikesDao;
import org.support.project.knowledge.entity.KnowledgesEntity;
import org.support.project.knowledge.entity.LikesEntity;
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
        LOG.trace("createWebhookJson");
        WebhookLongIdJson json = new WebhookLongIdJson();
        json.id = like.getNo();
        return JSON.encode(json);
    }
    
    @Override
    public String createSendJson(WebhooksEntity entity, WebhookConfigsEntity configEntity) throws Exception {
        LOG.trace("createSendJson");
        String template = loadTemplate(configEntity);
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
        Map<String, Object> map = new HashMap<>();
        map.put("knowledge", knowledge);
        map.put("like", like);
        map.put("knowledge.type", QueueNotification.TYPE_KNOWLEDGE_UPDATE);
        map.put("knowledge.became_public", false);
        buildJson(send.getAsJsonObject(), map);
        return send.toString();
    }
    @Override
    public String loadTemplate(WebhookConfigsEntity configEntity) throws UnsupportedEncodingException, IOException {
        String template = configEntity.getTemplate();
        if (StringUtils.isEmpty(template)) {
            template = FileUtil.read(getClass().getResourceAsStream("liked_knowledge_template.json"), "UTF-8");
        }
        return template;
    }

}
