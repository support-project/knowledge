package org.support.project.web.websocket;

import java.io.IOException;

import javax.websocket.Session;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.web.bean.LoginedUser;

public abstract class AbstractEndpoint {
    private static final Log LOG = LogFactory.getLog(AbstractEndpoint.class);

    public abstract void onOpen(Session session) throws Exception;

    public abstract void onClose(Session session) throws Exception;

    public abstract void onMessage(String text, Session session) throws Exception;

    public abstract void onError(Throwable t);

    /**
     * WebSocketにアクセスしているユーザが管理者かどうかチェック
     * 
     * @param session session
     * @return result
     */
    protected boolean isAdmin(Session session) {
        if (session == null) {
            return false;
        }
        try {
            if (session.getUserProperties().containsKey(EndpointConfigurator.LOCALE_KEY)) {
                LoginedUser loginuser = (LoginedUser) session.getUserProperties().get(EndpointConfigurator.LOGIN_USER_KEY);
                if (loginuser.isAdmin()) {
                    return true;
                }
            }
        } catch (IllegalStateException e) {
            LOG.debug("The WebSocket session was wrong. " + e.getMessage());
        }
        return false;
    }
    
    protected void close(Session session) {
        if (session == null) {
            return;
        }
        try {
            session.close();
        } catch (IllegalStateException | IOException e) {
            LOG.debug("The WebSocket session was wrong. " + e.getMessage());
        }
    }

}
