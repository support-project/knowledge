package org.support.project.knowledge.control.api.internal.articles.comments;

import java.lang.invoke.MethodHandles;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.StringUtils;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.control.api.internal.articles.AbstractArticleApi;
import org.support.project.knowledge.logic.CommentDataSelectLogic;
import org.support.project.knowledge.vo.api.Comment;
import org.support.project.web.boundary.Boundary;
import org.support.project.web.common.HttpStatus;
import org.support.project.web.control.service.Get;
import org.support.project.web.logic.invoke.Open;

@DI(instance = Instance.Prototype)
public class GetArticleCommentOneApiControl extends AbstractArticleApi {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(MethodHandles.lookup());
    /**
     * コメントを取得
     * @throws Exception 
     */
    @Get(path="_api/articles/:id/comments/:commentid")
    @Open
    public Boundary execute() throws Exception {
        LOG.trace("access user: " + getLoginUserId());
        long knowledgeId = getRouteArticleId();
        
        String commentid = super.getRouteParam("commentid");
        if (!StringUtils.isLong(commentid)) {
            return sendError(HttpStatus.SC_400_BAD_REQUEST);
        }
        Long commentNo = new Long(commentid);
        Comment comment = CommentDataSelectLogic.get().getComment(knowledgeId, commentNo, getLoginedUser());
        if (comment == null) {
            return sendError(HttpStatus.SC_404_NOT_FOUND);
        }
        super.setSendEscapeHtml(false);
        return send(HttpStatus.SC_200_OK, comment);
    }

}
