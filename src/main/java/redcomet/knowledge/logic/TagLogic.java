package redcomet.knowledge.logic;

import java.util.List;

import redcomet.common.log.Log;
import redcomet.common.log.LogFactory;
import redcomet.di.Container;
import redcomet.knowledge.dao.TagsDao;
import redcomet.knowledge.entity.TagsEntity;
import redcomet.web.bean.LoginedUser;

public class TagLogic {
	/** ログ */
	private static Log LOG = LogFactory.getLog(TagLogic.class);

	public static TagLogic get() {
		return Container.getComp(TagLogic.class);
	}
	
	private TagsDao tagsDao = TagsDao.get();
	
	/**
	 * タグの一覧を取得
	 * 同時に、ナレッジの件数も取得する
	 * 件数は、ログインユーザがアクセス権があるもの
	 * @param loginedUser
	 * @param i
	 * @param pageLimit
	 * @return
	 */
	public List<TagsEntity> selectTagsWithCount(LoginedUser loginedUser, int offset, int limit) {
		int userid = Integer.MIN_VALUE;
		if (loginedUser != null) {
			userid = loginedUser.getUserId();
		}
		return tagsDao.selectTagsWithCountOnUser(userid, offset, limit);
	}
	
	
	
	
	
}
