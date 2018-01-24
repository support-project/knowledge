package org.support.project.web.logic;

import java.lang.invoke.MethodHandles;
import java.util.List;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.web.bean.AccessUser;
import org.support.project.web.bean.SendList;
import org.support.project.web.dao.NoticesDao;
import org.support.project.web.dao.ReadMarksDao;
import org.support.project.web.entity.NoticesEntity;
import org.support.project.web.entity.ReadMarksEntity;

@DI(instance = Instance.Singleton)
public class NoticesLogic {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(MethodHandles.lookup());
    /**
     * Get instance
     * @return instance
     */
    public static NoticesLogic get() {
        return Container.getComp(NoticesLogic.class);
    }
    /**
     * Select all notices on pager
     * @param limit limit
     * @param offset offset
     * @return list
     */
    public SendList selectAllNotices(Integer limit, Integer offset) {
        SendList sendList = new SendList();
        sendList.setLimit(limit);
        sendList.setOffset(offset);
        sendList.setItems(NoticesDao.get().selectAllWidthPager(limit, offset));
        sendList.setTotal(NoticesDao.get().selectCountAll());
        return sendList;
    }
    /**
     * Select notice
     * @param no no
     * @return entity
     */
    public NoticesEntity selectNotice(Integer no) {
        return NoticesDao.get().selectOnKey(no);
    }
    
    /**
     * Insert notice
     * @param entity entity
     * @return entity
     */
    public NoticesEntity insertNotice(NoticesEntity entity) {
        LOG.trace("insertNotice");
        return NoticesDao.get().insert(entity);
    }
    /**
     * Update notice
     * @param entity entity
     * @return entity
     */
    public NoticesEntity updateNotice(NoticesEntity entity) {
        LOG.trace("updateNotice");
        NoticesEntity exists = NoticesDao.get().selectOnKey(entity.getNo());
        if (exists == null) {
            return null;
        }
        return NoticesDao.get().update(entity);
    }
    
    /**
     * Delete notice
     * @param no no
     * @return deleated entity
     */
    public NoticesEntity deleteNotice(Integer no) {
        LOG.trace("deleteNotice");
        NoticesEntity exists = NoticesDao.get().selectOnKey(no);
        if (exists == null) {
            return null;
        }
        NoticesDao.get().delete(no);
        return exists;
    }

    /**
     * Select notices on now and not read
     * @param loginedUser loginedUser
     * @return notices
     */
    public List<NoticesEntity> selectMyNotices(AccessUser loginedUser) {
        if (loginedUser == null) {
            return NoticesDao.get().selectNowNotices();
        }
        return NoticesDao.get().selectMyNotices(loginedUser.getUserId());
    }
    
    /**
     * Set read mark
     * @param userId user id
     * @param no no
     * @param showNextTime showNextTime
     */
    public void readMark(Integer userId, Integer no, Integer showNextTime) {
        ReadMarksEntity entity = new ReadMarksEntity(no, userId);
        entity.setShowNextTime(showNextTime);
        ReadMarksDao.get().save(entity);
    }

}
