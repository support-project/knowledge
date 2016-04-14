package org.support.project.knowledge.entity;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.entity.gen.GenKnowledgeFilesEntity;

/**
 * 添付ファイル
 */
@DI(instance = Instance.Prototype)
public class KnowledgeFilesEntity extends GenKnowledgeFilesEntity {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * インスタンス取得 AOPに対応
     * 
     * @return インスタンス
     */
    public static KnowledgeFilesEntity get() {
        return Container.getComp(KnowledgeFilesEntity.class);
    }

    /**
     * コンストラクタ
     */
    public KnowledgeFilesEntity() {
        super();
    }

    /**
     * コンストラクタ
     * 
     * @param fileNo 添付ファイル番号
     */

    public KnowledgeFilesEntity(Long fileNo) {
        super(fileNo);
    }

}
