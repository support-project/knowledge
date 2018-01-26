package org.support.project.common.bat;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.di.Container;

/**
 * Async Job
 * @author Koda
 */
public class AsyncJob extends BatJob implements Runnable {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(MethodHandles.lookup());
    
    /**
     * get instance
     * @return instance
     */
    public static AsyncJob get() {
        return Container.getComp(AsyncJob.class);
    }
    
    /**
     * リスナー
     */
    private List<BatListener> batListeners;
    
    /**
     * コンストラクタ
     */
    public AsyncJob() {
        super();
        batListeners = Collections.synchronizedList(new ArrayList<BatListener>());
    }

    @Override
    public void run() {
        try {
            JobResult result = super.execute();
            LOG.debug(result);
            for (BatListener listener : batListeners) {
                listener.finish(result);
            }
        } catch (IOException | InterruptedException e) {
            LOG.error("error on job.", e);
            JobResult result = new JobResult(-99, e.getLocalizedMessage());
            for (BatListener listener : batListeners) {
                listener.finish(result);
            }
        }
    }

    @Override
    public JobResult execute() throws IOException, InterruptedException {
        Thread thread = new Thread(this);
        thread.start();
        
        JobResult result = new JobResult(0, "success called.");
        return result;
    }
    
    /**
     * リスナー追加
     * @param listener listener
     */
    public void addListener(BatListener listener) {
        if (!batListeners.contains(listener)) {
            batListeners.add(listener);
        }
    }
    
    /**
     * remove listener
     * @param listener listener
     */
    public void removeListener(BatListener listener) {
        if (batListeners.contains(listener)) {
            batListeners.remove(listener);
        }
    }

}
