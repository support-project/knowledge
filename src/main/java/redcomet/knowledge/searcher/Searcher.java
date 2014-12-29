package redcomet.knowledge.searcher;

import java.util.List;

import redcomet.di.DI;
import redcomet.knowledge.searcher.impl.LuceneSearcher;

@DI(impl=LuceneSearcher.class)
public interface Searcher {

	/**
	 * 検索
	 * @param value
	 * @return
	 * @throws Exception
	 */
	List<SearchResultValue> search(SearchingValue value) throws Exception;
	
}
