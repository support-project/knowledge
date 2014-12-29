package redcomet.knowledge.entity;

import redcomet.knowledge.entity.gen.GenVotesEntity;

import java.util.List;
import java.util.Map;

import redcomet.common.bean.ValidateError;
import redcomet.di.Container;
import redcomet.di.DI;
import redcomet.di.Instance;

import java.sql.Timestamp;


/**
 * 投票
 */
@DI(instance=Instance.Prototype)
public class VotesEntity extends GenVotesEntity {

	/** SerialVersion */
	private static final long serialVersionUID = 1L;

	/**
	 * インスタンス取得
	 * AOPに対応
	 * @return インスタンス
	 */
	public static VotesEntity get() {
		return Container.getComp(VotesEntity.class);
	}

	/**
	 * コンストラクタ
	 */
	public VotesEntity() {
		super();
	}

	/**
	 * コンストラクタ
	 * @param voteNo VOTE_NO
	 */

	public VotesEntity(Long voteNo) {
		super( voteNo);
	}

}
