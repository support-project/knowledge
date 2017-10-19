package org.support.project.web.filter;

import java.io.IOException;
import java.util.Date;
import java.util.Enumeration;
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
import org.support.project.common.util.DateUtils;
import org.support.project.common.util.StringUtils;
import org.support.project.web.common.HttpStatus;
import org.support.project.web.common.HttpUtil;

public class LoggingFilter implements Filter {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(LoggingFilter.class);

    /** 対象外なパスの正規表現 */
    private String ignoreRegularExpression = "css$|js$|jpg$|jpeg$|gif$|png$|init$";
    private Pattern pattern = null;

    @Override
    public void init(FilterConfig filterconfig) throws ServletException {
        String ignoreRegularExpression = filterconfig.getInitParameter("ignore-regular-expression");
        if (StringUtils.isNotEmpty(ignoreRegularExpression)) {
            this.ignoreRegularExpression = ignoreRegularExpression;
        }
        if (StringUtils.isNotEmpty(this.ignoreRegularExpression)) {
            this.pattern = Pattern.compile(this.ignoreRegularExpression);
        }
    }

    @Override
    public void destroy() {
        this.ignoreRegularExpression = null;
        this.pattern = null;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        Date start = DateUtils.now();

        // if (LOG.isTraceEnabled()) {
        // if (request instanceof HttpServletRequest) {
        // request = new BufferedServletRequestWrapper((HttpServletRequest) request);
        // }
        // }

        // 処理へ
        try {
            chain.doFilter(request, response);
        } catch (Throwable e) {
            LOG.error("Any exception is thrown. [" + e.getClass().getName() + "]", e);
            request.setAttribute("SERVER_EXCEPTION", e);
        } finally {
            Date end = DateUtils.now();

            if (request instanceof HttpServletRequest && response instanceof HttpServletResponse) {
                HttpServletRequest req = (HttpServletRequest) request;
                HttpServletResponse res = (HttpServletResponse) response;

                StringBuilder pathBuilder = new StringBuilder();
                pathBuilder.append(req.getServletPath());
                if (req.getPathInfo() != null && req.getPathInfo().length() > 0) {
                    pathBuilder.append(req.getPathInfo());
                }
                if (pattern != null) {
                    Matcher matcher = pattern.matcher(pathBuilder.toString());
                    if (matcher.find() && (res.getStatus() == HttpStatus.SC_200_OK || res.getStatus() == HttpStatus.SC_304_NOT_MODIFIED)) {
                        // 対象外なのでスルー
                        return;
                    }
                }

                StringBuilder builder = new StringBuilder();

                if (LOG.isInfoEnabled()) {
                    builder.append(getDefaultRequestInfo(start, end, req, res));
                }
                if (LOG.isDebugEnabled()) {
                    builder.append("\n");
                    builder.append(getParamInfo(req));
                    builder.append("\n");
                    builder.append(getCookieInfo(req));
                }

                if (builder.length() > 0) {
                    if (LOG.isInfoEnabled()) {
                        LOG.info(builder.toString());
                    } else if (LOG.isDebugEnabled()) {
                        LOG.debug(builder.toString());
                    }
                }
            }
        }
    }

    private String getCookieInfo(HttpServletRequest req) {
        StringBuilder infos = new StringBuilder();
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                infos.append("\n[cookies] ").append(cookie.getName()).append("=").append(cookie.getValue());
            }
        } else {
            infos.append(" none.");
        }

        return infos.toString();
    }

    private String getParamInfo(HttpServletRequest req) {
        StringBuilder paramInfos = new StringBuilder();
        // リクエストパラメータデバッグ
        Enumeration<String> params = req.getParameterNames();
        while (params.hasMoreElements()) {
            String string = (String) params.nextElement();
            String[] values = req.getParameterValues(string);
            paramInfos.append("[param]").append(string).append(":");
            if (values == null) {
                paramInfos.append("");
            } else {
                int count = 0;
                for (String val : values) {
                    if (count > 0) {
                        paramInfos.append(",");
                    }
                    paramInfos.append(val);
                    count++;
                }
            }
            paramInfos.append("\n");
        }
        return paramInfos.toString();
    }

    private String getDefaultRequestInfo(Date start, Date end, HttpServletRequest req, HttpServletResponse res) {
        StringBuilder path = new StringBuilder();

        path.append(req.getMethod());
        path.append("\t");

        String contextUrl = HttpUtil.getContextUrl(req);
        path.append(contextUrl);

        if (StringUtils.isNotEmpty(req.getServletPath())) {
            path.append(req.getServletPath());
        }
        if (StringUtils.isNotEmpty(req.getPathInfo())) {
            path.append(req.getPathInfo());
        }
        if (StringUtils.isNotEmpty(req.getQueryString())) {
            path.append("?");
            path.append(req.getQueryString());
        }

        path.append("\t").append(res.getStatus());
        String ip = HttpUtil.getRemoteAddr(req);
        path.append("\t").append(ip);

        long time = end.getTime() - start.getTime();
        path.append("\t").append(time).append("[ms]");
        return path.toString();
    }

}
