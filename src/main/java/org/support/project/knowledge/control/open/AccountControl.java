package org.support.project.knowledge.control.open;

import java.io.InputStream;
import java.util.List;

import org.apache.http.HttpStatus;
import org.support.project.common.util.StringUtils;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.control.Control;
import org.support.project.knowledge.dao.AccountImagesDao;
import org.support.project.knowledge.dao.ExUsersDao;
import org.support.project.knowledge.entity.AccountImagesEntity;
import org.support.project.knowledge.entity.KnowledgesEntity;
import org.support.project.knowledge.logic.KnowledgeLogic;
import org.support.project.knowledge.vo.AccountInfo;
import org.support.project.web.boundary.Boundary;
import org.support.project.web.control.service.Get;
import org.support.project.web.exception.InvalidParamException;

@DI(instance=Instance.Prototype)
public class AccountControl extends Control {
	public static final int PAGE_LIMIT = 50;

	/**
	 * ユーザのアイコン画像を取得
	 * @return
	 * @throws InvalidParamException
	 */
	@Get
	public Boundary icon() throws InvalidParamException {
		Integer userId = getPathInteger(-1);
		
		AccountImagesDao dao = AccountImagesDao.get();
		AccountImagesEntity entity = dao.selectOnUserId(userId);
		
		String fileName = "icon.png";
		String contentType = "image/png";
		InputStream inputStream;
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
	
	@Get
	public Boundary info() throws Exception {
		// 指定のユーザの情報を取得
		Integer userId = getPathInteger(-1);
		ExUsersDao usersDao = ExUsersDao.get();
		AccountInfo account = usersDao.selectAccountInfoOnKey(userId);
		if (account == null) {
			return sendError(HttpStatus.SC_NOT_FOUND, "NOT FOUND");
		}
		
		setAttributeOnProperty(account);
		
		// そのユーザが登録したナレッジを取得
		int offset = 0;
		if (StringUtils.isInteger(getParam("offset"))) {
			offset = getParam("offset", Integer.class);
		}
		List<KnowledgesEntity> knowledges = KnowledgeLogic.get().showKnowledgeOnUser(userId, getLoginedUser(),
				offset * PAGE_LIMIT, PAGE_LIMIT);
		setAttribute("knowledges", knowledges);
		
		int previous = offset -1;
		if (previous < 0) {
			previous = 0;
		}
		setAttribute("offset", offset);
		setAttribute("previous", previous);
		setAttribute("next", offset + 1);
		
		return forward("account.jsp");
	}
	
	
}
