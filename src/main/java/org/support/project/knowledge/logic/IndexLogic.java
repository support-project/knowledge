package org.support.project.knowledge.logic;

import java.util.List;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.indexer.Indexer;
import org.support.project.knowledge.indexer.IndexingValue;
import org.support.project.knowledge.searcher.SearchResultValue;
import org.support.project.knowledge.searcher.Searcher;
import org.support.project.knowledge.searcher.SearchingValue;

import net.arnx.jsonic.JSON;

/**
 * 全文検索インデックスを使った処理
 * 
 * @author Koda
 *
 */
@DI(instance = Instance.Singleton)
public class IndexLogic {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(IndexLogic.class);

    public static IndexLogic get() {
        return Container.getComp(IndexLogic.class);
    }
    
    private String indexerType = "LuceneIndexer";
    private String searcherType = "LuceneSearcher";
    public Indexer getIndexer() {
        Indexer indexer = Container.getComp(indexerType, Indexer.class);
        return indexer;
    }
    public Searcher getSearcher() {
        Searcher searcher = Container.getComp(searcherType, Searcher.class);
        return searcher;
    }
    

    /**
     * 全文検索エンジンへ登録
     * 
     * @param indexingValue
     * @throws Exception
     */
    public void save(IndexingValue indexingValue) throws Exception {
        Indexer indexer = getIndexer();
        indexer.writeIndex(indexingValue);
    }

    /**
     * 全文検索エンジンから検索
     * 
     * @param search
     * @return
     * @throws Exception
     */
    public List<SearchResultValue> search(SearchingValue search, int keywordSortType) throws Exception {
        Searcher searcher = getSearcher();
        List<SearchResultValue> list = searcher.search(search, keywordSortType);
        if (LOG.isDebugEnabled()) {
            LOG.debug(JSON.encode(list, true));
        }
        return list;
    }

    /**
     * 全文検索エンジンから削除
     * 
     * @param knowledgeId
     * @throws Exception
     */
    public void delete(Long knowledgeId) throws Exception {
        delete(String.valueOf(knowledgeId));
    }

    /**
     * 全文検索エンジンから削除
     * 
     * @param knowledgeId
     * @throws Exception
     */
    public void delete(String id) throws Exception {
        Indexer indexer = getIndexer();
        indexer.deleteItem(id);
    }

    /**
     * 全文検索エンジンから、ユーザが登録したナレッジを全て削除
     * 
     * @param loginUserId
     * @throws Exception
     */
    public void deleteOnUser(Integer loginUserId) throws Exception {
        Indexer indexer = getIndexer();
        indexer.deleteOnCreator(loginUserId);
    }

}
