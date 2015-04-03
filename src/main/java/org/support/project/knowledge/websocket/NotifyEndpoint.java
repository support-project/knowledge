package org.support.project.knowledge.websocket;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Observer;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import net.arnx.jsonic.JSONException;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.di.Container;
import org.support.project.web.bean.LoginedUser;

@ServerEndpoint(value = "/notify", configurator=NotifyEndpointConfigurator.class)
public class NotifyEndpoint {
	/** ログ */
	private static Log LOG = LogFactory.getLog(NotifyEndpoint.class);
	
	private Map<String, Observer> map = Collections.synchronizedMap(new HashMap<>());
	private NotifyAction notify = Container.getComp(NotifyAction.class);
	
	@OnOpen
	public void onOpen(Session session) {
		// 開始時
		if (LOG.isInfoEnabled()) {
			 if (session.getUserProperties().containsKey(NotifyEndpointConfigurator.LOCALE_KEY)) {
				LoginedUser loginuser = (LoginedUser) session.getUserProperties().get(NotifyEndpointConfigurator.LOGIN_USER_KEY);
				LOG.info("websocket open: " + session.getId() + " : " + loginuser.getUserId());
			}
		}
		SessionObserver observer = new SessionObserver(session);
		map.put(session.getId(), observer);
		notify.addObserver(observer);
	}

	@OnClose
	public void onClose(Session session) {
		// 完了時
		if (LOG.isInfoEnabled()) {
			 if (session.getUserProperties().containsKey(NotifyEndpointConfigurator.LOCALE_KEY)) {
				LoginedUser loginuser = (LoginedUser) session.getUserProperties().get(NotifyEndpointConfigurator.LOGIN_USER_KEY);
				LOG.info("websocket close: " + session.getId() + " : " + loginuser.getUserId());
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
	
}
