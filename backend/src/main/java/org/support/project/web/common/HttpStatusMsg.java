package org.support.project.web.common;

public final class HttpStatusMsg {
    
    public static String getMsg(int status) {
        switch (status) {
            case HttpStatus.SC_200_OK:
                return "OK";
            case HttpStatus.SC_201_CREATED:
                return "CREATED";
            case HttpStatus.SC_202_ACCEPTED:
                return "ACCEPTED";
            case HttpStatus.SC_400_BAD_REQUEST:
                return "BAD_REQUEST";
            case HttpStatus.SC_401_UNAUTHORIZED:
                return "UNAUTHORIZED";
            case HttpStatus.SC_403_FORBIDDEN:
                return "FORBIDDEN";
            case HttpStatus.SC_404_NOT_FOUND:
                return "NOT_FOUND";
            case HttpStatus.SC_500_INTERNAL_SERVER_ERROR:
                return "INTERNAL_SERVER_ERROR";
            case HttpStatus.SC_501_NOT_IMPLEMENTED:
                return "NOT_IMPLEMENTED";
            case HttpStatus.SC_503_SERVICE_UNAVAILABLE:
                return "SERVICE_UNAVAILABLE";
            default:
                return "";
        }
    }
    
}
