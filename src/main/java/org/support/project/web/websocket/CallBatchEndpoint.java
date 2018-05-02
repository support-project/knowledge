package org.support.project.web.websocket;

import java.io.IOException;
import java.util.List;

import javax.websocket.OnClose;
import javax.websocket.Session;

import org.support.project.common.bat.JobResult;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.web.bean.MessageResult;

import net.arnx.jsonic.JSON;


public abstract class CallBatchEndpoint extends AbstractEndpoint implements JobOnWebsocket.Listener {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(CallBatchEndpoint.class);

    private String sendPlefix = "[SEND]";
    private Class<?> batch = null;
    protected void setSendPlefix(String string) {
        this.sendPlefix = string;
    }
    
    @OnClose
    public void onClose(Session session) {
        if (batch != null && BatchPool.get().getPool().containsKey(batch.getName())) {
            BatchPool.get().getPool().get(batch.getName()).removeSession(session);
        }
    }
    
    @Override
    public void finishJob(JobResult result, Class<?> batch, List<Session> sessions) {
        if (BatchPool.get().getPool().containsKey(batch.getName())) {
            BatchPool.get().getPool().remove(batch.getName());
        }
    }

    protected void call(Session session, Class<?> batch) {
        if (BatchPool.get().getPool().containsKey(batch.getName())) {
            LOG.info("Already started. " + batch.getSimpleName());
            try {
                MessageResult result = new MessageResult();
                result.setMessage("already started.");
                session.getBasicRemote().sendText(JSON.encode(result));
            } catch (IllegalStateException e) {
                LOG.warn("websocket message send error. " + e.getClass().getSimpleName() + ". but this batch is continue...");
            } catch (IOException e) {
                LOG.warn("websocket message send error", e);
            }
            JobOnWebsocket job = BatchPool.get().getPool().get(batch.getName());
            job.addSession(session);
            return;
        }
        this.batch = batch;
        JobOnWebsocket job = new JobOnWebsocket();
        BatchPool.get().getPool().put(batch.getName(), job);
        job.setSendPlefix(sendPlefix);
        job.addSession(session);
        job.setListener(this);
        job.execute(batch);
    }
}
