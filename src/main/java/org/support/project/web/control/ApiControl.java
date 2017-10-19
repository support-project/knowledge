package org.support.project.web.control;

import org.support.project.common.config.Resources;
import org.support.project.common.util.StringUtils;
import org.support.project.web.bean.ApiParams;
import org.support.project.web.bean.Msg;
import org.support.project.web.boundary.Boundary;
import org.support.project.web.common.HttpStatus;
import org.support.project.web.common.HttpStatusMsg;
import org.support.project.web.common.HttpUtil;
import org.support.project.web.exception.InvalidParamException;

public abstract class ApiControl extends Control {
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
    
    public abstract Boundary getList(ApiParams params);
    public abstract Boundary getSingle(String id);
    public int maxLimit() {
        return 50;
    }
    
    protected ApiParams getApiParams() {
         // 一覧取得
        int limit = 10;
        int offset = 0;
        String limitStr = getParam("limit");
        if (StringUtils.isInteger(limitStr)) {
            limit = Integer.parseInt(limitStr);
        }
        if (limit > maxLimit()) {
            limit = maxLimit();
        }
        String offsetStr = getParam("offset");
        if (StringUtils.isInteger(offsetStr)) {
            offset = Integer.parseInt(offsetStr);
        }
        ApiParams params = new ApiParams();
        params.setLimit(limit);
        params.setOffset(offset);
        return params;
    }
    
    /**
     * APIの基本的なGetのパターンを処理
     * 上の getList or getSingle が呼び出される
     * @return
     */
    protected Boundary get() {
        String id = super.getPathString("");
        if (StringUtils.isEmpty(id)) {
            ApiParams params = getApiParams();
            return getList(params);
        } else {
            // 1件取得
            return getSingle(id);
        }
    }
    
}
