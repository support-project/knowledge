package org.support.project.web.wrapper;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public class HttpServletResponseWrapper extends javax.servlet.http.HttpServletResponseWrapper implements HttpServletResponse {

    /** ラップするHttpServletResponseオブジェクト */
    private HttpServletResponse response_;

    private boolean sended = false;

    /**
     * コンストラクタ
     * 
     * @param response
     *            HttpServletResponse
     */
    public HttpServletResponseWrapper(final HttpServletResponse response) {
        super(response);
        this.response_ = response;
    }

    public void sendError(int arg0, String arg1) throws IOException {
        response_.sendError(arg0, arg1);
        sended = true;
    }

    public void sendError(int arg0) throws IOException {
        response_.sendError(arg0);
        sended = true;
    }

    public boolean isCommitted() {
        if (sended) {
            return true;
        }
        return response_.isCommitted();
    }

    public void addCookie(Cookie arg0) {
        response_.addCookie(arg0);
    }

    public void addDateHeader(String arg0, long arg1) {
        response_.addDateHeader(arg0, arg1);
    }

    public void addHeader(String arg0, String arg1) {
        response_.addHeader(arg0, arg1);
    }

    public void addIntHeader(String arg0, int arg1) {
        response_.addIntHeader(arg0, arg1);
    }

    public boolean containsHeader(String arg0) {
        return response_.containsHeader(arg0);
    }

    public String encodeRedirectURL(String arg0) {
        return response_.encodeRedirectURL(arg0);
    }

    public String encodeRedirectUrl(String arg0) {
        return response_.encodeRedirectUrl(arg0);
    }

    public String encodeURL(String arg0) {
        return response_.encodeURL(arg0);
    }

    public String encodeUrl(String arg0) {
        return response_.encodeUrl(arg0);
    }

    public void flushBuffer() throws IOException {
        response_.flushBuffer();
    }

    public int getBufferSize() {
        return response_.getBufferSize();
    }

    public String getCharacterEncoding() {
        return response_.getCharacterEncoding();
    }

    public String getContentType() {
        return response_.getContentType();
    }

    public String getHeader(String arg0) {
        return response_.getHeader(arg0);
    }

    public Collection<String> getHeaderNames() {
        return response_.getHeaderNames();
    }

    public Collection<String> getHeaders(String arg0) {
        return response_.getHeaders(arg0);
    }

    public Locale getLocale() {
        return response_.getLocale();
    }

    public ServletOutputStream getOutputStream() throws IOException {
        return response_.getOutputStream();
    }

    public int getStatus() {
        return response_.getStatus();
    }

    public PrintWriter getWriter() throws IOException {
        return response_.getWriter();
    }

    public void reset() {
        response_.reset();
    }

    public void resetBuffer() {
        response_.resetBuffer();
    }

    public void sendRedirect(String arg0) throws IOException {
        response_.sendRedirect(arg0);
    }

    public void setBufferSize(int arg0) {
        response_.setBufferSize(arg0);
    }

    public void setCharacterEncoding(String arg0) {
        response_.setCharacterEncoding(arg0);
    }

    public void setContentLength(int arg0) {
        response_.setContentLength(arg0);
    }

    public void setContentType(String arg0) {
        response_.setContentType(arg0);
    }

    public void setDateHeader(String arg0, long arg1) {
        response_.setDateHeader(arg0, arg1);
    }

    public void setHeader(String arg0, String arg1) {
        response_.setHeader(arg0, arg1);
    }

    public void setIntHeader(String arg0, int arg1) {
        response_.setIntHeader(arg0, arg1);
    }

    public void setLocale(Locale arg0) {
        response_.setLocale(arg0);
    }

    public void setStatus(int arg0, String arg1) {
        response_.setStatus(arg0, arg1);
    }

    public void setStatus(int arg0) {
        response_.setStatus(arg0);
    }

}
