package org.support.project.knowledge.control.open;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.support.project.common.exception.ParseException;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.DateUtils;
import org.support.project.common.util.StringJoinBuilder;
import org.support.project.common.util.StringUtils;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.config.AppConfig;
import org.support.project.knowledge.config.SystemConfig;
import org.support.project.knowledge.config.UserConfig;
import org.support.project.knowledge.control.KnowledgeControlBase;
import org.support.project.knowledge.dao.CommentsDao;
import org.support.project.knowledge.dao.DraftItemValuesDao;
import org.support.project.knowledge.dao.ExGroupsDao;
import org.support.project.knowledge.dao.ExUsersDao;
import org.support.project.knowledge.dao.KnowledgeHistoriesDao;
import org.support.project.knowledge.dao.KnowledgeItemValuesDao;
import org.support.project.knowledge.dao.KnowledgesDao;
import org.support.project.knowledge.dao.LikeCommentsDao;
import org.support.project.knowledge.dao.LikesDao;
import org.support.project.knowledge.dao.StocksDao;
import org.support.project.knowledge.dao.TagsDao;
import org.support.project.knowledge.dao.TemplateMastersDao;
import org.support.project.knowledge.entity.CommentsEntity;
import org.support.project.knowledge.entity.DraftItemValuesEntity;
import org.support.project.knowledge.entity.KnowledgeHistoriesEntity;
import org.support.project.knowledge.entity.KnowledgeItemValuesEntity;
import org.support.project.knowledge.entity.KnowledgesEntity;
import org.support.project.knowledge.entity.LikeCommentsEntity;
import org.support.project.knowledge.entity.LikesEntity;
import org.support.project.knowledge.entity.StocksEntity;
import org.support.project.knowledge.entity.TagsEntity;
import org.support.project.knowledge.entity.TemplateItemsEntity;
import org.support.project.knowledge.entity.TemplateMastersEntity;
import org.support.project.knowledge.logic.DiffLogic;
import org.support.project.knowledge.logic.EventsLogic;
import org.support.project.knowledge.logic.GroupLogic;
import org.support.project.knowledge.logic.KeywordLogic;
import org.support.project.knowledge.logic.KnowledgeLogic;
import org.support.project.knowledge.logic.LikeLogic;
import org.support.project.knowledge.logic.MarkdownLogic;
import org.support.project.knowledge.logic.TagLogic;
import org.support.project.knowledge.logic.TargetLogic;
import org.support.project.knowledge.logic.TemplateLogic;
import org.support.project.knowledge.logic.TimeZoneLogic;
import org.support.project.knowledge.logic.UploadedFileLogic;
import org.support.project.knowledge.logic.activity.Activity;
import org.support.project.knowledge.logic.activity.ActivityLogic;
import org.support.project.knowledge.vo.LikeCount;
import org.support.project.knowledge.vo.ListData;
import org.support.project.knowledge.vo.MarkDown;
import org.support.project.knowledge.vo.Participations;
import org.support.project.knowledge.vo.StockKnowledge;
import org.support.project.knowledge.vo.UploadFile;
import org.support.project.web.bean.LabelValue;
import org.support.project.web.bean.LoginedUser;
import org.support.project.web.boundary.Boundary;
import org.support.project.web.common.HttpStatus;
import org.support.project.web.common.HttpUtil;
import org.support.project.web.control.service.Get;
import org.support.project.web.control.service.Post;
import org.support.project.web.dao.SystemConfigsDao;
import org.support.project.web.dao.UserConfigsDao;
import org.support.project.web.dao.UsersDao;
import org.support.project.web.entity.GroupsEntity;
import org.support.project.web.entity.SystemConfigsEntity;
import org.support.project.web.entity.UserConfigsEntity;
import org.support.project.web.entity.UsersEntity;
import org.support.project.web.exception.InvalidParamException;

/**
 * ナレッジ操作のコントロール
 * 
 * @author Koda
 */
@DI(instance = Instance.Prototype)
public class KnowledgeControl extends KnowledgeControlBase {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(KnowledgeControl.class);
    /** Cookieに保持する閲覧履歴の件数 */
    private static final int COOKIE_COUNT = 20;
    /** Cookieに保持する閲覧履歴の区切り文字 */
    private static final String COOKIE_SEPARATOR = "-";
    /** ナレッジ一覧に表示する件数 */
    public static final int PAGE_LIMIT = 50;
    /** お気に入りに表示する件数 */
    public static final int FAV_PAGE_LIMIT = 10;

    /**
     * ナレッジを表示
     * 
     * @return
     * @throws InvalidParamException
     * @throws ParseException
     */
    @Get(publishToken = "knowledge")
    public Boundary view() throws InvalidParamException, ParseException {
        // 共通処理呼の表示条件の保持の呼び出し
        setViewParam();
        
        Long knowledgeId = super.getPathLong(Long.valueOf(-1));
        
        SystemConfigsDao dao = SystemConfigsDao.get();
        SystemConfigsEntity config = dao.selectOnKey(SystemConfig.SYSTEM_URL, AppConfig.get().getSystemName());
        StringBuilder url = new StringBuilder();
        if (config == null) {
            url.append(HttpUtil.getContextUrl(getRequest()));
        } else {
            url.append(config.getConfigValue());
        }
        url.append("/open.knowledge/view/").append(knowledgeId);
        setAttribute("url", url.toString());

        KnowledgeLogic knowledgeLogic = KnowledgeLogic.get();
        LoginedUser loginedUser = getLoginedUser();

        if (loginedUser == null) {
            KnowledgesEntity entity = KnowledgesDao.get().selectOnKey(knowledgeId);
            if (entity == null) {
                return sendError(HttpStatus.SC_404_NOT_FOUND, "NOT FOUND");
            }

            if (!entity.getPublicFlag().equals(KnowledgeLogic.PUBLIC_FLAG_PUBLIC)) {
                return super.redirect(getRequest().getContextPath() + "/protect.knowledge/view/" + knowledgeId);
            }
        }

        KnowledgesEntity entity = knowledgeLogic.selectWithTags(knowledgeId, getLoginedUser());
        if (entity == null) {
            return sendError(HttpStatus.SC_404_NOT_FOUND, "NOT FOUND");
        }

        // 今見たナレッジの情報をCookieに保存
        List<String> ids = new ArrayList<String>();
        ids.add(String.valueOf(knowledgeId));
        String history = getCookie(SystemConfig.COOKIE_KEY_HISTORY);
        LOG.debug("history: " + history);
        if (history.indexOf(COOKIE_SEPARATOR) != -1) {
            String[] historyIds = history.split(COOKIE_SEPARATOR);
            for (int i = 0; i < historyIds.length; i++) {
                if (!ids.contains(historyIds[i]) && StringUtils.isLong(historyIds[i])) {
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
        String cookieHistory = String.join(COOKIE_SEPARATOR, ids);
        setCookie(SystemConfig.COOKIE_KEY_HISTORY, cookieHistory);

        // Markdownを処理
        entity.setTitle(sanitize(entity.getTitle()));
        MarkDown markDown = MarkdownLogic.get().markdownToHtml(entity.getContent());
        entity.setContent(markDown.getHtml());

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
        // Markdown を処理
        for (CommentsEntity commentsEntity : comments) {
            MarkDown markDown2 = MarkdownLogic.get().markdownToHtml(commentsEntity.getComment());
            commentsEntity.setComment(markDown2.getHtml());
            
            Long likeCount = LikeCommentsDao.get().selectOnCommentNo(commentsEntity.getCommentNo());
            commentsEntity.setLikeCount(likeCount);
        }
        setAttribute("comments", comments);

        // 表示するグループを取得
        // List<GroupsEntity> groups = GroupLogic.get().selectGroupsOnKnowledgeId(knowledgeId);
        List<LabelValue> groups = TargetLogic.get().selectTargetsViewOnKnowledgeId(knowledgeId, loginedUser);
        setAttribute("groups", groups);

        // 編集権限
        List<LabelValue> editors = TargetLogic.get().selectEditorsViewOnKnowledgeId(knowledgeId, loginedUser);
        setAttribute("editors", editors);
        List<LabelValue> editors2 = TargetLogic.get().selectEditorsOnKnowledgeId(knowledgeId);
        boolean edit = knowledgeLogic.isEditor(loginedUser, entity, editors2);
        setAttribute("edit", edit);

        ArrayList<Long> knowledgeIds = new ArrayList<Long>();
        knowledgeIds.add(entity.getKnowledgeId());
        
        // ナレッジの公開先の情報を取得（共通処理）
        setKnowledgeTargets(loginedUser, knowledgeIds);

        //ストック情報を取得
        List<StocksEntity> stocks = StocksDao.get().selectStockOnKnowledge(entity, loginedUser);
        setAttribute("stocks", stocks);
        
        UserConfigsEntity stealth = UserConfigsDao.get().selectOnKey(UserConfig.STEALTH_ACCESS, AppConfig.get().getSystemName(), getLoginUserId());
        if (stealth == null || !"1".equals(stealth.getConfigValue())) {
            ActivityLogic.get().processActivity(Activity.KNOWLEDGE_SHOW, getLoginedUser(), DateUtils.now(), entity);
        }
        long point = KnowledgesDao.get().selectPoint(entity.getKnowledgeId());
        setAttribute("point", point);
        
        return forward("view.jsp");
    }
    
    /**
     * ナレッジの公開先の情報を取得
     * @param loginedUser
     * @param knowledges
     */
    private void setKnowledgeTargetsWithConv(LoginedUser loginedUser, List<KnowledgesEntity> knowledges) {
        ArrayList<Long> knowledgeIds = new ArrayList<>();
        for (KnowledgesEntity knowledgesEntity : knowledges) {
            knowledgeIds.add(knowledgesEntity.getKnowledgeId());
        }
        setKnowledgeTargets(loginedUser, knowledgeIds);
    }
    /**
     * ナレッジの公開先の情報を取得
     * @param loginedUser
     * @param knowledgeIds
     */
    private void setKnowledgeTargets(LoginedUser loginedUser, List<Long> knowledgeIds) {
        TargetLogic targetLogic = TargetLogic.get();
        Map<Long, List<LabelValue>> targets = targetLogic.selectTargetsOnKnowledgeIds(knowledgeIds, loginedUser);
        setAttribute("targets", targets);
        setAttribute("targetLogic", targetLogic);
    }
    
    /**
     * タグとグループの情報を取得（一覧画面の右側のサブリスト部分に表示する情報をセット）
     * @param loginedUser
     */
    private void setSublistInformations(LoginedUser loginedUser) {
        TagsDao tagsDao = TagsDao.get();
        ExGroupsDao groupsDao = ExGroupsDao.get();

        // タグとグループの情報を取得（画面右側のサブリスト部分に表示する）
        if (loginedUser != null && loginedUser.isAdmin()) {
            // 管理者であれば、ナレッジの件数は、参照権限を考慮していない
            List<TagsEntity> tags = tagsDao.selectTagsWithCount(0, FAV_PAGE_LIMIT);
            setAttribute("tags", tags);

            List<GroupsEntity> groups = groupsDao.selectGroupsWithCount(0, FAV_PAGE_LIMIT);
            setAttribute("groups", groups);
        } else {
            TagLogic tagLogic = TagLogic.get();
            List<TagsEntity> tags = tagLogic.selectTagsWithCount(loginedUser, 0, FAV_PAGE_LIMIT);
            setAttribute("tags", tags);

            if (loginedUser != null) {
                GroupLogic groupLogic = GroupLogic.get();
                List<GroupsEntity> groups = groupLogic.selectMyGroup(loginedUser, 0, FAV_PAGE_LIMIT);
                setAttribute("groups", groups);
            }
        }
        LOG.trace("タグ、グループ取得完了");
        
        List<TemplateMastersEntity> templateList = TemplateLogic.get().selectAll();
        Map<Integer, TemplateMastersEntity> templates = new LinkedHashMap<>();
        for (TemplateMastersEntity templateMastersEntity : templateList) {
            templates.put(templateMastersEntity.getTypeId(), templateMastersEntity);
        }
        setAttribute("templates", templates);
    }
    
    /**
     * 一覧に表示するオフセット（ページ番号）を取得する
     * @return
     * @throws InvalidParamException
     */
    private Integer getOffsetParameter() throws InvalidParamException {
        Integer offset = super.getPathInteger(0);
        int previous = offset - 1;
        if (previous < 0) {
            previous = 0;
        }
        setAttribute("offset", offset);
        setAttribute("previous", previous);
        setAttribute("next", offset + 1);
        return offset;
    }
    
    
    
    /**
     * リストを表示
     * 
     * @return
     * @throws Exception
     */
    @Get
    public Boundary list() throws Exception {
        LOG.trace("Call list");
        Integer offset = getOffsetParameter();
        
        String keyword = getParam("keyword");
        setAttribute("searchKeyword", keyword);

        // ログインユーザ情報を最新化
        // TODO 毎回最新化するのは、パフォーマンスが悪い？グループ情報が更新になった場合に、影響があるユーザの一覧を保持しておき、
        // そのユーザのみを更新した方が良いかも。いったんは、ナレッジの一覧を表示する際に、毎回更新してみる（それほど負荷が高くなさそうなので）
        updateLoginInfo();

        // 共通処理呼の表示条件の保持の呼び出し
        setViewParam();

        TagsDao tagsDao = TagsDao.get();
        ExGroupsDao groupsDao = ExGroupsDao.get();
        KnowledgeLogic knowledgeLogic = KnowledgeLogic.get();
        KeywordLogic keywordLogic = KeywordLogic.get();

        LoginedUser loginedUser = super.getLoginedUser();
        String tag = getParam("tag");
        String group = getParam("group");
        String user = getParam("user");
        String tagNames = getParam("tagNames");
        String groupNames = getParam("groupNames");
        String creators = getParam("creators");
        String[] templates = getParam("template", String[].class);

        boolean hiddenFilter = false;
        if ("quickFilter".equals(getParam("from"))) {
            if (templates == null) {
                templates = new String[0];
            }
            hiddenFilter = true;
            if (getLoginedUser() != null && getLoginUserId() != Integer.MIN_VALUE) {
                UserConfigsEntity config = UserConfigsDao.get().selectOnKey("LIST_FILTERS", AppConfig.get().getSystemName(), getLoginUserId());
                // Filterの設定を変更した
                if (config == null) {
                    config = new UserConfigsEntity();
                    config.setSystemName(AppConfig.get().getSystemName());
                    config.setConfigName("LIST_FILTERS");
                    config.setUserId(getLoginUserId());
                }
                StringJoinBuilder<String> builder = new StringJoinBuilder<>();
                for (String template : templates) {
                    builder.append(template);
                }
                String value = builder.join(",");
                if (!value.equals(config.getConfigValue())) {
                    config.setConfigValue(value);
                    UserConfigsDao.get().save(config);
                }
            }
            setAttribute("params", ""); // Filterの設定変更の場合は、検索パラメータをクリア
        } else {
            if (getLoginedUser() != null && getLoginUserId() != Integer.MIN_VALUE) {
                UserConfigsEntity config = UserConfigsDao.get().selectOnKey("LIST_FILTERS", AppConfig.get().getSystemName(), getLoginUserId());
                if (templates == null) {
                    // 検索画面からきていない
                    if (config != null) {
                        templates = config.getConfigValue().split(",");
                    }
                    // フィルタの条件を変更していないので、フィルタは表示しない
                    if (StringUtils.isEmpty(keyword)) {
                        hiddenFilter = true;
                    }
                }
            }
        }

        String keywordSortTypeString = getCookie(SystemConfig.COOKIE_KEY_KEYWORD_SORT_TYPE);
        int keywordSortType;
        if ("" == keywordSortTypeString) {
            keywordSortType = KnowledgeLogic.KEYWORD_SORT_TYPE_SCORE;
        } else {
            keywordSortType = Integer.valueOf(keywordSortTypeString);
        }
        knowledgeLogic.setKeywordSortType(keywordSortType);
        setAttribute("keywordSortType", keywordSortType);

        List<KnowledgesEntity> knowledges = new ArrayList<>();
        try {
            if (StringUtils.isInteger(tag)) {
                // タグを選択している
                LOG.trace("show on Tag");
                knowledges.addAll(knowledgeLogic.showKnowledgeOnTag(tag, loginedUser, offset * PAGE_LIMIT, PAGE_LIMIT));
                TagsEntity tagsEntity = tagsDao.selectOnKey(new Integer(tag));
                List<Integer> tagIds = new ArrayList<Integer>();
                String name = "";
                if (tagsEntity != null) {
                    tagIds.add(tagsEntity.getTagId());
                    name = tagsEntity.getTagName();
                }
                setAttribute("selectedTag", tagsEntity);
                setAttribute("selectedTagIds", tagIds);
                setAttribute("searchKeyword", keywordLogic.toTagsQuery(name) + keyword);
            } else if (StringUtils.isInteger(group)) {
                // グループを選択している
                LOG.trace("show on Group");
                knowledges.addAll(knowledgeLogic.showKnowledgeOnGroup(group, loginedUser, offset * PAGE_LIMIT, PAGE_LIMIT));
                GroupsEntity groupsEntity = groupsDao.selectOnKey(new Integer(group));
                List<Integer> groupIds = new ArrayList<Integer>();
                String name = "";
                if (groupsEntity != null) {
                    groupIds.add(groupsEntity.getGroupId());
                    name = groupsEntity.getGroupName();
                }
                setAttribute("selectedGroup", groupsEntity);
                setAttribute("selectedGroupIds", groupIds);
                setAttribute("searchKeyword", keywordLogic.toGroupsQuery(name) + keyword);
            } else if (StringUtils.isNotEmpty(user) && StringUtils.isInteger(user)) {
                // ユーザを選択している
                LOG.trace("show on User");
                int userId = Integer.parseInt(user);
                knowledges.addAll(knowledgeLogic.showKnowledgeOnUser(userId, loginedUser, offset * PAGE_LIMIT, PAGE_LIMIT));
                UsersEntity usersEntity = UsersDao.get().selectOnKey(userId);
                usersEntity.setPassword("");
                setAttribute("selectedUser", usersEntity);
            } else if (StringUtils.isNotEmpty(tagNames) || StringUtils.isNotEmpty(groupNames)) {
                // タグとキーワードで検索
                LOG.trace("show on Tags and Groups and keyword");
                String searchKeyword = "";
                String[] taglist = tagNames.split(",");
                List<TagsEntity> tags = new ArrayList<TagsEntity>();
                List<Integer> tagIds = new ArrayList<Integer>();
                for (String string : taglist) {
                    String tagname = string.trim();
                    if (tagname.startsWith(" ") && tagname.length() > " ".length()) {
                        tagname = tagname.substring(" ".length());
                    }
                    TagsEntity tagsEntity = tagsDao.selectOnTagName(tagname);
                    if (tagsEntity != null) {
                        tags.add(tagsEntity);
                        tagIds.add(tagsEntity.getTagId());
                    }
                }
                if (0 < tags.size()) {
                    searchKeyword += keywordLogic.toTagsQuery(tagNames.replaceAll("[\\xc2\\xa0]", ""));
                }
    
                List<GroupsEntity> groups = new ArrayList<GroupsEntity>();
                List<Integer> groupIds = new ArrayList<Integer>();
                if (loginedUser != null) {
                    String[] grouplist = groupNames.split(",");
                    for (String string : grouplist) {
                        String groupname = string.trim();
                        if (groupname.startsWith(" ") && groupname.length() > " ".length()) {
                            groupname = groupname.substring(" ".length());
                        }
                        GroupsEntity groupsEntity = groupsDao.selectOnGroupName(groupname);
                        if (groupsEntity != null) {
                            groups.add(groupsEntity);
                            groupIds.add(groupsEntity.getGroupId());
                        }
                    }
                    if (0 < groups.size()) {
                        searchKeyword += keywordLogic.toGroupsQuery(groupNames.replaceAll("[\\xc2\\xa0]", ""));
                    }
                }
    
                setAttribute("selectedTags", tags);
                setAttribute("selectedGroups", groups);
                setAttribute("selectedTagIds", tagIds);
                setAttribute("selectedGroupIds", groupIds);
                setAttribute("searchKeyword", searchKeyword + keyword);
    
                knowledges.addAll(knowledgeLogic.searchKnowledge(keyword, tags, groups, null, templates, loginedUser, offset * PAGE_LIMIT, PAGE_LIMIT));
            } else {
                // その他(キーワード検索)
                LOG.trace("search");
                List<GroupsEntity> groups = null;
                List<TagsEntity> tags = null;
                List<Integer> groupIds = new ArrayList<Integer>();
                List<Integer> tagIds = new ArrayList<Integer>();
    
                if (loginedUser != null) {
                    String groupKeyword = keywordLogic.parseQuery("groups", keyword);
                    if (groupKeyword != null) {
                        groups = new ArrayList<GroupsEntity>();
                        for (String groupName : groupKeyword.split(",")) {
                            GroupsEntity groupsEntity = groupsDao.selectOnGroupName(groupName);
                            if (groupsEntity != null) {
                                groups.add(groupsEntity);
                                groupIds.add(groupsEntity.getGroupId());
                            }
                        }
                        setAttribute("selectedGroups", groups);
                        setAttribute("selectedGroupIds", groupIds);
                    }
                }
    
                String tagKeyword = keywordLogic.parseQuery("tags", keyword);
                if (tagKeyword != null) {
                    tags = new ArrayList<TagsEntity>();
                    for (String tagName : tagKeyword.split(",")) {
                        TagsEntity tagsEntity = tagsDao.selectOnTagName(tagName);
                        if (tagsEntity != null) {
                            tags.add(tagsEntity);
                            tagIds.add(tagsEntity.getTagId());
                        }
                    }
                    setAttribute("selectedTags", tags);
                    setAttribute("selectedTagIds", tagIds);
                }
    
                keyword = keywordLogic.parseKeyword(keyword);
    
                setAttribute("keyword", keyword);
                
                List <UsersEntity> creatorUserEntities = new ArrayList<>();
                if (StringUtils.isNotEmpty(creators)) {
                    String[] creatorsArray = creators.split(",");
                    for (String userName : creatorsArray) {
                        List<UsersEntity> users = ExUsersDao.get().selectByUserName(userName);
                        creatorUserEntities.addAll(users);
                    }
                }
                setAttribute("creators", creatorUserEntities);
                
                knowledges.addAll(knowledgeLogic.searchKnowledge(keyword, tags, groups, creatorUserEntities, templates, loginedUser, offset * PAGE_LIMIT, PAGE_LIMIT));
            }
    
            List<StockKnowledge> stocks = knowledgeLogic.setStockInfo(knowledges, loginedUser);
            KnowledgeLogic.get().setViewed(stocks, getLoginedUser());
            setAttribute("knowledges", stocks);
            LOG.trace("検索終了");

            if (templates != null) {
                List<TemplateMastersEntity> templateItems = new ArrayList<>();
                for (String template: templates) {
                    if (StringUtils.isNotEmpty(template) && StringUtils.isInteger(template)) {
                        TemplateMastersEntity templateMastersEntity = TemplateMastersDao.get().selectOnKey(new Integer(template));
                        templateItems.add(templateMastersEntity);
                    }
                }
                if (LOG.isDebugEnabled()) {
                    LOG.debug(templateItems);
                }
                if (!hiddenFilter) {
                    setAttribute("types", templateItems);
                }
                setAttribute("selectedTemplates", templateItems);
            }
            // ナレッジの公開先の情報を取得
            setKnowledgeTargetsWithConv(loginedUser, knowledges);
            // タグとグループの情報を取得（一覧画面の右側のサブリスト部分に表示する情報をセット）
            setSublistInformations(loginedUser);
            
            return forward("list.jsp");
        } catch (InvalidParamException e) {
            return forward("list.jsp");
        }
    }

    /**
     * イベント一覧
     * 
     * @return
     * @throws InvalidParamException
     */
    @Get
    public Boundary events() throws InvalidParamException {
        String date = getParam("date");
        String timezone = getParam("timezone");
        LoginedUser loginedUser = super.getLoginedUser();
        if (StringUtils.isEmpty(date) || StringUtils.isEmpty(timezone)) {
            return sendError(HttpStatus.SC_400_BAD_REQUEST, "BAD REQUEST");
        }
        if (!TimeZoneLogic.get().exist(timezone)) {
            return sendError(HttpStatus.SC_400_BAD_REQUEST, "BAD REQUEST");
        }
        DateFormat monthformat = new SimpleDateFormat("yyyyMMdd");
        List<KnowledgesEntity> knowledges;
        try {
            Date d = monthformat.parse(date);
            setAttribute("start", d);
            knowledges = EventsLogic.get().eventKnowledgeList(date, timezone, loginedUser);
            List<StockKnowledge> stocks = KnowledgeLogic.get().setStockInfo(knowledges, loginedUser);
            for (StockKnowledge stock : stocks) {
                Participations participations = EventsLogic.get().isParticipation(stock.getKnowledgeId(), getLoginUserId());
                stock.setParticipations(participations);
            }
            KnowledgeLogic.get().setViewed(stocks, getLoginedUser());
            setAttribute("knowledges", stocks);
        } catch (java.text.ParseException e) {
            return sendError(HttpStatus.SC_400_BAD_REQUEST, "BAD REQUEST");
        }
        // ナレッジの公開先の情報を取得
        setKnowledgeTargetsWithConv(loginedUser, knowledges);
        // タグとグループの情報を取得（一覧画面の右側のサブリスト部分に表示する情報をセット）
        setSublistInformations(loginedUser);

        return forward("events.jsp");
    }
    
    
    /**
     * 閲覧履歴の表示
     * 
     * @return
     * @throws InvalidParamException
     */
    @Get
    public Boundary show_history() throws InvalidParamException {
        LoginedUser loginedUser = super.getLoginedUser();
        KnowledgeLogic knowledgeLogic = KnowledgeLogic.get();

        // History表示
        String history = getCookie(SystemConfig.COOKIE_KEY_HISTORY);
        List<String> historyIds = new ArrayList<>();
        ArrayList<Long> knowledgeIds = new ArrayList<>();
        LOG.debug("history: " + history);
        if (history.indexOf(COOKIE_SEPARATOR) != -1) {
            String[] splits = history.split(COOKIE_SEPARATOR);
            for (String string : splits) {
                if (StringUtils.isLong(string)) {
                    historyIds.add(string);
                    knowledgeIds.add(new Long(string));
                }
            }
        } else {
            if (StringUtils.isLong(history)) {
                historyIds.add(history);
                knowledgeIds.add(new Long(history));
            }
        }
        List<KnowledgesEntity> histories = knowledgeLogic.getKnowledges(historyIds, loginedUser);
        List<StockKnowledge> stocks = knowledgeLogic.setStockInfo(histories, loginedUser);
        KnowledgeLogic.get().setViewed(stocks, getLoginedUser());
        setAttribute("histories", stocks);
        LOG.trace("履歴取得完了");

        // ナレッジの公開先の情報を取得
        setKnowledgeTargets(loginedUser, knowledgeIds);
        // タグとグループの情報を取得（一覧画面の右側のサブリスト部分に表示する情報をセット）
        setSublistInformations(loginedUser);

        return forward("show_history.jsp");
    }

    /**
     * 人気のKnowledgeを表示
     * 
     * @return
     * @throws InvalidParamException
     */
    @Get
    public Boundary show_popularity() throws InvalidParamException {
        LoginedUser loginedUser = super.getLoginedUser();
        KnowledgeLogic knowledgeLogic = KnowledgeLogic.get();

        List<KnowledgesEntity> list = knowledgeLogic.getPopularityKnowledges(loginedUser, 0, 20);
        List<StockKnowledge> stocks = knowledgeLogic.setStockInfo(list, loginedUser);
        KnowledgeLogic.get().setViewed(stocks, getLoginedUser());
        setAttribute("popularities", stocks);
        LOG.trace("取得完了");
        // ナレッジの公開先の情報を取得
        setKnowledgeTargetsWithConv(loginedUser, list);
        // タグとグループの情報を取得（一覧画面の右側のサブリスト部分に表示する情報をセット）
        setSublistInformations(loginedUser);

        return forward("popularity.jsp");
    }

    /**
     * ストックしたナレッジを表示
     * 
     * @return
     * @throws InvalidParamException
     */
    @Get
    public Boundary stocks() throws InvalidParamException {
        Integer offset = getOffsetParameter();
        String stockidstr = getParam("stockid");
        LoginedUser loginedUser = super.getLoginedUser();
        Long stockid = null;
        if (StringUtils.isLong(stockidstr)) {
            stockid = Long.parseLong(stockidstr);
            StocksEntity stocksEntity = StocksDao.get().selectOnKey(stockid);
            if (stocksEntity == null || stocksEntity.getInsertUser().intValue() != getLoginUserId().intValue()) {
                // タグとグループの情報を取得（一覧画面の右側のサブリスト部分に表示する情報をセット）
                setSublistInformations(loginedUser);
                // Stockにアクセスできない
                return forward("stocks.jsp");
            } else {
                setAttribute("stock", stocksEntity);
            }
        }
        
        KnowledgeLogic knowledgeLogic = KnowledgeLogic.get();
        List<KnowledgesEntity> list = knowledgeLogic.getStocks(loginedUser, offset * PAGE_LIMIT, PAGE_LIMIT, stockid);
        List<StockKnowledge> stocks = knowledgeLogic.setStockInfo(list, loginedUser);
        KnowledgeLogic.get().setViewed(stocks, getLoginedUser());
        setAttribute("popularities", stocks);
        LOG.trace("取得完了");
        
        // ナレッジの公開先の情報を取得
        setKnowledgeTargetsWithConv(loginedUser, list);
        // タグとグループの情報を取得（一覧画面の右側のサブリスト部分に表示する情報をセット）
        setSublistInformations(loginedUser);

        return forward("stocks.jsp");
    }    
    
    /**
     * いいねを押下
     * 
     * @return
     * @throws InvalidParamException
     */
    @Post(subscribeToken="knowledge")
    public Boundary like() throws InvalidParamException {
        Long knowledgeId = super.getPathLong(Long.valueOf(-1));
        Long count = LikeLogic.get().addLike(knowledgeId, getLoginedUser(), getLocale());
        LikeCount likeCount = new LikeCount();
        likeCount.setKnowledgeId(knowledgeId);
        likeCount.setCount(count);
        return send(likeCount);
    }
    /**
     * コメントにイイネを押下
     * @return
     * @throws InvalidParamException 
     */
    @Post(subscribeToken="knowledge")
    public Boundary likecomment() throws InvalidParamException {
        Long commentNo = super.getPathLong(Long.valueOf(-1));
        Long count = LikeLogic.get().addLikeComment(commentNo, getLoginedUser(), getLocale());
        LikeCount likeCount = new LikeCount();
        likeCount.setCount(count);
        return send(likeCount);
    }

    /**
     * タイトルとコンテンツの危険なタグをエスケープした結果を返す
     * 
     * @param entity
     * @return
     * @throws ParseException
     */
    @Post(subscribeToken = "")
    public Boundary escape(KnowledgesEntity entity) throws ParseException {
        super.setSendEscapeHtml(false);
        entity.setTitle(sanitize(entity.getTitle()));
        entity.setContent(sanitize(entity.getContent()));
        return super.send(entity);
    }

    /**
     * タイトルの危険なタグをサニタイズし、コンテンツのmarkdownをHTMLへ変換する
     * 
     * @param entity
     * @return
     * @throws ParseException
     */
    @Post(subscribeToken = "")
    public Boundary marked(KnowledgesEntity entity) throws ParseException {
        super.setSendEscapeHtml(false);
        entity.setTitle(sanitize(entity.getTitle()));
        MarkDown markDown = MarkdownLogic.get().markdownToHtml(entity.getContent());
        entity.setContent(markDown.getHtml());
        return super.send(entity);
    }

    /**
     * 検索画面を表示
     * 
     * @return
     */
    @Get
    public Boundary search() {
        KeywordLogic keywordLogic = KeywordLogic.get();
        LoginedUser loginedUser = super.getLoginedUser();
        List<GroupsEntity> groupitems = new ArrayList<GroupsEntity>();
        List<TagsEntity> tagitems = TagsDao.get().selectAll();
        setAttribute("tagitems", tagitems);
        if (loginedUser != null) {
            if (loginedUser.isAdmin()) {
                groupitems = ExGroupsDao.get().selectAll();
            } else {
                groupitems = loginedUser.getGroups();
            }
        }
        setAttribute("groupitems", groupitems);
        // 共通処理呼の表示条件の保持の呼び出し
        setViewParam();

        // groups:やtags:などの検索クエリを分解してフォームに表示するために分解したデータをセットする
        String keyword = getParam("keyword");
        setAttribute("searchKeyword", keywordLogic.parseKeyword(keyword));
        setAttribute("tagNames", keywordLogic.parseQuery("tags", keyword));
        if (loginedUser != null) {
            setAttribute("groupNames", keywordLogic.parseQuery("groups", keyword));
        }
        List<TemplateMastersEntity> templates = TemplateLogic.get().selectAll();
        setAttribute("templates", templates);
        return forward("search.jsp");
    }

    /**
     * いいねを押したユーザを一覧表示
     * 
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

        int previous = page - 1;
        if (previous < 0) {
            previous = 0;
        }
        setAttribute("page", page);
        setAttribute("previous", previous);
        setAttribute("next", page + 1);

        return forward("likes.jsp");
    }

    
    /**
     * いいねを押したユーザを一覧表示（コメントに対し）
     * 
     * @return
     * @throws InvalidParamException
     */
    @Get
    public Boundary likecomments() throws InvalidParamException {
        // 共通処理呼の表示条件の保持の呼び出し
        setViewParam();

        Long commentNo = super.getPathLong(Long.valueOf(-1));
        CommentsEntity comment = CommentsDao.get().selectOnKey(commentNo);
        if (comment == null) {
            return sendError(HttpStatus.SC_404_NOT_FOUND, "NOT FOUND");
        }
        setAttribute("knowledgeId", comment.getKnowledgeId());
        setAttribute("commentNo", comment.getKnowledgeId());

        Integer page = 0;
        String p = getParamWithDefault("page", "");
        if (StringUtils.isInteger(p)) {
            page = Integer.parseInt(p);
        }

        List<LikeCommentsEntity> likes = LikeCommentsDao.get().selectOnCommentNo(commentNo, page * PAGE_LIMIT, PAGE_LIMIT);
        setAttribute("likes", likes);

        int previous = page - 1;
        if (previous < 0) {
            previous = 0;
        }
        setAttribute("page", page);
        setAttribute("previous", previous);
        setAttribute("next", page + 1);
        
        return forward("likes.jsp");
    }
    
    /**
     * 編集履歴の表示
     * 
     * @return
     * @throws InvalidParamException
     */
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
        int previous = page - 1;
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

    /**
     * 編集履歴の更新内容表示
     * 
     * @return
     * @throws InvalidParamException
     */
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

    /**
     * ナレッジのテンプレート情報を取得
     * 
     * @return
     * @throws InvalidParamException
     */
    @Get
    public Boundary template() throws InvalidParamException {
        Integer typeId = super.getParam("type_id", Integer.class);
        TemplateMastersEntity template = TemplateMastersDao.get().selectWithItems(typeId);
        if (template == null) {
            // そのテンプレートは既に削除済みの場合、通常のナレッジのテンプレートで表示する（ナレッジのテンプレートは削除できないようにする）
            typeId = TemplateLogic.TYPE_ID_KNOWLEDGE;
            template = TemplateMastersDao.get().selectWithItems(typeId);
        }

        String draftId = super.getParam("draft_id");
        String knowledgeId = super.getParam("knowledge_id");
        if (StringUtils.isNotEmpty(draftId) && StringUtils.isLong(draftId)) {
            List<DraftItemValuesEntity> values = DraftItemValuesDao.get().selectOnDraftId(new Long(draftId));
            List<TemplateItemsEntity> items = template.getItems();
            for (DraftItemValuesEntity val : values) {
                for (TemplateItemsEntity item : items) {
                    if (val.getItemNo().equals(item.getItemNo())) {
                        item.setItemValue(val.getItemValue());
                        break;
                    }
                }
            }
        } else if (StringUtils.isNotEmpty(knowledgeId) && StringUtils.isLong(knowledgeId)) {
            KnowledgeLogic knowledgeLogic = KnowledgeLogic.get();
            KnowledgesEntity entity = knowledgeLogic.select(new Long(knowledgeId), getLoginedUser());
            if (entity == null) {
                return sendError(HttpStatus.SC_404_NOT_FOUND, "NOT FOUND");
            }

            // 保存している値も返す
            List<KnowledgeItemValuesEntity> values = KnowledgeItemValuesDao.get().selectOnKnowledgeId(entity.getKnowledgeId());
            List<TemplateItemsEntity> items = template.getItems();
            for (KnowledgeItemValuesEntity val : values) {
                for (TemplateItemsEntity item : items) {
                    if (val.getItemNo().equals(item.getItemNo())) {
                        item.setItemValue(val.getItemValue());
                        break;
                    }
                }
            }
        }
        return send(template);
    }

    
    /**
     * オートコンプリートの候補を取得（JSON）
     * 
     * @return
     * @throws ParseException
     */
    @Get
    public Boundary items() throws ParseException {
        String q = super.getParameter("q");
        List<KnowledgesEntity> list = KnowledgeLogic.get().selectAccessAbleKnowledge(q, getLoginedUser());
        ListData listdata = new ListData();
        listdata.setItems(list);
        return super.send(listdata);
    }
    
    
}
