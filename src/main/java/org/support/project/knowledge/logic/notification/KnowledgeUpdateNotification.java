package org.support.project.knowledge.logic.notification;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.support.project.aop.Aspect;
import org.support.project.common.config.INT_FLAG;
import org.support.project.common.config.Resources;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.NumberUtils;
import org.support.project.common.util.RandomUtil;
import org.support.project.common.util.StringUtils;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.config.AppConfig;
import org.support.project.knowledge.dao.ExUsersDao;
import org.support.project.knowledge.dao.KnowledgeGroupsDao;
import org.support.project.knowledge.dao.KnowledgeItemValuesDao;
import org.support.project.knowledge.dao.KnowledgeUsersDao;
import org.support.project.knowledge.dao.KnowledgesDao;
import org.support.project.knowledge.dao.NotifyConfigsDao;
import org.support.project.knowledge.dao.NotifyQueuesDao;
import org.support.project.knowledge.dao.TargetsDao;
import org.support.project.knowledge.dao.TemplateMastersDao;
import org.support.project.knowledge.entity.KnowledgeGroupsEntity;
import org.support.project.knowledge.entity.KnowledgeItemValuesEntity;
import org.support.project.knowledge.entity.KnowledgeUsersEntity;
import org.support.project.knowledge.entity.KnowledgesEntity;
import org.support.project.knowledge.entity.MailLocaleTemplatesEntity;
import org.support.project.knowledge.entity.NotifyConfigsEntity;
import org.support.project.knowledge.entity.NotifyQueuesEntity;
import org.support.project.knowledge.entity.TemplateItemsEntity;
import org.support.project.knowledge.entity.TemplateMastersEntity;
import org.support.project.knowledge.logic.KnowledgeLogic;
import org.support.project.knowledge.logic.MailLogic;
import org.support.project.knowledge.logic.NotificationLogic;
import org.support.project.knowledge.logic.NotifyLogic;
import org.support.project.knowledge.logic.notification.webhook.KnowledgeUpdateWebHookNotification;
import org.support.project.knowledge.vo.GroupUser;
import org.support.project.knowledge.vo.notification.KnowledgeUpdate;
import org.support.project.web.bean.LoginedUser;
import org.support.project.web.bean.MessageResult;
import org.support.project.web.dao.MailConfigsDao;
import org.support.project.web.dao.MailsDao;
import org.support.project.web.dao.NotificationsDao;
import org.support.project.web.entity.GroupsEntity;
import org.support.project.web.entity.MailConfigsEntity;
import org.support.project.web.entity.MailsEntity;
import org.support.project.web.entity.NotificationsEntity;
import org.support.project.web.entity.UsersEntity;

import net.arnx.jsonic.JSON;

/**
 * ナレッジを登録・更新した際の通知処理
 * 
 * @author koda
 */
@DI(instance = Instance.Prototype)
public class KnowledgeUpdateNotification extends AbstractQueueNotification implements DesktopNotification {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(KnowledgeUpdateNotification.class);

    /** インスタンス取得 */
    public static KnowledgeUpdateNotification get() {
        return Container.getComp(KnowledgeUpdateNotification.class);
    }

    /** Updated KnowledgesEntity */
    private KnowledgesEntity knowledge;

    public KnowledgesEntity getKnowledge() {
        return knowledge;
    }

    public void setKnowledge(KnowledgesEntity knowledge) {
        this.knowledge = knowledge;
    }

    @Override
    public void insertNotifyQueue() {
        NotifyQueuesEntity entity = new NotifyQueuesEntity();
        entity.setHash(RandomUtil.randamGen(30));
        entity.setType(getType());
        entity.setId(knowledge.getKnowledgeId());

        NotifyQueuesDao notifyQueuesDao = NotifyQueuesDao.get();
        // 重複チェックし
        if (NumberUtils.is(entity.getType(), QueueNotification.TYPE_KNOWLEDGE_INSERT)) {
            // ナレッジの新規登録は必ず通知のキューに入れる
            notifyQueuesDao.insert(entity);
        } else if (NumberUtils.is(entity.getType(), QueueNotification.TYPE_KNOWLEDGE_UPDATE)) {
            // ナレッジが更新された場合、キューに「登録通知」もしくは「更新通知」が存在しているのであれば登録しない
            NotifyQueuesEntity exist = notifyQueuesDao.selectOnTypeAndId(entity.getType(), entity.getId());
            if (exist == null) {
                exist = notifyQueuesDao.selectOnTypeAndId(QueueNotification.TYPE_KNOWLEDGE_INSERT, entity.getId());
                if (exist == null) {
                    notifyQueuesDao.insert(entity);
                }
            }
        }
    }

    @Override
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void notify(NotifyQueuesEntity notifyQueue) throws Exception {
        // ナレッジが登録/更新された
        KnowledgesDao knowledgesDao = KnowledgesDao.get();
        KnowledgesEntity knowledge = knowledgesDao.selectOnKeyWithUserName(notifyQueue.getId());
        if (null == knowledge) {
            LOG.warn("Knowledge record not found. id: " + notifyQueue.getId());
            return;
        }

        // Webhook通知
        KnowledgeUpdateWebHookNotification webhook = KnowledgeUpdateWebHookNotification.get();
        webhook.init(knowledge, notifyQueue.getType());
        webhook.saveWebhookData();

        // 「非公開」のナレッジは、メール通知対象外
        if (knowledge.getPublicFlag() == KnowledgeLogic.PUBLIC_FLAG_PUBLIC) {
            notifyPublicKnowledgeUpdate(notifyQueue, knowledge);
            updateNotifyStatus(knowledge);
        } else if (knowledge.getPublicFlag() == KnowledgeLogic.PUBLIC_FLAG_PROTECT) {
            notifyProtectKnowledgeUpdate(notifyQueue, knowledge);
            updateNotifyStatus(knowledge);
        }

    }

    /**
     * 「公開」のナレッジを登録・更新した際にメール通知を送信する
     * 
     * @param notifyQueuesEntity
     * @param knowledge
     * @throws Exception
     */
    private void notifyPublicKnowledgeUpdate(NotifyQueuesEntity notifyQueuesEntity, KnowledgesEntity knowledge) throws Exception {
        // ナレッジ登録ONでかつ、公開区分「公開」を除外しないユーザに通知
        List<UsersEntity> users = ExUsersDao.get().selectNotifyPublicUsers();
        notifyKnowledgeUpdateToUsers(notifyQueuesEntity, knowledge, users);
    }

    /**
     * 「保護」のナレッジを登録・更新した際にメール通知を送信する
     * 
     * @param notifyQueuesEntity
     * @param knowledge
     * @throws Exception
     */
    private void notifyProtectKnowledgeUpdate(NotifyQueuesEntity notifyQueuesEntity, KnowledgesEntity knowledge) throws Exception {
        List<UsersEntity> users = new ArrayList<>();
        // 宛先の一覧取得
        TargetsDao targetsDao = TargetsDao.get();
        List<UsersEntity> targetUsers = targetsDao.selectUsersOnKnowledgeId(knowledge.getKnowledgeId());
        users.addAll(targetUsers);

        // グループの一覧
        List<GroupsEntity> targetGroups = targetsDao.selectGroupsOnKnowledgeId(knowledge.getKnowledgeId());
        for (GroupsEntity groupsEntity : targetGroups) {
            List<GroupUser> groupUsers = ExUsersDao.get().selectGroupUser(groupsEntity.getGroupId(), 0, Integer.MAX_VALUE);
            for (GroupUser groupUser : groupUsers) {
                if (!contains(users, groupUser)) {
                    users.add(groupUser);
                }
            }
        }

        Iterator<UsersEntity> iterator = users.iterator();
        while (iterator.hasNext()) {
            UsersEntity usersEntity = (UsersEntity) iterator.next();
            // 自分宛てのナレッジ登録／更新で通知するかどうかの判定
            NotifyConfigsDao notifyConfigsDao = NotifyConfigsDao.get();
            NotifyConfigsEntity notifyConfigsEntity = notifyConfigsDao.selectOnKey(usersEntity.getUserId());
            if (notifyConfigsEntity == null) {
                iterator.remove();
            } else if (!INT_FLAG.flagCheck(notifyConfigsEntity.getToItemSave())) {
                iterator.remove();
            }
        }
        notifyKnowledgeUpdateToUsers(notifyQueuesEntity, knowledge, users);
    }

    /**
     * 指定のユーザ一覧に、ナレッジを登録・更新した際にメール通知を送信する
     * 
     * @param notifyQueuesEntity
     * @param knowledge
     * @param users
     * @throws Exception
     */
    private void notifyKnowledgeUpdateToUsers(NotifyQueuesEntity notifyQueuesEntity, KnowledgesEntity knowledge, List<UsersEntity> users)
            throws Exception {
        StringBuilder content = new StringBuilder();
        // テンプレートの種類をセット
        TemplateMastersEntity templateMaster = TemplateMastersDao.get().selectWithItems(knowledge.getTypeId());
        content.append("Type: ").append(templateMaster.getTypeName()).append("\n");
        // テンプレートの拡張項目の情報もメールにセットする
        List<KnowledgeItemValuesEntity> values = KnowledgeItemValuesDao.get().selectOnKnowledgeId(knowledge.getKnowledgeId());
        List<TemplateItemsEntity> items = templateMaster.getItems();
        for (KnowledgeItemValuesEntity val : values) {
            for (TemplateItemsEntity item : items) {
                if (val.getItemNo().equals(item.getItemNo())) {
                    item.setItemValue(val.getItemValue());
                    content.append(item.getItemName()).append(": ").append(item.getItemValue()).append("\n");
                    break;
                }
            }
        }
        if (values.size() > 0) {
            content.append("\n");
        }
        content.append(knowledge.getContent());

        // 画面での通知の情報を登録
        NotificationsEntity notification = insertNotificationOnKnowledgeUpdate(knowledge);

        for (UsersEntity usersEntity : users) {
            // 通知とユーザの紐付け
            NotificationLogic.get().insertUserNotification(notification, usersEntity);

            if (!StringUtils.isEmailAddress(usersEntity.getMailAddress())) {
                // 送信先のメールアドレスが不正なのでこのユーザにはメール送信しない
                LOG.warn("mail targget [" + usersEntity.getMailAddress() + "] is wrong.");
                continue;
            }
            if (LOG.isTraceEnabled()) {
                LOG.trace("[Notify] " + usersEntity.getMailAddress());
            }
            Locale locale = usersEntity.getLocale();
            MailLocaleTemplatesEntity template;
            if (knowledge.getNotifyStatus() == null || knowledge.getNotifyStatus().intValue() == 0) {
                template = MailLogic.get().load(locale, MailLogic.NOTIFY_INSERT_KNOWLEDGE);
            } else {
                template = MailLogic.get().load(locale, MailLogic.NOTIFY_UPDATE_KNOWLEDGE);
            }
            // メール送信
            insertNotifyKnowledgeUpdateMailQue(knowledge, usersEntity, template, content.toString());
        }
    }

    /**
     * メール送信のキュー情報を登録する
     * 
     * @param knowledge
     * @param usersEntity
     * @param content
     * @param config
     * @throws Exception
     */
    private void insertNotifyKnowledgeUpdateMailQue(KnowledgesEntity knowledge, UsersEntity usersEntity, MailLocaleTemplatesEntity template,
            String content) throws Exception {
        MailConfigsDao mailConfigsDao = MailConfigsDao.get();
        MailConfigsEntity mailConfigsEntity = mailConfigsDao.selectOnKey(AppConfig.get().getSystemName());
        if (mailConfigsEntity == null) {
            // メールの設定が登録されていなければ、送信処理は終了
            LOG.info("mail config is not exists.");
            return;
        }
        if (!StringUtils.isEmailAddress(usersEntity.getMailAddress())) {
            // 送信先のメールアドレスが不正なので、送信処理は終了
            LOG.warn("mail targget [" + usersEntity.getMailAddress() + "] is wrong.");
            return;
        }

        MailsEntity mailsEntity = new MailsEntity();
        String mailId = idGen("Notify");
        mailsEntity.setMailId(mailId);
        mailsEntity.setStatus(MailLogic.MAIL_STATUS_UNSENT);
        mailsEntity.setToAddress(usersEntity.getMailAddress());
        mailsEntity.setToName(usersEntity.getUserName());
        String title = template.getTitle();
        title = title.replace("{KnowledgeId}", knowledge.getKnowledgeId().toString());
        title = title.replace("{KnowledgeTitle}", StringUtils.abbreviate(knowledge.getTitle(), 80));
        mailsEntity.setTitle(title);
        String contents = template.getContent();
        contents = contents.replace("{KnowledgeId}", knowledge.getKnowledgeId().toString());
        contents = contents.replace("{KnowledgeTitle}", knowledge.getTitle());
        contents = contents.replace("{User}", knowledge.getUpdateUserName());

        // コンテンツがHTMLであった場合、テキストを取得する
        contents = contents.replace("{Contents}", MailLogic.get().getMailContent(content));
        contents = contents.replace("{URL}", NotifyLogic.get().makeURL(knowledge.getKnowledgeId()));
        mailsEntity.setContent(contents);
        if (LOG.isDebugEnabled()) {
            LOG.debug("News email has been registered. [type] knowledge update. [knowledge]" + knowledge.getKnowledgeId().toString() + " [target] "
                    + usersEntity.getMailAddress());
        }
        MailsDao.get().insert(mailsEntity);
    }

    /**
     * 既に指定のユーザが追加されているのか確認
     * 
     * @param users
     * @param groupUser
     * @return
     */
    private boolean contains(List<UsersEntity> users, UsersEntity groupUser) {
        for (UsersEntity usersEntity : users) {
            if (usersEntity.equalsOnKey(groupUser)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 通知を送ったもののステータスを更新
     * 
     * @param knowledge
     */
    private void updateNotifyStatus(KnowledgesEntity knowledge) {
        if (knowledge.getNotifyStatus() == null || knowledge.getNotifyStatus().intValue() == 0) {
            knowledge.setNotifyStatus(INT_FLAG.ON.getValue()); // 通知済へ
            KnowledgesDao.get().physicalUpdate(knowledge); // 更新日時などは更新しない
        }
    }

    /**
     * Knowledgeの登録/更新時の通知情報を作成
     * 
     * @param knowledge
     * @return
     */
    private NotificationsEntity insertNotificationOnKnowledgeUpdate(KnowledgesEntity knowledge) {
        // 通知情報を作成
        NotificationsEntity notification = new NotificationsEntity();
        if (knowledge.getNotifyStatus() == null || knowledge.getNotifyStatus().intValue() == 0) {
            notification.setTitle(MailLogic.NOTIFY_INSERT_KNOWLEDGE);
        } else {
            notification.setTitle(MailLogic.NOTIFY_UPDATE_KNOWLEDGE);
        }
        KnowledgeUpdate update = new KnowledgeUpdate();
        update.setKnowledgeId(knowledge.getKnowledgeId());
        update.setKnowledgeTitle(knowledge.getTitle());
        update.setUpdateUser(knowledge.getUpdateUserName());
        notification.setContent(JSON.encode(update));
        notification = NotificationsDao.get().insert(notification);
        return notification;
    }

    /**
     * ユーザへの通知を意味のある形へ変換
     * 
     * @param notificationsEntity
     * @param loginedUser
     */
    public void convNotification(NotificationsEntity notificationsEntity, LoginedUser loginedUser, TARGET target) {
        String category = notificationsEntity.getTitle();
        if (MailLogic.NOTIFY_INSERT_KNOWLEDGE.equals(category) || MailLogic.NOTIFY_UPDATE_KNOWLEDGE.equals(category)) {
            KnowledgeUpdate update = JSON.decode(notificationsEntity.getContent(), KnowledgeUpdate.class);
            MailLocaleTemplatesEntity template = MailLogic.get().load(loginedUser.getLocale(), MailLogic.NOTIFY_INSERT_KNOWLEDGE);

            String title = template.getTitle();
            title = title.replace("{KnowledgeId}", String.valueOf(update.getKnowledgeId()));
            title = title.replace("{KnowledgeTitle}", StringUtils.abbreviate(update.getKnowledgeTitle(), 80));
            notificationsEntity.setTitle(title);

            if (target == TARGET.detail) {
                String contents = template.getContent();
                contents = contents.replace("{KnowledgeId}", String.valueOf(update.getKnowledgeId()));
                contents = contents.replace("{KnowledgeTitle}", update.getKnowledgeTitle());
                contents = contents.replace("{User}", update.getUpdateUser());
                contents = contents.replace("{URL}", NotifyLogic.get().makeURL(update.getKnowledgeId()));
                KnowledgesEntity entity = KnowledgeLogic.get().select(update.getKnowledgeId(), loginedUser);
                if (entity != null) {
                    contents = contents.replace("{Contents}", entity.getContent());
                } else {
                    contents = contents.replace("{Contents}", "");
                }
                notificationsEntity.setContent(contents);
            }
        }
    }

    @Override
    public MessageResult getMessage(LoginedUser loginuser, Locale locale) {
        if (getType() == TYPE_KNOWLEDGE_UPDATE) {
            return getUpdateKnowledgeMessage(loginuser, locale, "knowledge.notify.msg.desktop.to.update");
        } else {
            return getUpdateKnowledgeMessage(loginuser, locale, "knowledge.notify.msg.desktop.to.insert");
        }
    }

    /**
     * 指定のナレッジは、自分宛てのナレッジかどうかを判定し、メッセージを取得する
     * 
     * @param loginuser
     * @param knowledge
     * @return
     */
    private MessageResult getUpdateKnowledgeMessage(LoginedUser loginuser, Locale locale, String title) {
        // TODO ストック機能ができたら、ストックしたナレッジの更新かどうかを判定する
        if (isToKnowledgeSave(loginuser, knowledge)) {
            MessageResult messageResult = new MessageResult();
            messageResult.setMessage(Resources.getInstance(locale).getResource(title, String.valueOf(knowledge.getKnowledgeId())));
            messageResult.setResult(NotifyLogic.get().makeURL(knowledge.getKnowledgeId())); // Knowledgeへのリンク
            return messageResult;
        }
        return null;
    }

    /**
     * 自分宛てのナレッジが登録／更新されたか判定する
     * 
     * @param loginuser
     * @param knowledge
     * @return
     */
    private boolean isToKnowledgeSave(LoginedUser loginuser, KnowledgesEntity knowledge) {
        NotifyConfigsDao dao = NotifyConfigsDao.get();
        NotifyConfigsEntity entity = dao.selectOnKey(loginuser.getUserId());
        if (!NotifyLogic.get().flagCheck(entity.getNotifyDesktop())) {
            // デスクトップ通知対象外
            return false;
        }
        // 自分が登録した場合は、通知する必要無し？いったんは通知する
        // if (knowledge.getInsertUser().intValue() == loginuser.getUserId().intValue()) {
        // return false;
        // }
        if (!NotifyLogic.get().flagCheck(entity.getToItemSave())) {
            // 自分宛てのナレッジが登録／更新されたら通知するがOFF
            return false;
        }
        return isToKnowledge(loginuser, knowledge, entity);
    }

    /**
     * 指定のナレッジは、自分宛てのナレッジかどうかを判定する 自分宛ては以下の場合を言う ・公開区分が「公開」でかつ、「公開」でも通知する設定になっている場合 ・公開区分が「保護」でかつ、宛先に自分が入っている場合
     * 
     * @param loginuser
     * @param knowledge
     * @param entity
     * @return
     */
    private boolean isToKnowledge(LoginedUser loginuser, KnowledgesEntity knowledge, NotifyConfigsEntity entity) {
        if (knowledge.getPublicFlag() == KnowledgeLogic.PUBLIC_FLAG_PUBLIC) {
            // 公開のナレッジ
            if (!NotifyLogic.get().flagCheck(entity.getToItemIgnorePublic())) {
                // 公開も除外しない
                return true;
            }
        } else if (knowledge.getPublicFlag() == KnowledgeLogic.PUBLIC_FLAG_PROTECT) {
            // 保護のナレッジ
            KnowledgeGroupsDao knowledgeGroupsDao = KnowledgeGroupsDao.get();
            List<KnowledgeGroupsEntity> groupsEntities = knowledgeGroupsDao.selectOnKnowledgeId(knowledge.getKnowledgeId());
            List<GroupsEntity> groups = loginuser.getGroups();
            for (KnowledgeGroupsEntity knowledgeGroupsEntity : groupsEntities) {
                for (GroupsEntity groupsEntity : groups) {
                    if (knowledgeGroupsEntity.getGroupId().intValue() == groupsEntity.getGroupId().intValue()) {
                        return true;
                    }
                }
            }

            KnowledgeUsersDao knowledgeUsersDao = KnowledgeUsersDao.get();
            List<KnowledgeUsersEntity> usersEntities = knowledgeUsersDao.selectOnKnowledgeId(knowledge.getKnowledgeId());
            for (KnowledgeUsersEntity knowledgeUsersEntity : usersEntities) {
                if (knowledgeUsersEntity.getUserId().intValue() == loginuser.getUserId().intValue()) {
                    return true;
                }
            }
        }
        return false;
    }

}
