package redcomet.knowledge.entity;

import redcomet.knowledge.entity.gen.GenKnowledgeGroupsEntity;

import java.util.List;
import java.util.Map;

import redcomet.common.bean.ValidateError;
import redcomet.di.Container;
import redcomet.di.DI;
import redcomet.di.Instance;

import java.sql.Timestamp;


/**
 * アクセス可能なグループ
 */
@DI(instance=Instance.Prototype)
public class KnowledgeGroupsEntity extends GenKnowledgeGroupsEntity {

	/** SerialVersion */
	private static final long serialVersionUID = 1L;

	/**
	 * インスタンス取得
	 * AOPに対応
	 * @return インスタンス
	 */
	public static KnowledgeGroupsEntity get() {
		return Container.getComp(KnowledgeGroupsEntity.class);
	}

	/**
	 * コンストラクタ
	 */
	public KnowledgeGroupsEntity() {
		super();
	}

	/**
	 * コンストラクタ
	 * @param groupId GROUP_ID
	 * @param knowledgeId ナレッジID
	 */

	public KnowledgeGroupsEntity(Integer groupId, Long knowledgeId) {
		super(groupId, knowledgeId);
	}

}
