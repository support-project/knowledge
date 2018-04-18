package org.support.project.knowledge.logic;

import static org.support.project.common.test.AssertEx.eqdb;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.test.Order;
import org.support.project.common.test.OrderedRunner;
import org.support.project.common.util.RandomUtil;
import org.support.project.knowledge.TestCommon;
import org.support.project.knowledge.dao.KnowledgesDao;
import org.support.project.knowledge.entity.KnowledgesEntity;
import org.support.project.knowledge.vo.KnowledgeData;
import org.support.project.ormapping.common.DBUserPool;

import net.arnx.jsonic.JSON;

@RunWith(OrderedRunner.class)
public class KnowledgeLogicTest extends TestCommon {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(MethodHandles.lookup());

    private static List<KnowledgesEntity> list = new ArrayList<>();

    @Test
    @Order(order = 1)
    public void testInsert() throws Exception {
        DBUserPool.get().setUser(loginedUser.getUserId());

        KnowledgesEntity entity = new KnowledgesEntity();
        entity.setTitle("Test1");
        entity.setContent("テストだよ");
        entity.setTypeId(-100);
        KnowledgeLogic logic = KnowledgeLogic.get();
        
        KnowledgeData data = new KnowledgeData();
        data.setKnowledge(entity);
        
        entity = logic.insert(data, loginedUser, false);

        // publicFlagは指定しないでDBに保存すると、公開になる
        entity.setPublicFlag(KnowledgeLogic.PUBLIC_FLAG_PUBLIC);

        KnowledgesEntity saved = KnowledgesDao.get().selectOnKey(entity.getKnowledgeId());
        eqdb(entity, saved);

        entity.setInsertUserName(loginedUser.getUserInfomation().getUserName());
        entity.setUpdateUserName(loginedUser.getUserInfomation().getUserName());
        saved = KnowledgesDao.get().selectOnKeyWithUserName(entity.getKnowledgeId());
        eqdb(entity, saved);
        entity.setLikeCount((long) 0);
        list.add(entity);
    }

    @Test
    @Order(order = 2)
    public void testUpdate() throws Exception {
        DBUserPool.get().setUser(loginedUser2.getUserId());

        KnowledgesEntity entity = new KnowledgesEntity();
        entity.setTitle(RandomUtil.randamGen(64));
        entity.setContent(RandomUtil.randamGen(1024));
        entity.setTypeId(-99);
        KnowledgeLogic logic = KnowledgeLogic.get();
        
        KnowledgeData data = new KnowledgeData();
        data.setKnowledge(entity);
        
        entity = logic.insert(data, loginedUser2, false);
        // publicFlagは指定しないでDBに保存すると、公開になる
        entity.setPublicFlag(KnowledgeLogic.PUBLIC_FLAG_PUBLIC);
        KnowledgesEntity saved = KnowledgesDao.get().selectOnKey(entity.getKnowledgeId());
        eqdb(entity, saved);

        entity.setTitle(RandomUtil.randamGen(64));
        entity.setContent(RandomUtil.randamGen(1024));
        entity.setPublicFlag(KnowledgeLogic.PUBLIC_FLAG_PRIVATE);
        logic.update(data, loginedUser2, false);

        saved = KnowledgesDao.get().selectOnKeyWithUserName(entity.getKnowledgeId());
        entity.setInsertUserName(loginedUser2.getUserInfomation().getUserName());
        entity.setUpdateUserName(loginedUser2.getUserInfomation().getUserName());
        eqdb(entity, saved);
        entity.setLikeCount((long) 0);
        list.add(entity);
    }

    @Test
    @Order(order = 3)
    public void testSearchKnowledge() throws Exception {
        // KnowledgesEntity entity = new KnowledgesEntity();
        // entity.setKnowledgeId(1);
        // entity.setTitle("Test1");
        // entity.setContent("テストだよ");
        // entity.setPublicFlag(KnowledgeLogic.PUBLIC_FLAG_PUBLIC);
        // entity.setDeleteFlag(INT_FLAG.OFF.getValue());
        List<KnowledgesEntity> checks = new ArrayList<KnowledgesEntity>();
        checks.add(list.get(0));
        List<String> ignores = new ArrayList<>();
        ignores.add("score"); // スコアは、Luceneが算出する値なのでテストしない（Luceneの実装が変化すると値が変わるので）

        KnowledgeLogic logic = KnowledgeLogic.get();
        List<KnowledgesEntity> entities = logic.searchKnowledge(null, loginedUser, 0, 100).getItems();
        LOG.info(JSON.encode(entities, true));
        eqdb(checks, entities, ignores);

        checks = new ArrayList<KnowledgesEntity>();
        list.get(1).setContent(list.get(1).getContent());
        checks.add(list.get(1)); // スコア上
        checks.add(list.get(0));

        entities = logic.searchKnowledge(null, loginedUser2, 0, 100).getItems();
        LOG.info(JSON.encode(entities, true));
        eqdb(checks, entities, ignores);

        checks = new ArrayList<KnowledgesEntity>();
        checks.add(list.get(0));

        entities = logic.searchKnowledge("テスト", loginedUser2, 0, 100).getItems();
        list.get(0).setContent("<span class=\"mark\">テスト</span>だよ");

        LOG.info(JSON.encode(entities, true));
        eqdb(checks, entities, ignores);

    }

}
