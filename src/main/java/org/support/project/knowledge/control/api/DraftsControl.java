package org.support.project.knowledge.control.api;

import net.arnx.jsonic.JSONException;

import java.util.ArrayList;
import java.util.List;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.PropertyUtil;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.dao.DraftKnowledgesDao;
import org.support.project.knowledge.entity.DraftKnowledgesEntity;
import org.support.project.knowledge.vo.api.Draft;
import org.support.project.web.bean.ApiParams;
import org.support.project.web.boundary.Boundary;
import org.support.project.web.common.HttpStatus;
import org.support.project.web.control.GetApiControl;
import org.support.project.web.control.service.Delete;
import org.support.project.web.control.service.Get;
import org.support.project.web.control.service.Post;
import org.support.project.web.control.service.Put;
import org.support.project.web.exception.InvalidParamException;
import org.support.project.web.filter.ControlManagerFilter;

@DI(instance = Instance.Prototype)
public class DraftsControl extends GetApiControl {
    private static Log LOG = LogFactory.getLog(ControlManagerFilter.class);

    @Get(path="api/drafts", publishToken="")
    public Boundary index() {
        return get();
    }

    @Override
    public Boundary getList(ApiParams params) {
        List<DraftKnowledgesEntity> draftsEntity = DraftKnowledgesDao.get().selectOnUser(super.getLoginUserId());
        List<Draft> results = new ArrayList<>();

        for (DraftKnowledgesEntity draftEntity : draftsEntity) {
            Draft draft = new Draft();
            PropertyUtil.copyPropertyValue(draftEntity, draft);
            results.add(draft);
        }

        return send(HttpStatus.SC_200_OK, results);
    }

    @Override
    public Boundary getSingle(String id) {
        // TODO Auto-generated method stub
        return null;
    }
}
