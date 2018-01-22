package org.support.project.knowledge.logic;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

import org.support.project.common.exception.ParseException;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.PropertyUtil;
import org.support.project.common.util.StringUtils;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.dao.CommentsDao;
import org.support.project.knowledge.entity.CommentsEntity;
import org.support.project.knowledge.entity.KnowledgesEntity;
import org.support.project.knowledge.vo.api.AttachedFile;
import org.support.project.knowledge.vo.api.Comment;
import org.support.project.web.bean.AccessUser;
import org.support.project.web.bean.LabelValue;
import org.support.project.web.common.HttpStatus;
import org.support.project.web.exception.SendErrorException;

@DI(instance = Instance.Singleton)
public class CommentDataEditLogic {
    /** LOG */
    private static final Log LOG = LogFactory.getLog(MethodHandles.lookup());
    /** Get instance */
    public static CommentDataEditLogic get() {
        return Container.getComp(CommentDataEditLogic.class);
    }
    
    /**
     * APIで取得した情報をDBの形に変換をかける
     * @param comment
     * @return
     * @throws ParseException
     */
    public CommentsEntity conv(Comment comment) throws ParseException {
        CommentsEntity entity = new CommentsEntity();
        PropertyUtil.copyPropertyValue(comment, entity);
        return entity;
    }
    
    /**
     * コメント登録
     * @param comment
     * @param loginedUser
     * @return
     * @throws Exception 
     */
    public Comment insert(Comment comment, AccessUser loginedUser) throws Exception {
        LOG.trace("insert");
        if (!loginedUser.isLogined()) {
            throw new SendErrorException(HttpStatus.SC_403_FORBIDDEN, "FORBIDDEN");
        }
        if (KnowledgeLogic.get().select(comment.getKnowledgeId(), loginedUser) == null) {
            // 存在しない or アクセス権無し
            throw new SendErrorException(HttpStatus.SC_403_FORBIDDEN, "FORBIDDEN");
        }
        if (StringUtils.isEmpty(comment.getComment())) {
            throw new SendErrorException(HttpStatus.SC_400_BAD_REQUEST, loginedUser.getMsg("errors.required", "Comment"));
        }
        List<Long> fileNos = new ArrayList<>();
        for (AttachedFile file : comment.getAttachments()) {
            fileNos.add(file.getFileNo());
        }
        CommentsEntity entity = KnowledgeLogic.get().saveComment(comment.getKnowledgeId(), comment.getComment(), fileNos, loginedUser);
        return CommentDataSelectLogic.get().getComment(comment.getKnowledgeId(), entity.getCommentNo(), loginedUser);
    }
    /**
     * コメント更新
     * @param comment
     * @param loginedUser
     * @return
     * @throws Exception
     */
    public Comment update(Comment comment, AccessUser loginedUser) throws Exception {
        LOG.trace("insert");
        if (!loginedUser.isLogined()) {
            throw new SendErrorException(HttpStatus.SC_403_FORBIDDEN, "FORBIDDEN");
        }
        KnowledgesEntity check = KnowledgeLogic.get().select(comment.getKnowledgeId(), loginedUser);
        if (check == null) {
            // 存在しない or アクセス権無し
            throw new SendErrorException(HttpStatus.SC_403_FORBIDDEN, "FORBIDDEN");
        }
        if (StringUtils.isEmpty(comment.getComment())) {
            throw new SendErrorException(HttpStatus.SC_400_BAD_REQUEST, loginedUser.getMsg("errors.required", "Comment"));
        }
        
        CommentsEntity db = CommentsDao.get().selectOnKey(comment.getCommentNo());
        if (db == null) {
            throw new SendErrorException(HttpStatus.SC_404_NOT_FOUND, "NOT_FOUND");
        }
        
        // コメントを更新できるか権限チェック
        // コメント登録者／管理者／記事の編集者はコメントを更新できる
        List<LabelValue> editors = TargetLogic.get().selectEditorsOnKnowledgeId(db.getKnowledgeId());
        if (!loginedUser.isAdmin()) {
            if (loginedUser.getUserId().intValue() != db.getInsertUser().intValue()
                    && !KnowledgeLogic.get().isEditor(loginedUser, check, editors))
                throw new SendErrorException(HttpStatus.SC_403_FORBIDDEN, "FORBIDDEN");
        }
        db.setComment(comment.getComment());
        List<Long> fileNos = new ArrayList<>();
        for (AttachedFile file : comment.getAttachments()) {
            fileNos.add(file.getFileNo());
        }
        KnowledgeLogic.get().updateComment(db, fileNos, loginedUser);
        return comment;
    }
    
    
    

}
