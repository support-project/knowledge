package redcomet.knowledge.entity;

import redcomet.knowledge.entity.gen.GenKnowledgeUsersEntity;

import java.util.List;
import java.util.Map;

import redcomet.common.bean.ValidateError;
import redcomet.di.Container;
import redcomet.di.DI;
import redcomet.di.Instance;

import java.sql.Timestamp;


/**
 * アクセス可能なユーザ
 */
@DI(instance=Instance.Prototype)
public class KnowledgeUsersEntity extends GenKnowledgeUsersEntity {

	/** SerialVersion */
	private static final long serialVersionUID = 1L;

	/**
	 * インスタンス取得
	 * AOPに対応
	 * @return インスタンス
	 */
	public static KnowledgeUsersEntity get() {
		return Container.getComp(KnowledgeUsersEntity.class);
	}

	/**
	 * コンストラクタ
	 */
	public KnowledgeUsersEntity() {
		super();
	}

	/**
	 * コンストラクタ
	 * @param knowledgeId ナレッジID
	 * @param userId USER_ID
	 */

	public KnowledgeUsersEntity(Long knowledgeId, Integer userId) {
		super(knowledgeId, userId);
	}

}
