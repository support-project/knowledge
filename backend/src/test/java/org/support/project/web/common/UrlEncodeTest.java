package org.support.project.web.common;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.junit.Test;

public class UrlEncodeTest {

	@Test
	public void test() throws UnsupportedEncodingException {
		String string = "%E4%BD%90%E8%97%A4";
		System.out.println(string);
		string = URLDecoder.decode(string, "UTF-8");
		System.out.println(string);
	}

}
