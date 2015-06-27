package org.support.project.knowledge.control.open;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;

import org.owasp.validator.html.PolicyException;
import org.owasp.validator.html.ScanException;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.StringUtils;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.control.KnowledgeControlBase;
import org.support.project.knowledge.dao.CommentsDao;
import org.support.project.knowledge.dao.KnowledgeHistoriesDao;
import org.support.project.knowledge.dao.LikesDao;
import org.support.project.knowledge.dao.TagsDao;
import org.support.project.knowledge.entity.CommentsEntity;
import org.support.project.knowledge.entity.KnowledgeHistoriesEntity;
import org.support.project.knowledge.entity.KnowledgesEntity;
import org.support.project.knowledge.entity.LikesEntity;
import org.support.project.knowledge.entity.TagsEntity;
import org.support.project.knowledge.logic.DiffLogic;
import org.support.project.knowledge.logic.KnowledgeLogic;
import org.support.project.knowledge.logic.TagLogic;
import org.support.project.knowledge.logic.TargetLogic;
import org.support.project.knowledge.logic.UploadedFileLogic;
import org.support.project.knowledge.vo.LikeCount;
import org.support.project.knowledge.vo.UploadFile;
import org.support.project.web.bean.LabelValue;
import org.support.project.web.bean.LoginedUser;
import org.support.project.web.boundary.Boundary;
import org.support.project.web.common.HttpStatus;
import org.support.project.web.control.service.Get;
import org.support.project.web.control.service.Post;
import org.support.project.web.dao.UsersDao;
import org.support.project.web.entity.UsersEntity;
import org.support.project.web.exception.InvalidParamException;

@DI(instance=Instance.Prototype)
public class KnowledgeControl extends KnowledgeControlBase {
	private static final int COOKIE_COUNT = 5;

	/** ログ */
	private static Log LOG = LogFactory.getLog(KnowledgeControl.class);
	
	public static final int PAGE_LIMIT = 10;
	public static final int COOKIE_AGE = 60 * 60 * 24 * 31;
	
	/**
	 * ナレッジを表示
	 * @return
	 * @throws InvalidParamException 
	 */
	@Get
	public Boundary view() throws InvalidParamException {
		// 共通処理呼の表示条件の保持の呼び出し
		setViewParam();
		
		Long knowledgeId = super.getPathLong(Long.valueOf(-1));
		
		KnowledgeLogic knowledgeLogic = KnowledgeLogic.get();
		
		// 今見たナレッジの情報をCookieに保存
		// TODO セッションのデータを操作
		List<String> ids = new ArrayList<String>();
		ids.add(String.valueOf(knowledgeId));
		Cookie[] cookies = getRequest().getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("KNOWLEDGE_HISTORY")) {
					String history = cookie.getValue();
					if (history.indexOf(",") != -1) {
						String[] historyIds = history.split(",");
						//for (int i = historyIds.length - 1; i >= 0; i--) {
						for (int i = 0; i < historyIds.length; i++) {
							if (!ids.contains(historyIds[i])) {
								ids.add(historyIds[i]);
							}
							if (ids.size() >= COOKIE_COUNT) {
								break;
							}
						}
					} else {
						if (!ids.contains(history)) {
							ids.add(history);
						}
					}
				}
			}
			String cookieHistory = String.join(",", ids);
			Cookie cookie = new  Cookie("KNOWLEDGE_HISTORY", cookieHistory);
			cookie.setPath(getRequest().getContextPath());
			cookie.setMaxAge(COOKIE_AGE);
			getResponse().addCookie(cookie);
		}
		
		KnowledgesEntity entity = knowledgeLogic.selectWithTags(knowledgeId, getLoginedUser());
		if (entity == null) {
			return sendError(HttpStatus.SC_404_NOT_FOUND, "NOT FOUND");
		}
		setAttributeOnProperty(entity);
		
		String offset = super.getParam("offset", String.class);
		if (StringUtils.isEmpty(offset)) {
			offset = "0";
		}
		setAttribute("offset", offset);
		
		// ナレッジに紐づく添付ファイルを取得
		UploadedFileLogic fileLogic = UploadedFileLogic.get();
		List<UploadFile> files = fileLogic.selectOnKnowledgeId(knowledgeId, getRequest().getContextPath());
		setAttribute("files", files);
		
		// 閲覧履歴を追加
		knowledgeLogic.addViewHistory(knowledgeId, getLoginedUser());
		
		// いいね！の件数取得
		LikesDao likesDao = LikesDao.get();
		Long count = likesDao.countOnKnowledgeId(knowledgeId);
		setAttribute("like_count", count);
		
		// コメント取得
		CommentsDao commentsDao = CommentsDao.get();
		List<CommentsEntity> comments = commentsDao.selectOnKnowledgeId(knowledgeId);
		setAttribute("comments", comments);
		
		// 表示するグループを取得
		//List<GroupsEntity> groups = GroupLogic.get().selectGroupsOnKnowledgeId(knowledgeId);
		List<LabelValue> groups = TargetLogic.get().selectTargetsOnKnowledgeId(knowledgeId);
		setAttribute("groups", groups);
		
		// 編集権限
		List<LabelValue> editors = TargetLogic.get().selectEditorsOnKnowledgeId(knowledgeId);
		setAttribute("editors", editors);
		LoginedUser loginedUser = super.getLoginedUser();
		boolean edit = knowledgeLogic.isEditor(loginedUser, entity, editors);
		setAttribute("edit", edit);
		
		return forward("view.jsp");
	}
	
	/**
	 * リストを表示
	 * @return
	 * @throws Exception 
	 */
	@Get
	public Boundary list() throws Exception {
		LOG.trace("Call list");
		Integer offset = super.getPathInteger(0);
		setAttribute("offset", offset);
		// 共通処理呼の表示条件の保持の呼び出し
		setViewParam();
		
		TagsDao tagsDao = TagsDao.get();
		KnowledgeLogic knowledgeLogic = KnowledgeLogic.get();
		
		LoginedUser loginedUser = super.getLoginedUser();
		String keyword = getParam("keyword");
		String tag = getParam("tag");
		String user = getParam("user");
		List<KnowledgesEntity> knowledges = new ArrayList<>();
		if (StringUtils.isInteger(tag)) {
			//タグを選択している
			LOG.trace("show on Tag");
			knowledges.addAll(knowledgeLogic.showKnowledgeOnTag(tag, loginedUser, offset * PAGE_LIMIT, PAGE_LIMIT));
			TagsEntity tagsEntity = tagsDao.selectOnKey(new Integer(tag));
			setAttribute("selectedTag", tagsEntity);
		} else if (StringUtils.isNotEmpty(user) && StringUtils.isInteger(user)) {
			// ユーザを選択している
			LOG.trace("show on User");
			int userId = Integer.parseInt(user);
			knowledges.addAll(knowledgeLogic.showKnowledgeOnUser(userId, loginedUser, offset * PAGE_LIMIT, PAGE_LIMIT));
			UsersEntity usersEntity = UsersDao.get().selectOnKey(userId);
			usersEntity.setPassword("");
			setAttribute("selectedUser", usersEntity);
		} else {
			// その他(キーワード検索)
			LOG.trace("search");
			knowledges.addAll(knowledgeLogic.searchKnowledge(keyword, loginedUser, offset * PAGE_LIMIT, PAGE_LIMIT));
		}
		setAttribute("knowledges", knowledges);
		LOG.trace("検索終了");
		
		int previous = offset -1;
		if (previous < 0) {
			previous = 0;
		}
		
		// タグの情報を取得
		if (super.getLoginedUser() != null && super.getLoginedUser().isAdmin()) {
			// 管理者であれば、ナレッジの件数は、参照権限を考慮していない
	 		List<TagsEntity> tags = tagsDao.selectTagsWithCount(0, PAGE_LIMIT);
			setAttribute("tags", tags);
		} else {
			TagLogic tagLogic = TagLogic.get();
			List<TagsEntity> tags = tagLogic.selectTagsWithCount(loginedUser, 0, PAGE_LIMIT);
			setAttribute("tags", tags);
		}
		LOG.trace("タグ取得完了");

		
		// History表示
		// TODO 履歴表示を毎回取得するのはイマイチ。いったんセッションに保存しておくのが良いかも
		Cookie[] cookies = getRequest().getCookies();
		List<String> historyIds = new ArrayList<>();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("KNOWLEDGE_HISTORY")) {
					String history = cookie.getValue();
					LOG.debug("history: " + history);
					if (history.indexOf(",") != -1) {
						String[] splits = history.split(",");
						for (String string : splits) {
							historyIds.add(string);
						}
					} else {
						historyIds.add(history);
					}
				}
			}
		}
		List<KnowledgesEntity> histories = knowledgeLogic.getKnowledges(historyIds, loginedUser);
		LOG.trace("履歴取得完了");
//		for (KnowledgesEntity knowledgesEntity : histories) {
//			LOG.trace("   文字数チェック");
//			knowledgesEntity.setContent(org.apache.commons.lang.StringUtils.abbreviate(
//					knowledgesEntity.getContent(), 40));
//			LOG.trace("   文字数チェック終了");
//			LOG.trace("   Samy");
//			knowledgesEntity.setContent(doSamy(knowledgesEntity.getContent()));
//			LOG.trace("   Samy終了");
//		}
		setAttribute("histories", histories);
		LOG.trace("履歴表示修正");
		
		setAttribute("offset", offset);
		setAttribute("previous", previous);
		setAttribute("next", offset + 1);
		return forward("list.jsp");
	}
	
	/**
	 * いいねを押下
	 * @return
	 * @throws InvalidParamException 
	 */
	@Get
	public Boundary like() throws InvalidParamException {
		Long knowledgeId = super.getPathLong(Long.valueOf(-1));
		KnowledgeLogic knowledgeLogic = KnowledgeLogic.get();
		Long count = knowledgeLogic.addLike(knowledgeId, getLoginedUser());
		LikeCount likeCount = new LikeCount();
		likeCount.setKnowledgeId(knowledgeId);
		likeCount.setCount(count);
		return send(likeCount);
	}
	
	
	
	/**
	 * タイトルとコンテンツの危険なタグをエスケープした結果を返す
	 * (KnowledgesEntityのgetXXXで実施しているので、entityにセットしてJSONで返すだけで良い)
	 * TODO クライアント側で出来ないか？
	 * @param entity
	 * @return
	 * @throws ScanException 
	 * @throws PolicyException 
	 */
	@Post
	public Boundary escape(KnowledgesEntity entity) throws PolicyException, ScanException {
		super.setSendEscapeHtml(false);
		entity.setTitle(doSamy(entity.getTitle()));
		entity.setContent(doSamy(entity.getContent()));
		return super.send(entity);
	}
	
	/**
	 * 検索画面を表示
	 * @return
	 */
	@Get
	public Boundary search() {
		// 共通処理呼の表示条件の保持の呼び出し
		setViewParam();
		return forward("search.jsp");
	}
	
	/**
	 * いいねを押したユーザを一覧表示
	 * @return
	 * @throws InvalidParamException 
	 */
	@Get
	public Boundary likes() throws InvalidParamException {
		// 共通処理呼の表示条件の保持の呼び出し
		setViewParam();
		
		Long knowledgeId = super.getPathLong(Long.valueOf(-1));
		setAttribute("knowledgeId", knowledgeId);
		// 権限チェック(いったんアクセスできるユーザは全て表示) TODO 登録者のみにする？
		KnowledgeLogic knowledgeLogic = KnowledgeLogic.get();
		KnowledgesEntity entity = knowledgeLogic.select(knowledgeId, getLoginedUser());
		if (entity == null) {
			return sendError(HttpStatus.SC_404_NOT_FOUND, "NOT FOUND");
		}
		
		Integer page = 0;
		String p = getParamWithDefault("page", "");
		if (StringUtils.isInteger(p)) {
			page = Integer.parseInt(p);
		}
		
		LikesDao likesDao = LikesDao.get();
		List<LikesEntity> likes = likesDao.selectOnKnowledge(knowledgeId, page * PAGE_LIMIT, PAGE_LIMIT);
		setAttribute("likes", likes);
		
		
		int previous = page -1;
		if (previous < 0) {
			previous = 0;
		}
		setAttribute("page", page);
		setAttribute("previous", previous);
		setAttribute("next", page + 1);
		
		return forward("likes.jsp");
	}
	
	@Get
	public Boundary histories() throws InvalidParamException {
		// 共通処理呼の表示条件の保持の呼び出し
		setViewParam();
		
		Long knowledgeId = super.getPathLong(Long.valueOf(-1));
		setAttribute("knowledgeId", knowledgeId);
		// 権限チェック(いったんアクセスできるユーザは全て表示)
		KnowledgeLogic knowledgeLogic = KnowledgeLogic.get();
		KnowledgesEntity entity = knowledgeLogic.select(knowledgeId, getLoginedUser());
		if (entity == null) {
			return sendError(HttpStatus.SC_404_NOT_FOUND, "NOT FOUND");
		}
		
		Integer page = 0;
		String p = getParamWithDefault("page", "");
		if (StringUtils.isInteger(p)) {
			page = Integer.parseInt(p);
		}
		int previous = page -1;
		if (previous < 0) {
			previous = 0;
		}
		setAttribute("page", page);
		setAttribute("previous", previous);
		setAttribute("next", page + 1);
		
		// 履歴を取得
		KnowledgeHistoriesDao historiesDao = KnowledgeHistoriesDao.get();
		List<KnowledgeHistoriesEntity> histories = historiesDao.selectOnKnowledge(knowledgeId, page * PAGE_LIMIT, PAGE_LIMIT);
		setAttribute("histories", histories);

		return forward("histories.jsp");
	}
	
	
	@Get
	public Boundary history() throws InvalidParamException {
		// 共通処理呼の表示条件の保持の呼び出し
		setViewParam();
		
		Long knowledgeId = super.getPathLong(Long.valueOf(-1));
		setAttribute("knowledgeId", knowledgeId);
		// 権限チェック(いったんアクセスできるユーザは全て表示)
		KnowledgeLogic knowledgeLogic = KnowledgeLogic.get();
		KnowledgesEntity entity = knowledgeLogic.select(knowledgeId, getLoginedUser());
		if (entity == null) {
			return sendError(HttpStatus.SC_404_NOT_FOUND, "NOT FOUND");
		}
		
		Integer page = 0;
		String p = getParamWithDefault("page", "");
		if (StringUtils.isInteger(p)) {
			page = Integer.parseInt(p);
		}
		setAttribute("page", page);
		
		Integer historyNo = 0;
		String h = getParamWithDefault("history_no", "");
		if (StringUtils.isInteger(h)) {
			historyNo = Integer.parseInt(h);
		}
		
		KnowledgeHistoriesDao historiesDao = KnowledgeHistoriesDao.get();
		KnowledgeHistoriesEntity history = historiesDao.selectOnKeyWithName(historyNo, knowledgeId);
		setAttribute("history", history);
		setAttribute("now", entity);
		
		List<String> changes = DiffLogic.get().diff(history.getContent(), entity.getContent());
		setAttribute("changes", changes);
		
		return forward("history.jsp");
	}
	
}


