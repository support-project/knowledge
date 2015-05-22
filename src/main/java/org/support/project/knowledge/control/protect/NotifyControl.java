package org.support.project.knowledge.control.protect;

import java.io.IOException;

import net.arnx.jsonic.JSONException;

import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.control.Control;
import org.support.project.knowledge.dao.NotifyConfigsDao;
import org.support.project.knowledge.entity.NotifyConfigsEntity;
import org.support.project.web.boundary.Boundary;
import org.support.project.web.control.service.Get;
import org.support.project.web.control.service.Post;
import org.support.project.web.exception.InvalidParamException;

@DI(instance=Instance.Prototype)
public class NotifyControl extends Control {

	/* (non-Javadoc)
	 * @see org.support.project.web.control.Control#index()
	 */
	@Override
	@Get
	public Boundary index() {
		NotifyConfigsDao notifyConfigsDao = NotifyConfigsDao.get();
		NotifyConfigsEntity notifyConfigsEntity = notifyConfigsDao.selectOnKey(getLoginUserId());
		if (notifyConfigsEntity == null) {
			notifyConfigsEntity = new NotifyConfigsEntity();
		}
		setAttributeOnProperty(notifyConfigsEntity);
		return super.index();
	}
	
	@Post
	public Boundary save() throws InstantiationException, IllegalAccessException, JSONException, IOException, InvalidParamException {
		NotifyConfigsEntity entity = super.getParamOnProperty(NotifyConfigsEntity.class);
		NotifyConfigsDao notifyConfigsDao = NotifyConfigsDao.get();
		entity.setUserId(getLoginUserId());
		notifyConfigsDao.save(entity);
		addMsgSuccess("message.success.save");
		return super.index();
	}
	
	
	
	
}
