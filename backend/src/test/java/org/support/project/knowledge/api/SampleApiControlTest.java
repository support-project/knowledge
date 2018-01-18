package org.support.project.knowledge.api;

import java.lang.invoke.MethodHandles;

import org.junit.Assert;
import org.junit.Test;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.knowledge.integration.IntegrationCommon;
import org.support.project.web.bean.Msg;
import org.support.project.web.boundary.JsonBoundary;
import org.support.project.web.config.SystemConfigValue;
import org.support.project.web.exception.CallControlException;
import org.support.project.web.logic.impl.DefaultAuthenticationLogicImpl;
import org.support.project.web.test.stub.StubHttpServletRequest;
import org.support.project.web.test.stub.StubHttpServletResponse;

public class SampleApiControlTest extends IntegrationCommon {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(MethodHandles.lookup());
    
    private static final String USER = "SampleApiControlTest";
    
    @Test
    public void test() throws Exception {
        StubHttpServletRequest request = new StubHttpServletRequest();
        StubHttpServletResponse response = new StubHttpServletResponse(request);

        request.setServletPath("api/sample");
        request.setMethod("get");
        try {
            invoke(request, response, JsonBoundary.class);
        } catch (CallControlException e) {
            // OK
        }
        
        addUser(USER);
        DefaultAuthenticationLogicImpl auth = org.support.project.di.Container.getComp(DefaultAuthenticationLogicImpl.class);
        auth.setSession(USER, request, response);
        JsonBoundary boundary = invoke(request, response, JsonBoundary.class);
        
        if (boundary.getObj() instanceof Msg) {
            LOG.info(((Msg)boundary.getObj()).getMsg());
        } else {
            Assert.fail("return value is wrong.");
        }
    }

    @Test
    public void test2() throws Exception {
        StubHttpServletRequest request = new StubHttpServletRequest();
        StubHttpServletResponse response = new StubHttpServletResponse(request);

        request.setServletPath("api/sample2");
        request.setMethod("get");
        JsonBoundary boundary = invoke(request, response, JsonBoundary.class);
        if (boundary.getObj() instanceof Msg) {
            LOG.info(((Msg)boundary.getObj()).getMsg());
        } else {
            Assert.fail("return value is wrong.");
        }
        
        SystemConfigValue.get().setClose(true);
        
        try {
            boundary = invoke(request, response, JsonBoundary.class);
        } catch (CallControlException e) {
            // OK
        }
        DefaultAuthenticationLogicImpl auth = org.support.project.di.Container.getComp(DefaultAuthenticationLogicImpl.class);
        auth.setSession(USER, request, response);
        boundary = invoke(request, response, JsonBoundary.class);
        
        if (boundary.getObj() instanceof Msg) {
            LOG.info(((Msg)boundary.getObj()).getMsg());
        } else {
            Assert.fail("return value is wrong.");
        }
    }


    @Test
    public void test3() throws Exception {
        StubHttpServletRequest request = new StubHttpServletRequest();
        StubHttpServletResponse response = new StubHttpServletResponse(request);

        request.setServletPath("api/sample3");
        request.setMethod("get");
        JsonBoundary boundary = invoke(request, response, JsonBoundary.class);
        if (boundary.getObj() instanceof Msg) {
            LOG.info(((Msg)boundary.getObj()).getMsg());
        } else {
            Assert.fail("return value is wrong.");
        }
        
        SystemConfigValue.get().setClose(true);
        
        boundary = invoke(request, response, JsonBoundary.class);
        if (boundary.getObj() instanceof Msg) {
            LOG.info(((Msg)boundary.getObj()).getMsg());
        } else {
            Assert.fail("return value is wrong.");
        }
        
        DefaultAuthenticationLogicImpl auth = org.support.project.di.Container.getComp(DefaultAuthenticationLogicImpl.class);
        auth.setSession(USER, request, response);
        boundary = invoke(request, response, JsonBoundary.class);
        
        if (boundary.getObj() instanceof Msg) {
            LOG.info(((Msg)boundary.getObj()).getMsg());
        } else {
            Assert.fail("return value is wrong.");
        }
    }


}
