package redcomet.knowledge.entity;

import redcomet.di.Container;
import redcomet.di.DI;
import redcomet.di.Instance;
import redcomet.knowledge.entity.gen.GenLikesEntity;


/**
 * いいね
 */
@DI(instance=Instance.Prototype)
public class LikesEntity extends GenLikesEntity {

	/** SerialVersion */
	private static final long serialVersionUID = 1L;

	/**
	 * インスタンス取得
	 * AOPに対応
	 * @return インスタンス
	 */
	public static LikesEntity get() {
		return Container.getComp(LikesEntity.class);
	}

	/**
	 * コンストラクタ
	 */
	public LikesEntity() {
		super();
	}

	/**
	 * コンストラクタ
	 * @param no NO
	 */

	public LikesEntity(Long no) {
		super(no);
	}

}
