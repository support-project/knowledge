package org.support.project.web.common;

import org.junit.Test;
import org.support.project.web.test.stub.StubHttpServletRequest;

public class HttpUtilTest {

	@Test
	public void testParseRequest() {
		StubHttpServletRequest request = new StubHttpServletRequest();
		request.addParameter("test", "test");
		System.out.println(request.getParameter("test"));
		
		
	}

}
