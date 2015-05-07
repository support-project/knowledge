package org.support.project.knowledge.control.open;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.control.Control;
import org.support.project.knowledge.entity.KnowledgeFilesEntity;
import org.support.project.knowledge.logic.UploadedFileLogic;
import org.support.project.web.boundary.Boundary;
import org.support.project.web.common.HttpStatus;
import org.support.project.web.control.service.Get;

@DI(instance=Instance.Prototype)
public class FileControl extends Control {
	/** ログ */
	private static Log LOG = LogFactory.getLog(FileControl.class);
	
	private UploadedFileLogic fileLogic = UploadedFileLogic.get();
	
	@Get
	public Boundary download() {
		LOG.trace("download()");
		
		Long fileNo = getParam("fileNo", Long.class);
		KnowledgeFilesEntity entity = fileLogic.getFile(fileNo, getLoginedUser());
		
		if (entity == null) {
			return sendError(HttpStatus.SC_404_NOT_FOUND, "NOT FOUND");
		}
		
		return download(entity.getFileName(), entity.getFileBinary(), entity.getFileSize().longValue());
	}
}
