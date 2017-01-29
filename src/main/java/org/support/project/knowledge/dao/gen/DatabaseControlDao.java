package org.support.project.knowledge.dao.gen;

import org.support.project.ormapping.dao.AbstractDao;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.di.DI;
import org.support.project.di.Instance;

/**
 * テンプレートの項目
 * this class is auto generate and not edit.
 */
@DI(instance = Instance.Singleton)
public class DatabaseControlDao extends AbstractDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;
    /** LOG */
    private static final Log LOG = LogFactory.getLog(DatabaseControlDao.class);

    /** Drop all tables */
    public void dropAllTable() {
        String[] sqls = new String[32];
        sqls[0] = "DROP TABLE IF EXISTS KNOWLEDGES CASCADE;";
        sqls[1] = "DROP TABLE IF EXISTS SERVICE_LOCALE_CONFIGS CASCADE;";
        sqls[2] = "DROP TABLE IF EXISTS ACCOUNT_IMAGES CASCADE;";
        sqls[3] = "DROP TABLE IF EXISTS MAIL_POSTS CASCADE;";
        sqls[4] = "DROP TABLE IF EXISTS WEBHOOK_CONFIGS CASCADE;";
        sqls[5] = "DROP TABLE IF EXISTS VOTES CASCADE;";
        sqls[6] = "DROP TABLE IF EXISTS COMMENTS CASCADE;";
        sqls[7] = "DROP TABLE IF EXISTS KNOWLEDGE_GROUPS CASCADE;";
        sqls[8] = "DROP TABLE IF EXISTS MAIL_HOOK_CONDITIONS CASCADE;";
        sqls[9] = "DROP TABLE IF EXISTS LIKES CASCADE;";
        sqls[10] = "DROP TABLE IF EXISTS DRAFT_ITEM_VALUES CASCADE;";
        sqls[11] = "DROP TABLE IF EXISTS VIEW_HISTORIES CASCADE;";
        sqls[12] = "DROP TABLE IF EXISTS KNOWLEDGE_EDIT_GROUPS CASCADE;";
        sqls[13] = "DROP TABLE IF EXISTS WEBHOOKS CASCADE;";
        sqls[14] = "DROP TABLE IF EXISTS KNOWLEDGE_FILES CASCADE;";
        sqls[15] = "DROP TABLE IF EXISTS NOTIFY_QUEUES CASCADE;";
        sqls[16] = "DROP TABLE IF EXISTS TEMPLATE_MASTERS CASCADE;";
        sqls[17] = "DROP TABLE IF EXISTS SERVICE_CONFIGS CASCADE;";
        sqls[18] = "DROP TABLE IF EXISTS ITEM_CHOICES CASCADE;";
        sqls[19] = "DROP TABLE IF EXISTS KNOWLEDGE_EDIT_USERS CASCADE;";
        sqls[20] = "DROP TABLE IF EXISTS PINS CASCADE;";
        sqls[21] = "DROP TABLE IF EXISTS KNOWLEDGE_HISTORIES CASCADE;";
        sqls[22] = "DROP TABLE IF EXISTS KNOWLEDGE_ITEM_VALUES CASCADE;";
        sqls[23] = "DROP TABLE IF EXISTS KNOWLEDGE_TAGS CASCADE;";
        sqls[24] = "DROP TABLE IF EXISTS NOTIFY_CONFIGS CASCADE;";
        sqls[25] = "DROP TABLE IF EXISTS MAIL_HOOKS CASCADE;";
        sqls[26] = "DROP TABLE IF EXISTS DRAFT_KNOWLEDGES CASCADE;";
        sqls[27] = "DROP TABLE IF EXISTS STOCKS CASCADE;";
        sqls[28] = "DROP TABLE IF EXISTS STOCK_KNOWLEDGES CASCADE;";
        sqls[29] = "DROP TABLE IF EXISTS KNOWLEDGE_USERS CASCADE;";
        sqls[30] = "DROP TABLE IF EXISTS TAGS CASCADE;";
        sqls[31] = "DROP TABLE IF EXISTS TEMPLATE_ITEMS CASCADE;";
        for (String sql : sqls) {
            LOG.debug(sql);
            executeUpdate(sql);
        }
    }
    /** Delete all table data */
    public void dropAllData() {
        String[] sqls = new String[32];
        sqls[0] = "TRUNCATE TABLE KNOWLEDGES;";
        sqls[1] = "TRUNCATE TABLE SERVICE_LOCALE_CONFIGS;";
        sqls[2] = "TRUNCATE TABLE ACCOUNT_IMAGES;";
        sqls[3] = "TRUNCATE TABLE MAIL_POSTS;";
        sqls[4] = "TRUNCATE TABLE WEBHOOK_CONFIGS;";
        sqls[5] = "TRUNCATE TABLE VOTES;";
        sqls[6] = "TRUNCATE TABLE COMMENTS;";
        sqls[7] = "TRUNCATE TABLE KNOWLEDGE_GROUPS;";
        sqls[8] = "TRUNCATE TABLE MAIL_HOOK_CONDITIONS;";
        sqls[9] = "TRUNCATE TABLE LIKES;";
        sqls[10] = "TRUNCATE TABLE DRAFT_ITEM_VALUES;";
        sqls[11] = "TRUNCATE TABLE VIEW_HISTORIES;";
        sqls[12] = "TRUNCATE TABLE KNOWLEDGE_EDIT_GROUPS;";
        sqls[13] = "TRUNCATE TABLE WEBHOOKS;";
        sqls[14] = "TRUNCATE TABLE KNOWLEDGE_FILES;";
        sqls[15] = "TRUNCATE TABLE NOTIFY_QUEUES;";
        sqls[16] = "TRUNCATE TABLE TEMPLATE_MASTERS;";
        sqls[17] = "TRUNCATE TABLE SERVICE_CONFIGS;";
        sqls[18] = "TRUNCATE TABLE ITEM_CHOICES;";
        sqls[19] = "TRUNCATE TABLE KNOWLEDGE_EDIT_USERS;";
        sqls[20] = "TRUNCATE TABLE PINS;";
        sqls[21] = "TRUNCATE TABLE KNOWLEDGE_HISTORIES;";
        sqls[22] = "TRUNCATE TABLE KNOWLEDGE_ITEM_VALUES;";
        sqls[23] = "TRUNCATE TABLE KNOWLEDGE_TAGS;";
        sqls[24] = "TRUNCATE TABLE NOTIFY_CONFIGS;";
        sqls[25] = "TRUNCATE TABLE MAIL_HOOKS;";
        sqls[26] = "TRUNCATE TABLE DRAFT_KNOWLEDGES;";
        sqls[27] = "TRUNCATE TABLE STOCKS;";
        sqls[28] = "TRUNCATE TABLE STOCK_KNOWLEDGES;";
        sqls[29] = "TRUNCATE TABLE KNOWLEDGE_USERS;";
        sqls[30] = "TRUNCATE TABLE TAGS;";
        sqls[31] = "TRUNCATE TABLE TEMPLATE_ITEMS;";
        for (String sql : sqls) {
            LOG.debug(sql);
            executeUpdate(sql);
        }
    }

}
