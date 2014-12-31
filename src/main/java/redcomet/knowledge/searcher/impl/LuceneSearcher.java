package redcomet.knowledge.searcher.impl;

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

import redcomet.common.config.ConfigLoader;
import redcomet.common.log.Log;
import redcomet.common.log.LogFactory;
import redcomet.common.util.StringUtils;
import redcomet.knowledge.config.AppConfig;
import redcomet.knowledge.config.IndexType;
import redcomet.knowledge.indexer.impl.LuceneIndexer;
import redcomet.knowledge.searcher.SearchResultValue;
import redcomet.knowledge.searcher.Searcher;
import redcomet.knowledge.searcher.SearchingValue;

public class LuceneSearcher implements Searcher {
	/** ログ */
	private static Log log = LogFactory.getLog(LuceneSearcher.class);

	public static final int CONTENTS_LIMIT_LENGTH = 200;

	public static final String FIELD_LABEL_TYPE = LuceneIndexer.FIELD_LABEL_TYPE;
	public static final String FIELD_LABEL_ID = LuceneIndexer.FIELD_LABEL_ID;
	public static final String FIELD_LABEL_TITLE = LuceneIndexer.FIELD_LABEL_TITLE;
	public static final String FIELD_LABEL_CONTENTS = LuceneIndexer.FIELD_LABEL_CONTENTS;
	public static final String FIELD_LABEL_TAGS = LuceneIndexer.FIELD_LABEL_TAGS;
	public static final String FIELD_LABEL_USERS = LuceneIndexer.FIELD_LABEL_USERS;
	public static final String FIELD_LABEL_GROUPS = LuceneIndexer.FIELD_LABEL_GROUPS;
	public static final String FIELD_LABEL_CREATE_USER = LuceneIndexer.FIELD_LABEL_CREATE_USER;
	public static final String FIELD_LABEL_TIME = LuceneIndexer.FIELD_LABEL_TIME;

//	private Analyzer analyzer = new SimpleAnalyzer(Version.LUCENE_4_10_2);
	private Analyzer analyzer = new JapaneseAnalyzer();

	private String getIndexPath() {
		AppConfig appConfig = ConfigLoader.load(AppConfig.APP_CONFIG, AppConfig.class);
		return appConfig.getIndexPath();
	}
	
	public List<SearchResultValue> search(final SearchingValue value) throws IOException, ParseException, InvalidTokenOffsetsException {
		List<SearchResultValue> resultValues = new ArrayList<>();
		
		File indexDir = new File(getIndexPath());
		if (!indexDir.exists() || indexDir.listFiles().length == 0) {
			return resultValues;
		}
		
		IndexReader reader = DirectoryReader.open(FSDirectory.open(indexDir));
		IndexSearcher searcher = new IndexSearcher(reader);

		Query query = structQuery(value);
		log.debug("Searching for: " + query.toString());
		
		TotalHitCountCollector countCollector = new TotalHitCountCollector();
		searcher.search(query, countCollector);
		log.debug("Found " + countCollector.getTotalHits() + " hits.");
		
		TopDocsCollector<? extends ScoreDoc> collector;
		if (StringUtils.isNotEmpty(value.getKeyword())) {
			collector = TopScoreDocCollector.create(value.getOffset() + value.getLimit(), true);
		} else {
			//Sort sort = new Sort(new SortField(FIELD_LABEL_ID, SortField.Type.INT, true));
			//Sort sort = Sort.INDEXORDER;
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
				log.debug((i + 1) + ". \n" 
						+ "\t[id]\t" + d.get(FIELD_LABEL_ID) + "\n" 
						+ "\t[tag]\t" + d.get(FIELD_LABEL_TAGS) + "\n"
						+ "\t[user]\t" + d.get(FIELD_LABEL_USERS) + "\n"
						+ "\t[group]\t" + d.get(FIELD_LABEL_GROUPS) + "\n"
						+ "\t[score]\t" + hits[i].score + "\n"
						);
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
				String bestFragment = getHighlightedField(query, analyzer, FIELD_LABEL_CONTENTS, resultValue.getContents());
				if (log.isDebugEnabled()) {
					log.debug("----- highlited contents -----\n" + bestFragment);
				}
				resultValue.setHighlightedContents(bestFragment);
			}
			
			
			resultValues.add(resultValue);
		}
		
		return resultValues;
		
	}

	private Query structQuery(final SearchingValue value) throws ParseException {
		
		// クエリー組み立て
		// 条件が指定されていれば、containerに入れていく
		BooleanQuery container = new BooleanQuery();
		
		if (StringUtils.isNotEmpty(value.getKeyword())) {
			//キーワード検索(内容かパス名にキーワードがあるか)
			BooleanQuery miniContainer = new BooleanQuery();
			
			QueryParser queryParser = new QueryParser(Version.LUCENE_4_10_2, FIELD_LABEL_TITLE, analyzer);
			queryParser.setDefaultOperator(Operator.OR);
			Query query = queryParser.parse(value.getKeyword());
			miniContainer.add(query, BooleanClause.Occur.SHOULD);
			
			queryParser = new QueryParser(Version.LUCENE_4_10_2, FIELD_LABEL_CONTENTS, analyzer);
			queryParser.setDefaultOperator(Operator.OR);
			query = queryParser.parse(value.getKeyword());
			miniContainer.add(query, BooleanClause.Occur.SHOULD);
			
			container.add(miniContainer, BooleanClause.Occur.MUST);
		} else {
			Query query = NumericRangeQuery.newIntRange(FIELD_LABEL_TYPE, 1, IndexType.Knoeledge.getValue(), IndexType.Knoeledge.getValue(), true, true);
			container.add(query, BooleanClause.Occur.MUST);
		}
		
		if (StringUtils.isNotEmpty(value.getTags())) {
			QueryParser queryParser = new QueryParser(Version.LUCENE_4_10_2, FIELD_LABEL_TAGS, analyzer);
			queryParser.setDefaultOperator(Operator.OR);
			Query query = queryParser.parse(value.getTags());
			container.add(query, BooleanClause.Occur.MUST);
		}
		if (StringUtils.isNotEmpty(value.getUsers())) {
			QueryParser queryParser = new QueryParser(Version.LUCENE_4_10_2, FIELD_LABEL_USERS, analyzer);
			queryParser.setDefaultOperator(Operator.OR);
			Query query = queryParser.parse(value.getUsers());
			container.add(query, BooleanClause.Occur.MUST);
		}
		if (StringUtils.isNotEmpty(value.getGroups())) {
			QueryParser queryParser = new QueryParser(Version.LUCENE_4_10_2, FIELD_LABEL_GROUPS, analyzer);
			queryParser.setDefaultOperator(Operator.OR);
			Query query = queryParser.parse(value.getGroups());
			container.add(query, BooleanClause.Occur.MUST);
		}
		return container;
	}

	private String getHighlightedField(Query query, Analyzer analyzer, String fieldName, String fieldValue) throws IOException,
			InvalidTokenOffsetsException {
		Formatter formatter = new SimpleHTMLFormatter("<span class=\"mark\">", "</span>");
		//Formatter formatter = new SimpleHTMLFormatter();
		QueryScorer queryScorer = new QueryScorer(query);
		Highlighter highlighter = new Highlighter(formatter, queryScorer);
		highlighter.setTextFragmenter(new SimpleSpanFragmenter(queryScorer, CONTENTS_LIMIT_LENGTH));
		highlighter.setMaxDocCharsToAnalyze(Integer.MAX_VALUE);
		return highlighter.getBestFragment(analyzer, fieldName, fieldValue);
	}

}
