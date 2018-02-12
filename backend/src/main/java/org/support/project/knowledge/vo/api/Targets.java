package org.support.project.knowledge.vo.api;

import java.util.ArrayList;
import java.util.List;

/**
 * 閲覧可能／編集可能に指定されているターゲット情報
 * @author koda
 */
public class Targets {

    private List<Target> groups = new ArrayList<>();
    private List<Target> users = new ArrayList<>();
    /**
     * @return the groups
     */
    public List<Target> getGroups() {
        return groups;
    }
    /**
     * @param groups the groups to set
     */
    public void setGroups(List<Target> groups) {
        this.groups = groups;
    }
    /**
     * @return the users
     */
    public List<Target> getUsers() {
        return users;
    }
    /**
     * @param users the users to set
     */
    public void setUsers(List<Target> users) {
        this.users = users;
    }
    
}
