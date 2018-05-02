package org.support.project.common.exception;

import static org.junit.Assert.*;

import java.util.Locale;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.support.project.common.config.Resources;

public class NotImplementedExceptionTest {

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
	public void testNotImplementedExceptionResourcesStringStringArray() {
		NotImplementedException exception = new NotImplementedException(Resources.getInstance(Locale.getDefault()), "errors.integer", "AAA");
		Resources resources = Resources.getInstance(Locale.getDefault());
		assertEquals(resources.getResource("errors.integer", "AAA"), exception.getMessage());
	}

	@Test
	public void testNotImplementedExceptionResourcesStringThrowableStringArray() {
		NotImplementedException exception = new NotImplementedException(Resources.getInstance(Locale.getDefault()), "errors.integer", new Exception(), "AAA");
		Resources resources = Resources.getInstance(Locale.getDefault());
		assertEquals(resources.getResource("errors.integer", "AAA"), exception.getMessage());
		assertEquals(Exception.class.getName(), exception.getCause().getClass().getName());
	}

	@Test
	public void testNotImplementedExceptionResourcesStringThrowable() {
		NotImplementedException exception = new NotImplementedException(Resources.getInstance(Locale.getDefault()), "errors.prefix", new Exception());
		Resources resources = Resources.getInstance(Locale.getDefault());
		assertEquals(resources.getResource("errors.prefix"), exception.getMessage());
		assertEquals(Exception.class.getName(), exception.getCause().getClass().getName());
	}

	@Test
	public void testNotImplementedExceptionResourcesString() {
		NotImplementedException exception = new NotImplementedException(Resources.getInstance(Locale.getDefault()), "errors.prefix");
		Resources resources = Resources.getInstance(Locale.getDefault());
		assertEquals(resources.getResource("errors.prefix"), exception.getMessage());
	}


	@Test
	public void testNotImplementedExceptionStringStringArray() {
		NotImplementedException exception = new NotImplementedException("errors.required", "123");
		Resources resources = Resources.getInstance(Locale.getDefault());
		assertEquals(resources.getResource("errors.required", "123"), exception.getMessage());
	}

	@Test
	public void testNotImplementedExceptionStringThrowableStringArray() {
		NotImplementedException exception = new NotImplementedException("errors.required", new Exception(), "123");
		Resources resources = Resources.getInstance(Locale.getDefault());
		assertEquals(resources.getResource("errors.required", "123"), exception.getMessage());
		assertEquals(Exception.class.getName(), exception.getCause().getClass().getName());
	}

	@Test
	public void testNotImplementedExceptionStringThrowable() {
		NotImplementedException exception = new NotImplementedException("123", new Exception());
		assertEquals("123", exception.getMessage());
		assertEquals(Exception.class.getName(), exception.getCause().getClass().getName());
	}

	@Test
	public void testNotImplementedExceptionString() {
		NotImplementedException exception = new NotImplementedException("123");
		assertEquals("123", exception.getMessage());
	}

	@Test
	public void testNotImplementedExceptionThrowable() {
		NotImplementedException exception = new NotImplementedException(new Exception());
		assertEquals(Exception.class.getName(), exception.getCause().getClass().getName());
	}

	@Test
	public void testNotImplementedException() {
		NotImplementedException exception = new NotImplementedException();
		assertEquals("org.support.project.common.exception.NotImplementedException", exception.getMessage());
	}

}
