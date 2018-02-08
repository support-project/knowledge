package org.support.project.knowledge.logic;

import java.lang.invoke.MethodHandles;

import org.support.project.common.config.INT_FLAG;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.knowledge.dao.ExUsersDao;
import org.support.project.knowledge.dao.NotifyConfigsDao;
import org.support.project.knowledge.entity.NotifyConfigsEntity;
import org.support.project.web.entity.UsersEntity;
import org.support.project.web.logic.AddUserProcess;

public class AddUserProcessLogic implements AddUserProcess {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(MethodHandles.lookup());

    @Override
    public void addUserProcess(String userKey) {
        LOG.debug("addUserProcess");
        
        UsersEntity user = ExUsersDao.get().selectOnUserKey(userKey);
        if (user == null) {
            LOG.debug("user: " + userKey + " is not exists.");
            return;
        }
        
        // 追加されたユーザの、通知設定をデフォルトONにする
        NotifyConfigsDao notifyConfigsDao = NotifyConfigsDao.get();
        NotifyConfigsEntity entity = new NotifyConfigsEntity();
        entity.setNotifyMail(INT_FLAG.ON.getValue());
        entity.setMyItemComment(INT_FLAG.ON.getValue());
        entity.setMyItemLike(INT_FLAG.ON.getValue());
        entity.setMyItemStock(INT_FLAG.ON.getValue());
        entity.setToItemComment(INT_FLAG.ON.getValue());
        entity.setToItemSave(INT_FLAG.ON.getValue());
        entity.setToItemIgnorePublic(INT_FLAG.OFF.getValue());
        entity.setUserId(user.getUserId());
        notifyConfigsDao.save(entity);
        
        LOG.debug("set notify config to user: " + userKey + "");
    }

}
