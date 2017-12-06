package org.support.project.knowledge;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.ja.JapaneseAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.support.project.common.config.ConfigLoader;
import org.support.project.common.config.INT_FLAG;
import org.support.project.common.exception.SerializeException;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.logic.H2DBServerLogic;
import org.support.project.common.serialize.SerializeUtils;
import org.support.project.common.test.OrderedRunner;
import org.support.project.common.test.TestWatcher;
import org.support.project.common.util.RandomUtil;
import org.support.project.common.util.StringUtils;
import org.support.project.common.util.SystemUtils;
import org.support.project.knowledge.config.AppConfig;
import org.support.project.knowledge.dao.gen.DatabaseControlDao;
import org.support.project.knowledge.deploy.InitDB;
import org.support.project.knowledge.entity.KnowledgesEntity;
import org.support.project.knowledge.logic.KnowledgeLogic;
import org.support.project.knowledge.logic.TemplateLogic;
import org.support.project.knowledge.logic.UserLogicEx;
import org.support.project.knowledge.vo.KnowledgeData;
import org.support.project.ormapping.common.DBUserPool;
import org.support.project.ormapping.common.IDGen;
import org.support.project.ormapping.connection.ConnectionManager;
import org.support.project.ormapping.tool.config.ORmappingToolConfig;
import org.support.project.web.bean.LoginedUser;
import org.support.project.web.dao.GroupsDao;
import org.support.project.web.dao.RolesDao;
import org.support.project.web.dao.UserGroupsDao;
import org.support.project.web.dao.UserRolesDao;
import org.support.project.web.dao.UsersDao;
import org.support.project.web.entity.GroupsEntity;
import org.support.project.web.entity.RolesEntity;
import org.support.project.web.entity.UserGroupsEntity;
import org.support.project.web.entity.UserRolesEntity;
import org.support.project.web.entity.UsersEntity;

/**
 * Abstract class for test.
 * @author Koda
 *
 */
@RunWith(OrderedRunner.class)
public abstract class TestCommon {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(TestCommon.class);

    public static int WAIT_MILLSEC = 50;
    
    public static final String KNOWLEDGE_TEST_HOME = "KNOWLEDGE_TEST_HOME";
    public static final String KNOWLEDGE_UNIT_TEST_WAIT_MILL = "KNOWLEDGE_UNIT_TEST_WAIT_MILL";
    /** login user for test */
    public static LoginedUser loginedUser = null;
    /** login user for test */
    public static LoginedUser loginedUser2 = null;
    /** login user for test */
    public static LoginedUser loginedUser3 = null;
    /** login user for test */
    public static LoginedUser loginedAdmin = null;
    
    /** group for test */
    public static GroupsEntity group = null;
    /** login user for test */
    public static LoginedUser groupuser1 = null;
    /** login user for test */
    public static LoginedUser groupuser2 = null;
    
    @Rule
    public TestWatcher watchman = new TestWatcher();
    
    /**
     * @BeforeClass
     * @throws Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        AppConfig.initEnvKey(KNOWLEDGE_TEST_HOME);
        if (!H2DBServerLogic.get().isActive()) {
            H2DBServerLogic.get().start();
        }
        
        String wait = SystemUtils.getenv(KNOWLEDGE_UNIT_TEST_WAIT_MILL);
        if (StringUtils.isInteger(wait)) {
            WAIT_MILLSEC = new Integer(wait);
        }
        
        testConnection();
        initData();
        DBUserPool.get().setUser(loginedUser.getUserId());
    }
    /**
     * @AfterClass
     * @throws Exception
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
//        H2DBServerLogic.get().stop();
    }
    /**
     * @Before
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
    }
    /**
     * @After
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception {
    }
    
    /**
     * connection change for test
     * @throws SerializeException
     * @throws IOException
     */
    public static void testConnection() throws SerializeException, IOException {
        // コネクション設定
        String configFileName = "/ormappingtool.xml";
        ORmappingToolConfig config = SerializeUtils.bytesToObject(TestCommon.class.getResourceAsStream(configFileName), ORmappingToolConfig.class);
        config.getConnectionConfig().convURL();
        ConnectionManager.getInstance().addConnectionConfig(config.getConnectionConfig());
    }
    
    /**
     * Insert common data for test.
     * @throws Exception
     */
    public static void initData() throws Exception {
        LOG.info("init data");
        loginedUser = new LoginedUser();
        loginedUser2 = new LoginedUser();
        loginedUser3 = new LoginedUser();
        loginedAdmin = new LoginedUser();
        groupuser1 = new LoginedUser();
        groupuser2 = new LoginedUser();
        
        loginedUser.setLocale(Locale.ENGLISH);
        loginedUser2.setLocale(Locale.ENGLISH);
        loginedUser3.setLocale(Locale.ENGLISH);
        loginedAdmin.setLocale(Locale.ENGLISH);
        groupuser1.setLocale(Locale.ENGLISH);
        groupuser2.setLocale(Locale.ENGLISH);

        synchronized (KNOWLEDGE_TEST_HOME) {
            Thread.sleep(WAIT_MILLSEC);
        }
        
        // DBを完全初期化
        DatabaseControlDao dao1 = new DatabaseControlDao();
        dao1.dropAllTable();
        org.support.project.web.dao.gen.DatabaseControlDao dao2 = new org.support.project.web.dao.gen.DatabaseControlDao();
        dao2.dropAllTable();
        
        synchronized (KNOWLEDGE_TEST_HOME) {
            Thread.sleep(WAIT_MILLSEC);
        }
        
        InitDB.main(new String[0]);
        
        synchronized (KNOWLEDGE_TEST_HOME) {
            Thread.sleep(WAIT_MILLSEC);
        }
        
        // 全文検索エンジンのインデックスの消去
        Analyzer analyzer = new JapaneseAnalyzer();
        AppConfig appConfig = ConfigLoader.load(AppConfig.APP_CONFIG, AppConfig.class);
        File indexDir = new File(appConfig.getIndexPath());
        Directory dir = FSDirectory.open(indexDir);
        IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_4_10_2, analyzer);
        iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);
        IndexWriter writer = null;
        try {
            writer = new IndexWriter(dir, iwc);
            writer.deleteAll();
            writer.commit();
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
        
        Integer[] rolesAdmin = {1}; // 2はユーザ、1はAdmin
        Integer[] roles = {2}; // 2はユーザ、1はAdmin
        addUser(loginedUser, "テストユーザ1", roles);
        addUser(loginedUser2, "テストユーザ2", roles);
        addUser(loginedUser3, "テストユーザ3", roles);
        addUser(loginedAdmin, "管理者", rolesAdmin);
        
        addUser(groupuser1, "GroupUser1", roles);
        addUser(groupuser2, "GroupUser2", roles);
        
        GroupsEntity groupsEntity = new GroupsEntity();
        groupsEntity.setGroupName("テストグループ");
        groupsEntity.setGroupKey("TestGroup");
        group = GroupsDao.get().save(groupsEntity);
        
        UserGroupsEntity usergroup = new UserGroupsEntity();
        usergroup.setGroupId(group.getGroupId());
        usergroup.setUserId(groupuser1.getUserId());
        UserGroupsDao.get().save(usergroup);
        
        usergroup = new UserGroupsEntity();
        usergroup.setGroupId(group.getGroupId());
        usergroup.setUserId(groupuser2.getUserId());
        UserGroupsDao.get().save(usergroup);
    }
    
    /**
     * テストユーザの情報を生成
     * 直接ユーザテーブルにデータを入れているため、通知設定などが設定されない
     * 古いテスト用（現在は使わないこと）
     * @param user
     * @param userName
     * @param roleIds
     * @return
     */
    private static List<RolesEntity> addUser(LoginedUser user, String userName, Integer[] roleIds) {
        synchronized (user) {
            try {
                Thread.sleep(WAIT_MILLSEC);
            } catch (InterruptedException e) {
            }
        }
        // テスト用のユーザを登録
        UsersEntity entity = new UsersEntity();
        entity.setUserKey(RandomUtil.randamGen(64));
        entity.setUserName(userName);
        entity.setPassword(RandomUtil.randamGen(64));
        entity.setMailAddress("sample@example.com");
        entity = UsersDao.get().insert(entity);
        user.setLoginUser(entity);

        synchronized (user) {
            try {
                Thread.sleep(WAIT_MILLSEC);
            } catch (InterruptedException e) {
            }
        }

        RolesDao rolesDao = RolesDao.get();
        for (Integer roleId : roleIds) {
            UserRolesEntity role = new UserRolesEntity();
            role.setRoleId(roleId);
            role.setUserId(entity.getUserId());
            UserRolesDao.get().save(role);
        }
        List<RolesEntity> rolesEntities = rolesDao.selectOnUserKey(entity.getUserKey());
        user.setRoles(rolesEntities);
        return rolesEntities;
    }






    /**
     * ナレッジを登録
     * @param title
     * @param loginedUser
     * @return
     * @throws Exception
     */
    protected KnowledgesEntity insertKnowledge(String title, LoginedUser loginedUser) throws Exception {
        int publicFlag = KnowledgeLogic.PUBLIC_FLAG_PUBLIC;
        int typeId = TemplateLogic.TYPE_ID_KNOWLEDGE;
        return insertKnowledge(title, loginedUser, typeId, publicFlag);
    }
    protected KnowledgesEntity insertKnowledge(String title, LoginedUser loginedUser, int typeId, int publicFlag) throws Exception {
        String viewersStr = "";
        return insertKnowledge(title, loginedUser, typeId, publicFlag, viewersStr);
    }
    protected KnowledgesEntity insertKnowledge(String title, LoginedUser loginedUser, int typeId, int publicFlag, String viewersStr) throws Exception {
        KnowledgesEntity entity = new KnowledgesEntity();
        entity.setTitle(title);
        entity.setContent("contens of " + title);
        entity.setTypeId(typeId);
        entity.setPublicFlag(publicFlag);
        KnowledgeData data = new KnowledgeData();
        data.setKnowledge(entity);
        if (StringUtils.isNotEmpty(viewersStr)) {
            data.setViewers(viewersStr);
        }
        entity = KnowledgeLogic.get().insert(data, loginedUser);
        return entity;
    }
    
    /**
     * ユーザの登録
     * @param userKey
     * @return
     */
    protected UsersEntity addUser(String userKey) {
        UsersEntity user = null;
        int count = 0;
        while (user == null) {
            try {
                user = doAddUser(userKey);
            } catch (Exception e) {
                count++;
                if (count > 10) {
                    throw e;
                }
                LOG.warn("insert user error. " + e.getMessage() + " retry add User");
            }
        }
        UsersEntity check = UsersDao.get().selectOnUserKey(user.getUserKey());
        Assert.assertNotNull(check);
        Assert.assertEquals(user.getUserKey(), check.getUserKey());
        Assert.assertEquals(user.getLocaleKey(), check.getLocaleKey());
        Assert.assertEquals(user.getMailAddress(), check.getMailAddress());
        Assert.assertEquals(user.getUserName(), check.getUserName());
        
        return user;
    }

    private UsersEntity doAddUser(String userKey) {
        synchronized (this) {
            try {
                Thread.sleep(WAIT_MILLSEC);
            } catch (InterruptedException e) {
            }
        }
        UsersEntity user = new UsersEntity();
        user.setAuthLdap(INT_FLAG.OFF.getValue());
        user.setEncrypted(false);
        user.setUserKey(userKey);
        user.setLocaleKey(Locale.JAPAN.toString());
        user.setMailAddress(RandomUtil.randamGen(8) + "@example.com");
        user.setUserName("Test-" + RandomUtil.randamGen(8));
        user.setPassword(IDGen.get().gen("hoge"));
        String[] roles = {"user"};
        user = UserLogicEx.get().insert(user, roles);
        return user;
    }


    /**
     * 登録されているユーザのログインユーザ情報を取得
     * @param userKey
     * @return
     */
    protected LoginedUser getLoginUser(String userKey) {
        UsersDao usersDao = UsersDao.get();
        UsersEntity usersEntity = usersDao.selectOnUserKey(userKey);
        RolesDao rolesDao = RolesDao.get();
        List<RolesEntity> rolesEntities = rolesDao.selectOnUserKey(userKey);

        LoginedUser loginedUser = new LoginedUser();
        loginedUser.setLoginUser(usersEntity);
        loginedUser.setRoles(rolesEntities);
        loginedUser.setLocale(usersEntity.getLocale());

        // グループ
        GroupsDao groupsDao = GroupsDao.get();
        List<GroupsEntity> groups = groupsDao.selectMyGroup(loginedUser, 0, Integer.MAX_VALUE);
        loginedUser.setGroups(groups);
        return loginedUser;
   }
}
