package org.support.project.knowledge.control.api.internal.articles.comments;

import java.util.List;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.StringUtils;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.dao.CommentsDao;
import org.support.project.knowledge.entity.CommentsEntity;
import org.support.project.knowledge.logic.KnowledgeLogic;
import org.support.project.knowledge.logic.SanitizeMarkdownTextLogic;
import org.support.project.web.boundary.Boundary;
import org.support.project.web.common.HttpStatus;
import org.support.project.web.control.ApiControl;
import org.support.project.web.control.service.Get;
import org.support.project.web.logic.invoke.Open;

@DI(instance = Instance.Prototype)
public class GetArticleCommentsApiControl extends ApiControl {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(GetArticleCommentsApiControl.class);
    /**
     * 記事の一覧を取得
     * @throws Exception 
     */
    @Get(path="_api/articles/:id/comments")
    @Open
    public Boundary comments() throws Exception {
        LOG.trace("access user: " + getLoginUserId());
        String id = super.getParam("id");
        LOG.debug(id);
        if (!StringUtils.isLong(id)) {
            return sendError(HttpStatus.SC_400_BAD_REQUEST);
        }
        long knowledgeId = Long.parseLong(id);
        if (KnowledgeLogic.get().select(knowledgeId, getLoginedUser()) == null) {
            // 存在しない or アクセス権無し
            return sendError(HttpStatus.SC_404_NOT_FOUND);
        }
        List<CommentsEntity> comments = CommentsDao.get().selectOnKnowledgeId(knowledgeId);
        for (CommentsEntity comment : comments) {
            comment.setComment(SanitizeMarkdownTextLogic.get().sanitize(comment.getComment()));
        }
        super.setSendEscapeHtml(false);
        return send(HttpStatus.SC_200_OK, comments);
    }
    
}
