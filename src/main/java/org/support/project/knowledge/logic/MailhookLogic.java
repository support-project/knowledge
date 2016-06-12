package org.support.project.knowledge.logic;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Header;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;

import org.support.project.aop.Aspect;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.PasswordUtil;
import org.support.project.common.util.StringUtils;
import org.support.project.di.Container;
import org.support.project.knowledge.config.MailHookCondition;
import org.support.project.knowledge.dao.CommentsDao;
import org.support.project.knowledge.dao.KnowledgesDao;
import org.support.project.knowledge.dao.MailHookConditionsDao;
import org.support.project.knowledge.dao.MailHooksDao;
import org.support.project.knowledge.dao.MailPostsDao;
import org.support.project.knowledge.entity.CommentsEntity;
import org.support.project.knowledge.entity.KnowledgesEntity;
import org.support.project.knowledge.entity.MailHookConditionsEntity;
import org.support.project.knowledge.entity.MailHooksEntity;
import org.support.project.knowledge.entity.MailPostsEntity;
import org.support.project.knowledge.entity.TagsEntity;
import org.support.project.ormapping.common.DBUserPool;
import org.support.project.web.bean.LabelValue;
import org.support.project.web.bean.LoginedUser;
import org.support.project.web.dao.RolesDao;
import org.support.project.web.dao.UsersDao;
import org.support.project.web.entity.UsersEntity;

public class MailhookLogic {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(MailhookLogic.class);

    /**
     * MAIL HOOK のID DBとしては、MailHookは複数のメールアドレスで実行できる構成をとったが、 システムが受信するメールアドレスは1つと考えたほうが無難なのでIDを決め打ちする
     */
    public static final Integer MAIL_HOOK_ID = 1;

    private static final String MAIL_FOLDER = "INBOX";
    
    public static MailhookLogic get() {
        return Container.getComp(MailhookLogic.class);
    }

    /**
     * メール受信サーバーに接続できるかチェック
     * 
     * @param entity
     * @return
     */
    public boolean connect(MailHooksEntity entity) {
        Properties props = new Properties();
        props.setProperty("mail.store.protocol", "imaps");
        props.put("mail.imaps.ssl.trust", "*");
        try {
            String host = entity.getMailHost();
            int port = entity.getMailPort();
            String user = entity.getMailUser();
            String pass = PasswordUtil.decrypt(entity.getMailPass(), entity.getMailPassSalt());
            Session session = Session.getInstance(props, null);
            Store store = session.getStore();
            store.connect(host, port, user, pass);
            Folder inbox = store.getFolder(MAIL_FOLDER);
            inbox.open(Folder.READ_ONLY);
            LOG.info("[Mail Count] " + inbox.getMessageCount());
            inbox.close(true);
            return true;
        } catch (MessagingException | InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException
                | BadPaddingException e) {
            LOG.warn(e);
            return false;
        }
    }
    
    /**
     * メール受信設定に従いメールを取得し、条件にあったメールがあればKnowledgeに投稿する
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void postFromMail() {
        MailHooksEntity entity = MailHooksDao.get().selectOnKey(MAIL_HOOK_ID);
        if (entity == null) {
            LOG.info("Mail hook config is undefined.");
            return;
        }
        
        List<MailHookConditionsEntity> conditions = MailHookConditionsDao.get().selectOnHookId(MAIL_HOOK_ID);
        if (conditions == null || conditions.isEmpty()) {
            LOG.info("Mail hook config conditions is undefined.");
            return;
        }
        
        Properties props = new Properties();
        props.setProperty("mail.store.protocol", "imaps");
        props.put("mail.imaps.ssl.trust", "*");
        try {
            String host = entity.getMailHost();
            int port = entity.getMailPort();
            String user = entity.getMailUser();
            String pass = PasswordUtil.decrypt(entity.getMailPass(), entity.getMailPassSalt());
            Session session = Session.getInstance(props, null);
            Store store = session.getStore();
            store.connect(host, port, user, pass);
            Folder inbox = store.getFolder(MAIL_FOLDER);
            inbox.open(Folder.READ_WRITE);

            int count = inbox.getMessageCount();
            LOG.info("Read mail count: " + count);

            if (count > 0) {
                Message[] messages = inbox.getMessages();
                for (Message msg : messages) {
                    checkConditionsAndPost(msg, conditions);
                }
                Flags deleted = new Flags(Flags.Flag.DELETED);
                inbox.setFlags(messages, deleted, true);
            }
            inbox.close(true);
        } catch (Exception e) {
            LOG.error("Mail Hook Error", e);
        }
    }
    
    /**
     * メールから投稿
     * @param msg
     * @param condition
     * @throws Exception 
     */
    private void addData(Message msg, MailHookConditionsEntity condition) throws Exception {
        if (LOG.isDebugEnabled()) {
            LOG.debug("SUBJECT:" + msg.getSubject());
            LOG.debug("CONTENT:" + getContent(msg));
            Enumeration<Header> headers = msg.getAllHeaders();
            while (headers.hasMoreElements()) {
                Header header = (Header) headers.nextElement();
                LOG.debug(header.getName() + " : " + header.getValue());
            }
        }
        String msgId = getHeader(msg, "Message-ID");
        String references = getHeader(msg, "References");
        if (LOG.isDebugEnabled()) {
            LOG.debug("[Message-ID] " + msgId);
            LOG.debug("[References] " + references);
        }
        
        // 現在操作しているMessage-IDが既に登録されている？
        MailPostsEntity exists = MailPostsDao.get().selectOnKey(msgId);
        if (exists != null) {
            LOG.info("[Message-ID] " + msgId + " exists. skip add data.");
            return;
        }
        
        LoginedUser loginedUser = new LoginedUser();
        UsersEntity user = null;
        Address[] in = msg.getFrom();
        for (Address address : in) {
            if (address instanceof InternetAddress) {
                InternetAddress a = (InternetAddress) address;
                String from = a.getAddress();
                user = UsersDao.get().selectOnUserKey(from);
            } else {
                String from = address.toString();
                user = UsersDao.get().selectOnUserKey(from);
            }
            if (user != null) {
                break;
            }
        }
        
        if (user == null) {
            Integer userId = condition.getProcessUser();
            DBUserPool.get().setUser(userId);
            user = UsersDao.get().selectOnKey(userId);
            if (user == null) {
                LOG.debug("Register user [" + userId + "] is not found.");
                return;
            }
            loginedUser.setLoginUser(user);
            loginedUser.setRoles(RolesDao.get().selectOnUserKey(user.getUserKey()));
        } else {
            DBUserPool.get().setUser(user.getUserId());
            loginedUser.setLoginUser(user);
            loginedUser.setRoles(RolesDao.get().selectOnUserKey(user.getUserKey()));
        }
        
        if (StringUtils.isNotEmpty(references)) {
            String[] checks = references.split(" ");
            for (String string : checks) {
                exists = MailPostsDao.get().selectOnKey(string.trim());
                if (exists != null) {
                    LOG.debug("[Reference] " + string.trim() + " exists.");
                    break;
                }
            }
        }
        if (exists == null) {
            // 新規ナレッジを登録
            addKnowkedge(msg, condition, loginedUser, msgId);
        } else {
            // 既存のナレッジへの返信なので、コメントとして登録
            KnowledgesEntity parent = null;
            if (exists.getPostKind().intValue() == 1) {
                parent = KnowledgesDao.get().selectOnKey(exists.getId());
            } else if (exists.getPostKind().intValue() == 2) {
                CommentsEntity comment = CommentsDao.get().selectOnKey(exists.getId());
                if (comment != null) {
                    parent = KnowledgesDao.get().selectOnKey(comment.getKnowledgeId());
                }
            }
            if (parent == null) {
                // 新規ナレッジを登録
                addKnowkedge(msg, condition, loginedUser, msgId);
            } else {
                // 新規コメントを登録
                addCommnet(msg, condition, loginedUser, msgId, parent);
            }
        }
    }
    
    /**
     * メールからコメントを登録
     * @param msg
     * @param condition
     * @param loginedUser
     * @param msgId
     * @param parent
     * @throws Exception 
     * @throws MessagingException 
     */
    private void addCommnet(Message msg, MailHookConditionsEntity condition, LoginedUser loginedUser, String msgId, KnowledgesEntity parent)
            throws MessagingException, Exception {
        LOG.debug("Add Comment");
        
        CommentsEntity entity = 
                KnowledgeLogic.get().saveComment(parent.getKnowledgeId(), getContent(msg), new ArrayList<>(), loginedUser);
        
        MailPostsEntity post = new MailPostsEntity();
        post.setMessageId(msgId);
        post.setPostKind(2);
        post.setId(entity.getCommentNo());
        post.setSender(getFrom(msg));
        MailPostsDao.get().save(post);

        LOG.info("Commnet[" + entity.getCommentNo() + "]  was added.");
    }


    /**
     * メールからKnowledgeを新規登録
     * @param msg
     * @param condition
     * @param loginedUser 
     * @throws Exception 
     */
    private void addKnowkedge(Message msg, MailHookConditionsEntity condition, LoginedUser loginedUser, String msgId) throws Exception {
        LOG.debug("Add Knowledge");
        
        KnowledgesEntity entity = new KnowledgesEntity();
        entity.setTitle(msg.getSubject());
        entity.setContent(getContent(msg));
        entity.setTypeId(-100);
        entity.setPublicFlag(condition.getPublicFlag());
        List<TagsEntity> tagList = null;
        if (StringUtils.isNotEmpty(condition.getTags())) {
            tagList = KnowledgeLogic.get().manegeTags(condition.getTags());
        }
        List<LabelValue> viewers = null;
        if (StringUtils.isNotEmpty(condition.getViewers())) {
            viewers = TargetLogic.get().selectTargets(condition.getViewers().split(","));
        }
        List<LabelValue> editors = null;
        if (StringUtils.isNotEmpty(condition.getEditors())) {
            editors = TargetLogic.get().selectTargets(condition.getEditors().split(","));
        }
        entity = KnowledgeLogic.get().insert(entity, tagList, null, viewers, editors, null, loginedUser);
        
        MailPostsEntity post = new MailPostsEntity();
        post.setMessageId(msgId);
        post.setPostKind(1);
        post.setId(entity.getKnowledgeId());
        post.setSender(getFrom(msg));
        MailPostsDao.get().save(post);

        LOG.info("Knowledge[" + entity.getKnowledgeId() + "] " + entity.getTitle() + " was added.");
    }
    
    /**
     * 送信者のアドレスを取得
     * @param msg
     * @return
     * @throws MessagingException
     */
    private String getFrom(Message msg) throws MessagingException {
        Address[] in = msg.getFrom();
        StringBuilder builder = new StringBuilder();
        for (Address address : in) {
            if (builder.length() > 0) {
                builder.append(", ");
            }
            if (address instanceof InternetAddress) {
                InternetAddress a = (InternetAddress) address;
                String from = a.getAddress();
                builder.append(from);
            } else {
                String from = address.toString();
                builder.append(from);
            }
        }
        return builder.toString();
    }
    
    
    /**
     * メールのヘッダーの値を取得
     * 複数ある場合は最初の一つを取得する
     * @param msg
     * @param string
     * @return
     * @throws MessagingException
     */
    private String getHeader(Message msg, String string) throws MessagingException {
        String[] values = msg.getHeader(string);
        if (values != null && values.length > 0) {
            return values[0];
        }
        return "";
    }
    
    /**
     * メールの本文の取得
     * @param msg
     * @return
     * @throws IOException
     * @throws MessagingException
     */
    private String getContent(Message msg) throws IOException, MessagingException {
        if (msg.getContent() instanceof Multipart) {
            Multipart mp = (Multipart) msg.getContent();
            BodyPart bp = mp.getBodyPart(0);
            return bp.getContent().toString();
        } else {
            return msg.getContent().toString();
        }
    }

    /**
     * メールから投稿の条件にあうメールかをチェックし、条件にあっている場合その条件を返す
     * @param msg
     * @param conditions
     * @return
     * @throws Exception 
     */
    private void checkConditionsAndPost(Message msg, List<MailHookConditionsEntity> conditions)
            throws Exception {
        if (LOG.isDebugEnabled()) {
            Address[] in = msg.getFrom();
            for (Address address : in) {
                LOG.debug("FROM:" + address.toString());
            }
            Address[] to = msg.getAllRecipients();
            for (Address address : to) {
                LOG.debug("Recipient:" + address.toString());
            }
            LOG.debug("SENT DATE:" + msg.getSentDate());
        }
        
        for (MailHookConditionsEntity condition : conditions) {
            if (checkCondition(msg, condition)) {
                addData(msg, condition);
            }
        }
    }
    

    /**
     * メールがメールから登録の条件に合致するかチェック
     * @param msg
     * @param condition
     * @return
     * @throws MessagingException
     */
    private boolean checkCondition(Message msg, MailHookConditionsEntity condition) throws MessagingException {
        if (condition.getConditionKind().intValue() == MailHookCondition.Recipient.getValue()) {
            Address[] to = msg.getAllRecipients();
            for (Address address : to) {
                if (address instanceof InternetAddress) {
                    InternetAddress a = (InternetAddress) address;
                    if (a.getAddress().equals(condition.getCondition())) {
                        return true;
                    }
                } else {
                    if (address.toString().indexOf(condition.getCondition()) != -1) {
                        return true;
                    }
                }
            }
        } else if (condition.getConditionKind().intValue() == MailHookCondition.Title.getValue()) {
            if (StringUtils.isNotEmpty(msg.getSubject()) && msg.getSubject().indexOf(condition.getCondition()) != -1) {
                return true;
            }
        }
        return false;
    }

    /**
     * メールからの投稿の条件を保存する
     * @param hooksEntity
     * @param editors 
     * @param groups 
     * @return
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public MailHookConditionsEntity saveCondition(MailHookConditionsEntity hooksEntity) {
        if (hooksEntity.getConditionNo().intValue() == -1) {
            int conditionNo = MailHookConditionsDao.get().nextConditionNo(hooksEntity.getHookId());
            hooksEntity.setConditionNo(conditionNo);
        }
        hooksEntity = MailHookConditionsDao.get().save(hooksEntity);
        return hooksEntity;
    }
    
    
    /**
     * メール送信の条件の削除
     * @param conditionNo
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void deleteHookCondition(Integer conditionNo) {
        MailHookConditionsDao.get().delete(conditionNo, MAIL_HOOK_ID);
    }
    
    /**
     * Hookを全て削除
     * @param mailHookId
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void removeHook(Integer mailHookId) {
        List<MailHookConditionsEntity> conditions = MailHookConditionsDao.get().selectOnHookId(mailHookId);
        for (MailHookConditionsEntity mailHookConditionsEntity : conditions) {
            MailHookConditionsDao.get().physicalDelete(mailHookConditionsEntity);
        }
        MailHooksDao.get().delete(mailHookId);
    }

}
