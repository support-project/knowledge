package org.support.project.web.boundary;

import org.support.project.web.common.HttpUtil;

public class RedirectBoundary extends AbstractBoundary {
    
    private String path;
    
    public RedirectBoundary() {
        super();
    }
    public RedirectBoundary(String path) {
        super();
        this.path = path;
    }


    @Override
    public void navigate() throws Exception {
        HttpUtil.redirect(getResponse(), getRequest(), path);
        
        
    }

    /**
     * @return the path
     */
    public String getPath() {
        return path;
    }

    /**
     * @param path the path to set
     */
    public void setPath(String path) {
        this.path = path;
    }

}
