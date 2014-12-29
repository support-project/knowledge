package redcomet.knowledge.dao;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import redcomet.common.log.Log;
import redcomet.common.log.LogFactory;
import redcomet.knowledge.TestCommon;
import redcomet.knowledge.entity.KnowledgesEntity;
import redcomet.knowledge.logic.KnowledgeLogic;
import redcomet.ormapping.common.DBUserPool;

public class KnowledgesDaoTest extends TestCommon {
	/** ログ */
	private static Log LOG = LogFactory.getLog(KnowledgesDaoTest.class);

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		initData();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSelectKnowledges() throws Exception {
		java.util.List<Long> knowledgeIds = new ArrayList<Long>();
		for (int i = 0; i < 10; i++) {
			if (i % 2 == 0) {
				DBUserPool.get().setUser(loginedUser.getUserId());
			} else {
				DBUserPool.get().setUser(loginedUser.getUserId());
			}
			KnowledgesEntity entity = new KnowledgesEntity();
			entity.setTitle("Test-" + i);
			entity.setContent("テスト");
			KnowledgeLogic logic = KnowledgeLogic.get();
			entity = logic.insert(entity, null, new  ArrayList<Long>(), loginedUser);
			knowledgeIds.add(entity.getKnowledgeId());
		}
		KnowledgesDao dao = KnowledgesDao.get();
		List<KnowledgesEntity> knowledgesEntities = dao.selectKnowledges(knowledgeIds);
		for (KnowledgesEntity knowledgesEntity : knowledgesEntities) {
			LOG.info(knowledgesEntity);
			org.junit.Assert.assertNotNull(knowledgesEntity.getInsertUserName());
		}
		
	}

}
