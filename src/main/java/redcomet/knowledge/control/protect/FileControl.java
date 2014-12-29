package redcomet.knowledge.control.protect;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.fileupload.FileItem;

import redcomet.common.log.Log;
import redcomet.common.log.LogFactory;
import redcomet.knowledge.control.Control;
import redcomet.knowledge.dao.KnowledgeFilesDao;
import redcomet.knowledge.entity.KnowledgeFilesEntity;
import redcomet.knowledge.logic.UploadedFileLogic;
import redcomet.knowledge.vo.UploadFile;
import redcomet.knowledge.vo.UploadResults;
import redcomet.web.boundary.Boundary;
import redcomet.web.boundary.JsonBoundary;
import redcomet.web.common.HttpStatus;

public class FileControl extends Control {
	/** ログ */
	private static Log LOG = LogFactory.getLog(FileControl.class);
	
	private KnowledgeFilesDao filesDao = KnowledgeFilesDao.get();
	private UploadedFileLogic fileLogic = UploadedFileLogic.get();
	
	/**
	 * アップロードされたファイルを保存する
	 * @return
	 * @throws Exception
	 */
	public Boundary upload() throws Exception {
		UploadResults results = new UploadResults();
		List<UploadFile> files = new ArrayList<UploadFile>();
		Object obj = getParam("files[]", Object.class);
		if (obj instanceof FileItem) {
			FileItem fileItem = (FileItem) obj;
			UploadFile file = fileLogic.saveFile(fileItem, getLoginedUser(), getRequest().getContextPath());
			files.add(file);
		} else if (obj instanceof List) {
			List<FileItem> fileItems = (List<FileItem>) obj;
			for (FileItem fileItem : fileItems) {
				UploadFile file = fileLogic.saveFile(fileItem, getLoginedUser(), getRequest().getContextPath());
				files.add(file);
			}
		}
		results.setFiles(files);
		return send(HttpStatus.SC_200_OK, results);
	}
	
	
	
	public JsonBoundary delete() {
		LOG.trace("delete()");
		
		Long fileNo = getParam("fileNo", Long.class);
		KnowledgeFilesEntity entity = filesDao.selectOnKeyWithoutBinary(fileNo);
		if (entity == null) {
			// 既に削除済
			return send(HttpStatus.SC_200_OK, "success: " + fileNo);
		}
		if (0 == entity.getKnowledgeId() || entity.getKnowledgeId() == null) {
			// ナレッジと紐づいていないものなので削除してOK
			// 紐づいているものは、ナレッジを更新した際に、紐付きがなければ削除になるので、実際の削除処理は実施しない
			if (!getLoginedUser().isAdmin()) {
				if (entity.getInsertUser().intValue() != getLoginUserId().intValue())  {
					// 登録者以外に削除はさせない
					return send(HttpStatus.SC_400_BAD_REQUEST, "fail: " + fileNo);
				}
			}
			// 削除実行
			fileLogic.removeFile(fileNo, getLoginedUser());
		}
		return send(HttpStatus.SC_200_OK, "success: " + fileNo);
	}
	
	
}
