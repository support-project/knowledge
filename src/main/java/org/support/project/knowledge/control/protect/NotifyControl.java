package org.support.project.knowledge.control.protect;

import java.io.IOException;

import org.support.project.common.config.INT_FLAG;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.control.Control;
import org.support.project.knowledge.dao.NotifyConfigsDao;
import org.support.project.knowledge.entity.NotifyConfigsEntity;
import org.support.project.web.boundary.Boundary;
import org.support.project.web.control.service.Get;
import org.support.project.web.control.service.Post;
import org.support.project.web.exception.InvalidParamException;

import net.arnx.jsonic.JSONException;

@DI(instance = Instance.Prototype)
public class NotifyControl extends Control {

    /*
     * (non-Javadoc)
     * 
     * @see org.support.project.web.control.Control#index()
     */
    @Override
    @Get(publishToken = "knowledge")
    public Boundary index() {
        NotifyConfigsDao notifyConfigsDao = NotifyConfigsDao.get();
        NotifyConfigsEntity notifyConfigsEntity = notifyConfigsDao.selectOnKey(getLoginUserId());
        if (notifyConfigsEntity == null) {
            notifyConfigsEntity = new NotifyConfigsEntity();
        }
        setAttributeOnProperty(notifyConfigsEntity);
        return super.index();
    }

    @Post(subscribeToken = "knowledge")
    public Boundary save() throws InstantiationException, IllegalAccessException, JSONException, IOException, InvalidParamException {
        NotifyConfigsEntity entity = super.getParamOnProperty(NotifyConfigsEntity.class);
        NotifyConfigsDao notifyConfigsDao = NotifyConfigsDao.get();
        if (entity.getNotifyMail() == null) {
            entity.setNotifyMail(INT_FLAG.OFF.getValue());
        }
        if (entity.getNotifyDesktop() == null) {
            entity.setNotifyDesktop(INT_FLAG.OFF.getValue());
        }
        
        if (entity.getNotifyMail() == INT_FLAG.ON.getValue() || entity.getNotifyDesktop() == INT_FLAG.ON.getValue()) {
            // 通知はONにしたけどメールが届かないという問い合わせがある
            // 通知をONにしても、通知する種類を選択していないとメールが届かないので、通知ONの場合、
            // 通知する種類を明示的にOFFにしない場合以外はONにする
            if (entity.getMyItemComment() == null) {
                entity.setMyItemComment(INT_FLAG.ON.getValue());
            }
            if (entity.getMyItemLike() == null) {
                entity.setMyItemLike(INT_FLAG.ON.getValue());
            }
            if (entity.getMyItemStock() == null) {
                entity.setMyItemStock(INT_FLAG.ON.getValue());
            }
            if (entity.getToItemComment() == null) {
                entity.setToItemComment(INT_FLAG.ON.getValue());
            }
            if (entity.getToItemSave() == null) {
                entity.setToItemSave(INT_FLAG.ON.getValue());
            }
        }
        
        entity.setUserId(getLoginUserId());
        notifyConfigsDao.save(entity);
        addMsgSuccess("message.success.save");
        return super.index();
    }

}
