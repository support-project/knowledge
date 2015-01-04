package redcomet.knowledge.logic;

import java.io.IOException;
import java.util.Date;

import org.apache.commons.fileupload.FileItem;

import redcomet.common.log.Log;
import redcomet.common.log.LogFactory;
import redcomet.common.util.StringUtils;
import redcomet.di.Container;
import redcomet.knowledge.dao.AccountImagesDao;
import redcomet.knowledge.entity.AccountImagesEntity;
import redcomet.knowledge.vo.UploadFile;
import redcomet.web.bean.LoginedUser;

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
