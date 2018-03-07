package org.support.project.knowledge.vo.api;

public class StockSelect {
    private long stockId;
    /** ストックする際の備考（記事についてのコメントなど） */
    private String comment = "";
    public long getStockId() {
        return stockId;
    }
    public void setStockId(long stockId) {
        this.stockId = stockId;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }

}
