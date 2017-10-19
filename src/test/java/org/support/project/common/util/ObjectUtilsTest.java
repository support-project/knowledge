package org.support.project.common.util;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ObjectUtilsTest {

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
	public void testGetMethod() {
		Method m1 = ObjectUtils.getMethod(TestClass2.class, "getD", null);
		assertNotNull(m1);
		
		Method m2 = ObjectUtils.getMethod(TestClass2.class, "getStr", null);
		assertNotNull(m2);
	}

	@Test
	public void testInvokeObjectStringObjectArray() {
		TestClass2 obj = new TestClass2();
		ObjectUtils.invoke(obj, "setD", (double) 2);
		Object d = ObjectUtils.invoke(obj, "getD", null);
		assertNotNull(d);
		assertEquals((double) 2, d);
	}

	@Test
	public void testInvokeObjectMethodObjectArray() {
		TestClass2 obj = new TestClass2();
		obj.setD((double) 2);

		Method m1 = ObjectUtils.getMethod(TestClass2.class, "getD", null);
		assertNotNull(m1);
		
		Object d = ObjectUtils.invoke(obj, m1, null);
		assertNotNull(d);
		assertEquals((double) 2, d);
	}

	@Test
	public void testGetFieldObject() {
		TestClass2 obj = new TestClass2();
		obj.setD((double) 2);
		Object d = ObjectUtils.getFieldObject(obj, "d");
		assertNotNull(d);
		assertEquals((double) 2, d);
	}

}
