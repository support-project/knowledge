package org.support.project.di.exception;

import org.support.project.common.config.Resources;
import org.support.project.common.exception.SystemException;
import org.support.project.di.config.DIParameter;

/**
 * DI操作で発生するException
 */
public class DIException extends SystemException {
    /**
     * シリアルバージョン
     */
    private static final long serialVersionUID = 1L;

    /**
     * メッセージ取得のリソースファイル
     * 
     * @return message resource
     */
    protected Resources getResources() {
        return Resources.getInstance(DIParameter.DI_RESOURCE);
    }

    /**
     * Exception
     * 
     * @param key
     *            key
     * @param params
     *            params
     */
    public DIException(String key, String... params) {
        super(key, params);
    }

    /**
     * Exception
     * 
     * @param key
     *            key
     * @param params
     *            params
     * @param cause
     *            cause
     */
    public DIException(String key, Throwable cause, String... params) {
        super(key, cause, params);
    }

    /**
     * Exception
     * 
     * @param key
     *            key
     * @param cause
     *            cause
     */
    public DIException(String key, Throwable cause) {
        super(key, cause);
    }

    /**
     * Exception
     * 
     * @param key
     *            key
     */
    public DIException(String key) {
        super(key);
    }

    /**
     * Exception
     * 
     * @param cause
     *            cause
     */
    public DIException(Throwable cause) {
        super(cause);
    }

}
