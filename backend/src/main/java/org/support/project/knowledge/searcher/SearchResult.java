package org.support.project.knowledge.searcher;

import java.util.ArrayList;
import java.util.List;

public class SearchResult {
    
    private List<SearchResultValue> items = new ArrayList<>();
    private long total = 0;
    
    public List<SearchResultValue> getItems() {
        return items;
    }
    public long getTotal() {
        return total;
    }
    public void setTotal(long total) {
        this.total = total;
    }
    
}
