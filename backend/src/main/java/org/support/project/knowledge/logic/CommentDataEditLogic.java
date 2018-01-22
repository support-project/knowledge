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
    public Comment update(Comment comment, AccessUser accessUser) throws Exception {
        CommentsEntity db = checkUpdateAble(comment, accessUser);
        db.setComment(comment.getComment());
        List<Long> fileNos = new ArrayList<>();
        for (AttachedFile file : comment.getAttachments()) {
            fileNos.add(file.getFileNo());
        }
        KnowledgeLogic.get().updateComment(db, fileNos, accessUser);
        return comment;
    }
    /**
     * コメントのステータス（表示／非表示）を保存する
     * @param comment
     * @param geAccessUser
     * @return
     * @throws SendErrorException 
     */
    public Comment updateCollapse(Comment comment, AccessUser accessUser) throws Exception {
        CommentsEntity db = checkUpdateAble(comment, accessUser);
        db.setComment(comment.getComment());
        List<Long> fileNos = new ArrayList<>();
        for (AttachedFile file : comment.getAttachments()) {
            fileNos.add(file.getFileNo());
        }
        
        db.setCommentStatus(comment.getCommentStatus());
        CommentsDao.get().physicalUpdate(db); // 更新履歴は付けないで更新
        return comment;
    }
    /**
     * 指定のコメントを更新できる権限かチェック
     * @param comment
     * @param loginedUser
     * @return
     * @throws SendErrorException
     */
    private CommentsEntity checkUpdateAble(Comment comment, AccessUser accessUser) throws SendErrorException {
        LOG.trace("insert");
        if (!accessUser.isLogined()) {
            throw new SendErrorException(HttpStatus.SC_403_FORBIDDEN, "FORBIDDEN");
        }
        KnowledgesEntity check = KnowledgeLogic.get().select(comment.getKnowledgeId(), accessUser);
        if (check == null) {
            // 存在しない or アクセス権無し
            throw new SendErrorException(HttpStatus.SC_403_FORBIDDEN, "FORBIDDEN");
        }
        if (StringUtils.isEmpty(comment.getComment())) {
            throw new SendErrorException(HttpStatus.SC_400_BAD_REQUEST, accessUser.getMsg("errors.required", "Comment"));
        }
        
        CommentsEntity db = CommentsDao.get().selectOnKey(comment.getCommentNo());
        if (db == null) {
            throw new SendErrorException(HttpStatus.SC_404_NOT_FOUND, "NOT_FOUND");
        }
        
        // コメントを更新できるか権限チェック
        // コメント登録者／管理者／記事の編集者はコメントを更新できる
        List<LabelValue> editors = TargetLogic.get().selectEditorsOnKnowledgeId(db.getKnowledgeId());
        if (!accessUser.isAdmin()) {
            if (accessUser.getUserId().intValue() != db.getInsertUser().intValue()
                    && !KnowledgeLogic.get().isEditor(accessUser, check, editors))
                throw new SendErrorException(HttpStatus.SC_403_FORBIDDEN, "FORBIDDEN");
        }
        return db;
    }

    
    
    

}
