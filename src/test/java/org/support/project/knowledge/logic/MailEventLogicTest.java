/**
 * 
 */
package org.support.project.knowledge.logic;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.knowledge.TestCommon;
import org.support.project.knowledge.config.AppConfig;
import org.support.project.knowledge.config.SystemConfig;
import org.support.project.knowledge.dao.EventsDao;
import org.support.project.knowledge.dao.KnowledgeItemValuesDao;
import org.support.project.knowledge.dao.KnowledgesDao;
import org.support.project.knowledge.dao.ParticipantsDao;
import org.support.project.knowledge.dao.TemplateMastersDao;
import org.support.project.knowledge.entity.EventsEntity;
import org.support.project.knowledge.entity.KnowledgeItemValuesEntity;
import org.support.project.knowledge.entity.KnowledgesEntity;
import org.support.project.knowledge.entity.ParticipantsEntity;
import org.support.project.knowledge.entity.TemplateItemsEntity;
import org.support.project.knowledge.entity.TemplateMastersEntity;
import org.support.project.knowledge.vo.KnowledgeData;
import org.support.project.web.dao.MailConfigsDao;
import org.support.project.web.dao.MailsDao;
import org.support.project.web.dao.SystemConfigsDao;
import org.support.project.web.entity.MailConfigsEntity;
import org.support.project.web.entity.MailsEntity;
import org.support.project.web.entity.SystemConfigsEntity;

/**
 * @author koda
 *
 */
public class MailEventLogicTest extends TestCommon{
    /** ログ */
    private static final Log LOG = LogFactory.getLog(MailEventLogicTest.class);

    /**
     * @BeforeClass
     * @throws Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        TestCommon.setUpBeforeClass();
        
        MailConfigsEntity config = new MailConfigsEntity(AppConfig.get().getSystemName());
        config.setHost("localhost");
        config.setPort(110);
        config.setAuthType(0);
        config.setFromAddress("sample_sender@example.com");
        config.setFromName("sample sender");
        MailConfigsDao.get().save(config);
        
        SystemConfigsEntity system = new SystemConfigsEntity(SystemConfig.SYSTEM_URL, AppConfig.get().getSystemName());
        system.setConfigValue("http://localhost:8080/knowledge");
        SystemConfigsDao.get().save(system);
    }

    /**
     * @Before
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        List<KnowledgesEntity> knowledges = KnowledgesDao.get().selectAll();
        for (KnowledgesEntity knowledge : knowledges) {
            KnowledgesDao.get().delete(knowledge);
        }
        List<KnowledgeItemValuesEntity> items = KnowledgeItemValuesDao.get().selectAll();
        for (KnowledgeItemValuesEntity item : items) {
            KnowledgeItemValuesDao.get().delete(item);
        }
        List<EventsEntity> events = EventsDao.get().selectAll();
        for (EventsEntity event : events) {
            EventsDao.get().delete(event);
        }
        List<MailsEntity> mails = MailsDao.get().selectAll();
        for (MailsEntity mail : mails) {
            MailsDao.get().delete(mail);
        }
    }
    
    
    

    /**
     * Test method for {@link org.support.project.knowledge.logic.MailEventLogic#notifyEvents()}.
     * @throws Exception 
     */
    @Test
    public void testNotifyEvents() throws Exception {
        KnowledgesEntity knowledge = new KnowledgesEntity();
        knowledge.setTitle("Sample1");
        knowledge.setContent("Test");
        knowledge.setTypeId(TemplateLogic.TYPE_ID_EVENT);
        
        TimeZone timezone = TimeZone.getTimeZone("GMT");
        Calendar now = Calendar.getInstance(timezone);
        int diff = now.get(Calendar.DAY_OF_WEEK);
        now.set(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DATE), 0, 0, 0);
        now.add(Calendar.DATE, -diff);
        now.add(Calendar.DATE, Calendar.THURSDAY);
        
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        
        TemplateMastersEntity template = TemplateMastersDao.get().selectWithItems(knowledge.getTypeId());
        List<TemplateItemsEntity> items = template.getItems();
        for (TemplateItemsEntity item : items) {
            if (item.getItemNo() == EventsLogic.ITEM_NO_DATE) {
                item.setItemValue(dateFormat.format(new Date(now.getTimeInMillis())));
            } else if (item.getItemNo() == EventsLogic.ITEM_NO_START) {
                item.setItemValue("11:00");
            } else if (item.getItemNo() == EventsLogic.ITEM_NO_END) {
                item.setItemValue("13:00");
            } else if (item.getItemNo() == EventsLogic.ITEM_NO_TIMEZONE) {
                item.setItemValue("Asia/Tokyo");
            } else if (item.getItemNo() == EventsLogic.ITEM_NO_THE_NUMBER_TO_BE_ACCEPTED) {
                item.setItemValue("3");
            } else if (item.getItemNo() == EventsLogic.ITEM_NO_PLACE) {
                item.setItemValue("第1会議室");
            }
        }
        
        KnowledgeData data = KnowledgeData.create(knowledge, "", "", "", null, null, template);
        knowledge = KnowledgeLogic.get().insert(data, loginedUser);
        
        // 参加登録
        ParticipantsEntity participant = new ParticipantsEntity(knowledge.getKnowledgeId(), loginedUser.getUserId());
        participant.setStatus(EventsLogic.STATUS_PARTICIPATION);
        ParticipantsDao.get().save(participant);
        
        // メール通知実行
        MailEventLogic.get().notifyEvents();
        
        // メールが1件登録されていること
        List<MailsEntity> mails = MailsDao.get().selectAll();
        Calendar today = Calendar.getInstance(timezone);
        EventsLogic.get().logging(today);
        if (today.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            Assert.assertEquals(1, mails.size());
        } else {
            Assert.assertEquals(0, mails.size());
        }
    }

}
