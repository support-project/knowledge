package org.support.project.web.bean;

import java.sql.Timestamp;
import java.util.List;


/**
 * DBAuthenticationLogicが取得する抽象化されたユーザ情報
 */
public class User {
	
	/** ユーザID  */
	private String userId;
	/** ユーザ名  */
	private String userName;
	/** パスワード */
	private String password;
	/** 登録者 */
	private String insertUser;
	/** 登録日時 */
	private Timestamp insertDatetime;
	/** 更新者 */
	private String updateUser;
	/** 更新日時 */
	private Timestamp updateDatetime;
	
	/** ロール */
	private List<String> roleIds;
	
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getInsertUser() {
		return insertUser;
	}
	public void setInsertUser(String insertUser) {
		this.insertUser = insertUser;
	}
	public Timestamp getInsertDatetime() {
		return insertDatetime;
	}
	public void setInsertDatetime(Timestamp insertDatetime) {
		this.insertDatetime = insertDatetime;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	public Timestamp getUpdateDatetime() {
		return updateDatetime;
	}
	public void setUpdateDatetime(Timestamp updateDatetime) {
		this.updateDatetime = updateDatetime;
	}
	public List<String> getRoleIds() {
		return roleIds;
	}
	public void setRoleIds(List<String> roleIds) {
		this.roleIds = roleIds;
	}

}
