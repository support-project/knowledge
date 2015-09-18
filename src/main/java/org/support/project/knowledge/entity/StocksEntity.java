package org.support.project.knowledge.entity;

import java.util.List;
import java.util.Map;
import java.sql.Timestamp;

import org.support.project.common.bean.ValidateError;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.entity.gen.GenStocksEntity;


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
	 * @param stockId stockId
	 */

	public StocksEntity(Long stockId) {
		super(stockId);
	}

}
