package org.support.project.common.util;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class RandomUtilTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
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
	public void testRandamGen() {
		String str = RandomUtil.randamGen(16);
		assertEquals(16, str.length());
	}

	@Test
	public void testRandamNum() {
		int num = RandomUtil.randamNum(3, 10);
		assertTrue(num >= 3);
		assertTrue(num <= 10);
	}

}
