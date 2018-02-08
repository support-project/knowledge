package org.support.project.knowledge.logic;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.support.project.common.config.INT_FLAG;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.config.NotifyType;
import org.support.project.knowledge.dao.CommentsDao;
import org.support.project.knowledge.dao.ExUsersDao;
import org.support.project.knowledge.dao.NotifyConfigsDao;
import org.support.project.knowledge.dao.TargetsDao;
import org.support.project.knowledge.entity.CommentsEntity;
import org.support.project.knowledge.entity.KnowledgesEntity;
import org.support.project.knowledge.entity.NotifyConfigsEntity;
import org.support.project.knowledge.vo.GroupUser;
import org.support.project.web.dao.UsersDao;
import org.support.project.web.entity.GroupsEntity;
import org.support.project.web.entity.UsersEntity;

/**
 * Notification on comment.
 * 
 * @author Koda
 *
 */
@DI(instance = Instance.Singleton)
public class NotifyCommentLogic extends NotifyLogic {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(MethodHandles.lookup());

    /**
     * インスタンスを取得
     * 
     * @return
     */
    public static NotifyCommentLogic get() {
        return Container.getComp(NotifyCommentLogic.class);
    }

    
    
    
    /**
     * コメントが更新された際に、自分宛てのコメントが更新された場合に通知するを選択しているユーザの一覧を取得する
     * @param notifyType 
     * 
     * @param comment
     * @param knowledge
     * @return
     */
    public List<UsersEntity> getTargetUsersOnComment(NotifyType notifyType, CommentsEntity comment, KnowledgesEntity knowledge) {
        CommentsDao commentsDao = CommentsDao.get();
        UsersDao usersDao = UsersDao.get();
        List<UsersEntity> users = new ArrayList<>(); // 宛先候補
        
        if (knowledge.getPublicFlag() == KnowledgeLogic.PUBLIC_FLAG_PRIVATE) {
            // 非公開のナレッジにコメントが登録された
            // 登録者がコメントを登録しただけなので、通知の対象外で良い
            return users; // 空のリストを返す
        } else if (knowledge.getPublicFlag() == KnowledgeLogic.PUBLIC_FLAG_PUBLIC) {
            // 公開なので、全てのユーザが宛先の対象
            // 自分が宛先でかつ、公開を除外しないユーザの一覧を取得
            int limit = 999999; // リミットは充分に大きい数にしておく
                                // （TODO 全てリストに入れるので、ユーザ数が膨大になったときには、OutOfMemoryが発生するが、そこまで大きな組織を想定していないのでいったんはこのまま）
            if (notifyType == NotifyType.Desktop) {
                List<UsersEntity> alluser = NotifyConfigsDao.get().getNotifyDesktopUsersOnPublicComment(limit, 0);
                users.addAll(alluser);
            } else if (notifyType == NotifyType.Mail) {
                List<UsersEntity> alluser = NotifyConfigsDao.get().getNotifyMailUsersOnPublicComment(limit, 0);
                users.addAll(alluser);
            }
            Iterator<UsersEntity> iterator = users.iterator();
            while (iterator.hasNext()) {
                UsersEntity usersEntity = (UsersEntity) iterator.next();
                if (usersEntity.getUserId().intValue() == knowledge.getInsertUser().intValue()) {
                    // Knowledgeの登録者には通知しない（自分が登録したナレッジにコメントを登録したら通知するかどうかは別の設定)
                    iterator.remove();
                }
            }
            
            // 公開を除外していても、自分が「コメント」を登録したナレッジの場合、通知する
            List<UsersEntity> addCommentUsers = new ArrayList<>();
            List<CommentsEntity> comments = commentsDao.selectOnKnowledgeId(knowledge.getKnowledgeId());
            for (CommentsEntity commentsEntity : comments) {
                UsersEntity writedUser = usersDao.selectOnKey(commentsEntity.getInsertUser());
                if (!contains(addCommentUsers, writedUser)) {
                    addCommentUsers.add(writedUser);
                }
            }
            iterator = addCommentUsers.iterator();
            while (iterator.hasNext()) {
                UsersEntity usersEntity = (UsersEntity) iterator.next();
                // 宛先候補のユーザが、「自分宛てのナレッジ登録／更新で通知するか」の設定を確認
                NotifyConfigsDao notifyConfigsDao = NotifyConfigsDao.get();
                NotifyConfigsEntity notifyConfigsEntity = notifyConfigsDao.selectOnKey(usersEntity.getUserId());
                if (notifyConfigsEntity == null) {
                    iterator.remove();
                } else if (!INT_FLAG.flagCheck(notifyConfigsEntity.getToItemComment())) {
                    iterator.remove();
                } else if (usersEntity.getUserId().intValue() == knowledge.getInsertUser().intValue()) {
                    // Knowledgeの登録者には通知しない（自分が登録したナレッジにコメントを登録したら通知するかどうかは別の設定)
                    iterator.remove();
                } else {
                    if (!contains(users, usersEntity)) {
                        users.add(usersEntity);
                    }
                }
            }
            return users;
        } else if (knowledge.getPublicFlag() == KnowledgeLogic.PUBLIC_FLAG_PROTECT) {
            // 非公開の区分で、宛先が指定されている
            
            // ユーザ指定の宛先の一覧取得は全て宛先候補に入れる
            TargetsDao targetsDao = TargetsDao.get();
            List<UsersEntity> targetUsers = targetsDao.selectUsersOnKnowledgeId(knowledge.getKnowledgeId());
            users.addAll(targetUsers);
            
            // グループ指定の宛先の場合、グループに所属しているユーザを宛先候補に入れる
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
                // 宛先候補のユーザが、「自分宛てのナレッジ登録／更新で通知するか」の設定を確認
                NotifyConfigsDao notifyConfigsDao = NotifyConfigsDao.get();
                NotifyConfigsEntity notifyConfigsEntity = notifyConfigsDao.selectOnKey(usersEntity.getUserId());
                if (notifyConfigsEntity == null) {
                    iterator.remove();
                } else if (!INT_FLAG.flagCheck(notifyConfigsEntity.getToItemComment())) {
                    iterator.remove();
                } else if (usersEntity.getUserId().intValue() == knowledge.getInsertUser().intValue()) {
                    // Knowledgeの登録者には通知しない（自分が登録したナレッジにコメントを登録したら通知するかどうかは別の設定)
                    iterator.remove();
                }
            }
            
            if (LOG.isDebugEnabled()) {
                for (UsersEntity usersEntity : users) {
                    LOG.debug("notify on added comment." + usersEntity.getUserId());
                }
            }
            return users;
        }
        return users;
    }

    /**
     * 自分が登録されたナレッジにコメントが追加されたら通知するになっていた場合に、ナレッジ登録者のユーザを取得する
     * 
     * @param knowledge
     * @return
     */
    public UsersEntity getInsertUserOnComment(NotifyType notifyType, CommentsEntity comment, KnowledgesEntity knowledge) {
        if (knowledge.getPublicFlag() == KnowledgeLogic.PUBLIC_FLAG_PRIVATE) {
            if (knowledge.getInsertUser().equals(comment.getInsertUser())) {
                // ナレッジが非公開で、コメント登録したユーザがナレッジ登録ユーザであれば通知する必要は無い
                return null;
            }
        }
        UsersDao usersDao = UsersDao.get();
        UsersEntity user = usersDao.selectOnKey(knowledge.getInsertUser());
        if (user != null) {
            NotifyConfigsDao notifyConfigsDao = NotifyConfigsDao.get();
            NotifyConfigsEntity notifyConfigsEntity = notifyConfigsDao.selectOnKey(user.getUserId());
            if (notifyConfigsEntity != null && INT_FLAG.flagCheck(notifyConfigsEntity.getMyItemComment())) {
                if (notifyType == NotifyType.Desktop && INT_FLAG.flagCheck(notifyConfigsEntity.getNotifyDesktop())) {
                    return user;
                } else if (notifyType == NotifyType.Mail && INT_FLAG.flagCheck(notifyConfigsEntity.getNotifyMail())) {
                    return user;
                }
            }
        }
        return null;
    }
}
