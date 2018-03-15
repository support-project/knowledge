package org.support.project.knowledge.control.api.internal.articles.comments;

import java.lang.invoke.MethodHandles;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.StringUtils;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.control.api.internal.articles.AbstractArticleApi;
import org.support.project.knowledge.logic.CommentDataEditLogic;
import org.support.project.knowledge.vo.api.Comment;
import org.support.project.web.boundary.Boundary;
import org.support.project.web.common.HttpStatus;
import org.support.project.web.control.service.Put;
import org.support.project.web.exception.InvalidParamException;
import org.support.project.web.exception.SendErrorException;

@DI(instance = Instance.Prototype)
public class PutArticleCommentDisplayApiControl extends AbstractArticleApi {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(MethodHandles.lookup());
    /**
     * コメントを登録
     * @throws Exception 
     */
    @Put(path="_api/articles/:id/comments/:commentid/collapse", checkCookieToken=false, checkHeaderToken=true)
    public Boundary execute() throws Exception {
        LOG.debug("post");
        long knowledgeId = getRouteArticleId();
        String commentid = super.getRouteParam("commentid");
        if (!StringUtils.isLong(commentid)) {
            return sendError(HttpStatus.SC_400_BAD_REQUEST);
        }
        Long commentNo = new Long(commentid);
        Comment comment = super.parseJson(Comment.class);
        comment.setKnowledgeId(knowledgeId);
        comment.setCommentNo(commentNo);
        try {
            comment = CommentDataEditLogic.get().updateCollapse(comment, getAccessUser());
        } catch (InvalidParamException e) {
            return sendError(e);
        } catch (SendErrorException e) {
            return send(e.getHttpStatus(), e.getMsg());
        }
        return send(HttpStatus.SC_200_OK, comment);
    }

}
