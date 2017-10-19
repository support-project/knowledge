package org.support.project.web.config;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;

@DI(instance = Instance.Singleton)
public class AnalyticsConfig {

    /** アナリティクス設定を保持するキー */
    public static final String KEY_ANALYTICS = "KEY_ANALYTICS";

    public static AnalyticsConfig get() {
        return Container.getComp(AnalyticsConfig.class);
    }

    private String analyticsScript = "";

    /**
     * @return the analyticsScript
     */
    public String getAnalyticsScript() {
        return analyticsScript;
    }

    /**
     * @param analyticsScript the analyticsScript to set
     */
    public void setAnalyticsScript(String analyticsScript) {
        this.analyticsScript = analyticsScript;
    }

}
