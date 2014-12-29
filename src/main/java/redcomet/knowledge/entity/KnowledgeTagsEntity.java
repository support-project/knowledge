package redcomet.knowledge.entity;

import redcomet.knowledge.entity.gen.GenKnowledgeTagsEntity;

import redcomet.di.Container;
import redcomet.di.DI;
import redcomet.di.Instance;

import java.sql.Timestamp;


/**
 * ナレッジが持つタグ
 */
@DI(instance=Instance.Prototype)
public class KnowledgeTagsEntity extends GenKnowledgeTagsEntity {

	/** SerialVersion */
	private static final long serialVersionUID = 1L;

	/**
	 * インスタンス取得
	 * AOPに対応
	 * @return インスタンス
	 */
	public static KnowledgeTagsEntity get() {
		return Container.getComp(KnowledgeTagsEntity.class);
	}

	/**
	 * コンストラクタ
	 */
	public KnowledgeTagsEntity() {
		super();
	}

	/**
	 * コンストラクタ
	 * @param knowledgeId ナレッジID
	 * @param tagId タグ_ID
	 */

	public KnowledgeTagsEntity(Long knowledgeId, Integer tagId) {
		super(knowledgeId, tagId);
	}

}
