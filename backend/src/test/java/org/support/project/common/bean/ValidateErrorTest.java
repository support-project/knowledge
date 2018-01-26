package org.support.project.common.bean;

import static org.junit.Assert.assertEquals;

import java.util.Locale;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.support.project.common.config.Resources;

public class ValidateErrorTest {

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
	public void testValidateErrorIntegerStringStringArray() {
		ValidateError validateError = new ValidateError("errors.invalid", "Hoge");
		Resources resources = Resources.getInstance(Locale.JAPAN);
		assertEquals(resources.getResource("errors.invalid", "Hoge"), validateError.getMsg(Locale.JAPAN));
	}

	@Test
	public void testValidateErrorStringStringArray() {
		ValidateError validateError = new ValidateError(1, "errors.invalid", "Hoge");
		Resources resources = Resources.getInstance(Locale.JAPAN);
		assertEquals(resources.getResource("errors.invalid", "Hoge"), validateError.getMsg(Locale.JAPAN));
		assertEquals(1, validateError.getLevel().intValue());
	}

}
