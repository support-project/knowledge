package org.support.project.web.boundary;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.invoke.MethodHandles;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

import org.support.project.common.exception.SystemException;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.web.common.HttpUtil;

public class DownloadBoundary extends AbstractBoundary {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(MethodHandles.lookup());
    
    private String fileName;
    private InputStream inputStream;
    private long contentsSize;
    private String contentType = "application/octet-stream";
    
    
    public DownloadBoundary(String fileName, InputStream inputStream, long contentsSize) {
        super();
        this.fileName = fileName;
        this.inputStream = inputStream;
        this.contentsSize = contentsSize;
    }
    public DownloadBoundary(String fileName, InputStream inputStream, long contentsSize, String contentType) {
        super();
        this.fileName = fileName;
        this.inputStream = inputStream;
        this.contentsSize = contentsSize;
        this.contentType = contentType;
    }

    @Override
    public void navigate() throws Exception {
        LOG.trace("navigate");
        HttpServletResponse response = getResponse();
        OutputStream os = response.getOutputStream();

        try {
            BufferedInputStream bis = new BufferedInputStream(inputStream);
            response.setContentType(contentType);

            // response.setContentLength((int) file.length());
            response.setHeader("Content-Length", String.valueOf(contentsSize));

            if (HttpUtil.isIE(getRequest())) {
                // IEの場合ファイル名をURLEncodeする
                response.setHeader("Content-Disposition", "inline; filename=\"" + URLEncoder.encode(fileName, "UTF-8") + "\"");
            } else {
                // ファイル名の設定ISO_8859_1にエンコード
                response.setHeader("Content-Disposition", "inline; filename=\"" + new String(fileName.getBytes("UTF-8"), "ISO_8859_1") + "\"");
            }

            int len = 0;
            byte[] buffer = new byte[1024];
            while ((len = bis.read(buffer)) >= 0) {
                try {
                    os.write(buffer, 0, len);
                    os.flush();
                } catch (Exception e) {
                    // スマフォからダウンロードするとBroken pipeが発生する(クライアントからの通知が何も無いのでキャンセルしたように判定する？）
                    if (LOG.isTraceEnabled()) {
                        LOG.trace(e.getClass().getName() + ": " + e.getMessage());
                    }
                }
            }
            bis.close();
        } finally {
            inputStream.close();
            
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    throw new SystemException(e);
                } finally {
                    os = null;
                }
            }
        }
    }
    
}
