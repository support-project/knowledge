package org.support.project.knowledge.entity;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.entity.gen.GenVotesEntity;

/**
 * 投票
 */
@DI(instance = Instance.Prototype)
public class VotesEntity extends GenVotesEntity {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * インスタンス取得 AOPに対応
     * 
     * @return インスタンス
     */
    public static VotesEntity get() {
        return Container.getComp(VotesEntity.class);
    }

    /**
     * コンストラクタ
     */
    public VotesEntity() {
        super();
    }

    /**
     * コンストラクタ
     * 
     * @param voteNo VOTE_NO
     */

    public VotesEntity(Long voteNo) {
        super(voteNo);
    }

}
