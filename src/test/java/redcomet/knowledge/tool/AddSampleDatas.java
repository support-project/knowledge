package redcomet.knowledge.tool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.TimeZone;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import redcomet.common.config.INT_FLAG;
import redcomet.common.log.Log;
import redcomet.common.log.LogFactory;
import redcomet.knowledge.deploy.InitDB;
import redcomet.ormapping.common.DBUserPool;
import redcomet.ormapping.tool.dao.InitializeDao;
import redcomet.web.config.GroupRoleType;
import redcomet.web.dao.GroupsDao;
import redcomet.web.dao.UserGroupsDao;
import redcomet.web.dao.UserRolesDao;
import redcomet.web.dao.UsersDao;
import redcomet.web.entity.GroupsEntity;
import redcomet.web.entity.UserGroupsEntity;
import redcomet.web.entity.UserRolesEntity;
import redcomet.web.entity.UsersEntity;

public class AddSampleDatas {

	/** ログ */
	private static Log LOG = LogFactory.getLog(AddSampleDatas.class);

	public static void main(String[] args) throws Exception {
		// 内部的には、日付はGMTとして扱う
		TimeZone zone = TimeZone.getTimeZone("GMT");
		TimeZone.setDefault(zone);

		DBUserPool.get().setUser(2);
		AddSampleDatas addSampleDatas = new AddSampleDatas();
		addSampleDatas.createTables();
		
		InitDB.main(args);
		
		addSampleDatas.doInitialize();
		
		AddSampleKnowledge.main(args);
	}
	
	public void createTables() {
		//存在するテーブルを全て削除
		InitializeDao initializeDao = InitializeDao.get();
		initializeDao.dropAllTable();
		
//		String[] sqlpaths = {
//				"/redcomet/web/database/ddl.sql",
//				"/redcomet/knowledge/database/ddl.sql"
//		};
//		initializeDao.initializeDatabase(sqlpaths);
	}

	
	public void doInitialize() throws ParserConfigurationException,
			SAXException, IOException {
//		RolesEntity adminRole = RolesEntity.get();
//		adminRole.setRoleId(1);
//		adminRole.setRoleKey("admin");
//		adminRole.setRoleName("管理者権限");
//		RolesDao.get().insert(adminRole);
//		
//		RolesEntity userRole = RolesEntity.get();
//		userRole.setRoleId(2);
//		userRole.setRoleKey("user");
//		userRole.setRoleName("一般ユーザ権限");
//		RolesDao.get().insert(userRole);
//
		UsersDao usersDao = UsersDao.get();
		UsersEntity usersEntity = UsersEntity.get();
//		usersEntity.setUserId(1);
//		usersEntity.setUserKey("admin@test.com");
//		usersEntity.setPassword("admin");
//		usersEntity.setUserName("管理者ユーザ");
//		usersDao.save(usersEntity);
//
		UserRolesDao userRolesDao = UserRolesDao.get();
		UserRolesEntity userRolesEntity = UserRolesEntity.get();
//		userRolesEntity.setUserId(1);
//		userRolesEntity.setRoleId(1);
//		userRolesDao.save(userRolesEntity);
		
		UsersEntity user = UsersEntity.get();
		user.setUserId(2);
		user.setUserKey("user@test.com");
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
		doc = builder.parse(this.getClass()
				.getResourceAsStream("/randam_user.xml"));

		NodeList childs = doc.getElementsByTagName("record");
		LOG.debug("Count : " + childs.getLength());

		for (int i = 0; i < 50; i++) {
//		for (int i = 0; i < childs.getLength(); i++) {
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
			userRolesEntity.setRoleId(2);
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
				if (count > 50) {
					break;
				}
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
		
		LOG.debug("Initialize finished.");
	}

	private static String getNodeValue(Element e, String tag) {
		Node node = e.getElementsByTagName(tag).item(0);
		String value = node.getFirstChild().getNodeValue();
		return value;
	}
	
}
