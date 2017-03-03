package org.support.project.knowledge.bat;

import java.util.List;

import org.apache.commons.lang.ClassUtils;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.di.Container;
import org.support.project.knowledge.dao.NotifyQueuesDao;
import org.support.project.knowledge.entity.NotifyQueuesEntity;
import org.support.project.knowledge.logic.MailLogic;
import org.support.project.knowledge.vo.Notify;

/**
 * メッセージ処理を処理する定期的なバッチプログラム
 * @author Koda
 */
public class NotifyMailBat extends AbstractBat {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(NotifyMailBat.class);
    
    /**
     * バッチ処理の開始
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        try {
            initLogName("NotifyMailBat.log");
            configInit(ClassUtils.getShortClassName(NotifyMailBat.class));
            
            NotifyMailBat bat = Container.getComp(NotifyMailBat.class);
            bat.dbInit();
            bat.start();
            
            finishInfo();
        } catch (Exception e) {
            LOG.error("any error", e);
            throw e;
        }
    }

    /**
     * 通知キューを処理して、メール送信テーブルにメール通知を登録する
     * @throws Exception 
     */
    private void start() throws Exception {
        LOG.info("Notify process started.");
        NotifyQueuesDao notifyQueuesDao = NotifyQueuesDao.get();
        List<NotifyQueuesEntity> notifyQueuesEntities = notifyQueuesDao.selectAll();
        for (NotifyQueuesEntity notifyQueuesEntity : notifyQueuesEntities) {
            if (notifyQueuesEntity.getType() == Notify.TYPE_KNOWLEDGE_INSERT 
                    || notifyQueuesEntity.getType() == Notify.TYPE_KNOWLEDGE_UPDATE) {
                MailLogic.get().notifyKnowledgeUpdate(notifyQueuesEntity);
            } else if (notifyQueuesEntity.getType() == Notify.TYPE_KNOWLEDGE_COMMENT) {
                MailLogic.get().notifyCommentInsert(notifyQueuesEntity);
            } else if (notifyQueuesEntity.getType() == Notify.TYPE_KNOWLEDGE_LIKE) {
                MailLogic.get().notifyLikeInsert(notifyQueuesEntity);
            }
            // 通知のキューから削除
            //notifyQueuesDao.delete(notifyQueuesEntity);
            notifyQueuesDao.physicalDelete(notifyQueuesEntity); // とっておいてもしょうがないので物理削除
        }
        LOG.info("Notify process finished. count: " + notifyQueuesEntities.size());
    }
    
    




}
