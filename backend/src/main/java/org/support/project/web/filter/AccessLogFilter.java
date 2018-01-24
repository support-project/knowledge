package org.support.project.web.filter;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
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

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.StringUtils;
import org.support.project.web.common.HttpStatus;
import org.support.project.web.dao.AccessLogsDao;
import org.support.project.web.entity.AccessLogsEntity;

public class AccessLogFilter implements Filter {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(MethodHandles.lookup());

    /** 対象外なパスの正規表現 */
    private String ignoreRegularExpression = "css$|js$|jpg$|jpeg$|gif$|png$|init$";
    private Pattern pattern = null;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String ignoreRegularExpression = filterConfig.getInitParameter("ignore-regular-expression");
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
        if (!(request instanceof HttpServletRequest)) {
            chain.doFilter(request, response);
            return;
        }
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        StringBuilder pathBuilder = new StringBuilder();
        pathBuilder.append(req.getServletPath());
        if (req.getPathInfo() != null && req.getPathInfo().length() > 0) {
            pathBuilder.append(req.getPathInfo());
        }
        String path = pathBuilder.toString();

        if (pattern != null) {
            Matcher matcher = pattern.matcher(path);
            if (matcher.find() && (res.getStatus() == HttpStatus.SC_200_OK || res.getStatus() == HttpStatus.SC_304_NOT_MODIFIED)) {
                // 対象外なのでスルー
                chain.doFilter(request, response);
                return;
            }
        }

        String ip = req.getRemoteAddr();
        String ip1 = req.getHeader("x-forwarded-for");
        if (ip1 == null || ip1.length() < 4) {
            ip1 = req.getHeader("INTEL_SOURCE_IP");
        }

        if (!StringUtils.isEmpty(ip1)) {
            ip = ip1;
        }

        String userAgent = req.getHeader("User-Agent");

        AccessLogsDao accessLogsDao = AccessLogsDao.get();
        AccessLogsEntity accessLogsEntity = new AccessLogsEntity();
        accessLogsEntity.setIpAddress(ip);
        accessLogsEntity.setPath(path);
        accessLogsEntity.setUserAgent(userAgent);
        accessLogsDao.insert(accessLogsEntity);

        chain.doFilter(request, response);
        return;
    }

}
