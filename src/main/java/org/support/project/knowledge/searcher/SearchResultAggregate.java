package org.support.project.knowledge.searcher;

import java.util.List;

public class SearchResultAggregate {

    /** 検索結果の総件数 */
    private int totalResultCount;
    
    /** 検索条件に一致した Lucene 検索結果のリスト */
    private List<SearchResultValue> resultList;
    
    /**
     * コンストラクタ
     * @param totalResultCount 検索結果の総件数
     * @param resultList 検索条件に一致した Lucene 検索結果のリスト
     */
    public SearchResultAggregate(int totalResultCount, List<SearchResultValue> resultList) {
        this.totalResultCount = totalResultCount;
        this.resultList = resultList;
    }

    /**
     * 検索結果の総件数を取得します。
     * @return 検索結果の総件数
     */
    public int getTotalResultCount() {
        return this.totalResultCount;
    }
    
    /**
     * 検索条件に一致した Lucene 検索結果のリストを取得します。
     * @return 検索条件に一致した Lucene 検索結果のリスト
     */
    public List<SearchResultValue> getResultList() {
        return this.resultList;
    }
}
