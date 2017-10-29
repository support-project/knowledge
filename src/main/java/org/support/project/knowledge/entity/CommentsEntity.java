package org.support.project.knowledge.entity;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.entity.gen.GenCommentsEntity;

/**
 * コメント
 */
@DI(instance = Instance.Prototype)
public class CommentsEntity extends GenCommentsEntity {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /** 登録ユーザ名 */
    private String insertUserName;
    /** 更新ユーザ名 */
    private String updateUserName;
    
    private long likeCount = 0;

    public boolean isUpdate() {
        if (getInsertDatetime().getTime() != getUpdateDatetime().getTime()) {
            return true;
        }
        return false;
    }

    /**
     * インスタンス取得 AOPに対応
     * 
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
     * 
     * @param commentNo コメント番号
     */

    public CommentsEntity(Long commentNo) {
        super(commentNo);
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

    /**
     * @return the insertUserName
     */
    public String getInsertUserName() {
        return insertUserName;
    }

    /**
     * @param insertUserName the insertUserName to set
     */
    public void setInsertUserName(String insertUserName) {
        this.insertUserName = insertUserName;
    }

    /**
     * @return the likeCount
     */
    public long getLikeCount() {
        return likeCount;
    }

    /**
     * @param likeCount the likeCount to set
     */
    public void setLikeCount(long likeCount) {
        this.likeCount = likeCount;
    }

}
