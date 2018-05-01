package org.support.project.web.common;

/**
 * HTTPステータスコード
 * @author Koda
 */
public class HttpStatus {
    /* 1xxレスポンス：情報提供 */
    /** 100 Continue */
    public static final int SC_100_CONTINUE = 100;
    /** 101 Switching Protocols */
    public static final int SC_101_SWITCHING_PROTOCOLS = 101;
    /** 102 Processing */
    public static final int SC_102_PROCESSING = 102;
    
    /* 2xxレスポンス：成功 */
    /** 200 OK */
    public static final int SC_200_OK = 200;
    /** 201 Created */
    public static final int SC_201_CREATED = 201;
    /** 202 Accepted */
    public static final int SC_202_ACCEPTED = 202;
    /** 203 Non-Authoritative Information */
    public static final int SC_203_NON_AUTHORITATIVE_INFORMATION = 203;
    /** 204 No Content */
    public static final int SC_204_NO_CONTENT = 204;
    /** 205 Reset Content */
    public static final int SC_205_RESET_CONTENT = 205;
    /** 206 Partial Content */
    public static final int SC_206_PARTIAL_CONTENT = 206;
    /** 207 Multi-Status */
    public static final int SC_207_MULTI_STATUS = 207;
    /** 208 Already Reported */
    public static final int SC_208_ALREADY_REPORTED = 208;
    /** 226 IM Used */
    public static final int SC_226_IM_USED = 226;
    
    /* 3xxレスポンス：転送  */
    /** 300 Multiple Choices */
    public static final int SC_300_MULTIPLE_CHOICES = 300;
    /** 301 Moved Permanently */
    public static final int SC_301_MOVED_PERMANENTLY = 301;
    /** 302 Found */
    public static final int SC_302_FOUND = 302;
    /** 303 See Other */
    public static final int SC_303_SEE_OTHER = 303;
    /** 304 Not Modified */
    public static final int SC_304_NOT_MODIFIED = 304;
    /** 305 Use Proxy */
    public static final int SC_305_USE_PROXY = 305;
    /** 306 (Unused) */
    public static final int SC_306_UNUSED = 306;
    /** 307 Temporary Redirect */
    public static final int SC_307_TEMPORARY_REDIRECT = 307;
    /** 308 Permanent Redirect */
    public static final int SC_308_PERMANENT_REDIRECT = 308;
    
    /* 4xxレスポンス：クライアントエラー */
    /** 400 Bad Request */
    public static final int SC_400_BAD_REQUEST = 400;
    /** 401 Unauthorized */
    public static final int SC_401_UNAUTHORIZED = 401;
    /** 402 Payment Required */
    public static final int SC_402_PAYMENT_REQUIRED = 402;
    /** 403 Forbidden */
    public static final int SC_403_FORBIDDEN = 403;
    /** 404 Not Found */
    public static final int SC_404_NOT_FOUND = 404;
    
    
    /*
     いったん良く使うものだけ定義しておく
     405 Method Not Allowed
     406 Not Acceptable
     407 Proxy Authentication Required
     408 Request Timeout
     409 Conflict
     410 Gone
     411 Length Required
     412 Precondition Failed
     413 Request Entity Too Large
     414 Request-URI Too Long
     415 Unsupported Media Type
     416 Requested Range Not Satisfiable
     417 Expectation Failed
     418
     420, 421
     422 Unprocessable Entity
     423 Locked
     424 Failed Dependency
     425 (Reserved for WebDAV advanced collections expired proposal)
     426 Upgrade Required
     428 Precondition Required
     429 Too Many Requests
     431 Request Header Fields Too Large
     451 Unavailable For Legal Reasons
     
     
     501 Not Implemented
     502 Bad Gateway
     503 Service Unavailable
     504 Gateway Timeout
     505 HTTP Version Not Supported
     506 Variant Also Negotiates
     507 Insufficient Storage
     508 Loop Detected
     510 Not Extended
     511 Network Authentication Required
     */
    
    
    /* 5xxレスポンス：サーバエラー */
    /** 500 Internal Server Error */
    public static final int SC_500_INTERNAL_SERVER_ERROR = 500;
    /** 501 Not Implemented */
    public static final int SC_501_NOT_IMPLEMENTED = 501;
    /** 503 Service Unavailable */
    public static final int SC_503_SERVICE_UNAVAILABLE = 503;

}
