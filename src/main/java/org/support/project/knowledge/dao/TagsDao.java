package org.support.project.knowledge.dao;

import java.util.List;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.dao.gen.GenTagsDao;
import org.support.project.knowledge.entity.TagsEntity;
import org.support.project.ormapping.common.SQLManager;

/**
 * タグ
 */
@DI(instance=Instance.Singleton)
public class TagsDao extends GenTagsDao {

	/** SerialVersion */
	private static final long serialVersionUID = 1L;
	/**
	 * インスタンス取得
	 * AOPに対応
	 * @return インスタンス
	 */
	public static TagsDao get() {
		return Container.getComp(TagsDao.class);
	}


	/**
	 * ID 
	 */
	private int currentId = 0;

	/**
	 * IDを採番 
	 * ※コミットしなくても次のIDを採番する為、保存しなければ欠番になる 
	 */
	public Integer getNextId() {
		String sql = "SELECT MAX(TAG_ID) FROM TAGS;";
		Integer integer = executeQuerySingle(sql, Integer.class);
		if (integer != null) {
			if (currentId < integer) {
				currentId = integer;
			}
		}
		currentId++;
		return currentId;
	}

	/**
	 * タグを名称で取得
	 * @param tag
	 * @return
	 */
	public TagsEntity selectOnTagName(String tag) {
		String sql = "SELECT * FROM TAGS WHERE TAG_NAME = ?";
		return executeQuerySingle(sql, TagsEntity.class, tag);
	}

	/**
	 * 指定のナレッジが持つタグを取得
	 * @param knowledgeId
	 * @return
	 */
	public List<TagsEntity> selectOnKnowledgeId(Long knowledgeId) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/TagsDao/TagsDao_selectOnKnowledgeId.sql");
		return executeQuery(sql, TagsEntity.class, knowledgeId);
	}
	
	
	/**
	 * タグの一覧を取得
	 * 同時にカウント取得
	 * アクセス権を考慮していない
	 * @param offset
	 * @param limit
	 * @return
	 */
	public List<TagsEntity> selectTagsWithCount(int offset, int limit) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/TagsDao/TagsDao_selectTagsWithCount.sql");
		return executeQuery(sql, TagsEntity.class, limit, offset);
	}

	/**
	 * タグの一覧を取得
	 * 同時にカウント取得
	 * ユーザIDがアクセス権に登録されている、もしくは公開のもののみカウント対象
	 * @param userid
	 * @param offset
	 * @param limit
	 * @return
	 */
	public List<TagsEntity> selectTagsWithCountOnUser(int userid, int offset, int limit) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/TagsDao/TagsDao_selectTagsWithCountOnUser.sql");
		return executeQuery(sql, TagsEntity.class, userid, limit, offset);
	}

}
