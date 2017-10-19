package org.support.project.common.exception;

import static org.junit.Assert.*;

import java.util.Locale;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.support.project.common.config.Resources;

public class ArgumentExceptionTest {

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
    public void testArgumentExceptionResourcesStringStringArray() {
        ArgumentException exception = new ArgumentException(Resources.getInstance(Locale.getDefault()), "errors.integer", "AAA");
        Resources resources = Resources.getInstance(Locale.getDefault());
        assertEquals(resources.getResource("errors.integer", "AAA"), exception.getLocalizedMessage());
    }

    @Test
    public void testArgumentExceptionResourcesStringThrowableStringArray() {
        ArgumentException exception = new ArgumentException(Resources.getInstance(Locale.getDefault()), "errors.integer", new Exception(), "AAA");
        Resources resources = Resources.getInstance(Locale.getDefault());
        assertEquals(resources.getResource("errors.integer", "AAA"), exception.getMessage());
        assertEquals(Exception.class.getName(), exception.getCause().getClass().getName());
    }

    @Test
    public void testArgumentExceptionResourcesStringThrowable() {
        ArgumentException exception = new ArgumentException(Resources.getInstance(Locale.getDefault()), "errors.prefix", new Exception());
        Resources resources = Resources.getInstance(Locale.getDefault());
        assertEquals(resources.getResource("errors.prefix"), exception.getMessage());
        assertEquals(Exception.class.getName(), exception.getCause().getClass().getName());
    }

    @Test
    public void testArgumentExceptionResourcesString() {
        ArgumentException exception = new ArgumentException(Resources.getInstance(Locale.getDefault()), "errors.prefix");
        Resources resources = Resources.getInstance(Locale.getDefault());
        assertEquals(resources.getResource("errors.prefix"), exception.getMessage());
    }

    @Test
    public void testArgumentExceptionStringStringArray() {
        ArgumentException exception = new ArgumentException("errors.required", "123");
        Resources resources = Resources.getInstance(Locale.getDefault());
        assertEquals(resources.getResource("errors.required", "123"), exception.getMessage());
    }

    @Test
    public void testArgumentExceptionStringThrowableStringArray() {
        ArgumentException exception = new ArgumentException("errors.required", new Exception(), "123");
        Resources resources = Resources.getInstance(Locale.getDefault());
        assertEquals(resources.getResource("errors.required", "123"), exception.getMessage());
        assertEquals(Exception.class.getName(), exception.getCause().getClass().getName());
    }

    @Test
    public void testArgumentExceptionStringThrowable() {
        ArgumentException exception = new ArgumentException("123", new Exception());
        assertEquals("123", exception.getMessage());
        assertEquals(Exception.class.getName(), exception.getCause().getClass().getName());
    }

    @Test
    public void testArgumentExceptionString() {
        ArgumentException exception = new ArgumentException("123");
        assertEquals("123", exception.getMessage());
    }

    @Test
    public void testArgumentExceptionThrowable() {
        ArgumentException exception = new ArgumentException(new Exception());
        assertEquals(Exception.class.getName(), exception.getCause().getClass().getName());
    }

    @Test
    public void testArgumentException() {
        ArgumentException exception = new ArgumentException();
        assertEquals("org.support.project.common.exception.ArgumentException", exception.getMessage());
    }

}
