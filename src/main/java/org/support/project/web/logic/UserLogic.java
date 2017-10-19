package org.support.project.web.logic;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.support.project.aop.Aspect;
import org.support.project.common.config.INT_FLAG;
import org.support.project.common.config.Resources;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.RandomUtil;
import org.support.project.common.util.StringUtils;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.web.bean.LoginedUser;
import org.support.project.web.config.AppConfig;
import org.support.project.web.config.CommonWebParameter;
import org.support.project.web.config.WebConfig;
import org.support.project.web.dao.ProvisionalRegistrationsDao;
import org.support.project.web.dao.RolesDao;
import org.support.project.web.dao.UserAliasDao;
import org.support.project.web.dao.UserGroupsDao;
import org.support.project.web.dao.UserRolesDao;
import org.support.project.web.dao.UsersDao;
import org.support.project.web.entity.ProvisionalRegistrationsEntity;
import org.support.project.web.entity.RolesEntity;
import org.support.project.web.entity.UserAliasEntity;
import org.support.project.web.entity.UserGroupsEntity;
import org.support.project.web.entity.UserRolesEntity;
import org.support.project.web.entity.UsersEntity;

/**
 * User logic
 * @author Koda
 */
@DI(instance = Instance.Singleton)
public class UserLogic {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(UserLogic.class);

    private UsersDao usersDao = UsersDao.get();
    private UserRolesDao userRolesDao = UserRolesDao.get();
    /**
     * get instance
     * @return instance
     */
    public static UserLogic get() {
        return Container.getComp(UserLogic.class);
    }

    /**
     * ユーザを新規登録
     * @param user UsersEntity
     * @param roles roles
     * @return UsersEntity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public UsersEntity insert(UsersEntity user, String[] roles) {
        LOG.trace("insert");

        user.setEncrypted(false);
        if (StringUtils.isEmpty(user.getMailAddress())) {
            if (StringUtils.isEmailAddress(user.getUserKey())) {
                user.setMailAddress(user.getUserKey());
            }
        }
        user = usersDao.insert(user);
        user.setPassword("");

        // ロールをセット
        insertRoles(user, roles);
        insertDefaultGroup(user);

        // ユーザ登録時の拡張処理の呼び出し
        if (StringUtils.isNotEmpty(AppConfig.get().getAddUserProcess())) {
            AddUserProcess process = Container.getComp(AppConfig.get().getAddUserProcess(), AddUserProcess.class);
            process.addUserProcess(user.getUserKey());
        }
        
        return user;
    }

    /**
     * 仮登録から、本登録
     * @param entity 仮登録情報
     * @return UsersEntity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public UsersEntity activate(ProvisionalRegistrationsEntity entity) {
        LOG.trace("activate");

        if (usersDao.selectOnUserKey(entity.getUserKey()) != null) {
            // 仮登録のユーザが既に存在している
            ProvisionalRegistrationsDao provisionalRegistrationsDao = ProvisionalRegistrationsDao.get();
            provisionalRegistrationsDao.deleteOnUserKey(entity.getUserKey());
            return null;
        }

        UsersEntity user = new UsersEntity();
        user.setUserKey(entity.getUserKey());
        user.setUserName(entity.getUserName());
        user.setPassword(entity.getPassword());
        if (StringUtils.isEmpty(user.getMailAddress())) {
            if (StringUtils.isEmailAddress(user.getUserKey())) {
                user.setMailAddress(user.getUserKey());
            }
        }
        user.setSalt(entity.getSalt());
        user.setEncrypted(true);
        String[] roles = { WebConfig.ROLE_USER };

        user = usersDao.insert(user);
        user.setPassword("");

        // ロールをセット
        insertRoles(user, roles);
        insertDefaultGroup(user);

        // 仮登録の削除
        ProvisionalRegistrationsDao provisionalRegistrationsDao = ProvisionalRegistrationsDao.get();
        provisionalRegistrationsDao.deleteOnUserKey(entity.getUserKey());

        // ユーザ登録時の拡張処理の呼び出し
        if (StringUtils.isNotEmpty(AppConfig.get().getAddUserProcess())) {
            AddUserProcess process = Container.getComp(AppConfig.get().getAddUserProcess(), AddUserProcess.class);
            process.addUserProcess(user.getUserKey());
        }
        return user;
    }

    /**
     * ユーザ情報更新
     * 
     * @param user UsersEntity
     * @param roles roles
     * @param loginedUser LoginedUser
     * @return UsersEntity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public UsersEntity update(UsersEntity user, String[] roles, LoginedUser loginedUser) {
        LOG.trace("update");

        if (StringUtils.isEmpty(user.getMailAddress())) {
            if (StringUtils.isEmailAddress(user.getUserKey())) {
                user.setMailAddress(user.getUserKey());
            }
        }
        user = usersDao.update(user);
        user.setPassword("");

        // ロールをセット
        insertRoles(user, roles);
        insertDefaultGroup(user);

        return user;
    }

    /**
     * ユーザのロールを登録 （デリートインサート
     * 
     * @param user UsersEntity
     * @param roles
     *            設定されているID
     */
    private void insertRoles(UsersEntity user, String[] roles) {
        if (LOG.isTraceEnabled()) {
            LOG.trace(roles);
        }

        // ユーザに紐づくロールを削除
        userRolesDao.deleteOnUser(user.getUserId());

        // システムに登録されているロールを全て取得
        RolesDao rolesDao = RolesDao.get();
        List<RolesEntity> rolesEntities = rolesDao.selectAll();
        Map<String, RolesEntity> map = new HashMap<String, RolesEntity>();
        for (RolesEntity role : rolesEntities) {
            map.put(role.getRoleKey(), role);
        }

        // ユーザのロールを登録
        if (roles != null) {
            for (String role : roles) {
                RolesEntity entity = map.get(role);
                if (entity != null) {
                    UserRolesEntity userRolesEntity = new UserRolesEntity(entity.getRoleId(), user.getUserId());
                    userRolesDao.insert(userRolesEntity);
                }
            }
        }
    }

    /**
     * 退会
     * 
     * @param loginUserId loginUserId
     * @param locale Locale
     * @throws Exception Exception
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void withdrawal(Integer loginUserId, Locale locale) throws Exception {
        // アカウント削除
        UsersDao usersDao = UsersDao.get();
        UsersEntity user = usersDao.selectOnKey(loginUserId);
        if (user != null) {
            user.setPassword(RandomUtil.randamGen(32));
            user.setUserKey(RandomUtil.randamGen(32));
            user.setMailAddress(user.getUserKey());

            Resources resources = Resources.getInstance(locale);
            user.setUserName(resources.getResource("knowledge.withdrawal.label.name"));
            user.setDeleteFlag(INT_FLAG.ON.getValue());
            usersDao.update(user);
            usersDao.delete(loginUserId);
        }
        // Aliasの情報を削除
        List<UserAliasEntity> aliases = UserAliasDao.get().selectOnUserId(loginUserId);
        for (UserAliasEntity userAliasEntity : aliases) {
            UserAliasDao.get().physicalDelete(userAliasEntity);
        }
    }

    /**
     * ユーザを登録した際に、デフォルトのグループを設定する
     * 
     * @param user UsersEntity
     */
    public void insertDefaultGroup(UsersEntity user) {
        if (user == null) {
            return;
        }
        UserGroupsDao userGroupsDao = UserGroupsDao.get();
        UserGroupsEntity userGroupsEntity = userGroupsDao.selectOnKey(0, user.getUserId());
        if (userGroupsEntity == null) {
            userGroupsEntity = new UserGroupsEntity(0, user.getUserId());
            userGroupsEntity.setGroupRole(CommonWebParameter.GROUP_ROLE_MEMBER);
            userGroupsDao.insert(userGroupsEntity);
        }
    }

}
