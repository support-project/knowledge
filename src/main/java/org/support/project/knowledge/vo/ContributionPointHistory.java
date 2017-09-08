package org.support.project.knowledge.vo;

/**
 * ContributionPoint の獲得履歴を日付毎に保持するクラス
 * @author koda
 */
public class ContributionPointHistory {
    private String ymd;
    private int total;
    private int before;
    /**
     * @return the ymd
     */
    public String getYmd() {
        return ymd;
    }
    /**
     * @param ymd the ymd to set
     */
    public void setYmd(String ymd) {
        this.ymd = ymd;
    }
    /**
     * @return the total
     */
    public int getTotal() {
        return total;
    }
    /**
     * @param total the total to set
     */
    public void setTotal(int total) {
        this.total = total;
    }
    /**
     * @return the before
     */
    public int getBefore() {
        return before;
    }
    /**
     * @param before the before to set
     */
    public void setBefore(int before) {
        this.before = before;
    }
    
}
