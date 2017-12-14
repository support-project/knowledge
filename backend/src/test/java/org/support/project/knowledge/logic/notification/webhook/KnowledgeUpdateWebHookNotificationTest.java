package org.support.project.knowledge.logic.notification.webhook;

import java.io.InputStreamReader;
import java.io.StringReader;
import java.sql.Timestamp;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.DateUtils;
import org.support.project.common.util.StringUtils;
import org.support.project.common.util.SystemUtils;
import org.support.project.knowledge.TestCommon;
import org.support.project.knowledge.bat.NotifyMailBat;
import org.support.project.knowledge.bat.WebhookBat;
import org.support.project.knowledge.config.AppConfig;
import org.support.project.knowledge.config.SystemConfig;
import org.support.project.knowledge.dao.WebhookConfigsDao;
import org.support.project.knowledge.dao.WebhooksDao;
import org.support.project.knowledge.entity.KnowledgesEntity;
import org.support.project.knowledge.entity.WebhookConfigsEntity;
import org.support.project.knowledge.entity.WebhooksEntity;
import org.support.project.knowledge.logic.KnowledgeLogic;
import org.support.project.knowledge.logic.TemplateLogic;
import org.support.project.knowledge.vo.KnowledgeData;
import org.support.project.web.dao.SystemConfigsDao;
import org.support.project.web.entity.SystemConfigsEntity;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class KnowledgeUpdateWebHookNotificationTest extends TestCommon {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(KnowledgeUpdateWebHookNotificationTest.class);
    
    public static boolean sendWebhook = false;
    public static WebhookConfigsEntity config;
    
    /**
     * @BeforeClass
     * @throws Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        TestCommon.setUpBeforeClass();
        
        SystemConfigsEntity systemConfig = new SystemConfigsEntity(SystemConfig.SYSTEM_URL, AppConfig.get().getSystemName());
        systemConfig.setConfigValue("http://localhost:8080");
        SystemConfigsDao.get().save(systemConfig);
        
        config = new WebhookConfigsEntity();
        config.setHook(WebhookConfigsEntity.HOOK_KNOWLEDGES);
        config.setUrl("http://localhost:8081");
        WebhookConfigsDao.get().insert(config);
        
        String testWebhookSend = SystemUtils.getenv("KNOWLEDGE_TEST_SEND_WEBHOOK");
        if (StringUtils.isNotEmpty(testWebhookSend) && testWebhookSend.toLowerCase().equals("true")) {
            // 実際にWebHookを送信する
            // src/knowledge/src/test/webhook/app.js の node.js のテスト用サーバーを起動しておくことを想定
            sendWebhook = true;
        }
    }
    
    @Test
    public void testSendKnowledgeWebhook() throws Exception {
        KnowledgesEntity knowledge = new KnowledgesEntity();
        knowledge.setTitle("サンプル");
        knowledge.setContent("１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０サンプル");
        knowledge.setPublicFlag(KnowledgeLogic.PUBLIC_FLAG_PROTECT);
        knowledge.setLikeCount(new Long(0));
        knowledge.setCommentCount(0);
        knowledge.setTypeId(TemplateLogic.TYPE_ID_KNOWLEDGE);
        knowledge.setInsertUser(loginedUser.getUserId());
        knowledge.setInsertDatetime(new Timestamp(DateUtils.now().getTime()));
        KnowledgeData data = new KnowledgeData();
        data.setTagsStr("KnowledgeManagement,Java,SQL");
        data.setViewers("G-0,G-1,U-3");
        data.setKnowledge(knowledge);
        knowledge = KnowledgeLogic.get().insert(data, loginedUser);
        
        NotifyMailBat.main(null);

        List<WebhooksEntity> hooks = WebhooksDao.get().selectAll();
        Assert.assertEquals(1, hooks.size());
        Assert.assertEquals(WebhookConfigsEntity.HOOK_KNOWLEDGES, hooks.get(0).getHook());

        JsonElement expected = new JsonParser().parse(new InputStreamReader(getClass().getResourceAsStream("webhook_knowledge.json"), "UTF-8"));
        JsonElement actual = new JsonParser().parse(new StringReader(hooks.get(0).getContent()));
        AssertJson.equals(expected.getAsJsonObject(), actual.getAsJsonObject());
        
        String sendJSON = KnowledgeUpdateWebHookNotification.get().createSendJson(hooks.get(0), config);
        expected = new JsonParser().parse(new InputStreamReader(getClass().getResourceAsStream("webhook_send_knowledge.json"), "UTF-8"));
        actual = new JsonParser().parse(new StringReader(sendJSON));
        AssertJson.equals(expected.getAsJsonObject(), actual.getAsJsonObject());
        
        if (sendWebhook) {
            LOG.info("Webhook送信");
            WebhookBat.main(null);
        }
    }

}
