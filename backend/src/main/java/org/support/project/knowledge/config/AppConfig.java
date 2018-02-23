package org.support.project.knowledge.config;

import org.support.project.common.config.ConfigLoader;
import org.support.project.common.util.StringUtils;

public class AppConfig extends org.support.project.web.config.AppConfig {

    public static AppConfig get() {
        if (appConfig == null) {
            appConfig = ConfigLoader.load(AppConfig.APP_CONFIG, AppConfig.class);
        }
        return appConfig;
    }

    private static AppConfig appConfig = null;

    private String indexPath;

    private boolean convIndexPath = false;

    private String slidePath;
    
    private boolean convSlidePath = false;
    
    private boolean enableV2 = false;
    

    /**
     * @return the indexPath
     */
    public String getIndexPath() {
        if (StringUtils.isEmpty(indexPath)) {
            return "";
        }

        if (!convIndexPath) {
            String path = indexPath;
            this.indexPath = convPath(path);
            convIndexPath = true;
        }
        return indexPath;
    }

    /**
     * @param indexPath the indexPath to set
     */
    public void setIndexPath(String indexPath) {
        this.indexPath = indexPath;
    }
    
    /**
     * Get slidePath
     * @return the slidePath
     */
    public String getSlidePath() {
        if (StringUtils.isEmpty(slidePath)) {
            return "";
        }

        if (!convSlidePath) {
            String path = slidePath;
            this.slidePath = convPath(path);
            convSlidePath = true;
        }
        return slidePath;
    }

    /**
     * Set slidePath
     * @param slidePath the slidePath to set
     */
    public void setSlidePath(String slidePath) {
        this.slidePath = slidePath;
    }

    public boolean isEnableV2() {
        return enableV2;
    }

    public void setEnableV2(boolean enableV2) {
        this.enableV2 = enableV2;
    }

}
