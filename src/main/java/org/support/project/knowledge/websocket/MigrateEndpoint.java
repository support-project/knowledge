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
import org.support.project.knowledge.config.AppConfig;
import org.support.project.knowledge.deploy.InitDB;
import org.support.project.web.dao.SystemsDao;
import org.support.project.web.entity.SystemsEntity;
import org.support.project.web.websocket.CallBatchEndpoint;
import org.support.project.web.websocket.EndpointConfigurator;

import net.arnx.jsonic.JSONException;

@ServerEndpoint(value = "/migrate", configurator = EndpointConfigurator.class)
public class MigrateEndpoint extends CallBatchEndpoint {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(ReindexingEndpoint.class);
    
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
        if (text.equals("START_MIGRATE") && super.isAdmin(session)) {
            setSendPlefix("");
            call(session, InitDB.class);
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
        SystemsEntity entity = SystemsDao.get().selectOnKey(AppConfig.get().getSystemName());
        if (entity != null) {
            if (InitDB.CURRENT.equals(entity.getVersion())) {
                AppConfig.get().setMaintenanceMode(false);
            }
        }
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
