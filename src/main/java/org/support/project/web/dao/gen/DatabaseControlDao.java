package org.support.project.web.dao.gen;

import org.support.project.ormapping.dao.AbstractDao;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.di.DI;
import org.support.project.di.Instance;

/**
 * ユーザ
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
        String[] sqls = new String[27];
        sqls[0] = "DROP TABLE IF EXISTS HASH_CONFIGS CASCADE;";
        sqls[1] = "DROP TABLE IF EXISTS GROUPS CASCADE;";
        sqls[2] = "DROP TABLE IF EXISTS SYSTEM_CONFIGS CASCADE;";
        sqls[3] = "DROP TABLE IF EXISTS MAIL_CONFIGS CASCADE;";
        sqls[4] = "DROP TABLE IF EXISTS PASSWORD_RESETS CASCADE;";
        sqls[5] = "DROP TABLE IF EXISTS FUNCTIONS CASCADE;";
        sqls[6] = "DROP TABLE IF EXISTS NOTICES CASCADE;";
        sqls[7] = "DROP TABLE IF EXISTS LOCALES CASCADE;";
        sqls[8] = "DROP TABLE IF EXISTS SYSTEM_ATTRIBUTES CASCADE;";
        sqls[9] = "DROP TABLE IF EXISTS USER_GROUPS CASCADE;";
        sqls[10] = "DROP TABLE IF EXISTS NOTIFICATIONS CASCADE;";
        sqls[11] = "DROP TABLE IF EXISTS ACCESS_LOGS CASCADE;";
        sqls[12] = "DROP TABLE IF EXISTS ROLE_FUNCTIONS CASCADE;";
        sqls[13] = "DROP TABLE IF EXISTS PROXY_CONFIGS CASCADE;";
        sqls[14] = "DROP TABLE IF EXISTS MAILS CASCADE;";
        sqls[15] = "DROP TABLE IF EXISTS LDAP_CONFIGS CASCADE;";
        sqls[16] = "DROP TABLE IF EXISTS LOGIN_HISTORIES CASCADE;";
        sqls[17] = "DROP TABLE IF EXISTS CONFIRM_MAIL_CHANGES CASCADE;";
        sqls[18] = "DROP TABLE IF EXISTS USER_ALIAS CASCADE;";
        sqls[19] = "DROP TABLE IF EXISTS USER_NOTIFICATIONS CASCADE;";
        sqls[20] = "DROP TABLE IF EXISTS PROVISIONAL_REGISTRATIONS CASCADE;";
        sqls[21] = "DROP TABLE IF EXISTS USER_ROLES CASCADE;";
        sqls[22] = "DROP TABLE IF EXISTS ROLES CASCADE;";
        sqls[23] = "DROP TABLE IF EXISTS USER_CONFIGS CASCADE;";
        sqls[24] = "DROP TABLE IF EXISTS READ_MARKS CASCADE;";
        sqls[25] = "DROP TABLE IF EXISTS SYSTEMS CASCADE;";
        sqls[26] = "DROP TABLE IF EXISTS USERS CASCADE;";
        for (String sql : sqls) {
            LOG.debug(sql);
            executeUpdate(sql);
        }
    }
    /** Delete all table data */
    public void dropAllData() {
        String[] sqls = new String[27];
        sqls[0] = "TRUNCATE TABLE HASH_CONFIGS;";
        sqls[1] = "TRUNCATE TABLE GROUPS;";
        sqls[2] = "TRUNCATE TABLE SYSTEM_CONFIGS;";
        sqls[3] = "TRUNCATE TABLE MAIL_CONFIGS;";
        sqls[4] = "TRUNCATE TABLE PASSWORD_RESETS;";
        sqls[5] = "TRUNCATE TABLE FUNCTIONS;";
        sqls[6] = "TRUNCATE TABLE NOTICES;";
        sqls[7] = "TRUNCATE TABLE LOCALES;";
        sqls[8] = "TRUNCATE TABLE SYSTEM_ATTRIBUTES;";
        sqls[9] = "TRUNCATE TABLE USER_GROUPS;";
        sqls[10] = "TRUNCATE TABLE NOTIFICATIONS;";
        sqls[11] = "TRUNCATE TABLE ACCESS_LOGS;";
        sqls[12] = "TRUNCATE TABLE ROLE_FUNCTIONS;";
        sqls[13] = "TRUNCATE TABLE PROXY_CONFIGS;";
        sqls[14] = "TRUNCATE TABLE MAILS;";
        sqls[15] = "TRUNCATE TABLE LDAP_CONFIGS;";
        sqls[16] = "TRUNCATE TABLE LOGIN_HISTORIES;";
        sqls[17] = "TRUNCATE TABLE CONFIRM_MAIL_CHANGES;";
        sqls[18] = "TRUNCATE TABLE USER_ALIAS;";
        sqls[19] = "TRUNCATE TABLE USER_NOTIFICATIONS;";
        sqls[20] = "TRUNCATE TABLE PROVISIONAL_REGISTRATIONS;";
        sqls[21] = "TRUNCATE TABLE USER_ROLES;";
        sqls[22] = "TRUNCATE TABLE ROLES;";
        sqls[23] = "TRUNCATE TABLE USER_CONFIGS;";
        sqls[24] = "TRUNCATE TABLE READ_MARKS;";
        sqls[25] = "TRUNCATE TABLE SYSTEMS;";
        sqls[26] = "TRUNCATE TABLE USERS;";
        for (String sql : sqls) {
            LOG.debug(sql);
            executeUpdate(sql);
        }
    }

}
