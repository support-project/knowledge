package org.support.project.common.util;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class StringJoinBuilderTest {

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
	public void testJoin() {
		StringJoinBuilder builder = new StringJoinBuilder();
		builder.append("1");
		builder.append("2");
		builder.append("3");
		String str = builder.join(",");
		assertEquals("1,2,3", str);
	}

}
