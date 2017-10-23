package org.support.project.knowledge.control.api;

import org.support.project.common.util.PropertyUtil;
import org.support.project.common.util.StringUtils;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.dao.ExGroupsDao;
import org.support.project.knowledge.logic.GroupLogic;
import org.support.project.knowledge.logic.KnowledgeDataSelectLogic;
import org.support.project.knowledge.vo.GroupUser;
import org.support.project.knowledge.vo.SearchKnowledgeParam;
import org.support.project.knowledge.vo.api.Group;
import org.support.project.knowledge.vo.api.GroupDetail;
import org.support.project.knowledge.vo.api.Knowledge;
import org.support.project.knowledge.vo.api.User;
import org.support.project.web.bean.ApiParams;
import org.support.project.web.boundary.Boundary;
import org.support.project.web.common.HttpStatus;
import org.support.project.web.control.GetApiControl;
import org.support.project.web.control.service.Get;
import org.support.project.web.entity.GroupsEntity;

import java.util.ArrayList;
import java.util.List;

@DI(instance = Instance.Prototype)
public class GroupsControl extends GetApiControl {
    /**
     * List groups
     */
    @Get(path="api/groups", publishToken="")
    public Boundary index() {
        return get();
    }

    @Override
    public Boundary getList(ApiParams params) {
        String keyword = getParam("keyword");
        List<Group> results = new ArrayList<>();
        List<GroupsEntity> groups = GroupLogic.get().selectOnKeyword(keyword, super.getLoginedUser(), params.getOffset(), params.getLimit());
        for (GroupsEntity groupsEntity : groups) {
            Group group = new Group();
            PropertyUtil.copyPropertyValue(groupsEntity, group);
            results.add(group);
        }
        return send(results);
    }

    @Override
    public Boundary getSingle(String id) {
        if (!StringUtils.isInteger(id)) {
            return sendError(HttpStatus.SC_400_BAD_REQUEST);
        }
        int groupId = Integer.parseInt(id);
        // グループの情報にアクセスできるかのチェックを含めてグループ情報を取得
        GroupsEntity group = GroupLogic.get().getGroup(groupId, getLoginedUser());
        if (group == null) {
            return sendError(HttpStatus.SC_404_NOT_FOUND);
        }
        
        // グループそのものの情報が取得したいのか、その下位の情報を取得したいかをチェック
        String[] pathinfos = getPathInfos();
        if (pathinfos.length > 1) {
            String submethod = pathinfos[1];
            if (StringUtils.isNotEmpty(submethod)) {
                if (submethod.equals("knowledges")) {
                    return getKnowledgeList(group);
                } else if (submethod.equals("users")) {
                    return getUserList(group);
                }
            }
        }
        
        // グループの情報を取得
        GroupDetail result = new GroupDetail();
        PropertyUtil.copyPropertyValue(group, result);
        
        result.setGroupKnowledgeCount(ExGroupsDao.get().selectGroupKnowledgeCount(groupId));
        result.setUserCount(ExGroupsDao.get().selectGroupUserCount(groupId));
        
        return send(result);
    }

    private Boundary getUserList(GroupsEntity group) {
        ApiParams params = getApiParams();
        List<GroupUser> users = GroupLogic.get().getGroupUsers(group.getGroupId(), params.getOffset(), params.getLimit());
        List<User> results = new ArrayList<>();
        for (GroupUser usersEntity : users) {
            User user = new User();
            PropertyUtil.copyPropertyValue(usersEntity, user);
            results.add(user);
        }
        return send(HttpStatus.SC_200_OK, results);
    }
    private Boundary getKnowledgeList(GroupsEntity group) {
        List<GroupsEntity> groups = new ArrayList<>();
        groups.add(group);
        ApiParams params = getApiParams();
        SearchKnowledgeParam param = new SearchKnowledgeParam();
        param.setLimit(params.getLimit());
        param.setOffset(params.getOffset());
        param.setKeyword("");
        param.setTags("");
        param.setGroups(groups);
        param.setTemplate("");
        param.setLoginedUser(getLoginedUser());
        try {
            List<Knowledge> results = KnowledgeDataSelectLogic.get().selectList(param);
            return send(HttpStatus.SC_200_OK, results);
        } catch (Exception e) {
            return sendError(HttpStatus.SC_500_INTERNAL_SERVER_ERROR);
        }
    }

}
