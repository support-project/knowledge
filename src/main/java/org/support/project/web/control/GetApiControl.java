package org.support.project.web.control;

import org.support.project.common.util.StringUtils;
import org.support.project.web.bean.ApiParams;
import org.support.project.web.boundary.Boundary;

public abstract class GetApiControl extends ApiControl {

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

