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
import org.support.project.knowledge.TestCommon;
import org.support.project.knowledge.bat.NotifyMailBat;
import org.support.project.knowledge.dao.WebhookConfigsDao;
import org.support.project.knowledge.dao.WebhooksDao;
import org.support.project.knowledge.entity.KnowledgesEntity;
import org.support.project.knowledge.entity.WebhookConfigsEntity;
import org.support.project.knowledge.entity.WebhooksEntity;
import org.support.project.knowledge.logic.KnowledgeLogic;
import org.support.project.knowledge.logic.TemplateLogic;
import org.support.project.knowledge.vo.KnowledgeData;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class KnowledgeUpdateWebHookNotificationTest extends TestCommon {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(KnowledgeUpdateWebHookNotificationTest.class);
    
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
    }
    
    @Test
    public void testSendKnowledgeWebhook() throws Exception {
        KnowledgesEntity knowledge = new KnowledgesEntity();
        knowledge.setTitle("サンプル");
        knowledge.setContent("サンプルです");
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
        
        JsonElement expected = new JsonParser().parse(new InputStreamReader(getClass().getResourceAsStream("webhook1.json"), "UTF-8"));
        LOG.info(expected.toString());
        JsonElement actual = new JsonParser().parse(new StringReader(hooks.get(0).getContent()));
        LOG.info(actual.toString());
        
        Assert.assertEquals(expected.getAsJsonObject().get("title").getAsString(), actual.getAsJsonObject().get("title").getAsString());
        Assert.assertEquals(expected.getAsJsonObject().get("content").getAsString(), actual.getAsJsonObject().get("content").getAsString());
        
        Assert.assertEquals(expected.getAsJsonObject().get("comment_count").getAsInt(), actual.getAsJsonObject().get("comment_count").getAsInt());
        Assert.assertEquals(expected.getAsJsonObject().get("like_count").getAsInt(), actual.getAsJsonObject().get("like_count").getAsInt());
        Assert.assertEquals(expected.getAsJsonObject().get("became_public").getAsBoolean(), actual.getAsJsonObject().get("became_public").getAsBoolean());
        Assert.assertEquals(expected.getAsJsonObject().get("type_id").getAsInt(), actual.getAsJsonObject().get("type_id").getAsInt());
        
        Assert.assertEquals(expected.getAsJsonObject().get("link").getAsString(), actual.getAsJsonObject().get("link").getAsString()); // システムのURLが設定されていない
        Assert.assertEquals(expected.getAsJsonObject().get("groups").getAsJsonArray().size(), actual.getAsJsonObject().get("groups").getAsJsonArray().size()); //0
        for (int i = 0; i < expected.getAsJsonObject().get("groups").getAsJsonArray().size(); i++) {
            Assert.assertEquals(expected.getAsJsonObject().get("groups").getAsJsonArray().get(i).getAsString(),
                    actual.getAsJsonObject().get("groups").getAsJsonArray().get(i).getAsString());
        }
        Assert.assertEquals(expected.getAsJsonObject().get("insert_user").getAsString(), actual.getAsJsonObject().get("insert_user").getAsString());
        Assert.assertEquals(expected.getAsJsonObject().get("tags").getAsJsonArray().size(), actual.getAsJsonObject().get("tags").getAsJsonArray().size());
        for (int i = 0; i < expected.getAsJsonObject().get("tags").getAsJsonArray().size(); i++) {
            Assert.assertEquals(expected.getAsJsonObject().get("tags").getAsJsonArray().get(i).getAsString(),
                    actual.getAsJsonObject().get("tags").getAsJsonArray().get(i).getAsString());
        }
        Assert.assertEquals(expected.getAsJsonObject().get("update_user").getAsString(), actual.getAsJsonObject().get("update_user").getAsString());
        Assert.assertEquals(expected.getAsJsonObject().get("text").getAsString(), actual.getAsJsonObject().get("text").getAsString());
        Assert.assertEquals(expected.getAsJsonObject().get("public_flag").getAsInt(), actual.getAsJsonObject().get("public_flag").getAsInt());
        Assert.assertEquals(expected.getAsJsonObject().get("knowledge_id").getAsInt(), actual.getAsJsonObject().get("knowledge_id").getAsInt());
        Assert.assertEquals(expected.getAsJsonObject().get("status").getAsString(), actual.getAsJsonObject().get("status").getAsString());
    }

}
