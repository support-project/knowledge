package org.support.project.knowledge.searcher.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.ja.JapaneseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.queryparser.classic.QueryParser.Operator;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TopDocsCollector;
import org.apache.lucene.search.TopFieldCollector;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.search.TotalHitCountCollector;
import org.apache.lucene.search.highlight.Formatter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.search.highlight.SimpleSpanFragmenter;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.support.project.common.config.ConfigLoader;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.HtmlUtils;
import org.support.project.common.util.StringUtils;
import org.support.project.knowledge.config.AppConfig;
import org.support.project.knowledge.config.IndexType;
import org.support.project.knowledge.indexer.impl.LuceneIndexer;
import org.support.project.knowledge.logic.KnowledgeLogic;
import org.support.project.knowledge.searcher.SearchResultValue;
import org.support.project.knowledge.searcher.Searcher;
import org.support.project.knowledge.searcher.SearchingValue;
import org.support.project.web.bean.MessageResult;
import org.support.project.web.common.HttpStatus;
import org.support.project.web.config.MessageStatus;
import org.support.project.web.exception.InvalidParamException;

/**
 * Luceneを使った検索
 * 
 * @author Koda
 *
 */
public class LuceneSearcher implements Searcher {
    /** ログ */
    private static Log log = LogFactory.getLog(LuceneSearcher.class);
    /** 検索のリミット */
    public static final int CONTENTS_LIMIT_LENGTH = 200;

    /** ラベル：種類 */
    public static final String FIELD_LABEL_TYPE = LuceneIndexer.FIELD_LABEL_TYPE;
    /** ラベル：ID */
    public static final String FIELD_LABEL_ID = LuceneIndexer.FIELD_LABEL_ID;
    /** ラベル：タイトル */
    public static final String FIELD_LABEL_TITLE = LuceneIndexer.FIELD_LABEL_TITLE;
    /** ラベル：コンテンツ */
    public static final String FIELD_LABEL_CONTENTS = LuceneIndexer.FIELD_LABEL_CONTENTS;
    /** ラベル：タグ */
    public static final String FIELD_LABEL_TAGS = LuceneIndexer.FIELD_LABEL_TAGS;
    /** ラベル：アクセス可能なユーザ */
    public static final String FIELD_LABEL_USERS = LuceneIndexer.FIELD_LABEL_USERS;
    /** ラベル：アクセス可能なグループ */
    public static final String FIELD_LABEL_GROUPS = LuceneIndexer.FIELD_LABEL_GROUPS;
    /** ラベル：作成者 */
    public static final String FIELD_LABEL_CREATE_USER = LuceneIndexer.FIELD_LABEL_CREATE_USER;
    /** ラベル：日時 */
    public static final String FIELD_LABEL_TIME = LuceneIndexer.FIELD_LABEL_TIME;
    /** ラベル：テンプレート */
    public static final String FIELD_LABEL_TEMPLATE = LuceneIndexer.FIELD_LABEL_TEMPLATE;

    /** 検索キーワードを抽出するアナライザー N-gramではなく形態素解析を利用 */
    // private Analyzer analyzer = new SimpleAnalyzer(Version.LUCENE_4_10_2);
    private Analyzer analyzer = new JapaneseAnalyzer();

    /**
     * Indexが格納されているディレクトリのパスを取得
     * 
     * @return
     */
    private String getIndexPath() {
        AppConfig appConfig = ConfigLoader.load(AppConfig.APP_CONFIG, AppConfig.class);
        log.debug("lucene index: " + appConfig.getIndexPath());
        return appConfig.getIndexPath();
    }

    /**
     * 検索
     * @throws InvalidParamException 
     */
    public List<SearchResultValue> search(final SearchingValue value, int keywordSortType) throws IOException, InvalidTokenOffsetsException, InvalidParamException {
        List<SearchResultValue> resultValues = new ArrayList<>();

        File indexDir = new File(getIndexPath());
        if (!indexDir.exists()) {
            return resultValues;
        }
        File[] children = indexDir.listFiles();
        if (children == null || children.length == 0) {
            return resultValues;
        }

        IndexReader reader = DirectoryReader.open(FSDirectory.open(indexDir));
        IndexSearcher searcher = new IndexSearcher(reader);
        
        Query query = null;
        try {
            query = structQuery(value);
            log.debug("Searching for: " + query.toString());
        } catch (ParseException e) {
            MessageResult msg = new MessageResult(
                    MessageStatus.Warning,
                    HttpStatus.SC_400_BAD_REQUEST,
                    "knowledge.list.invalid.keyword",
                    "");
            throw new InvalidParamException(msg);
        }

        TotalHitCountCollector countCollector = new TotalHitCountCollector();
        searcher.search(query, countCollector);
        log.debug("Found " + countCollector.getTotalHits() + " hits.");

        TopDocsCollector<? extends ScoreDoc> collector;
        if (StringUtils.isNotEmpty(value.getKeyword())) {
            switch (keywordSortType) {
            case KnowledgeLogic.KEYWORD_SORT_TYPE_TIME:
                Sort sort = new Sort(new SortField(FIELD_LABEL_TIME, SortField.Type.LONG, true));
                collector = TopFieldCollector.create(sort, value.getOffset() + value.getLimit(), true, false, false, false);
                break;
            case KnowledgeLogic.KEYWORD_SORT_TYPE_SCORE:
            default:
                collector = TopScoreDocCollector.create(value.getOffset() + value.getLimit(), true);
                break;
            }
        } else {
            // Sort sort = new Sort(new SortField(FIELD_LABEL_ID, SortField.Type.INT, true));
            // Sort sort = Sort.INDEXORDER;
            Sort sort = new Sort(new SortField(FIELD_LABEL_TIME, SortField.Type.LONG, true));
            collector = TopFieldCollector.create(sort, value.getOffset() + value.getLimit(), true, false, false, false);
        }

        searcher.search(query, collector);
        ScoreDoc[] hits = collector.topDocs(value.getOffset(), value.getOffset() + value.getLimit()).scoreDocs;

        log.debug("Found " + hits.length + " hits.");
        for (int i = 0; i < hits.length; ++i) {
            int docId = hits[i].doc;
            Document d = searcher.doc(docId);
            if (log.isDebugEnabled()) {
                log.debug((i + 1) + ". \n" + "\t[id]\t" + d.get(FIELD_LABEL_ID) + "\n" + "\t[tag]\t" + d.get(FIELD_LABEL_TAGS) + "\n" + "\t[user]\t"
                        + d.get(FIELD_LABEL_USERS) + "\n" + "\t[group]\t" + d.get(FIELD_LABEL_GROUPS) + "\n" + "\t[score]\t" + hits[i].score + "\n");
            }

            SearchResultValue resultValue = new SearchResultValue();
            resultValue.setType(Integer.parseInt(d.get(FIELD_LABEL_TYPE)));
            resultValue.setId(d.get(FIELD_LABEL_ID));
            resultValue.setScore(hits[i].score);
            resultValue.setTitle(d.get(FIELD_LABEL_TITLE));
            resultValue.setContents(d.get(FIELD_LABEL_CONTENTS));

            if (StringUtils.isNotEmpty(resultValue.getTitle())) {
                String bestFragment = getHighlightedField(query, analyzer, FIELD_LABEL_TITLE, resultValue.getTitle());
                if (log.isDebugEnabled()) {
                    log.debug("----- highlited title -----\n" + bestFragment);
                }
                resultValue.setHighlightedTitle(bestFragment);
            }

            if (StringUtils.isNotEmpty(resultValue.getContents())) {
                String content = HtmlUtils.escapeHTML(resultValue.getContents()); // 一覧で表示する際には、HTMLタグはエスケープする
                String bestFragment = getHighlightedField(query, analyzer, FIELD_LABEL_CONTENTS, content);
                if (log.isDebugEnabled()) {
                    log.debug("----- highlited contents -----\n" + bestFragment);
                }
                resultValue.setHighlightedContents(bestFragment);
            }

            resultValues.add(resultValue);
        }

        return resultValues;

    }

    /**
     * クエリの組み立て
     * 
     * @param value
     * @return
     * @throws ParseException
     */
    private Query structQuery(final SearchingValue value) throws ParseException {

        // クエリー組み立て
        // 条件が指定されていれば、containerに入れていく
        BooleanQuery container = new BooleanQuery();

        if (StringUtils.isNotEmpty(value.getKeyword())) {
            if (value.getKeyword().startsWith("*") || value.getKeyword().startsWith("?")) {
                log.info("Cannot parse '?': '*' or '?' not allowed as first character in WildcardQuery");
                value.setKeyword(value.getKeyword().substring(1)); // 初めの1文字を削除
                return structQuery(value); // 削除したキーワードで再度クエリ組み立て
            }

            // キーワード検索(内容かパス名にキーワードがあるか)
            BooleanQuery miniContainer = new BooleanQuery();

            QueryParser queryParser = new QueryParser(Version.LUCENE_4_10_2, FIELD_LABEL_TITLE, analyzer);
            queryParser.setDefaultOperator(Operator.OR);
            Query query;
            try {
                query = queryParser.parse(value.getKeyword());
            } catch (org.apache.lucene.queryparser.classic.ParseException e) {
                query = queryParser.parse(value.getKeyword().replaceAll("/", ""));
            }
            miniContainer.add(query, BooleanClause.Occur.SHOULD);

            queryParser = new QueryParser(Version.LUCENE_4_10_2, FIELD_LABEL_CONTENTS, analyzer);
            queryParser.setDefaultOperator(Operator.OR);
            try {
                query = queryParser.parse(value.getKeyword());
            } catch (org.apache.lucene.queryparser.classic.ParseException e) {
                query = queryParser.parse(value.getKeyword().replaceAll("/", ""));
            }
            miniContainer.add(query, BooleanClause.Occur.SHOULD);

            container.add(miniContainer, BooleanClause.Occur.MUST);
        } else {
            Query query = NumericRangeQuery.newIntRange(FIELD_LABEL_TYPE, 1, IndexType.knowledge.getValue(), IndexType.knowledge.getValue(), true,
                    true);
            container.add(query, BooleanClause.Occur.MUST);
        }

        if (StringUtils.isNotEmpty(value.getTags())) {
            QueryParser queryParser = new QueryParser(Version.LUCENE_4_10_2, FIELD_LABEL_TAGS, analyzer);
            queryParser.setDefaultOperator(Operator.AND);
            Query query = queryParser.parse(value.getTags());
            container.add(query, BooleanClause.Occur.MUST);
        }
        if (StringUtils.isNotEmpty(value.getUsers()) || StringUtils.isNotEmpty(value.getGroups())) {
            // ユーザかグループのどちらかにアクセス権があること
            BooleanQuery miniContainer = new BooleanQuery();
            QueryParser queryParser;
            Query query;

            if (StringUtils.isNotEmpty(value.getUsers())) {
                queryParser = new QueryParser(Version.LUCENE_4_10_2, FIELD_LABEL_USERS, analyzer);
                queryParser.setDefaultOperator(Operator.OR);
                query = queryParser.parse(value.getUsers());
                miniContainer.add(query, BooleanClause.Occur.SHOULD);
            }

            if (StringUtils.isNotEmpty(value.getGroups())) {
                queryParser = new QueryParser(Version.LUCENE_4_10_2, FIELD_LABEL_GROUPS, analyzer);
                queryParser.setDefaultOperator(Operator.OR);
                query = queryParser.parse(value.getGroups());
                miniContainer.add(query, BooleanClause.Occur.SHOULD);
            }

            container.add(miniContainer, BooleanClause.Occur.MUST);
        }
        if (StringUtils.isNotEmpty(value.getCreators())) {
            QueryParser queryParser = new QueryParser(Version.LUCENE_4_10_2, FIELD_LABEL_CREATE_USER, analyzer);
            queryParser.setDefaultOperator(Operator.OR);
            Query query = queryParser.parse(value.getCreators());
            container.add(query, BooleanClause.Occur.MUST);
        }
        
        if (value.getTemplates() != null && !value.getTemplates().isEmpty()) {
            BooleanQuery miniContainer = new BooleanQuery();
            for (Integer templatesId: value.getTemplates()) {
                Query query = NumericRangeQuery.newIntRange(FIELD_LABEL_TEMPLATE, 1, templatesId, templatesId, true, true);
                miniContainer.add(query, BooleanClause.Occur.SHOULD);
            }
            container.add(miniContainer, BooleanClause.Occur.MUST);
        }
        
        return container;
    }

    /**
     * 検索キーワードのハイライト
     * 
     * @param query
     * @param analyzer
     * @param fieldName
     * @param fieldValue
     * @return
     * @throws IOException
     * @throws InvalidTokenOffsetsException
     */
    private String getHighlightedField(Query query, Analyzer analyzer, String fieldName, String fieldValue)
            throws IOException, InvalidTokenOffsetsException {
        Formatter formatter = new SimpleHTMLFormatter("<span class=\"mark\">", "</span>");
        // Formatter formatter = new SimpleHTMLFormatter();
        QueryScorer queryScorer = new QueryScorer(query);
        Highlighter highlighter = new Highlighter(formatter, queryScorer);
        highlighter.setTextFragmenter(new SimpleSpanFragmenter(queryScorer, CONTENTS_LIMIT_LENGTH));
        highlighter.setMaxDocCharsToAnalyze(Integer.MAX_VALUE);
        return highlighter.getBestFragment(analyzer, fieldName, fieldValue);
    }
}
