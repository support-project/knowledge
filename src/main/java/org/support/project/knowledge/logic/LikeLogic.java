package org.support.project.knowledge.logic;

import java.util.Locale;

import org.support.project.common.config.Resources;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.DateUtils;
import org.support.project.di.Container;
import org.support.project.knowledge.config.AppConfig;
import org.support.project.knowledge.config.SystemConfig;
import org.support.project.knowledge.dao.CommentsDao;
import org.support.project.knowledge.dao.KnowledgesDao;
import org.support.project.knowledge.dao.LikeCommentsDao;
import org.support.project.knowledge.dao.LikesDao;
import org.support.project.knowledge.dao.NotificationStatusDao;
import org.support.project.knowledge.entity.LikeCommentsEntity;
import org.support.project.knowledge.entity.LikesEntity;
import org.support.project.knowledge.entity.NotificationStatusEntity;
import org.support.project.knowledge.logic.activity.Activity;
import org.support.project.knowledge.logic.activity.ActivityLogic;
import org.support.project.web.bean.LoginedUser;
import org.support.project.web.bean.MessageResult;
import org.support.project.web.common.HttpStatus;
import org.support.project.web.config.MessageStatus;
import org.support.project.web.dao.SystemConfigsDao;
import org.support.project.web.entity.SystemConfigsEntity;
import org.support.project.web.exception.InvalidParamException;

public class LikeLogic {
    /** LOG */
    private static final Log LOG = LogFactory.getLog(LikeLogic.class);
    /** Get instance */
    public static LikeLogic get() {
        return Container.getComp(LikeLogic.class);
    }
    
    private static final int TYPE_KNOWLEDGE = 1;
    private static final int TYPE_COMMENT = 2;

    
    /**
     * イイネの重複チェックを行うかをシステム設定情報から取得
     * @return
     */
    private boolean getCheckOfLike() {
        boolean check = false;
        SystemConfigsEntity config = SystemConfigsDao.get().selectOnKey(SystemConfig.LIKE_CONFIG, AppConfig.get().getSystemName());
        if (config != null) {
            if (SystemConfig.LIKE_CONFIG_ONLY_ONE.equals(config.getConfigValue())) {
                check = true;
            }
        }
        return check;
    }

    private boolean duplicateLike(Long knowledgeId, Integer userId) {
        NotificationStatusEntity status = NotificationStatusDao.get().selectOnKey(knowledgeId, TYPE_KNOWLEDGE, userId);
        if (status == null) {
            return false;
        }
        return true;
    }
    private boolean duplicateLikeComment(Long commentNo, Integer userId) {
        NotificationStatusEntity status = NotificationStatusDao.get().selectOnKey(commentNo, TYPE_COMMENT, userId);
        if (status == null) {
            return false;
        }
        return true;
    }

    private void saveNotifyStatusLike(Long knowledgeId, Integer userId) {
        NotificationStatusEntity status = new NotificationStatusEntity(knowledgeId, TYPE_KNOWLEDGE, userId);
        status.setStatus(0);
        NotificationStatusDao.get().save(status);
    }
    private void saveNotifyStatusLikeComment(Long commentNo, Integer userId) {
        NotificationStatusEntity status = new NotificationStatusEntity(commentNo, TYPE_COMMENT, userId);
        status.setStatus(0);
        NotificationStatusDao.get().save(status);
    }
    
    
    /**
     * いいね！を追加
     * 
     * @param knowledgeId
     * @param loginedUser
     * @return
     * @throws InvalidParamException 
     */
    public Long addLike(Long knowledgeId, LoginedUser loginedUser, Locale locale) throws InvalidParamException {
        LOG.debug("start addLike");
        if (getCheckOfLike()) {
            Resources resources = Resources.getInstance(locale);
            if (loginedUser == null || loginedUser.getUserId().equals(Integer.MIN_VALUE)) {
                throw new InvalidParamException(new MessageResult(
                        MessageStatus.Warning, HttpStatus.SC_403_FORBIDDEN, resources.getResource("knowledge.likes.required.signin"), ""));
            }
            LikesEntity likesEntity = LikesDao.get().selectExistsOnUser(knowledgeId, loginedUser.getUserId());
            if (likesEntity != null) {
                throw new InvalidParamException(new MessageResult(
                        MessageStatus.Warning, HttpStatus.SC_403_FORBIDDEN, resources.getResource("knowledge.likes.duplicate"), ""));
            }
        }
        LikesDao likesDao = LikesDao.get();
        LikesEntity likesEntity = new LikesEntity();
        likesEntity.setKnowledgeId(knowledgeId);
        likesDao.insert(likesEntity);
        
        KnowledgeLogic.get().updateKnowledgeExInfo(knowledgeId);

        Long count = likesDao.countOnKnowledgeId(knowledgeId);

        // 通知
        int userId = Integer.MIN_VALUE;
        if (loginedUser != null) {
            userId = loginedUser.getUserId();
        }
        if (!duplicateLike(knowledgeId, userId)) {
            NotifyLogic.get().notifyOnKnowledgeLiked(knowledgeId, likesEntity);
            saveNotifyStatusLike(knowledgeId, userId);
        }

        ActivityLogic.get().processActivity(Activity.KNOWLEDGE_LIKE, loginedUser, DateUtils.now(),
                KnowledgesDao.get().selectOnKey(knowledgeId));

        return count;
    }

    /**
     * コメントにイイネを追加
     * @param commentNo
     * @param loginedUser
     * @return
     * @throws InvalidParamException 
     */
    public Long addLikeComment(Long commentNo, LoginedUser loginedUser, Locale locale) throws InvalidParamException {
        LOG.debug("start addLikeComment");
        if (getCheckOfLike()) {
            Resources resources = Resources.getInstance(locale);
            if (loginedUser == null || loginedUser.getUserId().equals(Integer.MIN_VALUE)) {
                throw new InvalidParamException(new MessageResult(
                        MessageStatus.Warning, HttpStatus.SC_403_FORBIDDEN, resources.getResource("knowledge.likes.required.signin"), ""));
            }
            LikeCommentsEntity like = LikeCommentsDao.get().selectExistsOnUser(commentNo, loginedUser.getUserId());
            if (like != null) {
                throw new InvalidParamException(new MessageResult(
                        MessageStatus.Warning, HttpStatus.SC_403_FORBIDDEN, resources.getResource("knowledge.likes.duplicate"), ""));
            }
        }
        LikeCommentsEntity like = new LikeCommentsEntity();
        like.setCommentNo(commentNo);
        like = LikeCommentsDao.get().insert(like);
        Long count = LikeCommentsDao.get().selectOnCommentNo(commentNo);
        
        // 通知
        int userId = Integer.MIN_VALUE;
        if (loginedUser != null) {
            userId = loginedUser.getUserId();
        }
        if (!duplicateLikeComment(commentNo, userId)) {
            NotifyLogic.get().notifyOnCommentLiked(like);
            saveNotifyStatusLikeComment(commentNo, userId);
        }

        ActivityLogic.get().processActivity(Activity.COMMENT_LIKE, loginedUser, DateUtils.now(),
                CommentsDao.get().selectOnKey(commentNo));

        return count;
    }
}
