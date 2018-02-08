package org.support.project.web.wrapper;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.invoke.MethodHandles;
import java.nio.charset.Charset;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;

@Deprecated
public class BufferedServletRequestWrapper extends HttpServletRequestWrapper {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(MethodHandles.lookup());

    private byte[] buffer;

    public BufferedServletRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);

        InputStream is = request.getInputStream();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buff = new byte[1024];
        int read;
        while ((read = is.read(buff)) > 0) {
            baos.write(buff, 0, read);
        }
        this.buffer = baos.toByteArray();

        if (LOG.isTraceEnabled()) {
            BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(this.buffer), Charset.forName("UTF-8")));
            String line;
            while ((line = br.readLine()) != null) {
                LOG.trace(line);
            }
            br.close();
        }
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return new BufferedServletInputStream(this.buffer);
    }

}
