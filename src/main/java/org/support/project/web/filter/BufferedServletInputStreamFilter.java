package org.support.project.web.filter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
import org.support.project.web.wrapper.BufferedServletRequestWrapper;

/**
 * request.getInputStream()は、通常1回しか呼べないが、 デバッグの為に、何回も呼びたいという事がある。 その時に利用するフィルタ(通常は使わない)
 */
@Deprecated
public class BufferedServletInputStreamFilter implements Filter {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(BufferedServletInputStreamFilter.class);

    public void destroy() {
    }

    public void init(FilterConfig arg0) throws ServletException {
    }

    public void doFilter(ServletRequest servletrequest, ServletResponse servletresponse, FilterChain filterchain)
            throws IOException, ServletException {
        HttpServletRequest httpreq = (HttpServletRequest) servletrequest;
        BufferedServletRequestWrapper req = new BufferedServletRequestWrapper(httpreq);
        HttpServletResponse res = (HttpServletResponse) servletresponse;

        if (LOG.isDebugEnabled()) {
            // リクエストの中身のデバッグ
            BufferedReader reader = new BufferedReader(new InputStreamReader(req.getInputStream(), "UTF-8"));
            // BufferedReader reader= req.getReader();
            String line;
            StringBuilder builder = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            reader.close();
            LOG.debug("[RequestBody]  " + httpreq.getRequestURL());
            LOG.debug("" + builder.toString());
        }

        filterchain.doFilter(req, res);
    }

}
