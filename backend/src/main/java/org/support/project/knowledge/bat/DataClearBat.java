package org.support.project.knowledge.bat;

import java.lang.invoke.MethodHandles;
import java.util.List;

import org.apache.commons.lang.ClassUtils;
import org.support.project.common.config.Flag;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.NumberUtils;
import org.support.project.knowledge.dao.KnowledgeFilesDao;
import org.support.project.knowledge.dao.NotifyQueuesDao;
import org.support.project.knowledge.entity.KnowledgeFilesEntity;
import org.support.project.knowledge.entity.NotifyQueuesEntity;
import org.support.project.knowledge.logic.LogManageLogic;
import org.support.project.knowledge.logic.MailLogic;
import org.support.project.knowledge.logic.UploadedFileLogic;
import org.support.project.web.dao.MailsDao;
import org.support.project.web.dao.TokensDao;
import org.support.project.web.entity.MailsEntity;

public class DataClearBat extends AbstractBat {
    
    /** ログ */
    private static final Log LOG = LogFactory.getLog(MethodHandles.lookup());
    
    public static void main(String[] args) {
        try {
            initLogName("KnowledgeFileClearBat.log");
            configInit(ClassUtils.getShortClassName(DataClearBat.class));
            
            DataClearBat bat = new DataClearBat();
            bat.dbInit();
            bat.start();
            
            finishInfo();
        } catch (Exception e) {
            LOG.error("any error", e);
            throw e;
        }
    }

    private void start() {
        KnowledgeFilesDao filesDao = KnowledgeFilesDao.get();
        List<KnowledgeFilesEntity> files = filesDao.deleteNotConnectFiles();
        if (files != null && !files.isEmpty()) {
            LOG.debug("Knowledge Files deleted. Count: " + files.size() + "");
            for (KnowledgeFilesEntity file : files) {
                // 添付ファイルの削除
                try {
                    UploadedFileLogic.get().removeFile(file.getFileNo());
                } catch (Exception e) {
                    LOG.error("File delete error. file_no = " + file.getFileNo(), e);
                }
            }
        }
        
        // メール送信とNotifyのゴミ情報をクリア
        List<NotifyQueuesEntity> notifyQueuesEntities = NotifyQueuesDao.get().physicalSelectAll();
        for (NotifyQueuesEntity notifyQueuesEntity : notifyQueuesEntities) {
            if (Flag.is(notifyQueuesEntity.getDeleteFlag())) {
                // 削除フラグはONになっているのに、物理削除していないものは物理削除する（とっておいてもしかたない）
                NotifyQueuesDao.get().physicalDelete(notifyQueuesEntity);
            }
        }
        
        List<MailsEntity> mailsEntities = MailsDao.get().physicalSelectAll();
        for (MailsEntity mailsEntity : mailsEntities) {
            if (NumberUtils.is(mailsEntity.getStatus(), MailLogic.MAIL_STATUS_SENDED)) {
                // 送信済みになっているものは、削除
                MailsDao.get().physicalDelete(mailsEntity);
            }
        }
        
        // ログファイルの定期削除
        LogManageLogic.get().clearLogFiles();
        // 有効期限が切れてしばらく経過したTokenを削除
        TokensDao.get().removeExpiredToken(1, 1000 * 60 * 60 * 24 * 180); // UIで発行したTokenは180日たったら削除
        TokensDao.get().removeExpiredToken(2, 1000 * 60 * 60 * 24 * 5); // 自動的に発行したTokenは有効期限が切れて5日たったら削除
    }

}
