package org.support.project.web.filter;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.RandomUtil;
import org.support.project.common.util.StringUtils;
import org.support.project.web.common.HttpStatus;
import org.support.project.web.common.HttpUtil;
import org.support.project.web.config.CommonWebParameter;
import org.support.project.web.dao.UsersDao;
import org.support.project.web.entity.UsersEntity;
import org.support.project.web.exception.AuthenticateException;
import org.support.project.web.logic.AuthenticationLogic;
import org.support.project.web.wrapper.HttpServletRequestWrapper;

/**
 * 認証用のフィルタ
 * 
 * @author Koda
 *
 */
public class AuthenticationFilter implements Filter {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(AuthenticationFilter.class);

    /** ログインの処理を実施するサーブレットパス */
    private String loginProcess = "/signin";
    /** ログイン後の初期ページ(リダイレクト) */
    private String initialPage = "/index";
    /** 認可エラーURL */
    private String authorizerErrorUri = "/authorizer_error";
    /** ログアウトの処理を実施するサーブレットパス */
    private String logoutProcess = "/signout";
    /** ログアウト後の初期ページ(リダイレクト) */
    private String outPage = "/index";

    /** ログイン画面 */
    private String loginPage = "/WEB-INF/view/auth/form.jsp";
    /** ログイン失敗画面 */
    private String loginErrorPage = "/WEB-INF/view/auth/form.jsp";
    /** 認可エラー画面 */
    private String authorizerErrorPage = "/WEB-INF/view/auth/authorizerError.jsp";
    // /** ログインを促すインフォメーション画面 */
    // private String loginInformationPage = "a/WEB-INF/view/auth/information.jsp";

    /** ログインしていなくてもアクセス可能なパスの正規表現 */
    private String ignoreRegularExpression = "^open|css$|js$|jpg$|jpeg$|gif$|png$|init$";
    private Pattern pattern = null;

    /** 認証／認可ロジックのクラス名 */
    private String authLogicClassName = "org.support.project.web.logic.impl.DBAuthenticationLogic";
    /** 認証／認可ロジックのインスタンス */
    private AuthenticationLogic<?> authenticationLogic = null;

    // 認証情報をクッキーに保持
    private int cookieMaxAge = -1; // 日にち単位
    private String cookieEncryptKey = "";
    private boolean cookieSecure = true;

    @Override
    public void init(final FilterConfig filterconfig) throws ServletException {
        String loginPage = filterconfig.getInitParameter("login-page");
        if (StringUtils.isNotEmpty(loginPage)) {
            this.loginPage = loginPage;
        }
        String loginErrorPage = filterconfig.getInitParameter("login-error-page");
        if (StringUtils.isNotEmpty(loginErrorPage)) {
            this.loginErrorPage = loginErrorPage;
        }
        // String loginInformationPage = filterconfig.getInitParameter("login-information-page");
        // if (StringUtils.isNotEmpty(loginInformationPage)) {
        // this.loginInformationPage = loginInformationPage;
        // }

        String loginProcess = filterconfig.getInitParameter("login-process");
        if (StringUtils.isNotEmpty(loginProcess)) {
            this.loginProcess = loginProcess;
        }
        String logoutProcess = filterconfig.getInitParameter("logout-process");
        if (StringUtils.isNotEmpty(logoutProcess)) {
            this.logoutProcess = logoutProcess;
        }

        String initialPage = filterconfig.getInitParameter("initial-page");
        if (StringUtils.isNotEmpty(initialPage)) {
            this.initialPage = initialPage;
        }
        String outPage = filterconfig.getInitParameter("out-page");
        if (StringUtils.isNotEmpty(outPage)) {
            this.outPage = outPage;
        }
        String authorizerErrorPage = filterconfig.getInitParameter("authorizer-error-page");
        if (StringUtils.isNotEmpty(authorizerErrorPage)) {
            this.authorizerErrorPage = authorizerErrorPage;
        }

        String ignoreRegularExpression = filterconfig.getInitParameter("ignore-regular-expression");
        if (StringUtils.isNotEmpty(ignoreRegularExpression)) {
            this.ignoreRegularExpression = ignoreRegularExpression;
        }
        if (StringUtils.isNotEmpty(this.ignoreRegularExpression)) {
            this.pattern = Pattern.compile(this.ignoreRegularExpression);
        }

        String authLogicClassName = filterconfig.getInitParameter("auth-class-name");
        if (StringUtils.isNotEmpty(authLogicClassName)) {
            this.authLogicClassName = authLogicClassName;
        }
        try {
            Class<AuthenticationLogic> class1;
            class1 = (Class<AuthenticationLogic>) Class.forName(this.authLogicClassName);
            this.authenticationLogic = org.support.project.di.Container.getComp(class1);
        } catch (ClassNotFoundException e) {
            throw new ServletException(e);
        }

        String cookieage = filterconfig.getInitParameter("cookie-max-age");
        if (StringUtils.isInteger(cookieage)) {
            cookieMaxAge = Integer.parseInt(cookieage) * 60 * 60 * 24; // 日にち単位で設定するので計算する（setMaxAgeは秒単位)
        }
        cookieEncryptKey = filterconfig.getInitParameter("cookie-encrypt-key");
        if (StringUtils.isEmpty(cookieEncryptKey)) {
            cookieEncryptKey = RandomUtil.randamGen(24);
        }

        String secure = filterconfig.getInitParameter("cookie-secure");
        if (secure != null && secure.toLowerCase().equals("false")) {
            cookieSecure = false;
        }
        authenticationLogic.initCookie(cookieMaxAge, cookieEncryptKey, cookieSecure);
    }

    @Override
    public void destroy() {
        this.loginPage = null;
        this.loginErrorPage = null;
        // this.loginInformationPage = null;
        this.loginProcess = null;
        this.initialPage = null;
        this.ignoreRegularExpression = null;
        this.pattern = null;
        this.authLogicClassName = null;
        this.authenticationLogic = null;
    }

    @Override
    public void doFilter(ServletRequest servletrequest, ServletResponse servletresponse, FilterChain filterchain)
            throws IOException, ServletException {
        HttpServletRequest req_origin = (HttpServletRequest) servletrequest;
        HttpServletRequestWrapper req = new HttpServletRequestWrapper((HttpServletRequest) req_origin, authenticationLogic);

        HttpServletResponse res = (HttpServletResponse) servletresponse;

        try {

            StringBuilder pathBuilder = new StringBuilder();
            pathBuilder.append(req.getServletPath());
            if (req.getPathInfo() != null && req.getPathInfo().length() > 0) {
                pathBuilder.append(req.getPathInfo());
            }
            String path = pathBuilder.toString();
            // LOG.trace(path);

            if (!isLogin(req)) {
                authenticationLogic.cookieLogin(req, res);
            }

            if (pattern != null) {
                Matcher matcher = pattern.matcher(path);
                if (matcher.find()) {
                    // 対象外なのでスルー
                    isLogin(req);
                    filterchain.doFilter(req, res);
                    return;
                }
            }
            if (path.equals("/")) {
                this.changePage(req, res);
                return;
            } else if (path.equals(loginProcess)) {
                if (req.getMethod().toLowerCase().equals("get")) {
                    LOG.trace("SC_401_UNAUTHORIZED");
                    String page = req.getParameter("page");
                    req.setAttribute("page", page);

                    res.setStatus(HttpStatus.SC_401_UNAUTHORIZED);
                    StringBuilder builder = new StringBuilder();
                    builder.append(loginPage);
                    HttpUtil.forward(res, req, builder.toString());
                    return;
                } else {
                    // Post
                    // ログイン処理のパスなので、ログイン実施
                    if (doLogin(req, res)) {
                        // ログイン情報をCookieに保持
                        authenticationLogic.setCookie(req, res);

                        // ログイン成功
                        this.changePage(req, res);
                        return;
                    } else {
                        // ログイン失敗
                        // ユーザIDやパスワード等をリクエストスコープへ
                        String username = req.getParameter("username");
                        String password = req.getParameter("password");
                        String page = req.getParameter("page");
                        req.setAttribute("username", username);
                        req.setAttribute("password", password);
                        req.setAttribute("page", page);
                        req.setAttribute("loginError", true);

                        // ログイン失敗時には、レスポンスを返すまでに時間をかける
                        synchronized (req) {
                            Thread.sleep(2000); // 2秒待つ
                        }
                        HttpUtil.forward(res, req, loginErrorPage);
                        return;
                    }
                }
            } else if (path.equals(logoutProcess)) {
                // ログアウト
                authenticationLogic.clearSession(req);
                // クッキー削除
                removeCookie(req, res);

                // ログアウト後の画面へリダイレクト
                LOG.trace("sign out");
                // HttpUtil.forward(res, req, loginErrorPage);
                StringBuilder builder = new StringBuilder();
                builder.append(req.getContextPath());
                builder.append(outPage);
                HttpUtil.redirect(res, req, builder.toString());
                return;
            } else if (path.startsWith(loginPage)) {
                // ログイン画面を表示
                filterchain.doFilter(req, res);
                return;
            } else if (path.startsWith(authorizerErrorUri)) {
                LOG.trace("SC_403_FORBIDDEN");
                res.setStatus(HttpStatus.SC_403_FORBIDDEN);
                // 認可エラー画面を表示
                // filterchain.doFilter(req, res);
                HttpUtil.forward(res, req, authorizerErrorPage);
                return;
            }

            if (!isLogin(req)) {
                LOG.trace("ログインしていません");

                boolean login = authenticationLogic.cookieLogin(req, res);
                if (login) {
                    filterchain.doFilter(req, res);
                    return;
                }

                StringBuilder builder = new StringBuilder();
                builder.append(req.getContextPath());

                // builder.append(loginPage);
                builder.append(loginProcess);

                builder.append("?page=").append(req.getServletPath());
                // HttpUtil.forward(res, req, builder.toString());
                HttpUtil.redirect(res, req, builder.toString());
                return;
            }
            // ログイン済みならば、FilterChainを行う
            LOG.trace("ログイン済み");
            // 認可チェック
            if (!isAuthorizer(req)) {
                // 認可無し
                StringBuilder builder = new StringBuilder();
                builder.append(req.getContextPath());
                // builder.append(authorizerErrorPage);
                // builder.append("?page=").append(req.getServletPath());

                builder.append(authorizerErrorUri);
                HttpUtil.redirect(res, req, builder.toString());
                return;
            }

            filterchain.doFilter(req, res);
        } catch (AuthenticateException e) {
            // 認可エラー画面を表示
            // res.setStatus(HttpStatus.SC_500_INTERNAL_SERVER_ERROR);
            // HttpUtil.forward(res, req, authorizerErrorPage);
            res.sendError(500);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    protected boolean isAuthorizer(HttpServletRequest req) throws Exception {
        return authenticationLogic.isAuthorize(req);
    }

    protected boolean isLogin(HttpServletRequest req) throws Exception {
        return authenticationLogic.isLogined(req);
    }

    /**
     * ログイン完了時にページ遷移(リダイレクト)
     * 
     * @param req request
     * @param res response
     * @throws ServletException ServletException
     * @throws IOException IOException
     */
    protected void changePage(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        String path = req.getParameter("page");
        if (StringUtils.isEmpty(path)) {
            path = initialPage;
        }
        if (path.equals(loginProcess)) {
            path = initialPage;
        }

        StringBuilder builder = new StringBuilder();
        builder.append(req.getContextPath());
        builder.append(path);

        HttpUtil.redirect(res, req, builder.toString());
    }

    /**
     * ログイン処理
     * 
     * @param req request
     * @return result
     * @throws Exception Exception
     */
    protected boolean doLogin(HttpServletRequest req, HttpServletResponse res) throws Exception {
        String userkey = req.getParameter("username");
        String password = req.getParameter("password");

        int userId = authenticationLogic.auth(userkey, password);
        if (userId >= 0) {
            // セッションにログイン情報を格納
            LOG.debug(userId + " is Login.");
            // ActiveDirectoryでは、ログインIDは大文字・小文字を判定しないので、DBに格納されているIDを取得
            UsersEntity usersEntity = UsersDao.get().selectOnKey(userId);
            if (usersEntity == null) {
                // なぜかユーザ情報が無い
                return false;
            }
            if (!userkey.equals(usersEntity.getUserKey())) {
                userkey = usersEntity.getUserKey();
            }
            authenticationLogic.setSession(userkey, req, res);
            return true;
        }
        return false;
    }





    /**
     * 認証のCookieを削除
     * 
     * @param req request
     * @param res response
     */
    private void removeCookie(HttpServletRequestWrapper req, HttpServletResponse res) {
        // Cookie削除
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            Cookie cookie = new Cookie(CommonWebParameter.LOGIN_USER_KEY, "");
            cookie.setPath(req.getContextPath() + "/");
            cookie.setMaxAge(0);
            res.addCookie(cookie);
        }
    }

    /**
     * get login page url
     * 
     * @return the loginProcess
     */
    protected String getLoginProcess() {
        return loginProcess;
    }

    /**
     * get after login page url
     * 
     * @return the loginPage
     */
    protected String getLoginPage() {
        return loginPage;
    }

}
