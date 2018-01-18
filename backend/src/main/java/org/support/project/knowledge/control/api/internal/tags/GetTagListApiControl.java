package org.support.project.knowledge.control.api.internal.tags;

import java.lang.invoke.MethodHandles;
import java.util.List;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.dao.TagsDao;
import org.support.project.knowledge.entity.TagsEntity;
import org.support.project.web.bean.ApiParams;
import org.support.project.web.boundary.Boundary;
import org.support.project.web.control.ApiControl;
import org.support.project.web.control.service.Get;
import org.support.project.web.logic.invoke.Open;

@DI(instance = Instance.Prototype)
public class GetTagListApiControl extends ApiControl {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(MethodHandles.lookup());
    /**
     * タグの一覧を取得
     * @throws Exception 
     */
    @Get(path="_api/tags")
    @Open
    public Boundary execute() throws Exception {
        LOG.trace("call _api/tags");
        String keyword = super.getParam("keyword");
        ApiParams apiParams = super.getCommonApiParams();
        int limit = apiParams.getLimit();
        int offset = apiParams.getOffset();
        // タグに紐付いているナレッジの件数でデータをソートして取得(ナレッジへのアクセス件は考慮しない)
        List<TagsEntity> tags = TagsDao.get().selectWithKnowledgeCountOnTagName(keyword, offset * limit, limit);
        return send(tags);
    }
}
