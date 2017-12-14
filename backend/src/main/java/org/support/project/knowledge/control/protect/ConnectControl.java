package org.support.project.knowledge.control.protect;

import java.io.IOException;
import java.util.List;

import org.apache.directory.api.ldap.model.exception.LdapException;
import org.support.project.common.config.INT_FLAG;
import org.support.project.common.util.StringUtils;
import org.support.project.knowledge.control.Control;
import org.support.project.web.bean.LdapInfo;
import org.support.project.web.boundary.Boundary;
import org.support.project.web.common.HttpStatus;
import org.support.project.web.control.service.Get;
import org.support.project.web.control.service.Post;
import org.support.project.web.dao.LdapConfigsDao;
import org.support.project.web.dao.UserAliasDao;
import org.support.project.web.dao.UsersDao;
import org.support.project.web.entity.LdapConfigsEntity;
import org.support.project.web.entity.UserAliasEntity;
import org.support.project.web.entity.UsersEntity;
import org.support.project.web.logic.LdapLogic;

/**
 * Ldapなどの外部サービスを使った認証の接続設定
 * @author koda
 */
public class ConnectControl extends Control {
    @Get
    public Boundary index() {
        List<LdapConfigsEntity> ldapConfigs = LdapConfigsDao.get().selectAll();
        setAttribute("ldapConfigs", ldapConfigs);
        
        List<UserAliasEntity> alias = UserAliasDao.get().selectOnUserId(getLoginUserId());
        setAttribute("alias", alias);
        
        return forward("index.jsp");
    }
    
    /**
     * 指定のユーザのLdap認証設定を設定する
     * @return
     */
    @Get(publishToken="csrf")
    public Boundary config() {
        String key = getParam("key");
        if (StringUtils.isEmpty(key)) {
            return sendError(HttpStatus.SC_400_BAD_REQUEST, "BAD_REQUEST");
        }
        LdapConfigsEntity config = LdapConfigsDao.get().selectOnKey(key);
        if (config == null) {
            return sendError(HttpStatus.SC_400_BAD_REQUEST, "BAD_REQUEST");
        }
        setAttribute("config", config);
        
        UserAliasEntity alias = UserAliasDao.get().selectOnKey(key, getLoginUserId());
        setAttribute("alias", alias);
        
        if (alias != null) {
            List<UserAliasEntity> aliases = UserAliasDao.get().selectOnUserId(getLoginUserId());
            if (aliases.size() == 1) {
                setAttribute("onlyone", Boolean.TRUE);
            } else {
                setAttribute("onlyone", Boolean.FALSE);
            }
        }
        
        return forward("config.jsp");
    }
    
    @Post(subscribeToken="csrf")
    public Boundary connect() {
        String key = getParam("key");
        if (StringUtils.isEmpty(key)) {
            return sendError(HttpStatus.SC_400_BAD_REQUEST, "BAD_REQUEST");
        }
        LdapConfigsEntity config = LdapConfigsDao.get().selectOnKey(key);
        if (config == null) {
            return sendError(HttpStatus.SC_400_BAD_REQUEST, "BAD_REQUEST");
        }
        String id = getParam("username");
        String password = getParam("password");
        if (StringUtils.isEmpty(id) || StringUtils.isEmpty(password)) {
            super.addMsgWarn("message.login.error");
            return config();
        }
        String userInfoUpdate = getParam("userInfoUpdate");
        if (StringUtils.isEmpty(userInfoUpdate)) {
            userInfoUpdate = "0";
        }
        try {
            int update = Integer.parseInt(userInfoUpdate);
            
            UserAliasEntity exists = UserAliasDao.get().selectOnAliasKey(key, id);
            if (exists != null) {
                super.addMsgWarn("errors.exist", getResource("knowledge.connect.label.account"));
                return config();
            }
            
            // LdapAuth
            LdapLogic ldapLogic = LdapLogic.get();
            LdapInfo ldapInfo = ldapLogic.auth(config, id, password);
            if (ldapInfo == null) {
                super.addMsgWarn("message.login.error");
                return config();
            }
            
            UserAliasEntity alias = new UserAliasEntity();
            alias.setAuthKey(key);
            alias.setUserId(getLoginUserId());
            alias.setAliasKey(id);
            alias.setAliasName(ldapInfo.getName().toLowerCase());
            alias.setAliasMail(ldapInfo.getMail());
            alias.setUserInfoUpdate(update);
            UserAliasDao.get().save(alias);
            
            if (update == 1) {
                // ユーザの情報を更新する
                UsersEntity user = UsersDao.get().selectOnKey(getLoginUserId());
                if (StringUtils.isNotEmpty(ldapInfo.getName())) {
                    user.setUserName(ldapInfo.getName());
                }
                if (StringUtils.isNotEmpty(ldapInfo.getMail())) {
                    user.setMailAddress(ldapInfo.getMail());
                }
                UsersDao.get().save(user);
            }
            return config();
        } catch (IOException | LdapException e) {
            super.addMsgWarn("message.login.error");
            return config();
        } catch (NumberFormatException e) {
            return sendError(HttpStatus.SC_400_BAD_REQUEST, "BAD_REQUEST");
        }
    }

    @Post(subscribeToken="csrf")
    public Boundary disconnect() {
        String key = getParam("key");
        if (StringUtils.isEmpty(key)) {
            return sendError(HttpStatus.SC_400_BAD_REQUEST, "BAD_REQUEST");
        }
        LdapConfigsEntity config = LdapConfigsDao.get().selectOnKey(key);
        if (config == null) {
            return sendError(HttpStatus.SC_400_BAD_REQUEST, "BAD_REQUEST");
        }
        UserAliasEntity exists = UserAliasDao.get().selectOnKey(key, getLoginUserId());
        if (exists == null) {
            super.addMsgWarn("errors.noexist", getResource("knowledge.connect.label.account"));
            return index();
        }
        UserAliasDao.get().physicalDelete(exists);
        super.addMsgInfo("message.success.delete");
        return index();
    }
    
    @Post(subscribeToken="csrf")
    public Boundary update() {
        String key = getParam("key");
        if (StringUtils.isEmpty(key)) {
            return sendError(HttpStatus.SC_400_BAD_REQUEST, "BAD_REQUEST");
        }
        LdapConfigsEntity config = LdapConfigsDao.get().selectOnKey(key);
        if (config == null) {
            return sendError(HttpStatus.SC_400_BAD_REQUEST, "BAD_REQUEST");
        }
        String userInfoUpdate = getParam("userInfoUpdate");
        if (StringUtils.isEmpty(userInfoUpdate)) {
            userInfoUpdate = "0";
        }
        try {
            int update = Integer.parseInt(userInfoUpdate);
            
            UserAliasEntity exists = UserAliasDao.get().selectOnKey(key, getLoginUserId());
            if (exists == null) {
                super.addMsgWarn("errors.noexist", getResource("knowledge.connect.label.account"));
                return index();
            }
            exists.setUserInfoUpdate(update);
            UserAliasDao.get().save(exists);
            
            if (update == INT_FLAG.ON.getValue()) {
                UsersEntity user = UsersDao.get().selectOnKey(getLoginUserId());
                user.setUserName(exists.getAliasName());
                user.setMailAddress(exists.getAliasMail());
                UsersDao.get().save(user);
            }
            
            super.addMsgInfo("message.success.update");
            return config();
        } catch (NumberFormatException e) {
            return sendError(HttpStatus.SC_400_BAD_REQUEST, "BAD_REQUEST");
        }
    }
    
    
    

}
