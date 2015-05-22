package org.support.project.knowledge.dao;

import java.util.ArrayList;
import java.util.List;

import org.support.project.common.util.StringJoinBuilder;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.dao.gen.GenTagsDao;
import org.support.project.knowledge.entity.TagsEntity;
import org.support.project.ormapping.common.SQLManager;
import org.support.project.web.entity.GroupsEntity;

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
		return executeQueryList(sql, TagsEntity.class, knowledgeId);
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
		return executeQueryList(sql, TagsEntity.class, limit, offset);
	}

	/**
	 * タグの一覧を取得
	 * 同時にカウント取得
	 * ユーザIDがアクセス権に登録されている、もしくは公開のもののみカウント対象
	 * @param userid
	 * @param offset
	 * @param limit
	 * @return
	 * @deprecated
	 */
	public List<TagsEntity> selectTagsWithCountOnUser(int userid, int offset, int limit) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/TagsDao/TagsDao_selectTagsWithCountOnUser.sql");
		return executeQueryList(sql, TagsEntity.class, userid, limit, offset);
	}
	
	/**
	 * タグの一覧と、それに紐づくナレッジの件数を取得
	 * @param groups 
	 * @param offset
	 * @param loginedUser
	 * @return
	 */
	public List<TagsEntity> selectWithKnowledgeCount(int userId, List<GroupsEntity> groups, int offset, int limit) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/TagsDao/TagsDao_selectWithKnowledgeCount.sql");
		StringJoinBuilder builder = new StringJoinBuilder();
		List<Integer> params = new ArrayList<>();
		params.add(new Integer(userId));
		
		builder.append("?");
		params.add(-1);
		for (GroupsEntity group : groups) {
			builder.append("?");
			params.add(group.getGroupId());
		}
		params.add(new Integer(limit));
		params.add(new Integer(offset));
		sql = sql.replace("${groups}", builder.join(", "));
		
		return executeQueryList(sql, TagsEntity.class, params.toArray(new Integer[0]));
	}
	/**
	 * タグの一覧と、それに紐づくナレッジの件数を取得
	 * 管理者用で、ナレッジにアクセス可能かのアクセス権限チェックはしない
	 * @param offset
	 * @param limit
	 * @return
	 */
	public List<TagsEntity> selectWithKnowledgeCountAdmin(int offset, int limit) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/TagsDao/TagsDao_selectWithKnowledgeCountAdmin.sql");
		return executeQueryList(sql, TagsEntity.class, limit, offset);
	}

}
