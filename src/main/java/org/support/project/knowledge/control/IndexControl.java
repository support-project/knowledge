package org.support.project.knowledge.control;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.web.bean.MessageResult;
import org.support.project.web.boundary.Boundary;
import org.support.project.web.config.MessageStatus;

@DI(instance=Instance.Prototype)
public class IndexControl extends Control {
	/** ログ */
	private static Log log = LogFactory.getLog(IndexControl.class);
	
	public Boundary msg() {
		MessageResult result = new MessageResult();
		result.setStatus(MessageStatus.Success.getValue());
		result.setMessage("OK");
		return send(result);
	}
	
}
