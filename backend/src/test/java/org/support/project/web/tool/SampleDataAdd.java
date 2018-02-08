package org.support.project.web.tool;

import java.io.IOException;
import java.lang.invoke.MethodHandles;

import javax.xml.parsers.ParserConfigurationException;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.di.Container;
import org.support.project.ormapping.common.DBUserPool;
import org.support.project.ormapping.tool.dao.InitializeDao;
import org.support.project.web.dao.RolesDao;
import org.support.project.web.dao.UserRolesDao;
import org.support.project.web.dao.UsersDao;
import org.support.project.web.entity.RolesEntity;
import org.support.project.web.entity.UserRolesEntity;
import org.support.project.web.entity.UsersEntity;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class SampleDataAdd {

    /** ログ */
    private static final Log LOG = LogFactory.getLog(MethodHandles.lookup());

    public static void main(String[] args) throws Exception {
        DBUserPool.get().setUser(-99);
        SampleDataAdd control = new SampleDataAdd();
        control.doInitialize();
    }
    
    public static SampleDataAdd get() {
        return Container.getComp(SampleDataAdd.class);
    }

    public void doInitialize() throws ParserConfigurationException,
            SAXException, IOException {
        // Webの初期化
        InitializeDao dao = InitializeDao.get();
        
        //全テーブル削除
        org.support.project.web.dao.gen.DatabaseControlDao dao2 = new org.support.project.web.dao.gen.DatabaseControlDao();
        dao2.dropAllTable();
        
        dao.initializeDatabase("/org/support/project/web/database/ddl.sql");
        
        RolesEntity adminRole = RolesEntity.get();
        adminRole.setRoleId(1);
        adminRole.setRoleKey("admin");
        adminRole.setRoleName("管理者権限");
        RolesDao.get().insert(adminRole);
        
        RolesEntity userRole = RolesEntity.get();
        userRole.setRoleId(2);
        userRole.setRoleKey("user");
        userRole.setRoleName("一般ユーザ権限");
        RolesDao.get().insert(userRole);

        UsersDao usersDao = UsersDao.get();
        UsersEntity usersEntity = UsersEntity.get();
        usersEntity.setUserId(1);
        usersEntity.setUserKey("a@test.com");
        usersEntity.setPassword("a");
        usersEntity.setUserName("管理者ユーザ");
        usersDao.save(usersEntity);

        UserRolesDao userRolesDao = UserRolesDao.get();
        UserRolesEntity userRolesEntity = UserRolesEntity.get();
        userRolesEntity.setUserId(1);
        userRolesEntity.setRoleId(1);
        userRolesDao.save(userRolesEntity);
        
        /*
        UsersEntity user = UsersEntity.get();
        user.setUserId(2);
        user.setUserKey("u@test.com");
        user.setPassword("u");
        user.setUserName("一般ユーザ");
        usersDao.save(user);

        userRolesEntity = UserRolesEntity.get();
        userRolesEntity.setUserId(2);
        userRolesEntity.setRoleId(2);
        userRolesDao.save(userRolesEntity);

        // ランダムなユーザ情報を登録
        DocumentBuilderFactory factory;
        DocumentBuilder builder;
        Document doc;
        factory = DocumentBuilderFactory.newInstance();
        builder = factory.newDocumentBuilder();
        doc = builder.parse(UsersDaoTest.class
                .getResourceAsStream("/randam_user.xml"));

        NodeList childs = doc.getElementsByTagName("record");
        LOG.debug("Count : " + childs.getLength());

        for (int i = 0; i < childs.getLength(); i++) {
            Node n = childs.item(i);
            Element e = (Element) n;

            usersEntity = UsersEntity.get();
            usersEntity.setUserId(usersDao.getNextId());

            usersEntity.setUserKey(getNodeValue(e, "mail"));
            usersEntity.setPassword(getNodeValue(e, "age"));
            usersEntity.setUserName(getNodeValue(e, "name"));
            usersEntity.setDeleteFlag(INT_FLAG.ON.getValue());
            usersDao.save(usersEntity);

            if (i >= 900) {
                usersDao.delete(usersEntity.getUserId());
            }

            userRolesEntity = UserRolesEntity.get();
            userRolesEntity.setUserId(usersEntity.getUserId());
            userRolesEntity.setRoleId(CommonWebParameter.ROLE_USER);
            userRolesDao.save(userRolesEntity);
        }

        // グループ登録
        GroupsDao groupsDao = GroupsDao.get();
        UserGroupsDao userGroupsDao = UserGroupsDao.get();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/randamu_kana.txt"), "UTF-8"));
            String line;
            int count = 1;
            while ((line = br.readLine()) != null) {
                if (line.trim().length() > 0) {
                    System.out.println(line);
                    
                    if (line.equals("exit")) {
                        break;
                    }
                    
                    GroupsEntity groupsEntity = new GroupsEntity();
                    groupsEntity.setGroupId(groupsDao.getNextId());
                    groupsEntity.setGroupKey("GROUP" + groupsEntity.getGroupId());
                    groupsEntity.setGroupName(line);
                    groupsEntity.setDescription("グループサンプル " + groupsEntity.getGroupId());
                    groupsDao.save(groupsEntity);
                    
                    //グループとユーザのひも付
                    if (count < childs.getLength()) {
                        UserGroupsEntity userGroupsEntity = new UserGroupsEntity(
                                groupsEntity.getGroupId(),
                                count);
                        if (count % 2 == 0) {
                            userGroupsEntity.setGroupRole(GroupRoleType.Manager.getValue());
                        } else {
                            userGroupsEntity.setGroupRole(GroupRoleType.Manager.getValue());
                        }
                        userGroupsDao.save(userGroupsEntity);
                        count++;
                    }
                }
            }
        } finally {
            if (br != null) {
                br.close();
            }
        }
        */
        
        LOG.debug("Initialize finished.");
    }

    private static String getNodeValue(Element e, String tag) {
        Node node = e.getElementsByTagName(tag).item(0);
        String value = node.getFirstChild().getNodeValue();
        return value;
    }

}
