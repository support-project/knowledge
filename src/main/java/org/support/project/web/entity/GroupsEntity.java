package org.support.project.web.entity;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.web.entity.gen.GenGroupsEntity;

/**
 * グループ
 */
@DI(instance = Instance.Prototype)
public class GroupsEntity extends GenGroupsEntity {
    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * 編集可能かどうか
     */
    private boolean editAble = false;

    /**
     * 所属状態
     */
    private int status = 0;

    /**
     * グループのナレッジ数
     */
    private int groupKnowledgeCount = 0;

    /**
     * ナレッジID
     */
    private Long knowledgeId = 0L;

    /**
     * インスタンス取得 AOPに対応
     * 
     * @return インスタンス
     */
    public static GroupsEntity get() {
        return Container.getComp(GroupsEntity.class);
    }

    /**
     * コンストラクタ
     */
    public GroupsEntity() {
        super();
    }

    /**
     * コンストラクタ
     * 
     * @param groupId グループID CHARACTER SET latin1
     */
    public GroupsEntity(Integer groupId) {
        super(groupId);
    }

    /**
     * @return the editAble
     */
    public boolean isEditAble() {
        return editAble;
    }

    /**
     * @param editAble the editAble to set
     */
    public void setEditAble(boolean editAble) {
        this.editAble = editAble;
    }

    /**
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * @return the groupKnowledgeCount
     */
    public int getGroupKnowledgeCount() {
        return this.groupKnowledgeCount;
    }

    /**
     * @param groupKnowledgeCount the groupKnowledgeCount to set
     */
    public void setGroupKnowledgeCount(int groupKnowledgeCount) {
        this.groupKnowledgeCount = groupKnowledgeCount;
    }

    /**
     * @return the knowledgeId
     */
    public Long getKnowledgeId() {
        return this.knowledgeId;
    }

    /**
     * @param knowledgeId the knowledgeId to set
     */
    public void setKnowledgeId(Long knowledgeId) {
        this.knowledgeId = knowledgeId;
    }
}
