package org.support.project.knowledge.logic.notification.webhook;

import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.List;
import java.util.Locale;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.test.Order;
import org.support.project.common.test.OrderedRunner;
import org.support.project.knowledge.TestCommon;
import org.support.project.knowledge.bat.NotifyMailBat;
import org.support.project.knowledge.bat.WebhookBat;
import org.support.project.knowledge.dao.WebhookConfigsDao;
import org.support.project.knowledge.dao.WebhooksDao;
import org.support.project.knowledge.entity.WebhookConfigsEntity;
import org.support.project.knowledge.entity.WebhooksEntity;
import org.support.project.knowledge.logic.LikeLogic;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

@RunWith(OrderedRunner.class)
public class CommentInsertWebhookNotificationTest extends TestCommon {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(CommentInsertWebhookNotificationTest.class);
    
    /**
     * @BeforeClass
     * @throws Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        CommentLikedWebhookNotificationTest.setUpBeforeClass();
        
        WebhookConfigsEntity config = new WebhookConfigsEntity();
        config.setHook(WebhookConfigsEntity.HOOK_LIKED_COMMENT);
        config.setUrl("http://localhost:8081");
        WebhookConfigsDao.get().insert(config);
    }
    
    @Test
    @Order(order=1)
    public void testSendKnowledgeWebhook() throws Exception {
        // Knowledgeを登録し、Webhookを登録
        KnowledgeUpdateWebHookNotificationTest test = new KnowledgeUpdateWebHookNotificationTest();
        test.testSendKnowledgeWebhook();
        
        List<WebhooksEntity> hooks = WebhooksDao.get().selectAll();
        for (WebhooksEntity webhooksEntity : hooks) {
            WebhooksDao.get().delete(webhooksEntity);
        }
    }
    
    @Test
    @Order(order=2)
    public void testSendCommentWebhook() throws Exception {
        CommentLikedWebhookNotificationTest test = new CommentLikedWebhookNotificationTest();
        test.testSendCommentWebhook();
        
        List<WebhooksEntity> hooks = WebhooksDao.get().selectAll();
        for (WebhooksEntity webhooksEntity : hooks) {
            WebhooksDao.get().delete(webhooksEntity);
        }
    }
    

    @Test
    @Order(order=3)
    public void testSendLikedCommentWebhook() throws Exception {
        int index = 1;
        LikeLogic.get().addLikeComment(new Long(1), loginedUser, Locale.JAPAN);
        NotifyMailBat.main(null);
        
        List<WebhooksEntity> hooks = WebhooksDao.get().selectAll();
        Assert.assertEquals(index, hooks.size());
        Assert.assertEquals(WebhookConfigsEntity.HOOK_LIKED_COMMENT, hooks.get(hooks.size() - 1).getHook());
        
        JsonElement expected = new JsonParser().parse(new InputStreamReader(getClass().getResourceAsStream("webhook_liked.json"), "UTF-8"));
        JsonElement actual = new JsonParser().parse(new StringReader(hooks.get(hooks.size() - 1).getContent()));
        AssertJson.equals(expected.getAsJsonObject(), actual.getAsJsonObject());

        String sendJSON = CommentLikedWebhookNotification.get().createSendJson(hooks.get(hooks.size() - 1), KnowledgeUpdateWebHookNotificationTest.config);
        LOG.info(sendJSON);
        expected = new JsonParser().parse(new InputStreamReader(getClass().getResourceAsStream("webhook_send_liked_comment.json"), "UTF-8"));
        actual = new JsonParser().parse(new StringReader(sendJSON));
        AssertJson.equals(expected.getAsJsonObject(), actual.getAsJsonObject());
        
        if (KnowledgeUpdateWebHookNotificationTest.sendWebhook) {
            LOG.info("Webhook送信");
            WebhookBat.main(null);
        }
    }
    
    
}
