package org.support.project.knowledge.logic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.mail.Address;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Header;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeUtility;

import org.support.project.aop.Aspect;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.FileUtil;
import org.support.project.common.util.PasswordUtil;
import org.support.project.common.util.StringUtils;
import org.support.project.di.Container;
import org.support.project.knowledge.config.AppConfig;
import org.support.project.knowledge.config.MailHookCondition;
import org.support.project.knowledge.dao.CommentsDao;
import org.support.project.knowledge.dao.KnowledgeFilesDao;
import org.support.project.knowledge.dao.KnowledgesDao;
import org.support.project.knowledge.dao.MailHookConditionsDao;
import org.support.project.knowledge.dao.MailHooksDao;
import org.support.project.knowledge.dao.MailPostsDao;
import org.support.project.knowledge.dao.MailPropertiesDao;
import org.support.project.knowledge.dao.TagsDao;
import org.support.project.knowledge.entity.CommentsEntity;
import org.support.project.knowledge.entity.KnowledgeFilesEntity;
import org.support.project.knowledge.entity.KnowledgesEntity;
import org.support.project.knowledge.entity.MailHookConditionsEntity;
import org.support.project.knowledge.entity.MailHooksEntity;
import org.support.project.knowledge.entity.MailPostsEntity;
import org.support.project.knowledge.entity.MailPropertiesEntity;
import org.support.project.knowledge.entity.TagsEntity;
import org.support.project.knowledge.vo.KnowledgeData;
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
     * MAIL HOOK のID DBとしては、MailHookは複数のメールアドレスで実行できる構成をとったが、 
     * システムが受信するメールアドレスは1つと考えたほうが無難なのでIDを決め打ちする
     */
    public static final Integer MAIL_HOOK_ID = 1;

    private static final String MAIL_FOLDER = "INBOX";
    
    public static MailhookLogic get() {
        return Container.getComp(MailhookLogic.class);
    }
    
    /**
     * メールセッションのデバッグ出力を行うか
     */
    private static final boolean MAIL_SESSION_DEBUG = false;
    /**
     * メールを受信後、削除するか
     */
    private static final boolean DELETE_READED_MAIL = true;
    
    
    /**
     * メールサーバーと接続
     * @param entity
     * @return
     * @throws MessagingException
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    private Store getStore(MailHooksEntity entity) throws MessagingException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        // TODO MailHooksEntity の protocol は必要無かった（プロパティで設定する）ため、いずれ削除する
        Properties props = new Properties();
        
        List<MailPropertiesEntity> properties = MailPropertiesDao.get().selectOnHookId(MAIL_HOOK_ID);
        if (properties == null) {
            // v1.10.0以前のデフォルト値
          props.setProperty("mail.store.protocol", "imaps");
          props.put("mail.imaps.ssl.trust", "*");
          
          /* IMAPS無しのサンプル
          props.setProperty("mail.store.protocol", "imap");
          String protocol = "mail.imap";
          props.setProperty(protocol + ".starttls.enable", "false");
          props.setProperty(protocol + ".ssl.enable", "false");
          props.setProperty("mail.imap.auth.ntlm.disable", "true");
          props.setProperty("mail.imap.auth.plain.disable", "true");
          props.setProperty("mail.imap.auth.gssapi.disable", "true");
          */
        } else {
            for (MailPropertiesEntity property : properties) {
                props.setProperty(property.getPropertyKey(), property.getPropertyValue());
            }
        }
        String host = entity.getMailHost();
        int port = entity.getMailPort();
        String user = entity.getMailUser();
        String pass = PasswordUtil.decrypt(entity.getMailPass(), entity.getMailPassSalt());
        Session session = Session.getInstance(props, null);
        session.setDebug(MAIL_SESSION_DEBUG);
        Store store = session.getStore();
        store.connect(host, port, user, pass);
        
        return store;
    }
    
    
    
    /**
     * メール受信サーバーに接続できるかチェック
     * 
     * @param entity
     * @return
     */
    public boolean connect(MailHooksEntity entity) {
        try {
            Store store = getStore(entity);
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
        
        try {
            Store store = getStore(entity);
            Folder inbox = store.getFolder(MAIL_FOLDER);
            inbox.open(Folder.READ_WRITE);

            int count = inbox.getMessageCount();
            LOG.info("Read mail count: " + count);

            if (count > 0) {
                Message[] messages = inbox.getMessages();
                for (Message msg : messages) {
                    checkConditionsAndPost(msg, conditions);
                }
                if (DELETE_READED_MAIL) {
                    LOG.info("Remove all readed mails.");
                    Flags deleted = new Flags(Flags.Flag.DELETED);
                    inbox.setFlags(messages, deleted, true);
                }
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
            @SuppressWarnings("unchecked")
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
        
        // 登録者の取得
        LoginedUser loginedUser = new LoginedUser();
        UsersEntity user = null;
        Address[] in = msg.getFrom();
        for (Address address : in) {
            if (address instanceof InternetAddress) {
                InternetAddress a = (InternetAddress) address;
                String from = a.getAddress();
                user = UsersDao.get().selectOnMail(from);
            } else {
                String from = address.toString();
                user = UsersDao.get().selectOnMail(from);
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
        
        
        // 現在操作しているMessage-IDが既に登録されている？
        MailPostsEntity exists = MailPostsDao.get().selectOnKey(msgId);
        if (exists != null) {
            if (exists.getPostKind() == 1) {
                // Knowledge本体
                KnowledgesEntity knowledge = KnowledgesDao.get().selectOnKey(exists.getId());
                if (knowledge != null) {
                    LOG.info("[Message-ID] " + msgId + " exists. so, update tagets.");
                    updateTargetsAndTags(knowledge, condition, loginedUser);
                }
            } else {
                LOG.info("[Message-ID] " + msgId + " exists. skip add data.");
            }
            // それ以降の処理は実施しないで終了
            return;
        }
        
        // 新規登録するナレッジ？それともコメント？
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
     * 既に登録されているKnowledgeに対し、別のメールから投稿の条件にヒットした場合、
     * その条件に書かれている、参照範囲／共同編集者、タグの設定を追加する
     * 
     * TODO メールから投稿の条件に2件ヒットした場合に、片方の公開範囲が「公開」でもう片方が「保護」の場合、
     * 公開範囲の設定は、先勝ちになっている。それで良いかな？
     * 
     * @param knowledge
     * @param condition
     * @param loginedUser 
     * @throws Exception 
     */
    private void updateTargetsAndTags(KnowledgesEntity knowledge, MailHookConditionsEntity condition, LoginedUser loginedUser) throws Exception {
        // タグ
        List<TagsEntity> tagList = null;
        if (StringUtils.isNotEmpty(condition.getTags())) {
            List<TagsEntity> addTagList = KnowledgeLogic.get().manegeTags(condition.getTags());
            List<TagsEntity> existsTagList = TagsDao.get().selectOnKnowledgeId(knowledge.getKnowledgeId());
            List<TagsEntity> temp = new ArrayList<>();
            if (addTagList != null) {
                for (TagsEntity tag : addTagList) {
                    boolean exixts = false;
                    for (TagsEntity ext : existsTagList) {
                        if (tag.getTagId().intValue() == ext.getTagId().intValue()) {
                            exixts = true;
                        }
                    }
                    if (!exixts) {
                        temp.add(tag);
                    }
                }
            }
            existsTagList.addAll(temp);
            tagList = existsTagList;
        }
        
        // 閲覧者
        List<LabelValue> viewers = null;
        if (StringUtils.isNotEmpty(condition.getViewers())) {
            List<LabelValue> addViewers = TargetLogic.get().selectTargets(condition.getViewers().split(","));
            List<LabelValue> existsViewers = TargetLogic.get().selectTargetsOnKnowledgeId(knowledge.getKnowledgeId());
            List<LabelValue> temp = new ArrayList<>();
            if (addViewers != null) {
                for (LabelValue target : addViewers) {
                    boolean exixts = false;
                    for (LabelValue ext : existsViewers) {
                        if (target.getValue().equals(ext.getValue())) {
                            exixts = true;
                        }
                    }
                    if (!exixts) {
                        temp.add(target);
                    }
                }
            }
            existsViewers.addAll(temp);
            viewers = existsViewers;
        }
        
        // 編集者
        List<LabelValue> editors = null;
        if (StringUtils.isNotEmpty(condition.getEditors())) {
            List<LabelValue> addEditors = TargetLogic.get().selectTargets(condition.getEditors().split(","));
            List<LabelValue> existsEditors = TargetLogic.get().selectEditorsOnKnowledgeId(knowledge.getKnowledgeId());
            List<LabelValue> temp = new ArrayList<>();
            if (addEditors != null) {
                for (LabelValue target : addEditors) {
                    boolean exixts = false;
                    for (LabelValue ext : existsEditors) {
                        if (target.getValue().equals(ext.getValue())) {
                            exixts = true;
                        }
                    }
                    if (!exixts) {
                        temp.add(target);
                    }
                }
            }
            existsEditors.addAll(temp);
            editors = existsEditors;
        }
        
        KnowledgeData data = new KnowledgeData();
        data.setKnowledge(knowledge);
        data.setTags(tagList);
        data.setViewers(viewers);
        data.setEditors(editors);
        
        KnowledgesEntity entity = KnowledgeLogic.get().update(data, loginedUser);
        LOG.info("Knowledge[" + entity.getKnowledgeId() + "] " + entity.getTitle() + " was updated.");
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
        String content = getContent(msg);
        List<Long> fileNos = addAtachFiles(msg);
        CommentsEntity entity = 
                KnowledgeLogic.get().saveComment(parent.getKnowledgeId(), content, fileNos, loginedUser);
        
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
        List<Long> fileNos = addAtachFiles(msg);
        
        KnowledgeData data = new KnowledgeData();
        data.setKnowledge(entity);
        data.setTags(tagList);
        data.setViewers(viewers);
        data.setEditors(editors);
        data.setFileNos(fileNos);
        
        entity = KnowledgeLogic.get().insert(data, loginedUser);
        
        MailPostsEntity post = new MailPostsEntity();
        post.setMessageId(msgId);
        post.setPostKind(1);
        post.setId(entity.getKnowledgeId());
        post.setSender(getFrom(msg));
        MailPostsDao.get().save(post);

        LOG.info("Knowledge[" + entity.getKnowledgeId() + "] " + entity.getTitle() + " was added.");
    }
    
    /**
     * メッセージの中の添付ファイルを取得し保存する
     * @param msg
     * @return
     * @throws IOException 
     * @throws MessagingException 
     */
    private List<Long> addAtachFiles(Message msg) throws IOException, MessagingException {
        List<Long> fileNos = new ArrayList<>();
        if (msg.getContent() instanceof Multipart) {
            final Multipart multiPart = (Multipart) msg.getContent();
            for (int indexPart = 0; indexPart < multiPart.getCount(); indexPart++) {
                final Part part = multiPart.getBodyPart(indexPart);
                final String disposition = part.getDisposition();
                if (Part.ATTACHMENT.equals(disposition) || Part.INLINE.equals(disposition)) {
                    String fileName = part.getFileName();
                    if (fileName != null) {
                        fileName = MimeUtility.decodeText(fileName);
                        InputStream is = part.getInputStream();
                        // ファイル保存
                        Long fileNo = addAttach(part, fileName, is);
                        LOG.debug("[" + indexPart + "]" + fileName);
                        if (fileNo != null) {
                            fileNos.add(fileNo);
                        }
                    }
                }
            }
        }
        
        return fileNos;
    }
    
    /**
     * 添付ファイル保存
     * @param part
     * @param fileName
     * @param is
     * @return 
     * @throws MessagingException
     * @throws IOException
     */
    private Long addAttach(final Part part, String fileName, InputStream is) throws MessagingException, IOException {
        if (is == null) {
            return null;
        }
        
        File temp = new File(AppConfig.get().getTmpPath());
        if (!temp.exists()) {
            temp.mkdirs();
        }
        String uuid = UUID.randomUUID().toString();
        File copy = new File(temp, uuid);
        FileUtil.copy(is, new FileOutputStream(copy));
        is.close();
        
        FileInputStream in = null;
        try {
            in = new FileInputStream(copy);
            KnowledgeFilesEntity entity = new KnowledgeFilesEntity();
            entity.setFileName(fileName);
            entity.setFileSize(new Double(copy.length()));
            entity.setFileBinary(in);
            entity.setParseStatus(0);
            entity = KnowledgeFilesDao.get().insert(entity);
            return entity.getFileNo();
        } finally {
            if (in != null) {
                in.close();
            }
            FileUtil.delete(copy);
        }
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
     * @throws Exception 
     */
    private String getContent(Message msg) throws Exception {
        String content;
        if (msg.getContent() instanceof Multipart) {
            Multipart multiPart = (Multipart) msg.getContent();
            content = readContent(multiPart);
        } else {
            content = msg.getContent().toString();
        }
        // HTMLメールの場合、タグを消して本文のみ抽出
        return MailLogic.get().getMailContent(content);
    }
    
    /**
     * Multipart から本文のテキストを読み込む
     * @param multiPart
     * @return
     * @throws MessagingException
     * @throws IOException
     */
    private String readContent(Multipart multiPart) throws MessagingException, IOException {
        final StringBuilder builder = new StringBuilder();
        for (int indexPart = 0; indexPart < multiPart.getCount(); indexPart++) {
            final Part part = multiPart.getBodyPart(indexPart);
            final String disposition = part.getDisposition();
            if (!Part.ATTACHMENT.equals(disposition) && !Part.INLINE.equals(disposition)) {
                Object content = part.getContent();
                if (content instanceof String) {
                    builder.append(part.getContent().toString());
                } else if (content instanceof Multipart) {
                    return readContent((Multipart) content);
                }
                break; // メッセージは初めの1つだけで良いっぽい
            }
        }
        return builder.toString();
    }

    /**
     * メールから投稿の条件にあうメールかをチェックし、条件にあっている場合その条件を返す
     * @param msg
     * @param conditions
     * @return
     * @throws Exception 
     */
    public void checkConditionsAndPost(Message msg, List<MailHookConditionsEntity> conditions)
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
                LOG.info("Post Knowledge from a mail. " + msg.getSubject());
                addData(msg, condition);
            } else {
                LOG.warn("Receive mail is not target condition. " + msg.getSubject());
                LOG.warn(getContent(msg));
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
        // 投稿者制限がある場合、投稿可能なメールアドレスかチェック
        if (condition.getPostLimit() != null && condition.getPostLimit().intValue() != 0) {
            Address[] in = msg.getFrom();
            boolean target = false;
            for (Address address : in) {
                String from;
                if (address instanceof InternetAddress) {
                    InternetAddress a = (InternetAddress) address;
                    from = a.getAddress();
                } else {
                    from = address.toString();
                }
                if (condition.getPostLimit().intValue() == 1) {
                    // Knowledge登録ユーザのみ投稿可能
                    UsersEntity user = UsersDao.get().selectOnMail(from);
                    if (user != null) {
                        target = true;
                        break;
                    }
                } else if (condition.getPostLimit().intValue() == 2) {
                    // 特定ドメインのメールアドレスならばOK
                    if (StringUtils.isEmpty(condition.getLimitParam())) {
                        // 許可するドメインが登録されていないので投稿できない
                        return false;
                    }
                    String[] domains = condition.getLimitParam().split(",");
                    for (String domain : domains) {
                        // メールアドレスの中にドメインに一致する文字があればOKにしとく（サブドメインなどもOKになる）
                        if (from.indexOf(domain) != -1) {
                            target = true;
                            break;
                        }
                    }
                }
            }
            if (!target) {
                StringBuilder f = new StringBuilder();
                for (Address address : in) {
                    if (f.length() > 0) {
                        f.append(", ");
                    }
                    String from;
                    if (address instanceof InternetAddress) {
                        InternetAddress a = (InternetAddress) address;
                        from = a.getAddress();
                    } else {
                        from = address.toString();
                    }
                    f.append(from);
                }
                LOG.info("Email was destroyed because it is not email posting of target.  -> " + f.toString());
                // 投稿できない
                return false;
            }
        }
        
        // 受信したメールの内容が、投稿する条件に合致するかチェック
        if (condition.getConditionKind().intValue() == MailHookCondition.Recipient.getValue()) {
            Address[] to = msg.getAllRecipients();
            for (Address address : to) {
                if (address instanceof InternetAddress) {
                    InternetAddress a = (InternetAddress) address;
                    if (a.getAddress().indexOf(condition.getCondition())  != -1) {
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
        MailHookConditionsDao.get().physicalDelete(conditionNo, MAIL_HOOK_ID);
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
        List<MailPropertiesEntity> now = MailPropertiesDao.get().selectOnHookId(MAIL_HOOK_ID);
        for (MailPropertiesEntity mailPropertiesEntity : now) {
            MailPropertiesDao.get().physicalDelete(mailPropertiesEntity);
        }
        MailHooksDao.get().physicalDelete(mailHookId);
    }

    /**
     * メール受信の設定を保存
     * @param entity
     * @param properties
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void saveMailConfig(MailHooksEntity entity, List<MailPropertiesEntity> properties) {
        MailHooksDao.get().save(entity);
        List<MailPropertiesEntity> now = MailPropertiesDao.get().selectOnHookId(MAIL_HOOK_ID);
        for (MailPropertiesEntity mailPropertiesEntity : now) {
            MailPropertiesDao.get().physicalDelete(mailPropertiesEntity);
        }
        for (MailPropertiesEntity mailPropertiesEntity : properties) {
            MailPropertiesDao.get().save(mailPropertiesEntity);
        }
    }

}
