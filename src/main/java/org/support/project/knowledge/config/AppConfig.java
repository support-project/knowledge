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

}
