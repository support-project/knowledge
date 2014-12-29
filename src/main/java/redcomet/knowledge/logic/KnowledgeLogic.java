package redcomet.knowledge.logic;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import redcomet.aop.Aspect;
import redcomet.common.log.Log;
import redcomet.common.log.LogFactory;
import redcomet.common.util.PropertyUtil;
import redcomet.common.util.StringUtils;
import redcomet.di.Container;
import redcomet.knowledge.bat.FileParseBat;
import redcomet.knowledge.config.IndexType;
import redcomet.knowledge.dao.KnowledgeFilesDao;
import redcomet.knowledge.dao.KnowledgeTagsDao;
import redcomet.knowledge.dao.KnowledgeUsersDao;
import redcomet.knowledge.dao.KnowledgesDao;
import redcomet.knowledge.dao.LikesDao;
import redcomet.knowledge.dao.TagsDao;
import redcomet.knowledge.dao.ViewHistoriesDao;
import redcomet.knowledge.entity.KnowledgeFilesEntity;
import redcomet.knowledge.entity.KnowledgeTagsEntity;
import redcomet.knowledge.entity.KnowledgeUsersEntity;
import redcomet.knowledge.entity.KnowledgesEntity;
import redcomet.knowledge.entity.LikesEntity;
import redcomet.knowledge.entity.TagsEntity;
import redcomet.knowledge.entity.ViewHistoriesEntity;
import redcomet.knowledge.indexer.IndexingValue;
import redcomet.knowledge.searcher.SearchResultValue;
import redcomet.knowledge.searcher.SearchingValue;
import redcomet.knowledge.searcher.impl.LuceneSearcher;
import redcomet.web.bean.LoginedUser;

public class KnowledgeLogic {
	/** ログ */
	private static Log LOG = LogFactory.getLog(KnowledgeLogic.class);

	public static final int ALL_USER = 0;
	
	public static final int PUBLIC_FLAG_PUBLIC = 0;
	public static final int PUBLIC_FLAG_PRIVATE = 1;
	public static final int PUBLIC_FLAG_SELECT_USERS = 2;
	
	public static final int TYPE_KNOWLEDGE = IndexType.Knoeledge.getValue();
	public static final int TYPE_FILE = IndexType.KnowledgeFile.getValue();

	public static KnowledgeLogic get() {
		return Container.getComp(KnowledgeLogic.class);
	}
	
	private KnowledgesDao knowledgesDao = Container.getComp(KnowledgesDao.class);
	private KnowledgeUsersDao knowledgeUsersDao = KnowledgeUsersDao.get();
	private TagsDao tagsDao = TagsDao.get();
	private KnowledgeTagsDao knowledgeTagsDao = KnowledgeTagsDao.get();
	private UploadedFileLogic fileLogic = UploadedFileLogic.get();
	
	/**
	 * タグの文字列（カンマ区切り）から、登録済のタグであれば、それを取得し、
	 * 存在しないものであれば、新たにタグを生成してタグの情報を取得
	 * @param tags
	 * @return
	 */
	@Aspect(advice=redcomet.ormapping.transaction.Transaction.class)
	public List<TagsEntity> manegeTags(String tags) {
		List<TagsEntity> tagList = new ArrayList<>();
		if (StringUtils.isEmpty(tags)) {
			return tagList;
		}
		String[] splits;
		if (tags.indexOf(",") != -1) {
			splits = tags.split(",");
		} else {
			splits = new String[1];
			splits[0] = tags;
		}
		
		for (String tag : splits) {
			TagsEntity tagsEntity = tagsDao.selectOnTagName(tag);
			if (tagsEntity == null) {
				tagsEntity = new TagsEntity();
				tagsEntity.setTagName(tag);
				tagsEntity = tagsDao.insert(tagsEntity);
				LOG.debug("Tag added." + PropertyUtil.reflectionToString(tagsEntity));
			}
			tagList.add(tagsEntity);
		}
		return tagList;
	}

	/**
	 * ナレッジを登録
	 * @param entity
	 * @param tags 
	 * @param fileNos 
	 * @param loginedUser
	 * @return
	 * @throws Exception
	 */
	@Aspect(advice=redcomet.ormapping.transaction.Transaction.class)
	public KnowledgesEntity insert(KnowledgesEntity entity, List<TagsEntity> tags, List<Long> fileNos, LoginedUser loginedUser) throws Exception {
		// ナレッジを登録
		entity = knowledgesDao.insert(entity);
		// アクセス権を登録
		saveAccessUser(entity, loginedUser);
		// タグを登録
		setTags(entity, tags);
		
		// 添付ファイルを更新（紐付けをセット）
		fileLogic.setKnowledgeFiles(entity, fileNos, loginedUser);
		
		// 全文検索エンジンへ登録
		saveIndex(entity, tags, loginedUser);
		return entity;
	}
	
	/**
	 * ナレッジを更新
	 * @param entity
	 * @param fileNos 
	 * @param loginedUser
	 * @return
	 * @throws Exception 
	 */
	@Aspect(advice=redcomet.ormapping.transaction.Transaction.class)
	public KnowledgesEntity update(KnowledgesEntity entity, List<TagsEntity> tags, List<Long> fileNos, LoginedUser loginedUser) throws Exception {
		// ナレッッジを更新
		entity = knowledgesDao.update(entity);
		// アクセス権を登録
		knowledgeUsersDao.deleteOnKnowledgeId(entity.getKnowledgeId());
		saveAccessUser(entity, loginedUser);
		
		// タグを登録
		knowledgeTagsDao.deleteOnKnowledgeId(entity.getKnowledgeId());
		setTags(entity, tags);
		
		// 添付ファイルを更新（紐付けをセット）
		fileLogic.setKnowledgeFiles(entity, fileNos, loginedUser);
		
		// 全文検索エンジンへ登録
		saveIndex(entity, tags, loginedUser);
		return entity;
	}
	
	
	
	/**
	 * タグを登録
	 * @param entity
	 * @param tags
	 */
	private void setTags(KnowledgesEntity entity, List<TagsEntity> tags) {
		if (tags != null) {
			for (TagsEntity tagsEntity : tags) {
				KnowledgeTagsEntity knowledgeTagsEntity = new KnowledgeTagsEntity(entity.getKnowledgeId(), tagsEntity.getTagId());
				knowledgeTagsDao.insert(knowledgeTagsEntity);
			}
		}
	}

	
	/**
	 * アクセス権を登録
	 * @param entity
	 * @param loginedUser
	 */
	private void saveAccessUser(KnowledgesEntity entity, LoginedUser loginedUser) {
		// ナレッジにアクセス可能なユーザに、自分自身をセット
		KnowledgeUsersEntity knowledgeUsersEntity = new KnowledgeUsersEntity();
		knowledgeUsersEntity.setKnowledgeId(entity.getKnowledgeId());
		knowledgeUsersEntity.setUserId(loginedUser.getLoginUser().getUserId());
		knowledgeUsersDao.insert(knowledgeUsersEntity);
		if (entity.getPublicFlag() == null || PUBLIC_FLAG_PUBLIC == entity.getPublicFlag()) {
			// 全て公開する情報
			knowledgeUsersEntity = new KnowledgeUsersEntity();
			knowledgeUsersEntity.setKnowledgeId(entity.getKnowledgeId());
			knowledgeUsersEntity.setUserId(ALL_USER);
			knowledgeUsersDao.insert(knowledgeUsersEntity);
		}
	}
	
	/**
	 * 全文検索エンジンへ保存
	 * @param entity
	 * @param tags 
	 * @param loginedUser
	 * @throws Exception
	 */
	private void saveIndex(KnowledgesEntity entity, List<TagsEntity> tags, LoginedUser loginedUser) throws Exception {
		IndexingValue indexingValue = new IndexingValue();
		indexingValue.setType(TYPE_KNOWLEDGE);
		indexingValue.setId(String.valueOf(entity.getKnowledgeId()));
		indexingValue.setTitle(entity.getTitle());
		indexingValue.setContents(entity.getContent());
		indexingValue.addUser(loginedUser.getLoginUser().getUserId());
		if (entity.getPublicFlag() == null || PUBLIC_FLAG_PUBLIC == entity.getPublicFlag()) {
			indexingValue.addUser(ALL_USER);
		}
		if (tags != null) {
			for (TagsEntity tagsEntity : tags) {
				indexingValue.addTag(tagsEntity.getTagId());
			}
		}
		indexingValue.setCreator(loginedUser.getUserId());
		indexingValue.setTime(entity.getUpdateDatetime().getTime()); // 更新日時をセットするので、更新日時でソート
		
		IndexLogic.get().save(indexingValue); //全文検索のエンジンにも保存（DBに保存する意味ないかも）
	}

	/**
	 * ナレッジの検索
	 * @param keyword
	 * @param loginedUser
	 * @param offset
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	public List<KnowledgesEntity> searchKnowledge(String keyword, LoginedUser loginedUser, Integer offset, Integer limit) throws Exception {
		SearchingValue searchingValue = new SearchingValue();
		searchingValue.setKeyword(keyword);
		searchingValue.setOffset(offset);
		searchingValue.setLimit(limit);
		
		if (loginedUser != null && loginedUser.isAdmin()) {
			// 管理者の場合はユーザのアクセス権を考慮しない
			if (StringUtils.isEmpty(keyword)) {
				//キーワードが指定されていなければDBから直接取得
				// TODO 登録ユーザ名
				List<KnowledgesEntity> list = knowledgesDao.selectKnowledge(offset, limit, loginedUser.getUserId());
				for (KnowledgesEntity entity : list) {
					setTags(entity);
					setLikeCount(entity);
				}
				return list;
			}
		} else {
			searchingValue.addUser(ALL_USER);
			Integer userId = null;
			if (loginedUser != null) {
				userId = loginedUser.getLoginUser().getUserId();
				searchingValue.addUser(userId);
			}
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("search params：" + PropertyUtil.reflectionToString(searchingValue));
		}
		List<SearchResultValue> list = IndexLogic.get().search(searchingValue);
		
		return getKnowledgeDatas(list);
	}
	
	/**
	 * ナレッジをタグ指定で表示
	 * @param keyword
	 * @param loginedUser
	 * @param offset
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	public List<KnowledgesEntity> showKnowledgeOnTag(String tag, LoginedUser loginedUser, Integer offset, Integer limit) throws Exception {
		SearchingValue searchingValue = new SearchingValue();
		searchingValue.setOffset(offset);
		searchingValue.setLimit(limit);
		
		if (loginedUser != null && loginedUser.isAdmin()) {
			//管理者の場合、ユーザのアクセス権を考慮しない
			
		} else {
			searchingValue.addUser(ALL_USER);
			Integer userId = null;
			if (loginedUser != null) {
				userId = loginedUser.getLoginUser().getUserId();
				searchingValue.addUser(userId);
			}
		}
		
		if (StringUtils.isInteger(tag)) {
			searchingValue.addTag(new Integer(tag));
		}
		
		if (LOG.isDebugEnabled()) {
			LOG.debug("search params：" + PropertyUtil.reflectionToString(searchingValue));
		}
		List<SearchResultValue> list = IndexLogic.get().search(searchingValue);
		
		return getKnowledgeDatas(list);
	}
	
	/**
	 * 全文検索エンジンの結果を元に、DBからデータを取得し、
	 * さらにアクセス権のチェックなどを行う
	 * @param list
	 * @return
	 */
	private List<KnowledgesEntity> getKnowledgeDatas(List<SearchResultValue> list) {
		KnowledgeFilesDao filesDao = KnowledgeFilesDao.get();
		List<Long> knowledgeIds = new ArrayList<Long>();
//		List<Long> fileIds = new ArrayList<>();
		for (SearchResultValue searchResultValue : list) {
			if (searchResultValue.getType() == TYPE_KNOWLEDGE) {
				knowledgeIds.add(new Long(searchResultValue.getId()));
//			} else if (searchResultValue.getType() == TYPE_FILE) {
//				LOG.trace("FILE!!!   " + searchResultValue.getId());
//				String id = searchResultValue.getId().substring(FileParseBat.ID_PREFIX.length());
//				fileIds.add(new Long(id));
			}
		}
		
		List<KnowledgesEntity> dbs = knowledgesDao.selectKnowledges(knowledgeIds);
		Map<Long, KnowledgesEntity> map = new HashMap<Long, KnowledgesEntity>();
		for (KnowledgesEntity knowledgesEntity : dbs) {
			map.put(knowledgesEntity.getKnowledgeId(), knowledgesEntity);
		}
		
		List<KnowledgesEntity> knowledges = new ArrayList<>();
		for (SearchResultValue searchResultValue : list) {
			if (searchResultValue.getType() == TYPE_KNOWLEDGE) {
				Long key = new Long(searchResultValue.getId());
				if (map.containsKey(key)) {
					KnowledgesEntity entity = map.get(key);
					if (StringUtils.isNotEmpty(searchResultValue.getHighlightedTitle())) {
						entity.setTitle(searchResultValue.getHighlightedTitle());
					}
					if (StringUtils.isNotEmpty(searchResultValue.getHighlightedContents())) {
						entity.setContent(searchResultValue.getHighlightedContents());
					} else {
						entity.setContent(org.apache.commons.lang.StringUtils.abbreviate(entity.getContent(), LuceneSearcher.CONTENTS_LIMIT_LENGTH));
					}
					// タグを取得（1件づつ処理するのでパフォーマンス悪いかも？）
					setTags(entity);
					// いいねの回数
					setLikeCount(entity);
					knowledges.add(entity);
				}
			} else if (searchResultValue.getType() == TYPE_FILE) {
				// TODO 1件づつ処理しているので、パフォーマンスが悪いので後で処理を検討
				String id = searchResultValue.getId().substring(FileParseBat.ID_PREFIX.length());
				Long fileNo = new Long(id);
				KnowledgeFilesEntity filesEntity = filesDao.selectOnKeyWithoutBinary(fileNo);
				if (filesEntity.getKnowledgeId() != null) {
					KnowledgesEntity entity = knowledgesDao.selectOnKeyWithUserName(filesEntity.getKnowledgeId());
					entity.setTitle(entity.getTitle());
					
					StringBuilder builder = new StringBuilder();
					builder.append("[添付]");
					
					if (StringUtils.isNotEmpty(searchResultValue.getHighlightedTitle())) {
						builder.append(searchResultValue.getHighlightedTitle());
					} else {
						builder.append(filesEntity.getFileName());
					}
					builder.append("<br/>");
					if (StringUtils.isNotEmpty(searchResultValue.getHighlightedContents())) {
						builder.append(searchResultValue.getHighlightedContents());
					}
					entity.setContent(builder.toString());
					// タグを取得（1件づつ処理するのでパフォーマンス悪いかも？）
					setTags(entity);
					// いいねの回数
					setLikeCount(entity);
					knowledges.add(entity);
				}
			}
		}
		return knowledges;
	}

	private void setLikeCount(KnowledgesEntity entity) {
		LikesDao likesDao = LikesDao.get();
		Long count = likesDao.countOnKnowledgeId(entity.getKnowledgeId());
		entity.setLikeCount(count);
	}

	/**
	 * ナレッジを取得
	 * @param knowledgeId
	 * @param loginedUser
	 * @return
	 */
	public KnowledgesEntity select(Long knowledgeId, LoginedUser loginedUser) {
		KnowledgesDao dao = Container.getComp(KnowledgesDao.class);
		KnowledgesEntity entity = dao.selectOnKeyWithUserName(knowledgeId);
		if (entity == null) {
			return entity;
		}
		//タグをセット
		setTags(entity);
		
		if (entity.getPublicFlag() == null || entity.getPublicFlag().intValue() == PUBLIC_FLAG_PUBLIC) {
			return entity;
		}
		Integer userId = Integer.MIN_VALUE;
		if (loginedUser != null) {
			userId = loginedUser.getLoginUser().getUserId();
		}
		if (entity.getInsertUser().intValue() == userId.intValue()) {
			// 作成者ならば、アクセス可能
			return entity;
		}
		if (loginedUser != null && loginedUser.isAdmin()) {
			// 管理者は全ての情報にアクセス可能
			return entity;
		}
		
		List<KnowledgeUsersEntity> usersEntities = knowledgeUsersDao.selectOnKnowledgeId(entity.getKnowledgeId());
		for (KnowledgeUsersEntity knowledgeUsersEntity : usersEntities) {
			if (knowledgeUsersEntity.getUserId().intValue() == userId.intValue()) {
				// アクセス権限が登録されていれば、取得
				return entity;
			}
		}
		return null;
	}

	/**
	 * ナレッジのタグをセット
	 * @param entity
	 */
	private void setTags(KnowledgesEntity entity) {
		//タグを取得
		List<TagsEntity> tagsEntities = tagsDao.selectOnKnowledgeId(entity.getKnowledgeId());
		int count = 0;
		StringBuilder builder = new StringBuilder();
		for (TagsEntity tagsEntity : tagsEntities) {
			if (count > 0) {
				builder.append(",");
			}
			builder.append(tagsEntity.getTagName());
			count++;
		}
		entity.setTags(builder.toString());
	}

	/**
	 * ナレッジを取得
	 * @param ids
	 * @param loginedUser
	 * @return
	 */
	public List<KnowledgesEntity> getKnowledges(List<String> ids, LoginedUser loginedUser) {
		List<Long> knowledgeIds = new ArrayList<>();
		for (String string : ids) {
			if (StringUtils.isLong(string)) {
				knowledgeIds.add(new Long(string));
			}
		}
		
		//List<KnowledgesEntity> knowledgesEntities = knowledgesDao.selectKnowledges(knowledgeIds);
		//アクセス権を考慮して取得
		List<KnowledgesEntity> knowledgesEntities = new ArrayList<>();
		for (Long integer : knowledgeIds) {
			KnowledgesEntity entity = select(integer, loginedUser);
			if (entity != null) {
				knowledgesEntities.add(entity);
			}
		}
		return knowledgesEntities;
	}

	/**
	 * ナレッジを削除
	 * @param knowledgeId
	 * @param loginedUser 
	 * @throws Exception 
	 */
	@Aspect(advice=redcomet.ormapping.transaction.Transaction.class)
	public void delete(Long knowledgeId, LoginedUser loginedUser) throws Exception {
		//ナレッジ削除(通常のdeleteは、論理削除になる → 管理者は見える)
		knowledgesDao.delete(knowledgeId);
		
		// アクセス権削除
		knowledgeUsersDao.deleteOnKnowledgeId(knowledgeId);
		// タグを削除
		knowledgeTagsDao.deleteOnKnowledgeId(knowledgeId);
		
		// 添付ファイルを削除
		fileLogic.deleteOnKnowledgeId(knowledgeId);
		
		//全文検索エンジンから削除
		IndexLogic indexLogic = IndexLogic.get();
		indexLogic.delete(knowledgeId);
	}
	
	/**
	 * ユーザのナレッジを削除
	 * TODO ものすごく多くのナレッジを登録したユーザの場合、それを全て削除するのは時間がかかるかも？
	 * ただ、非同期で実施して、「そのうち消えます」と表示するのも気持ち悪いと感じられるので、
	 * いったん同期処理で1件づつ消す（効率的な消し方を検討する）
	 * @param loginUserId
	 * @throws Exception 
	 */
	public void deleteOnUser(Integer loginUserId) throws Exception {
		// ユーザのナレッジを取得
		List<Long> knowledgeIds = knowledgesDao.selectOnUser(loginUserId);
		for (Long knowledgeId : knowledgeIds) {
			LOG.warn(knowledgeId);
			// アクセス権削除
			knowledgeUsersDao.deleteOnKnowledgeId(knowledgeId);
			// タグを削除
			knowledgeTagsDao.deleteOnKnowledgeId(knowledgeId);
		}
		// ユーザが登録したナレッジを削除
		knowledgesDao.deleteOnUser(loginUserId);
		
		//全文検索エンジンから削除
		IndexLogic indexLogic = IndexLogic.get();
		indexLogic.deleteOnUser(loginUserId);

	}
	
	/**
	 * 閲覧履歴を保持
	 * @param knowledgeId
	 * @param loginedUser
	 */
	public void addViewHistory(Long knowledgeId, LoginedUser loginedUser) {
		ViewHistoriesDao historiesDao = ViewHistoriesDao.get();
		ViewHistoriesEntity historiesEntity = new ViewHistoriesEntity();
		historiesEntity.setKnowledgeId(knowledgeId);
		historiesEntity.setViewDateTime(new Timestamp(new Date().getTime()));
		if (loginedUser != null) {
			historiesEntity.setInsertUser(loginedUser.getUserId());
		}
		historiesDao.insert(historiesEntity);
	}

	public Long addLike(Long knowledgeId, LoginedUser loginedUser) {
		LikesDao likesDao = LikesDao.get();
		LikesEntity likesEntity = new LikesEntity();
		likesEntity.setKnowledgeId(knowledgeId);
		likesDao.insert(likesEntity);
		
		Long count = likesDao.countOnKnowledgeId(knowledgeId);
		return count;
	}

}
