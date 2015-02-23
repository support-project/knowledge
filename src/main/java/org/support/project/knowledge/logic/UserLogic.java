package org.support.project.knowledge.logic;

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
import org.support.project.di.Container;
import org.support.project.knowledge.config.SystemConfig;
import org.support.project.web.bean.LoginedUser;
import org.support.project.web.dao.ProvisionalRegistrationsDao;
import org.support.project.web.dao.RolesDao;
import org.support.project.web.dao.UserRolesDao;
import org.support.project.web.dao.UsersDao;
import org.support.project.web.entity.ProvisionalRegistrationsEntity;
import org.support.project.web.entity.RolesEntity;
import org.support.project.web.entity.UserRolesEntity;
import org.support.project.web.entity.UsersEntity;

public class UserLogic {
	/** ログ */
	private static Log LOG = LogFactory.getLog(UserLogic.class);

	private UsersDao usersDao = UsersDao.get();
	private RolesDao rolesDao = RolesDao.get();
	private UserRolesDao userRolesDao = UserRolesDao.get();
	
	public static UserLogic get() {
		return Container.getComp(UserLogic.class);
	}
	
	/**
	 * ユーザを新規登録
	 * @param user
	 * @param roles
	 * @param loginedUser
	 * @return
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public UsersEntity insert(UsersEntity user, String[] roles, LoginedUser loginedUser) {
		LOG.trace("insert");
		
		user.setEncrypted(false);
		user = usersDao.insert(user);
		user.setPassword("");
		
		// ロールをセット
		insertRoles(user, roles);
		
		return user;
	}
	
	
	/**
	 * 仮登録から、本登録
	 * @param user
	 * @param roles
	 * @param loginedUser
	 * @return
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
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
		user.setSalt(entity.getSalt());
		user.setEncrypted(true);
		String[] roles = {SystemConfig.ROLE_USER};
		
		user = usersDao.insert(user);
		user.setPassword("");
		
		// ロールをセット
		insertRoles(user, roles);
		
		// 仮登録の削除
		ProvisionalRegistrationsDao provisionalRegistrationsDao = ProvisionalRegistrationsDao.get();
		provisionalRegistrationsDao.deleteOnUserKey(entity.getUserKey());
		
		return user;
	}

	/**
	 * ユーザ情報更新
	 * @param user
	 * @param roles
	 * @param loginedUser
	 * @return
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public UsersEntity update(UsersEntity user, String[] roles, LoginedUser loginedUser) {
		LOG.trace("update");
		
		user = usersDao.update(user);
		user.setPassword("");
		
		// ロールをセット
		insertRoles(user, roles);
		
		return user;
	}

	/**
	 * ユーザのロールを登録
	 * （デリートインサート
	 * @param user
	 * @param roles 設定されているID
	 * @param loginedUser
	 */
	private void insertRoles(UsersEntity user, String[] roles) {
		LOG.info(roles);
		
		
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
	 * @param loginUserId
	 * @param knowledgeRemove
	 * @param locale 
	 * @throws Exception 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void withdrawal(Integer loginUserId, boolean knowledgeRemove, Locale locale) throws Exception {
		// アカウント削除
		UsersDao usersDao = UsersDao.get();
		UsersEntity user = usersDao.selectOnKey(loginUserId);
		if (user != null) {
			user.setPassword(RandomUtil.randamGen(32));
			user.setUserKey(RandomUtil.randamGen(32));
			Resources resources = Resources.getInstance(locale);
			user.setUserName(resources.getResource("knowledge.withdrawal.label.name"));
			user.setDeleteFlag(INT_FLAG.ON.getValue());
			usersDao.update(user);
			usersDao.delete(loginUserId);
		}
		
		if (knowledgeRemove) {
			// ナレッジを削除
			KnowledgeLogic.get().deleteOnUser(loginUserId);
		}
	}


}
