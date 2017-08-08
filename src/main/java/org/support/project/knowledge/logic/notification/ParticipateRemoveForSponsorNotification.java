package org.support.project.knowledge.logic.notification;

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
import org.support.project.knowledge.logic.MailLogic;
import org.support.project.knowledge.logic.NotifyLogic;
import org.support.project.web.dao.MailConfigsDao;
import org.support.project.web.dao.MailsDao;
import org.support.project.web.dao.UsersDao;
import org.support.project.web.entity.MailConfigsEntity;
import org.support.project.web.entity.MailsEntity;
import org.support.project.web.entity.UsersEntity;

/**
 * 開催者（＝登録者）へ参加キャンセルのメール通知
 * @author koda
 */
@DI(instance = Instance.Singleton)
public class ParticipateRemoveForSponsorNotification extends AbstractParticipateNotification{
    /** ログ */
    private static final Log LOG = LogFactory.getLog(ParticipateRemoveForSponsorNotification.class);
    /** インスタンス取得 */
    public static ParticipateRemoveForSponsorNotification get() {
        return Container.getComp(ParticipateRemoveForSponsorNotification.class);
    }
    
    @Override
    public void notify(Long knowledgeId, Integer userId, Integer status) {
        KnowledgesEntity knowledge = KnowledgesDao.get().selectOnKey(knowledgeId);
        if (knowledge == null) {
            LOG.info("knowledge [" + knowledgeId + "] is not exists.");
            return;
        }
        // 開催者
        UsersEntity sponsor = UsersDao.get().selectOnKey(knowledge.getInsertUser());
        // 参加者
        UsersEntity participant = UsersDao.get().selectOnKey(userId);
        if (sponsor == null || participant == null) {
            LOG.warn("sponsor or participant is not exist.");
            return;
        }
        
        // 通知情報生成
        insertNotification(knowledge, userId, status, MailLogic.NOTIFY_REMOVE_PARTICIPATE);
        
        MailConfigsDao mailConfigsDao = MailConfigsDao.get();
        MailConfigsEntity mailConfigsEntity = mailConfigsDao.selectOnKey(AppConfig.get().getSystemName());
        if (mailConfigsEntity == null) {
            // メールの設定が登録されていなければ、送信処理は終了
            LOG.info("mail config is not exists.");
            return;
        }
        if (!StringUtils.isEmailAddress(sponsor.getMailAddress())) {
            // 送信先のメールアドレスが不正なので、送信処理は終了
            LOG.warn("mail targget [" + sponsor.getMailAddress() + "] is wrong.");
            return;
        }

        MailsEntity mailsEntity = new MailsEntity();
        String mailId = idGen("Notify");
        mailsEntity.setMailId(mailId);
        mailsEntity.setStatus(MailLogic.MAIL_STATUS_UNSENT);
        mailsEntity.setToAddress(sponsor.getMailAddress());
        mailsEntity.setToName(sponsor.getUserName());
        
        MailLocaleTemplatesEntity template = MailLogic.get().load(sponsor.getLocale(), MailLogic.NOTIFY_REMOVE_PARTICIPATE);
        String title = template.getTitle();
        title = title.replace("{KnowledgeId}", knowledge.getKnowledgeId().toString());
        title = title.replace("{KnowledgeTitle}", StringUtils.abbreviate(knowledge.getTitle(), 80));
        mailsEntity.setTitle(title);
        String contents = template.getContent();
        contents = contents.replace("{KnowledgeId}", knowledge.getKnowledgeId().toString());
        contents = contents.replace("{KnowledgeTitle}", knowledge.getTitle());
        contents = contents.replace("{Participant}", participant.getUserName());
        contents = contents.replace("{URL}", NotifyLogic.get().makeURL(knowledge.getKnowledgeId()));

        mailsEntity.setContent(contents);
        MailsDao.get().insert(mailsEntity);
    }


}
