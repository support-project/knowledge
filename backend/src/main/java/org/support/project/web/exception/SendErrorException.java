package org.support.project.web.exception;

public class SendErrorException extends Exception {
    /** SerialVersion */
    private static final long serialVersionUID = 1L;
    /** Http Status */
    private int httpStatus;
    /** Message */
    private String msg;
    
    
    public SendErrorException(int httpStatus, String msg) {
        super();
        this.httpStatus = httpStatus;
        this.msg = msg;
    }
    public int getHttpStatus() {
        return httpStatus;
    }
    public String getMsg() {
        return msg;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Throwable#getMessage()
     */
    @Override
    public String getMessage() {
        StringBuilder builder = new StringBuilder();
        builder.append(getMsg()).append(" [ReturnStatus]").append(getHttpStatus());
        return builder.toString();
    }
}
