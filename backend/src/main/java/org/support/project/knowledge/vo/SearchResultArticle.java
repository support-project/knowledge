package org.support.project.knowledge.vo;

import java.util.ArrayList;
import java.util.List;

public class SearchResultArticle {
    private List<? extends ArticleKeyInterface> items = new ArrayList<>();
    private long total = 0;
    public List<? extends ArticleKeyInterface> getItems() {
        return items;
    }
    public void setItems(List<? extends ArticleKeyInterface> items) {
        if (items != null) {
            this.items = items;
        }
    }
    public long getTotal() {
        return total;
    }
    public void setTotal(long total) {
        this.total = total;
    }
}
