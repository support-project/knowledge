package org.support.project.knowledge.bat;

import java.util.List;

import org.apache.commons.lang.ClassUtils;
import org.support.project.common.config.Flag;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.NumberUtils;
import org.support.project.knowledge.dao.KnowledgeFilesDao;
import org.support.project.knowledge.dao.NotifyQueuesDao;
import org.support.project.knowledge.entity.NotifyQueuesEntity;
import org.support.project.web.dao.MailsDao;
import org.support.project.web.entity.MailsEntity;

public class KnowledgeFileClearBat extends AbstractBat {
	
	/** ログ */
	private static Log LOG = LogFactory.getLog(KnowledgeFileClearBat.class);
	
	public static void main(String[] args) {
		initLogName("KnowledgeFileClearBat.log");
		configInit(ClassUtils.getShortClassName(KnowledgeFileClearBat.class));
		
		KnowledgeFileClearBat bat = new KnowledgeFileClearBat();
		bat.dbInit();
		bat.start();
		
		finishInfo();
	}

	private void start() {
		KnowledgeFilesDao filesDao = KnowledgeFilesDao.get();
		int count = filesDao.deleteNotConnectFiles();
		if (count > 0) {
			LOG.debug("Knowledge Files deleted. Count: " + count + "");
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
			if (NumberUtils.is(mailsEntity.getStatus(), MailSendBat.MAIL_STATUS_SENDED)) {
				// 送信済みになっているものは、削除
				MailsDao.get().physicalDelete(mailsEntity);
			}
		}
	}

}
