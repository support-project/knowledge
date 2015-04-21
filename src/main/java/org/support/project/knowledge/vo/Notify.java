package org.support.project.knowledge.vo;

import java.util.Locale;

import org.support.project.common.util.RandomUtil;
import org.support.project.knowledge.entity.CommentsEntity;
import org.support.project.knowledge.entity.KnowledgesEntity;
import org.support.project.knowledge.entity.LikesEntity;
import org.support.project.knowledge.entity.NotifyQueuesEntity;
import org.support.project.knowledge.logic.NotifyLogic;
import org.support.project.web.bean.LoginedUser;
import org.support.project.web.bean.MessageResult;

public class Notify {
	
	public static final int TYPE_KNOWLEDGE_INSERT = 1;
	public static final int TYPE_KNOWLEDGE_UPDATE = 2;
	public static final int TYPE_KNOWLEDGE_COMMENT = 11;
	public static final int TYPE_KNOWLEDGE_LIKE = 21;
	
	private int type;
	
	private KnowledgesEntity knowledge;
	private CommentsEntity comment;
	private LikesEntity like;
	
	public void inserted(KnowledgesEntity entity) {
		type = TYPE_KNOWLEDGE_INSERT;
		knowledge = entity;
	}
	public void updated(KnowledgesEntity entity) {
		type = TYPE_KNOWLEDGE_UPDATE;
		knowledge = entity;
	}
	public void commented(CommentsEntity commentsEntity) {
		type = TYPE_KNOWLEDGE_COMMENT;
		comment = commentsEntity;
	}
	public void liked(LikesEntity entity) {
		type = TYPE_KNOWLEDGE_LIKE;
		like = entity;
	}
	
	/**
	 * 通知するメッセージを取得
	 * @param loginuser
	 * @param locale
	 * @return
	 */
	public MessageResult getMessage(LoginedUser loginuser, Locale locale) {
		if (type == TYPE_KNOWLEDGE_INSERT) {
			return NotifyLogic.get().getInsertKnowledgeMessage(loginuser, locale, knowledge);
		} else if (type == TYPE_KNOWLEDGE_UPDATE) {
			return NotifyLogic.get().getUpdateKnowledgeMessage(loginuser, locale, knowledge);
		} else if (type == TYPE_KNOWLEDGE_COMMENT) {
			return NotifyLogic.get().getSaveCommentMessage(loginuser, locale, comment);
		} else if (type == TYPE_KNOWLEDGE_LIKE) {
			return NotifyLogic.get().getSaveLikeMessage(loginuser, locale, like);
		}
		return null;
	}
	/**
	 * メール通知のキューを取得
	 * @return
	 */
	public NotifyQueuesEntity getQueue() {
		NotifyQueuesEntity entity = new NotifyQueuesEntity();
		entity.setHash(RandomUtil.randamGen(30));
		entity.setType(type);
		
		if (type == TYPE_KNOWLEDGE_INSERT || type == TYPE_KNOWLEDGE_UPDATE) {
			entity.setId(knowledge.getKnowledgeId());
		} else if (type == TYPE_KNOWLEDGE_COMMENT) {
			entity.setId(comment.getCommentNo());
		} else if (type == TYPE_KNOWLEDGE_LIKE) {
			entity.setId(like.getNo());
		}
		return entity;
	}
	
	
	
}
