package org.support.project.ormapping.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.support.project.common.logic.H2DBServerLogic;
import org.support.project.di.Container;
import org.support.project.ormapping.gen.dao.CampanyDao;
import org.support.project.ormapping.gen.entity.CampanyEntity;

/**
 * test for CampanyDao
 * @author Koda
 *
 */
public class CampanyDaoTest {
    // @Before・@Afterを付けたメソッドが、各テストメソッドの実行前後に呼ばれる。
    // @BeforeClass・@AfterClassを付けたメソッドが、全テストの実行前後に呼ばれる。

    /**
     * setUpBeforeClass
     * @throws Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        DatabaseInitialization.setUp();
    }

    /**
     * tearDownAfterClass
     * @throws Exception
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        // H2DBServerLogic.get().stop();
    }
    /**
     * test
     */
    @Test
    public void testDao() {
        CampanyDao campanyDao = Container.getComp(CampanyDao.class);

        dataclear(campanyDao);

        CampanyEntity entity = new CampanyEntity();
        entity.setCampanyCode("Company01");
        entity.setCampanyName("会社1");
        entity.setAdress("住所");
        campanyDao.insert(entity);

        List<CampanyEntity> campanyList = campanyDao.selectAll();
        assertEquals(1, campanyList.size());

        dataclear(campanyDao);
    }
    /**
     * company data clear 
     * @param campanyDao
     */
    private void dataclear(CampanyDao campanyDao) {
        List<CampanyEntity> campanyList = campanyDao.selectAll();
        for (CampanyEntity campanyEntity : campanyList) {
            campanyDao.delete(campanyEntity);
        }
    }

}
