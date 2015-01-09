package org.support.project.knowledge.entity;

import java.util.List;
import java.util.Map;
import java.sql.Timestamp;

import org.support.project.common.bean.ValidateError;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.entity.gen.GenCommentsEntity;


/**
 * コメント
 */
@DI(instance=Instance.Prototype)
public class CommentsEntity extends GenCommentsEntity {

	/** SerialVersion */
	private static final long serialVersionUID = 1L;
	
	/** 更新ユーザ名 */
	private String updateUserName;
	
	
	/**
	 * インスタンス取得
	 * AOPに対応
	 * @return インスタンス
	 */
	public static CommentsEntity get() {
		return Container.getComp(CommentsEntity.class);
	}

	/**
	 * コンストラクタ
	 */
	public CommentsEntity() {
		super();
	}

	/**
	 * コンストラクタ
	 * @param commentNo コメント番号
	 */

	public CommentsEntity(Long commentNo) {
		super( commentNo);
	}

	/**
	 * @return the updateUserName
	 */
	public String getUpdateUserName() {
		return updateUserName;
	}

	/**
	 * @param updateUserName the updateUserName to set
	 */
	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName;
	}

}
