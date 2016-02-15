package org.support.project.knowledge.dao;

import static org.junit.Assert.assertNotNull;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.support.project.common.logic.H2DBServerLogic;
import org.support.project.common.util.RandomUtil;
import org.support.project.knowledge.entity.AccountImagesEntity;

public class AccountImagesDaoTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		H2DBServerLogic.get().start();
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
	public void testSelectOnUserId() throws FileNotFoundException, IOException {
		try {
			AccountImagesEntity entity = new AccountImagesEntity();
			entity.setUserId(RandomUtil.randamNum(10000, 60000));
			entity.setFileBinary(getClass().getResourceAsStream("/appresource_ja.properties"));
			
			AccountImagesDao dao = AccountImagesDao.get();
			entity = dao.insert(entity);
			
			assertNotNull(entity.getImageId());
			
			
			entity = dao.selectOnUserId(entity.getUserId());
			
//			FileUtil.copy(entity.getFileBinary(), new FileOutputStream(new File("data/test")));
//			FileUtil.delete(new File("data/test"));
			
			assertNotNull(entity.getImageId());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}

}
