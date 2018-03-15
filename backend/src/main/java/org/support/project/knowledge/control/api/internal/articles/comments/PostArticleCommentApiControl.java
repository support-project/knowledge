package org.support.project.knowledge.control.api.internal.articles.comments;

import java.lang.invoke.MethodHandles;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.control.api.internal.articles.AbstractArticleApi;
import org.support.project.knowledge.logic.CommentDataEditLogic;
import org.support.project.knowledge.vo.api.Comment;
import org.support.project.web.boundary.Boundary;
import org.support.project.web.common.HttpStatus;
import org.support.project.web.control.service.Post;
import org.support.project.web.exception.InvalidParamException;
import org.support.project.web.exception.SendErrorException;

@DI(instance = Instance.Prototype)
public class PostArticleCommentApiControl extends AbstractArticleApi {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(MethodHandles.lookup());
    /**
     * コメントを登録
     * @throws Exception 
     */
    @Post(path="_api/articles/:id/comments", checkCookieToken=false, checkHeaderToken=true)
    public Boundary execute() throws Exception {
        LOG.debug("post");
        long knowledgeId = getRouteArticleId();
        Comment comment = super.parseJson(Comment.class);
        comment.setKnowledgeId(knowledgeId);
        try {
            comment = CommentDataEditLogic.get().insert(comment, getAccessUser());
        } catch (InvalidParamException e) {
            return sendError(e);
        } catch (SendErrorException e) {
            return send(e.getHttpStatus(), e.getMsg());
        }
        return send(HttpStatus.SC_200_OK, comment);
    }

}
