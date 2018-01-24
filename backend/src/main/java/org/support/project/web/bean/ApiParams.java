package org.support.project.web.bean;

public class ApiParams {
    private int limit = 10;
    private int offset = 0;
    /**
     * @return the limit
     */
    public int getLimit() {
        return limit;
    }
    /**
     * @param limit the limit to set
     */
    public void setLimit(int limit) {
        this.limit = limit;
    }
    /**
     * @return the offset
     */
    public int getOffset() {
        return offset;
    }
    /**
     * @param offset the offset to set
     */
    public void setOffset(int offset) {
        this.offset = offset;
    }
    
    
}
