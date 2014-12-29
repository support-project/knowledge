package redcomet.knowledge.control.open;

import redcomet.common.log.Log;
import redcomet.common.log.LogFactory;
import redcomet.knowledge.control.Control;
import redcomet.knowledge.entity.KnowledgeFilesEntity;
import redcomet.knowledge.logic.UploadedFileLogic;
import redcomet.web.boundary.Boundary;
import redcomet.web.common.HttpStatus;

public class FileControl extends Control {
	/** ログ */
	private static Log LOG = LogFactory.getLog(FileControl.class);
	
	private UploadedFileLogic fileLogic = UploadedFileLogic.get();
	
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
