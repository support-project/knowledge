package org.support.project.knowledge.test.control.open;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.support.project.common.exception.ParseException;
import org.support.project.common.test.Order;
import org.support.project.knowledge.TestCommon;
import org.support.project.knowledge.control.open.KnowledgeControl;
import org.support.project.web.exception.InvalidParamException;
import org.support.project.web.test.stub.ControlContainer;
import org.support.project.web.test.stub.StubCookie;

public class KnowledgeControlTest extends TestCommon {
    private ControlContainer container;
    
    
    @Before
    public void setUp() throws Exception {
        container = new ControlContainer();
        container.setContextPath("/knowledge");
        List<Cookie> cookies = new ArrayList<>();
        cookies.add(new StubCookie("KNOWLEDGE_HISTORY", "1,2,3,4,5,6,7,8"));
        container.setCookies(cookies);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    @Order(order = 1)
    public void testView() throws InvalidParamException, ParseException {
        KnowledgeControl control = container.getComp(KnowledgeControl.class);
        control.view();
    }

    @Test
    @Order(order = 2)
    public void testList() throws Exception {
        KnowledgeControl control = container.getComp(KnowledgeControl.class);
        control.list();
    }

    // @Test
    // public void testLike() {
    // fail("Not yet implemented");
    // }
    //
    // @Test
    // public void testEscape() {
    // fail("Not yet implemented");
    // }
    //
    // @Test
    // public void testSearch() {
    // fail("Not yet implemented");
    // }

}
