package org.support.project.knowledge.control.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.support.project.common.bean.ValidateError;
import org.support.project.common.wrapper.FileInputStreamWithDeleteWrapper;
import org.support.project.knowledge.control.Control;
import org.support.project.knowledge.logic.DatabaseLogic;
import org.support.project.web.annotation.Auth;
import org.support.project.web.boundary.Boundary;
import org.support.project.web.logic.H2DBServerLogic;

public class DatabaseControl extends Control {
	
	/* (non-Javadoc)
	 * @see org.support.project.web.control.Control#index()
	 */
	@Override
	public Boundary index() {
		H2DBServerLogic h2dbServerLogic = H2DBServerLogic.get();
		boolean active = h2dbServerLogic.isActive();
		setAttribute("active", active);
		return super.index();
	}
	
	@Auth(roles="admin")
	public Boundary start() {
		H2DBServerLogic h2dbServerLogic = H2DBServerLogic.get();
		h2dbServerLogic.start();
		boolean active = h2dbServerLogic.isActive();
		setAttribute("active", active);
		return super.index();
	}
	@Auth(roles="admin")
	public Boundary stop() {
		H2DBServerLogic h2dbServerLogic = H2DBServerLogic.get();
		h2dbServerLogic.stop();
		boolean active = h2dbServerLogic.isActive();
		setAttribute("active", active);
		return super.index();
	}

	
	
	/**
	 * データをバックアップ
	 * @return
	 * @throws IOException 
	 */
	@Auth(roles="admin")
	public Boundary backup() throws IOException {
		
		HttpServletResponse res = getResponse();
		res.setDateHeader("Expires",0);
		res.setHeader("Pragma","no-cache");
		res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		
		DatabaseLogic databaseLogic = DatabaseLogic.get();
		FileInputStreamWithDeleteWrapper inputStream = databaseLogic.getData();
		
		return download("knowledge.zip", inputStream, inputStream.size(), "application/zip");
	}

	
	@Auth(roles="admin")
	public Boundary restore() throws IOException {
		H2DBServerLogic h2dbServerLogic = H2DBServerLogic.get();
		boolean active = h2dbServerLogic.isActive();
		if (active) {
			addMsgInfo("knowledge.data.label.msg.before.stop");
			setAttribute("active", active);
			return super.index();
		}
		
		FileItem fileItem = super.getParam("upload", FileItem.class);
		if (fileItem == null || fileItem.getSize() == 0) {
			addMsgWarn("knowledge.data.label.msg.empty");
		} else if (!fileItem.getName().endsWith(".zip") && !fileItem.getName().endsWith(".ZIP")) {
			addMsgWarn("knowledge.data.label.msg.invalid.file");
		} else {
			DatabaseLogic databaseLogic = DatabaseLogic.get();
			List<ValidateError> errors = databaseLogic.restore(fileItem);
			setResult("knowledge.data.label.msg.restore", errors);
		}
		active = h2dbServerLogic.isActive();
		setAttribute("active", active);
		return super.index();
	}


}
