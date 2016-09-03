package org.support.project.knowledge.vo;

import java.util.List;

public class SlideInfo {
    
    /** パース結果 */
    private Integer parseStatus;
    /** スライドショーの画像のリスト */
    private List<String> files;
    
    /**
     * Get parseStatus
     * @return the parseStatus
     */
    public Integer getParseStatus() {
        return parseStatus;
    }
    /**
     * Set parseStatus
     * @param parseStatus the parseStatus to set
     */
    public void setParseStatus(Integer parseStatus) {
        this.parseStatus = parseStatus;
    }
    /**
     * Get files
     * @return the files
     */
    public List<String> getFiles() {
        return files;
    }
    /**
     * Set files
     * @param files the files to set
     */
    public void setFiles(List<String> files) {
        this.files = files;
    }
}
