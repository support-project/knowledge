package org.support.project.knowledge.logic;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.config.AppConfig;
import org.support.project.knowledge.config.SystemConfig;
import org.support.project.web.dao.LdapConfigsDao;
import org.support.project.web.dao.SystemConfigsDao;
import org.support.project.web.entity.LdapConfigsEntity;
import org.support.project.web.entity.SystemConfigsEntity;

@DI(instance = Instance.Singleton)
public class SystemConfigLogic {

    public static SystemConfigLogic get() {
        return Container.getComp(SystemConfigLogic.class);
    }

    /**
     * ユーザ自身の手でユーザ追加のリクエストを出せるかチェック
     * 
     * @return
     */
    public boolean isUserAddAble() {
        SystemConfigsDao systemConfigsDao = SystemConfigsDao.get();
        SystemConfigsEntity userAddType = systemConfigsDao.selectOnKey(SystemConfig.USER_ADD_TYPE, AppConfig.get().getSystemName());
        if (userAddType == null) {
            return false;
        }
        if (userAddType.getConfigValue().equals(SystemConfig.USER_ADD_TYPE_VALUE_ADMIN)) {
            return false;
        }

        LdapConfigsDao ldapConfigsDao = LdapConfigsDao.get();
        LdapConfigsEntity ldapConfigsEntity = ldapConfigsDao.selectOnKey(AppConfig.get().getSystemName());
        if (ldapConfigsEntity != null && ldapConfigsEntity.isLdapLoginOnly()) {
            return false; // Ldapでのみ認証するので、新規ユーザの追加はできない
        }
        return true;
    }
}
