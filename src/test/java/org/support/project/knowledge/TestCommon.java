package org.support.project.knowledge;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.support.project.common.config.ConfigLoader;
import org.support.project.common.exception.SerializeException;
import org.support.project.common.logic.H2DBServerLogic;
import org.support.project.common.serialize.SerializeUtils;
import org.support.project.common.test.OrderedRunner;
import org.support.project.common.util.FileUtil;
import org.support.project.common.util.RandomUtil;
import org.support.project.knowledge.config.AppConfig;
import org.support.project.knowledge.dao.gen.DatabaseControlDao;
import org.support.project.knowledge.deploy.InitDB;
import org.support.project.knowledge.entity.KnowledgesEntity;
import org.support.project.knowledge.logic.KnowledgeLogic;
import org.support.project.knowledge.vo.KnowledgeData;
import org.support.project.ormapping.common.DBUserPool;
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
    public static final String KNOWLEDGE_TEST_HOME = "KNOWLEDGE_TEST_HOME";
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

        // DBを完全初期化
        DatabaseControlDao dao1 = new DatabaseControlDao();
        dao1.dropAllTable();
        org.support.project.web.dao.gen.DatabaseControlDao dao2 = new org.support.project.web.dao.gen.DatabaseControlDao();
        dao2.dropAllTable();
        InitDB.main(new String[0]);
        
        // 全文検索エンジンのインデックスの消去
        AppConfig appConfig = ConfigLoader.load(AppConfig.APP_CONFIG, AppConfig.class);
        File indexDir = new File(appConfig.getIndexPath());
        FileUtil.delete(indexDir);
        
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
     * @param user
     * @param userName
     * @param roleIds
     * @return
     */
    protected static List<RolesEntity> addUser(LoginedUser user, String userName, Integer[] roleIds) {
        // テスト用のユーザを登録
        UsersEntity entity = new UsersEntity();
        entity.setUserKey(RandomUtil.randamGen(64));
        entity.setUserName(userName);
        entity.setPassword(RandomUtil.randamGen(64));
        entity.setMailAddress("sample@example.com");
        entity = UsersDao.get().insert(entity);
        user.setLoginUser(entity);

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

    protected KnowledgesEntity insertKnowledge(String title, LoginedUser loginedUser) throws Exception {
        KnowledgesEntity entity = new KnowledgesEntity();
        entity.setTitle(title);
        entity.setContent("contens of " + title);
        entity.setTypeId(-100);
        entity.setPublicFlag(KnowledgeLogic.PUBLIC_FLAG_PUBLIC);
        KnowledgeData data = new KnowledgeData();
        data.setKnowledge(entity);
        entity = KnowledgeLogic.get().insert(data, loginedUser);
        return entity;
    }
    
}
