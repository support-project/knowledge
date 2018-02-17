package org.support.project.knowledge.vo;

import java.util.ArrayList;
import java.util.List;

import org.support.project.knowledge.entity.KnowledgesEntity;

public class SearchResultKnowledge {
    private List<KnowledgesEntity> items = new ArrayList<>();
    private long total = 0;
    public List<KnowledgesEntity> getItems() {
        return items;
    }
    public void setItems(List<KnowledgesEntity> items) {
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
