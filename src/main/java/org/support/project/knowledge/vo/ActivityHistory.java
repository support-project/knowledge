package org.support.project.knowledge.vo;

import java.util.Date;

public class ActivityHistory {
    private Date date;
    private String dispDate;
    private String msg;
    /**
     * @return the date
     */
    public Date getDate() {
        return date;
    }
    /**
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = date;
    }
    /**
     * @return the dispDate
     */
    public String getDispDate() {
        return dispDate;
    }
    /**
     * @param dispDate the dispDate to set
     */
    public void setDispDate(String dispDate) {
        this.dispDate = dispDate;
    }
    /**
     * @return the msg
     */
    public String getMsg() {
        return msg;
    }
    /**
     * @param msg the msg to set
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }
    
}
