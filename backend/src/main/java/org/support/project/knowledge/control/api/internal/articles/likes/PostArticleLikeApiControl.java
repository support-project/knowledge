package org.support.project.knowledge.control.api.internal.articles.likes;

import java.lang.invoke.MethodHandles;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.control.api.internal.articles.AbstractArticleApi;
import org.support.project.knowledge.logic.KnowledgeLogic;
import org.support.project.knowledge.logic.LikeLogic;
import org.support.project.knowledge.vo.LikeCount;
import org.support.project.web.boundary.Boundary;
import org.support.project.web.common.HttpStatus;
import org.support.project.web.control.service.Post;
import org.support.project.web.logic.invoke.Open;

@DI(instance = Instance.Prototype)
public class PostArticleLikeApiControl extends AbstractArticleApi {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(MethodHandles.lookup());
    /**
     * いいね
     * @throws Exception 
     */
    @Open
    @Post(path="_api/articles/:id/likes", checkCookieToken=false, checkHeaderToken=true)
    public Boundary execute() throws Exception {
        LOG.trace("access user: " + getLoginUserId());
        long knowledgeId = super.getRouteArticleId();
        if (KnowledgeLogic.get().select(knowledgeId, getLoginedUser()) == null) {
            // 存在しない or アクセス権無し
            return sendError(HttpStatus.SC_404_NOT_FOUND);
        }
        
        Long count = LikeLogic.get().addLike(knowledgeId, getLoginedUser(), getLocale());
        LikeCount likeCount = new LikeCount();
        likeCount.setKnowledgeId(knowledgeId);
        likeCount.setCount(count);
        
        return send(HttpStatus.SC_200_OK, likeCount);
    }

}
