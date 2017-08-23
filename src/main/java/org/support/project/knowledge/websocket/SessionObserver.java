package org.support.project.knowledge.websocket;

import java.util.Locale;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javax.websocket.Session;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.knowledge.logic.notification.DesktopNotification;
import org.support.project.web.bean.LoginedUser;
import org.support.project.web.bean.MessageResult;
import org.support.project.web.websocket.EndpointConfigurator;

import net.arnx.jsonic.JSON;

/**
 * Websocketのセッションに通知を行うObserverクラス
 * @author Koda
 */
public class SessionObserver implements Observer {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(SessionObserver.class);
    
    /**
     * セッション
     */
    private Session session;
    /**
     * コンストラクタ
     * @param session
     */
    public SessionObserver(Session session) {
        super();
        this.session = session;
    }

    @Override
    public void update(Observable o, Object message) {
        try {
            if (!session.isOpen()) {
                return;
            }

            Locale locale = Locale.getDefault();
            Map<String, Object> prop = session.getUserProperties();
            LoginedUser loginuser = null;
            if (prop != null) {
                if (prop.containsKey(EndpointConfigurator.LOCALE_KEY)) {
                    locale = (Locale) prop.get(EndpointConfigurator.LOCALE_KEY);
                }
                if (prop.containsKey(EndpointConfigurator.LOCALE_KEY)) {
                    loginuser = (LoginedUser) prop.get(EndpointConfigurator.LOGIN_USER_KEY);
                }
            }

            if (message instanceof DesktopNotification && loginuser != null) {
                DesktopNotification notify = (DesktopNotification) message;
                MessageResult result = notify.getMessage(loginuser, locale);
                if (result != null) {
                    if (LOG.isDebugEnabled()) {
                        StringBuilder builder = new StringBuilder();
                        builder.append("[Notify] ").append("User id:    ").append(session.getUserPrincipal().getName()).append("  ");
                        builder.append("Session id: ").append(session.getId()).append("  ").append(result.getMessage());
                        LOG.debug(builder.toString());
                    }
                    session.getBasicRemote().sendText(JSON.encode(result));
                }
            }
        } catch (Exception e) {
            LOG.error("error", e);
        }
    }

    /**
     * Sessionが開いているかチェック
     * 
     * @return
     */
    public boolean isOpen() {
        return session.isOpen();
    }

}
