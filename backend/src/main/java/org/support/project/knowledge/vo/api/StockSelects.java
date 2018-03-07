package org.support.project.knowledge.vo.api;

import java.util.ArrayList;
import java.util.List;

public class StockSelects {
    /** 選択可能なストックIDのリスト */
    private List<Long> stockIds = new ArrayList<>();
    /** 選択しているストックIDのリスト */
    private List<StockSelect> selectedItems = new ArrayList<>();
    public List<Long> getStockIds() {
        return stockIds;
    }
    public void setStockIds(List<Long> stockIds) {
        this.stockIds = stockIds;
    }
    public List<StockSelect> getSelectedItems() {
        return selectedItems;
    }
    public void setSelectedItems(List<StockSelect> selectedItems) {
        this.selectedItems = selectedItems;
    }

}
