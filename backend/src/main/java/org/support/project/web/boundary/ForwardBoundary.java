package org.support.project.web.boundary;

import java.lang.invoke.MethodHandles;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.web.common.HttpUtil;


public class ForwardBoundary extends AbstractBoundary {
    /** ログ */
    private static Log log = LogFactory.getLog(MethodHandles.lookup());

    /** フォワードするパス */
    private String path;
    
    public ForwardBoundary(String path) {
        super();
        this.path = path;
    }
    
    @Override
    public void navigate() throws Exception {
        log.debug("foward to : " + path);
        HttpUtil.forward(getResponse(), getRequest(), path);
    }
    
}
