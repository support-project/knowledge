package org.support.project.knowledge.control.open;

import java.io.FileNotFoundException;
import java.lang.invoke.MethodHandles;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.control.Control;
import org.support.project.knowledge.entity.KnowledgeFilesEntity;
import org.support.project.knowledge.logic.SlideLogic;
import org.support.project.knowledge.logic.UploadedFileLogic;
import org.support.project.knowledge.vo.SlideInfo;
import org.support.project.web.bean.DownloadInfo;
import org.support.project.web.boundary.Boundary;
import org.support.project.web.common.HttpStatus;
import org.support.project.web.control.service.Get;
import org.support.project.web.exception.InvalidParamException;

@DI(instance = Instance.Prototype)
public class FileControl extends Control {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(MethodHandles.lookup());

    private UploadedFileLogic fileLogic = UploadedFileLogic.get();

    @Get
    public Boundary download() {
        LOG.trace("download()");

        Long fileNo = getParam("fileNo", Long.class);
        KnowledgeFilesEntity entity = fileLogic.getFile(fileNo, getLoginedUser());
        if (entity == null) {
            return sendError(HttpStatus.SC_404_NOT_FOUND, "NOT FOUND");
        }
        if (entity.getFileBinary() == null) {
            LOG.debug("File binary is null. [fileNo] " + fileNo);
            return sendError(HttpStatus.SC_404_NOT_FOUND, "NOT FOUND");
        }
        if (getParam("attachment").equals("")) {
            // 画像（PNG, JPEG, GIF）を本文内に表示する場合は attachment パラメーターが付与されていない。
            if (entity.getFileName().toLowerCase().endsWith(".png")) {
                return download(entity.getFileName(), entity.getFileBinary(), entity.getFileSize().longValue(), "image/png");
            }
            if (entity.getFileName().toLowerCase().endsWith(".jpg") || entity.getFileName().toLowerCase().endsWith(".jpeg")) {
                return download(entity.getFileName(), entity.getFileBinary(), entity.getFileSize().longValue(), "image/jpeg");
            }
            if (entity.getFileName().toLowerCase().endsWith(".gif")) {
                return download(entity.getFileName(), entity.getFileBinary(), entity.getFileSize().longValue(), "image/gif");
            }
        }
        return download(entity.getFileName(), entity.getFileBinary(), entity.getFileSize().longValue());
    }
    
    @Get
    public Boundary slide() throws InvalidParamException, FileNotFoundException {
        String[] pathInfos = getPathInfos();
        if (pathInfos == null || pathInfos.length == 0) {
            return send(HttpStatus.SC_400_BAD_REQUEST);
        }
        String fileNo = pathInfos[0];
        String slideImage = null;
        if (pathInfos.length > 1) {
            slideImage = pathInfos[1];
        }
        if (slideImage == null) {
            // スライドの情報を取得
            SlideInfo slideInfo = SlideLogic.get().getSlideInfo(fileNo, getLoginedUser());
            if (slideInfo == null) {
                return sendError(HttpStatus.SC_404_NOT_FOUND, "NOT FOUND");
            }
            return send(slideInfo);
        } else {
            // スライドの画像を取得
            DownloadInfo down = SlideLogic.get().getSlideImage(fileNo, slideImage, getLoginedUser());
            if (down == null) {
                return sendError(HttpStatus.SC_404_NOT_FOUND, "Not Found");
            }
            return download(down);
        }
    }
    
}
