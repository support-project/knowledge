package org.support.project.knowledge.dao;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.logic.H2DBServerLogic;
import org.support.project.knowledge.TestCommon;
import org.support.project.knowledge.entity.KnowledgesEntity;
import org.support.project.knowledge.entity.LikesEntity;
import org.support.project.knowledge.logic.KnowledgeLogic;
import org.support.project.ormapping.common.DBUserPool;

public class KnowledgesDaoTest extends TestCommon {
	/** ログ */
	private static Log LOG = LogFactory.getLog(KnowledgesDaoTest.class);

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		H2DBServerLogic.get().start();
		initData();
	}
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		H2DBServerLogic.get().stop();
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
			entity = logic.insert(entity, null, new  ArrayList<Long>(), null, null, null, loginedUser);
			knowledgeIds.add(entity.getKnowledgeId());
		}
		KnowledgesDao dao = KnowledgesDao.get();
		List<KnowledgesEntity> knowledgesEntities = dao.selectKnowledges(knowledgeIds);
		for (KnowledgesEntity knowledgesEntity : knowledgesEntities) {
			LOG.info(knowledgesEntity);
			org.junit.Assert.assertNotNull(knowledgesEntity.getInsertUserName());
		}
		
	}
	
	
	@Test
	public void testSelectPopularity() throws Exception {
		// テストデータ登録
		List<KnowledgesEntity> knowledge = new ArrayList<>();
		for (int i = 0; i < 30; i++) {
			KnowledgesEntity entity = new KnowledgesEntity();
			entity.setTitle("Test-" + i);
			entity.setContent("テスト");
			KnowledgeLogic logic = KnowledgeLogic.get();
			entity = logic.insert(entity, null, new  ArrayList<Long>(), null, null, null, loginedUser);
			knowledge.add(entity);
			
			for (int j = 0; j < i; j++) {
				LikesEntity like = new LikesEntity();
				like.setKnowledgeId(entity.getKnowledgeId());
				LikesDao.get().save(like);
			}
		}
		
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, -1);
		Timestamp start = new Timestamp(c.getTimeInMillis());
		LOG.info(new SimpleDateFormat("YYYY-MM-dd hh:mm").format(start));
		
		c = Calendar.getInstance();
		c.add(Calendar.DATE, 1);
		Timestamp end = new Timestamp(c.getTimeInMillis());
		LOG.info(new SimpleDateFormat("YYYY-MM-dd hh:mm").format(end));
		
		List<KnowledgesEntity> list = KnowledgesDao.get().selectPopularity(start, end, 0, 20);
		
		for (KnowledgesEntity knowledgesEntity : list) {
			LOG.info(knowledgesEntity.getTitle() + ":" + knowledgesEntity.getLikeCountOnTerm());
		}
		Assert.assertEquals("Test-29", list.get(0).getTitle());
	}
	
	

	@Test
	public void testSelectPopularityWithAccessControl() throws Exception {
		// テストデータ登録
		List<KnowledgesEntity> knowledge = new ArrayList<>();
		for (int i = 0; i < 30; i++) {
			KnowledgesEntity entity = new KnowledgesEntity();
			entity.setTitle("Test-" + i);
			entity.setContent("テスト");
			entity.setPublicFlag(0);
			KnowledgeLogic logic = KnowledgeLogic.get();
			entity = logic.insert(entity, null, new  ArrayList<Long>(), null, null, null, loginedUser);
			knowledge.add(entity);
			
			for (int j = 0; j < i; j++) {
				LikesEntity like = new LikesEntity();
				like.setKnowledgeId(entity.getKnowledgeId());
				LikesDao.get().save(like);
			}
		}
		
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, -1);
		Timestamp start = new Timestamp(c.getTimeInMillis());
		LOG.info(new SimpleDateFormat("YYYY-MM-dd hh:mm").format(start));
		
		c = Calendar.getInstance();
		c.add(Calendar.DATE, 1);
		Timestamp end = new Timestamp(c.getTimeInMillis());
		LOG.info(new SimpleDateFormat("YYYY-MM-dd hh:mm").format(end));
		
		List<KnowledgesEntity> list = KnowledgesDao.get().selectPopularityWithAccessControl(loginedUser, start, end, 0, 20);
		
		for (KnowledgesEntity knowledgesEntity : list) {
			LOG.info(knowledgesEntity.getTitle() + ":" + knowledgesEntity.getLikeCountOnTerm());
		}
		Assert.assertEquals("Test-29", list.get(0).getTitle());
	}
	
	

}
