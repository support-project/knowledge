package org.support.project.knowledge.vo;

import org.support.project.web.entity.UsersEntity;

public class GroupUser extends UsersEntity {

    /** シリアルバージョン */
    private static final long serialVersionUID = 1L;

    private int groupRole;

    /**
     * @return the groupRole
     */
    public int getGroupRole() {
        return groupRole;
    }

    /**
     * @param groupRole the groupRole to set
     */
    public void setGroupRole(int groupRole) {
        this.groupRole = groupRole;
    }

}
