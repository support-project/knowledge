package org.support.project.web.test.stub;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.support.project.common.exception.NotImplementedException;

public class StubHttpServletResponse implements HttpServletResponse {
    /** Cookie */
    private List<Cookie> cookies;
    private StubHttpServletRequest request;
    
    private int status;

    public StubHttpServletResponse(StubHttpServletRequest request) {
        cookies = new ArrayList<>();
        this.request = request;
    }

    @Override
    public void addCookie(Cookie paramCookie) {
        cookies.add(paramCookie);
        this.request.addCookie(paramCookie);
    }

    @Override
    public void setStatus(int paramInt) {
        this.status = paramInt;
    }
    @Override
    public void setStatus(int paramInt, String paramString) {
        this.status = paramInt;
    }
    @Override
    public int getStatus() {
        return status;
    }
    
    
    
    
    @Override
    public String getCharacterEncoding() {
        throw new NotImplementedException("NotImplemented");
    }

    @Override
    public String getContentType() {
        throw new NotImplementedException("NotImplemented");
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        throw new NotImplementedException("NotImplemented");
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        throw new NotImplementedException("NotImplemented");
    }

    @Override
    public void setCharacterEncoding(String paramString) {
        throw new NotImplementedException("NotImplemented");
    }

    @Override
    public void setContentLength(int paramInt) {
        throw new NotImplementedException("NotImplemented");
    }

    @Override
    public void setContentType(String paramString) {
        throw new NotImplementedException("NotImplemented");
    }

    @Override
    public void setBufferSize(int paramInt) {
        throw new NotImplementedException("NotImplemented");
    }

    @Override
    public int getBufferSize() {
        throw new NotImplementedException("NotImplemented");
    }

    @Override
    public void flushBuffer() throws IOException {
        throw new NotImplementedException("NotImplemented");
    }

    @Override
    public void resetBuffer() {
        throw new NotImplementedException("NotImplemented");
    }

    @Override
    public boolean isCommitted() {
        throw new NotImplementedException("NotImplemented");
    }

    @Override
    public void reset() {
        throw new NotImplementedException("NotImplemented");
    }

    @Override
    public void setLocale(Locale paramLocale) {
        throw new NotImplementedException("NotImplemented");
    }

    @Override
    public Locale getLocale() {
        throw new NotImplementedException("NotImplemented");
    }


    @Override
    public boolean containsHeader(String paramString) {
        throw new NotImplementedException("NotImplemented");
    }

    @Override
    public String encodeURL(String paramString) {
        throw new NotImplementedException("NotImplemented");
    }

    @Override
    public String encodeRedirectURL(String paramString) {
        throw new NotImplementedException("NotImplemented");
    }

    @Override
    public String encodeUrl(String paramString) {
        throw new NotImplementedException("NotImplemented");
    }

    @Override
    public String encodeRedirectUrl(String paramString) {
        throw new NotImplementedException("NotImplemented");
    }

    @Override
    public void sendError(int paramInt, String paramString) throws IOException {
        throw new NotImplementedException("NotImplemented");
    }

    @Override
    public void sendError(int paramInt) throws IOException {
        throw new NotImplementedException("NotImplemented");
    }

    @Override
    public void sendRedirect(String paramString) throws IOException {
        throw new NotImplementedException("NotImplemented");
    }

    @Override
    public void setDateHeader(String paramString, long paramLong) {
        throw new NotImplementedException("NotImplemented");
    }

    @Override
    public void addDateHeader(String paramString, long paramLong) {
        throw new NotImplementedException("NotImplemented");
    }

    @Override
    public void setHeader(String paramString1, String paramString2) {
        throw new NotImplementedException("NotImplemented");
    }

    @Override
    public void addHeader(String paramString1, String paramString2) {
        throw new NotImplementedException("NotImplemented");
    }

    @Override
    public void setIntHeader(String paramString, int paramInt) {
        throw new NotImplementedException("NotImplemented");
    }

    @Override
    public void addIntHeader(String paramString, int paramInt) {
        throw new NotImplementedException("NotImplemented");
    }


    @Override
    public String getHeader(String paramString) {
        throw new NotImplementedException("NotImplemented");
    }

    @Override
    public Collection<String> getHeaders(String paramString) {
        throw new NotImplementedException("NotImplemented");
    }

    @Override
    public Collection<String> getHeaderNames() {
        throw new NotImplementedException("NotImplemented");
    }

    @Override
    public void setContentLengthLong(long arg0) {
        // TODO Auto-generated method stub
        
    }

}
