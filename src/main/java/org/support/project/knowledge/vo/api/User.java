package org.support.project.knowledge.vo.api;

/**
 * APIで返すユーザ情報
 * @author koda
 */
public class User {
    /** ユーザID */
    private Integer userId;
    /** ユーザ名 */
    private String userName;
    /** ナレッジの件数 */
    private int knowledgeCount;
    /** イイネの件数 */
    private int likeCount;
    /** ストックの件数 */
    private int stockCount;
    /**
     * @return the userId
     */
    public Integer getUserId() {
        return userId;
    }
    /**
     * @param userId the userId to set
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }
    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }
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
