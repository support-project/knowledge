package redcomet.knowledge.tool;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import redcomet.common.config.ConfigLoader;
import redcomet.common.log.Log;
import redcomet.common.log.LogFactory;
import redcomet.common.util.FileUtil;
import redcomet.common.util.RandomUtil;
import redcomet.common.util.StringUtils;
import redcomet.knowledge.config.AppConfig;
import redcomet.knowledge.entity.KnowledgesEntity;
import redcomet.knowledge.entity.TagsEntity;
import redcomet.knowledge.logic.KnowledgeLogic;
import redcomet.web.bean.LoginedUser;
import redcomet.web.dao.RolesDao;
import redcomet.web.dao.UsersDao;
import redcomet.web.entity.RolesEntity;
import redcomet.web.entity.UsersEntity;

public class AddSampleKnowledge {
	/** ログ */
	private static Log LOG = LogFactory.getLog(AddSampleKnowledge.class);
	
	//private static final String userKey = "user@test.com";
	private UsersDao usersDao = UsersDao.get();
	private List<UsersEntity> users;

	
	public static void main(String[] args) throws Exception {
		AddSampleKnowledge addSampleKnowledge = new AddSampleKnowledge();
		
		addSampleKnowledge.crearIndex();
		addSampleKnowledge.start();
	}

	private void crearIndex() {
		AppConfig appConfig = ConfigLoader.load(AppConfig.APP_CONFIG, AppConfig.class);
		File indexDir = new File(appConfig.getIndexPath());
		FileUtil.delete(indexDir);
	}

	private void start() throws Exception {
		users = usersDao.selectAll();

		Reader fr = null;
		BufferedReader br = null;
		try {
			fr = new InputStreamReader(AddSampleKnowledge.class.getResourceAsStream("/dummy_text.txt"), Charset.forName("UTF-8"));
			br = new BufferedReader(fr);

			String line;
			while ((line = br.readLine()) != null) {
				if (StringUtils.isNotEmpty(line)) {
					addSample(line);
				}
			}
		} finally {
			br.close();
			fr.close();
		}
		
	}

	private int count = 0;
	private void addSample(String line) throws Exception {
		//UsersEntity usersEntity = usersDao.selectOnUserKey(userKey);
		UsersEntity usersEntity = users.get(RandomUtil.randamNum(1, users.size() - 2));
		RolesDao rolesDao = RolesDao.get();
		List<RolesEntity> rolesEntities = rolesDao.selectOnUserKey(usersEntity.getUserKey());
		
		LoginedUser loginedUser = new LoginedUser();
		loginedUser.setLoginUser(usersEntity);
		loginedUser.setRoles(rolesEntities);
		
		KnowledgesEntity entity = new KnowledgesEntity();
		entity.setTitle("sample-" + org.apache.commons.lang.StringUtils.abbreviate(line, 50));
		entity.setContent(line);
		String tags = "";
		if (count % 5 == 0) {
			entity.setPublicFlag(KnowledgeLogic.PUBLIC_FLAG_PUBLIC);
			tags = "テスト";
		} else if (count % 7 == 0) {
			entity.setPublicFlag(KnowledgeLogic.PUBLIC_FLAG_PRIVATE);
			tags = "サンプルデータ,タグ1";
		} else if (count % 8 == 0) {
			entity.setPublicFlag(KnowledgeLogic.PUBLIC_FLAG_PRIVATE);
			tags = "テスト,サンプルデータ,タグ1,タグ2";
		} else {
			entity.setPublicFlag(KnowledgeLogic.PUBLIC_FLAG_PRIVATE);
		}
		List<TagsEntity> tagList = KnowledgeLogic.get().manegeTags(tags);
		KnowledgeLogic.get().insert(entity, tagList, new ArrayList<Long>(), loginedUser);
		
		LOG.info("サンプル登録 :" + org.apache.commons.lang.StringUtils.abbreviate(line, 50));
		count++;
		
	}

}
