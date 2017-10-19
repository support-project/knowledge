package org.support.project.web.bean;

import java.util.List;

/**
 * Send list for JSON
 * @author Koda
 */
public class SendList {
    /**
     * items
     */
    private List<?> items;
    /**
     * offset
     */
    private int offset;
    /**
     * limit
     */
    private int limit;
    /**
     * total
     */
    private long total;
    /**
     * Get items
     * @return the items
     */
    public List<?> getItems() {
        return items;
    }
    /**
     * Set items
     * @param items the items to set
     */
    public void setItems(List<?> items) {
        this.items = items;
    }
    /**
     * Get offset
     * @return the offset
     */
    public int getOffset() {
        return offset;
    }
    /**
     * Set offset
     * @param offset the offset to set
     */
    public void setOffset(int offset) {
        this.offset = offset;
    }
    /**
     * Get limit
     * @return the limit
     */
    public int getLimit() {
        return limit;
    }
    /**
     * Set limit
     * @param limit the limit to set
     */
    public void setLimit(int limit) {
        this.limit = limit;
    }
    /**
     * Get total
     * @return the total
     */
    public long getTotal() {
        return total;
    }
    /**
     * Set total
     * @param total the total to set
     */
    public void setTotal(long total) {
        this.total = total;
    }
    
    
}
