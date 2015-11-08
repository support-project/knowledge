package org.support.project.knowledge.entity;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.entity.gen.GenStocksEntity;


/**
 * ストックしたナレッジ
 */
@DI(instance=Instance.Prototype)
public class StocksEntity extends GenStocksEntity {
	
	public static final int STOCKTYPE_PRIVATE = 0;
	public static final int STOCKTYPE_PUBLIC = 1;
	
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
