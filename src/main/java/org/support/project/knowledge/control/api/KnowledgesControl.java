package org.support.project.knowledge.control.api;

import java.util.List;

import org.support.project.common.util.StringUtils;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.logic.KnowledgeDataSelectLogic;
import org.support.project.knowledge.vo.SearchKnowledgeParam;
import org.support.project.knowledge.vo.api.Knowledge;
import org.support.project.web.bean.ApiParams;
import org.support.project.web.boundary.Boundary;
import org.support.project.web.common.HttpStatus;
import org.support.project.web.control.ApiControl;
import org.support.project.web.control.service.Get;

@DI(instance = Instance.Prototype)
public class KnowledgesControl extends ApiControl {
    /**
     * List knowledges
     */
    @Get(path="api/knowledges")
    public Boundary index() {
        return get();
    }

    @Override
    public Boundary getList(ApiParams params) {
        SearchKnowledgeParam param = new SearchKnowledgeParam();
        param.setLimit(params.getLimit());
        param.setOffset(params.getOffset());
        param.setKeyword(getParam("keyword"));
        param.setTags(getParam("tags"));
        param.setGroupsAndLoginUser(getParam("groups"), getLoginedUser());
        param.setTemplate(getParam("template"));
        try {
            List<Knowledge> results = KnowledgeDataSelectLogic.get().selectList(param);
            return send(HttpStatus.SC_200_OK, results);
        } catch (Exception e) {
            return sendError(HttpStatus.SC_500_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Boundary getSingle(String id) {
        // 1件取得
        if (!StringUtils.isLong(id)) {
            return sendError(HttpStatus.SC_400_BAD_REQUEST);
        }
        long knowledgeId = Long.parseLong(id);
        Knowledge result = KnowledgeDataSelectLogic.get().select(knowledgeId, getLoginedUser());
        if (result == null) {
            return sendError(HttpStatus.SC_404_NOT_FOUND);
        }
        return send(HttpStatus.SC_200_OK, result);
    }
    
    
}
