package org.support.project.ormapping.gen.dao;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.ormapping.gen.dao.gen.GenGroupRelationshipDao;

/**
 * グループの親子関係
 */
@DI(instance = Instance.Singleton)
public class GroupRelationshipDao extends GenGroupRelationshipDao {

	/** SerialVersion */
	private static final long serialVersionUID = 1L;
	/**
	 * インスタンス取得
	 * AOPに対応
	 * @return インスタンス
	 */
	public static GroupRelationshipDao get() {
		return Container.getComp(GroupRelationshipDao.class);
	}



}
