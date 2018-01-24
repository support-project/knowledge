package org.support.project.web.common;

import org.junit.Test;
import org.support.project.web.config.HttpMethod;

public class InvokeSearchTest {


    @Test
    public void testGetController() {
        InvokeSearch search = new InvokeSearch();
        search.addTarget("org.support.project.web.control", "Control");
        
        InvokeTarget target = search.getController(HttpMethod.get, "Test/test", null);
        target.invoke();
        
        target = search.getController(HttpMethod.post, "sub.TestSub/testMethod", null);
        target.invoke();
    }

}
