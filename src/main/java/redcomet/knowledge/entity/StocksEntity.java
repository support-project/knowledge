package redcomet.knowledge.entity;

import redcomet.knowledge.entity.gen.GenStocksEntity;

import java.util.List;
import java.util.Map;

import redcomet.common.bean.ValidateError;
import redcomet.di.Container;
import redcomet.di.DI;
import redcomet.di.Instance;

import java.sql.Timestamp;


/**
 * ストックしたナレッジ
 */
@DI(instance=Instance.Prototype)
public class StocksEntity extends GenStocksEntity {

	/** SerialVersion */
	private static final long serialVersionUID = 1L;

	/**
	 * インスタンス取得
	 * AOPに対応
	 * @return インスタンス
	 */
	public static StocksEntity get() {
		return Container.getComp(StocksEntity.class);
	}

	/**
	 * コンストラクタ
	 */
	public StocksEntity() {
		super();
	}

	/**
	 * コンストラクタ
	 * @param knowledgeId ナレッジID
	 * @param userId USER_ID
	 */

	public StocksEntity(Long knowledgeId, Integer userId) {
		super(knowledgeId, userId);
	}

}
