package org.support.project.knowledge.searcher;

import org.junit.Before;
import org.junit.Test;
import org.support.project.common.config.ConfigLoader;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.DateUtils;
import org.support.project.common.util.FileUtil;
import org.support.project.common.util.PropertyUtil;
import org.support.project.common.util.RandomUtil;
import org.support.project.di.Container;
import org.support.project.knowledge.config.AppConfig;
import org.support.project.knowledge.indexer.Indexer;
import org.support.project.knowledge.indexer.IndexingValue;
import org.support.project.knowledge.logic.TemplateLogic;

import java.io.File;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class SearchConditionTest {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(SearchConditionTest.class);

    @Before
    public void setUp() throws Exception {
        // テスト毎にインデックスを初期化する
        AppConfig appConfig = ConfigLoader.load(AppConfig.APP_CONFIG, AppConfig.class);
        File f = new File(appConfig.getIndexPath());
        FileUtil.delete(f);
        LOG.info("インデックスを削除しました");
    }

    /**
     * インデックスにアイテム追加
     * @param title タイトル
     * @param contents コンテンツ
     * @param type 種類（記事、添付ファイル、コメントなど）
     * @param template テンプレート
     * @param creator 作成者
     * @param updateDateTime 更新日時
     * @param tagIds タグのID
     * @param viewUsers 閲覧可能ユーザ
     * @param viewGroups 閲覧可能グループ
     * @return 登録した情報のID
     */
    private String addItem(String title, String contents, int type, int template, int creator, Date updateDateTime,
                           int[] tagIds, int[] viewUsers, int[] viewGroups) throws Exception {
        String id = String.valueOf(RandomUtil.randamNum(0, 999999));
        IndexingValue indexingValue = new IndexingValue();
        indexingValue.setType(type);
        indexingValue.setId(id);
        indexingValue.setTitle(title);
        indexingValue.setContents(contents);
        indexingValue.setTemplate(template);
        indexingValue.setCreator(creator);
        indexingValue.setTime(updateDateTime.getTime());

        if (tagIds != null) {
            for (int tagId : tagIds) {
                indexingValue.addTag(tagId);
            }
        }
        if (viewUsers != null) {
            for (int user : viewUsers) {
                indexingValue.addUser(user);
            }
        }
        if (viewGroups != null) {
            for (int group : viewGroups) {
                indexingValue.addGroup(group);
            }
        }
        Indexer indexer = Container.getComp(Indexer.class);
        indexer.writeIndex(indexingValue);
        return id;
    }

    @Test
    public void testTemplateFilter() throws Exception {
        String id1 = addItem("title:testTemplateFilter", "contents:testTemplateFilter", 0, TemplateLogic.TYPE_ID_KNOWLEDGE,
                100, DateUtils.now(), null, null, null);
        String id2 = addItem("title:testTemplateFilter", "contents:testTemplateFilter", 0, TemplateLogic.TYPE_ID_EVENT,
                100, DateUtils.now(), null, null, null);
        String id3 = addItem("title:testTemplateFilter", "contents:testTemplateFilter", 0, TemplateLogic.TYPE_ID_BOOKMARK,
                100, DateUtils.now(), null, null, null);

        Searcher searcher = Container.getComp(Searcher.class);

        SearchingValue searchingValue = new SearchingValue();
        searchingValue.addTemplate(TemplateLogic.TYPE_ID_KNOWLEDGE);
        List<SearchResultValue> results = searcher.search(searchingValue, 1);
        for (SearchResultValue searchResultValue : results) {
            LOG.info(PropertyUtil.reflectionToString(searchResultValue));
        }
        assertEquals(1, results.size());
        SearchResultValue result = results.get(0);
        assertEquals(id1, result.getId());

        searchingValue.addTemplate(TemplateLogic.TYPE_ID_EVENT);
        results = searcher.search(searchingValue, 1);
        for (SearchResultValue searchResultValue : results) {
            LOG.info(PropertyUtil.reflectionToString(searchResultValue));
        }
        assertEquals(2, results.size());
    }

    @Test
    public void testSearchCreator() throws Exception {
        String id1 = addItem("title:testTemplateFilter", "contents:testTemplateFilter", 0, TemplateLogic.TYPE_ID_KNOWLEDGE,
                100, DateUtils.now(), null, null, null);
        String id2 = addItem("title:testTemplateFilter", "contents:testTemplateFilter", 0, TemplateLogic.TYPE_ID_EVENT,
                101, DateUtils.now(), null, null, null);
        String id3 = addItem("title:testTemplateFilter", "contents:testTemplateFilter", 0, TemplateLogic.TYPE_ID_BOOKMARK,
                102, DateUtils.now(), null, null, null);

        Searcher searcher = Container.getComp(Searcher.class);

        SearchingValue searchingValue = new SearchingValue();
        searchingValue.addCreator(100);
        List<SearchResultValue> results = searcher.search(searchingValue, 1);
        for (SearchResultValue searchResultValue : results) {
            LOG.info(PropertyUtil.reflectionToString(searchResultValue));
        }
        assertEquals(1, results.size());
        SearchResultValue result = results.get(0);
        assertEquals(id1, result.getId());

    }

}
