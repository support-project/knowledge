package org.support.project.knowledge.logic;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

import org.support.project.common.exception.ParseException;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.PropertyUtil;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.dao.CommentsDao;
import org.support.project.knowledge.dao.KnowledgeFilesDao;
import org.support.project.knowledge.dao.LikeCommentsDao;
import org.support.project.knowledge.entity.CommentsEntity;
import org.support.project.knowledge.entity.KnowledgeFilesEntity;
import org.support.project.knowledge.vo.api.AttachedFile;
import org.support.project.knowledge.vo.api.Comment;
import org.support.project.web.bean.AccessUser;

@DI(instance = Instance.Singleton)
public class CommentDataSelectLogic {
    /** LOG */
    private static final Log LOG = LogFactory.getLog(MethodHandles.lookup());
    /** Get instance */
    public static CommentDataSelectLogic get() {
        return Container.getComp(CommentDataSelectLogic.class);
    }
    
    /**
     * DBのコメント情報をAPIでやり取りする形に変換
     * 本文のMarkdown形式の文はサニタイズする
     * @param commentsEntity
     * @return
     * @throws ParseException 
     */
    public Comment conv(CommentsEntity commentsEntity) throws ParseException {
        Comment comment = new Comment();
        PropertyUtil.copyPropertyValue(commentsEntity, comment);
        comment.setComment(SanitizeMarkdownTextLogic.get().sanitize(comment.getComment()));
        return comment;
    }
    
    /**
     * 指定の記事に対するコメント取得
     * 権限チェックしていない
     * @param knowledgeId
     * @return
     * @throws ParseException
     */
    public List<Comment> getComments(long knowledgeId) throws ParseException {
        List<Comment> comments = new ArrayList<>();
        List<CommentsEntity> commentsEntities = CommentsDao.get().selectOnKnowledgeId(knowledgeId);
        for (CommentsEntity commentsEntity : commentsEntities) {
            Long likeCount = LikeCommentsDao.get().selectOnCommentNo(commentsEntity.getCommentNo());
            commentsEntity.setLikeCount(likeCount);
            comments.add(conv(commentsEntity));
        }
        return comments;
    }
    
    /**
     * 指定の記事に対するコメント取得
     * 権限チェックありで、権限が無い場合空の配列が返る
     * @param knowledgeId
     * @param loginedUser
     * @return
     * @throws ParseException
     */
    public List<Comment> getComments(long knowledgeId, AccessUser loginedUser) throws ParseException {
        LOG.trace("getComments");
        List<Comment> comments = new ArrayList<>();
        if (KnowledgeLogic.get().select(knowledgeId, loginedUser) == null) {
            // 存在しない or アクセス権無し
            return comments;
        }
        return getComments(knowledgeId);
    }
    
    /**
     * 指定のコメントの情報を取得
     * 権限チェックありで、権限が無い場合Nullが返る
     * @param knowledgeId
     * @param commentNo
     * @param loginedUser
     * @return
     * @throws ParseException
     */
    public Comment getComment(long knowledgeId, Long commentNo, AccessUser loginedUser) throws ParseException {
        LOG.trace("getComment");
        if (KnowledgeLogic.get().select(knowledgeId, loginedUser) == null) {
            // 存在しない or アクセス権無し
            return null;
        }
        CommentsEntity commentsEntity = CommentsDao.get().selectWithUserName(commentNo);
        if (commentsEntity == null) {
            return null;
        }
        Long likeCount = LikeCommentsDao.get().selectOnCommentNo(commentsEntity.getCommentNo());
        commentsEntity.setLikeCount(likeCount);
        Comment comment = conv(commentsEntity);
        List<AttachedFile> attachedFiles = new ArrayList<>();
        List<KnowledgeFilesEntity> filesEntities = KnowledgeFilesDao.get().selectOnKnowledgeId(knowledgeId);
        for (KnowledgeFilesEntity knowledgeFilesEntity : filesEntities) {
            if (commentsEntity.getCommentNo().equals(knowledgeFilesEntity.getCommentNo())) {
                AttachedFile file = new AttachedFile();
                PropertyUtil.copyPropertyValue(knowledgeFilesEntity, file);
                attachedFiles.add(file);
            }
        }
        comment.setAttachments(attachedFiles);;
        return comment;
    }


}
