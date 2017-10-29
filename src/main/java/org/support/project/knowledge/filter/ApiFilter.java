package org.support.project.knowledge.filter;

import java.io.IOException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.support.project.common.util.DateUtils;
import org.support.project.common.util.StringUtils;
import org.support.project.di.Container;
import org.support.project.knowledge.dao.TokensDao;
import org.support.project.knowledge.entity.TokensEntity;
import org.support.project.knowledge.logic.KnowledgeAuthenticationLogic;
import org.support.project.web.bean.LoginedUser;
import org.support.project.web.common.HttpStatus;
import org.support.project.web.dao.UsersDao;
import org.support.project.web.entity.UsersEntity;
import org.support.project.web.logic.AuthenticationLogic;
import org.support.project.web.wrapper.HttpServletRequestWrapper;

public class ApiFilter implements Filter {
    private String targetRegex = "(^/api)(.)*";
    private Pattern pattern;
    private boolean enableAuthParameter = true; // TODO 後でfalseへ

    @Override
    public void init(final FilterConfig filterconfig) throws ServletException {
        String targetRegex = filterconfig.getInitParameter("target-regex");
        if (StringUtils.isNotEmpty(targetRegex)) {
            this.targetRegex = targetRegex;
        }
        this.pattern = Pattern.compile(this.targetRegex);
        
        String authParam = filterconfig.getInitParameter("enable-auth-parameter");
        if (StringUtils.isNotEmpty(authParam)) {
            if (authParam.toLowerCase().equals("true")) {
                enableAuthParameter = true;
            }
        }
    }
    @Override
    public void destroy() {
        this.targetRegex = null;
    }

    @Override
    public void doFilter(ServletRequest servletrequest, ServletResponse servletresponse, FilterChain filterchain)
            throws IOException, ServletException {
        AuthenticationLogic<LoginedUser> authenticationLogic = (AuthenticationLogic) Container.getComp(KnowledgeAuthenticationLogic.class);
        HttpServletRequest req_origin = (HttpServletRequest) servletrequest;
        HttpServletRequestWrapper req = new HttpServletRequestWrapper((HttpServletRequest) req_origin, authenticationLogic);
        HttpServletResponse res = (HttpServletResponse) servletresponse;
        
        StringBuilder pathBuilder = new StringBuilder();
        pathBuilder.append(req.getServletPath());
        if (req.getPathInfo() != null && req.getPathInfo().length() > 0) {
            pathBuilder.append(req.getPathInfo());
        }
        String path = pathBuilder.toString();
        Matcher matcher = pattern.matcher(path);
        if (!matcher.find()) {
            // 対象外
            filterchain.doFilter(req, res);
            return;
        }
        // 認証
        // Httpヘッダー「PRIVATE-TOKEN」か、リクエストパラメータ「private_token」の値で認証することを検討（GitLab準拠）
        // → クエリパラメータ指定だとアクセスログにtoken情報が表示されてしまい良くない気がするので、デフォルトはHttpヘッダー指定のみにする（設定で有効にもできる）
        String token = req.getHeader("PRIVATE-TOKEN");
        if (enableAuthParameter) {
            if (StringUtils.isEmpty(token)) {
                token = req.getParameter("private_token");
            }
        }
        if (StringUtils.isEmpty(token)) {
            // Tokenが指定されていない
            res.sendError(HttpStatus.SC_403_FORBIDDEN);
            return;
        }
        TokensEntity tokensEntity = TokensDao.get().selectOnKey(token);
        if (tokensEntity == null) {
            res.sendError(HttpStatus.SC_403_FORBIDDEN);
            return;
        }
        Date now = DateUtils.now();
        if (now.getTime() > tokensEntity.getExpires().getTime()) {
            res.sendError(HttpStatus.SC_403_FORBIDDEN);
            return;
        }
        // APIを使う場合、Tokensテーブルの「更新日付」現在の日付でアップデートする（最終利用日時）
        TokensDao.get().update(tokensEntity);
        // セッション生成
        UsersEntity user = UsersDao.get().selectOnKey(tokensEntity.getUserId());
        if (user == null) {
            res.sendError(HttpStatus.SC_403_FORBIDDEN);
            return;
        }
        // 毎回セッションを生成して登録する（毎回なので少し重いかも）
        authenticationLogic.setSession(user.getUserKey(), req, res);
        
        // APIの実際の処理を実施
        filterchain.doFilter(req, res);
        
        // リクエスト毎にセッションはクリアする
        authenticationLogic.clearSession(req);
    }


}
