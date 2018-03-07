package org.support.project.knowledge.vo;

/**
 * ストック情報 画面とやり取りするJSONの型
 * 
 * @author Koda
 *
 */
public class Stock {
    /** STOCK ID */
    private Long stockId;
    /** STOCK 名 */
    private String stockName;
    /** 区分 */
    private Integer stockType;
    /** 説明 */
    private String description;
    /**
     * ストックされているかの情報を画面とやりとりするためのフラグ
     */
    private Boolean stocked = false;
    /**
     * コメントを付加した際のコメント
     */
    private String comment = "";

    /**
     * @return the stockId
     */
    public Long getStockId() {
        return stockId;
    }

    /**
     * @param stockId the stockId to set
     */
    public void setStockId(Long stockId) {
        this.stockId = stockId;
    }

    /**
     * @return the stockName
     */
    public String getStockName() {
        return stockName;
    }

    /**
     * @param stockName the stockName to set
     */
    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    /**
     * @return the stockType
     */
    public Integer getStockType() {
        return stockType;
    }

    /**
     * @param stockType the stockType to set
     */
    public void setStockType(Integer stockType) {
        this.stockType = stockType;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the stocked
     */
    public Boolean getStocked() {
        return stocked;
    }

    /**
     * @param stocked the stocked to set
     */
    public void setStocked(Boolean stocked) {
        this.stocked = stocked;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
