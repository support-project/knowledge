package org.support.project.knowledge.control.api.internal.drafts;

import java.util.List;

import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.dao.DraftKnowledgesDao;
import org.support.project.knowledge.entity.DraftKnowledgesEntity;
import org.support.project.web.bean.ApiParams;
import org.support.project.web.boundary.Boundary;
import org.support.project.web.common.HttpStatus;
import org.support.project.web.control.ApiControl;
import org.support.project.web.control.service.Get;

@DI(instance = Instance.Prototype)
public class GetDraftListApiControl extends ApiControl {
    /**
     * 下書き一覧
     * @throws Exception 
     */
    @Get(path="_api/drafts")
    public Boundary execute() throws Exception {
        ApiParams apiParams = super.getCommonApiParams();
        int limit = apiParams.getLimit();
        int offset = apiParams.getOffset();
        List<DraftKnowledgesEntity> drafts = DraftKnowledgesDao.get().selectOnUser(
                super.getLoginUserId(), limit, offset);
        return send(HttpStatus.SC_200_OK, drafts);
    }
}
