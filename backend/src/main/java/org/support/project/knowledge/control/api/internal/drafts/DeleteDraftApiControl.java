package org.support.project.knowledge.control.api.internal.drafts;

import java.lang.invoke.MethodHandles;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.StringUtils;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.logic.DraftDataLogic;
import org.support.project.web.boundary.Boundary;
import org.support.project.web.common.HttpStatus;
import org.support.project.web.control.ApiControl;
import org.support.project.web.control.service.Delete;

@DI(instance = Instance.Prototype)
public class DeleteDraftApiControl extends ApiControl {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(MethodHandles.lookup());
    /**
     * 下書き削除
     * @throws Exception 
     */
    @Delete(path="_api/drafts/:id")
    public Boundary execute() throws Exception {
        LOG.trace("Delete draft");
        String id = super.getParam("id");
        LOG.debug(id);
        if (!StringUtils.isLong(id)) {
            return sendError(HttpStatus.SC_400_BAD_REQUEST);
        }
        long draftId = new Long(id);
        DraftDataLogic.get().deleteDraft(draftId, getAccessUser());
        return send(HttpStatus.SC_200_OK, "Removed");
    }
}
