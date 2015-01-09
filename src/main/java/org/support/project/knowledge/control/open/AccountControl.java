package org.support.project.knowledge.control.open;

import java.io.InputStream;

import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.control.Control;
import org.support.project.knowledge.dao.AccountImagesDao;
import org.support.project.knowledge.entity.AccountImagesEntity;
import org.support.project.web.boundary.Boundary;
import org.support.project.web.exception.InvalidParamException;

@DI(instance=Instance.Prototype)
public class AccountControl extends Control {
	/**
	 * ユーザのアイコン画像を取得
	 * @return
	 * @throws InvalidParamException
	 */
	public Boundary icon() throws InvalidParamException {
		Integer userId = getPathInteger(-1);
		
		AccountImagesDao dao = AccountImagesDao.get();
		AccountImagesEntity entity = dao.selectOnUserId(userId);
		
		String fileName = "icon.png";
		String contentType = "image/png";
		InputStream inputStream = null;
		long size = 12140;
		if (entity != null) {
			fileName = entity.getFileName();
			contentType = entity.getContentType();
			size = entity.getFileSize().longValue();
			inputStream = entity.getFileBinary();
		} else {
			inputStream = getClass().getResourceAsStream("/icon/icon.png");
		}
		return download(fileName, inputStream, size, contentType);
		
	}
	
}
