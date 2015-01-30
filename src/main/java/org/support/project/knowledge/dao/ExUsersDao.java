package org.support.project.knowledge.dao;

import java.util.List;

import org.support.project.di.Container;
import org.support.project.knowledge.vo.GroupUser;
import org.support.project.ormapping.common.SQLManager;
import org.support.project.web.dao.UsersDao;

public class ExUsersDao extends UsersDao {
	/**
	 * インスタンス取得
	 * AOPに対応
	 * @return インスタンス
	 */
	public static ExUsersDao get() {
		return Container.getComp(ExUsersDao.class);
	}

	/**
	 * グループに所属しているユーザを取得
	 * @param groupId
	 * @param offset
	 * @param limit
	 * @return
	 */
	public List<GroupUser> selectGroupUser(Integer groupId, int offset, int limit) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/ExUsersDao/selectGroupUser.sql");
		return executeQueryList(sql, GroupUser.class, groupId, limit, offset);
	}

}
