package org.support.project.common.util;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PropertyComparatorTtest {

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
	public void testCompare() {
		TestClass1 o1 = new TestClass1();
		o1.setNum(1);
		o1.setStr("aaa");
		
		TestClass1 o2 = new TestClass1();
		o2.setNum(2);
		o2.setStr("aaa");
		
		PropertyComparator<TestClass1> comparator = new PropertyComparator<>("num", "str");
		int result = comparator.compare(o1, o2);
		assertEquals(-1, result);
		
		o2.setNum(1);
		result = comparator.compare(o1, o2);
		assertEquals(0, result);
		
		o1.setStr("bbb");
		result = comparator.compare(o1, o2);
		assertEquals(1, result);
		
		result = comparator.compare(null, o2);
		assertEquals(1, result);
		
		result = comparator.compare(o1, null);
		assertEquals(-1, result);
		
		result = comparator.compare(null, null);
		assertEquals(0, result);
	}

}
