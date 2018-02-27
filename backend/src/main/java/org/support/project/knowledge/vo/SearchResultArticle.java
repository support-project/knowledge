package org.support.project.knowledge.vo;

import java.util.ArrayList;
import java.util.List;

import org.support.project.knowledge.vo.api.Knowledge;

public class SearchResultArticle {
    private List<Knowledge> items = new ArrayList<>();
    private long total = 0;
    public List<Knowledge> getItems() {
        return items;
    }
    public void setItems(List<Knowledge> items) {
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
