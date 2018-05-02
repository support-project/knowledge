package org.support.project.web.common;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.PropertyUtil;
import org.support.project.common.util.StringUtils;
import org.support.project.web.bean.LoginedUser;
import org.support.project.web.bean.MessageResult;
import org.support.project.web.config.AppConfig;
import org.support.project.web.config.CommonWebParameter;
import org.support.project.web.config.MessageStatus;
import org.support.project.web.dao.LocalesDao;
import org.support.project.web.dao.UsersDao;
import org.support.project.web.entity.LocalesEntity;
import org.support.project.web.entity.UsersEntity;
import org.support.project.web.exception.InvalidParamException;

import net.arnx.jsonic.JSON;
import net.arnx.jsonic.JSONException;

/**
 * 
 * Httpの処理のユーティリティ
 * 
 * @author Koda
 * 
 */
public class HttpUtil {
    /** ログ */
    private static Log log = LogFactory.getLog(HttpUtil.class);

    public static final int COOKIE_AGE = 60 * 60 * 24 * 31;

    /*
     * Windowsに入れたデフォルトのTomcat(自分の場合Tomcat7.0) ではどうやら内部処理がISO-8859-1らしい。 このためJSP(Java)側を
     * 
     * String GetItem = new String (request.getParameter("item").getBytes("ISO-8859-1"));
     * out.println("ISO-8859-1でエンコード："URLDecoder.decode(GetItem,"UTF-8")); という風にISO-8859-1で渡されましたよーってJavaに教えてあげないといけない。
     * 
     * 
     * 他にも方法があって、 Server.xml(tomcat/7.0/conf/server.xml)に
     * 
     * <Connector port="8080" <!-- * * なんかいっぱい書いてある * --> useBodyEncodingForURI="true" /> って
     * 
     * useBodyEncodingForURI="true" を追記してあげるといい
     * 
     * もしくは
     * 
     * <Connector port="8080" <!-- * * なんかいっぱい書いてある * --> URIEncoding="UTF-8" /> という感じで
     * 
     * URIEncoding="UTF-8" を追記してUTF-8で処理すると明示しても良い
     */

    /**
     * 画面を表示する
     * 
     * @param res HttpServletResponse
     * @param req HttpServletRequest
     * @param page 表示するページ名称
     * @throws IOException IOException
     * @throws ServletException ServletException
     * 
     * @author Koda
     */
    public static void forward(HttpServletResponse res, HttpServletRequest req, String page) throws IOException, ServletException {
        try {
            req.getRequestDispatcher(page).forward(req, res);
        } catch (IOException | ServletException e) {
            log.debug("forward error.", e);
            throw e;
        }
    }

    /**
     * 画面を表示する
     * 
     * @param res HttpServletResponse
     * @param req HttpServletRequest
     * @param page 表示するページ名称
     * @throws IOException IOException
     * @throws ServletException ServletException
     * 
     * @author Koda
     */
    public static void redirect(HttpServletResponse res, HttpServletRequest req, String page) throws IOException, ServletException {
        StringBuilder path = new StringBuilder();
        if (!page.startsWith("http")) {
            path.append(getHostUrl(req));
        }
        path.append(page);
        res.sendRedirect(path.toString());
    }
    
    /**
     * リクエストのJSONをオブジェクトにして返す
     * @param req request
     * @param paramtypes type
     * @param <T> type
     * @return value 
     * @throws InvalidParamException InvalidParamException
     */
    public static <T> T parseJson(HttpServletRequest req, Class<? extends T> paramtypes) throws InvalidParamException {
        String contentType = req.getHeader("content-type");
        if (contentType != null && contentType.startsWith("application/json")) {
            try {
                T object = JSON.decode(req.getInputStream(), paramtypes);
                return object;
            } catch (JSONException | IOException e) {
                log.warn("JSON parse error.", e);
                throw new InvalidParamException(new MessageResult(MessageStatus.Error, HttpStatus.SC_400_BAD_REQUEST, "BAD_REQUEST", ""));
            }
        }
        throw new InvalidParamException(new MessageResult(MessageStatus.Error, HttpStatus.SC_400_BAD_REQUEST, "BAD_REQUEST", ""));
    }
    
    
    /**
     * リクエストのパラメータから、指定のクラスに値をセットしたオブジェクトを返す
     * 
     * @param req リクエスト
     * @param paramtypes パラメータのタイプ
     * @param <T> type
     * @return オブジェクト
     * @throws IllegalAccessException IllegalAccessException
     * @throws InstantiationException InstantiationException
     * @throws IOException IOException
     * @throws JSONException JSONException
     * @throws InvalidParamException InvalidParamException
     */
    public static <T> T parseRequest(HttpServletRequest req, Class<? extends T> paramtypes)
            throws InstantiationException, IllegalAccessException, JSONException, IOException, InvalidParamException {
        return parseRequest(req, paramtypes, null);
    }

    /**
     * リクエストのパラメータから、指定のクラスに値をセットしたオブジェクトを返す
     * 
     * @param req リクエスト
     * @param paramtypes パラメータのタイプ
     * @param prefix 取得の際のプレフィックス
     * @param <T> type
     * @return オブジェクト
     * @throws IllegalAccessException IllegalAccessException
     * @throws InstantiationException InstantiationException
     * @throws IOException IOException
     * @throws JSONException JSONException
     * @throws InvalidParamException InvalidParamException
     */
    public static <T> T parseRequest(HttpServletRequest req, Class<? extends T> paramtypes, String prefix)
            throws InstantiationException, IllegalAccessException, JSONException, IOException, InvalidParamException {
        T object = null;
        if (log.isDebugEnabled()) {
            StringBuilder builder = new StringBuilder();
            Enumeration<String> headers = req.getHeaderNames();
            while (headers.hasMoreElements()) {
                String header = (String) headers.nextElement();
                String value = req.getHeader(header);

                builder.append(header).append(" : ").append(value);
                builder.append("\n");
            }
            log.debug("[RequestHeader] ¥n" + builder.toString());
        }

        String contentType = req.getHeader("content-type");
        if (contentType != null && contentType.startsWith("application/json")) {
            // try {
            // BufferedReader br = new BufferedReader(
            // new InputStreamReader(req.getInputStream(), Charset.forName("UTF-8")));
            // String line;
            // while ((line = br.readLine()) != null) {
            // log.trace(line);
            // }
            // br.close();
            // } catch (IOException e) {
            // e.printStackTrace();
            // }
            object = JSON.decode(req.getInputStream(), paramtypes);

        } else {
            object = paramtypes.newInstance();
            List<String> paramNames = PropertyUtil.getPropertyNames(paramtypes);
            log.debug(req.getQueryString());

            StringBuilder builder = new StringBuilder();
            for (String string : paramNames) {
                StringBuilder param = new StringBuilder();
                param.append(string);
                if (prefix != null) {
                    if (string.startsWith(prefix + ".")) {
                        param.delete(0, prefix.length() + 1);
                    } else {
                        param = null;
                    }
                }

                if (param != null && paramNames.contains(param.toString())) {
                    Class<?> type = PropertyUtil.getPropertyType(paramtypes, param.toString());
                    // String[] values = req.getParameterValues(string);
                    // if (values != null && values.length == 1) {
                    // // ただのString
                    // String str = req.getParameter(string);
                    // values = new String[1];
                    // values[0] = str;
                    // }
                    // Object value = ValueConverter.conv(values, type);
                    Object value = getParameter(req, string, type);
                    if (value != null) {
                        PropertyUtil.setPropertyValue(object, param.toString(), value);
                    }

                    builder.append(param).append(" : ").append(value);
                    builder.append("\n");
                }
            }
            log.debug("[RequestParameter] \n" + builder.toString());
        }
        return object;
    }
    /**
     * Get parameter
     * @param req request
     * @param param param
     * @param paramtypes type
     * @param <T> type
     * @return value
     * @throws InvalidParamException InvalidParamException
     */
    public static <T> T getParameter(HttpServletRequest req, String param, Class<? extends T> paramtypes) throws InvalidParamException {
        String[] values = null;
        if (paramtypes.isArray() || List.class.isAssignableFrom(paramtypes)) {
            values = req.getParameterValues(param);
        } else {
            String str = req.getParameter(param);
            if (str != null) {
                values = new String[1];
                values[0] = str;
            }
        }

        if (values == null || values.length == 0) {
            Object value = req.getAttribute(param);
            if (value == null) {
                HttpSession session = req.getSession();
                value = session.getAttribute(param);
            }
            if (value == null) {
                return null;
            }
            if (paramtypes.isAssignableFrom(value.getClass())) {
                return (T) value;
            } else {
                if (value instanceof String) {
                    values = new String[1];
                    values[0] = (String) value;
                    return ValueConverter.conv(values, paramtypes);
                } else if (value instanceof String[]) {
                    return ValueConverter.conv((String[]) value, paramtypes);
                }
                if (paramtypes.isAssignableFrom(String.class)) {
                    // String型取得であれば、どんな型でも取得する
                    return (T) value.toString();
                }

                log.error("fail getParameter   [param]" + param + "   [type]" + paramtypes.getName() + "   [value]"
                        + PropertyUtil.reflectionToString(value));
                MessageResult messageResult = new MessageResult();
                messageResult.setMessage("");
                messageResult.setStatus(HttpStatus.SC_400_BAD_REQUEST);
                throw new InvalidParamException(messageResult);
            }
        } else {
            return ValueConverter.conv(values, paramtypes);
        }
    }

    /**
     * セッションに登録されたログインユーザ情報を取得
     * 
     * @param request request 
     * @return LoginedUser
     */
    public static LoginedUser getLoginedUser(HttpServletRequest request) {
        return (LoginedUser) request.getSession().getAttribute(CommonWebParameter.LOGIN_USER_INFO_SESSION_KEY);
    }
    /**
     * mobileでアクセスしているか
     * @param request request
     * @return result
     */
    public static boolean isMobilephone(HttpServletRequest request) {
        String sUserAgent = request.getHeader("user-agent");
        if (sUserAgent.indexOf("DoCoMo") == 0) {
            // ドコモの携帯
            return true;
        } else if (sUserAgent.indexOf("J-PHONE") == 0 || sUserAgent.indexOf("Vodafone") == 0 || sUserAgent.indexOf("SoftBank") == 0) {
            // ソフトバンクの携帯
            return true;
        } else if (sUserAgent.indexOf("UP.Browser") == 0 || sUserAgent.indexOf("KDDI") == 0) {
            // AUの携帯
            return true;
        }
        return false;
    }
    /**
     * Smartphoneでアクセスしているか
     * @param request request
     * @return result
     */
    public static boolean isSmartphone(HttpServletRequest request) {
        String sUserAgent = request.getHeader("user-agent");
        if ((sUserAgent.indexOf("iPhone") > 0 && sUserAgent.indexOf("iPad") == -1) || sUserAgent.indexOf("iPod") > 0
                || sUserAgent.indexOf("Android") > 0) {
            return true;
        } else {
            return false;
        }
    }

    /** ブラウザ不明 */
    public static final String BROWSER_UNKNOWN = "0";
    /** ブラウザIE */
    public static final String BROWSER_IE = "1";
    /** ブラウザFirefox */
    public static final String BROWSER_FIREFOX = "2";
    /** ブラウザOpera */
    public static final String BROWSER_OPERA = "3";
    /** ブラウザChrome */
    public static final String BROWSER_CHROME = "4";
    /** ブラウザSafari */
    public static final String BROWSER_SAFARI = "5";
    /** ブラウザNetscape */
    public static final String BROWSER_NETSCAPE = "6";

    /**
     * ブラウザの判定を行います。
     * 
     * @param request {@link HttpServletRequest}
     * @return ブラウザを表す文字列
     */
    public static String getBrowser(HttpServletRequest request) {

        String sUserAgent = request.getHeader("user-agent");

        if (isIE(sUserAgent)) {
            return BROWSER_IE;
        }
        if (isFirefox(sUserAgent)) {
            return BROWSER_FIREFOX;
        }
        if (isOpera(sUserAgent)) {
            return BROWSER_OPERA;
        }
        if (isChrome(sUserAgent)) {
            return BROWSER_CHROME;
        }
        if (isSafari(sUserAgent)) {
            return BROWSER_SAFARI;
        }
        if (isNetscape(sUserAgent)) {
            return BROWSER_NETSCAPE;
        }
        return BROWSER_UNKNOWN;
    }

    public static String getUserAgent(HttpServletRequest request) {
        return request.getHeader("user-agent");
    }

    /**
     * ブラウザがIEであるかどうかの判定を行います。
     * 
     * @param sUserAgent ユーザエージェント
     * @return IEであるかどうか
     */
    public static boolean isIE(String sUserAgent) {
        Pattern pattern = Pattern.compile(".*((MSIE)+ [0-9]\\.[0-9]).*");
        Matcher matcher = pattern.matcher(sUserAgent);
        boolean bMatch = matcher.matches();

        if (!bMatch) {
            // Trident の文字列があればIE
            bMatch = sUserAgent.indexOf("Trident") != -1;
        }
        return bMatch;
    }

    /**
     * ブラウザがIEであるかどうかの判定を行います。
     * 
     * @param request request
     * @return IEであるかどうか
     */
    public static boolean isIE(HttpServletRequest request) {
        return isIE(getUserAgent(request));
    }

    /**
     * ブラウザがFirefoxであるかどうかの判定を行います。
     * 
     * @param sUserAgent ユーザエージェント
     * @return Firefoxであるかどうか
     */
    public static boolean isFirefox(String sUserAgent) {
        Pattern pattern = Pattern.compile(".*((Firefox/)+[0-9]\\.[0-9]\\.?[0-9]?).*");
        Matcher matcher = pattern.matcher(sUserAgent);
        boolean bMatch = matcher.matches();
        return bMatch;
    }

    /**
     * ブラウザがFirefoxであるかどうかの判定を行います。
     * 
     * @param request request
     * @return Firefoxであるかどうか
     */
    public static boolean isFirefox(HttpServletRequest request) {
        return isFirefox(getUserAgent(request));
    }

    /**
     * ブラウザがOperaであるかどうかの判定を行います。
     * 
     * @param sUserAgent ユーザエージェント
     * @return Opereであるかどうか
     */
    public static boolean isOpera(String sUserAgent) {
        Pattern pattern = Pattern.compile(".*((Opera)+/? ?[0-9]\\.[0-9][0-9]?).*");
        Matcher matcher = pattern.matcher(sUserAgent);
        boolean bMatch = matcher.matches();
        return bMatch;
    }

    /**
     * ブラウザがOperaであるかどうかの判定を行います。
     * 
     * @param request request
     * @return Opereであるかどうか
     */
    public static boolean isOpera(HttpServletRequest request) {
        return isOpera(getUserAgent(request));
    }

    /**
     * ブラウザがChromeであるかどうかの判定を行います。
     * 
     * @param sUserAgent ユーザエージェント
     * @return Chromeであるかどうか
     */
    public static boolean isChrome(String sUserAgent) {
        Pattern pattern = Pattern.compile(".*((Chrome)+/?[0-9]\\.?[0-9]?).*");
        Matcher matcher = pattern.matcher(sUserAgent);
        boolean bMatch = matcher.matches();
        return bMatch;
    }

    /**
     * ブラウザがChromeであるかどうかの判定を行います。
     * 
     * @param request request
     * @return Chromeであるかどうか
     */
    public static boolean isChrome(HttpServletRequest request) {
        return isChrome(getUserAgent(request));
    }

    /**
     * ブラウザがSafariであるかどうかの判定を行います。
     * 
     * @param sUserAgent ユーザエージェント
     * @return Safariであるかどうか
     */
    public static boolean isSafari(String sUserAgent) {
        Pattern pattern = Pattern.compile(".*((Version/)+[0-9]\\.?[0-9]?\\.?[0-9]? Safari).*");
        Matcher matcher = pattern.matcher(sUserAgent);
        boolean bMatch = matcher.matches();
        return bMatch;
    }

    /**
     * ブラウザがSafariであるかどうかの判定を行います。
     * 
     * @param request request
     * @return Safariであるかどうか
     */
    public static boolean isSafari(HttpServletRequest request) {
        return isSafari(getUserAgent(request));
    }

    /**
     * ブラウザがNetscapeであるかどうかの判定を行います。
     * 
     * @param sUserAgent ユーザエージェント
     * @return Netscapeであるかどうか
     */
    public static boolean isNetscape(String sUserAgent) {
        Pattern pattern = Pattern.compile(".*((Netscape)+[0-9]\\.[0-9][0-9]?).*");
        Matcher matcher = pattern.matcher(sUserAgent);
        boolean bMatch = matcher.matches();
        return bMatch;
    }

    /**
     * ブラウザがNetscapeであるかどうかの判定を行います。
     * 
     * @param request request
     * @return Netscapeであるかどうか
     */
    public static boolean isNetscape(HttpServletRequest request) {
        return isNetscape(getUserAgent(request));
    }

    /**
     * ホストまでのURLを取得
     * 
     * @param req request
     * @return URL
     */
    public static String getHostUrl(HttpServletRequest req) {
        StringBuilder path = new StringBuilder();
        String protocol = req.getHeader("X-Forwarded-Proto");
        if (StringUtils.isEmpty(protocol)) {
            protocol = req.getScheme();
        }
        path.append(protocol);
        path.append("://");
        path.append(req.getServerName());
        if (req.getServerPort() != 80 && req.getServerPort() != 443) {
            path.append(":");
            path.append(req.getServerPort());
        }
        return path.toString();
    }

    /**
     * コンテキストパスまでのURLを取得
     * 
     * @param req request
     * @return context path
     */
    public static String getContextUrl(HttpServletRequest req) {
        StringBuilder path = new StringBuilder();
        path.append(getHostUrl(req));
        if (StringUtils.isNotEmpty(req.getContextPath())) {
            path.append(req.getContextPath());
        }
        return path.toString();
    }

    /**
     * アクセスしてきたユーザのIPを取得
     * 
     * @param req request
     * @return ip
     */
    public static String getRemoteAddr(HttpServletRequest req) {
        String ip = req.getRemoteAddr();
        String ip1 = req.getHeader("x-forwarded-for");
        if (ip1 == null || ip1.length() < 4) {
            ip1 = req.getHeader("INTEL_SOURCE_IP");
        }
        if (!StringUtils.isEmpty(ip1)) {
            ip = ip1;
        }
        return ip;
    }

    /**
     * ロケールをセッションにセット
     * 
     * @param request request
     * @param locale locale
     */
    public static void setLocale(HttpServletRequest request, Locale locale) {
        HttpSession session = request.getSession();
        session.setAttribute(CommonWebParameter.LOCALE_SESSION_KEY, locale);

        // ログインユーザ情報があれば、自身の言語を設定
        LoginedUser loginedUser = getLoginedUser(request);
        if (loginedUser != null) {
            if (loginedUser.getUserId() > Integer.MIN_VALUE) {
                LocalesDao localesDao = LocalesDao.get();
                LocalesEntity localesEntity = localesDao.selectOrCreate(locale);
                if (localesEntity == null) {
                    return;
                }
                UsersDao usersDao = UsersDao.get();
                UsersEntity user = usersDao.selectOnKey(loginedUser.getUserId());
                if (user != null) {
                    user.setLocaleKey(localesEntity.getKey());
                    usersDao.update(user);
                }
            }
        }
    }

    /**
     * ロケールを取得
     * 
     * @param request request
     * @return locale
     */
    public static Locale getLocale(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Locale locale = (Locale) session.getAttribute(CommonWebParameter.LOCALE_SESSION_KEY);
        if (locale == null) {
            LoginedUser loginedUser = getLoginedUser(request);
            if (loginedUser != null) {
                // 前にまえに言語設定を登録してあった場合、それを復元
                if (loginedUser.getUserId() > Integer.MIN_VALUE) {
                    UsersDao usersDao = UsersDao.get();
                    UsersEntity user = usersDao.selectOnKey(loginedUser.getUserId());
                    if (user != null) {
                        String localekay = user.getLocaleKey();
                        LocalesDao localesDao = LocalesDao.get();
                        LocalesEntity localesEntity = localesDao.selectOnKey(localekay);
                        if (localesEntity != null) {
                            locale = new Locale(localesEntity.getLanguage(), localesEntity.getCountry(), localesEntity.getVariant());
                        }
                    }
                }
            }
        }
        if (locale == null) {
            locale = request.getLocale();
            if (locale != null) {
                setLocale(request, locale);
            }
        }
        if (locale == null) {
            locale = Locale.getDefault();
        }
        return locale;
    }

    /**
     * 指定のキーのCookie値を取得
     * 
     * @param request request
     * @param key key
     * @return value
     */
    public static String getCookie(HttpServletRequest request, String key) {
        return getCookie(request, key, "");
    }

    /**
     * 指定のキーのCookie値を取得
     * 
     * @param request request
     * @param key key
     * @param defaultValue defaultValue
     * @return value
     */
    public static String getCookie(HttpServletRequest request, String key, String defaultValue) {
        StringBuilder name = new StringBuilder();
        name.append(AppConfig.get().getSystemName()).append("_").append(key);
        try {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (!cookie.getName().equals(name.toString())) {
                        continue;
                    }

                    String value = cookie.getValue();
                    value = URLDecoder.decode(value, "UTF-8");
                    return value;
                }
            }
            return defaultValue;
        } catch (UnsupportedEncodingException e) {
            log.error("Get cokkie value error. [name]:" + name.toString(), e);
            return defaultValue;
        }
    }
    
    /**
     * 指定のキーのCookieをセット
     * 稼働しているシステムで一意にするために、システム名称にラベルを付けてキーとする
     * @param request request
     * @param response response
     * @param key key
     * @param value value
     */
    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String key, String value) {
        StringBuilder name = new StringBuilder();
        name.append(AppConfig.get().getSystemName()).append("_").append(key);
        try {
            String cookieValue = URLEncoder.encode(value, "UTF-8");
            Cookie cookie = new Cookie(name.toString(), cookieValue);
            cookie.setPath(request.getContextPath() + "/");
            cookie.setMaxAge(COOKIE_AGE);
            response.addCookie(cookie);
        } catch (UnsupportedEncodingException e) {
            log.error("Set cokkie value error. [name]:" + name.toString(), e);
        }
    }

    public static Object getRequestInfo(HttpServletRequest request) {
        StringBuilder builder = new StringBuilder();
        builder.append(getContextUrl(request));
        builder.append(request.getServletPath());
        return builder.toString();
    }

}
