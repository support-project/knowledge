package org.support.project.web.bean;

import java.io.InputStream;

public class DownloadInfo {
    private String fileName;
    private InputStream inputStream;
    private long size;
    private String contentType;
    /**
     * Get fileName
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }
    /**
     * Set fileName
     * @param fileName the fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    /**
     * Get inputStream
     * @return the inputStream
     */
    public InputStream getInputStream() {
        return inputStream;
    }
    /**
     * Set inputStream
     * @param inputStream the inputStream to set
     */
    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }
    /**
     * Get size
     * @return the size
     */
    public long getSize() {
        return size;
    }
    /**
     * Set size
     * @param size the size to set
     */
    public void setSize(long size) {
        this.size = size;
    }
    /**
     * Get contentType
     * @return the contentType
     */
    public String getContentType() {
        return contentType;
    }
    /**
     * Set contentType
     * @param contentType the contentType to set
     */
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}
