package org.support.project.knowledge.searcher;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.support.project.common.config.ConfigLoader;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.FileUtil;
import org.support.project.common.util.PropertyUtil;
import org.support.project.common.util.RandomUtil;
import org.support.project.di.Container;
import org.support.project.knowledge.config.AppConfig;
import org.support.project.knowledge.indexer.Indexer;
import org.support.project.knowledge.indexer.IndexingValue;
import org.support.project.knowledge.searcher.SearchResultValue;
import org.support.project.knowledge.searcher.Searcher;
import org.support.project.knowledge.searcher.SearchingValue;

public class SearchTest {
	/** ログ */
	private static Log LOG = LogFactory.getLog(SearchTest.class);

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		AppConfig appConfig = ConfigLoader.load(AppConfig.APP_CONFIG, AppConfig.class);
		File f = new File(appConfig.getIndexPath());
		FileUtil.delete(f);
		LOG.info("インデックスを削除しました");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSearch1() throws Exception {
		String title = "[1]Lucene";
		String contents = "Lucene（ルシーン）は、Javaで記述された全文検索ソフトウェアである。あらかじめ蓄積した大量のデータから、指定したキーワードを探し出す機能を持つ。Javaのクラスライブラリとして提供される。";
		
		IndexingValue indexingValue = new IndexingValue();
		indexingValue.setType(0);
		indexingValue.setId(String.valueOf(RandomUtil.randamNum(0, 10000)));
		indexingValue.setTitle(title);
		indexingValue.setContents(contents);
		indexingValue.setCreator(100);
		indexingValue.setTime(new Date().getTime());
		
		Indexer indexer = Container.getComp(Indexer.class);
		indexer.writeIndex(indexingValue);
		
		SearchingValue searchingValue = new SearchingValue();
		searchingValue.setKeyword("Lucene");
		Searcher searcher = Container.getComp(Searcher.class);
		List<SearchResultValue> results = searcher.search(searchingValue);
		
		for (SearchResultValue searchResultValue : results) {
			LOG.info(PropertyUtil.reflectionToString(searchResultValue));
		}
		
		assertEquals(1, results.size());
		SearchResultValue result = results.get(0);
		assertEquals(title, result.getTitle());
		assertEquals(contents, result.getContents());
		
		title = "[2]るしーん";
		contents = "Lucene（ルシーン）は、Javaで記述された全文検索ソフトウェアである。";
		indexingValue = new IndexingValue();
		indexingValue.setType(0);
		indexingValue.setId(String.valueOf(RandomUtil.randamNum(0, 10000)));
		indexingValue.setTitle(title);
		indexingValue.setContents(contents);
		indexingValue.setCreator(100);
		indexingValue.setTime(new Date().getTime());

		indexer.writeIndex(indexingValue);

		
		results = searcher.search(searchingValue);
		for (SearchResultValue searchResultValue : results) {
			LOG.info(PropertyUtil.reflectionToString(searchResultValue));
		}
		
		assertEquals(2, results.size());
	}
	
	@Test
	public void testSearch2() throws Exception {
		String title = "[3]るしーん";
		String contents = "Luceneは、Javaで記述された全文検索ソフトウェアである。";
		
		IndexingValue indexingValue = new IndexingValue();
		indexingValue.setType(0);
		indexingValue.setId(String.valueOf(RandomUtil.randamNum(0, 10000)));
		indexingValue.setTitle(title);
		indexingValue.setContents(contents);
		indexingValue.addUser(0);
		indexingValue.setCreator(100);
		indexingValue.setTime(new Date().getTime());

		Indexer indexer = Container.getComp(Indexer.class);
		indexer.writeIndex(indexingValue);
		
		LOG.info("検索（ユーザで絞り込み）");
		SearchingValue searchingValue = new SearchingValue();
		searchingValue.setKeyword("Lucene");
		searchingValue.addUser(0);
		
		Searcher searcher = Container.getComp(Searcher.class);
		List<SearchResultValue> results = searcher.search(searchingValue);
		
		for (SearchResultValue searchResultValue : results) {
			LOG.info(PropertyUtil.reflectionToString(searchResultValue));
		}
		
		assertEquals(1, results.size());
		SearchResultValue result = results.get(0);
		assertEquals(title, result.getTitle());
		assertEquals(contents, result.getContents());
		
		LOG.info("再度検索（タグで絞り込み）");
		searchingValue = new SearchingValue();
		searchingValue.setKeyword("Lucene");
		searchingValue.addTag(0);
		results = searcher.search(searchingValue);
		assertEquals(0, results.size());
		
		
		LOG.info("再度検索（絞り込みなし）");
		searchingValue = new SearchingValue();
		searchingValue.setKeyword("Lucene");
		
		results = searcher.search(searchingValue);
		
		for (SearchResultValue searchResultValue : results) {
			LOG.info(PropertyUtil.reflectionToString(searchResultValue));
		}
		assertEquals(3, results.size());
		

		LOG.info("再度検索 limit/offset");
		searchingValue = new SearchingValue();
		searchingValue.setKeyword("Lucene");
		searchingValue.setOffset(2);
		searchingValue.setLimit(1);
		
		results = searcher.search(searchingValue);
		
		for (SearchResultValue searchResultValue : results) {
			LOG.info(PropertyUtil.reflectionToString(searchResultValue));
		}
		assertEquals(1, results.size());
		
		
	}
	
	

}
