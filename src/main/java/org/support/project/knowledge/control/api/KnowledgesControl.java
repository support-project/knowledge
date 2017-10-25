package org.support.project.knowledge.control.api;

import java.util.List;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.StringUtils;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.logic.KnowledgeDataEditLogic;
import org.support.project.knowledge.logic.KnowledgeDataSelectLogic;
import org.support.project.knowledge.vo.SearchKnowledgeParam;
import org.support.project.knowledge.vo.api.Knowledge;
import org.support.project.knowledge.vo.api.KnowledgeDetail;
import org.support.project.web.bean.ApiParams;
import org.support.project.web.bean.Msg;
import org.support.project.web.bean.NameId;
import org.support.project.web.boundary.Boundary;
import org.support.project.web.common.HttpStatus;
import org.support.project.web.control.ApiControl;
import org.support.project.web.control.GetApiControl;
import org.support.project.web.control.service.Delete;
import org.support.project.web.control.service.Get;
import org.support.project.web.control.service.Post;
import org.support.project.web.control.service.Put;
import org.support.project.web.exception.InvalidParamException;
import org.support.project.web.filter.ControlManagerFilter;

import net.arnx.jsonic.JSONException;

@DI(instance = Instance.Prototype)
public class KnowledgesControl extends GetApiControl {
    /** ログ */
    private static Log LOG = LogFactory.getLog(ControlManagerFilter.class);
    
    @Get(path="api/knowledges", publishToken="")
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
    
    /**
     * Post knowledges
     */
    @Post(path="api/knowledges", checkReferer=false, subscribeToken="")
    public Boundary post() {
        try {
            KnowledgeDetail data = getJsonObject(KnowledgeDetail.class);
            long id = KnowledgeDataEditLogic.get().insert(data, getLoginedUser());
            return send(HttpStatus.SC_201_CREATED, new NameId(data.getTitle(), String.valueOf(id)));
        } catch (JSONException e) {
            LOG.debug("json parse error", e);
            return sendError(HttpStatus.SC_400_BAD_REQUEST);
        } catch (InvalidParamException e) {
            return sendError(e);
        } catch (Exception e) {
            LOG.error("error", e);
            return sendError(HttpStatus.SC_500_INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Put knowledges
     */
    @Put(path="api/knowledges", checkReferer=false, subscribeToken="")
    public Boundary put() {
        try {
            Long id = getPathLong();
            KnowledgeDetail data = getJsonObject(KnowledgeDetail.class);
            data.setKnowledgeId(id);
            KnowledgeDataEditLogic.get().update(data, getLoginedUser());
            return send(HttpStatus.SC_200_OK, new Msg("updated"));
        } catch (JSONException e) {
            LOG.debug("json parse error", e);
            return sendError(HttpStatus.SC_400_BAD_REQUEST);
        } catch (InvalidParamException e) {
            return sendError(e);
        } catch (Exception e) {
            LOG.error("error", e);
            return sendError(HttpStatus.SC_500_INTERNAL_SERVER_ERROR);
        }
    }

    
    /**
     * Delete knowledges
     */
    @Delete(path="api/knowledges", checkReferer=false, subscribeToken="")
    public Boundary delete() {
        try {
            Long id = getPathLong();
            KnowledgeDataEditLogic.get().delete(id, getLoginedUser());
            return send(HttpStatus.SC_200_OK, new Msg("deleted"));
        } catch (JSONException e) {
            LOG.debug("json parse error", e);
            return sendError(HttpStatus.SC_400_BAD_REQUEST);
        } catch (InvalidParamException e) {
            return sendError(e);
        } catch (Exception e) {
            LOG.error("error", e);
            return sendError(HttpStatus.SC_500_INTERNAL_SERVER_ERROR);
        }
    }
    
    
}
