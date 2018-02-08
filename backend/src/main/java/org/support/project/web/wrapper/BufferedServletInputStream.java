package org.support.project.web.wrapper;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;

@Deprecated
public class BufferedServletInputStream extends ServletInputStream {

    private ByteArrayInputStream inputStream;

    public BufferedServletInputStream(byte[] buffer) {
        this.inputStream = new ByteArrayInputStream(buffer);
    }

    @Override
    public int available() throws IOException {
        return inputStream.available();
    }

    @Override
    public int read() throws IOException {
        return inputStream.read();
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        return inputStream.read(b, off, len);
    }

	@Override
	public boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isReady() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setReadListener(ReadListener readListener) {
		// TODO Auto-generated method stub
		
	}

}