package org.support.project.knowledge.control.api.internal.articles.comments.likes;

import java.lang.invoke.MethodHandles;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.StringUtils;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.logic.KnowledgeLogic;
import org.support.project.knowledge.logic.LikeLogic;
import org.support.project.knowledge.vo.LikeCount;
import org.support.project.web.boundary.Boundary;
import org.support.project.web.common.HttpStatus;
import org.support.project.web.control.ApiControl;
import org.support.project.web.control.service.Post;
import org.support.project.web.logic.invoke.Open;

@DI(instance = Instance.Prototype)
public class PostArticleCommentLikeApiControl extends ApiControl {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(MethodHandles.lookup());
    /**
     * 記事の一覧を取得
     * @throws Exception 
     */
    @Post(path="_api/articles/:id/comments/:commentid/likes", checkCookieToken=false, checkHeaderToken=true)
    @Open
    public Boundary execute() throws Exception {
        LOG.trace("access user: " + getLoginUserId());
        String id = super.getParam("id");
        LOG.debug(id);
        if (!StringUtils.isLong(id)) {
            return sendError(HttpStatus.SC_400_BAD_REQUEST);
        }
        String commentid = super.getParam("commentid");
        if (!StringUtils.isLong(commentid)) {
            return sendError(HttpStatus.SC_400_BAD_REQUEST);
        }
        
        long knowledgeId = Long.parseLong(id);
        if (KnowledgeLogic.get().select(knowledgeId, getLoginedUser()) == null) {
            // 存在しない or アクセス権無し
            return sendError(HttpStatus.SC_404_NOT_FOUND);
        }
        
        Long commentNo = new Long(commentid);
        Long count = LikeLogic.get().addLikeComment(commentNo, getLoginedUser(), getLocale());
        LikeCount likeCount = new LikeCount();
        likeCount.setKnowledgeId(knowledgeId);
        likeCount.setCount(count);
        
        return send(HttpStatus.SC_200_OK, likeCount);
    }

}
