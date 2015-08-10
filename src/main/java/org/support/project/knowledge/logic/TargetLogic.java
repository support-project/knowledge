package org.support.project.knowledge.logic;

import java.util.ArrayList;
import java.util.List;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.StringUtils;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.dao.TargetsDao;
import org.support.project.web.bean.LabelValue;
import org.support.project.web.bean.LoginedUser;
import org.support.project.web.dao.GroupsDao;
import org.support.project.web.dao.UsersDao;
import org.support.project.web.entity.GroupsEntity;
import org.support.project.web.entity.UsersEntity;

@DI(instance=Instance.Singleton)
public class TargetLogic {
	/** ログ */
	private static Log LOG = LogFactory.getLog(TargetLogic.class);
	
	public static final String ID_PREFIX_GROUP = "G-";
	public static final String ID_PREFIX_USER = "U-";
	
	public static final String NAME_PREFIX_GROUP = "[GROUP] ";
	public static final String NAME_PREFIX_USER = "[USER] ";
	
	public static TargetLogic get() {
		return Container.getComp(TargetLogic.class);
	}

	public List<LabelValue> selectOnKeyword(String keyword, LoginedUser loginedUser, int offset, int limit) {
		LOG.trace("call selectOnKeyword");
		TargetsDao targetDao = TargetsDao.get();
		List<LabelValue> list = targetDao.selectOnKeyword(keyword, offset, limit);
		for (LabelValue labelValue : list) {
			if (labelValue.getValue().startsWith(ID_PREFIX_GROUP)) {
				labelValue.setLabel(NAME_PREFIX_GROUP + labelValue.getLabel());
			} else if (labelValue.getValue().startsWith(ID_PREFIX_USER)) {
				labelValue.setLabel(NAME_PREFIX_USER + labelValue.getLabel());
			}
		}
		return list;
	}

	public List<LabelValue> selectTargets(String[] targets) {
		List<LabelValue> results = new ArrayList<>();
		GroupsDao groupsDao = GroupsDao.get();
		UsersDao usersDao = UsersDao.get();
		List<Integer> groupids = new ArrayList<>();
		List<Integer> userids = new ArrayList<>();
		for (String str : targets) {
			Integer id = getGroupId(str);
			if (id != Integer.MIN_VALUE) {
				groupids.add(id);
			}
			id = getUserId(str);
			if (id != Integer.MIN_VALUE) {
				userids.add(id);
			}
		}
		if (!groupids.isEmpty()) {
			List<GroupsEntity> groups = groupsDao.selectOnGroupIds(groupids);
			for (GroupsEntity groupsEntity : groups) {
				LabelValue labelValue = new LabelValue();
				labelValue.setLabel(NAME_PREFIX_GROUP + groupsEntity.getGroupName());
				labelValue.setValue(ID_PREFIX_GROUP + groupsEntity.getGroupId());
				results.add(labelValue);
			}
		}
		if (!userids.isEmpty()) {
			List<UsersEntity> users = usersDao.selectOnUserIds(userids);
			for (UsersEntity usersEntity : users) {
				LabelValue labelValue = new LabelValue();
				labelValue.setLabel(NAME_PREFIX_USER + usersEntity.getUserName());
				labelValue.setValue(ID_PREFIX_USER + usersEntity.getUserId());
				results.add(labelValue);
			}
		}
		return results;
	}
	
	/**
	 * ナレッジに指定されているアクセス可能なグループを取得
	 * @param knowledgeId
	 * @return
	 */
	public List<LabelValue> selectTargetsOnKnowledgeId(Long knowledgeId) {
		List<LabelValue> results = new ArrayList<>();
		TargetsDao targetsDao = TargetsDao.get();
		List<GroupsEntity> groups = targetsDao.selectGroupsOnKnowledgeId(knowledgeId);
		for (GroupsEntity groupsEntity : groups) {
			LabelValue labelValue = new LabelValue();
			labelValue.setLabel(NAME_PREFIX_GROUP + groupsEntity.getGroupName());
			labelValue.setValue(ID_PREFIX_GROUP + groupsEntity.getGroupId());
			results.add(labelValue);
		}
		List<UsersEntity> users = targetsDao.selectUsersOnKnowledgeId(knowledgeId);
		for (UsersEntity usersEntity : users) {
			LabelValue labelValue = new LabelValue();
			labelValue.setLabel(NAME_PREFIX_USER + usersEntity.getUserName());
			labelValue.setValue(ID_PREFIX_USER + usersEntity.getUserId());
			results.add(labelValue);
		}
		return results;
	}
	
	/**
	 * ナレッジに指定されている編集可能なグループを取得
	 * @param knowledgeId
	 * @return
	 */
	public List<LabelValue> selectEditorsOnKnowledgeId(Long knowledgeId) {
		List<LabelValue> results = new ArrayList<>();
		TargetsDao targetsDao = TargetsDao.get();
		List<GroupsEntity> groups = targetsDao.selectEditorGroupsOnKnowledgeId(knowledgeId);
		for (GroupsEntity groupsEntity : groups) {
			LabelValue labelValue = new LabelValue();
			labelValue.setLabel(NAME_PREFIX_GROUP + groupsEntity.getGroupName());
			labelValue.setValue(ID_PREFIX_GROUP + groupsEntity.getGroupId());
			results.add(labelValue);
		}
		List<UsersEntity> users = targetsDao.selectEditorUsersOnKnowledgeId(knowledgeId);
		for (UsersEntity usersEntity : users) {
			LabelValue labelValue = new LabelValue();
			labelValue.setLabel(NAME_PREFIX_USER + usersEntity.getUserName());
			labelValue.setValue(ID_PREFIX_USER + usersEntity.getUserId());
			results.add(labelValue);
		}
		return results;
	}
	
	
	/**
	 * ターゲットからグループのIDを取得
	 * 対象外（ユーザがターゲット）の場合、MIN_VALUEを返す
	 * 
	 * @param target
	 * @return
	 */
	public Integer getGroupId(String str) {
		if (str.startsWith(ID_PREFIX_GROUP)) {
			String id = str.substring(ID_PREFIX_GROUP.length());
			if (StringUtils.isInteger(id)) {
				return Integer.parseInt(id);
			}
		}
		return Integer.MIN_VALUE;
	}
	
	/**
	 * ターゲットからユーザのIDを取得
	 * 対象外（ユーザがターゲット）の場合、MIN_VALUEを返す
	 * 
	 * @param target
	 * @return
	 */
	public Integer getUserId(String str) {
		if (str.startsWith(ID_PREFIX_USER)) {
			String id = str.substring(ID_PREFIX_USER.length());
			if (StringUtils.isInteger(id)) {
				return Integer.parseInt(id);
			}
		}
		return Integer.MIN_VALUE;
	}

	public List<LabelValue> selectUsersOnKeyword(String keyword, LoginedUser loginedUser, int offset, int limit) {
		UsersDao usersDao = UsersDao.get();
		List<UsersEntity> users = usersDao.selectOnKeyword(offset, limit, keyword);
		List<LabelValue> list = new ArrayList<>();
		for (UsersEntity user : users) {
			LabelValue labelValue = new LabelValue();
			labelValue.setValue(ID_PREFIX_USER + user.getUserId());
			labelValue.setLabel(NAME_PREFIX_USER + user.getUserName());
			list.add(labelValue);
		}
		return list;
	}


}
