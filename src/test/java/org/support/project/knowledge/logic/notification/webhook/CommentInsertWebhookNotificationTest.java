package org.support.project.knowledge.logic.notification.webhook;

import java.io.InputStreamReader;
import java.io.StringReader;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.test.Order;
import org.support.project.common.test.OrderedRunner;
import org.support.project.common.util.DateUtils;
import org.support.project.knowledge.TestCommon;
import org.support.project.knowledge.bat.NotifyMailBat;
import org.support.project.knowledge.dao.WebhookConfigsDao;
import org.support.project.knowledge.dao.WebhooksDao;
import org.support.project.knowledge.entity.CommentsEntity;
import org.support.project.knowledge.entity.KnowledgesEntity;
import org.support.project.knowledge.entity.WebhookConfigsEntity;
import org.support.project.knowledge.entity.WebhooksEntity;
import org.support.project.knowledge.logic.KnowledgeLogic;
import org.support.project.knowledge.logic.TemplateLogic;
import org.support.project.knowledge.vo.KnowledgeData;

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
        TestCommon.setUpBeforeClass();
        WebhookConfigsEntity config = new WebhookConfigsEntity();
        config.setHook(WebhookConfigsEntity.HOOK_KNOWLEDGES);
        config.setUrl("http://example.support-project.org");
        WebhookConfigsDao.get().insert(config);
        
        config = new WebhookConfigsEntity();
        config.setHook(WebhookConfigsEntity.HOOK_COMMENTS);
        config.setUrl("http://example.support-project.org");
        WebhookConfigsDao.get().insert(config);
    }
    
    @Test
    @Order(order=1)
    public void testSendKnowledgeWebhook() throws Exception {
        // Knowledgeを登録し、Webhookを登録
        KnowledgeUpdateWebHookNotificationTest test = new KnowledgeUpdateWebHookNotificationTest();
        test.testSendKnowledgeWebhook();
    }
    
    @Test
    @Order(order=2)
    public void testSendCommentWebhook() throws Exception {
        KnowledgeLogic.get().saveComment(new Long(1), "コメント", new ArrayList<>(), loginedUser);
        NotifyMailBat.main(null);
        
        List<WebhooksEntity> hooks = WebhooksDao.get().selectAll();
        Assert.assertEquals(2, hooks.size());
        Assert.assertEquals(WebhookConfigsEntity.HOOK_COMMENTS, hooks.get(0).getHook());
        
        JsonElement expected = new JsonParser().parse(new InputStreamReader(getClass().getResourceAsStream("webhook2.json"), "UTF-8"));
        JsonElement actual = new JsonParser().parse(new StringReader(hooks.get(0).getContent()));
        AssertJson.equals(expected.getAsJsonObject(), actual.getAsJsonObject());
    }
    

}
