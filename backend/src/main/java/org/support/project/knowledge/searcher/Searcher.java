package org.support.project.knowledge.searcher;

import org.support.project.di.DI;
import org.support.project.knowledge.searcher.impl.LuceneSearcher;

@DI(impl = LuceneSearcher.class)
public interface Searcher {

    /**
     * 検索
     * 
     * @param value
     * @param keywordSortType
     * @return
     * @throws Exception
     */
    SearchResult search(SearchingValue value, int keywordSortType) throws Exception;

}
