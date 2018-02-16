package org.support.project.knowledge.logic;

import java.lang.invoke.MethodHandles;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.indexer.Indexer;
import org.support.project.knowledge.indexer.IndexingValue;
import org.support.project.knowledge.searcher.SearchResult;
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
    private static final Log LOG = LogFactory.getLog(MethodHandles.lookup());

    public static IndexLogic get() {
        return Container.getComp(IndexLogic.class);
    }

    /**
     * 全文検索エンジンへ登録
     * 
     * @param indexingValue
     * @throws Exception
     */
    public void save(IndexingValue indexingValue) throws Exception {
        Indexer indexer = Container.getComp(Indexer.class);
        indexer.writeIndex(indexingValue);
    }

    /**
     * 全文検索エンジンから検索
     * 
     * @param search
     * @return
     * @throws Exception
     */
    public SearchResult search(SearchingValue search, int keywordSortType) throws Exception {
        Searcher searcher = Container.getComp(Searcher.class);
        SearchResult result = searcher.search(search, keywordSortType);
        if (LOG.isDebugEnabled()) {
            LOG.debug(JSON.encode(result, true));
        }
        return result;
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
        Indexer indexer = Container.getComp(Indexer.class);
        indexer.deleteItem(id);
    }

    /**
     * 全文検索エンジンから、ユーザが登録したナレッジを全て削除
     * 
     * @param loginUserId
     * @throws Exception
     */
    public void deleteOnUser(Integer loginUserId) throws Exception {
        Indexer indexer = Container.getComp(Indexer.class);
        indexer.deleteOnCreator(loginUserId);
    }

}
