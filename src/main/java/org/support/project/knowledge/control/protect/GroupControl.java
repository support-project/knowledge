package org.support.project.knowledge.control.protect;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.support.project.common.bean.ValidateError;
import org.support.project.common.exception.ParseException;
import org.support.project.common.util.RandomUtil;
import org.support.project.common.util.StringUtils;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.control.Control;
import org.support.project.knowledge.logic.GroupLogic;
import org.support.project.knowledge.vo.GroupUser;
import org.support.project.knowledge.vo.StringList;
import org.support.project.web.bean.LabelValue;
import org.support.project.web.bean.LoginedUser;
import org.support.project.web.bean.MessageResult;
import org.support.project.web.boundary.Boundary;
import org.support.project.web.common.HttpStatus;
import org.support.project.web.config.CommonWebParameter;
import org.support.project.web.config.HttpMethod;
import org.support.project.web.config.MessageStatus;
import org.support.project.web.control.service.Get;
import org.support.project.web.control.service.Post;
import org.support.project.web.dao.UserGroupsDao;
import org.support.project.web.dao.UsersDao;
import org.support.project.web.entity.GroupsEntity;
import org.support.project.web.entity.UserGroupsEntity;
import org.support.project.web.entity.UsersEntity;
import org.support.project.web.exception.InvalidParamException;

/**
 * グループ操作のコントロール
 * 
 * @author Koda
 */
@DI(instance = Instance.Prototype)
public class GroupControl extends Control {
    /** 一覧の表示件数 */
    public static final int PAGE_LIMIT = 20;

    /**
     * 自分が所属しているグループの一覧を取得
     * 
     * @return
     * @throws InvalidParamException
     */
    @Get(publishToken = "knowledge")
    public Boundary mygroups() throws InvalidParamException {
        Integer offset = super.getPathInteger(0);

        GroupLogic groupLogic = GroupLogic.get();
        List<GroupsEntity> groups = groupLogic.selectMyGroup(super.getLoginedUser(), offset * PAGE_LIMIT, PAGE_LIMIT);
        setAttribute("groups", groups);

        int previous = offset - 1;
        if (previous < 0) {
            previous = 0;
        }
        setAttribute("offset", offset);
        setAttribute("previous", previous);
        setAttribute("next", offset + 1);

        return forward("mygroups.jsp");
    }

    /**
     * グループの一覧を取得
     * 
     * @return
     * @throws InvalidParamException
     */
    @Get(publishToken = "knowledge")
    public Boundary list() throws InvalidParamException {
        Integer offset = super.getPathInteger(0);
        String keyword = super.getParam("keyword");

        GroupLogic groupLogic = GroupLogic.get();
        List<GroupsEntity> groups = groupLogic.selectOnKeyword(keyword, super.getLoginedUser(), offset * PAGE_LIMIT, PAGE_LIMIT);
        setAttribute("groups", groups);

        int previous = offset - 1;
        if (previous < 0) {
            previous = 0;
        }
        setAttribute("offset", offset);
        setAttribute("previous", previous);
        setAttribute("next", offset + 1);

        return forward("groups.jsp");
    }

    /**
     * グループ追加の画面を表示
     * 
     * @return
     */
    @Get(publishToken = "knowledge")
    public Boundary view_add() {
        return forward("add_group.jsp");
    }

    /**
     * グループを追加
     * 
     * @return
     * @throws ParseException
     * @throws ScanException
     * @throws PolicyException
     */
    @Post(subscribeToken = "knowledge")
    public Boundary add() throws ParseException {
        // 入力チェック
        GroupsEntity groupsEntity = new GroupsEntity();
        Map<String, String> params = super.getParams();
        params.put("groupKey", "g-" + RandomUtil.randamGen(16));
        params.put("groupName", super.sanitize(params.get("groupName"))); // XSS対策

        List<ValidateError> errors = groupsEntity.validate(params);
        if (!errors.isEmpty()) {
            setResult(null, errors);
            return forward("add_group.jsp");
        }

        GroupLogic groupLogic = GroupLogic.get();
        groupsEntity = super.getParams(GroupsEntity.class);
        groupsEntity.setGroupKey(params.get("groupKey"));
        groupsEntity = groupLogic.addGroup(groupsEntity, super.getLoginedUser());

        setAttributeOnProperty(groupsEntity);
        addMsgSuccess("message.success.insert");

        return forward("edit_group.jsp");
    }

    /**
     * グループ表示の画面を表示
     * 
     * @return
     * @throws InvalidParamException
     */
    @Get(publishToken = "knowledge")
    public Boundary view() throws InvalidParamException {
        Integer groupId = super.getPathInteger(0);
        GroupLogic groupLogic = GroupLogic.get();
        GroupsEntity group = groupLogic.getGroup(groupId, getLoginedUser());
        setAttributeOnProperty(group);

        GroupsEntity edit = groupLogic.getEditAbleGroup(groupId, getLoginedUser());
        if (edit != null) {
            setAttribute("editAble", true);
        } else {
            setAttribute("editAble", false);
        }
        // グループのユーザ
        String userOffset = super.getParam("offset");
        int offset = 0;
        if (StringUtils.isInteger(userOffset)) {
            offset = Integer.parseInt(userOffset);
        }
        List<GroupUser> users = groupLogic.getGroupUsers(groupId, offset * PAGE_LIMIT, PAGE_LIMIT);
        boolean belong = false; // 所属済かどうか
        for (GroupUser groupUser : users) {
            if (groupUser.getUserId().intValue() == getLoginUserId().intValue()) {
                belong = true;
            }
            if (groupUser.getGroupRole() == 0) {
                groupUser.setGroupRole(CommonWebParameter.GROUP_ROLE_MEMBER);
            }
        }

        int previous = offset - 1;
        if (previous < 0) {
            previous = 0;
        }
        setAttribute("offset", offset);
        setAttribute("previous", previous);
        setAttribute("next", offset + 1);

        setAttribute("users", users);
        setAttribute("belong", belong);

        return forward("view_group.jsp");
    }

    /**
     * グループ更新の画面を表示
     * 
     * @return
     * @throws InvalidParamException
     */
    @Get(publishToken = "knowledge")
    public Boundary view_edit() throws InvalidParamException {
        Integer groupId = super.getPathInteger(0);
        GroupLogic groupLogic = GroupLogic.get();
        GroupsEntity group = groupLogic.getEditAbleGroup(groupId, getLoginedUser());
        if (group == null) {
            return sendError(HttpStatus.SC_403_FORBIDDEN, "");
        }
        setAttributeOnProperty(group);

        return forward("edit_group.jsp");
    }

    /**
     * グループを更新
     * 
     * @return
     * @throws ParseException
     * @throws ScanException
     * @throws PolicyException
     */
    @Post(subscribeToken = "knowledge")
    public Boundary update() throws ParseException {
        // 入力チェック
        GroupsEntity groupsEntity = new GroupsEntity();
        Map<String, String> params = super.getParams();
        params.put("groupName", super.sanitize(params.get("groupName"))); // XSS対策

        List<ValidateError> errors = groupsEntity.validate(params);
        if (!errors.isEmpty()) {
            setResult(null, errors);
            return forward("edit_group.jsp");
        }

        GroupLogic groupLogic = GroupLogic.get();
        groupsEntity = super.getParams(GroupsEntity.class);
        GroupsEntity group = groupLogic.getEditAbleGroup(groupsEntity.getGroupId(), getLoginedUser());
        if (group == null) {
            return sendError(HttpStatus.SC_403_FORBIDDEN, "");
        }

        groupsEntity = groupLogic.updateGroup(groupsEntity, super.getLoginedUser());
        setAttributeOnProperty(groupsEntity);
        addMsgSuccess("message.success.update");

        return forward("edit_group.jsp");
    }

    /**
     * 削除
     * 
     * @return
     * @throws InvalidParamException
     */
    @Post(subscribeToken = "knowledge")
    public Boundary delete() throws InvalidParamException {
        Integer groupId = -1;
        String id = getParam("groupId");
        if (StringUtils.isInteger(id)) {
            groupId = Integer.parseInt(id);
        }
        GroupLogic groupLogic = GroupLogic.get();
        GroupsEntity group = groupLogic.getEditAbleGroup(groupId, getLoginedUser());
        if (group == null) {
            return sendError(HttpStatus.SC_403_FORBIDDEN, "");
        }

        groupLogic.deleteGroup(groupId, super.getLoginedUser());

        addMsgSuccess("message.success.delete");

        // return redirect("");
        return super.devolution(HttpMethod.get, "protect.Group/list");
    }

    /**
     * グループの所属をやめる
     * 
     * @return
     * @throws InvalidParamException
     */
    @Get(subscribeToken = "knowledge")
    public Boundary unsubscribe() throws InvalidParamException {
        Integer groupId = super.getPathInteger(0);
        GroupLogic groupLogic = GroupLogic.get();
        GroupsEntity group = groupLogic.getGroup(groupId, getLoginedUser());
        if (group == null) {
            return sendError(HttpStatus.SC_403_FORBIDDEN, "");
        }
        UserGroupsDao userGroupsDao = UserGroupsDao.get();
        if (userGroupsDao.selectOnKey(groupId, getLoginUserId()) != null) {
            UserGroupsEntity userGroupsEntity = new UserGroupsEntity(groupId, getLoginUserId());
            userGroupsDao.physicalDelete(userGroupsEntity);
        }
        // 所属追加したのでグループから削除
        LoginedUser loginedUser = super.getLoginedUser();
        List<GroupsEntity> groups = loginedUser.getGroups();
        for (GroupsEntity groupsEntity : groups) {
            if (groupsEntity.equalsOnKey(group)) {
                groups.remove(groupsEntity);
                break;
            }
        }
        addMsgSuccess("message.success.delete");

        return super.redirect(getRequest().getContextPath() + "/protect.group/mygroups");
    }

    /**
     * 公開グループへ入る
     * 
     * @return
     * @throws InvalidParamException
     */
    @Get(subscribeToken = "knowledge")
    public Boundary subscribe() throws InvalidParamException {
        Integer groupId = super.getPathInteger(0);
        GroupLogic groupLogic = GroupLogic.get();
        GroupsEntity group = groupLogic.getGroup(groupId, getLoginedUser());
        if (group == null || group.getGroupClass().intValue() != CommonWebParameter.GROUP_CLASS_PUBLIC) {
            // 所属するをよべるのは、公開グループのみ
            return sendError(HttpStatus.SC_403_FORBIDDEN, "");
        }
        UserGroupsDao userGroupsDao = UserGroupsDao.get();
        if (userGroupsDao.selectOnKey(groupId, getLoginUserId()) == null) {
            UserGroupsEntity userGroupsEntity = new UserGroupsEntity(groupId, getLoginUserId());
            userGroupsEntity.setGroupRole(CommonWebParameter.GROUP_ROLE_MEMBER);
            userGroupsDao.save(userGroupsEntity);
        }
        // 所属追加したのでグループにセット
        LoginedUser loginedUser = super.getLoginedUser();
        loginedUser.getGroups().add(group);

        addMsgSuccess("message.success.insert");

        return super.devolution(HttpMethod.get, "protect.Group/view");
    }

    /**
     * 保護されたグループに所属したいのリクエストを登録
     * 
     * @return
     * @throws InvalidParamException
     */
    @Get(subscribeToken = "knowledge")
    public Boundary request() throws InvalidParamException {
        Integer groupId = super.getPathInteger(0);
        GroupLogic groupLogic = GroupLogic.get();
        GroupsEntity group = groupLogic.getGroup(groupId, getLoginedUser());
        if (group == null || group.getGroupClass().intValue() != CommonWebParameter.GROUP_CLASS_PROTECT) {
            // 保護グループのみ
            return sendError(HttpStatus.SC_403_FORBIDDEN, "");
        }
        UserGroupsDao userGroupsDao = UserGroupsDao.get();
        if (userGroupsDao.selectOnKey(groupId, getLoginUserId()) == null) {
            UserGroupsEntity userGroupsEntity = new UserGroupsEntity(groupId, getLoginUserId());
            userGroupsEntity.setGroupRole(CommonWebParameter.GROUP_ROLE_WAIT);
            userGroupsDao.save(userGroupsEntity);
        }
        addMsgSuccess("message.success.insert");
        return super.devolution(HttpMethod.get, "protect.Group/view");
    }

    /**
     * 保護されたグループに所属したいのリクエストを登録
     * 
     * @return
     * @throws InvalidParamException
     */
    @Get(subscribeToken = "knowledge")
    public Boundary accept() throws InvalidParamException {
        Integer groupId = super.getPathInteger(0);
        String userIdstr = super.getParam("userId");
        GroupLogic groupLogic = GroupLogic.get();
        // 編集可能なグループを取得
        GroupsEntity group = groupLogic.getEditAbleGroup(groupId, getLoginedUser());
        if (group == null || group.getGroupClass().intValue() != CommonWebParameter.GROUP_CLASS_PROTECT) {
            // 保護グループのみ
            return sendError(HttpStatus.SC_403_FORBIDDEN, "");
        }
        if (!StringUtils.isInteger(userIdstr)) {
            return sendError(HttpStatus.SC_400_BAD_REQUEST, "");
        }
        Integer userId = Integer.parseInt(userIdstr);
        UserGroupsDao userGroupsDao = UserGroupsDao.get();

        UserGroupsEntity userGroupsEntity = userGroupsDao.selectOnKey(groupId, userId);
        if (userGroupsEntity == null) {
            addMsgWarn("message.allready.updated");
        } else if (userGroupsEntity != null) {
            if (userGroupsEntity.getGroupRole().intValue() == CommonWebParameter.GROUP_ROLE_WAIT) {
                userGroupsEntity.setGroupRole(CommonWebParameter.GROUP_ROLE_MEMBER);
                userGroupsDao.save(userGroupsEntity);
                addMsgSuccess("message.success.accept");
            } else {
                addMsgWarn("message.allready.updated");
            }
        }
        return super.devolution(HttpMethod.get, "protect.Group/view");
    }

    /**
     * 所属しているグループの状態変更
     * 
     * @return
     * @throws InvalidParamException
     */
    @Get(subscribeToken = "knowledge")
    public Boundary change() throws InvalidParamException {
        Integer groupId = super.getPathInteger(0);
        String userIdstr = super.getParam("userId");
        String statusStr = super.getParam("status");
        GroupLogic groupLogic = GroupLogic.get();
        // 編集可能なグループを取得
        GroupsEntity group = groupLogic.getEditAbleGroup(groupId, getLoginedUser());
        if (group == null) {
            // 編集可能なグループが存在しない
            return sendError(HttpStatus.SC_403_FORBIDDEN, "");
        }
        if (!StringUtils.isInteger(userIdstr) || !StringUtils.isInteger(statusStr)) {
            return sendError(HttpStatus.SC_400_BAD_REQUEST, "");
        }
        Integer userId = Integer.parseInt(userIdstr);
        Integer status = Integer.parseInt(statusStr);
        UserGroupsDao userGroupsDao = UserGroupsDao.get();

        UserGroupsEntity userGroupsEntity = userGroupsDao.selectOnKey(groupId, userId);
        if (userGroupsEntity == null) {
            addMsgWarn("message.allready.updated");
        } else if (userGroupsEntity != null) {
            if (status >= CommonWebParameter.GROUP_ROLE_MEMBER) {
                // 権限変更
                userGroupsEntity.setGroupRole(status);
                userGroupsDao.save(userGroupsEntity);
                addMsgSuccess("message.success.update");
            } else {
                // グループから削除
                if (groupId.intValue() == 0) {
                    // ALLグループから削除は出来ない
                    addMsgWarn("Can not remove user from Group [ALL].");
                } else {
                    userGroupsDao.physicalDelete(userGroupsEntity);
                    addMsgSuccess("message.success.delete");
                }
            }
        }
        return super.devolution(HttpMethod.get, "protect.Group/view");
    }

    /**
     * グループ選択のための候補をJSONで取得
     * 
     * @return
     * @throws InvalidParamException
     */
    @Get
    public Boundary typeahead() throws InvalidParamException {
        String keyword = super.getParam("keyword");
        int offset = 0;
        int limit = 10;
        String off = super.getParam("offset");
        if (StringUtils.isInteger(off)) {
            offset = Integer.parseInt(off);
        }

        GroupLogic groupLogic = GroupLogic.get();
        List<GroupsEntity> groups = groupLogic.selectOnKeyword(keyword, super.getLoginedUser(), offset * limit, limit);
        List<LabelValue> aHeads = new ArrayList<>();
        for (GroupsEntity groupsEntity : groups) {
            LabelValue aHead = new LabelValue();
            aHead.setValue(String.valueOf(groupsEntity.getGroupId()));
            aHead.setLabel(groupsEntity.getGroupName());
            aHeads.add(aHead);
        }
        return send(aHeads);
    }

    /**
     * 自身が所属してるグループ選択のための候補をJSONで取得
     *
     * @return
     * @throws InvalidParamException
     */
    @Get
    public Boundary json() throws InvalidParamException {
        int limit = 10;
        String keyword = super.getParam("keyword");
        Integer offset = super.getPathInteger(0);
        LoginedUser loginedUser = super.getLoginedUser();

        List<GroupsEntity> groups = new ArrayList<GroupsEntity>();
        if (loginedUser == null) {
            return send(groups);
        }

        if (loginedUser.isAdmin()) {
            groups = GroupLogic.get().selectOnKeyword(keyword, loginedUser, offset * limit, limit);
        } else {
            groups = GroupLogic.get().selectMyGroupOnKeyword(keyword, loginedUser, offset * limit, limit);
        }

        return send(groups);
    }

    /**
     * 非公開グループにユーザを追加
     * 
     * @return
     */
    @Post(subscribeToken = "knowledge")
    public Boundary addUsers() {
        Integer groupId = getParam("group", Integer.class);
        String users = getParam("users");
        GroupLogic groupLogic = GroupLogic.get();
        MessageResult result = groupLogic.addUsers(super.getLoginedUser(), groupId, users);
        return send(result);
    }
    
    /**
     * メールアドレス抽出
     * 
     * @return
     */
    @Post(subscribeToken = "knowledge")
    public Boundary extractEmail() {
        String str = getParam("str");
        Matcher m = Pattern.compile("[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+").matcher(str);
        List<String> list = new ArrayList<>();
        while (m.find()) {
            if (!list.contains(m.group())) {
                list.add(m.group());
            }
        }
        StringList stringList = new StringList();
        stringList.setList(list);
        return send(stringList);
    }
    
    /**
     * メールアドレスの一覧から、グループのユーザを一括登録
     * @return
     */
    @Post(subscribeToken = "knowledge")
    public Boundary addUsersOnEmail() {
        Integer groupId = getParam("group", Integer.class);
        GroupsEntity group = GroupLogic.get().getEditAbleGroup(groupId, getLoginedUser());
        if (group == null) {
            // 編集可能なグループが存在しない
            return sendError(HttpStatus.SC_403_FORBIDDEN, "");
        }
        
        String emails = getParam("emails");
        String[] mails = emails.split("\n");
        MessageResult result = new MessageResult(MessageStatus.Success, HttpStatus.SC_200_OK, "", "");
        StringBuilder msg = new StringBuilder();
        int count = 0;
        for (String string : mails) {
            if (count > 0) {
                msg.append("\n");
            }
            count++;
            UsersEntity users = UsersDao.get().selectOnMail(string);
            if (users != null) {
                if (UserGroupsDao.get().selectOnKey(groupId, users.getUserId()) == null) {
                    UserGroupsEntity userGroupsEntity = new UserGroupsEntity();
                    userGroupsEntity.setGroupId(groupId);
                    userGroupsEntity.setUserId(users.getUserId());
                    userGroupsEntity.setGroupRole(CommonWebParameter.GROUP_ROLE_MEMBER);
                    UserGroupsDao.get().insert(userGroupsEntity);
                    msg.append(super.getResource("message.success.insert.target", string));
                } else {
                    msg.append(super.getResource("errors.exist", string));
                }
            } else {
                msg.append(super.getResource("errors.noexist", string));
            }
        }
        result.setMessage(msg.toString());
        return send(result);
    }
    
}
