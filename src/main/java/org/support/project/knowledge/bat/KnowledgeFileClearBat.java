package org.support.project.knowledge.bat;

import org.apache.commons.lang.ClassUtils;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.knowledge.dao.KnowledgeFilesDao;

public class KnowledgeFileClearBat extends AbstractBat {
	
	/** ログ */
	private static Log LOG = LogFactory.getLog(KnowledgeFileClearBat.class);
	
	public static void main(String[] args) {
		initLogName("KnowledgeFileClearBat.log");
		configInit(ClassUtils.getShortClassName(KnowledgeFileClearBat.class));
		
		KnowledgeFileClearBat bat = new KnowledgeFileClearBat();
		bat.dbInit();
		bat.start();
	}

	private void start() {
		KnowledgeFilesDao filesDao = KnowledgeFilesDao.get();
		int count = filesDao.deleteNotConnectFiles();
		if (count > 0) {
			LOG.debug("Knowledge Files deleted. Count: " + count + "");
		}
	}

}
