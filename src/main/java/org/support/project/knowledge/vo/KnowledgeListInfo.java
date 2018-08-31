package org.support.project.knowledge.vo;

import java.util.List;

import org.support.project.knowledge.entity.KnowledgesEntity;

public class KnowledgeListInfo {

    /** ナレッジの検索結果総件数（Lucene ベース） */
    private int searchResultTotalCount;
    
    /** ナレッジのリスト */
    private List<KnowledgesEntity> knowledgesEntityList;
    
    public KnowledgeListInfo(int searchResultTotalCount, List<KnowledgesEntity> knowledgesEntityList) {
        this.searchResultTotalCount = searchResultTotalCount;
        this.knowledgesEntityList = knowledgesEntityList;
    }
    
    public int getSearchResultTotalCount() {
        return this.searchResultTotalCount;
    }
    
    public List<KnowledgesEntity> getKnowledgesEntityList() {
        return this.knowledgesEntityList;
    }
}
