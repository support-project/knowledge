package redcomet.knowledge.entity;

import org.owasp.validator.html.PolicyException;
import org.owasp.validator.html.ScanException;

import redcomet.common.exception.SystemException;
import redcomet.common.util.StringUtils;
import redcomet.di.Container;
import redcomet.di.DI;
import redcomet.di.Instance;
import redcomet.knowledge.control.Control;
import redcomet.knowledge.entity.gen.GenKnowledgesEntity;


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
	 * @see redcomet.knowledge.entity.gen.GenKnowledgesEntity#getTitle()
	 */
	@Override
	public String getTitle() {
		if (StringUtils.isEmpty(super.getTitle())) {
			return super.getTitle();
		}
		try {
			return Control.doSamy(super.getTitle());
		} catch (PolicyException | ScanException e) {
			throw new SystemException(e);
		}
	}

	/* (non-Javadoc)
	 * @see redcomet.knowledge.entity.gen.GenKnowledgesEntity#getContent()
	 */
	@Override
	public String getContent() {
		if (StringUtils.isEmpty(super.getContent())) {
			return super.getContent();
		}
		try {
			return Control.doSamy(super.getContent());
		} catch (PolicyException | ScanException e) {
			throw new SystemException(e);
		}
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

	/**
	 * @return the tags
	 */
	public String getTags() {
		return tags;
	}

	/**
	 * @param tags the tags to set
	 */
	public void setTags(String tags) {
		this.tags = tags;
	}

	/**
	 * @return the likeCount
	 */
	public Long getLikeCount() {
		return likeCount;
	}

	/**
	 * @param likeCount the likeCount to set
	 */
	public void setLikeCount(Long likeCount) {
		this.likeCount = likeCount;
	}
	
	
	
	

}
