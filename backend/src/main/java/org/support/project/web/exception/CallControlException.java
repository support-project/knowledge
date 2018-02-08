package org.support.project.web.exception;

/**
 * CallControlLogic で何らかのエラーになった際に、送信するHttpStatusを保持するException
 * @author koda
 */
public class CallControlException extends SendErrorException {
    /** SerialVersion */
    private static final long serialVersionUID = 1L;
    /** Http Method */
    private String method;
    /** Request path */
    private String path;
    
    /**
     * コンストラクタ
     * @param httpStatus Http Status
     */
    public CallControlException(int httpStatus, String method, String path, String msg) {
        super(httpStatus, msg);
        this.method = method;
        this.path = path;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Throwable#getMessage()
     */
    @Override
    public String getMessage() {
        StringBuilder builder = new StringBuilder();
        builder.append(getMsg()).append(" [Method]").append(method).append(" [Path]").append(path).append(" [ReturnStatus]").append(getHttpStatus());
        return builder.toString();
    }
}
