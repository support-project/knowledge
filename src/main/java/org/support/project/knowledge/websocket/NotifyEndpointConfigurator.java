package org.support.project.knowledge.websocket;

import java.util.Locale;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;
import javax.websocket.server.ServerEndpointConfig.Configurator;

import org.support.project.web.bean.LoginedUser;
import org.support.project.web.config.CommonWebParameter;

public class NotifyEndpointConfigurator extends Configurator {
	
	public static final String LOCALE_KEY = "LOCALE_KEY";
	public static final String LOGIN_USER_KEY = "LOGIN_USER_KEY";
	
	@Override
	public void modifyHandshake(ServerEndpointConfig config, HandshakeRequest request, HandshakeResponse response) {
		super.modifyHandshake(config, request, response);
		
		Locale locale = Locale.getDefault();
		if (request.getHttpSession() instanceof HttpSession) {
			HttpSession session = (HttpSession) request.getHttpSession();
			locale = (Locale) session.getAttribute(CommonWebParameter.LOCALE_SESSION_KEY);
			LoginedUser loginuser = (LoginedUser) session.getAttribute(CommonWebParameter.LOGIN_USER_INFO_SESSION_KEY);
			config.getUserProperties().put(LOGIN_USER_KEY, loginuser);
		}
		
		config.getUserProperties().put(LOCALE_KEY, locale);
	}

}
