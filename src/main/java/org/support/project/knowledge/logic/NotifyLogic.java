package org.support.project.knowledge.logic;

import java.util.List;
import java.util.Locale;

import org.support.project.common.config.INT_FLAG;
import org.support.project.common.config.Resources;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.NumberUtils;
import org.support.project.common.util.StringUtils;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.config.AppConfig;
import org.support.project.knowledge.config.SystemConfig;
import org.support.project.knowledge.dao.KnowledgeGroupsDao;
import org.support.project.knowledge.dao.KnowledgeUsersDao;
import org.support.project.knowledge.dao.KnowledgesDao;
import org.support.project.knowledge.dao.NotifyConfigsDao;
import org.support.project.knowledge.dao.NotifyQueuesDao;
import org.support.project.knowledge.entity.CommentsEntity;
import org.support.project.knowledge.entity.KnowledgeGroupsEntity;
import org.support.project.knowledge.entity.KnowledgeUsersEntity;
import org.support.project.knowledge.entity.KnowledgesEntity;
import org.support.project.knowledge.entity.LikesEntity;
import org.support.project.knowledge.entity.MailLocaleTemplatesEntity;
import org.support.project.knowledge.entity.NotifyConfigsEntity;
import org.support.project.knowledge.entity.NotifyQueuesEntity;
import org.support.project.knowledge.vo.Notify;
import org.support.project.knowledge.vo.notification.KnowledgeUpdate;
import org.support.project.knowledge.websocket.NotifyAction;
import org.support.project.web.bean.LoginedUser;
import org.support.project.web.bean.MessageResult;
import org.support.project.web.dao.SystemConfigsDao;
import org.support.project.web.entity.GroupsEntity;
import org.support.project.web.entity.NotificationsEntity;
import org.support.project.web.entity.SystemConfigsEntity;
import org.support.project.web.entity.UsersEntity;

import net.arnx.jsonic.JSON;
/**
 * 通知を処理するロジック
 * @author Koda
 *
 */
@DI(instance = Instance.Singleton)
public class NotifyLogic {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(NotifyLogic.class);
    /**
     * インスタンスを取得
     * @return
     */
    public static NotifyLogic get() {
        return Container.getComp(NotifyLogic.class);
    }

    /**
     * 指定のナレッジにアクセスするURLを作成
     * 
     * @param knowledge
     * @return
     */
    public String makeURL(Long knowledgeId) {
        SystemConfigsDao dao = SystemConfigsDao.get();
        SystemConfigsEntity config = dao.selectOnKey(SystemConfig.SYSTEM_URL, AppConfig.get().getSystemName());
        if (config == null) {
            return "";
        }

        StringBuilder builder = new StringBuilder();
        builder.append(config.getConfigValue());
        if (!config.getConfigValue().endsWith("/")) {
            builder.append("/");
        }
        builder.append("open.knowledge/view/");
        builder.append(knowledgeId);
        return builder.toString();
    }

    /**
     * 通知を処理
     * 
     * @param notify
     */
    private void notify(Notify notify) {
        // Mail通知
        NotifyQueuesDao notifyQueuesDao = NotifyQueuesDao.get();
        NotifyQueuesEntity notifyQueuesEntity = notify.getQueue();
        // 重複チェックし
        if (NumberUtils.is(notifyQueuesEntity.getType(), Notify.TYPE_KNOWLEDGE_INSERT)) {
            // ナレッジの新規登録は必ず通知のキューに入れる
            notifyQueuesDao.insert(notifyQueuesEntity);
        } else if (NumberUtils.is(notifyQueuesEntity.getType(), Notify.TYPE_KNOWLEDGE_UPDATE)) {
            // ナレッジが更新された場合、キューに「登録通知」もしくは「更新通知」が存在しているのであれば登録しない
            NotifyQueuesEntity exist = notifyQueuesDao.selectOnTypeAndId(notifyQueuesEntity.getType(), notifyQueuesEntity.getId());
            if (exist == null) {
                exist = notifyQueuesDao.selectOnTypeAndId(Notify.TYPE_KNOWLEDGE_INSERT, notifyQueuesEntity.getId());
                if (exist == null) {
                    notifyQueuesDao.insert(notifyQueuesEntity);
                }
            }
        } else if (NumberUtils.is(notifyQueuesEntity.getType(), Notify.TYPE_KNOWLEDGE_LIKE)
                || NumberUtils.is(notifyQueuesEntity.getType(), Notify.TYPE_KNOWLEDGE_COMMENT)) {
            NotifyQueuesEntity exist = notifyQueuesDao.selectOnTypeAndId(notifyQueuesEntity.getType(), notifyQueuesEntity.getId());
            if (exist == null) {
                notifyQueuesDao.insert(notifyQueuesEntity);
            }
        }

        // Desktop通知は、多数のユーザを処理するので、別スレッドで処理する
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                LOG.debug("start notify");
                NotifyAction notifyAction = Container.getComp(NotifyAction.class);
                notifyAction.notifyObservers(notify);
                LOG.debug("end notify");
            }
        });
        t.setDaemon(true);
        t.start();
    }

    /**
     * ナレッジが登録された際の通知
     * 
     * @param knowledgesEntity
     */
    public void notifyOnKnowledgeInsert(KnowledgesEntity knowledgesEntity) {
        Notify notify = new Notify();
        notify.inserted(knowledgesEntity);
        notify(notify);
    }

    /**
     * ナレッジが更新された際の通知
     * 
     * @param knowledgesEntity
     */
    public void notifyOnKnowledgeUpdate(KnowledgesEntity knowledgesEntity) {
        Notify notify = new Notify();
        notify.updated(knowledgesEntity);

        notify(notify);
    }

    /**
     * ナレッジに「イイネ」が登録された際の通知
     * 
     * @param knowledgeId
     */
    public void notifyOnKnowledgeLiked(Long knowledgeId, LikesEntity likesEntity) {
        Notify notify = new Notify();
        notify.liked(likesEntity);

        notify(notify);
    }

    /**
     * ナレッジにコメントが追加された
     * 
     * @param knowledgeId
     */
    public void notifyOnKnowledgeComment(Long knowledgeId, CommentsEntity commentsEntity) {
        Notify notify = new Notify();
        notify.commented(commentsEntity);

        notify(notify);
    }

    /**
     * 指定のナレッジは、自分宛てのナレッジかどうかを判定し、メッセージを取得する
     * 
     * @param loginuser
     * @param knowledge
     * @return
     */
    public MessageResult getInsertKnowledgeMessage(LoginedUser loginuser, Locale locale, KnowledgesEntity knowledge) {
        if (isToKnowledgeSave(loginuser, knowledge)) {
            MessageResult messageResult = new MessageResult();
            messageResult.setMessage(
                    Resources.getInstance(locale).getResource("knowledge.notify.msg.desktop.to.insert", String.valueOf(knowledge.getKnowledgeId())));
            messageResult.setResult(makeURL(knowledge.getKnowledgeId())); // Knowledgeへのリンク
            return messageResult;
        }
        return null;
    }

    /**
     * 指定のナレッジは、自分宛てのナレッジかどうかを判定し、メッセージを取得する
     * 
     * @param loginuser
     * @param knowledge
     * @return
     */
    public MessageResult getUpdateKnowledgeMessage(LoginedUser loginuser, Locale locale, KnowledgesEntity knowledge) {
        // TODO ストック機能ができたら、ストックしたナレッジの更新かどうかを判定する
        if (isToKnowledgeSave(loginuser, knowledge)) {
            MessageResult messageResult = new MessageResult();
            messageResult.setMessage(
                    Resources.getInstance(locale).getResource("knowledge.notify.msg.desktop.to.update", String.valueOf(knowledge.getKnowledgeId())));
            messageResult.setResult(makeURL(knowledge.getKnowledgeId())); // Knowledgeへのリンク
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
        if (!flagCheck(entity.getNotifyDesktop())) {
            // デスクトップ通知対象外
            return false;
        }
//  自分が登録した場合は、通知する必要無し？いったんは通知する
//        if (knowledge.getInsertUser().intValue() == loginuser.getUserId().intValue()) {
//             return false;
//        }
        if (!flagCheck(entity.getToItemSave())) {
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
            if (!flagCheck(entity.getToItemIgnorePublic())) {
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

    /**
     * Integer型のフラグをチェック
     * 
     * @param check
     * @return
     */
    public boolean flagCheck(Integer check) {
        if (check == null) {
            return false;
        }
        if (check.intValue() == INT_FLAG.ON.getValue()) {
            return true;
        }
        return false;
    }

    /**
     * 指定の「イイネ」の追加で通知するかどうかを判定し、通知する場合はメッセージを取得する
     * 
     * @param loginuser
     * @param locale
     * @param like
     * @return
     */
    public MessageResult getSaveLikeMessage(LoginedUser loginuser, Locale locale, LikesEntity like) {
        NotifyConfigsDao dao = NotifyConfigsDao.get();
        NotifyConfigsEntity entity = dao.selectOnKey(loginuser.getUserId());
        if (!flagCheck(entity.getNotifyDesktop())) {
            // デスクトップ通知対象外
            return null;
        }
        KnowledgesDao knowledgesDao = KnowledgesDao.get();
        KnowledgesEntity knowledge = knowledgesDao.selectOnKey(like.getKnowledgeId());

        if (flagCheck(entity.getMyItemLike()) && knowledge.getInsertUser().intValue() == loginuser.getUserId().intValue()) {
            // 自分で投稿したナレッジにイイネが押されたので通知
            MessageResult messageResult = new MessageResult();
            messageResult.setMessage(Resources.getInstance(locale).getResource("knowledge.notify.msg.desktop.myitem.like",
                    String.valueOf(knowledge.getKnowledgeId())));
            messageResult.setResult(makeURL(knowledge.getKnowledgeId())); // Knowledgeへのリンク
            return messageResult;
        }

        return null;
    }
    
    
    
    /**
     * 既に指定のユーザが追加されているのか確認
     * @param users
     * @param groupUser
     * @return
     */
    protected boolean contains(List<UsersEntity> users, UsersEntity groupUser) {
        for (UsersEntity usersEntity : users) {
            if (usersEntity.equalsOnKey(groupUser)) {
                return true;
            }
        }
        return false;
    }
    
    
    

}
