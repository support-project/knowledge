package org.support.project.common.exception;

import static org.junit.Assert.*;

import java.util.Locale;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.support.project.common.config.Resources;

public class SerializeExceptionTest {

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
	public void testSerializeExceptionResourcesStringStringArray() {
		SerializeException exception = new SerializeException(Resources.getInstance(Locale.getDefault()), "errors.integer", "AAA");
		Resources resources = Resources.getInstance(Locale.getDefault());
		assertEquals(resources.getResource("errors.integer", "AAA"), exception.getMessage());
	}

	@Test
	public void testSerializeExceptionResourcesStringThrowableStringArray() {
		SerializeException exception = new SerializeException(Resources.getInstance(Locale.getDefault()), "errors.integer", new Exception(), "AAA");
		Resources resources = Resources.getInstance(Locale.getDefault());
		assertEquals(resources.getResource("errors.integer", "AAA"), exception.getMessage());
		assertEquals(Exception.class.getName(), exception.getCause().getClass().getName());
	}

	@Test
	public void testSerializeExceptionResourcesStringThrowable() {
		SerializeException exception = new SerializeException(Resources.getInstance(Locale.getDefault()), "errors.prefix", new Exception());
		Resources resources = Resources.getInstance(Locale.getDefault());
		assertEquals(resources.getResource("errors.prefix"), exception.getMessage());
		assertEquals(Exception.class.getName(), exception.getCause().getClass().getName());
	}

	@Test
	public void testSerializeExceptionResourcesString() {
		SerializeException exception = new SerializeException(Resources.getInstance(Locale.getDefault()), "errors.prefix");
		Resources resources = Resources.getInstance(Locale.getDefault());
		assertEquals(resources.getResource("errors.prefix"), exception.getMessage());
	}


	@Test
	public void testSerializeExceptionStringStringArray() {
		SerializeException exception = new SerializeException("errors.required", "123");
		Resources resources = Resources.getInstance(Locale.getDefault());
		assertEquals(resources.getResource("errors.required", "123"), exception.getMessage());
	}

	@Test
	public void testSerializeExceptionStringThrowableStringArray() {
		SerializeException exception = new SerializeException("errors.required", new Exception(), "123");
		Resources resources = Resources.getInstance(Locale.getDefault());
		assertEquals(resources.getResource("errors.required", "123"), exception.getMessage());
		assertEquals(Exception.class.getName(), exception.getCause().getClass().getName());
	}

	@Test
	public void testSerializeExceptionStringThrowable() {
		SerializeException exception = new SerializeException("123", new Exception());
		assertEquals("123", exception.getMessage());
		assertEquals(Exception.class.getName(), exception.getCause().getClass().getName());
	}

	@Test
	public void testSerializeExceptionString() {
		SerializeException exception = new SerializeException("123");
		assertEquals("123", exception.getMessage());
	}

	@Test
	public void testSerializeExceptionThrowable() {
		SerializeException exception = new SerializeException(new Exception());
		assertEquals(Exception.class.getName(), exception.getCause().getClass().getName());
	}

	@Test
	public void testSerializeException() {
		SerializeException exception = new SerializeException();
		assertEquals("org.support.project.common.exception.SerializeException", exception.getMessage());
	}

}
