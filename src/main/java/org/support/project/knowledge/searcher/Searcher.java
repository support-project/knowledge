package org.support.project.knowledge.searcher;

import java.util.List;

import org.support.project.di.DI;
import org.support.project.knowledge.searcher.impl.LuceneSearcher;

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
