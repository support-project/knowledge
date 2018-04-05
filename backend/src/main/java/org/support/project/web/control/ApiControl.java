package org.support.project.web.control;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;

import org.support.project.common.config.AppConfig;
import org.support.project.common.config.Resources;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.StringUtils;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.config.SystemConfig;
import org.support.project.web.bean.ApiParams;
import org.support.project.web.bean.Msg;
import org.support.project.web.boundary.Boundary;
import org.support.project.web.boundary.JsonBoundary;
import org.support.project.web.common.HttpStatus;
import org.support.project.web.common.HttpStatusMsg;
import org.support.project.web.common.HttpUtil;
import org.support.project.web.dao.SystemConfigsDao;
import org.support.project.web.entity.SystemConfigsEntity;
import org.support.project.web.exception.InvalidParamException;

@DI(instance = Instance.Prototype)
public abstract class ApiControl extends Control {
    /** ログ */
    private static Log LOG = LogFactory.getLog(MethodHandles.lookup());

    protected static final Msg _OK = new Msg("OK");
    
    private boolean isSetPaginationHeaders = false;
    
    protected <T> T parseJson(Class<T> type) throws InvalidParamException {
        return HttpUtil.parseJson(getRequest(), type); 
    }
    protected String getResource(String key) {
        Resources resources = Resources.getInstance(HttpUtil.getLocale(getRequest()));
        return resources.getResource(key);
    }
    protected String getResource(String key, String... params) {
        Resources resources = Resources.getInstance(HttpUtil.getLocale(getRequest()));
        return resources.getResource(key, params);
    }
    
    protected Boundary sendError(int status) {
        return send(status, new Msg(HttpStatusMsg.getMsg(status)));
    }
    protected Boundary sendError(InvalidParamException e) {
        if (e.getMessageResult() != null) {
            return send(e.getMessageResult().getCode(), e.getMessageResult());
        } else {
            return this.sendError(HttpStatus.SC_400_BAD_REQUEST);
        }
    }
    
    /**
     * JSONでオブジェクト送信
     * 
     * @param json json
     * @param status status
     * @return JsonBoundary
     */
    protected JsonBoundary send(int status, Object json) {
        if (json instanceof List) {
            if (!isSetPaginationHeaders) {
                LOG.warn("Api result is list. but is not set pagination headers.");
            }
        }
        return super.send(status, json);
    }
    
    
    /**
     * Set some response headers for pagination.
     * 
     * X-Total The total number of items
     * X-Offset The offset of the current
     * X-Limit  The number of items per 1 request
     * 
     * X-First-Offset The offset of the first
     * X-Last-Offset The offset of the last
     * X-Next-Offset The offset of the next(nullable)
     * X-Previous-Offset The offset of the previous(nullable)
     * 
     * X-Page1-Label The display label of the page1
     * X-Page2-Label The display label of the page2 (nullable)
     * X-Page3-Label The display label of the page3 (nullable)
     * X-Page4-Label The display label of the page4 (nullable)
     * X-Page5-Label The display label of the page5 (nullable)
     * 
     * X-Page1-Offset The offset of the page1
     * X-Page2-Offset The offset of the page2 (nullable)
     * X-Page3-Offset The offset of the page3 (nullable)
     * X-Page4-Offset The offset of the page4 (nullable)
     * X-Page5-Offset The offset of the page5 (nullable)
     * 
     * X-Current-URL The URL of the current(This URL may be different from the actual one, because parameters such as offset can be omitted)
     * X-First-URL The URL of the first
     * X-Last-URL The URL of the last
     * X-Next-URL The URL of the next(nullable)
     * X-Previous-URL The URL of the previous(nullable)
     * 
     * @param total
     * @param currentOffset
     * @param limit
     */
    protected void setPaginationHeaders(long total, long currentOffset, int limit) {
        getResponse().addHeader("X-Total", String.valueOf(total));
        getResponse().addHeader("X-Offset", String.valueOf(currentOffset));
        getResponse().addHeader("X-Limit", String.valueOf(limit));
        
        // システムURL
        SystemConfigsDao dao = SystemConfigsDao.get();
        SystemConfigsEntity config = dao.selectOnKey(SystemConfig.SYSTEM_URL, AppConfig.get().getSystemName());
        StringBuilder url = new StringBuilder();
        if (config == null) {
            url.append(HttpUtil.getContextUrl(getRequest()));
        } else {
            url.append(config.getConfigValue());
        }
        if (url.toString().endsWith("/")) {
            url.delete(url.length(), url.length());
        }
        
        url.append(getRequest().getServletPath());
        if (StringUtils.isNotEmpty(getRequest().getPathInfo())) {
            url.append(getRequest().getPathInfo());
        }
        
        String query = getRequest().getQueryString();
        LinkedHashMap<String, String> params = new LinkedHashMap<>();
        Enumeration<String> paramKeys = getRequest().getParameterNames();
        while (paramKeys.hasMoreElements()) {
            String key = (String) paramKeys.nextElement();
            if (key.equals("offset") || key.equals("limit")) {
                continue;
            }
            String value = getRequest().getParameter(key);
            if (StringUtils.isEmpty(value)) {
                continue;
            }
            if (query.indexOf(key) != -1) {
                params.put(key, value);
            }
        }
        params.put("limit", String.valueOf(limit));
        
        LinkedHashMap<String, String> current = new LinkedHashMap<>(params);
        current.put("offset", String.valueOf(currentOffset));
        StringBuilder currentURL = new StringBuilder(url.toString());
        currentURL.append(HttpUtil.buildQueryParams(current, false));
        getResponse().addHeader("X-Current-URL", currentURL.toString());
        
        LinkedHashMap<String, String> firtt = new LinkedHashMap<>(params);
        firtt.put("offset", String.valueOf(0));
        StringBuilder firttURL = new StringBuilder(url.toString());
        firttURL.append(HttpUtil.buildQueryParams(firtt, false));
        getResponse().addHeader("X-First-Offset", String.valueOf(0));
        getResponse().addHeader("X-First-URL", firttURL.toString());
        
        long lastOffset = total / limit;
        lastOffset = lastOffset * limit;
        LinkedHashMap<String, String> last = new LinkedHashMap<>(params);
        last.put("offset", String.valueOf(lastOffset));
        StringBuilder lastURL = new StringBuilder(url.toString());
        lastURL.append(HttpUtil.buildQueryParams(last, false));
        getResponse().addHeader("X-Last-Offset", String.valueOf(lastOffset));
        getResponse().addHeader("X-Last-URL", lastURL.toString());
        
        long nextOffset = currentOffset + limit;
        if (total > nextOffset) {
            LinkedHashMap<String, String> next = new LinkedHashMap<>(params);
            next.put("offset", String.valueOf(nextOffset));
            StringBuilder nextURL = new StringBuilder(url.toString());
            nextURL.append(HttpUtil.buildQueryParams(next, false));
            getResponse().addHeader("X-Next-Offset", String.valueOf(nextOffset));
            getResponse().addHeader("X-Next-URL", nextURL.toString());
        }
        
        if (currentOffset > 0) {
            long prevOffset = currentOffset - limit;
            if (prevOffset < 0) {
                prevOffset = 0;
            }
            LinkedHashMap<String, String> prev = new LinkedHashMap<>(params);
            prev.put("offset", String.valueOf(prevOffset));
            StringBuilder prevURL = new StringBuilder(url.toString());
            prevURL.append(HttpUtil.buildQueryParams(prev, false));
            getResponse().addHeader("X-Previous-Offset", String.valueOf(prevOffset));
            getResponse().addHeader("X-Previous-URL", prevURL.toString());
        }
        
        // ページコントロール
        int prevCount = 0;
        for (int i = 1; i <= 2; i++) {
            if (currentOffset - (limit * i) >= 0) {
                prevCount ++;
            }
        }
        int nextCount = 0;
        for (int i = 1; i <= (4 - prevCount); i++) {
            if (currentOffset + (limit * i) < total) {
                nextCount ++;
            }
        }
        if (nextCount < 2) {
            for (int i = 3; i <= 4 - nextCount; i++) {
                if (currentOffset - (limit * i) >= 0) {
                    prevCount ++;
                }
            }
        }
        List<Long> offsets = new ArrayList<>();
        for (int i = prevCount; i > 0; i--) {
            offsets.add(currentOffset - (limit * i));
        }
        offsets.add(currentOffset);
        for (int i = 1; i <= nextCount; i++) {
            offsets.add(currentOffset + (limit * i));
        }
        int pageIndex = 1;
        for (Long long1 : offsets) {
            getResponse().addHeader("X-Page" + pageIndex + "-Offset", String.valueOf(long1));
            
            long pageLabel = long1 / limit;
            pageLabel = pageLabel + 1; // ページは0スタートでない
            getResponse().addHeader("X-Page" + pageIndex + "-label", String.valueOf(pageLabel));
            
            pageIndex++;
        }
    }
    
    protected ApiParams getCommonApiParams() {
        ApiParams param = new ApiParams();
        param.setLimit(getParamInt("limit", 10, 100));
        param.setOffset(getParamInt("offset", 0, -1));
        return param;
    }
}
