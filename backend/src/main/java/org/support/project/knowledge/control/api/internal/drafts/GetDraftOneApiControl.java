package org.support.project.knowledge.control.api.internal.drafts;

import java.lang.invoke.MethodHandles;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.StringUtils;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.dao.DraftKnowledgesDao;
import org.support.project.knowledge.entity.DraftKnowledgesEntity;
import org.support.project.knowledge.logic.DraftDataLogic;
import org.support.project.knowledge.vo.api.Knowledge;
import org.support.project.web.boundary.Boundary;
import org.support.project.web.common.HttpStatus;
import org.support.project.web.control.ApiControl;
import org.support.project.web.control.service.Get;

@DI(instance = Instance.Prototype)
public class GetDraftOneApiControl extends ApiControl {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(MethodHandles.lookup());
    /**
     * 下書き
     * @throws Exception 
     */
    @Get(path="_api/drafts/:id")
    public Boundary execute() throws Exception {
        LOG.trace("access user: " + getLoginUserId());
        String id = super.getRouteParam("id");
        LOG.debug(id);
        if (!StringUtils.isLong(id)) {
            return sendError(HttpStatus.SC_400_BAD_REQUEST);
        }
        boolean parseMarkdown = false;
        String parse = getParam("parse");
        if (parse != null && parse.toLowerCase().equals("true")) {
            parseMarkdown = true;
        }
        boolean sanitize = true;
        String s = getParam("sanitize");
        if (s != null && s.toLowerCase().equals("false")) {
            sanitize = false;
        }
        long draftId = Long.parseLong(id);
        DraftKnowledgesEntity draft = DraftKnowledgesDao.get().selectOnKey(draftId);
        if (draft == null) {
            return sendError(HttpStatus.SC_400_BAD_REQUEST);
        }
        Knowledge result = DraftDataLogic.get().convDraft(draft, parseMarkdown, sanitize);
        if (result == null) {
            return sendError(HttpStatus.SC_404_NOT_FOUND);
        }
        super.setSendEscapeHtml(false);
        return send(HttpStatus.SC_200_OK, result);
    }

}
