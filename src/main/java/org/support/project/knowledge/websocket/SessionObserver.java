package org.support.project.knowledge.websocket;

import java.io.IOException;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;

import javax.websocket.Session;

import net.arnx.jsonic.JSON;
import net.arnx.jsonic.JSONException;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.knowledge.vo.Notify;
import org.support.project.web.bean.LoginedUser;
import org.support.project.web.bean.MessageResult;

public class SessionObserver implements Observer {
	/** ログ */
	private static Log LOG = LogFactory.getLog(SessionObserver.class);

	private Session session;
	
	public SessionObserver(Session session) {
		super();
		this.session = session;
	}

	@Override
	public void update(Observable o, Object message) {
		try {
			Locale locale = Locale.getDefault();
			if (session.getUserProperties().containsKey(NotifyEndpointConfigurator.LOCALE_KEY)) {
				locale = (Locale) session.getUserProperties().get(NotifyEndpointConfigurator.LOCALE_KEY);
			}
			LoginedUser loginuser = null;
			if (session.getUserProperties().containsKey(NotifyEndpointConfigurator.LOCALE_KEY)) {
				loginuser = (LoginedUser) session.getUserProperties().get(NotifyEndpointConfigurator.LOGIN_USER_KEY);
			}
			
			if (message instanceof Notify && loginuser != null) {
				Notify notify = (Notify) message;
				MessageResult result = notify.getMessage(loginuser, locale);
				if (result != null) {
					if (LOG.isInfoEnabled()) {
						LOG.info("[Notify] ");
						LOG.info("Session id: " + session.getId());
						LOG.info("User id:    " + session.getUserPrincipal().getName());
						//LOG.info("User key:   " + loginuser.getLoginUser().getUserKey());
						//LOG.info("Locale:     " + locale.toString());
						LOG.info(result.getMessage());
					}
					session.getBasicRemote().sendText(JSON.encode(result));
				}
			}
		} catch (JSONException | IOException e) {
			LOG.error("error", e);
		}
	}

}
