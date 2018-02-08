package org.support.project.knowledge.dao;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.support.project.common.config.INT_FLAG;
import org.support.project.common.test.AssertEx;
import org.support.project.common.test.Order;
import org.support.project.knowledge.TestCommon;
import org.support.project.knowledge.entity.NotifyConfigsEntity;
import org.support.project.web.entity.UsersEntity;

/**
 * 通知のDaoのテスト
 * @author Koda
 */
public class NotifyConfigsDaoTest extends TestCommon {
    private static final int LIMIT = 100;
    private static final int OFFSET = 0;
    
    /**
     * @BeforeClass
     * @throws Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        TestCommon.setUpBeforeClass();
    }
    
    @Test
    @Order(order = 1)
    public void testNotify1() throws Exception {
        NotifyConfigsEntity notifyConfigs = new NotifyConfigsEntity();
        notifyConfigs.setUserId(loginedUser.getUserId());
        notifyConfigs.setNotifyDesktop(INT_FLAG.ON.getValue());
        notifyConfigs.setToItemComment(INT_FLAG.ON.getValue());
        NotifyConfigsDao.get().save(notifyConfigs);
        
        List<UsersEntity> users = NotifyConfigsDao.get().getNotifyDesktopUsersOnPublicComment(LIMIT, OFFSET);
        AssertEx.eq(1, users.size());
    }
    @Test
    @Order(order = 2)
    public void testNotify2() throws Exception {
        NotifyConfigsEntity notifyConfigs = new NotifyConfigsEntity();
        notifyConfigs.setUserId(loginedUser.getUserId());
        notifyConfigs.setNotifyDesktop(INT_FLAG.ON.getValue());
        notifyConfigs.setToItemComment(INT_FLAG.ON.getValue());
        notifyConfigs.setToItemIgnorePublic(INT_FLAG.ON.getValue());
        NotifyConfigsDao.get().save(notifyConfigs);
        
        List<UsersEntity> users = NotifyConfigsDao.get().getNotifyDesktopUsersOnPublicComment(LIMIT, OFFSET);
        AssertEx.eq(0, users.size());
    }
    @Test
    @Order(order = 3)
    public void testNotify3() throws Exception {
        NotifyConfigsEntity notifyConfigs = new NotifyConfigsEntity();
        notifyConfigs.setUserId(loginedUser.getUserId());
        notifyConfigs.setNotifyDesktop(INT_FLAG.ON.getValue());
        notifyConfigs.setToItemComment(INT_FLAG.ON.getValue());
        NotifyConfigsDao.get().save(notifyConfigs);
        
        notifyConfigs = new NotifyConfigsEntity();
        notifyConfigs.setUserId(loginedUser2.getUserId());
        notifyConfigs.setNotifyDesktop(INT_FLAG.ON.getValue());
        notifyConfigs.setToItemComment(INT_FLAG.ON.getValue());
        NotifyConfigsDao.get().save(notifyConfigs);
        
        List<UsersEntity> users = NotifyConfigsDao.get().getNotifyDesktopUsersOnPublicComment(1, OFFSET);
        AssertEx.eq(1, users.size());
        AssertEx.eq(loginedUser.getUserId(), users.get(0).getUserId());
    }
    @Test
    @Order(order = 4)
    public void testNotify4() throws Exception {
        NotifyConfigsEntity notifyConfigs = new NotifyConfigsEntity();
        notifyConfigs.setUserId(loginedUser.getUserId());
        notifyConfigs.setNotifyDesktop(INT_FLAG.ON.getValue());
        notifyConfigs.setToItemComment(INT_FLAG.ON.getValue());
        NotifyConfigsDao.get().save(notifyConfigs);
        
        notifyConfigs = new NotifyConfigsEntity();
        notifyConfigs.setUserId(loginedUser2.getUserId());
        notifyConfigs.setNotifyDesktop(INT_FLAG.ON.getValue());
        notifyConfigs.setToItemComment(INT_FLAG.ON.getValue());
        NotifyConfigsDao.get().save(notifyConfigs);
        
        List<UsersEntity> users = NotifyConfigsDao.get().getNotifyDesktopUsersOnPublicComment(1, 1);
        AssertEx.eq(1, users.size());
        AssertEx.eq(loginedUser2.getUserId(), users.get(0).getUserId());
    }
    
    
    
    @Test
    @Order(order = 5)
    public void testNotify5() throws Exception {
        NotifyConfigsEntity notifyConfigs = new NotifyConfigsEntity();
        notifyConfigs.setUserId(loginedUser.getUserId());
        notifyConfigs.setNotifyMail(INT_FLAG.ON.getValue());
        notifyConfigs.setToItemComment(INT_FLAG.ON.getValue());
        NotifyConfigsDao.get().save(notifyConfigs);
        
        List<UsersEntity> users = NotifyConfigsDao.get().getNotifyMailUsersOnPublicComment(LIMIT, OFFSET);
        AssertEx.eq(1, users.size());
    }
    @Test
    @Order(order = 6)
    public void testNotify6() throws Exception {
        NotifyConfigsEntity notifyConfigs = new NotifyConfigsEntity();
        notifyConfigs.setUserId(loginedUser.getUserId());
        notifyConfigs.setNotifyMail(INT_FLAG.ON.getValue());
        notifyConfigs.setToItemComment(INT_FLAG.ON.getValue());
        notifyConfigs.setToItemIgnorePublic(INT_FLAG.ON.getValue());
        NotifyConfigsDao.get().save(notifyConfigs);
        
        List<UsersEntity> users = NotifyConfigsDao.get().getNotifyMailUsersOnPublicComment(LIMIT, OFFSET);
        AssertEx.eq(0, users.size());
    }
    @Test
    @Order(order = 7)
    public void testNotify7() throws Exception {
        NotifyConfigsEntity notifyConfigs = new NotifyConfigsEntity();
        notifyConfigs.setUserId(loginedUser.getUserId());
        notifyConfigs.setNotifyMail(INT_FLAG.ON.getValue());
        notifyConfigs.setToItemComment(INT_FLAG.ON.getValue());
        NotifyConfigsDao.get().save(notifyConfigs);
        
        notifyConfigs = new NotifyConfigsEntity();
        notifyConfigs.setUserId(loginedUser2.getUserId());
        notifyConfigs.setNotifyMail(INT_FLAG.ON.getValue());
        notifyConfigs.setToItemComment(INT_FLAG.ON.getValue());
        NotifyConfigsDao.get().save(notifyConfigs);
        
        List<UsersEntity> users = NotifyConfigsDao.get().getNotifyMailUsersOnPublicComment(1, OFFSET);
        AssertEx.eq(1, users.size());
        AssertEx.eq(loginedUser.getUserId(), users.get(0).getUserId());
    }
    @Test
    @Order(order = 8)
    public void testNotify8() throws Exception {
        NotifyConfigsEntity notifyConfigs = new NotifyConfigsEntity();
        notifyConfigs.setUserId(loginedUser.getUserId());
        notifyConfigs.setNotifyMail(INT_FLAG.ON.getValue());
        notifyConfigs.setToItemComment(INT_FLAG.ON.getValue());
        NotifyConfigsDao.get().save(notifyConfigs);
        
        notifyConfigs = new NotifyConfigsEntity();
        notifyConfigs.setUserId(loginedUser2.getUserId());
        notifyConfigs.setNotifyMail(INT_FLAG.ON.getValue());
        notifyConfigs.setToItemComment(INT_FLAG.ON.getValue());
        NotifyConfigsDao.get().save(notifyConfigs);
        
        List<UsersEntity> users = NotifyConfigsDao.get().getNotifyMailUsersOnPublicComment(1, 1);
        AssertEx.eq(1, users.size());
        AssertEx.eq(loginedUser2.getUserId(), users.get(0).getUserId());
    }    

}
