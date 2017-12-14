package org.support.project.knowledge.logic.notification;

import org.junit.BeforeClass;
import org.support.project.common.config.INT_FLAG;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.knowledge.TestCommon;
import org.support.project.knowledge.dao.NotifyConfigsDao;
import org.support.project.knowledge.entity.NotifyConfigsEntity;
import org.support.project.knowledge.logic.AddUserProcessLogic;

public abstract class NotificationTestCommon extends TestCommon {
    /** ログ */
    protected Log LOG = LogFactory.getLog(this.getClass());
    
    /**
     * @BeforeClass
     * @throws Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        TestCommon.setUpBeforeClass();
        AddUserProcessLogic logic = new AddUserProcessLogic();
        logic.addUserProcess(loginedUser.getLoginUser().getUserKey());
        // デスクトップ通知をONにする
        NotifyConfigsEntity notifyConfig = NotifyConfigsDao.get().selectOnKey(loginedUser.getUserId());
        if (notifyConfig != null) {
            // デスクトプ通知はONにする
            notifyConfig.setNotifyDesktop(INT_FLAG.ON.getValue());
            NotifyConfigsDao.get().update(notifyConfig);
        }
    }


}
