package org.support.project.web.logic.impl;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.directory.api.ldap.model.exception.LdapException;
import org.support.project.aop.Aspect;
import org.support.project.common.config.ConfigLoader;
import org.support.project.common.config.INT_FLAG;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.Compare;
import org.support.project.common.util.PasswordUtil;
import org.support.project.common.util.RandomUtil;
import org.support.project.common.util.StringUtils;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.web.bean.LdapInfo;
import org.support.project.web.bean.LoginedUser;
import org.support.project.web.bean.UserSecret;
import org.support.project.web.config.AppConfig;
import org.support.project.web.config.CommonWebParameter;
import org.support.project.web.config.WebConfig;
import org.support.project.web.dao.LdapConfigsDao;
import org.support.project.web.dao.UserAliasDao;
import org.support.project.web.dao.UsersDao;
import org.support.project.web.entity.LdapConfigsEntity;
import org.support.project.web.entity.UserAliasEntity;
import org.support.project.web.entity.UsersEntity;
import org.support.project.web.exception.AuthenticateException;
import org.support.project.web.logic.AddUserProcess;
import org.support.project.web.logic.LdapLogic;
import org.support.project.web.logic.UserLogic;

import net.arnx.jsonic.JSON;

@DI(instance = Instance.Singleton)
public class DefaultAuthenticationLogicImpl extends AbstractAuthenticationLogic<LoginedUser> {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(DefaultAuthenticationLogicImpl.class);
    
    private int cookieMaxAge = -1; // 日にち単位
    private String cookieEncryptKey = "";
    private boolean cookieSecure = true;
    
    /**
     * Cookieログインに使う情報の初期化
     * @param cookieMaxAge cookieMaxAge
     * @param cookieEncryptKey cookieEncryptKey
     * @param cookieSecure cookieSecure
     */
    public void initCookie(int cookieMaxAge, String cookieEncryptKey, boolean cookieSecure) {
        this.cookieMaxAge = cookieMaxAge;
        this.cookieEncryptKey = cookieEncryptKey;
        this.cookieSecure = cookieSecure;
    }
    
    /**
     * ログイン情報をクッキーに保持
     * 
     * @param req request
     * @param res response
     * @throws AuthenticateException AuthenticateException
     */
    public void setCookie(HttpServletRequest req, HttpServletResponse res)
            throws AuthenticateException {
        try {
            // 認証情報保持の情報をセット(暗号化)
            Cookie[] cookies = req.getCookies();
            if (cookies != null && cookieMaxAge > 0 && StringUtils.isNotEmpty(cookieEncryptKey)) {
                LoginedUser user = getSession(req);
                
                UserSecret secret = new UserSecret();
                secret.setUserKey(user.getLoginUser().getUserKey());
                secret.setUserName(user.getLoginUser().getUserName());
                secret.setEmail(user.getLoginUser().getMailAddress());
                
                String json = JSON.encode(secret);
                json = PasswordUtil.encrypt(json, cookieEncryptKey);
    
                Cookie cookie = new Cookie(CommonWebParameter.LOGIN_USER_KEY, json);
                cookie.setPath(req.getContextPath() + "/");
                cookie.setMaxAge(cookieMaxAge);
                cookie.setSecure(cookieSecure);
                res.addCookie(cookie);
            }
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            throw new AuthenticateException(e);
        }
    }
    
    /**
     * Cookieに保持しているログイン情報でログイン
     * 
     * @param req request
     * @param res response
     * @return result
     */
    public boolean cookieLogin(HttpServletRequest req, HttpServletResponse res) {
        // 認証情報を保持しているか？
        HttpSession session = req.getSession();
        if (Boolean.TRUE.equals(session.getAttribute("COOKIE_LOGIN_CHECK"))) {
            // 既にCookieでログインを試したのであれば実行しない
            return false;
        }

        Cookie[] cookies = req.getCookies();
        if (cookies != null && cookieMaxAge > 0 && StringUtils.isNotEmpty(cookieEncryptKey)) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(CommonWebParameter.LOGIN_USER_KEY)) {
                    String json = cookie.getValue();
                    try {
                        json = PasswordUtil.decrypt(json, cookieEncryptKey);
                        UserSecret user = JSON.decode(json, UserSecret.class);
                        
                        UsersEntity entity =  UsersDao.get().selectOnLowerUserKey(user.getUserKey());
                        if (entity == null) {
                            return false;
                        }
                        if (!user.getUserKey().toLowerCase().equals(entity.getUserKey().toLowerCase())
                                || !user.getUserName().equals(entity.getUserName())
                                || !StringUtils.equals(user.getEmail(), entity.getMailAddress())) {
                            LOG.info("Cookie of LOGIN_USER_KEY is invalid.");
                            return false;
                        }
                        
                        
                        LOG.debug(user.getUserKey() + " is Login(from cookie).");
                        setSession(user.getUserKey(), req, res); //セッションにLoginUserを生成

                        // Cookie再セット
                        UserSecret secret = new UserSecret();
                        secret.setUserKey(user.getUserKey());
                        secret.setUserName(user.getUserName());
                        secret.setEmail(user.getEmail());
                        json = JSON.encode(user);
                        json = PasswordUtil.encrypt(json, cookieEncryptKey);

                        cookie = new Cookie(CommonWebParameter.LOGIN_USER_KEY, json);
                        cookie.setPath(req.getContextPath() + "/");
                        cookie.setMaxAge(cookieMaxAge);
                        cookie.setSecure(cookieSecure);
                        res.addCookie(cookie);

                        // ログイン成功
                        return true;
                    } catch (Exception e) {
                        // 何もしない
                        LOG.trace("error cookieLogin.", e);
                    }
                }
            }
        }
        session.setAttribute("COOKIE_LOGIN_CHECK", Boolean.TRUE);
        return false;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see org.support.project.web.logic.impl.AbstractAuthenticationLogic#auth(java.lang.String, java.lang.String)
     */
    @Override
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public int auth(String userId, String password) throws AuthenticateException {
        initLogic();
        // Ldap認証が有効であれば、Ldap認証を実施する
        LdapConfigsDao dao = LdapConfigsDao.get();
        List<LdapConfigsEntity> ldaps = dao.selectAll();
        for (LdapConfigsEntity config : ldaps) {
            try {
                LdapLogic ldapLogic = LdapLogic.get();
                LdapInfo ldapInfo = ldapLogic.auth(config, userId, password);
                if (ldapInfo != null) {
                    // Ldap認証成功
                    UserAliasEntity alias = UserAliasDao.get().selectOnAliasKey(config.getSystemName(), userId);
                    if (alias != null) {
                        // Aliasが既にある
                        UsersDao usersDao = UsersDao.get();
                        UsersEntity usersEntity = usersDao.selectOnKey(alias.getUserId());
                        if (usersEntity == null) {
                            return Integer.MIN_VALUE;
                        } else {
                            if (Compare.equal(alias.getUserInfoUpdate(), INT_FLAG.ON.getValue())) {
                                // 情報更新するというフラグが無ければ更新しない
                                updateUser(userId, password, ldapInfo, usersDao, usersEntity);
                            }
                        }
                        return usersEntity.getUserId();
                    } else {
                        UsersDao usersDao = UsersDao.get();
                        
                        // ユーザ情報が無ければ登録、あれば更新
                        UsersEntity usersEntity = usersDao.selectOnLowerUserKey(userId);
                        if (usersEntity == null) {
                            usersEntity = addUser(userId, password, ldapInfo);
                            // 拡張処理の呼び出し
                            if (StringUtils.isNotEmpty(AppConfig.get().getAddUserProcess())) {
                                AddUserProcess process = Container.getComp(AppConfig.get().getAddUserProcess(), AddUserProcess.class);
                                process.addUserProcess(usersEntity.getUserKey());
                            }
                        } else {
                            updateUser(userId, password, ldapInfo, usersDao, usersEntity);
                        }
                        // ユーザのAliasを登録
                        alias = new UserAliasEntity();
                        alias.setUserInfoUpdate(INT_FLAG.ON.getValue());
                        alias.setUserId(usersEntity.getUserId());
                        alias.setAuthKey(config.getSystemName());
                        alias.setAliasKey(userId);
                        alias.setAliasName(ldapInfo.getName().toLowerCase());
                        alias.setAliasMail(ldapInfo.getMail());
                        UserAliasDao.get().save(alias);
                        return usersEntity.getUserId();
                    }
                }
            } catch (LdapException | IOException e) {
                throw new AuthenticateException(e);
            }
        }
        
        // DB認証開始
        try {
            if (StringUtils.isEmpty(password)) {
                return Integer.MIN_VALUE;
            }
            UsersDao usersDao = UsersDao.get();
            UsersEntity usersEntity = usersDao.selectOnUserKey(userId);
            AppConfig config = ConfigLoader.load(AppConfig.APP_CONFIG, AppConfig.class);
            if (usersEntity != null && 
                    (usersEntity.getAuthLdap() == null || usersEntity.getAuthLdap().intValue() == INT_FLAG.OFF.getValue())
            ) {
                String hash = PasswordUtil.getStretchedPassword(password, usersEntity.getSalt(), config.getHashIterations());
                if (usersEntity.getPassword().equals(hash)) {
                    return usersEntity.getUserId();
                }
            }
            return Integer.MIN_VALUE;
        } catch (NoSuchAlgorithmException e) {
            throw new AuthenticateException(e);
        }
    }

    /**
     * Ldapから取得した情報でユーザ情報更新 同一IDの
     * 
     * @param userId
     * @param ldapInfo
     * @param usersDao
     * @param usersEntity
     */
    private void updateUser(String userId, String password, LdapInfo ldapInfo, UsersDao usersDao, UsersEntity usersEntity) {
        // 既にユーザ情報は登録されているので、Ldapの情報でデータ更新があればKnowledgeのユーザ情報を更新する
        boolean change = false;
        if (StringUtils.isNotEmpty(ldapInfo.getName())) {
            if (!ldapInfo.getName().equals(usersEntity.getUserName())) {
                usersEntity.setUserName(ldapInfo.getName());
                change = true;
            }
        }
        if (StringUtils.isNotEmpty(ldapInfo.getMail())) {
            if (StringUtils.isEmailAddress(ldapInfo.getMail()) && !ldapInfo.getMail().equals(usersEntity.getMailAddress())) {
                usersEntity.setMailAddress(ldapInfo.getMail());
                change = true;
            }
        }
        if (usersEntity.getAuthLdap() == null || usersEntity.getAuthLdap().intValue() != INT_FLAG.ON.getValue()) {
            // 既にKnowledgeに登録されているユーザとLdapのユーザのIDが同じ場合は、
            // 既存のユーザ情報を更新する？？？
            // TODO 更新で良いか、検討する必要あり（いったん更新する）
            usersEntity.setAuthLdap(INT_FLAG.ON.getValue());
            change = true;
        }
        if (change) {
            usersEntity.setPassword(RandomUtil.randamGen(32));
            usersDao.save(usersEntity);
            LOG.debug("Change User info on Ldap login. [user]" + userId);
        }
    }

    /**
     * Ldapから取得した情報でユーザ登録
     * 
     * @param userId
     * @param password
     * @param ldapInfo
     */
    private UsersEntity addUser(String userId, String password, LdapInfo ldapInfo) {
        UsersEntity usersEntity;
        // Ldap認証でユーザ登録されていないユーザがログイン
        usersEntity = new UsersEntity();
        usersEntity.setUserKey(ldapInfo.getId());
        if (StringUtils.isNotEmpty(ldapInfo.getName())) {
            usersEntity.setUserName(ldapInfo.getName());
        } else {
            usersEntity.setUserName(ldapInfo.getId());
        }
        if (StringUtils.isNotEmpty(ldapInfo.getMail())) {
            if (StringUtils.isEmailAddress(ldapInfo.getMail())) {
                usersEntity.setMailAddress(ldapInfo.getMail());
            }
        }
        usersEntity.setAuthLdap(INT_FLAG.ON.getValue());
        usersEntity.setAdmin(ldapInfo.isAdmin());
        // usersEntity.setPassword(password);
        usersEntity.setPassword(RandomUtil.randamGen(24)); // Ldapログインしたユーザのパスワードは推測不可能な形にしておく

        List<String> roles = new ArrayList<String>();
        roles.add(WebConfig.ROLE_USER);
        if (ldapInfo.isAdmin()) {
            roles.add(WebConfig.ROLE_ADMIN);
        }
        usersEntity.setPassword(RandomUtil.randamGen(32));
        usersEntity = UserLogic.get().insert(usersEntity, roles.toArray(new String[0]));
        LOG.info("Add User on first Ldap login. [user]" + userId);
        return usersEntity;
    }

}
