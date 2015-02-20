package org.support.project.knowledge.entity;

import org.support.project.common.util.StringUtils;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.entity.gen.GenKnowledgesEntity;


/**
 * ナレッジ
 */
@DI(instance=Instance.Prototype)
public class KnowledgesEntity extends GenKnowledgesEntity {

	/** SerialVersion */
	private static final long serialVersionUID = 1L;

	/**
	 * インスタンス取得
	 * AOPに対応
	 * @return インスタンス
	 */
	public static KnowledgesEntity get() {
		return Container.getComp(KnowledgesEntity.class);
	}
	
	/**
	 * 登録者の名称
	 */
	private String insertUserName;
	/**
	 * タグ
	 */
	private String tags;
	
	/** いいねのカウント */
	private Long likeCount;
	/** コメントのカウント */
	private int commentsCount;
	
	
	/**
	 * コンストラクタ
	 */
	public KnowledgesEntity() {
		super();
	}

	/**
	 * コンストラクタ
	 * @param knowledgeId ナレッジID
	 */

	public KnowledgesEntity(Long knowledgeId) {
		super( knowledgeId);
	}

	/* (non-Javadoc)
	 * @see org.support.project.knowledge.entity.gen.GenKnowledgesEntity#getTitle()
	 */
	@Override
	public String getTitle() {
		if (StringUtils.isEmpty(super.getTitle())) {
			return super.getTitle();
		}
//		try {
//			return Control.doSamy(super.getTitle());
//		} catch (PolicyException | ScanException e) {
//			throw new SystemException(e);
//		}
		return super.getTitle();
	}

	/* (non-Javadoc)
	 * @see org.support.project.knowledge.entity.gen.GenKnowledgesEntity#getContent()
	 */
	@Override
	public String getContent() {
		if (StringUtils.isEmpty(super.getContent())) {
			return super.getContent();
		}
//		try {
//			return Control.doSamy(super.getContent());
//		} catch (PolicyException | ScanException e) {
//			throw new SystemException(e);
//		}
		return super.getContent();
	}

	/**
	 * @return the insertUserName
	 */
	public String getInsertUserName() {
		return insertUserName;
	}

	/**
	 * @param insertUserName the insertUserName to set
	 */
	public void setInsertUserName(String insertUserName) {
		this.insertUserName = insertUserName;
	}
	
	
	
	

}
