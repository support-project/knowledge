package org.support.project.web.logic.impl;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.support.project.common.config.ConfigLoader;
import org.support.project.common.util.PasswordUtil;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.ormapping.common.DBUserPool;
import org.support.project.web.bean.AccessUser;
import org.support.project.web.common.HttpUtil;
import org.support.project.web.config.AppConfig;
import org.support.project.web.config.CommonWebParameter;
import org.support.project.web.dao.FunctionsDao;
import org.support.project.web.dao.GroupsDao;
import org.support.project.web.dao.RoleFunctionsDao;
import org.support.project.web.dao.RolesDao;
import org.support.project.web.dao.UsersDao;
import org.support.project.web.entity.FunctionsEntity;
import org.support.project.web.entity.GroupsEntity;
import org.support.project.web.entity.RoleFunctionsEntity;
import org.support.project.web.entity.RolesEntity;
import org.support.project.web.entity.UsersEntity;
import org.support.project.web.exception.AuthenticateException;
import org.support.project.web.logic.AuthenticationLogic;
import org.support.project.web.logic.HttpRequestCheckLogic;
import org.support.project.web.util.ThredUserPool;

@DI(instance = Instance.Singleton)
public abstract class AbstractAuthenticationLogic<T extends AccessUser> implements AuthenticationLogic<T> {
    /**
     * ロールが必要な機能のリスト
     */
    private Map<String, List<Integer>> roleRequireFunctionList = null;

    private boolean init = false;

    /**
     * コンストラクタ
     */
    public AbstractAuthenticationLogic() {
        super();
    }

    /**
     * 初期化処理
     */
    protected void initLogic() {
        if (!init) {
            // ロールと機能のリストを読み込む
            roleRequireFunctionList = new HashMap<String, List<Integer>>();
            FunctionsDao functionsDao = FunctionsDao.get();
            RoleFunctionsDao roleFunctionsDao = RoleFunctionsDao.get();

            List<FunctionsEntity> functionsEntities = functionsDao.selectAll();
            for (FunctionsEntity functionsEntity : functionsEntities) {
                List<RoleFunctionsEntity> roleFunctionsEntities = roleFunctionsDao.selectOnFunction(functionsEntity.getFunctionKey());
                List<Integer> roles = new ArrayList<>();
                for (RoleFunctionsEntity roleFunctionsEntity : roleFunctionsEntities) {
                    roles.add(roleFunctionsEntity.getRoleId());
                }
                roleRequireFunctionList.put(functionsEntity.getFunctionKey(), roles);
            }
            init = true;
        }
    }

    /**
     * 認証 デフォルトでは、Database認証
     * @param userId userId
     * @param password password
     * @throws AuthenticateException AuthenticateException
     */
    @Override
    public int auth(String userId, String password) throws AuthenticateException {
        if (!init) {
            initLogic();
        }

        try {
            UsersDao usersDao = UsersDao.get();
            UsersEntity usersEntity = usersDao.selectOnUserKey(userId);

            AppConfig config = ConfigLoader.load(AppConfig.APP_CONFIG, AppConfig.class);
            if (usersEntity != null) {
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
     * ログインしているかチェック
     * @param request request
     * @throws AuthenticateException AuthenticateException
     */
    @Override
    public boolean isLogined(HttpServletRequest request) throws AuthenticateException {
        if (getSession(request) != null) {
            AccessUser loginedUser = getSession(request);
            if (loginedUser.getUserInfomation() == null) {
                return false;
            }
            setUserInfo(request);
            return true;
        } else {
            clearUserInfo();
        }
        return false;
    }

    /**
     * セッション情報を作成
     * @param userId userId
     * @param request request
     * @param response response
     * @throws AuthenticateException AuthenticateException
     */
    @Override
    public void setSession(String userId, HttpServletRequest request, HttpServletResponse response) throws AuthenticateException {
        try {
            HttpSession session = request.getSession();
            session.setAttribute(CommonWebParameter.LOGIN_USER_ID_SESSION_KEY, userId);

            UsersDao usersDao = UsersDao.get();
            UsersEntity usersEntity = usersDao.selectOnUserKey(userId);
            RolesDao rolesDao = RolesDao.get();
            List<RolesEntity> rolesEntities = rolesDao.selectOnUserKey(userId);
            session.setAttribute(CommonWebParameter.LOGIN_ROLE_IDS_SESSION_KEY, rolesEntities);

            AccessUser loginedUser = new AccessUser();
            loginedUser.setUserInfomation(usersEntity);
            loginedUser.setRoles(rolesEntities);
            loginedUser.setLocale(HttpUtil.getLocale(request));

            // グループ
            GroupsDao groupsDao = GroupsDao.get();
            List<GroupsEntity> groups = groupsDao.selectMyGroup(loginedUser, 0, Integer.MAX_VALUE);
            loginedUser.setGroups(groups);

            session.setAttribute(CommonWebParameter.LOGIN_USER_INFO_SESSION_KEY, loginedUser);

            setUserInfo(request);
        } catch (Exception e) {
            throw new AuthenticateException(e);
        }

    }

    /**
     * 認可 Databaseの認可情報により、指定のパスにアクセス出来るかチェック ※Controlのインタフェースメソッドに、アクセス出来るロールが指定されているが、 そのチェックとは別なので、わかりづらいかも。。。
     * Controlにある認可チェックは、ControlManagerFilterで処理している。
     * @param request request
     */
    @Override
    public boolean isAuthorize(HttpServletRequest request) throws AuthenticateException {
        if (!init) {
            initLogic();
        }

        String path = request.getServletPath();

        Iterator<String> iterator = roleRequireFunctionList.keySet().iterator();
        while (iterator.hasNext()) {
            String function = (String) iterator.next();
            if (path.startsWith(function)) {
                HttpSession session = request.getSession();
                AccessUser loginedUser = (AccessUser) session.getAttribute(CommonWebParameter.LOGIN_USER_INFO_SESSION_KEY);

                if (loginedUser == null) {
                    return false;
                }

                List<Integer> accessRoles = roleRequireFunctionList.get(function);
                List<RolesEntity> userRoles = loginedUser.getRoles();
                for (RolesEntity userRole : userRoles) {
                    if (accessRoles.contains(userRole.getRoleId())) {
                        return true;
                    }
                }
                return false;
            }
        }
        // デフォルトはアクセス可能
        return true;
    }

    /**
     * ログインセッション情報を取得
     * 
     * @param request
     *            request
     */
    @Override
    public T getSession(HttpServletRequest request) throws AuthenticateException {
        HttpSession session = request.getSession();
        return (T) session.getAttribute(CommonWebParameter.LOGIN_USER_INFO_SESSION_KEY);
    }

    /**
     * ログインセッション情報をクリア
     * 
     * @param request
     *            request
     */
    @Override
    public void clearSession(HttpServletRequest request) throws AuthenticateException {
        HttpSession session = request.getSession();
        session.removeAttribute(CommonWebParameter.LOGIN_USER_ID_SESSION_KEY);
        session.removeAttribute(CommonWebParameter.LOGIN_ROLE_IDS_SESSION_KEY);
        session.removeAttribute(CommonWebParameter.LOGIN_USER_INFO_SESSION_KEY);
        session.removeAttribute(HttpRequestCheckLogic.CSRF_TOKENS);
        session.removeAttribute(HttpRequestCheckLogic.CSRF_REQIDS);
    }

    /**
     * このリクエストを処理する間のユーザ情報をセットしておく
     * 
     * @param request
     *            request
     */
    protected void setUserInfo(HttpServletRequest request) {
        AccessUser loginedUser = getSession(request);
        DBUserPool.get().setUser(loginedUser.getUserId());
        ThredUserPool.get().setInfo(CommonWebParameter.LOGIN_USER_INFO_SESSION_KEY, loginedUser);
    }

    /**
     * このリクエスト(Thread)にセットされているユーザ情報をクリアする
     */
    protected void clearUserInfo() {
        DBUserPool.get().clearUser();
        ThredUserPool.get().clearInfo();
    }

}
