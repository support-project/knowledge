package org.support.project.web.util;

import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.Map;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;

/**
 * Webアプリの一つのリクエストは1つのThreadに入る その一つのリクエスト内で何か情報を保持しておきたい場合に、 このクラスを利用する
 * 
 * @author Koda
 *
 */
@DI(instance = Instance.Singleton)
public class ThredUserPool {
    private static final Log LOG = LogFactory.getLog(MethodHandles.lookup());

    private Map<Thread, Map<String, Object>> infoMap;

    public static ThredUserPool get() {
        return Container.getComp(ThredUserPool.class);
    }

    public ThredUserPool() {
        super();
        infoMap = new HashMap<>();
    }

    public void setInfo(String key, Object info) {
        if (info == null) {
            return;
        }
        synchronized (infoMap) {
            if (LOG.isTraceEnabled()) {
                LOG.trace("set request infomation" + info.toString());
            }
            Thread t = Thread.currentThread();
            if (!infoMap.containsKey(t)) {
                infoMap.put(t, new HashMap<>());
            }
        }
    }

    public Object getInfo(String key) {
        synchronized (infoMap) {
            Thread t = Thread.currentThread();
            if (infoMap.containsKey(t)) {
                return infoMap.get(t).get(key);
            }
            return null;
        }
    }

    public void clearInfo() {
        synchronized (infoMap) {
            if (LOG.isTraceEnabled()) {
                LOG.trace("clear request infomation");
            }
            Thread t = Thread.currentThread();
            if (infoMap.containsKey(t)) {
                infoMap.remove(t);
            }
        }
    }
}
