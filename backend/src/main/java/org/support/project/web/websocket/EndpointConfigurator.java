package org.support.project.web.websocket;

import java.lang.invoke.MethodHandles;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;
import javax.websocket.server.ServerEndpointConfig.Configurator;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.web.bean.AccessUser;
import org.support.project.web.config.CommonWebParameter;

public class EndpointConfigurator extends Configurator {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(MethodHandles.lookup());

    public static final String LOCALE_KEY = "LOCALE_KEY";
    public static final String LOGIN_USER_KEY = "LOGIN_USER_KEY";

    @Override
    public void modifyHandshake(ServerEndpointConfig config, HandshakeRequest request, HandshakeResponse response) {
        try {
            super.modifyHandshake(config, request, response);

            // ログインの情報をセットする
            Map<String, Object> prop = config.getUserProperties();
            if (prop == null) {
                return;
            }

            Locale locale = Locale.getDefault();
            if (request.getHttpSession() instanceof HttpSession) {
                HttpSession session = (HttpSession) request.getHttpSession();
                if (session != null) {
                    locale = (Locale) session.getAttribute(CommonWebParameter.LOCALE_SESSION_KEY);
                    AccessUser loginuser = (AccessUser) session.getAttribute(CommonWebParameter.LOGIN_USER_INFO_SESSION_KEY);
                    if (loginuser != null) {
                        prop.put(LOGIN_USER_KEY, loginuser);
                    }
                }
            }
            prop.put(LOCALE_KEY, locale);
        } catch (Exception e) {
            // なぜかNullPointerExceptionが発生する事がある
            LOG.info("Error", e);
        }
    }

}
