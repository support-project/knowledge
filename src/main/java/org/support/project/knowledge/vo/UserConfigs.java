package org.support.project.knowledge.vo;

import java.io.Serializable;
import java.util.Locale;

public class UserConfigs implements Serializable {
    /** serialVersion */
    private static final long serialVersionUID = 1L;
    private Locale locale = Locale.getDefault();
    /** タイムゾーン */
    private String timezone = "UTC";
    /** タイムゾーンオフセット（分） */
    private int timezoneOffset = 0;
    /** テーマ */
    private String thema;
    /** ハイライター */
    private int highlight;
    /**
     * @return the timezone
     */
    public String getTimezone() {
        return timezone;
    }
    /**
     * @param timezone the timezone to set
     */
    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }
    /**
     * @return the timezoneOffset
     */
    public int getTimezoneOffset() {
        return timezoneOffset;
    }
    /**
     * @param timezoneOffset the timezoneOffset to set
     */
    public void setTimezoneOffset(int timezoneOffset) {
        this.timezoneOffset = timezoneOffset;
    }
    /**
     * @return the thema
     */
    public String getThema() {
        return thema;
    }
    /**
     * @param thema the thema to set
     */
    public void setThema(String thema) {
        this.thema = thema;
    }
    /**
     * @return the highlight
     */
    public int getHighlight() {
        return highlight;
    }
    /**
     * @param highlight the highlight to set
     */
    public void setHighlight(int highlight) {
        this.highlight = highlight;
    }
    /**
     * @return the locale
     */
    public Locale getLocale() {
        return locale;
    }
    /**
     * @param locale the locale to set
     */
    public void setLocale(Locale locale) {
        this.locale = locale;
    }
    
    
    
}
