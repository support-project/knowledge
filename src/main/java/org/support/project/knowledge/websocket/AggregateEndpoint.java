package org.support.project.knowledge.websocket;

import java.io.IOException;
import java.util.List;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.support.project.common.bat.JobResult;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.knowledge.bat.AggregateBat;
import org.support.project.web.websocket.CallBatchEndpoint;
import org.support.project.web.websocket.EndpointConfigurator;

import net.arnx.jsonic.JSONException;

@ServerEndpoint(value = "/aggregate", configurator = EndpointConfigurator.class)
public class AggregateEndpoint extends CallBatchEndpoint {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(AggregateEndpoint.class);
    
    @OnOpen
    public void onOpen(Session session) throws IOException {
        LOG.info("Endpoint instance: " + this.hashCode());
        if (!super.isAdmin(session)) {
            session.close();
        }
    }
    
    @OnClose
    public void onClose(Session session) {
        super.onClose(session);
    }

    @OnMessage
    public void onMessage(String text, Session session) throws JSONException, IOException {
        LOG.info(text);
        if (text.equals("START_PROCESS") && super.isAdmin(session)) {
            setSendPlefix("");
            call(session, AggregateBat.class);
        }
    }


    @OnError
    public void onError(Throwable t) {
        LOG.warn("websocket on error." + t.getClass().getName() + " : " + t.getMessage());
        if (LOG.isDebugEnabled()) {
            LOG.warn("websocket error -> ", t);
        }
    }

    @Override
    public void finishJob(JobResult result, Class<?> batch, List<Session> sessions) {
        super.finishJob(result, batch, sessions);
        for (Session session : sessions) {
            if (!super.isAdmin(session)) {
                return;
            }
            try {
                session.close();
            } catch (IOException e) {
                LOG.warn("websocket on error." + e.getClass().getName() + " : " + e.getMessage());
            }
        }
    }
}
