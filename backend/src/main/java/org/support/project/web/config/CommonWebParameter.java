package org.support.project.web.config;

public class CommonWebParameter {

	public static final String LOGIN_USER_ID_SESSION_KEY = "LOGIN_USER_SESSION_KEY";
	public static final String LOGIN_ROLE_IDS_SESSION_KEY = "LOGIN_USER_ROLES_SESSION_KEY";
	public static final String LOGIN_USER_INFO_SESSION_KEY = "LOGIN_USER_INFO_SESSION_KEY";
	public static final String LOGIN_USER_KEY = "LOGIN_USER_KEY";
	
	public static final String ROLE_ADMIN = RoleType.admin.name();
	public static final String ROLE_USER = RoleType.user.name();
	
	public static final String PASSWORD_NO_EDIT = "PASSWORD_NO_EDIT-LnLza8z8i4njYF-YtNMn";
	
	public static final String ERROR_ATTRIBUTE = "ERROR_ATTRIBUTE";

	public static final String LOCALE_SESSION_KEY = "LOCALE_SESSION_KEY";
	
	/** グループの区分：公開（一覧に表示されます。誰でも自由に参加できます。） */
	public static final int GROUP_CLASS_PUBLIC = 1;
	/** グループの区分：保護（一覧に表示されます。参加するためにはグループの管理者の承認が必要になります。） */
	public static final int GROUP_CLASS_PROTECT = 2;
	/** グループの区分：非公開（一覧に表示されません。新たにユーザをグループに追加するのは、グループの管理者が行ないます） */
	public static final int GROUP_CLASS_PRIVATE = 0;
	
	/** グループの権限：管理者 */
	public static final int GROUP_ROLE_ADMIN = GroupRoleType.Manager.getValue();
	/** グループの権限：メンバー */
	public static final int GROUP_ROLE_MEMBER = GroupRoleType.Member.getValue();
	/** グループの権限：申請中 */
	public static final int GROUP_ROLE_WAIT = -1;

}
