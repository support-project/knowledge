package org.support.project.knowledge.logic.notification;

import org.support.project.common.config.Resources;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.StringUtils;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.config.AppConfig;
import org.support.project.knowledge.dao.KnowledgesDao;
import org.support.project.knowledge.entity.KnowledgesEntity;
import org.support.project.knowledge.entity.MailLocaleTemplatesEntity;
import org.support.project.knowledge.logic.EventsLogic;
import org.support.project.knowledge.logic.MailLogic;
import org.support.project.knowledge.logic.NotifyLogic;
import org.support.project.web.dao.MailConfigsDao;
import org.support.project.web.dao.MailsDao;
import org.support.project.web.dao.UsersDao;
import org.support.project.web.entity.MailConfigsEntity;
import org.support.project.web.entity.MailsEntity;
import org.support.project.web.entity.UsersEntity;

/**
 * 参加登録したことを、参加者へ通知
 * @author koda
 */
@DI(instance = Instance.Singleton)
public class ParticipateForParticipantNotification extends AbstractParticipateNotification{
    /** ログ */
    private static final Log LOG = LogFactory.getLog(ParticipateForParticipantNotification.class);
    /** インスタンス取得 */
    public static ParticipateForParticipantNotification get() {
        return Container.getComp(ParticipateForParticipantNotification.class);
    }

    @Override
    public void notify(Long knowledgeId, Integer userId, Integer status) {
        KnowledgesEntity knowledge = KnowledgesDao.get().selectOnKey(knowledgeId);
        if (knowledge == null) {
            LOG.info("knowledge [" + knowledgeId + "] is not exists.");
            return;
        }
        // 参加者
        UsersEntity participant = UsersDao.get().selectOnKey(userId);
        if (participant == null) {
            LOG.warn("sponsor or participant is not exist.");
            return;
        }
        
        // 通知情報生成
        insertNotification(knowledge, userId, status, MailLogic.NOTIFY_REGISTRATION_EVENT);
        
        MailConfigsDao mailConfigsDao = MailConfigsDao.get();
        MailConfigsEntity mailConfigsEntity = mailConfigsDao.selectOnKey(AppConfig.get().getSystemName());
        if (mailConfigsEntity == null) {
            // メールの設定が登録されていなければ、送信処理は終了
            LOG.info("mail config is not exists.");
            return;
        }
        if (!StringUtils.isEmailAddress(participant.getMailAddress())) {
            // 送信先のメールアドレスが不正なので、送信処理は終了
            LOG.warn("mail targget [" + participant.getMailAddress() + "] is wrong.");
            return;
        }

        MailsEntity mailsEntity = new MailsEntity();
        String mailId = idGen("Notify");
        mailsEntity.setMailId(mailId);
        mailsEntity.setStatus(MailLogic.MAIL_STATUS_UNSENT);
        mailsEntity.setToAddress(participant.getMailAddress());
        mailsEntity.setToName(participant.getUserName());
        
        MailLocaleTemplatesEntity template = MailLogic.get().load(participant.getLocale(), MailLogic.NOTIFY_REGISTRATION_EVENT);
        String title = template.getTitle();
        title = title.replace("{KnowledgeId}", knowledge.getKnowledgeId().toString());
        title = title.replace("{KnowledgeTitle}", StringUtils.abbreviate(knowledge.getTitle(), 80));
        mailsEntity.setTitle(title);
        String contents = template.getContent();
        contents = contents.replace("{KnowledgeId}", knowledge.getKnowledgeId().toString());
        contents = contents.replace("{KnowledgeTitle}", knowledge.getTitle());
        StringBuilder builder = new StringBuilder();
        Resources resources = Resources.getInstance(participant.getLocale());
        if (status == EventsLogic.STATUS_PARTICIPATION) {
            builder.append(resources.getResource("knowledge.view.label.status.participation"));
        } else {
            builder.append(resources.getResource("knowledge.view.label.status.wait.cansel"));
        }
        contents = contents.replace("{Status}", builder.toString());
        contents = contents.replace("{URL}", NotifyLogic.get().makeURL(knowledge.getKnowledgeId()));

        mailsEntity.setContent(contents);
        MailsDao.get().insert(mailsEntity);
    }
    
}
