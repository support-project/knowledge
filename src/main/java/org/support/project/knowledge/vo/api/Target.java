package org.support.project.knowledge.vo.api;

import java.util.List;

import org.support.project.web.bean.NameId;

/**
 * 閲覧可能／編集可能に指定されているターゲット情報
 * @author koda
 */
public class Target {

    private List<NameId> groups;
    private List<NameId> users;
    /**
     * @return the groups
     */
    public List<NameId> getGroups() {
        return groups;
    }
    /**
     * @param groups the groups to set
     */
    public void setGroups(List<NameId> groups) {
        this.groups = groups;
    }
    /**
     * @return the users
     */
    public List<NameId> getUsers() {
        return users;
    }
    /**
     * @param users the users to set
     */
    public void setUsers(List<NameId> users) {
        this.users = users;
    }
    
}
