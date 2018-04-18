package org.support.project.knowledge.logic;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.support.project.common.config.INT_FLAG;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.test.Order;
import org.support.project.knowledge.TestCommon;
import org.support.project.knowledge.config.NotifyType;
import org.support.project.knowledge.dao.CommentsDao;
import org.support.project.knowledge.dao.NotifyConfigsDao;
import org.support.project.knowledge.entity.CommentsEntity;
import org.support.project.knowledge.entity.KnowledgesEntity;
import org.support.project.knowledge.entity.NotifyConfigsEntity;
import org.support.project.knowledge.vo.KnowledgeData;
import org.support.project.ormapping.common.DBUserPool;
import org.support.project.web.bean.LabelValue;
import org.support.project.web.entity.UsersEntity;

/**
 * Test for Notification logic.
 * @author Koda
 */
public class NotifyCommentLogicTest extends TestCommon {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(MethodHandles.lookup());
    
    /** テスト用ナレッジ1 */
    private static KnowledgesEntity knowledge1;
    /** テスト用ナレッジ2 */
    private static KnowledgesEntity knowledge2;
    /** テスト用ナレッジ3 */
    private static KnowledgesEntity knowledge3;
    /** テスト用ナレッジ4 */
    private static KnowledgesEntity knowledge4;
    
    /**
     * @BeforeClass
     * @throws Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        TestCommon.setUpBeforeClass();
        DBUserPool.get().setUser(loginedUser.getUserId());
        
        LOG.info("テストユーザ1でKnowledge登録");
        KnowledgesEntity knowledge = new KnowledgesEntity();
        
        knowledge.setTitle("テスト1");
        knowledge.setContent("テスト");
        knowledge.setPublicFlag(KnowledgeLogic.PUBLIC_FLAG_PUBLIC); // 公開
        KnowledgeData data = new KnowledgeData();
        data.setKnowledge(knowledge);
        knowledge1 = KnowledgeLogic.get().insert(data, loginedUser, false);
        
        LOG.info("テストユーザ1でKnowledge登録");
        knowledge = new KnowledgesEntity();
        knowledge.setTitle("テスト2");
        knowledge.setContent("テスト2");
        knowledge.setPublicFlag(KnowledgeLogic.PUBLIC_FLAG_PRIVATE); // 非公開
        data = new KnowledgeData();
        data.setKnowledge(knowledge);
        knowledge2 = KnowledgeLogic.get().insert(data, loginedUser, false);
        
        LOG.info("テストユーザ1でKnowledge登録");
        knowledge = new KnowledgesEntity();
        knowledge.setTitle("テスト3");
        knowledge.setContent("テスト3");
        knowledge.setPublicFlag(KnowledgeLogic.PUBLIC_FLAG_PROTECT); // 非公開
        List<LabelValue> targets = new ArrayList<>();
        LabelValue labelValue = new LabelValue();
        labelValue.setLabel(TargetLogic.ID_PREFIX_USER + loginedUser2.getUserId());
        labelValue.setValue(TargetLogic.ID_PREFIX_USER + loginedUser2.getUserId());
        targets.add(labelValue);
        data = new KnowledgeData();
        data.setKnowledge(knowledge);
        data.setViewers(targets);
        knowledge3 = KnowledgeLogic.get().insert(data, loginedUser, false);
        
        LOG.info("テストユーザ1でKnowledge登録");
        knowledge = new KnowledgesEntity();
        knowledge.setTitle("テスト4");
        knowledge.setContent("テスト4");
        knowledge.setPublicFlag(KnowledgeLogic.PUBLIC_FLAG_PROTECT); // 非公開
        targets = new ArrayList<>();
        labelValue = new LabelValue();
        labelValue.setLabel(TargetLogic.ID_PREFIX_GROUP + group.getGroupId());
        labelValue.setValue(TargetLogic.ID_PREFIX_GROUP + group.getGroupId());
        targets.add(labelValue);
        data = new KnowledgeData();
        data.setKnowledge(knowledge);
        data.setViewers(targets);
        knowledge4 = KnowledgeLogic.get().insert(data, loginedUser, false);
    }
    
    @Test
    @Order(order = 1)
    public void testNotify1() throws Exception {
        KnowledgesEntity knowledge = knowledge1;
        // 公開のナレッジにコメント登録
        CommentsEntity comment = new CommentsEntity();
        comment.setKnowledgeId(knowledge.getKnowledgeId());
        CommentsDao.get().save(comment);
        
        UsersEntity user = NotifyCommentLogic.get().getInsertUserOnComment(NotifyType.Desktop, comment, knowledge);
        Assert.assertNull(user); // 通知設定がセットされていないので、通知先は無し
        user = NotifyCommentLogic.get().getInsertUserOnComment(NotifyType.Mail, comment, knowledge);
        Assert.assertNull(user); // 通知設定がセットされていないので、通知先は無し
        
        List<UsersEntity> users = NotifyCommentLogic.get().getTargetUsersOnComment(NotifyType.Desktop, comment, knowledge);
        Assert.assertEquals(0, users.size());
        users = NotifyCommentLogic.get().getTargetUsersOnComment(NotifyType.Mail, comment, knowledge);
        Assert.assertEquals(0, users.size());
        
        knowledge = knowledge3;
        
        // 保護のナレッジにコメント登録
        comment = new CommentsEntity();
        comment.setKnowledgeId(knowledge.getKnowledgeId());
        CommentsDao.get().save(comment);
        
        user = NotifyCommentLogic.get().getInsertUserOnComment(NotifyType.Desktop, comment, knowledge);
        Assert.assertNull(user); // 通知設定がセットされていないので、通知先は無し
        user = NotifyCommentLogic.get().getInsertUserOnComment(NotifyType.Mail, comment, knowledge);
        Assert.assertNull(user); // 通知設定がセットされていないので、通知先は無し
        
        users = NotifyCommentLogic.get().getTargetUsersOnComment(NotifyType.Desktop, comment, knowledge);
        Assert.assertEquals(0, users.size());
        users = NotifyCommentLogic.get().getTargetUsersOnComment(NotifyType.Mail, comment, knowledge);
        Assert.assertEquals(0, users.size());
    }
    

    
    
    @Test
    @Order(order = 2)
    public void testNotify2() throws Exception {
        NotifyConfigsEntity notifyConfigs = new NotifyConfigsEntity();
        notifyConfigs.setUserId(loginedUser.getUserId());
        notifyConfigs.setNotifyMail(INT_FLAG.ON.getValue());
        notifyConfigs.setNotifyDesktop(INT_FLAG.ON.getValue());
        notifyConfigs.setMyItemComment(INT_FLAG.ON.getValue());
        notifyConfigs.setToItemComment(INT_FLAG.ON.getValue());
        NotifyConfigsDao.get().save(notifyConfigs);
        
        notifyConfigs = new NotifyConfigsEntity();
        notifyConfigs.setUserId(loginedUser2.getUserId());
        notifyConfigs.setNotifyMail(INT_FLAG.ON.getValue());
        notifyConfigs.setNotifyDesktop(INT_FLAG.ON.getValue());
        notifyConfigs.setMyItemComment(INT_FLAG.ON.getValue());
        notifyConfigs.setToItemComment(INT_FLAG.ON.getValue());
        NotifyConfigsDao.get().save(notifyConfigs);
        
        notifyConfigs = new NotifyConfigsEntity();
        notifyConfigs.setUserId(loginedUser3.getUserId());
        notifyConfigs.setNotifyMail(INT_FLAG.ON.getValue());
        notifyConfigs.setNotifyDesktop(INT_FLAG.ON.getValue());
        notifyConfigs.setMyItemComment(INT_FLAG.ON.getValue());
        notifyConfigs.setToItemComment(INT_FLAG.ON.getValue());
        NotifyConfigsDao.get().save(notifyConfigs);
        
        KnowledgesEntity knowledge = knowledge1;
        
        // 公開のナレッジにコメント登録
        CommentsEntity comment = new CommentsEntity();
        comment.setKnowledgeId(knowledge.getKnowledgeId());
        CommentsDao.get().save(comment);
        
        UsersEntity user = NotifyCommentLogic.get().getInsertUserOnComment(NotifyType.Desktop, comment, knowledge);
        Assert.assertEquals(loginedUser.getUserId(), user.getUserId());
        user = NotifyCommentLogic.get().getInsertUserOnComment(NotifyType.Mail, comment, knowledge);
        Assert.assertEquals(loginedUser.getUserId(), user.getUserId());
        
        List<UsersEntity> users = NotifyCommentLogic.get().getTargetUsersOnComment(NotifyType.Desktop, comment, knowledge);
        Assert.assertEquals(2, users.size());
        users = NotifyCommentLogic.get().getTargetUsersOnComment(NotifyType.Mail, comment, knowledge);
        Assert.assertEquals(2, users.size());
    }
    
    
    @Test
    @Order(order = 3)
    public void testNotify3() throws Exception {
        KnowledgesEntity knowledge = knowledge2;
        // 非公開のナレッジにコメント登録
        CommentsEntity comment = new CommentsEntity();
        comment.setKnowledgeId(knowledge.getKnowledgeId());
        CommentsDao.get().save(comment);
        
        UsersEntity user = NotifyCommentLogic.get().getInsertUserOnComment(NotifyType.Desktop, comment, knowledge);
        Assert.assertNull(user);
        user = NotifyCommentLogic.get().getInsertUserOnComment(NotifyType.Mail, comment, knowledge);
        Assert.assertNull(user);
        
        List<UsersEntity> users = NotifyCommentLogic.get().getTargetUsersOnComment(NotifyType.Desktop, comment, knowledge);
        Assert.assertEquals(0, users.size());
        users = NotifyCommentLogic.get().getTargetUsersOnComment(NotifyType.Mail, comment, knowledge);
        Assert.assertEquals(0, users.size());
    }
    
    @Test
    @Order(order = 4)
    public void testNotify4() throws Exception {
        KnowledgesEntity knowledge = knowledge3;
        // 保護のナレッジにコメント登録
        CommentsEntity comment = new CommentsEntity();
        comment.setKnowledgeId(knowledge.getKnowledgeId());
        CommentsDao.get().save(comment);
        
        UsersEntity user = NotifyCommentLogic.get().getInsertUserOnComment(NotifyType.Desktop, comment, knowledge);
        Assert.assertEquals(loginedUser.getUserId(), user.getUserId());
        user = NotifyCommentLogic.get().getInsertUserOnComment(NotifyType.Mail, comment, knowledge);
        Assert.assertEquals(loginedUser.getUserId(), user.getUserId());
        
        List<UsersEntity> users = NotifyCommentLogic.get().getTargetUsersOnComment(NotifyType.Desktop, comment, knowledge);
        Assert.assertEquals(1, users.size());
        users = NotifyCommentLogic.get().getTargetUsersOnComment(NotifyType.Mail, comment, knowledge);
        Assert.assertEquals(1, users.size());
    }


    @Test
    @Order(order = 5)
    public void testNotify5() throws Exception {
        DBUserPool.get().setUser(loginedUser2.getUserId());
        KnowledgesEntity knowledge = knowledge3;
        // 保護のナレッジにコメント登録
        CommentsEntity comment = new CommentsEntity();
        comment.setKnowledgeId(knowledge.getKnowledgeId());
        CommentsDao.get().save(comment);
        
        UsersEntity user = NotifyCommentLogic.get().getInsertUserOnComment(NotifyType.Desktop, comment, knowledge);
        Assert.assertEquals(loginedUser.getUserId(), user.getUserId());
        user = NotifyCommentLogic.get().getInsertUserOnComment(NotifyType.Mail, comment, knowledge);
        Assert.assertEquals(loginedUser.getUserId(), user.getUserId());
        
        List<UsersEntity> users = NotifyCommentLogic.get().getTargetUsersOnComment(NotifyType.Desktop, comment, knowledge);
        Assert.assertEquals(1, users.size());
        users = NotifyCommentLogic.get().getTargetUsersOnComment(NotifyType.Mail, comment, knowledge);
        Assert.assertEquals(1, users.size());
    }

    
    @Test
    @Order(order = 6)
    public void testNotify6() throws Exception {
        NotifyConfigsEntity notifyConfigs = new NotifyConfigsEntity();
        notifyConfigs.setUserId(loginedUser2.getUserId());
        notifyConfigs.setNotifyMail(INT_FLAG.ON.getValue());
        notifyConfigs.setNotifyDesktop(INT_FLAG.ON.getValue());
        notifyConfigs.setMyItemComment(INT_FLAG.ON.getValue());
        notifyConfigs.setToItemComment(INT_FLAG.OFF.getValue());
        NotifyConfigsDao.get().save(notifyConfigs);
        
        KnowledgesEntity knowledge = knowledge3;
        // 公開のナレッジにコメント登録
        CommentsEntity comment = new CommentsEntity();
        comment.setKnowledgeId(knowledge.getKnowledgeId());
        CommentsDao.get().save(comment);
        
        UsersEntity user = NotifyCommentLogic.get().getInsertUserOnComment(NotifyType.Desktop, comment, knowledge);
        Assert.assertEquals(loginedUser.getUserId(), user.getUserId());
        user = NotifyCommentLogic.get().getInsertUserOnComment(NotifyType.Mail, comment, knowledge);
        Assert.assertEquals(loginedUser.getUserId(), user.getUserId());
        
        List<UsersEntity> users = NotifyCommentLogic.get().getTargetUsersOnComment(NotifyType.Desktop, comment, knowledge);
        Assert.assertEquals(0, users.size());
        users = NotifyCommentLogic.get().getTargetUsersOnComment(NotifyType.Mail, comment, knowledge);
        Assert.assertEquals(0, users.size());
    }
    

    @Test
    @Order(order = 7)
    public void testNotify7() throws Exception {
        NotifyConfigsEntity notifyConfigs = new NotifyConfigsEntity();
        notifyConfigs.setUserId(groupuser1.getUserId());
        notifyConfigs.setNotifyMail(INT_FLAG.ON.getValue());
        notifyConfigs.setNotifyDesktop(INT_FLAG.ON.getValue());
        notifyConfigs.setMyItemComment(INT_FLAG.ON.getValue());
        notifyConfigs.setToItemComment(INT_FLAG.ON.getValue());
        NotifyConfigsDao.get().save(notifyConfigs);
        
        KnowledgesEntity knowledge = knowledge4;
        // 公開のナレッジにコメント登録
        CommentsEntity comment = new CommentsEntity();
        comment.setKnowledgeId(knowledge.getKnowledgeId());
        CommentsDao.get().save(comment);
        
        UsersEntity user = NotifyCommentLogic.get().getInsertUserOnComment(NotifyType.Desktop, comment, knowledge);
        Assert.assertEquals(loginedUser.getUserId(), user.getUserId());
        user = NotifyCommentLogic.get().getInsertUserOnComment(NotifyType.Mail, comment, knowledge);
        Assert.assertEquals(loginedUser.getUserId(), user.getUserId());
        
        List<UsersEntity> users = NotifyCommentLogic.get().getTargetUsersOnComment(NotifyType.Desktop, comment, knowledge);
        Assert.assertEquals(1, users.size());
        users = NotifyCommentLogic.get().getTargetUsersOnComment(NotifyType.Mail, comment, knowledge);
        Assert.assertEquals(1, users.size());
    }
    
    
}
