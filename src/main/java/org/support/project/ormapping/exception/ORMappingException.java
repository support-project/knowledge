package org.support.project.ormapping.exception;

import org.support.project.common.config.Resources;
import org.support.project.common.exception.SystemException;
import org.support.project.common.util.StringUtils;
import org.support.project.ormapping.config.ORMappingParameter;

/**
 * ORMappingException
 * 
 * @author Koda
 */
public class ORMappingException extends SystemException {
    /** sql of error. */
    private String sql = null;
    /** parameters of sql */
    private Object[] params = null;

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
        return Resources.getInstance(ORMappingParameter.OR_MAPPING_RESOURCE);
    }

    /**
     * Exception
     * 
     * @param key
     *            key
     * @param params
     *            params
     */
    public ORMappingException(String key, String... params) {
        super(key, params);
    }

    /**
     * Exception
     * 
     * @param key
     *            key
     * @param cause
     *            cause
     * @param params
     *            params
     */
    public ORMappingException(String key, Throwable cause, String... params) {
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
    public ORMappingException(String key, Throwable cause) {
        super(key, cause);
    }

    /**
     * Exception
     * 
     * @param key
     *            key
     */
    public ORMappingException(String key) {
        super(key);
    }

    /**
     * Exception
     * 
     * @param cause
     *            cause
     */
    public ORMappingException(Throwable cause) {
        super(cause);
    }
    
    /**
     * get sql
     * @return sql
     */
    public String getSql() {
        return sql;
    }
    /**
     * set sql
     * @param sql sql
     */
    public void setSql(String sql) {
        this.sql = sql;
    }
    /**
     * get parameters
     * @return parameters
     */
    public Object[] getParams() {
        return params;
    }
    /**
     * set parameters
     * @param params parameters
     */
    public void setParams(Object[] params) {
        this.params = params;
    }
    /**
     * debug of sql
     * @param append sql
     * @return string of debug
     */
    private String sqlDebug(String append) {
        StringBuilder builder = new StringBuilder();
        builder.append(append);
        builder.append("\n----- SQL Infomation -----\n");
        if (StringUtils.isNotEmpty(sql)) {
            builder.append("[sql]").append(sql).append("\n");
        }
        if (params != null) {
            builder.append("[params]").append("\n");
            for (int i = 0; i < params.length; i++) {
                Object object = params[i];
                builder.append("\t[params][").append(i).append("]").append(String.valueOf(object));
                builder.append("\n");
            }
        }
        if (StringUtils.isNotEmpty(sql)) {
            String execute = sql;
            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    try {
                        Object object = params[i];
                        execute = execute.replaceFirst("\\?", String.valueOf(object));
                    } catch (Exception e) {
                    }
                }
            }
            builder.append("[execute]").append(execute).append("\n");
        }

        return builder.toString();
    }

    @Override
    public String getMessage() {
        return sqlDebug(super.getMessage());
    }

    @Override
    public String getLocalizedMessage() {
        return sqlDebug(super.getLocalizedMessage());
    }

}
