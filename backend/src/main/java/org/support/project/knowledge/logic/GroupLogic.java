package org.support.project.knowledge.logic;

import java.util.ArrayList;
import java.util.List;

import org.support.project.aop.Aspect;
import org.support.project.common.util.StringUtils;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.dao.ExGroupsDao;
import org.support.project.knowledge.dao.ExUsersDao;
import org.support.project.knowledge.dao.KnowledgeGroupsDao;
import org.support.project.knowledge.dao.TargetsDao;
import org.support.project.knowledge.entity.KnowledgeGroupsEntity;
import org.support.project.knowledge.vo.GroupUser;
import org.support.project.web.bean.LabelValue;
import org.support.project.web.bean.AccessUser;
import org.support.project.web.bean.MessageResult;
import org.support.project.web.config.CommonWebParameter;
import org.support.project.web.config.MessageStatus;
import org.support.project.web.dao.UserGroupsDao;
import org.support.project.web.entity.GroupsEntity;
import org.support.project.web.entity.UserGroupsEntity;

@DI(instance = Instance.Singleton)
public class GroupLogic {

    public static GroupLogic get() {
        return Container.getComp(GroupLogic.class);
    }

    /**
     * グループを新規登録
     * 
     * @param groupsEntity
     * @param loginedUser
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public GroupsEntity addGroup(GroupsEntity groupsEntity, AccessUser loginedUser) {
        ExGroupsDao groupsDao = ExGroupsDao.get();
        GroupsEntity insertedEntity = groupsDao.insert(groupsEntity);

        UserGroupsDao userGroupsDao = UserGroupsDao.get();
        UserGroupsEntity userGroupsEntity = new UserGroupsEntity(insertedEntity.getGroupId(), loginedUser.getUserId());
        userGroupsEntity.setGroupRole(CommonWebParameter.GROUP_ROLE_ADMIN);
        userGroupsDao.insert(userGroupsEntity);

        return insertedEntity;
    }

    /**
     * グループを保存
     * 
     * @param groupsEntity
     * @param loginedUser
     * @return
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public GroupsEntity updateGroup(GroupsEntity groupsEntity, AccessUser loginedUser) {
        ExGroupsDao groupsDao = ExGroupsDao.get();
        if (!loginedUser.isAdmin()) {
            if (groupsDao.selectAccessAbleGroup(groupsEntity.getGroupId(), loginedUser) == null) {
                return null; // アクセス権がないユーザ
            }
        }
        return groupsDao.save(groupsEntity);
    }

    /**
     * グループ削除
     * 
     * @param groupId
     * @param loginedUser
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void deleteGroup(Integer groupId, AccessUser loginedUser) {
        ExGroupsDao groupsDao = ExGroupsDao.get();
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
     * 
     * @param keyword
     * @param loginedUser
     * @param offset
     * @param limit
     * @return
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<GroupsEntity> selectOnKeyword(String keyword, AccessUser loginedUser, int offset, int limit) {
        List<GroupsEntity> list;
        if (loginedUser == null) {
            list = new ArrayList<>();
            return list;
        }
        ExGroupsDao groupsDao = ExGroupsDao.get();
        if (loginedUser != null && loginedUser.isAdmin()) {
            if (StringUtils.isEmpty(keyword)) {
                list = groupsDao.selectGroupsWithCount(offset, limit);
            } else {
                list = groupsDao.selectOnKeyword(keyword, loginedUser, offset, limit);
            }
        } else {
            if (StringUtils.isEmpty(keyword)) {
                list = groupsDao.selectAccessAbleGroups(loginedUser, offset, limit);
            } else {
                list = groupsDao.selectOnKeyword(keyword, loginedUser, offset, limit);
            }
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
    public List<GroupsEntity> selectMyGroup(AccessUser loginedUser, int offset, int limit) {
        ExGroupsDao groupsDao = ExGroupsDao.get();
        List<GroupsEntity> list = groupsDao.selectMyGroup(loginedUser, offset, limit);

        for (GroupsEntity groupsEntity : list) {
            setGroupStatus(groupsEntity, loginedUser);
        }

        return list;
    }

    /**
     * 所属グループをキーワードで検索する
     *
     * @param keyword
     * @param loginedUser
     * @param offset
     * @param limit
     * @return
     */
    public List<GroupsEntity> selectMyGroupOnKeyword(String keyword, AccessUser loginedUser, int offset, int limit) {
        ExGroupsDao groupsDao = ExGroupsDao.get();
        List<GroupsEntity> list = groupsDao.selectMyGroupOnKeyword(keyword, loginedUser, offset, limit);

        for (GroupsEntity groupsEntity : list) {
            setGroupStatus(groupsEntity, loginedUser);
        }

        return list;
    }

    /**
     * 自分が指定のグループの所属状態がどうなっているかセットする
     * 
     * @param groupsEntity
     * @param loginedUser
     */
    private void setGroupStatus(GroupsEntity groupsEntity, AccessUser loginedUser) {
        UserGroupsDao userGroupsDao = UserGroupsDao.get();
        UserGroupsEntity userGroupsEntity = userGroupsDao.selectOnKey(groupsEntity.getGroupId(), loginedUser.getUserId());
        if (userGroupsEntity != null) {
            groupsEntity.setStatus(userGroupsEntity.getGroupRole());
        }
    }

    /**
     * グループ取得(アクセスできるもの）
     * 
     * @param groupId
     * @param loginedUser
     * @return
     */
    public GroupsEntity getGroup(Integer groupId, AccessUser loginedUser) {
        ExGroupsDao groupsDao = ExGroupsDao.get();
        GroupsEntity group;
        if (loginedUser.isAdmin()) {
            group = groupsDao.selectOnKey(groupId);
        } else {
            group = groupsDao.selectAccessAbleGroup(groupId, loginedUser);
        }

        if (group != null) {
            setGroupStatus(group, loginedUser);
        }
        return group;
    }

    /**
     * 編集可能なグループを取得 編集不可能であれば、NULLを返す
     * 
     * @param groupId
     * @param loginedUser
     * @return
     */
    public GroupsEntity getEditAbleGroup(Integer groupId, AccessUser loginedUser) {
        ExGroupsDao groupsDao = ExGroupsDao.get();
        if (loginedUser.isAdmin()) {
            return groupsDao.selectOnKey(groupId);
        }
        return groupsDao.selectEditAbleGroup(groupId, loginedUser);
    }

    /**
     * グループに所属しているユーザを取得
     * 
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
     * 
     * @param entity
     * @param groups
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void saveKnowledgeGroup(Long knowledgeId, List<LabelValue> targets) {
        this.removeKnowledgeGroup(knowledgeId);
        KnowledgeGroupsDao knowledgeGroupsDao = KnowledgeGroupsDao.get();
        if (targets != null && !targets.isEmpty()) {
            for (int i = 0; i < targets.size(); i++) {
                LabelValue labelValue = targets.get(i);
                Integer id = TargetLogic.get().getGroupId(labelValue.getValue());
                if (id != Integer.MIN_VALUE) {
                    KnowledgeGroupsEntity groupsEntity = new KnowledgeGroupsEntity(id, knowledgeId);
                    knowledgeGroupsDao.insert(groupsEntity);
                }
            }
        }
    }

    /**
     * 指定のナレッジに紐付いたグループを削除
     * 
     * @param knowledgeId
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void removeKnowledgeGroup(Long knowledgeId) {
        KnowledgeGroupsDao knowledgeGroupsDao = KnowledgeGroupsDao.get();
        List<KnowledgeGroupsEntity> knowledgeGroups = knowledgeGroupsDao.selectOnKnowledgeId(knowledgeId);
        for (KnowledgeGroupsEntity knowledgeGroupsEntity : knowledgeGroups) {
            knowledgeGroupsDao.physicalDelete(knowledgeGroupsEntity);
        }
    }

    /**
     * 指定のグループの一覧を取得
     * 
     * @param groups
     * @return
     */
    public List<GroupsEntity> selectGroups(String[] groups) {
        ExGroupsDao groupsDao = ExGroupsDao.get();
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
     * 
     * @param knowledgeId
     * @return
     */
    public List<GroupsEntity> selectGroupsOnKnowledgeId(Long knowledgeId) {
        TargetsDao groupsDao = TargetsDao.get();
        return groupsDao.selectGroupsOnKnowledgeId(knowledgeId);
    }

    /**
     * グループ管理者におけるユーザの追加
     * 
     * @param loginedUser
     * @param groupId
     * @param users
     * @return
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public MessageResult addUsers(AccessUser loginedUser, Integer groupId, String users) {
        ExGroupsDao groupsDao = ExGroupsDao.get();
        if (!loginedUser.isAdmin()) {
            if (groupsDao.selectAccessAbleGroup(groupId, loginedUser) == null) {
                MessageResult messageResult = new MessageResult();
                messageResult.setStatus(MessageStatus.Error.getValue());
                messageResult.setCode(org.support.project.web.common.HttpStatus.SC_403_FORBIDDEN);
                return messageResult; // アクセス権がないユーザ
            }
        }

        UserGroupsDao userGroupsDao = UserGroupsDao.get();
        String[] split = users.split(",");
        for (String string : split) {
            if (string.startsWith(TargetLogic.ID_PREFIX_USER)) {
                string = string.substring(TargetLogic.ID_PREFIX_USER.length());
            }
            if (StringUtils.isInteger(string)) {
                Integer user = Integer.parseInt(string);
                if (userGroupsDao.selectOnKey(groupId, user) == null) {
                    UserGroupsEntity userGroupsEntity = new UserGroupsEntity();
                    userGroupsEntity.setGroupId(groupId);
                    userGroupsEntity.setUserId(user);
                    userGroupsEntity.setGroupRole(CommonWebParameter.GROUP_ROLE_MEMBER);
                    userGroupsDao.insert(userGroupsEntity);
                }
            }
        }
        MessageResult messageResult = new MessageResult();
        messageResult.setMessage("message.success.insert");
        return messageResult;
    }

}
