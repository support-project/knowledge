package org.support.project.common.exception;

public class ExceptionHandler {

    /**
     * Search exception for root causes
     * @param e Throwable
     * @param type search type
     * @param <T> type
     * @return exception
     */
    public static <T> T exceptionSearch(Throwable e, Class<? extends Exception> type) {
        if (type.isAssignableFrom(e.getClass())) {
            return (T) e;
        }
        if (e.getCause() == null) {
            return null;
        }
        return exceptionSearch(e.getCause(), type);
    }
}
