package org.support.project.knowledge.logic;

import java.io.IOException;
import java.util.Date;

import org.apache.commons.fileupload.FileItem;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.StringUtils;
import org.support.project.di.Container;
import org.support.project.knowledge.dao.AccountImagesDao;
import org.support.project.knowledge.entity.AccountImagesEntity;
import org.support.project.knowledge.vo.UploadFile;
import org.support.project.web.bean.LoginedUser;

public class AccountLogic {
	/** ログ */
	private static Log LOG = LogFactory.getLog(AccountLogic.class);

	public static AccountLogic get() {
		return Container.getComp(AccountLogic.class);
	}

	public UploadFile saveIconImage(FileItem fileItem, LoginedUser loginedUser, String context) throws IOException {
		LOG.trace("saveFile()");
		AccountImagesDao dao = AccountImagesDao.get();
		AccountImagesEntity entity =dao.selectOnUserId(loginedUser.getUserId());
		if (entity == null) {
			entity = new AccountImagesEntity();
		}
		
		entity.setFileName(fileItem.getName());
		entity.setFileSize(new Double(fileItem.getSize()));
		entity.setFileBinary(fileItem.getInputStream());
		entity.setUserId(loginedUser.getUserId());
		
		String extension = StringUtils.getExtension(entity.getFileName());
		entity.setExtension(extension);
		
		String contentType = "application/octet-stream";
		if (StringUtils.isNotEmpty(extension)) {
			if (extension.toLowerCase().indexOf("png") != -1) {
				contentType = "image/png";
			} else if (extension.toLowerCase().indexOf("jpg") != -1) {
				contentType = "image/jpeg";
			} else if (extension.toLowerCase().indexOf("jpeg") != -1) {
				contentType = "image/jpeg";
			} else if (extension.toLowerCase().indexOf("gif") != -1) {
				contentType = "image/gif";
			}
		}
		entity.setContentType(contentType);
		entity = dao.save(entity);
		UploadFile file = convUploadFile(context, entity);
		//処理が完了したら、テンポラリのファイルを削除
		fileItem.delete();
		return file;
	}

	
	/**
	 * KnowledgeFilesEntity の情報から、画面に戻す UploadFile の情報を生成する
	 * @param context
	 * @param entity
	 * @return
	 */
	private UploadFile convUploadFile(String context, AccountImagesEntity entity) {
		UploadFile file = new UploadFile();
		file.setFileNo(new Long(entity.getUserId()));
		file.setUrl(context + "/open.account/icon/" + entity.getUserId());
		file.setThumbnailUrl(context + "/open.account/icon/" + entity.getUserId() + "?t=" + new Date().getTime());
		file.setName(entity.getFileName());
		file.setType("-");
		file.setSize(entity.getFileSize());
		file.setDeleteUrl(context + "/protect.account/delete");
		file.setDeleteType("DELETE");
		return file;
	}

	
}
