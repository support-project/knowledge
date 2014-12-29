package redcomet.knowledge.entity;

import redcomet.knowledge.entity.gen.GenViewHistoriesEntity;

import java.util.List;
import java.util.Map;

import redcomet.common.bean.ValidateError;
import redcomet.di.Container;
import redcomet.di.DI;
import redcomet.di.Instance;

import java.sql.Timestamp;


/**
 * ナレッジの参照履歴
 */
@DI(instance=Instance.Prototype)
public class ViewHistoriesEntity extends GenViewHistoriesEntity {

	/** SerialVersion */
	private static final long serialVersionUID = 1L;

	/**
	 * インスタンス取得
	 * AOPに対応
	 * @return インスタンス
	 */
	public static ViewHistoriesEntity get() {
		return Container.getComp(ViewHistoriesEntity.class);
	}

	/**
	 * コンストラクタ
	 */
	public ViewHistoriesEntity() {
		super();
	}

	/**
	 * コンストラクタ
	 * @param historyNo HISTORY_NO
	 */

	public ViewHistoriesEntity(Long historyNo) {
		super( historyNo);
	}

}
