package org.support.project.knowledge.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.web.bean.AccessUser;
import org.support.project.web.config.CommonWebParameter;
import org.support.project.web.config.RoleType;
import org.support.project.web.entity.RolesEntity;

@DI(instance = Instance.Singleton)
public class UserLogicEx extends org.support.project.web.logic.UserLogic {

    public static UserLogicEx get() {
        return Container.getComp(UserLogicEx.class);
    }

    /**
     * 退会
     * 
     * @param loginUserId
     * @param knowledgeRemove
     * @param locale
     * @throws Exception
     */
    public void withdrawal(Integer loginUserId, boolean knowledgeRemove, Locale locale) throws Exception {
        super.withdrawal(loginUserId, locale);
        if (knowledgeRemove) {
            // ナレッジを削除
            KnowledgeLogic.get().deleteOnUser(loginUserId);
        }
    }
    /**
     * バッチなどで、管理者権限として動作させたい場合に、管理者ユーザのオブジェクトを取得する
     * @return
     */
    public AccessUser getAdminUser() {
        AccessUser admin = new AccessUser();
        List<RolesEntity> roles = new ArrayList<>();
        RolesEntity role = new RolesEntity(RoleType.admin.ordinal());
        role.setRoleKey(CommonWebParameter.ROLE_ADMIN);
        roles.add(role);
        admin.setRoles(roles);
        return admin;
    }

}
