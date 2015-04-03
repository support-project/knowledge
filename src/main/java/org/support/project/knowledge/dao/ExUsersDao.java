package org.support.project.knowledge.dao;

import java.util.List;

import org.support.project.di.Container;
import org.support.project.knowledge.vo.GroupUser;
import org.support.project.ormapping.common.SQLManager;
import org.support.project.web.dao.UsersDao;
import org.support.project.web.entity.UsersEntity;

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
	
	
	/**
	 * 公開区分が「公開」のナレッジが登録された場合に、通知を希望しているユーザの一覧を取得
	 * @return
	 */
	public List<UsersEntity> selectNotifyPublicUsers() {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/ExUsersDao/selectNotifyPublicUsers.sql");
		return executeQueryList(sql, UsersEntity.class);
	}

}
