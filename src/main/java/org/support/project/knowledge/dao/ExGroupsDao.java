package org.support.project.knowledge.dao;

import java.util.List;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.ormapping.common.SQLManager;
import org.support.project.web.bean.LoginedUser;
import org.support.project.web.dao.GroupsDao;
import org.support.project.web.entity.GroupsEntity;

/**
 * Web共通モジュールにあるグループ情報アクセスをKnowledgeで拡張したクラス
 * @author Koda
 *
 */
@DI(instance=Instance.Singleton)
public class ExGroupsDao extends GroupsDao {

	/** SerialVersion */
	private static final long serialVersionUID = 1L;
	/**
	 * インスタンス取得
	 * AOPに対応
	 * @return インスタンス
	 */
	public static ExGroupsDao get() {
		return Container.getComp(ExGroupsDao.class);
	}

	
	/**
	 * 自分が所属しているグループを取得
	 * 
	 * @param loginedUser
	 * @param offset
	 * @param limit
	 * @return
	 */
	@Override
	public List<GroupsEntity> selectMyGroup(LoginedUser loginedUser, int offset, int limit) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/ExGroupsDao/ExGroupsDao_selectMyGroup.sql");
		return executeQueryList(sql, GroupsEntity.class, loginedUser.getUserId(), limit, offset);
	}
	
	/**
	 * アクセスできるグループを取得
	 * @param keyword
	 * @param loginedUser
	 * @param offset
	 * @param limit
	 * @return
	 */
	@Override
	public List<GroupsEntity> selectAccessAbleGroups(LoginedUser loginedUser, int offset, int limit) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/ExGroupsDao/ExGroupsDao_selectAccessAbleGroups.sql");
		return executeQueryList(sql, GroupsEntity.class, loginedUser.getUserId(), limit, offset);
	}
	
	
	/**
	 * キーワードでグループを取得
	 * @param keyword
	 * @param loginedUser
	 * @param offset
	 * @param limit
	 * @return
	 */
	@Override
	public List<GroupsEntity> selectOnKeyword(String keyword, LoginedUser loginedUser, int offset, int limit) {
		if (loginedUser != null && loginedUser.isAdmin()) {
			String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/ExGroupsDao/ExGroupsDao_selectAdminOnKeyword.sql");
			return executeQueryList(sql, GroupsEntity.class, keyword, limit, offset);
		} else {
			String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/ExGroupsDao/ExGroupsDao_selectOnKeyword.sql");
			return executeQueryList(sql, GroupsEntity.class, keyword, loginedUser.getUserId(), limit, offset);
		}
	}
	
	
	/**
	 * グループの一覧を取得
	 * 同時にグループの記事数を取得
	 * アクセス権を考慮していない
	 * @param offset
	 * @param limit
	 * @return
	 */
	public List<GroupsEntity> selectGroupsWithCount(int offset, int limit) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/ExGroupsDao/ExGroupsDao_selectGroupsWithCount.sql");
		return executeQueryList(sql, GroupsEntity.class, limit, offset);
	}
	
	
}
