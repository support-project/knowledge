package org.support.project.knowledge.tool;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.invoke.MethodHandles;
import java.nio.charset.Charset;
import java.util.List;

import org.support.project.common.config.ConfigLoader;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.FileUtil;
import org.support.project.common.util.RandomUtil;
import org.support.project.common.util.StringUtils;
import org.support.project.knowledge.config.AppConfig;
import org.support.project.knowledge.entity.KnowledgesEntity;
import org.support.project.knowledge.entity.TagsEntity;
import org.support.project.knowledge.logic.KnowledgeLogic;
import org.support.project.knowledge.vo.KnowledgeData;
import org.support.project.ormapping.common.DBUserPool;
import org.support.project.web.bean.AccessUser;
import org.support.project.web.dao.RolesDao;
import org.support.project.web.dao.UsersDao;
import org.support.project.web.entity.RolesEntity;
import org.support.project.web.entity.UsersEntity;

public class AddSampleKnowledge {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(MethodHandles.lookup());

    // private static final String userKey = "user@test.com";
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
                    String[] sp = line.split("。");
                    StringBuilder builder = new StringBuilder();
                    for (int i = 0; i < sp.length; i++) {
                        builder.append("- ").append(sp[i]).append("\n");
                    }
                    addSample(builder.toString());
                }
            }
        } finally {
            if (br != null) {
                br.close();
            }
            if (fr != null) {
                fr.close();
            }
        }

    }

    private int count = 0;

    private void addSample(String line) throws Exception {
        // UsersEntity usersEntity = usersDao.selectOnUserKey(userKey);
        UsersEntity usersEntity = users.get(RandomUtil.randamNum(1, users.size() - 2));
        DBUserPool.get().setUser(usersEntity.getUserId());

        RolesDao rolesDao = RolesDao.get();
        List<RolesEntity> rolesEntities = rolesDao.selectOnUserKey(usersEntity.getUserKey());

        AccessUser loginedUser = new AccessUser();
        loginedUser.setUserInfomation(usersEntity);
        loginedUser.setRoles(rolesEntities);

        KnowledgesEntity entity = new KnowledgesEntity();
        entity.setTitle("sample-" + org.apache.commons.lang.StringUtils.abbreviate(line, 50));
        entity.setContent(line);
        String tags = "";
        if (count % 5 == 0) {
            entity.setPublicFlag(KnowledgeLogic.PUBLIC_FLAG_PUBLIC);
            tags = "テスト,サンプル1,データ" + count;
        } else if (count % 9 == 0) {
            entity.setPublicFlag(KnowledgeLogic.PUBLIC_FLAG_PUBLIC);
            tags = "サンプル,タグ2,データ" + count;
        } else if (count % 13 == 0) {
            entity.setPublicFlag(KnowledgeLogic.PUBLIC_FLAG_PUBLIC);
            tags = "サンプル,タグ3,タグ4,テスト,データ" + count;
        } else if (count % 7 == 0) {
            entity.setPublicFlag(KnowledgeLogic.PUBLIC_FLAG_PRIVATE);
            tags = "サンプルデータ,タグ1,データ" + count;
        } else if (count % 8 == 0) {
            entity.setPublicFlag(KnowledgeLogic.PUBLIC_FLAG_PRIVATE);
            tags = "テスト,サンプルデータ,テスト1,タグ2,データ" + count;
        } else {
            entity.setPublicFlag(KnowledgeLogic.PUBLIC_FLAG_PRIVATE);
        }
        List<TagsEntity> tagList = KnowledgeLogic.get().manegeTags(tags);
        
        KnowledgeData data = new KnowledgeData();
        data.setKnowledge(entity);
        data.setTags(tagList);
        
        KnowledgeLogic.get().insert(data, loginedUser, false);

        LOG.info("サンプル登録 :" + org.apache.commons.lang.StringUtils.abbreviate(line, 50));
        count++;

    }

}
