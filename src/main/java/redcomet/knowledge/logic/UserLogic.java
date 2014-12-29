package redcomet.knowledge.logic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import redcomet.aop.Aspect;
import redcomet.common.config.INT_FLAG;
import redcomet.common.log.Log;
import redcomet.common.log.LogFactory;
import redcomet.common.util.RandomUtil;
import redcomet.di.Container;
import redcomet.web.bean.LoginedUser;
import redcomet.web.dao.RolesDao;
import redcomet.web.dao.UserRolesDao;
import redcomet.web.dao.UsersDao;
import redcomet.web.entity.RolesEntity;
import redcomet.web.entity.UserRolesEntity;
import redcomet.web.entity.UsersEntity;

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
	@Aspect(advice=redcomet.ormapping.transaction.Transaction.class)
	public UsersEntity insert(UsersEntity user, String[] roles, LoginedUser loginedUser) {
		LOG.trace("insert");
		
		user.setEncrypted(false);
		user = usersDao.insert(user);
		user.setPassword("");
		
		// ロールをセット
		insertRoles(user, roles, loginedUser);
		
		return user;
	}

	/**
	 * ユーザ情報更新
	 * @param user
	 * @param roles
	 * @param loginedUser
	 * @return
	 */
	@Aspect(advice=redcomet.ormapping.transaction.Transaction.class)
	public UsersEntity update(UsersEntity user, String[] roles, LoginedUser loginedUser) {
		LOG.trace("update");
		
		user = usersDao.update(user);
		user.setPassword("");
		
		// ロールをセット
		insertRoles(user, roles, loginedUser);
		
		return user;
	}

	/**
	 * ユーザのロールを登録
	 * （デリートインサート
	 * @param user
	 * @param roles 設定されているID
	 * @param loginedUser
	 */
	private void insertRoles(UsersEntity user, String[] roles, LoginedUser loginedUser) {
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
	 * @throws Exception 
	 */
	@Aspect(advice=redcomet.ormapping.transaction.Transaction.class)
	public void withdrawal(Integer loginUserId, boolean knowledgeRemove) throws Exception {
		// アカウント削除
		UsersDao usersDao = UsersDao.get();
		UsersEntity user = usersDao.selectOnKey(loginUserId);
		if (user != null) {
			user.setPassword(RandomUtil.randamGen(32));
			user.setUserKey(RandomUtil.randamGen(32));
			user.setUserName("削除済ユーザー");
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
