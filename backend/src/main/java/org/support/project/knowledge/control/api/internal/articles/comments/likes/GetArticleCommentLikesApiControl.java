package org.support.project.knowledge.control.api.internal.articles.comments.likes;

import java.lang.invoke.MethodHandles;
import java.util.List;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.StringUtils;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.dao.LikeCommentsDao;
import org.support.project.knowledge.entity.LikeCommentsEntity;
import org.support.project.knowledge.logic.KnowledgeLogic;
import org.support.project.web.bean.ApiParams;
import org.support.project.web.boundary.Boundary;
import org.support.project.web.common.HttpStatus;
import org.support.project.web.control.ApiControl;
import org.support.project.web.control.service.Get;
import org.support.project.web.logic.invoke.Open;

@DI(instance = Instance.Prototype)
public class GetArticleCommentLikesApiControl extends ApiControl {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(MethodHandles.lookup());
    /**
     * 記事のイイネの一覧を取得
     * @throws Exception 
     */
    @Get(path="_api/articles/:id/comments/:commentid/likes")
    @Open
    public Boundary execute() throws Exception {
        LOG.trace("access user: " + getLoginUserId());
        String id = super.getParam("id");
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
        
        ApiParams apiParams = super.getCommonApiParams();
        int limit = apiParams.getLimit();
        int offset = apiParams.getOffset();
        
        Long commentNo = new Long(commentid);
        List<LikeCommentsEntity> likes = LikeCommentsDao.get().selectOnCommentNo(commentNo, offset, limit);
        return send(HttpStatus.SC_200_OK, likes);
    }
    
}
