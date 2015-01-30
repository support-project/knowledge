package org.support.project.knowledge.logic;

import java.util.ArrayList;
import java.util.List;

import org.support.project.aop.Aspect;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.StringUtils;
import org.support.project.di.Container;
import org.support.project.knowledge.dao.ExUsersDao;
import org.support.project.knowledge.dao.KnowledgeGroupsDao;
import org.support.project.knowledge.entity.KnowledgeGroupsEntity;
import org.support.project.knowledge.vo.GroupUser;
import org.support.project.web.bean.LoginedUser;
import org.support.project.web.config.CommonWebParameter;
import org.support.project.web.dao.GroupsDao;
import org.support.project.web.dao.UserGroupsDao;
import org.support.project.web.entity.GroupsEntity;
import org.support.project.web.entity.UserGroupsEntity;

public class GroupLogic {
	/** ログ */
	private static Log LOG = LogFactory.getLog(GroupLogic.class);

	public static GroupLogic get() {
		return Container.getComp(GroupLogic.class);
	}
	
	/**
	 * グループを新規登録
	 * @param groupsEntity
	 * @param loginedUser
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public GroupsEntity addGroup(GroupsEntity groupsEntity, LoginedUser loginedUser) {
		GroupsDao groupsDao = GroupsDao.get();
		groupsEntity = groupsDao.insert(groupsEntity);
		
		UserGroupsDao userGroupsDao = UserGroupsDao.get();
		UserGroupsEntity userGroupsEntity = new UserGroupsEntity(
				groupsEntity.getGroupId(), 
				loginedUser.getUserId());
		userGroupsEntity.setGroupRole(CommonWebParameter.GROUP_ROLE_ADMIN);
		userGroupsDao.insert(userGroupsEntity);
		
		return groupsEntity;
	}
	
	/**
	 * グループを保存
	 * @param groupsEntity
	 * @param loginedUser
	 * @return
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public GroupsEntity updateGroup(GroupsEntity groupsEntity, LoginedUser loginedUser) {
		GroupsDao groupsDao = GroupsDao.get();
		if (!loginedUser.isAdmin()) {
			if (groupsDao.selectAccessAbleGroup(groupsEntity.getGroupId(), loginedUser) == null) {
				return null; // アクセス権がないユーザ
			}
		}
		groupsEntity = groupsDao.save(groupsEntity);
		return groupsEntity;
	}
	
	/**
	 * グループ削除
	 * @param groupId
	 * @param loginedUser
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void deleteGroup(Integer groupId, LoginedUser loginedUser) {
		GroupsDao groupsDao = GroupsDao.get();
		if (!loginedUser.isAdmin()) {
			if (groupsDao.selectAccessAbleGroup(groupId, loginedUser) == null) {
				return; // アクセス権がないユーザ
			}
		}
		
		// グループとの紐付けを削除
		UserGroupsDao userGroupsDao = UserGroupsDao.get();
		List<UserGroupsEntity> accounts = userGroupsDao.selectOnGroupId(groupId);
		for (UserGroupsEntity userGroupsEntity : accounts) {
			userGroupsDao.delete(userGroupsEntity);
		}
		groupsDao.delete(groupId);
	}
	
	
	
	/**
	 * グループを取得
	 * @param keyword
	 * @param loginedUser
	 * @param offset
	 * @param limit
	 * @return
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public List<GroupsEntity> selectOnKeyword(String keyword, LoginedUser loginedUser, int offset, int limit) {
		List<GroupsEntity> list;
		if (loginedUser == null) {
			list = new ArrayList<>();
		}
		GroupsDao groupsDao = GroupsDao.get();
		if (loginedUser.isAdmin()) {
			list = groupsDao.selectAll(offset, limit);
		}
		if (StringUtils.isEmpty(keyword)) {
			list = groupsDao.selectAccessAbleGroups(loginedUser, offset, limit);
		} else {
			list = groupsDao.selectOnKeyword(keyword, loginedUser, offset, limit);
		}
		
		for (GroupsEntity groupsEntity : list) {
			setGroupStatus(groupsEntity, loginedUser);
		}
		
		return list;
	}
	
	/**
	 * 
	 * @param loginedUser
	 * @param offset
	 * @param limit
	 * @return
	 */
	public List<GroupsEntity> selectMyGroup(LoginedUser loginedUser, int offset, int limit) {
		GroupsDao groupsDao = GroupsDao.get();
		List<GroupsEntity> list = groupsDao.selectMyGroup(loginedUser, offset, limit);
		
		for (GroupsEntity groupsEntity : list) {
			setGroupStatus(groupsEntity, loginedUser);
		}
		
		return list;
	}
	
	/**
	 * 自分が指定のグループの所属状態がどうなっているかセットする
	 * @param groupsEntity
	 * @param loginedUser
	 */
	private void setGroupStatus(GroupsEntity groupsEntity, LoginedUser loginedUser) {
		UserGroupsDao userGroupsDao = UserGroupsDao.get();
		UserGroupsEntity userGroupsEntity = userGroupsDao.selectOnKey(groupsEntity.getGroupId(), loginedUser.getUserId());
		if (userGroupsEntity != null) {
			groupsEntity.setStatus(userGroupsEntity.getGroupRole());
		}
	}

	/**
	 * グループ取得(アクセスできるもの）
	 * @param groupId
	 * @param loginedUser
	 * @return
	 */
	public GroupsEntity getGroup(Integer groupId, LoginedUser loginedUser) {
		GroupsDao groupsDao = GroupsDao.get();
		if (loginedUser.isAdmin()) {
			return groupsDao.selectOnKey(groupId);
		}
		return groupsDao.selectAccessAbleGroup(groupId, loginedUser);
	}

	/**
	 * 編集可能なグループを取得
	 * 編集不可能であれば、NULLを返す
	 * 
	 * @param groupId
	 * @param loginedUser
	 * @return
	 */
	public GroupsEntity getEditAbleGroup(Integer groupId, LoginedUser loginedUser) {
		GroupsDao groupsDao = GroupsDao.get();
		if (loginedUser.isAdmin()) {
			return groupsDao.selectOnKey(groupId);
		}
		return groupsDao.selectEditAbleGroup(groupId, loginedUser);
	}

	
	/**
	 * グループに所属しているユーザを取得
	 * @param groupId
	 * @param offset
	 * @param limit
	 * @return
	 */
	public List<GroupUser> getGroupUsers(Integer groupId, int offset, int limit) {
		ExUsersDao usersDao = ExUsersDao.get();
		List<GroupUser> users = usersDao.selectGroupUser(groupId, offset, limit);
		return users;
	}
	
	
	/**
	 * ナレッジにアクセスできるグループを保存
	 * @param entity
	 * @param groups
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void saveKnowledgeGroup(Long knowledgeId, List<GroupsEntity> groups) {
		this.removeKnowledgeGroup(knowledgeId);
		KnowledgeGroupsDao knowledgeGroupsDao = KnowledgeGroupsDao.get();
		if (groups != null && !groups.isEmpty()) {
			for (int i = 0; i < groups.size(); i++) {
				Integer groupid = groups.get(i).getGroupId();
				KnowledgeGroupsEntity groupsEntity = new KnowledgeGroupsEntity(groupid, knowledgeId);
				knowledgeGroupsDao.insert(groupsEntity);
			}
		}
	}
	
	/**
	 * 指定のナレッジに紐付いたグループを削除
	 * @param knowledgeId
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void removeKnowledgeGroup(Long knowledgeId) {
		KnowledgeGroupsDao knowledgeGroupsDao = KnowledgeGroupsDao.get();
		List<KnowledgeGroupsEntity> knowledgeGroups = knowledgeGroupsDao.selectOnKnowledgeId(knowledgeId);
		for (KnowledgeGroupsEntity knowledgeGroupsEntity : knowledgeGroups) {
			knowledgeGroupsDao.physicalDelete(knowledgeGroupsEntity);
		}
	}

	
	/**
	 * 指定のグループの一覧を取得
	 * @param groups
	 * @return
	 */
	public List<GroupsEntity> selectGroups(String[] groups) {
		GroupsDao groupsDao = GroupsDao.get();
		List<Integer> groupids = new ArrayList<>();
		for (String str : groups) {
			if (StringUtils.isInteger(str)) {
				groupids.add(Integer.parseInt(str));
			}
		}
		return groupsDao.selectOnGroupIds(groupids);
	}
	
	
	/**
	 * ナレッジに指定されているアクセス可能なグループを取得
	 * @param knowledgeId
	 * @return
	 */
	public List<GroupsEntity> selectGroupsOnKnowledgeId(Long knowledgeId) {
		GroupsDao groupsDao = GroupsDao.get();
		return groupsDao.selectGroupsOnKnowledgeId(knowledgeId);
	}





}
