package org.support.project.knowledge.vo.api;

public class UserDetail extends User {
    /** ナレッジの件数 */
    private int knowledgeCount;
    /** イイネの件数 */
    private int likeCount;
    /** ストックの件数 */
    private int stockCount;
    /**
     * @return the knowledgeCount
     */
    public int getKnowledgeCount() {
        return knowledgeCount;
    }
    /**
     * @param knowledgeCount the knowledgeCount to set
     */
    public void setKnowledgeCount(int knowledgeCount) {
        this.knowledgeCount = knowledgeCount;
    }
    /**
     * @return the likeCount
     */
    public int getLikeCount() {
        return likeCount;
    }
    /**
     * @param likeCount the likeCount to set
     */
    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }
    /**
     * @return the stockCount
     */
    public int getStockCount() {
        return stockCount;
    }
    /**
     * @param stockCount the stockCount to set
     */
    public void setStockCount(int stockCount) {
        this.stockCount = stockCount;
    }

}
