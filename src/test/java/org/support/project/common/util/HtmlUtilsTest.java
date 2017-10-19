package org.support.project.common.util;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.support.project.common.exception.ParseException;

public class HtmlUtilsTest {

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
	public void test() {
		String str = HtmlUtils.encode("<div style=\"aaa\">aaa</div>");
		assertEquals("&lt;div style=&quot;aaa&quot;&gt;aaa&lt;/div&gt;", str);
	}
	@Test
	public void test2() {
		String str = HtmlUtils.escapeHTML("<div style=\"aaa\">aaa</div>");
		assertEquals("&lt;div style=&quot;aaa&quot;&gt;aaa&lt;/div&gt;", str);
	}
	@Test
	public void test3() throws ParseException {
		String str = HtmlUtils.escapeURL("/&:; あ");
		assertEquals("%2F%26%3A%3B+%E3%81%82", str);
	}
}
