package org.support.project.web.filter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.support.project.common.config.ConfigLoader;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.web.bean.Msg;
import org.support.project.web.common.HttpStatus;
import org.support.project.web.config.AppConfig;
import org.support.project.web.config.CommonWebParameter;

import net.arnx.jsonic.JSON;

/**
 * filter for multi part form data.
 * 
 * @author Koda
 *
 */
public class MultipartFilter implements Filter {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(MultipartFilter.class);

    private AppConfig appConfig;

    @Override
    public void init(FilterConfig config) throws ServletException {
        appConfig = ConfigLoader.load(org.support.project.web.config.AppConfig.APP_CONFIG, org.support.project.web.config.AppConfig.class);
    }

    @Override
    public void destroy() {
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (!(request instanceof HttpServletRequest)) {
            chain.doFilter(request, response);
            return;
        }
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        // Multipartではない場合は通常処理
        if (!ServletFileUpload.isMultipartContent(httpRequest)) {
            chain.doFilter(request, response);
            return;
        }
        LOG.trace("isMultipartContent:true");

        int uploadMaxSize = appConfig.getUploadMaxMBSize();
        String encodingHeader = "utf-8";
        String encodingForm = "utf-8";
        String tempDir = appConfig.getTmpPath();

        // Create a factory for disk-based file items
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(50 * 1024);
        factory.setRepository(new File(tempDir));

        // Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setSizeMax(uploadMaxSize * 1024 * 1024);
        upload.setHeaderEncoding(encodingHeader);

        try {
            List<FileItem> list = upload.parseRequest(httpRequest);
            for (int i = 0; i < list.size(); i++) {
                FileItem item = (FileItem) list.get(i);
                if (item.isFormField()) {
                    // 通常のパラメータ
                    String str = item.getString(encodingForm);
                    if (httpRequest.getAttribute(item.getFieldName()) != null) {
                        Object obj = httpRequest.getAttribute(item.getFieldName());
                        if (obj instanceof List) {
                            List<String> items = (List<String>) obj;
                            items.add(str);
                        } else {
                            List<String> items = new ArrayList<String>();
                            if (obj instanceof String) {
                                items.add((String) obj);
                            } else {
                                LOG.warn("Field [" + item.getFieldName() + "] is not string.");
                            }
                            items.add(str);
                            httpRequest.setAttribute(item.getFieldName(), items);
                        }
                    } else {
                        httpRequest.setAttribute(item.getFieldName(), str);
                    }
                } else {
                    // アップロードされたファイル
                    if (httpRequest.getAttribute(item.getFieldName()) != null) {
                        Object obj = httpRequest.getAttribute(item.getFieldName());
                        if (obj instanceof List) {
                            List<FileItem> items = (List<FileItem>) obj;
                            items.add(item);
                        } else {
                            List<FileItem> items = new ArrayList<FileItem>();
                            if (obj instanceof FileItem) {
                                items.add((FileItem) obj);
                            } else {
                                LOG.warn("Field [" + item.getFieldName() + "] is not fileitem");
                            }
                            items.add(item);
                            httpRequest.setAttribute(item.getFieldName(), items);
                        }
                    } else {
                        httpRequest.setAttribute(item.getFieldName(), item);
                    }
                }
            }
            chain.doFilter(request, response);
        } catch (SizeLimitExceededException e) {
            // httpResponse.setStatus(HttpStatus.SC_400_BAD_REQUEST);
            Msg msg = new Msg(e.getMessage());
            String str = JSON.encode(msg);
            httpRequest.setAttribute(CommonWebParameter.ERROR_ATTRIBUTE, str);
            httpResponse.sendError(HttpStatus.SC_400_BAD_REQUEST, str);
            // String json = JSON.encode(msg);
            // httpResponse.getWriter().write(json);
            // httpResponse.getWriter().close();
        } catch (FileUploadException ex) {
            ServletException servletEx = new ServletException();
            servletEx.initCause(ex);
            throw servletEx;
        }
    }
}
