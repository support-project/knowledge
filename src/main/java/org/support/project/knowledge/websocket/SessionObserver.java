package org.support.project.knowledge.websocket;

import java.util.Locale;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javax.websocket.Session;

import net.arnx.jsonic.JSON;

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
			if (!session.isOpen()) {
				return;
			}
			
			Locale locale = Locale.getDefault();
			Map<String, Object> prop = session.getUserProperties();
			LoginedUser loginuser = null;
			if (prop != null) {
				if (prop.containsKey(NotifyEndpointConfigurator.LOCALE_KEY)) {
					locale = (Locale) prop.get(NotifyEndpointConfigurator.LOCALE_KEY);
				}
				if (prop.containsKey(NotifyEndpointConfigurator.LOCALE_KEY)) {
					loginuser = (LoginedUser) prop.get(NotifyEndpointConfigurator.LOGIN_USER_KEY);
				}
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
		} catch (Exception e) {
			LOG.error("error", e);
		}
	}
	
	/**
	 * Sessionが開いているかチェック
	 * @return
	 */
	public boolean isOpen() {
		return session.isOpen();
	}

}
