package redcomet.knowledge.logic;

import java.util.List;

import net.arnx.jsonic.JSON;
import redcomet.common.log.Log;
import redcomet.common.log.LogFactory;
import redcomet.di.Container;
import redcomet.knowledge.indexer.Indexer;
import redcomet.knowledge.indexer.IndexingValue;
import redcomet.knowledge.searcher.SearchResultValue;
import redcomet.knowledge.searcher.Searcher;
import redcomet.knowledge.searcher.SearchingValue;

/**
 * 全文検索インデックスを使った処理
 * @author Koda
 *
 */
public class IndexLogic {
	/** ログ */
	private static Log LOG = LogFactory.getLog(IndexLogic.class);

	public static IndexLogic get() {
		return Container.getComp(IndexLogic.class);
	}
	
	/**
	 * 全文検索エンジンへ登録
	 * @param indexingValue
	 * @throws Exception
	 */
	public void save(IndexingValue indexingValue) throws Exception {
		Indexer indexer = Container.getComp(Indexer.class);
		indexer.writeIndex(indexingValue);
	}
	
	/**
	 * 全文検索エンジンから検索
	 * @param search
	 * @return
	 * @throws Exception
	 */
	public List<SearchResultValue> search(SearchingValue search) throws Exception {
		Searcher searcher = Container.getComp(Searcher.class);
		List<SearchResultValue> list = searcher.search(search);
		if (LOG.isDebugEnabled()) {
			LOG.debug(JSON.encode(list, true));
		}
		return list;
	}
	
	/**
	 * 全文検索エンジンから削除
	 * @param knowledgeId
	 * @throws Exception 
	 */
	public void delete(Long knowledgeId) throws Exception {
		Indexer indexer = Container.getComp(Indexer.class);
		indexer.deleteItem(String.valueOf(knowledgeId));
	}
	
	/**
	 * 全文検索エンジンから、ユーザが登録したナレッジを全て削除
	 * @param loginUserId
	 * @throws Exception
	 */
	public void deleteOnUser(Integer loginUserId) throws Exception {
		Indexer indexer = Container.getComp(Indexer.class);
		indexer.deleteOnCreator(loginUserId);
	}
	
	
}
