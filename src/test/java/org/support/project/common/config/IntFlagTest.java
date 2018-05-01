package org.support.project.common.config;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class IntFlagTest {

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
	public void testGetValue() {
		assertEquals(1, INT_FLAG.ON.getValue());
	}

	@Test
	public void testGetType() {
		assertEquals(INT_FLAG.ON, INT_FLAG.getType(1));
	}

}
