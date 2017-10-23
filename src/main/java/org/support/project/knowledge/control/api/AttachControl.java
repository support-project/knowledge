package org.support.project.knowledge.control.api;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.knowledge.entity.KnowledgeFilesEntity;
import org.support.project.knowledge.logic.UploadedFileLogic;
import org.support.project.web.bean.ApiParams;
import org.support.project.web.boundary.Boundary;
import org.support.project.web.common.HttpStatus;
import org.support.project.web.control.GetApiControl;
import org.support.project.web.control.service.Get;

public class AttachControl extends GetApiControl {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(AttachControl.class);

    @Get(path="api/attachments", publishToken="")
    public Boundary index() {
        return get();
    }
    
    @Override
    public Boundary getList(ApiParams params) {
        return sendError(HttpStatus.SC_501_NOT_IMPLEMENTED);
    }

    @Override
    public Boundary getSingle(String id) {
        try {
            Long fileNo = Long.parseLong(id);
            KnowledgeFilesEntity entity = UploadedFileLogic.get().getFile(fileNo, getLoginedUser());
            if (entity == null) {
                return sendError(HttpStatus.SC_404_NOT_FOUND, "NOT FOUND");
            }
            if (entity.getFileBinary() == null) {
                LOG.debug("File binary is null. [fileNo] " + fileNo);
                return sendError(HttpStatus.SC_404_NOT_FOUND, "NOT FOUND");
            }
            return download(entity.getFileName(), entity.getFileBinary(), entity.getFileSize().longValue());
        } catch (Exception e) {
            return sendError(HttpStatus.SC_500_INTERNAL_SERVER_ERROR);
        }
    }

}
