package org.support.project.knowledge.control.api.internal.articles;

import java.lang.invoke.MethodHandles;
import java.util.List;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.logic.KnowledgeDataSelectLogic;
import org.support.project.knowledge.vo.SearchKnowledgeParam;
import org.support.project.knowledge.vo.api.Knowledge;
import org.support.project.knowledge.vo.api.internal.KnowledgeList;
import org.support.project.web.boundary.Boundary;
import org.support.project.web.common.HttpStatus;
import org.support.project.web.control.ApiControl;
import org.support.project.web.control.service.Get;
import org.support.project.web.logic.invoke.Open;

@DI(instance = Instance.Prototype)
public class GetArticleListApiControl extends ApiControl {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(MethodHandles.lookup());
    /**
     * 記事の一覧を取得
     * @throws Exception 
     */
    @Get(path="_api/articles")
    @Open
    public Boundary articles() throws Exception {
        LOG.trace("access user: " + getLoginUserId());
        SearchKnowledgeParam param = new SearchKnowledgeParam();
        param.setLimit(getParamInt("limit", 50, 50));
        param.setOffset(getParamInt("offset", 0, -1));
        param.setKeyword(getParam("keyword"));
        param.setTags(getParam("tags"));
        param.setGroupsAndLoginUser(getParam("groups"), getLoginedUser());
        param.setCreators(getParam("creators"));
        param.setTemplates(getParam("templates"));
        List<Knowledge> results = KnowledgeDataSelectLogic.get().selectList(param);
        List<KnowledgeList> list =  KnowledgeDataSelectLogic.get().convInternalList(results, getLoginedUser());
        return send(HttpStatus.SC_200_OK, list);
    }
}
