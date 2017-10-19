package org.support.project.web.exception;

/**
 * CallControlLogic で何らかのエラーになった際に、送信するHttpStatusを保持するException
 * @author koda
 */
public class CallControlException extends Exception {
    /** SerialVersion */
    private static final long serialVersionUID = 1L;
    /** Http Status */
    private int httpStatus;
    /** Http Method */
    private String method;
    /** Request path */
    private String path;
    /** Message */
    private String msg;
    
    /**
     * コンストラクタ
     * @param httpStatus Http Status
     */
    public CallControlException(int httpStatus, String method, String path, String msg) {
        super();
        this.httpStatus = httpStatus;
        this.method = method;
        this.path = path;
        this.msg = msg;
    }
    /**
     * @return the httpStatus
     */
    public int getHttpStatus() {
        return httpStatus;
    }
    /* (non-Javadoc)
     * @see java.lang.Throwable#getMessage()
     */
    @Override
    public String getMessage() {
        StringBuilder builder = new StringBuilder();
        builder.append(msg).append(" [Method]").append(method).append(" [Path]").append(path).append(" [ReturnStatus]").append(httpStatus);
        return builder.toString();
    }
}
