package org.support.project.knowledge.entity;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.entity.gen.GenLikesEntity;


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
