package org.support.project.knowledge.logic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.web.exception.AuthenticateException;
import org.support.project.web.logic.impl.DefaultAuthenticationLogicImpl;

@DI(instance = Instance.Singleton)
public class KnowledgeAuthenticationLogic extends DefaultAuthenticationLogicImpl {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(DefaultAuthenticationLogicImpl.class);
    
    
    @Override
    public void setSession(String userId, HttpServletRequest request, HttpServletResponse response) throws AuthenticateException {
        super.setSession(userId, request, response);
        
        /* TODO タイムゾーンなどのユーザ設定をDBに保持する。保持されていた場合は、そちらを有効にする。
         * UIでタイムゾーンを指定可能にする（現状はブラウザで自動判定している）
        
        LoginedUser loginedUser = getSession(request);
        if (loginedUser == null) {
            return;
        }

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            LOG.trace("Cookies");
            for (Cookie cookie : cookies) {
                LOG.trace(cookie.getName());
                LOG.trace(cookie.getValue());
                if (AccountLogic.get().getCookieKeyTimezone().equals(cookie.getName())) {
                    String value = AccountLogic.get().getConfig(loginedUser.getUserId(), UserConfig.TIMEZONE);
                    if (StringUtils.isEmpty(value)) {
                        AccountLogic.get().setConfig(loginedUser.getUserId(), UserConfig.TIMEZONE, cookie.getValue());
                    } else {
                        HttpUtil.setCookie(request, response, AccountLogic.get().getCookieKeyTimezone(), value);
                    }
                } else if (AccountLogic.get().getCookieKeyTimezoneOffset().equals(cookie.getName())) {
                    String value = AccountLogic.get().getConfig(loginedUser.getUserId(), UserConfig.TIME_ZONE_OFFSET);
                    if (StringUtils.isEmpty(value)) {
                        AccountLogic.get().setConfig(loginedUser.getUserId(), UserConfig.TIME_ZONE_OFFSET, cookie.getValue());
                    } else {
                        HttpUtil.setCookie(request, response, AccountLogic.get().getCookieKeyTimezoneOffset(), value);
                    }
                } else if (AccountLogic.get().getCookieKeyThema().equals(cookie.getName())) {
                    String value = AccountLogic.get().getConfig(loginedUser.getUserId(), UserConfig.THEMA);
                    if (StringUtils.isEmpty(value)) {
                        AccountLogic.get().setConfig(loginedUser.getUserId(), UserConfig.THEMA, cookie.getValue());
                    } else {
                        HttpUtil.setCookie(request, response, AccountLogic.get().getCookieKeyThema(), value);
                    }
                } else if (AccountLogic.get().getCookieKeyHighlight().equals(cookie.getName())) {
                    String value = AccountLogic.get().getConfig(loginedUser.getUserId(), UserConfig.HIGHLIGHT);
                    if (StringUtils.isEmpty(value)) {
                        AccountLogic.get().setConfig(loginedUser.getUserId(), UserConfig.HIGHLIGHT, cookie.getValue());
                    } else {
                        HttpUtil.setCookie(request, response, AccountLogic.get().getCookieKeyHighlight(), value);
                    }
                }
            }
        }
    */
    }
}
