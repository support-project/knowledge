package org.support.project.web.config;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;

@DI(instance = Instance.Singleton)
public class SystemConfigValue {
    public static SystemConfigValue get() {
        return Container.getComp(SystemConfigValue.class);
    }
    
    private boolean close = false;

    /**
     * @return the close
     */
    public boolean isClose() {
        return close;
    }

    /**
     * @param close the close to set
     */
    public void setClose(boolean close) {
        this.close = close;
    }

}
