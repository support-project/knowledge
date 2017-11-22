package org.support.project.knowledge.searcher;

import java.util.List;

import org.support.project.di.DI;
import org.support.project.knowledge.searcher.impl.ElasticsearchSearcher;
import org.support.project.knowledge.searcher.impl.LuceneSearcher;

@DI(keys= {"LuceneSearcher","ElasticsearchSearcher"}, impls= {LuceneSearcher.class, ElasticsearchSearcher.class})
public interface Searcher {

    /**
     * 検索
     * 
     * @param value
     * @param keywordSortType
     * @return
     * @throws Exception
     */
    List<SearchResultValue> search(SearchingValue value, int keywordSortType) throws Exception;

}
