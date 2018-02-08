package org.support.project.knowledge.websocket;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.di.Container;
import org.support.project.web.bean.AccessUser;
import org.support.project.web.websocket.EndpointConfigurator;

import net.arnx.jsonic.JSONException;

@ServerEndpoint(value = "/notify", configurator = EndpointConfigurator.class)
public class NotifyEndpoint {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(MethodHandles.lookup());

    private Map<String, SessionObserver> map = Collections.synchronizedMap(new HashMap<>());
    private NotifyAction notify = Container.getComp(NotifyAction.class);

    @OnOpen
    public void onOpen(Session session) {
        // 開始時
        if (LOG.isInfoEnabled()) {
            if (session.getUserProperties().containsKey(EndpointConfigurator.LOCALE_KEY)) {
                AccessUser loginuser = (AccessUser) session.getUserProperties().get(EndpointConfigurator.LOGIN_USER_KEY);
                LOG.debug("websocket open: " + session.getId() + " : " + loginuser.getUserId());
            }
        }
        SessionObserver observer = new SessionObserver(session);
        map.put(session.getId(), observer);
        notify.addObserver(observer);

        // 既存のセッションの存在チェック(Closeが呼ばれずに無くなってしまう事がある)
        Iterator<String> iterator = map.keySet().iterator();
        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            SessionObserver sessionObserver = map.get(key);
            if (!sessionObserver.isOpen()) {
                LOG.debug("websocket is allready closed: " + session.getId());
                notify.deleteObserver(map.get(session.getId()));
                map.remove(session.getId());
            }
        }
    }

    @OnClose
    public void onClose(Session session) {
        // 完了時
        if (LOG.isInfoEnabled()) {
            if (session.getUserProperties().containsKey(EndpointConfigurator.LOCALE_KEY)) {
                AccessUser loginuser = (AccessUser) session.getUserProperties().get(EndpointConfigurator.LOGIN_USER_KEY);
                LOG.debug("websocket close: " + session.getId() + " : " + loginuser.getUserId());
            }
        }
        if (map.containsKey(session.getId())) {
            notify.deleteObserver(map.get(session.getId()));
            map.remove(session.getId());
        }
    }

    @OnMessage
    public void onMessage(String text) throws JSONException, IOException {
        // LOG.info("onMessage: " + text);
        // MessageResult message = new MessageResult();
        // message.setMessage(text);
        // notify.notifyObservers(message);
    }

    @OnError
    public void onError(Throwable t) {
        LOG.warn("websocket on error." + t.getClass().getName() + " : " + t.getMessage());
        if (LOG.isDebugEnabled()) {
            LOG.warn("websocket error -> ", t);
        }
    }

}
