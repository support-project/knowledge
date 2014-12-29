package redcomet.knowledge.indexer;

import redcomet.di.DI;
import redcomet.knowledge.indexer.impl.LuceneIndexer;


@DI(impl=LuceneIndexer.class)
public interface Indexer {
	
	/**
	 * インデックス生成
	 * @param indexingValue
	 * @throws Exception
	 */
	void writeIndex(IndexingValue indexingValue) throws Exception;
	
	/**
	 * インデックスから削除
	 * @param entity
	 * @throws Exception
	 */
	void deleteItem(String id) throws Exception;
	
	/**
	 * ユーザが登録した内容を全て削除
	 * @param creator
	 * @throws Exception
	 */
	public void deleteOnCreator(Integer creator) throws Exception;
}
