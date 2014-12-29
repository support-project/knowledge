package redcomet.knowledge.entity;

import redcomet.knowledge.entity.gen.GenKnowledgeFilesEntity;

import redcomet.di.Container;
import redcomet.di.DI;
import redcomet.di.Instance;

import java.io.InputStream;
import java.sql.Timestamp;


/**
 * 添付ファイル
 */
@DI(instance=Instance.Prototype)
public class KnowledgeFilesEntity extends GenKnowledgeFilesEntity {

	/** SerialVersion */
	private static final long serialVersionUID = 1L;

	/**
	 * インスタンス取得
	 * AOPに対応
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
	 * @param fileNo 添付ファイル番号
	 */

	public KnowledgeFilesEntity(Long fileNo) {
		super(fileNo);
	}

}
