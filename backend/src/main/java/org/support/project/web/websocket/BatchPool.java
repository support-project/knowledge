package org.support.project.web.websocket;

import java.util.HashMap;
import java.util.Map;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;

@DI(instance = Instance.Singleton)
public class BatchPool {
    public static BatchPool get() {
        return Container.getComp(BatchPool.class);
    }
    private Map<String, JobOnWebsocket> pool = new HashMap<>();
    /**
     * @return the batchPool
     */
    public Map<String, JobOnWebsocket> getPool() {
        return pool;
    }
}
